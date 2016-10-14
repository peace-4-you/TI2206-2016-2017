/*
 * File: Bubble.java
 *
 * Class: Bubble
 *
 * Version: 0.0.2
 *
 * Date: September 13th, 2016
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
 * The Bubble class represents a single bubble entity.
 *
 * @author Calvin Nhieu
 */
public class Bubble {
    /**
     * Diameter of a bubble.
     */
    public static final double DIAMETER = 35;
    /**
     * Firing speed of a bubble.
     */
    private static double dropSpeed = GameConfig.DEFAULT_BUBBLE_SPEED;
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
     * Wrapped bubble component.
     */
    //CHECKSTYLE:OFF: VisibilityModifier
    protected Bubble bubble;
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
    public Bubble() {
    }

    /**
     * Creates Bubble instance.
     *
     * @param xPos      double value for starting x position
     * @param yPos      double value for starting y position
     * @param c         ColorChoice enum value for bubble's color
     * @param forCannon boolean describing if Bubble is for
     *                  Cannon or not (map otherwise)
     */
    public Bubble(final float xPos, final float yPos, final ColorChoice c,
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
        boundingBox = new Circle((float) (pos.getX() + Bubble.DIAMETER / 2),
                (float) (pos.getY() + Bubble.DIAMETER / 2),
                (float) Bubble.DIAMETER / 2);

        switch (color) {
            case RED:
                this.drawColor = Bubble.RED_COLOR;
                break;
            case BLUE:
                this.drawColor = Bubble.BLUE_COLOR;
                break;
            case YELLOW:
                this.drawColor = Bubble.YELLOW_COLOR;
                break;
            case GREEN:
                this.drawColor = Bubble.GREEN_COLOR;
                break;
            default:
                break;
        }
    }

    /**
     * Sets the gamehead, this is a instance of GameData.
     * @param game the gamehead to refer to
     */
    //CHECKSTYLE:OFF: DesignForExtension
    public void setGameHead(final GameData game) {
      this.gamehead = game;
    }
    //CHECKSTYLE:ON: DesignForExtension

    /**
     * Returns the gamehead.
     * @return  GameData gamehead
     */
     //CHECKSTYLE:OFF: DesignForExtension
    public GameData getGameHead() {
      return this.gamehead;
    }
    //CHECKSTYLE:ON: DesignForExtension

    /**
     * Draws the Bubble.
     *
     * @param g Java Graphics instance
     */
    //CHECKSTYLE:OFF: DesignForExtension
    public void draw(final Graphics g) {
        g.setColor(this.drawColor);
        g.fillOval((int) this.pos.getX(), (int) this.pos.getY(),
                (int) Bubble.DIAMETER, (int) Bubble.DIAMETER);
    }

