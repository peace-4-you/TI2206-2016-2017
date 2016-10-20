/*
 * File: PowerUp.java
 *
 * Class: PowerUp
 *
 * Version: 0.0.1
 *
 * Date: September 28th, 2016
 *
 */


package bustamove.bubble.powerup;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Circle;

import bustamove.bubble.Bubble;
import bustamove.game.GameData;

/**
 * PowerUp provides an abstract representation of a PowerUp
 * that can be recursively applied to a Bubble.
 *
 * @author Calvin Nhieu
 */
public abstract class PowerUp extends Bubble {
    /**
     * Root bubble component.
     */
    private Bubble rootBubble;
    /**
     * Wrapped bubble component.
     */
    private Bubble bubble;

    private final int powerupOffset = 7;

    private static final double SPEEDUP_CHANCE = 0.15;
    private static final double SLOWDOWN_CHANCE = 0.25;
    private static final double LONGSCOPE_CHANCE = 0.35;
    private static final double SHORTSCOPE_CHANCE = 0.45;
    private static final double OBOMB_CHANCE = 0.475;
    private static final double ROWBOMB_CHANCE = 0.485;


    /**
     * Empty constructor per abstract class specification.
     */
    public PowerUp() {
    }
    /**
     * Creates PowerUp instance.
     *
     * @param b  wrapped Bubble component.
     */
    public PowerUp(final Bubble b) {
        this.bubble = b;
        rootBubble = this.getRootBubble();
    }
    /**
     * Applies a random PowerUp to the supplied Bubble
     * (can be none).
     *
     * @param bubble Bubble to apply PowerUps to.
     * @return Bubble  PowerUp'd Bubble.
     */
    public static Bubble apply(final Bubble bubble) {
        double rng = Math.random();
        Bubble puBubble = bubble;

        if (rng < SPEEDUP_CHANCE) {
            puBubble = new SpeedUp(puBubble);
        } else if (rng < SLOWDOWN_CHANCE) {
            puBubble = new SlowDown(puBubble);
        } else if (rng < LONGSCOPE_CHANCE) {
            puBubble = new LongScope(puBubble);
        } else if (rng < SHORTSCOPE_CHANCE) {
            puBubble = new ShortScope(puBubble);
        } else if (rng < OBOMB_CHANCE) {
            puBubble = new OBomb(puBubble);
        } else if (rng < ROWBOMB_CHANCE) {
            puBubble = new RowBomb(puBubble);
        }

        return puBubble;
    }
    /**
     * Draws the PowerUp'd Bubble.
     *
     * @param g Java Graphics instance
     */
    public abstract void draw(Graphics g);
    /**
     * Updates the Bubble's position per game tick.
     */
    public final void move() {
        rootBubble.move();
    }
    /**
     * Stops the Bubble, adjusts bubble's position.
     *
     * @param xPos at adjusted x position
     * @param yPos at adjusted y position
     */
    public final void land(final float xPos, final float yPos) {
        rootBubble.land((float) xPos, (float) yPos);
    }
    /**
     * Wall collision detection, inverts ball's xSpeed.
     */
    public final void hitWall() {
        rootBubble.hitWall();
    }
    /**
     * Fires the Bubble by setting the state, xSpeed and ySpeed.
     *
     * @param angle the angle at which the bubble is fired at
     */
    public final void fire(final int angle) {
        rootBubble.fire(angle);
    }
    /**
     * Pops the root SimpleBubble.
     */
    public abstract void pop();
    /**
     * Drops the SimpleBubble by setting state to DROPPING and setting xSpeed
     * to zero and ySpeed to speed.
     */
    public final void drop() {
        rootBubble.drop();
    }
    /**
     * Recursively traverses the PowerUp hierarchy to find the root
     * Bubble instance.
     *
     * @return the root SimpleBubble instance
     */
    public final Bubble getRootBubble() {
        return bubble.getRootBubble();
    }
    /**
     * Getter method: for X coordinate of the bubble.
     *
     * @return x value
     */
    public final float getX() {
        return rootBubble.getX();
    }
    /**
     * Setter method: for the X coordinate of the bubble.
     *
     * @param xPos double value to set x to
     */
    public final void setX(final float xPos) {
        rootBubble.setX(xPos);
    }
    /**
     * Getter method: for the Y coordinate of the bubble.
     *
     * @return y value
     */
    public final float getY() {
        return rootBubble.getY();
    }
    /**
     * Setter method: for the Y coordinate of the bubble.
     *
     * @param yPos double value to set y to
     */
    public final void setY(final float yPos) {
        rootBubble.setY(yPos);
    }
    /**
     * Getter method: for the xSpeed value.
     *
     * @return xSpeed value
     */
    public final double getXSpeed() {
        return rootBubble.getXSpeed();
    }
    /**
     * Setter method: for the xSpeed value.
     *
     * @param xSpeed double value to set xSpeed to
     */
    public final void setXSpeed(final double xSpeed) {
        rootBubble.setXSpeed(xSpeed);
    }
    /**
     * Getter method: for the ySpeed value.
     *
     * @return ySpeed value
     */
    public final double getYSpeed() {
        return rootBubble.getYSpeed();
    }
    /**
     * Setter method: for the ySpeed value.
     *
     * @param ySpeed double value to set ySpeed to
     */
    public final void setYSpeed(final double ySpeed) {
        rootBubble.setYSpeed(ySpeed);
    }
    /**
     * Getter method: for the Color value.
     *
     * @return color value
     */
    public final ColorChoice getColor() {
        return rootBubble.getColor();
    }
    /**
     * Getter method: for the state.
     *
     * @return state value
     */
    public final State getState() {
        return rootBubble.getState();
    }
    /**
     * Setter method: for the state.
     *
     * @param newState State enum value to set state to
     */
    public final void setState(final State newState) {
        rootBubble.setState(newState);
    }
    /**
     * Getter method: for the boundingBox.
     *
     * @return circle object
     */
    public final Circle getBoundingBox() {
        return rootBubble.getBoundingBox();
    }
    /**
     * Getter method: for the wrapped Bubble component.
     *
     * @return bubble component
     */
    public final Bubble getBubble() {
        return bubble;
    }
    /**
     * Returns the x offset for drawing the powerup string.
     *
     * @return x offset in int format.
     */
    public final int getpowerupOffset() {
        return powerupOffset;
    }
    /**
     * Setter method: sets the gamedata object that corresponds to this PowerUp.
     *
     * @param game the gamehead to refer to
     */
    public final void setGameHead(final GameData game) {
        bubble.setGameHead(game);
    }
    /**
     * Getter method: gets the gamedata object that corresponds to this PowerUp.
     *
     * @return GameData gamehead;
     */
    public final GameData getGameHead() {
        return rootBubble.getGameHead();
    }
    /**
     * Setter method: for a Bubble's firing speed.
     *
     * @param nextSpeed new speed value
     */
    //CHECKSTYLE:ON: DesignForExtension
    public final void setFireSpeed(final double nextSpeed) {
        rootBubble.getGameHead().setBubbleSpeed(nextSpeed);
    }
    /**
     * Getter method for the Firing Speed of the bubble.
     *
     * @return double speed value
     */
    public final double getFireSpeed() {
        return rootBubble.getGameHead().getBubbleSpeed();
    }
}
