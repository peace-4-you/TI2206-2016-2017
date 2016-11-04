/*
 * File: Text.java
 * Class: Text
 * Version: 0.0.3
 */
package bustamove.screen.attributes;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.GameContainer;

/**
 * This class create and draws a String on screen.
 *
 * @author Maurice Willemsen
 */
public class Text {
    /**
     * The font the text should be displayed in.
     */
    private static TrueTypeFont font;
    /**
     * String Value of the text.
     */
    private String text;
    /**
     * X coordinate of the left point of the text.
     */
    private float xPos;
    /**
     * Y coordinate of the top point of the text.
     */
    private float yPos;
    /**
     * Width of the text to be displayed.
     */
    private float tWidth;
    /**
     * The color of this text.
     */
    private Color color = Color.white;

    /**
     * Constructor for a text without x parameter.
     *
     * @param s text that will be displayed
     * @param y y coordinate of the text
     */
    public Text(final String s, final float y) {
        text = s;
        xPos = 0;
        yPos = y;
    }

    /**
     * Constructor with parameter for x coordinate.
     *
     * @param s text that will be displayed
     * @param x x coordinate of the text
     * @param y y coordinate of the text
     */
    public Text(final String s, final float x, final float y) {
        this(s, y);
        xPos = x;
    }

    /**
     * Constructor with parameter for x coordinate.
     *
     * @param s text that will be displayed
     * @param x x coordinate of the text
     * @param y y coordinate of the text
     * @param c the color
     */
    public Text(final String s, final float x, final float y, Color c) {
        this(s, y);
        xPos = x;
        color = c;
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
     * Getter of the font used.
     *
     * @return font type
     */
    public final TrueTypeFont getFont() {
        return font;
    }

    /**
     * Draw the text centered.
     *
     * @param g Graphics object to draw on/in
     */
    public final void draw(final Graphics g) {
        g.setColor(Color.white);
        font.drawString(xPos, yPos, text, color);
    }

    /**
     * Center the text on the xPos-axis(so center it vertically) for the lazy
     * people.
     *
     * @param game to get the width of
     */
    public final void centerText(final GameContainer game) {
        tWidth = font.getWidth(this.text);
        xPos = (game.getWidth() - tWidth) / 2;
    }

    /**
     * Set the text.
     *
     * @param s the string to set
     */
    public final void setText(final String s) {
        this.text = s;
    }
}
