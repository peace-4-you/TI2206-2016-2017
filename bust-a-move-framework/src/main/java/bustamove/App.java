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
import bustamove.game.Highscore;
import bustamove.screen.DefeatScreen;
import bustamove.screen.DoubleNameScreen;
import bustamove.screen.HighscoreScreen;
import bustamove.screen.MainMenu;
import bustamove.screen.PausedScreen;
import bustamove.screen.Screen;
import bustamove.screen.SingleNameScreen;
import bustamove.screen.StartScreen;
import bustamove.screen.VictoryScreen;
import bustamove.screen.config.GameConfig;
import bustamove.system.SoundHandler;

/**
 * The App class contains the launcher of the program.
 */

public class App extends StateBasedGame {
    /**
     * Width dimension of the single player game window.
     */
    public static final int GAME_WIDTH = 640;
    /**
     * Width dimension of the 2 player game window.
     */
    public static final int GAME_WIDTH_MULTIPLAYER = 1040;
    /**
     * Height dimension of the game window.
     */
    public static final int GAME_HEIGHT = 580;
    /**
     * Values for fps of the game.
     */
    private static final int MAX_FPS = 120;
    /**
     * The high scores object.
     */
    private static Highscore highscores;
    /**
     * Association with states.
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
     * This is the main() of the program. This method is called when the program
     * runs.
     *
     * @param args argument included at program call
     * @throws SlickException any type of Slick exception
     */
    public static void main(final String[] args) throws SlickException {
        AppGameContainer app = new AppGameContainer(new App("Bust-A-Move!!"));
        app.setDisplayMode(GAME_WIDTH, GAME_HEIGHT, false);
        app.setAlwaysRender(true);
        app.setTargetFrameRate(MAX_FPS);
        SoundHandler.getInstance().init();
        highscores = new Highscore();
        highscores.setFile(GameConfig.HIGHSCORE_FILE);
        highscores.load();
        app.start();
    }

    /**
     * Initialize all the game states that are used in the game.
     *
     * @param container the container holding the game
     * @throws SlickException indicates a failure to initialise the state based
     *                        game resources
     */
    public final void initStatesList(final GameContainer container)
            throws SlickException {
        Screen.setFonts();
        game = new Game();
        game.setHighscores(highscores);
        PausedScreen pausedScreen = new PausedScreen();
        VictoryScreen victoryScreen = new VictoryScreen();
        DefeatScreen defeatScreen = new DefeatScreen();
        StartScreen startScreen = new StartScreen();
        MainMenu mainMenu = new MainMenu();
        SingleNameScreen singleNameScreen = new SingleNameScreen();
        DoubleNameScreen doubleNameScreen = new DoubleNameScreen();
        HighscoreScreen highscoreScreen = new HighscoreScreen(highscores);
        addState(startScreen);
        addState(mainMenu);
        addState(game);
        addState(pausedScreen);
        addState(victoryScreen);
        addState(defeatScreen);
        addState(singleNameScreen);
        addState(doubleNameScreen);
        addState(highscoreScreen);
        game.registerPlayerObserver(pausedScreen);
        game.registerPlayerObserver(victoryScreen);
        game.registerPlayerObserver(defeatScreen);
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
     * Return the height of the game window.
     *
     * @return height in int form
     */
    public static final int getGameHeight() {
        return GAME_HEIGHT;
    }
}
