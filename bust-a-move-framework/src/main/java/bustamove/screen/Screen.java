/*
 * File: Screen.java
 *
 * Class: Screen
 *
 * Version: 0.0.3
 *
 * Date: October 24th, 2016
 */

package bustamove.screen;

import java.awt.Font;
import java.util.ArrayList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.gui.TextField;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import bustamove.screen.attributes.Button;
import bustamove.screen.attributes.Text;
import bustamove.screen.config.GameConfig;

/**
 * Screen Super Class.
 * @author Maurice Willemsen
 *
 */
public abstract class Screen extends BasicGameState {
    /**
     * Lists of buttons,texts and textfields.
     */
    private ArrayList<Button> buttons;
    private ArrayList<Text> texts;
    private ArrayList<TextField> textfields;
    /**
     * GameContainer.
     */
    private GameContainer gamecontainer;
    /**
     * Font for all the texts.
     */
    private static TrueTypeFont font =
            new TrueTypeFont(new Font("Verdana", Font.BOLD,
                    GameConfig.TEXT_SIZE), true);

    /**
     * Getter method: for the GameState ID.
     * @return integer of BasicGameState number.
     */
    public abstract int getID();

    /**
     * Called from extending class. Inits the lists and sets the gamecontainer
     * @param game the game container
     */
    public final void initBasicScreen(final GameContainer game) {
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
            t.render(game, graphics);
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
                                        final StateBasedGame stateBasedGame,
                                        final int i)
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

    /**
     * Getter for the buttons lists.
     * @return Buttons Arraylist.
     */
    public final ArrayList<Button> getButtons() {
        return buttons;
    }

    /**
     * Getter for the text lists.
     * @return Text Arraylist.
     */
    public final ArrayList<Text> getTexts() {
        return texts;
    }

    /**
     * Getter for the textfield lists.
     * @return TextField Arraylist.
     */
    public final ArrayList<TextField> getTextFields() {
        return textfields;
    }

    /**
     * Static setter for the fonts.
     */
    public static void setFonts() {
        Button.setFont(font);
        Text.setFont(font);
    }
}
