/*
 * File: Screen.java
 *
 * Class: Screen
 *
 * Version: 0.0.3
 *
 * Date: October 24th, 2016
 */

package bustamove.gamestate;

import java.util.ArrayList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.gui.TextField;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

/**
 * Screen Super Class.
 * @author Maurice Willemsen
 *
 */
public abstract class Screen extends BasicGameState{
    /**
     * All the buttons.
     */
    protected ArrayList<Button> buttons;
    /**
     * All the buttons.
     */
    protected ArrayList<Text> texts;
    /**
     * All the buttons.
     */
    protected ArrayList<TextField> textfields;
    /**
     * GameContainer
     */
    protected GameContainer gamecontainer;
    
    /**
     * Getter method: for the GameState ID.
     * @return integer of BasicGameState number.
     */
    public abstract int getID();
    
    /**
     * Called when BasicGameState initializes.
     * @param game the game container
     * @param stateBasedGame the state based game
     * @throws SlickException any type of slick exception
     */
    public void init(final GameContainer game,
            final StateBasedGame stateBasedGame) throws SlickException {
        buttons = new ArrayList<Button>();
        texts = new ArrayList<Text>(); 
        textfields = new ArrayList<TextField>(); 
        gamecontainer = game;
    }

    /**
     * Renders the BasicGameState.
     * @param game the game container
     * @param stateBasedGame the state based game
     * @param graphics Graphics object
     * @throws SlickException any type of slick exception
     */
    public final void render(final GameContainer game,
            final StateBasedGame stateBasedGame, final Graphics graphics)
            throws SlickException {
        for (Button b : buttons) {
            b.draw(graphics);
        }
        for (Text t : texts) {
            t.draw(graphics);
        }
        for (TextField t : textfields) {
            t.render(game,graphics);
        }
    }
    
    /**
     * Centering all buttons and texts in the screen.
     */
    public final void center() {
        for (Button b : buttons) {
            b.centerButton(gamecontainer);
        }
        for (Text t : texts) {
            t.centerText(gamecontainer);
        }
    }
    
    /**
     * Updates the BasicGameState.
     * @param game the game container
     * @param stateBasedGame the state based game
     * @param i delta of time exceeded
     * @throws SlickException any type of slick exception
     */
    public final void update(final GameContainer game,
            final StateBasedGame stateBasedGame, final int i)
            throws SlickException {
        gamecontainer = game;
        Input input = game.getInput();
        if (input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
            for (Button b : buttons) {
                if (b.isInBounds(input)) {
                    b.click();
                }
            }
        }
    }
}
