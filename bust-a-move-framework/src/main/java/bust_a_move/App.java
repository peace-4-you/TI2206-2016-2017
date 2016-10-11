/*
 * File: App.java
 * Class: App
 *
 * Version: 0.0.4
 * Date: September 26th, 2016
 */
package bust_a_move;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import bust_a_move.game.Game;
import bust_a_move.gamestate.DefeatScreen;
import bust_a_move.gamestate.MainMenu;
import bust_a_move.gamestate.PausedScreen;
import bust_a_move.gamestate.StartScreen;
import bust_a_move.gamestate.VictoryScreen;
import bust_a_move.gamestate.NameScreen;
import bust_a_move.gamestate.NamesScreen;

/**
 * The App class contains the launcher of the program.
 */

public class App extends StateBasedGame {
    /**
     * Values for the width of the game window.
     */
    public static final int GAME_WIDTH = 640;
    /**
     * Values for the width of the game window.
     */
    public static final int GAME_WIDTH_MULTIPLAYER = 1040;
    /**
     * Values for the height of the game window.
     */
    public static final int GAME_HEIGHT = 580;
    /**
     * Values for fps of the game.
     */
    private static final int MAX_FPS = 120;

    /**
     * Association with Game class.
     */
    private static Game game;
    private static DefeatScreen defeatScreen;
    private static VictoryScreen victoryScreen;
    private static PausedScreen pausedScreen;
    

    /**
     * Constructor for the launcher of the game.
     * @param title the name of the game
     */

    public App(final String title) {
        super(title);
    }

    /**
     * This is the main() of the program. This method is called when the
     * program runs.
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
     * @param container the container holding the game
     * @throws SlickException indicates a failure to initialise the state
     * based game resources
     */
    public final void initStatesList(final GameContainer container)
            throws SlickException {
    	pausedScreen = new PausedScreen();
        game = new Game();
        
        victoryScreen = new VictoryScreen();
        defeatScreen = new DefeatScreen();
        addState(new StartScreen());
        addState(new MainMenu());
        addState(game);
        addState(pausedScreen);
        addState(victoryScreen);
        addState(defeatScreen);
        addState(new NameScreen());
        addState(new NamesScreen());
    }

    /**
     * Getter function for game object.
     * @return the game object associated to this class
     */
    public static final Game getGame() {
        return game;
    }
    
    public static final PausedScreen getPauseScreen(){
    	return pausedScreen;
    }
    
    public static final DefeatScreen getDefeatScreen(){
    	return defeatScreen;
    }

    /**
     * Setter function for game object.
     * @param g game object
     */
    public final void setGame(final Game g) {
        App.game = g;
    }
}
