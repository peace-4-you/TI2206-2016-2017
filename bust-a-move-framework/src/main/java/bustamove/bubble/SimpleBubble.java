/*
 * File: SimpleBubble.java
 *
 * Class: SimpleBubble
 *
 * Version: 0.0.2
 *
 * Date: September 13th, 2016
 *
 */

package bustamove.bubble;

import bustamove.system.Log;
import bustamove.system.SoundHandler;
import bustamove.game.GameData;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Point;


import java.util.LinkedList;

/**
 * The SimpleBubble class represents a single bubble entity.
 *
 * @author Calvin Nhieu
 */
public class SimpleBubble extends Bubble {
    /**
     * Colors of the bubble.
     */
    private static final Color RED_COLOR = Color.red;
    private static final Color BLUE_COLOR = Color.blue;
    private static final Color YELLOW_COLOR = Color.yellow;
    private static final Color GREEN_COLOR = Color.green;
    /**
     * Angle offset constant.
     */
    private static final int ANGLE_OFFSET = 90;
    /**
     * The position of the bubble.
     */
    private Point pos = new Point(0, 0);
    /**
     * Horizontal component of speed.
     */
    private double xSpeed;
    /**
     * Vertical component of speed.
     */
    private double ySpeed;
    /**
     * The color of the bubble.
     */
    private ColorChoice color;
    /**
     * Color used for rendering of the bubble.
     */
    private Color drawColor;
    /**
     * State of the bubble.
     */
    private State state;
    /**
     * Collision space of the bubble.
     */
    private Circle boundingBox;
    /**
     * GameData Object where the bubbles belong to. Needed by powerups
     */
    private GameData gamehead;
    /**
     * Empty constructor per abstract class specification.
     */
    public SimpleBubble() {
    }
    /**
     * Creates SimpleBubble instance.
     *
     * @param xPos      double value for starting x position
     * @param yPos      double value for starting y position
     * @param c         ColorChoice enum value for bubble's color
     * @param forCannon boolean describing if SimpleBubble is for
     *                  Cannon or not (map otherwise)
     */
    public SimpleBubble(final float xPos, final float yPos, final ColorChoice c,
                  final boolean forCannon) {
        this.pos = new Point(xPos, yPos);
        this.xSpeed = 0;
        this.ySpeed = 0;
        this.color = c;
        if (forCannon) {
            this.state = State.NEW;
        } else {
            this.state = State.LANDED;
        }
        boundingBox = new Circle(
                (float) (pos.getX() + SimpleBubble.DIAMETER / 2),
                (float) (pos.getY() + SimpleBubble.DIAMETER / 2),
                (float) SimpleBubble.DIAMETER / 2);

        switch (color) {
            case RED:
                this.drawColor = SimpleBubble.RED_COLOR;
                break;
            case BLUE:
                this.drawColor = SimpleBubble.BLUE_COLOR;
                break;
            case YELLOW:
                this.drawColor = SimpleBubble.YELLOW_COLOR;
                break;
            case GREEN:
                this.drawColor = SimpleBubble.GREEN_COLOR;
                break;
            default:
                break;
        }
    }

