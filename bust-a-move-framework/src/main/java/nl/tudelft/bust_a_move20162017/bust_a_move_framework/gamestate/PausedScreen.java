/*
 * File: PausedScreen.java
 * Class: PausedScreen
 *
 * Version: 0.0.3
 * Date: September 26th, 2016
 */
package nl.tudelft.bust_a_move20162017.bust_a_move_framework.gamestate;

import java.util.Observable;
import java.util.Observer;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

import nl.tudelft.bust_a_move20162017.bust_a_move_framework.App;

/**
 * Generates a PausedScreen as a instance of GameState
 * 
 * @author Jason Xie, Maurice Willemsen
 */

public class PausedScreen extends BasicGameState implements Observer {

	private Button resume;
	private Button quit;
	private Text nameText;
	private Text scoreText;
	private Text pauseText;
	private int score;

	/**
	 * @return integer of BasicGameState number
	 */

	public int getID() {
		return 4;
	}

	/**
	 * Called when BasicGameState initializes
	 */

	public void init(GameContainer game, StateBasedGame stateBasedGame) throws SlickException {
		// TODO: Come up with better name and text for buttons.
		resume = new Button("Resume", 170, 120, 30);
		resume.centerButton(game);
		quit = new Button("Quit", 205, 120, 30);
		quit.centerButton(game);
		pauseText = new Text("Game Paused", 30);
		pauseText.centerText(game);
		nameText = new Text("Player: " + App.getGame().player.getName(), 90);
		nameText.centerText(game);
		scoreText = new Text("Score: " + this.score, 120);
		scoreText.centerText(game);
		App.getGame().player.score.addAsObserver(this);
	}

	/**
	 * Renders the BasicGameState
	 */

	public void render(GameContainer game, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
		pauseText.draw(graphics);
		nameText.draw(graphics);
		scoreText.draw(graphics);
		resume.draw(graphics);
		quit.draw(graphics);
	}

	/**
	 * Updates the BasicGameState
	 */

	public void update(GameContainer game, StateBasedGame stateBasedGame, int i) throws SlickException {
		nameText.setText("Player: " + App.getGame().player.getName());
		nameText.centerText(game);
		scoreText.setText("Score: " + this.score);
		scoreText.centerText(game);
		Input input = game.getInput();
		if (input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
			if (resume.isInBounds(input)) {
				stateBasedGame.enterState(GameState.GAME_ACTIVE, new FadeOutTransition(), new FadeInTransition());
			}
			if (quit.isInBounds(input)) {
				stateBasedGame.enterState(GameState.MAIN_MENU, new FadeOutTransition(), new FadeInTransition());
			}
		}

	}

	/**
	 * Updates the Observer
	 */

	public void update(Observable o, Object arg) {
		this.score = (Integer) arg;
	}
}
