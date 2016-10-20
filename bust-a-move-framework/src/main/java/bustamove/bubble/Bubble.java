/*
 * File: Bubble.java
 *
 * Class: Bubble
 *
 * Version: 0.0.1
 *
 * Date: October 18th, 2016
 *
 */

package bustamove.bubble;

import bustamove.game.GameData;
import bustamove.gamestate.GameConfig;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Circle;

/**
 * The Bubble class represents a single bubble entity.
 *
 * @author Calvin Nhieu
 */
public abstract class Bubble {
    /**
     * Diameter of a bubble.
     */
    public static final double DIAMETER = 35;
    /**
     * Dropping speed of a bubble.
     */
    public static final double DROP_SPEED = GameConfig.DEFAULT_BUBBLE_SPEED;
    /**
     * Enum of all the values a State variable can take.
     */
    public enum State {
        NEW, LANDED, FIRING, POPPED, DROPPING
    }
    /**
     * Enum of all the values a Colorchoice variable can take.
     */
    public enum ColorChoice {
        RED, BLUE, YELLOW, GREEN
    }

    /**
     * Empty constructor per abstract class specification.
     */
    public Bubble() {
    }
    /**
     * Sets the gamehead, this is a instance of GameData.
     * @param game the gamehead to refer to
     */
    public abstract void setGameHead(final GameData game);
    /**
     * Returns the gamehead.
     * @return  GameData gamehead
     */
    public abstract GameData getGameHead();
    /**
     * Draws the Bubble.
     *
     * @param g Java Graphics instance
     */
    public abstract void draw(final Graphics g);
    /**
     * Updates the Bubble's position per game tick.
     */
    public abstract void move();
    /**
     * Stops the Bubble, adjusts bubble's position.
     *
     * @param xPos at adjusted x position
     * @param yPos at adjusted y position
     */
    public abstract void land(final float xPos, final float yPos);
    /**
     * Wall collision detection, inverts ball's xSpeed.
     */
    public abstract void hitWall();
    /**
     * Fires the Bubble by setting the state, xSpeed and ySpeed.
     *
     * @param angle the angle at which the bubble is fired at
     */
    public abstract void fire(final int angle);
    /**
     * Pops the Bubble by setting state to POPPED and setting xSpeed
     * and ySpeed to zero.
     */
    public abstract void pop();
    /**
     * Drops the Bubble by setting state to DROPPING and setting xSpeed
     * to zero and ySpeed to speed.
     */
    public abstract void drop();
    /**
     * Recursively traverses the PowerUp hierarchy to find the root
     * SimpleBubble instance.
     *
     * @return the root SimpleBubble instance
     */
    public abstract Bubble getRootBubble();
    /**
     * Getter method: for X coordinate of the bubble.
     *
     * @return x value
     */
    public abstract float getX();
    /**
     * Setter method: for the X coordinate of the bubble.
     *
     * @param xPos double value to set x to
     */
    public abstract void setX(final float xPos);
    /**
     * Getter method: for the Y coordinate of the bubble.
     *
     * @return y value
     */
    public abstract float getY();
    /**
     * Setter method: for the Y coordinate of the bubble.
     *
     * @param yPos double value to set y to
     */
    public abstract void setY(final float yPos);
    /**
     * Getter method: for the xSpeed value.
     *
     * @return xSpeed value
     */
    public abstract double getXSpeed();
    /**
     * Setter method: for the xSpeed value.
     *
     * @param xspeed double value to set xspeed to
     */
    public abstract void setXSpeed(final double xspeed);
    /**
     * Getter method: for the ySpeed value.
     *
     * @return ySpeed value
     */
    public abstract double getYSpeed();
    /**
     * Setter method: for the ySpeed value.
     *
     * @param yspeed double value to set yspeed to
     */
    public abstract void setYSpeed(final double yspeed);
    /**
     * Getter method: for the Color value.
     *
     * @return color value
     */
    public abstract ColorChoice getColor();
    /**
     * Getter method: for the state.
     *
     * @return state value
     */
    public abstract State getState();
    /**
     * Setter method: for the state.
     *
     * @param newState State enum value to set state to
     */
    public abstract void setState(final State newState);
    /**
     * Getter method: for the wrapped Bubble component.
     *
     * @return bubble component
     */
    public abstract Bubble getBubble();
    /**
     * Getter method: for the boundingBox.
     *
     * @return circle object
     */
    public abstract Circle getBoundingBox();
    /**
     * Setter method: for a Bubble's firing speed.
     *
     * @param nextSpeed new speed value
     */
    public abstract void setFireSpeed(final double nextSpeed);
    /**
     * Getter method for the Firing Speed of the bubble.
     *
     * @return double speed value
     */
    public abstract double getFireSpeed();
}
