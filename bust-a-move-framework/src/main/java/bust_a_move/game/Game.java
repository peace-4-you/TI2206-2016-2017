/*
 * File: game.java
 * Class: Game
 *
 * Version: 2.0
 * Date: October 11, 2016
 */

package bust_a_move.game;

import org.newdawn.slick.AppGameContainer;
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
import bust_a_move.game.GameData.GameDataState;
import bust_a_move.gamestate.Button;
import bust_a_move.gamestate.GameConfig;
import bust_a_move.gamestate.GameState;
import bust_a_move.log.Log;

/**
 * The Game class represents a game entity. Contains all GameModels
 *
 * @author Maurice Willemsen
 * @version 11.10.2016
 */
public class Game extends BasicGameState {
	/**
	 * The statebasedgame and container for runing BasicGameState instance.
	 */
	private StateBasedGame sbg;
	private GameContainer container;
	/**
	 * lists of all Game Data objects
	 */
	private ArrayList<GameData> gamedata;
	/**
	 * Button instance and x coordinate for a pause button
	 */
	private Button pause;
	private static final int button_X = 503;

	/**
	 * Public constructor.
	 */
	public Game() {
		this.gamedata = new ArrayList<GameData>();
		Log.log(this, "Game initialised");
	}

	/**
	 * Starts a 2 player mode,
	 * 
	 * @throws SlickException
	 *             all kind of SlickException
	 */
	public final void start2Player() throws SlickException {
		AppGameContainer gc = (AppGameContainer) this.container;
		gc.setDisplayMode(1040, 580, false);
		GameData firstplayer = new GameData(0, 2);
		GameData secondplayer = new GameData(500, 1);
		this.gamedata.add(firstplayer);
		this.gamedata.add(secondplayer);
		firstplayer.getPlayer().notifyAmountObserver(2);
	}

	/**
	 * Starts a 1 player mode,
	 */
	public final void start1Player() {
		GameData firstplayer = new GameData(0, 1);
		this.gamedata.add(firstplayer);
		firstplayer.getPlayer().notifyAmountObserver(1);
	}

	/**
	 * Clears the game, Emptys the list of GameDatas
	 * 
	 * @throws SlickException
	 *             All Kind of SlickException
	 */
	public final void destroyGame() throws SlickException {
		this.gamedata.clear();
		AppGameContainer gc = (AppGameContainer) this.container;
		gc.setDisplayMode(640, 580, false);
	}

	/**
	 * Ends game, changes state to WON.
	 */
	private void wonGame() {
		Log.log(this, "Game Won");
		sbg.enterState(GameState.WIN_SCREEN, new FadeOutTransition(), new FadeInTransition());
	}

	/**
	 * Ends game, changes state to FAILED.
	 */
	private void failedGame() {
		Log.log(this, "Game Failed");
		sbg.enterState(GameState.DEFEAT_SCREEN, new FadeOutTransition(), new FadeInTransition());
	}

	/**
	 * Pauses game, changes state to PAUSE.
	 */
	private void pauseGame() {
		Log.log(this, "Game Paused");
		sbg.enterState(GameState.PAUSE_SCREEN, new FadeOutTransition(), new FadeInTransition());
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
	public final void init(final GameContainer game, final StateBasedGame stateBasedGame) throws SlickException {
		this.sbg = stateBasedGame;
		this.container = game;
		this.pause = new Button("Pause", Game.button_X, GameConfig.SEVENTH_LINE, GameConfig.WIDTH1, GameConfig.HEIGHT);
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
		this.sbg = sbg;
		this.container = container;
		boolean loosing = true;
		for (GameData gd : this.gamedata) {
			gd.update(container, sbg, delta);
			GameDataState state = gd.getState();
			if (state == GameDataState.RUNNING) {
				loosing = false;
			} else if (state == GameDataState.WON) {
				this.wonGame();
			}
		}
		if (loosing == true) {
			this.failedGame();
		}
		if (container.getInput().isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
			if (pause.isInBounds(container.getInput())) {
				this.pauseGame();
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
	public final void render(final GameContainer container, final StateBasedGame sbg, final Graphics g)
			throws SlickException {
		g.setColor(Color.gray);
		g.fillRect(0, 530, 1040, 580);
		pause.draw(g);
		for (GameData gd : this.gamedata) {
			gd.render(container, sbg, g);
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
	 * Getter method: for the GameData list
	 * 
	 * @return List of GameData objects
	 */
	public ArrayList<GameData> getGameData() {
		return this.gamedata;
	}
}