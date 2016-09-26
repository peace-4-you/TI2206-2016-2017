/*
 * File: App.java
 * Class: App
 *
 * Version: 0.0.4
 * Date: September 26th, 2016
 */
package nl.tudelft.bust_a_move20162017.bust_a_move_framework;

import nl.tudelft.bust_a_move20162017.bust_a_move_framework.game.Game;
import nl.tudelft.bust_a_move20162017.bust_a_move_framework.gamestate.*;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

/**
 * Main overhead Class of Bust-A-Move
 * @author Jason Xie, Maurice Willemsen
 */

public class App extends StateBasedGame {
    /**
     * Values for the size and fps of the game window.
     */
	
    private static final int GAME_WIDTH = 640;
    private static final int GAME_HEIGHT = 580;
    private static final int MAX_FPS = 120;

    public static Game game;
    public static MainMenu mainmenu;

    /**
     * Constructor for the launcher of the game.
     *
     * @param title
     */
    
    public App(String title) {
        super(title);
    }

    /**
     * Main Method of the App Class, initialises StateBasedGame
     * @param args
     * @throws SlickException
     */

    public static void main(String[] args) throws SlickException {
        AppGameContainer app = new AppGameContainer(new App("Bust-A-Move!!"));
        app.setDisplayMode(GAME_WIDTH, GAME_HEIGHT, false);
        app.setAlwaysRender(true);
        app.setTargetFrameRate(MAX_FPS);
        app.start();
    }

    /**
     * Initialize all the game states that are used in the game.
     *
     * @param container the gamecontainer
     * @throws SlickException
     */
    
    public void initStatesList(GameContainer container) throws SlickException {
    	mainmenu = new MainMenu();
        game = new Game();
        game.initialisePlayer();
        
        addState(new StartScreen());
        addState(mainmenu);
        addState(game);
        addState(new PausedScreen());
        addState(new VictoryScreen());
        addState(new DefeatScreen());
    }
}