    /**
     * Sets the gamehead, this is a instance of GameData.
     * @param game the gamehead to refer to
     */
    public final void setGameHead(final GameData game) {
      this.gamehead = game;
    }
    /**
     * Returns the gamehead.
     * @return  GameData gamehead
     */
    public final GameData getGameHead() {
      return this.gamehead;
    }
    /**
     * Draws the SimpleBubble.
     *
     * @param g Java Graphics instance
     */
    public final void draw(final Graphics g) {
        g.setColor(this.drawColor);
        g.fillOval((int) this.pos.getX(), (int) this.pos.getY(),
                (int) SimpleBubble.DIAMETER, (int) SimpleBubble.DIAMETER);
    }
    /**
     * Updates the SimpleBubble's position per game tick.
     */
    public final void move() {
        float nextX = (float) (this.getX() + this.getXSpeed());
        float nextY = (float) (this.getY() + this.getYSpeed());

        switch (this.state) {
            case NEW:
                break;
            case LANDED:
                break;
            case FIRING:
                this.setX(nextX);
                this.setY(nextY);
                break;
            case POPPED:
                break;
            case DROPPING:
                this.setX(nextX);
                this.setY(nextY);
                break;
            default:
                break;
        }
    }
    /**
     * Stops the SimpleBubble, adjusts bubble's position.
     *
     * @param xPos at adjusted x position
     * @param yPos at adjusted y position
     */
    public final void land(final float xPos, final float yPos) {
        this.setState(State.LANDED);
        this.setXSpeed(0);
        this.setYSpeed(0);
        Log.getInstance().log(this, "Adjusting bubble land position to: ("
                + (int) pos.getX() + " ; " + (int) pos.getY() + ")");
        this.setX(xPos);
        this.setY(yPos);
    }
    /**
     * Wall collision detection, inverts ball's xSpeed.
     */
    public final void hitWall() {
        if (this.getState() == State.FIRING) {
            this.setXSpeed(-this.getXSpeed());
        }
    }
    /**
     * Fires the SimpleBubble by setting the state, xSpeed and ySpeed.
     *
     * @param angle the angle at which the bubble is fired at
     */
    public final void fire(final int angle) {
        this.setState(State.FIRING);
        this.setXSpeed(Math.cos(Math.toRadians(angle + ANGLE_OFFSET))
                * gamehead.getBubbleSpeed());
        this.setYSpeed(-Math.sin(Math.toRadians(angle + ANGLE_OFFSET))
                * gamehead.getBubbleSpeed());
      }
    /**
     * Pops the SimpleBubble by setting state to POPPED and setting xSpeed
     * and ySpeed to zero.
     */
    public final void pop() {
        SoundHandler.getInstance().playPopSound();
        this.setXSpeed(0);
        this.setYSpeed(0);
    }
    /**
     * Drops the SimpleBubble by setting state to DROPPING and setting xSpeed
     * to zero and ySpeed to speed.
     */
    public final void drop() {
        this.setState(State.DROPPING);
        this.setXSpeed(0);
        this.setYSpeed(Bubble.DROP_SPEED);
    }
    /**
     * Recursively traverses the PowerUp hierarchy to find the root
     * Bubble instance.
     *
     * @return the root SimpleBubble instance
     */
    public final Bubble getRootBubble() {
        return this;
    }
    /**
     * Getter method: for X coordinate of the bubble.
     *
     * @return x value
     */
    public final float getX() {
        return this.pos.getX();
    }
    /**
     * Setter method: for the X coordinate of the bubble.
     *
     * @param xPos double value to set x to
     */
    public final void setX(final float xPos) {
        this.pos.setX(xPos);
        this.boundingBox.setX((float) xPos);
    }
    /**
     * Getter method: for the Y coordinate of the bubble.
     *
     * @return y value
     */
    public final float getY() {
        return this.pos.getY();
    }
    /**
     * Setter method: for the Y coordinate of the bubble.
     *
     * @param yPos double value to set y to
     */
    public final void setY(final float yPos) {
        this.pos.setY(yPos);
        this.boundingBox.setY((float) yPos);
    }
    /**
     * Getter method: for the xSpeed value.
     *
     * @return xSpeed value
     */
    public final double getXSpeed() {
        return this.xSpeed;
    }
    /**
     * Setter method: for the xSpeed value.
     *
     * @param xspeed double value to set xspeed to
     */
    public final void setXSpeed(final double xspeed) {
        this.xSpeed = xspeed;
    }
    /**
     * Getter method: for the ySpeed value.
     *
     * @return ySpeed value
     */
    public final double getYSpeed() {
        return this.ySpeed;
    }
    /**
     * Setter method: for the ySpeed value.
     *
     * @param yspeed double value to set yspeed to
     */
    public final void setYSpeed(final double yspeed) {
        this.ySpeed = yspeed;
    }
    /**
     * Getter method: for the Color value.
     *
     * @return color value
     */
    public final ColorChoice getColor() {
        return this.color;
    }
    /**
     * Getter method: for the state.
     *
     * @return state value
     */
    public final State getState() {
        return this.state;
    }
    /**
     * Setter method: for the state.
     *
     * @param newState State enum value to set state to
     */
    public final void setState(final State newState) {
        this.state = newState;
    }
    /**
     * Getter method: for the wrapped Bubble component.
     *
     * @return bubble component
     */
    public final Bubble getBubble() {
        return null;
    }
    /**
     * Getter method: for the boundingBox.
     *
     * @return circle object
     */
    public final Circle getBoundingBox() {
        return this.boundingBox;
    }
    /**
     * Setter method: for a Bubble's firing speed.
     *
     * @param nextSpeed new speed value
     */
    public final void setFireSpeed(final double nextSpeed) {
        gamehead.setBubbleSpeed(nextSpeed);
    }
    /**
     * Getter method for the Firing Speed of the bubble.
     *
     * @return double speed value
     */
    public final double getFireSpeed() {
        return gamehead.getBubbleSpeed();
    }
    /**
     * Creates a bubble object with a random color from the given list.
     *
     * @param x         X coordinate of the new bubble
     * @param y         Y coordinate of the new bubble
     * @param colors    list of possible bubble colors
     * @param forCannon boolean describing if SimpleBubble is for Cannon or not
     *                  (map otherwise)
     * @return newly created bubble
     */
    public static SimpleBubble randomColor(final float x, final float y,
                                     final LinkedList<ColorChoice> colors,
                                     final boolean forCannon) {
        if (colors.size() == 0) {
            return null;
        }
        ColorChoice color = colors.get((int) Math.floor(Math.random()
                * colors.size()));
        return new SimpleBubble(x, y, color, forCannon);
    }
}
