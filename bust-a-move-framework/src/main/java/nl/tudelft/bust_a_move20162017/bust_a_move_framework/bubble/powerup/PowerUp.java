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


package nl.tudelft.bust_a_move20162017.bust_a_move_framework.bubble.powerup;

import nl.tudelft.bust_a_move20162017.bust_a_move_framework.bubble.Bubble;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Circle;

/**
 * PowerUp provides an abstract representation of a PowerUp
 * that can be recursively applied to a Bubble.
 *
 * @author Calvin Nhieu
 *
 */
public abstract class PowerUp extends Bubble {
  /**
   * Root bubble component.
   */
  protected Bubble rootBubble;

  /**
   * Empty constructor per abstract class specification.
   */
  public PowerUp() {}

  /**
   * Creates PowerUp instance.
   * @param bubble  wrapped Bubble component.
   */
  public PowerUp(final Bubble bubble) {
    this.bubble = bubble;
    this.rootBubble = this.getRootBubble();
  }

  /**
   * Applies a random PowerUp to the supplied Bubble
   * (can be none).
   *
   * @param bubble  Bubble to apply PowerUps to.
   * @return Bubble  PowerUp'd Bubble.
   */
  public static Bubble apply(final Bubble bubble) {
    double rng = Math.random();
    Bubble puBubble = bubble;

    if (rng < 0.15) {
      puBubble = new SpeedUp(puBubble);
    } else if (0.15 <= rng && rng < 0.25) {
      puBubble = new SlowDown(puBubble);
    } else if (0.25 <= rng && rng < 0.35) {
      puBubble = new LongScope(puBubble);
    } else if (0.35 <= rng && rng < 0.45) {
      puBubble = new ShortScope(puBubble);
    } else if (0.45 <= rng && rng < 0.475) {
      puBubble = new OBomb(puBubble);
    } else if (0.475 <= rng && rng < 0.485) {
      puBubble = new RowBomb(puBubble);
    }

    return puBubble;
  }

  /**
   * Draws the PowerUp'd Bubble.
   * @param g  Java Graphics instance
   */
  public abstract void draw(final Graphics g);

  /**
   * Updates the Bubble's position per game tick.
   */
  public final void move() {
    this.rootBubble.move();
  }

  /**
   * Stops the Bubble, adjusts bubble's position.
   *
   * @param xPos at adjusted x position
   * @param yPos at adjusted y position
   */
  public final void land(final double xPos, final double yPos) {
    this.rootBubble.land((float) xPos, (float) yPos);
  }

  /**
   * Wall collision detection, inverts ball's xSpeed.
   */
  public final void hitWall() {
    this.rootBubble.hitWall();
  }

  /**
   * Fires the Bubble by setting the state, xSpeed and ySpeed.
   * @param angle the angle at which the bubble is fired at
   */
  public final void fire(final int angle) {
    this.rootBubble.fire(angle);
  }

  /**
   * Pops the root Bubble.
   */
  public abstract void pop();

  /**
   * Drops the Bubble by setting state to DROPPING and setting xSpeed
   * to zero and ySpeed to SPEED.
   */
  public final void drop() {
    this.rootBubble.drop();
  }

  /**
   * Getter method: for X coordinate of the bubble.
   * @return x value
   */
  public final float getX() {
    return this.rootBubble.getX();
  }

  /**
   * Setter method: for the X coordinate of the bubble.
   * @param xPos  double value to set x to
   */
  public final void setX(final float xPos) {
    this.rootBubble.setX(xPos);
  }

  /**
   * Getter method: for the Y coordinate of the bubble.
   * @return y value
   */
  public final float getY() {
    return this.rootBubble.getY();
  }

  /**
   * Setter method: for the Y coordinate of the bubble.
   * @param yPos  double value to set y to
   */
  public final void setY(final float yPos) {
    this.rootBubble.setY(yPos);
  }

  /**
   * Getter method: for the xSpeed value.
   * @return xSpeed value
   */
  public final double getXSpeed() {
    return this.rootBubble.getXSpeed();
  }

  /**
   * Setter method: for the xSpeed value.
   * @param xSpeed  double value to set xSpeed to
   */
  public final void setXSpeed(final double xSpeed) {
    this.rootBubble.setXSpeed(xSpeed);
  }

  /**
   * Getter method: for the ySpeed value.
   * @return  ySpeed value
   */
  public final double getYSpeed() {
    return this.rootBubble.getYSpeed();
  }

  /**
   * Setter method: for the ySpeed value.
   * @param ySpeed  double value to set ySpeed to
   */
  public final void setYSpeed(final double ySpeed) {
    this.rootBubble.setYSpeed(ySpeed);
  }

  /**
   * Getter method: for the Color value.
   * @return color value
   */
  public final ColorChoice getColor() {
    return this.rootBubble.getColor();
  }

  /**
   * Getter method: for the state.
   * @return state value
   */
  public final State getState() {
    return this.rootBubble.getState();
  }

  /**
   * Setter method: for the state.
   * @param newState  State enum value to set state to
   */
  public final void setState(final State newState) {
    this.rootBubble.setState(newState);
  }

  /**
   * Getter method: for the boundingBox.
   * @return circle object
   */
  public final Circle getBoundingBox() {
    return this.rootBubble.getBoundingBox();
  }

  /**
   * Getter method: for the wrapped Bubble component.
   * @return bubble component
   */
  public final Bubble getBubble() {
    return this.bubble;
  }
}
