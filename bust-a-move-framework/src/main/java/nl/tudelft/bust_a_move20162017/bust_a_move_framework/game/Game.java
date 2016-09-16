/*
 * File: game.java
 * Class: Game
 *
 * Version: 0.0.3
 * Date: September 12th, 2016
 */

package nl.tudelft.bust_a_move20162017.bust_a_move_framework.game;

import nl.tudelft.bust_a_move20162017.bust_a_move_framework.cannon.Cannon;
import nl.tudelft.bust_a_move20162017.bust_a_move_framework.gamestate.Button;
import nl.tudelft.bust_a_move20162017.bust_a_move_framework.gamestate.GameState;
import nl.tudelft.bust_a_move20162017.bust_a_move_framework.bubble.Bubble;
import nl.tudelft.bust_a_move20162017.bust_a_move_framework.bubble.BubbleFactory;
import nl.tudelft.bust_a_move20162017.bust_a_move_framework.arena.Arena;
import nl.tudelft.bust_a_move20162017.bust_a_move_framework.player.Player;

import java.util.ArrayList;
import java.util.LinkedList;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

/**
 * The Game class represents a game entity.
 *
 * @author Maurice Willemsen
 */

public class Game extends BasicGameState {

	private int LEVEL;
	private int TIME_PASSED_KEY_RIGHT;
	private int TIME_PASSED_KEY_LEFT;
	private static final int TIME_TO_SHOOT = 10000;

	private static final int TIME_DISPLAY_FIRE_WARNING = 3000;

	public Cannon cannon;
	public ArrayList<Bubble> bubbleslist;
	private Arena arena;
	private Player player;
	private BubbleFactory bubblegen;
	private Button pause;
	private StateBasedGame sbg;


	/**
	 * Starts game, creates new arena and cannon instance
	 */

	private void startGame() {
		this.bubbleslist = new ArrayList<Bubble>();
		this.arena = new Arena(180, 0, 531, 280);
		this.bubblegen = new BubbleFactory(this.arena);
		this.cannon = new Cannon(this);
	}

	/**
	 * Ends game, changes state to WON
	 */

	private void wonGame() {
		sbg.enterState(GameState.WIN_SCREEN, new FadeOutTransition(), new FadeInTransition());
	}

	/**
	 * Ends game, changes state to FAILED
	 */

	private void failedGame() {
		sbg.enterState(GameState.DEFEAT_SCREEN, new FadeOutTransition(), new FadeInTransition());
	}

	/**
	 * Pauses game, changes state to FAILED
	 */

	private void pauseGame() {
		sbg.enterState(GameState.PAUSE_SCREEN, new FadeOutTransition(), new FadeInTransition());
	}

	/**
	 * Asks for new level and new start of game.
	 */

	private void startNewLevel() {
		this.levelUp();
		this.startGame();
	}

	/**
	 * Level up
	 */

	private void levelUp() {
		this.LEVEL++;
	}

	public void init(GameContainer container, StateBasedGame sbg) throws SlickException {
		this.sbg = sbg;
		this.pause = new Button("Pause", 507,50, 100, 30);
		this.LEVEL = 1;
		this.player = new Player("Player1");
		this.startGame();
	}

	public void update(GameContainer container, StateBasedGame sbg, int delta) throws SlickException {
		this.sbg = sbg;
		cannon.TIME_SHOT_FIRED += delta;

		if (container.getInput().isKeyDown(Input.KEY_RIGHT)) {
			this.TIME_PASSED_KEY_RIGHT += delta;
			if (this.TIME_PASSED_KEY_RIGHT > 10) {
				cannon.stepDown();
				this.TIME_PASSED_KEY_RIGHT = 0;
			}
		} else {
			this.TIME_PASSED_KEY_RIGHT = 0;
		}
		if (container.getInput().isKeyDown(Input.KEY_LEFT)) {
			this.TIME_PASSED_KEY_LEFT += delta;
			if (this.TIME_PASSED_KEY_LEFT > 10) {
				cannon.stepUp();
				this.TIME_PASSED_KEY_LEFT = 0;
			}
		} else {
			this.TIME_PASSED_KEY_LEFT = 0;
		}
		if (container.getInput().isKeyPressed(Input.KEY_UP)) {
			cannon.fire();
		}

		if(cannon.TIME_SHOT_FIRED > TIME_TO_SHOOT - TIME_DISPLAY_FIRE_WARNING) {
			cannon.display_warning = true;
		}

		if (cannon.TIME_SHOT_FIRED > TIME_TO_SHOOT) {
			cannon.fire();
			cannon.TIME_SHOT_FIRED = 0;
			cannon.display_warning = false;
		}

		LinkedList<Bubble[]> arenaBubbles = this.arena.get_BubbleArray();
		for (Bubble b1 : bubbleslist) {
			b1.move();
			if (b1.getState() == Bubble.State.FIRING) {
				if (b1.getX() <= arena.get_xPos() || b1.getX()+Bubble.DIAMETER >= arena.get_xPos()+arena.getWidth()) {
					b1.hitWall();
				}
				// collision with landed bubbles
				collisionLoop:
				for (int i = 0; i < arenaBubbles.size(); i++) {
					for (int j = 0; j < arenaBubbles.get(i).length; j++) {
						Bubble b2 = arenaBubbles.get(i)[j];
						if (b2 != null && b1 != b2) {
							if (b2.getState() == Bubble.State.LANDED) {
								if (b1.getBoundingBox().intersects(b2.getBoundingBox())) {
									System.out.println("Collision! " + b1.getColor() + " with " + b2.getColor());
									arena.landBubble(b1);
									break collisionLoop;
								}
							}
						}
					}
				}
				// end collision with landed bubbles
			}

		}
		if (arena.isArenaFull()){
			this.failedGame();
		}
		if (arena.isArenaEmpty()){
			this.wonGame();
		}
        if (container.getInput().isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
            if (pause.isInBounds(container.getInput())) {
            	this.pauseGame();
            }
        }
	}

	public void render(GameContainer container, StateBasedGame sbg, Graphics g) throws SlickException {
		g.setColor(Color.gray);
		g.fillRect(0, 530, 640, 580);
		g.setColor(Color.white);
		g.drawString("Level:" + this.LEVEL, 10, 130);
		cannon.draw(g);
		player.draw(g);
		arena.draw(g);
		pause.draw(g);
		for (Bubble bubble : this.bubbleslist) {
			if (bubble.getState() != Bubble.State.LANDED) {
				bubble.draw(g);
			}
		}
	}

	public BubbleFactory Generator() {
		BubbleFactory nextBubbleGen = new BubbleFactory(arena);
		return nextBubbleGen;
	}

	public int getID() {
		return 3;
	}

	public BubbleFactory getBubbleGen() {
		return this.bubblegen;
	}

	public Arena getArena() {
		return this.arena;
	}
}
