/*
 * File: Button.java
 * Class: Button
 *
 * Version: 0.0.3
 */
package nl.tudelft.bust_a_move20162017.bust_a_move_framework.gamestate;


import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.GameContainer;

import java.awt.Font;

/**
 * Created by Jason Xie on 12/09/2016.
 */
public class Button {

    /**
     * Values of the button.
     */
    private String bText;
    private float xPos;
    private float yPos;
    private float bWidth;
    private float bHeight;
    //TODO: Find a nice font.
    public TrueTypeFont font = new TrueTypeFont(new Font("Verdana", Font.BOLD, 20), true);


    /**
     * Constructor for a button without x parameter.
     *
     * @param text   text that will be displayed on the button
     * @param y      y coordinate of the button(upper bound)
     * @param width  width of the button
     * @param height height of the button
     */
    public Button(String text, float y, float width, float height) {
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
     *
     * @param text   text that will be displayed on the button
     * @param x      x coordinates of the button
     * @param y      y coordinate of the button(upper bound)
     * @param width  width of the button
     * @param height height of the button
     */
    public Button(String text, float x, float y, float width, float height) {
        this(text, y, width, height);
        assert x >= 0;
        xPos = x;

    }


    /**
     * Draw the button with the text centered.
     *
     * @param g Graphics object to draw on/in
     */
    public void draw(Graphics g) {
        g.setColor(Color.white);
        g.drawRect(xPos, yPos, bWidth, bHeight);
        float textwidth = font.getWidth(bText);
        float textheight = font.getHeight(bText);
        float centerx = xPos + bWidth / 2;
        float centery = yPos + bHeight / 2;
        font.drawString(centerx - textwidth / 2, centery - textheight / 2, bText, Color.white);
    }

    /**
     * Check whether the mouse in within the bounds of the button.
     *
     * @param mouse
     * @return
     */
    public final boolean isInBounds(Input mouse) {
        float xpos = mouse.getMouseX();
        float ypos = mouse.getMouseY();
        if (xpos >= xPos && xpos <= xPos + bWidth
                && ypos >= yPos && ypos <= yPos + bHeight) {
            return true;
        }
        return false;
    }

    /**
     * Center the button on the xPos-axis(so center it vertically) for the lazy people.
     *
     * @param game
     */
    public void centerButton(GameContainer game) {
        xPos = (game.getWidth() - bWidth) / 2;
    }
}
