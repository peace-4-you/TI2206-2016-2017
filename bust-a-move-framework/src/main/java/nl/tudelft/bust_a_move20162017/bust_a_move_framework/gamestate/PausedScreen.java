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
public class PausedScreen extends BasicGameState {
    
    private Button resume;
	
	public int getID() {
        return 4;
    }

    public void init(GameContainer game, StateBasedGame stateBasedGame) throws SlickException {
        resume = new Button("resume", 140, 100, 30);
        resume.centerButton(game);
    }

    public void render(GameContainer game, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
    	String text = "Plafgyer: ";
    	text += App.game.player.getName();
    	System.out.println(App.game.player.getName());
    	float textwidth = resume.font.getWidth(text);    	
    	resume.font.drawString(320 - textwidth / 2, 50,text, Color.white);
    	text = "Score: " + App.game.player.score.getScore();
    	textwidth = resume.font.getWidth(text);    	
    	resume.font.drawString(320 - textwidth / 2, 80,text, Color.white);
        resume.draw(graphics);
    }

    public void update(GameContainer game, StateBasedGame stateBasedGame, int i) throws SlickException {
        Input input = game.getInput();
        if (input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
            if (resume.isInBounds(input)) {
                stateBasedGame.enterState(GameState.GAME_ACTIVE,new FadeOutTransition(), new FadeInTransition());
            }
        }

    }
}
