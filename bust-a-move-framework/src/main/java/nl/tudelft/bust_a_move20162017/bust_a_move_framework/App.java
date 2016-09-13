package nl.tudelft.bust_a_move20162017.bust_a_move_framework;

import nl.tudelft.bust_a_move20162017.bust_a_move_framework.game.Game;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class App extends StateBasedGame {

	public App(String title) {
		super(title);		
	}

	public static void main(String[] args) throws SlickException {
		AppGameContainer app = new AppGameContainer(new App("Setup Test"));
		app.setDisplayMode(640, 480, false);
		app.setAlwaysRender(true);
		app.setTargetFrameRate(60);
		app.start();
	}

	public void initStatesList(GameContainer arg0) throws SlickException {
		addState(new Game());		
	}


}
