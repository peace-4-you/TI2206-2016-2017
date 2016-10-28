/*
 * File: StartScreen.java
 * Class: StartScreen
 *
 * Version: 0.0.3
 * Date: September 26th, 2016
 */

package bustamove.screen;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import bustamove.screen.attributes.Button;
import bustamove.screen.config.GameConfig;
import bustamove.screen.config.GameState;

/**
 * Generates a StartScreen as a instance of GameState.
 *
 * @author Jason Xie
 */
public class StartScreen extends Screen {
    /**
     * Getter method: for the GameState ID.
     * @return integer of BasicGameState number.
     */
    public final int getID() {
        return GameState.START_SCREEN;
    }

    /**
     * Called when BasicGameState initializes.
     *
     * @param game           the game container
     * @param stateBasedGame the state based game
     * @throws SlickException any type of slick exception
     */
    public final void init(final GameContainer game,
                           final StateBasedGame stateBasedGame)
            throws SlickException {
        initBasicScreen(game);
        Button start = new Button("Click to start", GameConfig.SIXT_LINE,
                GameConfig.WIDTH3, GameConfig.HEIGHT);
        start.addGameStateChangeAction(stateBasedGame,
                GameState.MAIN_MENU);
        getButtons().add(start);
        super.center();
    }
}
