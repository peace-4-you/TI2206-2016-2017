/*
 * File: App.java
 * Class: App
 *
 * Version: 0.0.4
 * Date: September 26th, 2016
 */
package bustamove;

import java.awt.Font;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.state.StateBasedGame;

import bustamove.game.Game;
import bustamove.game.Highscore;
import bustamove.player.Statistics;
import bustamove.screen.DefeatScreen;
import bustamove.screen.DoubleNameScreen;
import bustamove.screen.HighscoreScreen;
import bustamove.screen.AchievementsScreen;
import bustamove.screen.CreditsScreen;
import bustamove.screen.MainMenu;
import bustamove.screen.PausedScreen;
import bustamove.screen.Screen;
import bustamove.screen.SingleNameScreen;
import bustamove.screen.StartScreen;
import bustamove.screen.VictoryScreen;
import bustamove.screen.OptionScreen;
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
     * Font for all the texts.
     */
    private static TrueTypeFont font;
    /**
     * All the states/screens in the game.
     */
    private static StartScreen startScreen;
    private static MainMenu mainMenu;
    private static SingleNameScreen singleNameScreen;
    private static DoubleNameScreen doubleNameScreen;
    private static Game game;
    private static Statistics stats;
    private static DefeatScreen defeatScreen;
    private static VictoryScreen victoryScreen;
    private static PausedScreen pausedScreen;
    private static Highscore highscores;
    private static HighscoreScreen highscoreScreen;
    private static AchievementsScreen achievementsScreen;
    private static OptionScreen optionScreen;
    private static CreditsScreen creditsScreen;

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
        stats = Statistics.read(GameConfig.STATISTICS_FILE);
        app.start();
    }

    /**
     * Initializes the screens and the font.
     */
    public static void init() {
        font = new TrueTypeFont(new Font("Verdana", Font.BOLD,
                GameConfig.TEXT_SIZE), true);
        Screen.setFonts(font);
        game = new Game();
        game.setHighscores(highscores);
        game.setStatistics(stats);
        startScreen = new StartScreen();
        mainMenu = new MainMenu();
        singleNameScreen = new SingleNameScreen();
        doubleNameScreen = new DoubleNameScreen();
        highscoreScreen = new HighscoreScreen(highscores);
        creditsScreen = new CreditsScreen();
        pausedScreen = new PausedScreen();
        victoryScreen = new VictoryScreen();
        defeatScreen = new DefeatScreen();
        achievementsScreen = new AchievementsScreen(stats);
        game.registerPlayerObserver(pausedScreen);
        game.registerPlayerObserver(victoryScreen);
        game.registerPlayerObserver(defeatScreen);
        optionScreen = new OptionScreen();
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

    /**
     * Returns the width of the game window.
     *
     * @return width in int form
     */
    public static final int getGameWidth() {
        return GAME_WIDTH;
    }

    /**
     * Returns the Statistics object of this game.
     *
     * @return The statistics object.
     */
    public static Statistics getStatistics() {
        return stats;
    }

    /**
     * Method that is run before the window is closed.
     */
    @Override
    public final boolean closeRequested() {
        stats.write();
        return true;
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
        init();
        addState(startScreen);
        addState(mainMenu);
        addState(game);
        addState(pausedScreen);
        addState(victoryScreen);
        addState(defeatScreen);
        addState(singleNameScreen);
        addState(doubleNameScreen);
        addState(highscoreScreen);
        addState(optionScreen);
        addState(creditsScreen);
        addState(achievementsScreen);
    }
}