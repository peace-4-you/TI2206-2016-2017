/*
 * File: StartScreen.java
 * Class: StartScreen
 *
 * Version: 0.0.3
 * Date: September 26th, 2016
 */

package bustamove.gamestate;

import org.newdawn.slick.Input;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

import java.awt.Font;

/**
 * Generates a StartScreen as a instance of GameState.
 *
 * @author Jason Xie
 */
public class StartScreen extends BasicGameState {

    /**
     * Text showed on start.
     */
    private Text starttext;
    private TrueTypeFont font =
            new TrueTypeFont(new Font("Verdana", Font.BOLD,
                    GameConfig.TEXT_SIZE), true);

    /**
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
        Button.setFont(font);
        Text.setFont(font);
        starttext = new Text("Click anywhere to start", GameConfig.SIXT_LINE);
        starttext.centerText(game);
    }

    /**
     * Renders the BasicGameState.
     *
     * @param game           the game container
     * @param stateBasedGame the state based game
     * @param graphics       Graphics object
     * @throws SlickException any type of slick exception
     */
    public final void render(final GameContainer game,
                             final StateBasedGame stateBasedGame,
                             final Graphics graphics)
            throws SlickException {
        starttext.draw(graphics);
    }

    /**
     * Updates the BasicGameState.
     *
     * @param game           the game container
     * @param stateBasedGame the state based game
     * @param i              delta of time exceeded
     * @throws SlickException any type of slick exception
     */
    public final void update(final GameContainer game,
                             final StateBasedGame stateBasedGame, final int i) {
        if (game.getInput().isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
            stateBasedGame.enterState(GameState.MAIN_MENU,
                    new FadeOutTransition(), new FadeInTransition());
        }
    }
}
