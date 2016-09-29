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


package nl.tudelft.bust_a_move20162017.bust_a_move_framework.bubble;

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
   * Empty constructor per abstract specification.
   *
   */
  public PowerUp() {}

  /**
   * Creates PowerUp instance.
   * @param bubble  wrapped Bubble component.
   */
  public PowerUp(Bubble bubble) {
    this.bubble = bubble;
    this.rootBubble = this.getRootBubble();
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
    this.rootBubble.land(xPos, yPos);
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
  public final double getX() {
    return this.rootBubble.getX();
  }

  /**
   * Setter method: for the X coordinate of the bubble.
   * @param xPos  double value to set x to
   */
  public final void setX(final double xPos) {
    this.rootBubble.setX(xPos);
  }

  /**
   * Getter method: for the Y coordinate of the bubble.
   * @return y value
   */
  public final double getY() {
    return this.rootBubble.getY();
  }

  /**
   * Setter method: for the Y coordinate of the bubble.
   * @param yPos  double value to set y to
   */
  public final void setY(final double yPos) {
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
