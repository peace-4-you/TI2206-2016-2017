/*
 * File: MainMenu.java
 * Class: MainMenu
 *
 * Version: 0.0.3
 * Date: September 26th, 2016
 */

package nl.tudelft.bust_a_move20162017.bust_a_move_framework.gamestate;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.gui.TextField;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

import nl.tudelft.bust_a_move20162017.bust_a_move_framework.App;

/**
 * Generates a MainMenu as a instance of GameState.
 * 
 * @author Jason Xie, Maurice Willemsen
 */

public class MainMenu extends BasicGameState {

	private Button play;
	private Button quit;
	private Button setName;
	private TextField namefield;
	private Text nameText;

	/**
	 * @return integer of BasicGameState number
	 */

	public int getID() {
		return 2;
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

	public void init(GameContainer game, StateBasedGame stateBasedGame) throws SlickException {
		play = new Button("Play", GameConfig.THIRD_LINE, GameConfig.WIDTH1, GameConfig.HEIGHT);
		play.centerButton(game);
		quit = new Button("Quit", GameConfig.FOURTH_LINE, GameConfig.WIDTH1, GameConfig.HEIGHT);
		quit.centerButton(game);
		setName = new Button("Set Name", 275, GameConfig.WIDTH1, GameConfig.HEIGHT);
		setName.centerButton(game);
		nameText = new Text("Player: " + App.game.player.getName(), GameConfig.FIRST_LINE);
		nameText.centerText(game);
		namefield = new TextField(game, game.getDefaultFont(), 220, 235, 200, 30);
		namefield.setText("Player1");
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

	public void render(GameContainer game, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
		nameText.draw(graphics);
		play.draw(graphics);
		quit.draw(graphics);
		setName.draw(graphics);
		namefield.render(game, graphics);
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

	public void update(GameContainer game, StateBasedGame stateBasedGame, int i) throws SlickException {
		nameText.setText("Player: " + App.getGame().player.getName());
		nameText.centerText(game);
		Input input = game.getInput();
		if (input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
			if (play.isInBounds(input)) {
				stateBasedGame.enterState(GameState.GAME_ACTIVE, new FadeOutTransition(), new FadeInTransition());
			}
			if (setName.isInBounds(input)) {
				App.game.player.setName(this.namefield.getText());
			}
			if (quit.isInBounds(input)) {
				game.exit();
			}
		}
	}
}
