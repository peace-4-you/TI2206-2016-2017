package nl.tudelft.bust_a_move20162017.bust_a_move_framework.gamestate;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.GameContainer;

import java.awt.Font;

/**
 * This class creates and renders a button on the screen.
 * Users use this button to navigate through various menus.
 * Created by Jason Xie on 12/09/2016.
 */
public class Button {

    /**
     * Text inside the button.
     */
    private String bText;
    /**
     * X coordinate (top left) of the button.
     */
    private float xPos;
    /**
     * Y coordinate (top left) of the button.
     */
    private float yPos;
    /**
     * Pixel width of the button.
     */
    private float bWidth;
    /**
     * Pixel height of the button.
     */
    private float bHeight;
    /**
     * Font size constant.
     */
    private final int fontSize = 20;
    /**
     * Font of the text.
     */
    private TrueTypeFont font = new TrueTypeFont(
            new Font("Verdana", Font.BOLD, fontSize), true);



    /**
     * Constructor for a button without x parameter.
     * @param text   text that will be displayed on the button
     * @param y      y coordinate of the button(upper bound)
     * @param width  width of the button
     * @param height height of the button
     */
    public Button(final String text, final float y, final float width,
                  final float height) {
        assert text != null;
        assert y >= 0;
        assert width > 0;
        assert height > 0;

        bText = text;
        xPos = 0;
        yPos = y;
        bWidth = width;
        bHeight = height;
    }


    /**
     * Constructor with parameter for x coordinate.
     * @param text   text that will be displayed on the button
     * @param x      x coordinates of the button
     * @param y      y coordinate of the button(upper bound)
     * @param width  width of the button
     * @param height height of the button
     */
    public Button(final String text, final float x, final float y,
                  final float width, final float height) {
        this(text, y, width, height);
        assert x >= 0;
        xPos = x;

    }


    /**
     * Draw the button with the text centered.
     * @param g Graphics object to draw on/in
     */
    public final void draw(final Graphics g) {
        g.setColor(Color.white);
        g.drawRect(xPos, yPos, bWidth, bHeight);
        float textwidth = font.getWidth(bText);
        float textheight = font.getHeight(bText);
        float centerx = xPos + bWidth / 2;
        float centery = yPos + bHeight / 2;
        font.drawString(centerx - textwidth / 2, centery - textheight / 2,
                bText, Color.white);
    }

    /**
     * Check whether the mouse in within the bounds of the button.
     * @param mouse mouse event input
     * @return true if the mouse coordinates are inside the bounds
     */
    public final boolean isInBounds(final Input mouse) {
        float xpos = mouse.getMouseX();
        float ypos = mouse.getMouseY();
        if (xpos >= xPos && xpos <= xPos + bWidth
                && ypos >= yPos && ypos <= yPos + bHeight) {
            return true;
        }
        return false;
    }

    /**
     * Center the button on the xPos-axis(so center it vertically) for
     * the lazy people.
     * @param game the game container
     */
    public final void centerButton(final GameContainer game) {
        xPos = (game.getWidth() - bWidth) / 2;
    }

    /**
     * Setter of the font.
     * @param f font type
     */
    public final void setFont(final TrueTypeFont f) {
        this.font = f;
    }

    /**
     * Getter of the font used.
     * @return font tupe
     */
    public final TrueTypeFont getFont() {
        return font;
    }

}
