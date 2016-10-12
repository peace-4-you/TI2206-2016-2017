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

package bustamove.game;

import bustamove.gamestate.GameConfig;
import bustamove.player.Score;
import bustamove.system.Log;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import bustamove.bubble.Bubble;
import bustamove.bubble.BubbleStorage;
import bustamove.bubble.Collision;

/**
 * a LinkedList of ArrayList data structure to store the bubble objects.
 *
 * @author Winer Bao
 */
public final class Arena {

    /**
     * X, Y positions of the arena.
     */
    private int xPos;
    private int yPos;
    /**
     * Dimensions of the arena.
     */
    private final int height;
    private final int width;
    /**
     * bubbleStorage class to store all bubbles.
     */
    private BubbleStorage bubbleStorage;
    /**
     * Collision instance.
     */
    private Collision collide;

    /**
     * Creates Arena for bubbleStorage storage and render variables.
     *
     * @param xVal     X coordinate of the background
     * @param yVal     Y coordinate of the background
     * @param heightBg height of the background
     * @param widthBg  width of the background
     * @param score    container that holds the score of a GameData object
     */
    public Arena(final int xVal, final int yVal,
                 final int heightBg, final int widthBg, final Score score) {
        xPos = xVal;
        yPos = yVal;
        height = heightBg;
        width = widthBg;
        bubbleStorage = new BubbleStorage(xPos, yPos, this);
        collide = new Collision(bubbleStorage, this, score);
        Log.log(this, "Arena initialised");
    }

    /**
     * checks in collision on collison.
     *
     * @param bubble to check on collision
     */

    public void checkCollision(final Bubble bubble) {
        collide.checkCollision(bubble);
    }

    /**
     * getter for the collide.
     *
     * @return collide instance in object.
     */

    public Collision getCollision() {
        return collide;
    }

    /**
     * Sets the x position of the Arena background. This position is the most
     * top-left pixel of the background.
     *
     * @param xVal integer value to set xPos to
     */
    public void setxPos(final int xVal) {
        xPos = xVal;
    }

    /**
     * Returns xPos value.
     *
     * @return xPos
     */
    public int getxPos() {
        return xPos;
    }

    /**
     * Sets the y position of the Arena background. This position is the most
     * top-left pixel of the background.
     *
     * @param yVal integer value to set yPos to
     */
    public void setyPos(final int yVal) {
        yPos = yVal;
    }

    /**
     * Returns yPos value.
     *
     * @return yPos
     */
    public int getyPos() {
        return yPos;
    }

    /**
     * Returns the width.
     *
     * @return width
     */
    public int getWidth() {
        return width;
    }

    /**
     * Returns the height of the Arena.
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
    public void draw(final Graphics g) {
        bubbleStorage.draw(g);
        collide.draw(g);
        g.setColor(Color.white);
        g.drawRect(xPos, yPos, width, height);
        float yPosLine = (float) (bubbleStorage.getHeight()
                * GameConfig.ROW_OFFSET + yPos);
        g.drawLine(xPos, yPosLine, xPos + width, yPosLine);
    }
}
