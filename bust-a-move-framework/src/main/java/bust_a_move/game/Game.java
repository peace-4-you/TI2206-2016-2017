/*
 * File: game.java
 * Class: Game
 *
 * Version: 0.0.3
 * Date: September 12th, 2016
 */

package bust_a_move.game;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

import bust_a_move.bubble.Bubble;
import bust_a_move.gamestate.Button;
import bust_a_move.gamestate.GameConfig;
import bust_a_move.gamestate.GameState;
import bust_a_move.log.Log;
import bust_a_move.player.Player;

/**
 * The Game class represents a game entity.
 *
 * @author Maurice Willemsen
 */

public class Game extends BasicGameState implements Observer {

	/**
	 * Score of the game, updated by observer.
	 */
	private int score;
	/**
	 * Cannon instance for the game.
	 */
	public Cannon cannon;
	/**
	 * Arraylist of bubbles.
	 */
	public ArrayList<Bubble> bubbleslist;
	/**
	 * Player instance for the game.
	 */
	public Player player;
	/**
	 * Log instance for logging the game.
	 */
	public Log log;
	/**
	 * Arena instance for in the game.
	 */
	public Arena arena;
	/**
	 * Button instance for a pause button.
	 */
	private Button pause;
	/**
	 * The statebasedgame instance.
	 */
	private StateBasedGame sbg;
	/**
	 * X position for the button.
	 */
	private static final int button_X = 503;
	/**
	 * X position for the arena.
	 */
	private static final int arena_x = 180;
	/**
	 * Y position for the arena.
	 */
	private static final int arena_y = 0;
	/**
	 * width for the arena.
	 */
	private static final int arena_width = 531;
	/**
	 * height  for the arena.
	 */
	private static final int arena_height = 280;

	/**
	 * Public constructor.
	 */
	public Game() {
		this.log = new Log();
		log.log(this, "Game initialised");
	}

	/**
	 * Start function to initialize the player.
	 */
	public void initialisePlayer() {
		this.player = new Player("Player1");
		this.player.getScore().addAsObserver(this);
	}

	/**
	 * Starts game, creates new arena, cannon, BubbleFactory instance, and a
	 * bubblelist array.
	 */
	private void startGame() {
		log.log(this, "Game Started");
		this.bubbleslist = new ArrayList<Bubble>();
		this.arena = new Arena(Game.arena_x, Game.arena_y, Game.arena_width, Game.arena_height);
		this.cannon = new Cannon(this);
	}

	/**
	 * Ends game, changes state to WON.
	 */

	private void wonGame() {
		log.log(this, "Game Won");
		sbg.enterState(GameState.WIN_SCREEN, new FadeOutTransition(),
			new FadeInTransition());
	}

	/**
	 * Ends game, changes state to FAILED.
	 */

	private void failedGame() {
		log.log(this, "Game Failed");
		sbg.enterState(GameState.DEFEAT_SCREEN, new FadeOutTransition(),
			new FadeInTransition());
	}

	/**
	 * Pauses game, changes state to PAUSE.
	 */

	private void pauseGame() {
		log.log(this, "Game Paused");
		sbg.enterState(GameState.PAUSE_SCREEN, new FadeOutTransition(),
			new FadeInTransition());
	}

	/**
	 * Called when BasicGameState initializes.
	 * 
	 * @param game
	 *            the game container
	 * @param stateBasedGame
	 *            the state based game
	 * @throws SlickException
	 *             any type of slick exception
	 */
	public final void init(final GameContainer game,
		final StateBasedGame stateBasedGame) throws SlickException {
		this.sbg = stateBasedGame;
		this.pause = new Button("Pause", Game.button_X, GameConfig.SECOND_LINE,
			GameConfig.WIDTH1, GameConfig.HEIGHT);
		this.player.reset();
		Bubble.reset();
		this.startGame();
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

	public final void update(final GameContainer container,
		final StateBasedGame sbg, final int delta)
		throws SlickException {
		this.sbg = sbg;
		this.cannon.update(container, delta);

		for (Bubble b1 : bubbleslist) {
			b1.move();
			if (b1.getState() == Bubble.State.FIRING) {
				arena.checkCollision(b1);
			}
		}
		if (arena.getBubbleStorage().isFull()) {
			this.failedGame();
		}
		if (arena.getBubbleStorage().isEmpty()) {
			this.wonGame();
		}
		if (container.getInput().isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
			if (pause.isInBounds(container.getInput())) {
				this.pauseGame();
			}
		}
	}

	/**
	 * Updates the BasicGameState.
	 * 
	 * @param game
	 *            the game container
	 * @param stateBasedGame
	 *            the state based game
	 * @param i
	 *            delta of time exceeded
	 * @throws SlickException
	 *             any type of slick exception
	 */

	public final void render(final GameContainer game,
		final StateBasedGame stateBasedGame, final Graphics g)
		throws SlickException {
		g.setColor(Color.gray);
		g.fillRect(0, 530, 640, 580);
		g.setColor(Color.white);
		g.drawString("Score:" + this.score, 10, 70);
		g.drawString("Fire power: x" + (double) Math.round(Bubble.SPEED * 10
			/ 3) / 10, 10, 130);
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

	/**
	 * Getter method: for the GameState ID.
	 * 
	 * @return integer of BasicGameState number.
	 */

	public final int getID() {
		return GameState.GAME_ACTIVE;
	}

	/**
	 * Method to return the arena object inside game class.
	 *
	 * @return Arena object inside game class
	 */

	public final Arena getArena() {
		return this.arena;
	}

	/**
	 * Updates the Observer.
	 * 
	 * @param o
	 *            the observable item
	 * @param arg
	 *            the observable argument
	 */

	public final void update(final Observable o, final Object arg) {
		this.score = (Integer) arg;
	}
}