    /**
     * Updates the Bubble's position per game tick.
     */
    public void move() {
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
     * Stops the Bubble, adjusts bubble's position.
     *
     * @param xPos at adjusted x position
     * @param yPos at adjusted y position
     */
    public final void land(final float xPos, final float yPos) {
        this.setState(State.LANDED);
        this.setXSpeed(0);
        this.setYSpeed(0);
        Log.log(this, "Adjusting bubble land position to: (" + (int) pos.getX()
                + " ; " + (int) pos.getY() + ")");
        this.setX(xPos);
        this.setY(yPos);
    }

    /**
     * Wall collision detection, inverts ball's xSpeed.
     */
    public void hitWall() {
        if (this.getState() == State.FIRING) {
            this.setXSpeed(-this.getXSpeed());
        }
    }

    /**
     * Fires the Bubble by setting the state, xSpeed and ySpeed.
     *
     * @param angle the angle at which the bubble is fired at
     */
    public void fire(final int angle) {
        this.setState(State.FIRING);
        this.setXSpeed(Math.cos(Math.toRadians(angle + ANGLE_OFFSET))
                * gamehead.getBubbleSpeed());
        this.setYSpeed(-Math.sin(Math.toRadians(angle + ANGLE_OFFSET))
                * gamehead.getBubbleSpeed());
      }

    /**
     * Pops the Bubble by setting state to POPPED and setting xSpeed
     * and ySpeed to zero.
     */
    public void pop() {
    	SoundHandler.playPopSound();
        this.setXSpeed(0);
        this.setYSpeed(0);
    }

    /**
     * Drops the Bubble by setting state to DROPPING and setting xSpeed
     * to zero and ySpeed to speed.
     */
    public void drop() {
        this.setState(State.DROPPING);
        this.setXSpeed(0);
        this.setYSpeed(dropSpeed);
    }

    /**
     * Recursively traverses the PowerUp hierarchy to find the root
     * Bubble instance.
     *
     * @return the root Bubble instance
     */
    public final Bubble getRootBubble() {
        if (PowerUp.class.isAssignableFrom(this.getClass())) {
            return this.bubble.getRootBubble();
        } else if (this.getClass() == Bubble.class) {
            return this;
        } else {
            return null;
        }
    }

    /**
     * Getter method: for X coordinate of the bubble.
     *
     * @return x value
     */
    public float getX() {
        return this.pos.getX();
    }

    /**
     * Setter method: for the X coordinate of the bubble.
     *
     * @param xPos double value to set x to
     */
    public void setX(final float xPos) {
        this.pos.setX(xPos);
        this.boundingBox.setX((float) xPos);
    }

    /**
     * Getter method: for the Y coordinate of the bubble.
     *
     * @return y value
     */
    public float getY() {
        return this.pos.getY();
    }

    /**
     * Setter method: for the Y coordinate of the bubble.
     *
     * @param yPos double value to set y to
     */
    public void setY(final float yPos) {
        this.pos.setY(yPos);
        this.boundingBox.setY((float) yPos);
    }

    /**
     * Getter method: for the xSpeed value.
     *
     * @return xSpeed value
     */
    public double getXSpeed() {
        return this.xSpeed;
    }

    /**
     * Setter method: for the xSpeed value.
     *
     * @param xspeed double value to set xspeed to
     */
    public void setXSpeed(final double xspeed) {
        this.xSpeed = xspeed;
    }

    /**
     * Getter method: for the ySpeed value.
     *
     * @return ySpeed value
     */
    public double getYSpeed() {
        return this.ySpeed;
    }

    /**
     * Setter method: for the ySpeed value.
     *
     * @param yspeed double value to set yspeed to
     */
    public void setYSpeed(final double yspeed) {
        this.ySpeed = yspeed;
    }

    /**
     * Getter method: for the Color value.
     *
     * @return color value
     */
    public ColorChoice getColor() {
        return this.color;
    }

    /**
     * Getter method: for the state.
     *
     * @return state value
     */
    public State getState() {
        return this.state;
    }

    /**
     * Setter method: for the state.
     *
     * @param newState State enum value to set state to
     */
    public void setState(final State newState) {
        this.state = newState;
    }

    /**
     * Getter method: for the boundingBox.
     *
     * @return circle object
     */
    public Circle getBoundingBox() {
        return this.boundingBox;
    }

    /**
     * Setter method: for a Bubble's firing speed.
     *
     * @param nextSpeed new speed value
     */
    //CHECKSTYLE:ON: DesignForExtension
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
     * Setter method: for a Bubble's dropping speed.
     *
     * @param nextSpeed new speed value
     */
    public final void setDropSpeed(final double nextSpeed) {
        dropSpeed = nextSpeed;
    }

    /**
     * Getter method for the Dropping speed of the bubble.
     *
     * @return double speed value
     */
    public final double getDropSpeed() {
        return dropSpeed;
    }

    /**
     * Creates a bubble object with a random color from the given list.
     *
     * @param x         X coordinate of the new bubble
     * @param y         Y coordinate of the new bubble
     * @param colors    list of possible bubble colors
     * @param forCannon boolean describing if Bubble is for Cannon or not
     *                  (map otherwise)
     * @return newly created bubble
     */
    public static Bubble randomColor(final float x, final float y,
                                     final LinkedList<ColorChoice> colors,
                                     final boolean forCannon) {
        if (colors.size() == 0) {
            return null;
        }
        ColorChoice color = colors.get((int) Math.floor(Math.random()
                * colors.size()));
        return new Bubble(x, y, color, forCannon);
    }
}