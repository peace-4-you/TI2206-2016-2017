package nl.tudelft.bust_a_move20162017.bust_a_move_framework.gamestate;

import org.newdawn.slick.Color;
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
 * Created by Jason Xie on 15/09/2016.
 */
public class VictoryScreen extends BasicGameState {

	private Button main;
	
	private Button restart;

	public int getID() {
		return 5;
	}

	public void init(GameContainer game, StateBasedGame stateBasedGame) throws SlickException {

		main = new Button("Main Menu", 170, 150, 30);
		main.centerButton(game);
		restart = new Button("Restart", 220, 100, 30);
		restart.centerButton(game);
	}

	public void render(GameContainer game, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
		String text = "You Won.";
		float textwidth = restart.getFont().getWidth(text);
		restart.getFont().drawString(320 - textwidth / 2, 30, text, Color.white);

		text = "Player: " + App.getGame().player.getName();
		textwidth = restart.font.getWidth(text);
		restart.font.drawString(320 - textwidth / 2, 90, text, Color.white);
		
		text = "Score: " + App.getGame().player.score.getScore();
		textwidth = restart.font.getWidth(text);
		restart.font.drawString(320 - textwidth / 2, 120, text, Color.white);
		main.draw(graphics);
		restart.draw(graphics);
	}

	public void update(GameContainer game, StateBasedGame stateBasedGame, int i) throws SlickException {
		Input input = game.getInput();
		if (input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
			if (main.isInBounds(input)) {
				stateBasedGame.getState(GameState.GAME_ACTIVE).init(game, stateBasedGame);
				stateBasedGame.enterState(GameState.MAIN_MENU, new FadeOutTransition(), new FadeInTransition());
			}
			if (restart.isInBounds(input)) {
				stateBasedGame.getState(GameState.GAME_ACTIVE).init(game, stateBasedGame);
				stateBasedGame.enterState(GameState.GAME_ACTIVE, new FadeOutTransition(), new FadeInTransition());
			}
		}
	}
}
