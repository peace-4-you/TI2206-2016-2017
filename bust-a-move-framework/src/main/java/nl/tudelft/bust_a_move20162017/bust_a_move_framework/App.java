package nl.tudelft.bust_a_move20162017.bust_a_move_framework;

import nl.tudelft.bust_a_move20162017.bust_a_move_framework.game.Game;
import nl.tudelft.bust_a_move20162017.bust_a_move_framework.gamestate.*;
import nl.tudelft.bust_a_move20162017.bust_a_move_framework.log.Log;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class App extends StateBasedGame {
	
    private static final int START_SCREEN = 1;
    private static final int MAIN_MENU = 2;
    private static final int GAME_ACTIVE = 3;
    private static final int PAUSE_SCREEN = 4;
    private static final int WIN_SCREEN = 5;
    private static final int DEFEAT_SCREEN = 6;
    
    public static Game game;
    
	public App(String title) {
		super(title);		
	}

	public static void main(String[] args) throws SlickException {
		AppGameContainer app = new AppGameContainer(new App("Bust-A-Move!!"));
		app.setDisplayMode(640, 580, false);
		app.setAlwaysRender(true);
		app.setTargetFrameRate(120);
		app.start();		
	}

	public void initStatesList(GameContainer arg0) throws SlickException {	
		game = new Game();
		game.initialisePlayer();
        addState(new StartScreen());
        addState(new MainMenu());
        addState(game);
        addState(new PausedScreen());
        addState(new VictoryScreen());
        addState(new DefeatScreen());	
	}
}
