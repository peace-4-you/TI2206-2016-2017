/*
 * File: App.java
 * Class: App
 *
 * Version: 0.0.4
 * Date: September 26th, 2016
 */
package bustamove;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import bustamove.game.Game;
import bustamove.gamestate.DefeatScreen;
import bustamove.gamestate.MainMenu;
import bustamove.gamestate.PausedScreen;
import bustamove.gamestate.StartScreen;
import bustamove.gamestate.VictoryScreen;

/**
 * The App class contains the launcher of the program.
 */

public class App extends StateBasedGame {
    /**
     * Dimensions of the game window.
     */
    private static final int GAME_WIDTH = 640;
    private static final int GAME_HEIGHT = 580;
    /**
     * Values for fps of the game.
     */
    private static final int MAX_FPS = 120;

    /**
     * Association with Game class.
     */
    private static Game game;

    /**
     * Constructor for the launcher of the game.
     *
     * @param title the name of the game
     */

    public App(final String title) {
        super(title);
    }

    /**
     * This is the main() of the program. This method is called when the
     * program runs.
     *
     * @param args argument included at program call
     * @throws SlickException any type of Slick exception
     */
    public static void main(final String[] args) throws SlickException {
        AppGameContainer app = new AppGameContainer(new App("Bust-A-Move!!"));
        app.setDisplayMode(GAME_WIDTH, GAME_HEIGHT, false);
        app.setAlwaysRender(true);
        app.setTargetFrameRate(MAX_FPS);
        app.start();
    }

    /**
     * Initialize all the game states that are used in the game.
     *
     * @param container the container holding the game
     * @throws SlickException indicates a failure to initialise the state
     *                        based game resources
     */
    public final void initStatesList(final GameContainer container)
            throws SlickException {
        game = new Game();
        game.initialisePlayer();
        addState(new StartScreen());
        addState(new MainMenu());
        addState(game);
        addState(new PausedScreen());
        addState(new VictoryScreen());
        addState(new DefeatScreen());
    }

    /**
     * Getter function for game object.
     *
     * @return the game object associated to this class
     */
    public static final Game getGame() {
        return game;
    }

    /**
     * Setter function for game object.
     *
     * @param g game object
     */
    public final void setGame(final Game g) {
        App.game = g;
    }

    /**
     * Return the height of the game window.
     *
     * @return height in int form
     */
    public static final int getGameHeight() {
        return GAME_HEIGHT;
    }

    /**
     * Returns the width of the game window.
     *
     * @return width in int form
     */
    public static final int getGameWidth() {
        return GAME_WIDTH;
    }
}
