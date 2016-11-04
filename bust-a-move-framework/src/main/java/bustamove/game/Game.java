/*
 * File: game.java
 * Class: Game
 *
 * Version: 2.0
 * Date: October 11, 2016
 */
package bustamove.game;

import bustamove.App;
import bustamove.game.GameData.GameDataState;
import bustamove.game.GameData.GameDifficulty;
import bustamove.player.Player;
import bustamove.player.Statistics;
import bustamove.screen.attributes.Button;
import bustamove.screen.config.GameConfig;
import bustamove.screen.config.GameState;
import bustamove.system.Log;
import bustamove.system.SoundHandler;
import bustamove.util.PlayerObserver;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

import java.util.ArrayList;

/**
 * The Game class represents a game entity. Contains all GameModels.
 *
 * @author Maurice Willemsen
 * @version 11.10.2016
 */
public final class Game extends BasicGameState {
    private static final int BUTTON_X = 510;
    private static final int DRAWING_OFFSET = 500;
    private static final int MILLIS_TO_SECONDS = 1000;
    /**
     * Dimensions of the gray rectangle at the bottom of the game.
     */
    private static final int GRAYRECT_X = 0;
    private static final int GRAYRECT_Y = 530;
    private static final int GRAYRECT_WIDTH = 1040;
    private static final int GRAYRECT_HEIGHT = 580;
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
     * The observers that look at the players of this game.
     */
    private ArrayList<PlayerObserver> pObservers;
    /**
     * Button instance and x coordinate for a pause button.
     */
    private Button pause;
    /**
     * The high scores object.
     */
    private Highscore highscore;
    /**
     * The statistics object.
     */
    private Statistics stats;
    /**
     * The current game time in ms.
     */
    private long gametime = 0;

    /**
     * Private constructor.
     */
    public Game() {
        this.gamedata = new ArrayList<GameData>();
        this.pObservers = new ArrayList<PlayerObserver>();
        Log.getInstance().log(this, "Game initialised");
    }

    /**
     * Starts a 2 player mode.
     *
     * @param d GameDifficulty of game
     *
     * @throws SlickException all kind of SlickException
     */
    public void start2Player(GameDifficulty d) throws SlickException {
        Log.getInstance().log(this, "start2playergame");
        AppGameContainer gc = (AppGameContainer) this.container;
        gc.setDisplayMode(App.GAME_WIDTH_MULTIPLAYER, App.GAME_HEIGHT, false);
        GameData firstplayer = new GameData(0, 2, d);
        GameData secondplayer = new GameData(DRAWING_OFFSET, 1, d);
        addGameData(firstplayer);
        addGameData(secondplayer);
        firstplayer.getPlayer().notifyAmountObserver(2);
    }

    /**
     * Starts a 1 player mode.
     *
     * @param d GameDifficulty of game
     */
    public void start1Player(GameDifficulty d) {
        GameData firstplayer = new GameData(0, 1, d);
        addGameData(firstplayer);
        firstplayer.getPlayer().notifyAmountObserver(1);
    }

    /**
     * Clears the game, Emptys the list of GameDatas.
     *
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
        Log.getInstance().log(this, "Game Won");
        SoundHandler.getInstance().playWinSound();
        for (GameData gd : gamedata) {
            highscore.addEntryAndSave(gd.getPlayer().getName(),
                    gd.getPlayer().getScore().getScore());
        }
        stats.win();
        stats.setTimeWon((int) Math.floor(gametime
                / (double) GameConfig.MILLISECONDS_IN_SECOND));
        gametime = 0;
        sbg.enterState(GameState.WIN_SCREEN, new FadeOutTransition(),
                new FadeInTransition());
    }

    /**
     * Ends game, changes state to FAILED.
     */
    private void failedGame() {
        for (GameData gd : gamedata) {
            Player p = gd.getPlayer();
            highscore.addEntryAndSave(p.getName(),
                    p.getScore().getScore());
        }
        Log.getInstance().log(this, "Game Failed");
        SoundHandler.getInstance().playLoseSound();
        gametime = 0;
        sbg.enterState(GameState.DEFEAT_SCREEN, new FadeOutTransition(),
                new FadeInTransition());
    }

    /**
     * Pauses game, changes state to PAUSE.
     */
    private void pauseGame() {
        Log.getInstance().log(this, "Game Paused");
        sbg.enterState(GameState.PAUSE_SCREEN, new FadeOutTransition(),
                new FadeInTransition());
    }

    /**
     * Called when BasicGameState initializes.
     *
     * @param game           the game container
     * @param stateBasedGame the state based game
     * @throws SlickException any type of slick exception
     */
    public void init(final GameContainer game,
                     final StateBasedGame stateBasedGame)
            throws SlickException {
        this.sbg = stateBasedGame;
        this.container = game;
        this.pause = new Button("Pause", Game.BUTTON_X, GameConfig.NINETH_LINE,
                GameConfig.WIDTH1, GameConfig.HEIGHT);
    }

    /**
     * Updates the BasicGameState.
     *
     * @param game           the game container
     * @param stateBasedGame the state based game
     * @param delta          delta of time exceeded
     * @throws SlickException any type of slick exception
     */
    public void update(final GameContainer game,
                       final StateBasedGame stateBasedGame,
                       final int delta) throws SlickException {
        this.sbg = stateBasedGame;
        this.container = game;
        gametime += delta;
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
     *
     * @param game           the game container
     * @param stateBasedGame the state based game
     * @param g              Graphics object
     * @throws SlickException any type of slick exception
     */
    public void render(final GameContainer game,
                       final StateBasedGame stateBasedGame,
                       final Graphics g) throws SlickException {
        g.setColor(Color.gray);
        g.fillRect(GRAYRECT_X, GRAYRECT_Y, GRAYRECT_WIDTH, GRAYRECT_HEIGHT);
        pause.draw(g);
        g.setColor(Color.white);
        g.drawString("Time:" + this.gametime / MILLIS_TO_SECONDS + "s",
                BUTTON_X, GameConfig.EIGTH_LINE);
        for (GameData gd : this.gamedata) {
            gd.render(game, stateBasedGame, g);
        }
    }

    /**
     * Getter method: for the GameState ID.
     *
     * @return integer of BasicGameState number.
     */
    public int getID() {
        return GameState.GAME_ACTIVE;
    }

    /**
     * Getter method: for the GameData list.
     *
     * @return List of GameData objects
     */
    public ArrayList<GameData> getGameData() {
        return this.gamedata;
    }

    /**
     * Sets the highscore object of this class.
     *
     * @param hs The highscore object.
     */
    public void setHighscores(final Highscore hs) {
        this.highscore = hs;
    }

    /**
     * Sets the statistics object of this class.
     *
     * @param s The statistics object.
     */
    public void setStatistics(final Statistics s) {
        stats = s;
    }

    /**
     * Add the game data to the list.
     *
     * @param gd The GameData to add.
     */
    private void addGameData(final GameData gd) {
        this.gamedata.add(gd);
        for (PlayerObserver o : pObservers) {
            gd.registerPlayerObservers(o);
        }
    }

    /**
     * Adds observer to all Player objects created and those that will
     * be created through this game.
     *
     * @param o The PlayerObserver to add.
     */
    public void registerPlayerObserver(final PlayerObserver o) {
        pObservers.add(o);
        for (GameData gd : gamedata) {
            gd.registerPlayerObservers(o);
        }
    }

}
