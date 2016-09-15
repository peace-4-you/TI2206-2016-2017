package nl.tudelft.bust_a_move20162017.bust_a_move_framework.gamestate;


import org.newdawn.slick.*;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import java.awt.Font;

/**
 * Created by Jason Xie on 12/09/2016.
 */
public class Button {

    private String text;
    private float x;
    private float y;
    private float width;
    private float height;
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

        this.text = text;
        this.x = 0;
        this.y = y;
        this.width = width;
        this.height = height;
    }


    /**
     * Constructor with parameter for x coordinate.
     *
     * @param text
     * @param x
     * @param y
     * @param width
     * @param height
     */
    public Button(String text, float x, float y, float width, float height) {
        this(text, y, width, height);
        assert x >= 0;
        this.x = x;
        
    }


    /**
     * Draw the button with the text centered.
     *
     * @param g Graphics object to draw on/in
     */
    public void draw(Graphics g) {
    	g.setColor(Color.white);
        g.drawRect(x, y, width, height);
        float textwidth = font.getWidth(text);
        float textheight = font.getHeight(text);
        float centerx = x + width / 2;
        float centery = y + height / 2;
        font.drawString(centerx - textwidth / 2, centery - textheight / 2, text, Color.white);
    }

    /**
     * Check whether the mouse in within the bounds of the button.
     *
     * @param mouse
     * @return
     */
    public boolean isInBounds(Input mouse) {
        float xpos = mouse.getMouseX();
        float ypos = mouse.getMouseY();
        if (xpos >= x && xpos <= x + width &&
                ypos >= y && ypos <= y + height) {
            return true;
        }
        return false;
    }

    /**
     * Center the button on the x-axis(so center it vertically) for the lazy people.
     *
     * @param game
     */
    public void centerButton(GameContainer game) {
        x = (game.getWidth() - width) / 2;
    }
}
