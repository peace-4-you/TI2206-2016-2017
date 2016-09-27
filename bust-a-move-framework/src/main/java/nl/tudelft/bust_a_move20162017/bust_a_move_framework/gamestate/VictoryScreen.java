/*
 * File: VictoryScreen.java
 * Class: VictoryScreen
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
 * Generates a VictoryScreen as a instance of GameState.
 * 
 * @author Jason Xie, Maurice Willemsen
 */

public class VictoryScreen extends BasicGameState implements Observer {

	/**
	 * Main Button.
	 */
	private Button main;
	/**
	 * Restart Button.
	 */
	private Button restart;
	/**
	 * Name Text.
	 */
	private Text nameText;
	/**
	 * Score Text.
	 */
	private Text scoreText;
	/**
	 * Won Text.
	 */
	private Text wonText;
	/**
	 * Score Integer. Should be updated by observer
	 */
	private int score;

	/**
	 * @return integer of BasicGameState number.
	 */

	public final int getID() {
		return GameState.WIN_SCREEN;
	}

	/**
	 * Called when BasicGameState initializes.
	 * 
	 * @param game
	 *            the game container
	 * @param stateBasedGame
	 *            the state based game
	 * @throws SlickException
	 *             any type of slick exception
	 */

	public final void init(final GameContainer game,
		final StateBasedGame stateBasedGame) throws SlickException {
		wonText = new Text("You Won", GameConfig.FIRST_LINE);
		wonText.centerText(game);
		nameText = new Text("Player: " + App.getGame().player.getName(),
			GameConfig.SECOND_LINE);
		nameText.centerText(game);
		scoreText = new Text("Score: " + this.score, GameConfig.THIRD_LINE);
		scoreText.centerText(game);
		main = new Button("Main Menu", GameConfig.FOURTH_LINE,
			GameConfig.WIDTH2, GameConfig.HEIGHT);
		main.centerButton(game);
		restart = new Button("Restart", GameConfig.FIFTH_LINE,
			GameConfig.WIDTH1, GameConfig.HEIGHT);
		restart.centerButton(game);
		App.getGame().player.score.addAsObserver(this);
	}

	/**
	 * Renders the BasicGameState.
	 * 
	 * @param game
	 *            the game container
	 * @param stateBasedGame
	 *            the state based game
	 * @param graphics
	 *            Graphics object
	 * @throws SlickException
	 *             any type of slick exception
	 */

	public final void render(final GameContainer game,
		final StateBasedGame stateBasedGame, final Graphics graphics)
		throws SlickException {
		wonText.draw(graphics);
		nameText.draw(graphics);
		scoreText.draw(graphics);
		main.draw(graphics);
		restart.draw(graphics);
	}

	/**
	 * Updates the BasicGameState.
	 * 
	 * @param game
	 *            the game container
	 * @param stateBasedGame
	 *            the state based game
	 * @param i
	 *            delta of time exceeded
	 * @throws SlickException
	 *             any type of slick exception
	 */

	public final void update(final GameContainer game,
		final StateBasedGame stateBasedGame, final int i)
		throws SlickException {
		nameText.setText("Player: " + App.getGame().player.getName());
		nameText.centerText(game);
		scoreText.setText("Score: " + this.score);
		scoreText.centerText(game);
		Input input = game.getInput();
		if (input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
			if (main.isInBounds(input)) {
				stateBasedGame.getState(GameState.GAME_ACTIVE).init(game,
					stateBasedGame);
				stateBasedGame.enterState(GameState.MAIN_MENU,
					new FadeOutTransition(), new FadeInTransition());
			}
			if (restart.isInBounds(input)) {
				stateBasedGame.getState(GameState.GAME_ACTIVE).init(game,
					stateBasedGame);
				stateBasedGame.enterState(GameState.GAME_ACTIVE,
					new FadeOutTransition(), new FadeInTransition());
			}
		}
	}

	/**
	 * Updates the Observer
	 * 
	 * @param o
	 *            the observable item
	 * @param arg
	 *            the observable argument
	 */

	public final void update(final Observable o, final Object arg) {
		this.score = (Integer) arg;
	}
}
