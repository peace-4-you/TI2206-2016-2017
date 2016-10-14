/*
 * File: game.java
 * Class: Game
 *
 * Version: 2.0
 * Date: October 11, 2016
 */


package bustamove.game;

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

import bustamove.App;
import bustamove.game.GameData.GameDataState;
import bustamove.gamestate.Button;
import bustamove.gamestate.GameConfig;
import bustamove.gamestate.GameState;
import bustamove.system.Log;
import bustamove.sound.SoundHandler;

/**
 * The Game class represents a game entity. Contains all GameModels.
 * @author Maurice Willemsen
 * @version 11.10.2016
 */
public final class Game extends BasicGameState {
    /**
     * private game variable for Singleton.
     * Uses the eagerly created instance solution.
     */
    private static Game gameInstance = new Game();
    /**
     * The statebasedgame and container for runing BasicGameState instance.
     */
    private StateBasedGame sbg;
    private GameContainer container;
    /**
     * lists of all Game Data objects.
     */
    private ArrayList<GameData> gamedata;
    /**
     * Button instance and x coordinate for a pause button.
     */
    private Button pause;
    private static final int BUTTON_X = 503;
    private static final int DRAWING_OFFSET = 500;
    /**
     * Dimensions of the gray rectangle at the bottom of the game.
     */
    private static final int GRAYRECT_X = 0;
    private static final int GRAYRECT_Y = 530;
    private static final int GRAYRECT_WIDTH = 1040;
    private static final int GRAYRECT_HEIGHT = 580;

    /**
     * Singleton getInstance function.
     * @return the singleton game instance
     */
    public static Game getInstance() {
        return gameInstance;
    }

    /**
     * Private constructor.
     */
    private Game() {
        this.gamedata = new ArrayList<GameData>();
        Log.log(this, "Game initialised");
    }

    /**
     * Starts a 2 player mode.
     * @throws SlickException all kind of SlickException
     */
    public void start2Player() throws SlickException {
        AppGameContainer gc = (AppGameContainer) this.container;
        gc.setDisplayMode(App.GAME_WIDTH_MULTIPLAYER, App.GAME_HEIGHT, false);
        GameData firstplayer = new GameData(0, 2);
        GameData secondplayer = new GameData(DRAWING_OFFSET, 1);
        this.gamedata.add(firstplayer);
        this.gamedata.add(secondplayer);
        firstplayer.getPlayer().notifyAmountObserver(2);
    }

    /**
     * Starts a 1 player mode.
     */
    public void start1Player() {
        GameData firstplayer = new GameData(0, 1);
        this.gamedata.add(firstplayer);
        firstplayer.getPlayer().notifyAmountObserver(1);
    }

    /**
     * Clears the game, Emptys the list of GameDatas.
     * @throws SlickException All Kind of SlickException
     */
    public void destroyGame() throws SlickException {
        this.gamedata.clear();
        AppGameContainer gc = (AppGameContainer) this.container;
        gc.setDisplayMode(App.GAME_WIDTH, App.GAME_HEIGHT, false);
    }

    /**
     * Ends game, changes state to WON.
     */
    private void wonGame() {
        Log.log(this, "Game Won");
        SoundHandler.playWinSound();
        sbg.enterState(GameState.WIN_SCREEN, new FadeOutTransition(),
                new FadeInTransition());
    }

    /**
     * Ends game, changes state to FAILED.
     */
    private void failedGame() {
        Log.log(this, "Game Failed");
        SoundHandler.playLoseSound();
        sbg.enterState(GameState.DEFEAT_SCREEN, new FadeOutTransition(),
                new FadeInTransition());
    }

    /**
     * Pauses game, changes state to PAUSE.
     */
    private void pauseGame() {
        Log.log(this, "Game Paused");
        sbg.enterState(GameState.PAUSE_SCREEN, new FadeOutTransition(),
                new FadeInTransition());
    }

    /**
     * Called when BasicGameState initializes.
     * @param game the game container
     * @param stateBasedGame the state based game
     * @throws SlickException any type of slick exception
     */
    public void init(final GameContainer game,
            final StateBasedGame stateBasedGame) throws SlickException {
        this.sbg = stateBasedGame;
        this.container = game;
        this.pause = new Button("Pause", Game.BUTTON_X, GameConfig.SEVENTH_LINE,
                GameConfig.WIDTH1, GameConfig.HEIGHT);
    }

    /**
     * Updates the BasicGameState.
     * @param game the game container
     * @param stateBasedGame the state based game
     * @param delta delta of time exceeded
     * @throws SlickException any type of slick exception
     */
    public void update(final GameContainer game,
                             final StateBasedGame stateBasedGame,
                             final int delta) throws SlickException {
        this.sbg = stateBasedGame;
        this.container = game;
        boolean loosing = true;
        for (GameData gd : this.gamedata) {
            gd.update(container, sbg, delta);
            GameDataState state = gd.getState();
            if (state == GameDataState.RUNNING) {
                loosing = false;
            } else if (state == GameDataState.WON) {
                loosing = false;
                this.wonGame();
            }
        }
        if (loosing) {
            this.failedGame();
        }
        if (container.getInput().isMousePressed(Input.MOUSE_LEFT_BUTTON)
                && pause.isInBounds(container.getInput())) {
            this.pauseGame();
        }
    }

    /**
     * Renders the BasicGameState.
     * @param game the game container
     * @param stateBasedGame the state based game
     * @param g Graphics object
     * @throws SlickException any type of slick exception
     */
    public void render(final GameContainer game,
                             final StateBasedGame stateBasedGame,
                             final Graphics g) throws SlickException {
        g.setColor(Color.gray);
        g.fillRect(GRAYRECT_X, GRAYRECT_Y, GRAYRECT_WIDTH, GRAYRECT_HEIGHT);
        pause.draw(g);
        for (GameData gd : this.gamedata) {
            gd.render(game, stateBasedGame, g);
        }
    }

    /**
     * Getter method: for the GameState ID.
     * @return integer of BasicGameState number.
     */
    public int getID() {
        return GameState.GAME_ACTIVE;
    }

    /**
     * Getter method: for the GameData list.
     * @return List of GameData objects
     */
    public ArrayList<GameData> getGameData() {
        return this.gamedata;
    }
}

