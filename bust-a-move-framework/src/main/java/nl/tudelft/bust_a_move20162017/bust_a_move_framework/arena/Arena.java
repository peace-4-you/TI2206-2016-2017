/*
 * File: Arena.java
 *
 * Class: Arena
 *
 * Version: 0.0.1
 *
 * Date: September 12th 2016
 *
 */


package nl.tudelft.bust_a_move20162017.bust_a_move_framework.arena;

import java.util.LinkedList;

import nl.tudelft.bust_a_move20162017.bust_a_move_framework.bubble.BubbleStorage;
import nl.tudelft.bust_a_move20162017.bust_a_move_framework.bubble.Collision;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import nl.tudelft.bust_a_move20162017.bust_a_move_framework.App;
import nl.tudelft.bust_a_move20162017.bust_a_move_framework.bubble.Bubble;

/**
 * TODO: new description for functioning of this class
 * The arena class maintains a LinkedList of ArrayList data structure to store the bubble objects.
 *
 * @author Winer Bao
 */
public class Arena {

    private int xPos;
    private int yPos;
    private int height;
    private int width;
    private BubbleStorage bubbleStorage;


    /* Temporarily Bubble diameter variable */
    private final int DIAMETER = (int) Bubble.DIAMETER;
    private final double OFFSET = DIAMETER / 2;
    /* Max amount of bubbleStorage that fit in the vertical axis */


    private Collision collide;


    /**
     * Creates Arena for bubbleStorage storage and render variables
     *
     * @param xVal     X coordinate of the background
     * @param yVal     Y coordinate of the background
     * @param height_t height of the background
     * @param width_t  width of the background
     */
    public Arena(int xVal, int yVal, int height_t, int width_t) {
        xPos = xVal;
        yPos = yVal;
        height = height_t;
        width = width_t;
        bubbleStorage = new BubbleStorage(xPos, yPos, this);
        collide = new Collision(bubbleStorage, this);

        App.getGame().log.log("Arena initialised");
        //Level 1

    }

    public void checkCollision(Bubble bubble) {
        collide.checkCollision(bubble);
    }

    public Collision getCollision() {
        return collide;
    }

    /**
     * Sets the x position of the Arena background. This position is the most
     * top-left pixel of the background.
     *
     * @param xVal integer value to set xPos to
     */
    public void set_xPos(int xVal) {
        xPos = xVal;
    }

    /**
     * Returns xPos value
     *
     * @return xPos
     */
    public int get_xPos() {
        return xPos;
    }

    /**
     * Sets the y position of the Arena background. This position is the most
     * top-left pixel of the background.
     *
     * @param yVal integer value to set yPos to
     */
    public void set_yPos(int yVal) {
        yPos = yVal;
    }

    /**
     * Returns yPos value
     *
     * @return yPos
     */
    public int get_yPos() {
        return yPos;
    }

    /**
     * Returns the width
     *
     * @return width
     */
    public int getWidth() {
        return width;
    }

    /**
     * Returns the height of the Arena
     *
     * @return height
     */
    public int getHeight() {
        return height;
    }


    /**
     * Return the bubbleStorage.
     *
     * @return BubbleStorage instance
     */
    public BubbleStorage getBubbleStorage() {
        return bubbleStorage;
    }

    /**
     * Draw the boundary and the defeat line.
     *
     * @param g canvas to draw on.
     */
    public void draw(Graphics g) {
        bubbleStorage.draw(g);
        collide.draw(g);
        g.setColor(Color.white);
        g.drawRect(xPos, yPos, width, height);
        float yPosLine = (float) (bubbleStorage.getHeight() * ((DIAMETER * Math.tan(60)) + OFFSET + 2) + yPos + 5);
        g.drawLine(xPos, yPosLine, xPos + width, yPosLine);

    }
}