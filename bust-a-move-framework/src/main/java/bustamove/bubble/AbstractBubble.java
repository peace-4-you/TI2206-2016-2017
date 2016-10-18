/*
 * File: AbstractBubble.java
 *
 * Class: AbstractBubble
 *
 * Version: 0.0.1
 *
 * Date: October 18th, 2016
 *
 */

package bustamove.bubble;

import bustamove.system.Log;
import bustamove.game.GameData;
import bustamove.bubble.powerup.PowerUp;
import bustamove.gamestate.GameConfig;
import bustamove.sound.SoundHandler;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Point;


import java.util.LinkedList;

/**
 * The AbstractBubble class represents a single bubble entity.
 *
 * @author Calvin Nhieu
 */
public abstract class AbstractBubble {
    /**
     * Diameter of a bubble.
     */
    public static final double DIAMETER = 35;
    /**
     * Firing speed of a bubble.
     */
    protected static double dropSpeed = GameConfig.DEFAULT_BUBBLE_SPEED;
    /**
     * Colors of the bubble.
     */
    protected static final Color RED_COLOR = Color.red;
    protected static final Color BLUE_COLOR = Color.blue;
    protected static final Color YELLOW_COLOR = Color.yellow;
    protected static final Color GREEN_COLOR = Color.green;
    /**
     * Angle offset constant.
     */
    protected static final int ANGLE_OFFSET = 90;
    /**
     * The position of the bubble.
     */
    protected Point pos = new Point(0, 0);
    /**
     * Horizontal component of speed.
     */
    protected double xSpeed;
    /**
     * Vertical component of speed.
     */
    protected double ySpeed;
    /**
     * The color of the bubble.
     */
    protected ColorChoice color;
    /**
     * Color used for rendering of the bubble.
     */
    protected Color drawColor;
    /**
     * State of the bubble.
     */
    protected State state;
    /**
     * Collision space of the bubble.
     */
    protected Circle boundingBox;
    /**
     * Wrapped bubble component.
     */
    //CHECKSTYLE:OFF: VisibilityModifier
    protected AbstractBubble bubble;
    //CHECKSTYLE:ON: VisibilityModifier
    /**
     * GameData Object where the bubbles belong to. Needed by powerups
     */
    //CHECKSTYLE:OFF: VisibilityModifier
    protected GameData gamehead;
    //CHECKSTYLE:ON: VisibilityModifier
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
    public AbstractBubble() {
    }
    /**
     * Sets the gamehead, this is a instance of GameData.
     * @param game the gamehead to refer to
     */
    //CHECKSTYLE:OFF: DesignForExtension
    public abstract void setGameHead(final GameData game);
    //CHECKSTYLE:ON: DesignForExtension
    /**
     * Returns the gamehead.
     * @return  GameData gamehead
     */
    //CHECKSTYLE:OFF: DesignForExtension
    public abstract GameData getGameHead();
    //CHECKSTYLE:ON: DesignForExtension
    /**
     * Draws the AbstractBubble.
     *
     * @param g Java Graphics instance
     */
    //CHECKSTYLE:OFF: DesignForExtension
    public abstract void draw(final Graphics g);
    /**
     * Updates the AbstractBubble's position per game tick.
     */
    public abstract void move();
    /**
     * Stops the AbstractBubble, adjusts bubble's position.
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
     * Fires the AbstractBubble by setting the state, xSpeed and ySpeed.
     *
     * @param angle the angle at which the bubble is fired at
     */
    public abstract void fire(final int angle);
    /**
     * Pops the AbstractBubble by setting state to POPPED and setting xSpeed
     * and ySpeed to zero.
     */
    public abstract void pop();
    /**
     * Drops the AbstractBubble by setting state to DROPPING and setting xSpeed
     * to zero and ySpeed to speed.
     */
    public abstract void drop();
    /**
     * Recursively traverses the PowerUp hierarchy to find the root
     * SimpleBubble instance.
     *
     * @return the root SimpleBubble instance
     */
    public abstract SimpleBubble getRootBubble();
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
    public abstract AbstractBubble getBubble();
    /**
     * Getter method: for the boundingBox.
     *
     * @return circle object
     */
    public abstract Circle getBoundingBox();
    /**
     * Setter method: for a AbstractBubble's firing speed.
     *
     * @param nextSpeed new speed value
     */
    //CHECKSTYLE:ON: DesignForExtension
    public abstract void setFireSpeed(final double nextSpeed);
    /**
     * Getter method for the Firing Speed of the bubble.
     *
     * @return double speed value
     */
    public abstract double getFireSpeed();
    /**
     * Setter method: for a Bubble's dropping speed.
     *
     * @param nextSpeed new speed value
     */
    public static final void setDropSpeed(final double nextSpeed) {
        dropSpeed = nextSpeed;
    }
    /**
     * Getter method for the Dropping speed of the bubble.
     *
     * @return double speed value
     */
    public static final double getDropSpeed() {
        return dropSpeed;
    }
}
