package bustamove.screen.attributes;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

import bustamove.system.Log;
import bustamove.system.SoundHandler;

/**
 * This class creates and renders a button on the screen.
 * Users use this button to navigate through various menus.
 * Created by Jason Xie on 12/09/2016.
 */
public class Button {
    /**
     * Font of the text.
     */
    private static TrueTypeFont font;
    /**
     * Text inside the button.
     */
    private String bText;
    /**
     * X and Y coordinates of the button.
     */
    private float xPos;
    private float yPos;
    /**
     * X coordinates of the text. Compared to the button xPos.
     */
    private float xPosText;
    /**
     * Pixel width of the button.
     */
    private float bWidth;
    /**
     * Pixel height of the button.
     */
    private float bHeight;
    /**
     * List of actions that should be run when a button is clicked.
     */
    private ArrayList<Runnable> actions;

    /**
     * Constructor for a button without x parameter.
     *
     * @param text   text that will be displayed on the button
     * @param y      y coordinate of the button(upper bound)
     * @param width  width of the button
     * @param height height of the button
     */
    public Button(final String text, final float y, final float width,
                  final float height) {
        bText = text;
        xPos = 0;
        yPos = y;
        bWidth = width;
        bHeight = height;
        actions = new ArrayList<Runnable>();
        actions.add(new Runnable() {
            public void run() {
                SoundHandler.getInstance().playClickSound();
            }
        });
    }

    /**
     * Constructor with parameter for x coordinate.
     *
     * @param text   text that will be displayed on the button
     * @param x      x coordinates of the button
     * @param y      y coordinate of the button(upper bound)
     * @param width  width of the button
     * @param height height of the button
     */
    public Button(final String text, final float x, final float y,
                  final float width, final float height) {
        this(text, y, width, height);
        xPos = x;
    }

    /**
     * Draw the button with the text centered.
     *
     * @param g Graphics object to draw on/in
     */
    public final void draw(final Graphics g) {
        g.setColor(Color.white);
        g.drawRect(xPos, yPos, bWidth, bHeight);
        centerText();
        font.drawString(xPos + xPosText, yPos, bText, Color.white);
    }

    /**
     * Centers the text to the button.
     */
    public final void centerText() {
        xPosText = bWidth / 2.0f - font.getWidth(bText) / 2.0f;
    }

    /**
     * Check whether the mouse in within the bounds of the button.
     *
     * @param mouse mouse event input
     * @return true if the mouse coordinates are inside the bounds
     */
    public final boolean isInBounds(final Input mouse) {
        float xpos = mouse.getMouseX();
        float ypos = mouse.getMouseY();
        if (xpos >= xPos && xpos <= xPos + bWidth
                && ypos >= yPos && ypos <= yPos + bHeight) {
            SoundHandler.getInstance().playClickSound();
            return true;
        } else {
            return false;
        }
    }

    /**
     * Center the button on the xPos-axis(so center it vertically) for
     * the lazy people.
     *
     * @param game the game container
     */
    public final void centerButton(final GameContainer game) {
        xPos = (game.getWidth() - bWidth) / 2;
    }

    /**
     * Getter of the font used.
     *
     * @return font tupe
     */
    public final TrueTypeFont getFont() {
        return font;
    }

    /**
     * Setter of the font.
     *
     * @param f font type
     */
    public static final void setFont(final TrueTypeFont f) {
        font = f;
    }

    /**
     * Adds an action to be performed when the button is clicked.
     *
     * @param r The Runneable action to be performed.
     */
    public final void addAction(final Runnable r) {
        actions.add(r);
    }

    /**
     * Performs this buttons click action.
     */
    public final void click() {
        Log.getInstance().log(this, "Click!");
        for (Runnable r : actions) {
            r.run();
        }
    }

    /**
     * Adds a game state change action to this button.
     *
     * @param sbg   The state based game object.
     * @param state The state id to change to. (GameState.)
     */
    public final void addGameStateChangeAction(final StateBasedGame sbg,
                                               final int state) {
        addAction(new Runnable() {
            public void run() {
                sbg.enterState(state, new FadeOutTransition(),
                        new FadeInTransition());
            }
        });
    }

    /**
     * Setter to change the text inside the button.
     *
     * @param text message
     */
    public final void setText(final String text) {
        bText = text;
    }
}