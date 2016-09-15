/*
 * File: game.java
 * Class: Game
 *
 * Version: 0.0.3
 * Date: September 12th, 2016
 */

package nl.tudelft.bust_a_move20162017.bust_a_move_framework.game;

import nl.tudelft.bust_a_move20162017.bust_a_move_framework.cannon.Cannon;
import nl.tudelft.bust_a_move20162017.bust_a_move_framework.bubble.Bubble;
import nl.tudelft.bust_a_move20162017.bust_a_move_framework.bubble.BubbleGenerator;
import nl.tudelft.bust_a_move20162017.bust_a_move_framework.arena.Arena;
import nl.tudelft.bust_a_move20162017.bust_a_move_framework.player.Player;

import java.util.ArrayList;

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
	private static final int TIME_TOO_SHOOT = 5000;

	public Cannon cannon;
	public ArrayList<Bubble> bubbleslist;
	private Arena arena;
	private Player player;
	private BubbleGenerator bubblegen;

	private StateBasedGame sbg;

	/**
	 * Starts game, creates new arena and cannon instance
	 */

	private void startGame() {
		this.bubbleslist = new ArrayList<Bubble>();
		this.cannon = new Cannon(this);
		this.arena = new Arena(0, 0, 100, 100);
	}

	/**
	 * Ends game, changes state to WON
	 */

	private void wonGame() {
		sbg.enterState(5, new FadeOutTransition(), new FadeInTransition());
	}

	/**
	 * Ends game, changes state to FAILED
	 */

	private void failedGame() {
		sbg.enterState(6, new FadeOutTransition(), new FadeInTransition());
	}

	/**
	 * Pauses game, changes state to FAILED
	 */

	private void pauseGame() {
		sbg.enterState(4, new FadeOutTransition(), new FadeInTransition());
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
		this.LEVEL = 1;
		this.player = new Player("Player1");
		this.bubblegen = new BubbleGenerator(arena);
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
		if (cannon.TIME_SHOT_FIRED > TIME_TOO_SHOOT) {
			cannon.fire();
			cannon.TIME_SHOT_FIRED = 0;
		}
		if(cannon.TIMES_SHOT == 10) {
			cannon.TIMES_SHOT = 0;
			arena.addBubbleRow();
		}
		for (Bubble bubble : this.bubbleslist) {
			bubble.move();
		}
	}

	public void render(GameContainer container, StateBasedGame sbg, Graphics g) throws SlickException {
		g.setColor(Color.gray);
		g.fillRect(0, 430, 640, 480);
		g.setColor(Color.white);
		g.drawString("Level:" + this.LEVEL, 10, 130);
		cannon.draw(g);
		player.draw(g);
		arena.draw(g);
		for (Bubble bubble : this.bubbleslist) {
			bubble.draw(g);
		}

	}

	public BubbleGenerator Generator() {
		BubbleGenerator nextBubbleGen = new BubbleGenerator(arena);
		return nextBubbleGen;
	}

	public int getID() {
		return 3;
	}

	public BubbleGenerator getBubbleGen() {
		return this.bubblegen;
	}
}
