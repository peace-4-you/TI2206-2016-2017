package nl.tudelft.bust_a_move20162017.bust_a_move_framework.gamestate;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

/**
 * Created by Jason Xie on 15/09/2016.
 */
public class StartScreen extends BasicGameState {
	
	
	
    public int getID() {
        return 1;
    }

    public void init(GameContainer game, StateBasedGame stateBasedGame) throws SlickException {
    }

    public void render(GameContainer game, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
        graphics.drawString("Click anywhere to start", game.getWidth() / 2, game.getHeight() / 2);
    }

    public void update(GameContainer game, StateBasedGame stateBasedGame, int i) throws SlickException {
        if (game.getInput().isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
            stateBasedGame.enterState(GameState.MAIN_MENU,new FadeOutTransition(), new FadeInTransition());
        }
    }
}
