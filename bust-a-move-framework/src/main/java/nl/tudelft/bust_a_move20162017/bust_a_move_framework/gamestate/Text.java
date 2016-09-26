/*
 * File: Text.java
 * Class: Text
 *
 * Version: 0.0.3
 */
package nl.tudelft.bust_a_move20162017.bust_a_move_framework.gamestate;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.GameContainer;

import java.awt.Font;

/**
 * @author Maurice Willemsen
 */
public class Text {

    /**
     * Values of the button.
     */
    private String text;
    private float xPos;
    private float yPos;
    private float tWidth;
    public TrueTypeFont font = new TrueTypeFont(new Font("Verdana", Font.BOLD, 20), true);

    /**
     * Constructor for a text without x parameter.
     *
     * @param text   text that will be displayed
     * @param y      y coordinate of the text
     */
    
    public Text(String text, float y) {
        assert text != null;
        assert y >= 0;

        this.text = text;
        xPos = 0;
        yPos = y;
        tWidth = font.getWidth(this.text);
    }

    /**
     * Constructor with parameter for x coordinate.
     *
     * @param text   text that will be displayed
     * @param x      x coordinate of the text
     * @param y      y coordinate of the text
     */
    
    public Text(String text, float x, float y) {
        this(text, y);
        assert x >= 0;
        xPos = x;
    }

    /**
     * Draw the text centered.
     *
     * @param g Graphics object to draw on/in
     */
    
    public void draw(Graphics g) {
        g.setColor(Color.white);
        font.drawString(xPos, yPos, text, Color.white);
    }

    /**
     * Center the text on the xPos-axis(so center it vertically) for the lazy people.
     *
     * @param game
     */
    
    public void centerText(GameContainer game) {
        xPos = (game.getWidth() - tWidth) / 2;
    }
    
    /**
     * Set the text.
     * @param text
     */
    
    public void setText(String text) {
        this.text = text;
    }
}
