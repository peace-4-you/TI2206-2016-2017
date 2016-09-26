/*
 * File: StartScreen.java
 * Class: StartScreen
 *
 * Version: 0.0.3
 * Date: September 26th, 2016
 */

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
 * Generates a StartScreen as a instance of GameState
 * @author Jason Xie
 */

public class StartScreen extends BasicGameState {
	
    /**
     * @return integer of BasicGameState number
     */
	
    public int getID() {
        return 1;
    }
    
    /**
     * Called when BasicGameState initializes
     */

    public void init(GameContainer game, StateBasedGame stateBasedGame) throws SlickException {
    }
    
    /**
     * Renders the BasicGameState
     */

    public void render(GameContainer game, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
        graphics.drawString("Click anywhere to start", game.getWidth() / 2, game.getHeight() / 2);
    }
    
    /**
     * Updates the BasicGameState
     */

    public void update(GameContainer game, StateBasedGame stateBasedGame, int i) throws SlickException {
        if (game.getInput().isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
            stateBasedGame.enterState(GameState.MAIN_MENU,new FadeOutTransition(), new FadeInTransition());
        }
    }
}
