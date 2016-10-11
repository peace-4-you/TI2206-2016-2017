/*
 * File: GameData.java
 * Class: GameData
 *
 * Version: 1
 * Date: October 11, 2016
 */
package bust_a_move.game;

import java.util.ArrayList;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;
import bust_a_move.App;
import bust_a_move.bubble.Bubble;
import bust_a_move.gamestate.GameConfig;
import bust_a_move.log.Log;
import bust_a_move.player.Player;

/**
 * The Game class represents a single game model
 *
 * @author Maurice Willemsen
 * @version 11.10.2016
 */
public class GameData implements PlayerObserver {
	/**
	 * Enum for the state of the game model.
	 */
	public enum GameDataState {
		RUNNING, WON, LOOSE
	};

	private GameDataState state;
	/**
	 * Score and Name of the player, updated by observer.
	 */
	private int score;
	private String name;
	/**
	 * Cannon, Arena and Player instance for the game.
	 */
	private Cannon cannon;
	private Player player;
	private Arena arena;
	/**
	 * Arraylist of bubbles and their speed.
	 */
	public ArrayList<Bubble> bubbleslist;
	public double bubblespeed;
	/**
	 * position for the arena.
	 */
	private static final int arena_x = 180;
	private static final int arena_y = 0;
	private static final int arena_width = 531;
	private static final int arena_height = 280;
	/**
	 * GameModel number and the drawing offset.
	 */
	private int offset;
	private int player_nr;

	/**
	 * Constructor for the GameData
	 * 
	 * @param offset
	 *            integer of the drawing offset
	 * @param nr
	 *            integer of the GameData model
	 */
	public GameData(int offset, int nr) {
		Log.log(this, "GameData initialised");
		this.offset = offset;
		this.player_nr = nr;
		this.state = GameDataState.RUNNING;
		this.bubbleslist = new ArrayList<Bubble>();
		this.player = new Player("Player1", this.offset, this.player_nr);
		this.player.registerObserver((PlayerObserver) App.getPauseScreen());
		this.player.registerObserver((PlayerObserver) App.getDefeatScreen());
		this.player.registerObserver((PlayerObserver) this);
		this.arena = new Arena(GameData.arena_x + this.offset, GameData.arena_y, GameData.arena_width,
				GameData.arena_height, this);
		this.cannon = new Cannon(this, this.offset, this.player_nr);
		this.setBubbleSpeed(GameConfig.DEFAULT_BUBBLE_SPEED);
	}

	/**
	 * Updates the BasicGameState.
	 * 
	 * @param container
	 *            the game container
	 * @param sbg
	 *            the state based game
	 * @param delta
	 *            delta of time exceeded
	 * @throws SlickException
	 *             any type of slick exception
	 */
	public final void update(final GameContainer container, final StateBasedGame sbg, final int delta)
			throws SlickException {
		if (this.state == GameDataState.RUNNING) {
			this.cannon.update(container, delta, this);
			arena.getCollision().updateGame(this);
			for (Bubble b1 : bubbleslist) {
				b1.move();
				if (b1.getState() == Bubble.State.FIRING) {
					arena.checkCollision(b1);
				}
			}
			if (arena.getBubbleStorage().isFull()) {
				this.state = GameDataState.LOOSE;
			} else if (arena.getBubbleStorage().isEmpty()) {
				this.state = GameDataState.WON;
			}
		}
	}

	/**
	 * Renders the BasicGameState.
	 * 
	 * @param container
	 *            the game container
	 * @param sbg
	 *            the state based game
	 * @param g
	 *            Graphics object
	 * @throws SlickException
	 *             any type of slick exception
	 */
	public final void render(final GameContainer game, final StateBasedGame stateBasedGame, final Graphics g)
			throws SlickException {
		g.setColor(Color.white);
		g.drawString("Score:" + this.score, 10 + this.offset, 70);
		g.drawString("Name:" + this.name, 10 + this.offset,40);
		g.drawString("Fire power: x" + (double) Math.round(this.bubblespeed * 10 / 3) / 10, 10 + this.offset, 130);
		cannon.draw(g);
		player.draw(g);
		arena.draw(g);
		for (Bubble bubble : this.bubbleslist) {
			if (bubble.getState() != Bubble.State.LANDED) {
				bubble.draw(g);
			}
		}
	}

	/**
	 * Method to return the arena object inside gamedata class.
	 *
	 * @return Arena object inside gamedata class
	 */
	public final Arena getArena() {
		return this.arena;
	}

	/**
	 * Method to return the player object inside gamedata class.
	 *
	 * @return Player object inside gamedata class
	 */
	public final Player getPlayer() {
		return this.player;
	}

	/**
	 * Method to return the cannon object inside gamedata class.
	 *
	 * @return Cannon object inside gamedata class
	 */
	public final Cannon getCannon() {
		return this.cannon;
	}

	/**
	 * Method to return the gamedatastate object inside gamedata class.
	 *
	 * @return GameDataState object inside gamedata class
	 */
	public GameDataState getState() {
		return this.state;
	}

	/**
	 * Method to return the bubblespeed  inside gamedata class.
	 *
	 * @return Double bubblespeed object inside gamedata class
	 */
	public double getBubbleSpeed() {
		return this.bubblespeed;
	}

	/**
	 * Method to set the BubbleSpeed
	 * @param speed double to set speed to
	 */
	public void setBubbleSpeed(double speed) {
		this.bubblespeed = speed;
	}

	/**
	 * Empty update method from PlayerObserver interface.
	 */
	public void update(int playeramount) {}

	/**
	 * Updated by Observable. Sets data
	 * 
	 * @param number
	 *            integer of player number
	 * @param name
	 *            String of player name
	 * @param score
	 *            integer of player score
	 */
	public void update(int number, String name, int score) {
		if (number == player_nr){
			this.score = score;
			this.name = name;
		}		
	}
}
