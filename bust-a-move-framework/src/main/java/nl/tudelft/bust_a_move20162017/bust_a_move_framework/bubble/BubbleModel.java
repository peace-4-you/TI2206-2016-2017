/*
 * File: BubbleModel.java
 *
 * Class: BubbleModel
 *
 * Version: 0.0.1
 *
 * Date: September 7th, 2016
 *
 */


package nl.tudelft.bust_a_move20162017.bust_a_move_framework.bubblemodel;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Observable;

/**
 * The BubbleModel class represents a single bubble entity
 *
 * @author Calvin Nhieu
 *
 */
public class BubbleModel implements Observable {
  public static final double DIAMETER = 40;
  public static final double SPEED = 5;

  private static final Color RED_COLOR = new Color(0xFF0000);
  private static final Color BLUE_COLOR = new Color(0x00FF00);
  private static final Color YELLOW_COLOR = new Color(0x00FFFF);
  private static final Color GREEN_COLOR = new Color(0x0000FF);

  public enum ColorChoice {
    RED, BLUE, YELLOW, GREEN
  }
  public enum State {
    STILL, FIRING, POPPING, DROPPING
  }

  private double x;
  private double y;
  private double xSpeed;
  private double ySpeed;
  private int angle;
  private ColorChoice color;
  private Color drawColor;

  private State state;

  /**
   * Creates BubbleModel instance
   *
   * @param x  double value for starting x position
   * @param y  double value for starting y position
   * @param color  ColorChoice enum value for bubble's color
   */
  public Bubble(double x, double y, ColorChoice color) {
    this.x = x;
    this.y = y;
    this.xSpeed = 0;
    this.ySpeed = 0;
    this.angle = 0;
    this.color = color;
    this.state = State.STILL;

    switch(color) {
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
    }

    this.updateObservers();
  }

  /**
   * updates the Bubble's position per game tick
   */
  public void move() {
    switch(this.state) {
      case State.STILL:
        break;
      case State.FIRING:
        this.setX(this.getX() + this.getXSpeed());
        this.setY(this.getY() + this.getYSpeed());
        break;
      case State.POPPING:
        break;
      case State.DROPPING:
        this.setX(this.getX() + this.getXSpeed());
        this.setY(this.getY() + this.getYSpeed());
        break;
    }
  }

  /**
   * updates subscribed Observers
   */
  public void updateObservers() {
    this.setChanged(true);
    this.notifyObservers();
  }

  /**
   * stops the Bubble
   */
  public void still() {
    this.setState(State.STILL);
    this.setXSpeed(0);
    this.setYSpeed(0);
  }

  /**
   * fire the Bubble
   */
  public void fire(int angle) {
    this.setAngle(angle);
    this.setState(State.FIRING);
    if (this.angle > 0) {
      this.setXSpeed(Math.sin(angle)*Bubble.SPEED);
      this.setYSpeed(-Math.cos(angle)*Bubble.SPEED);
    } else if (this.angle < 0) {
      this.setXSpeed(-Math.sin(angle)*Bubble.SPEED);
      this.setYSpeed(-Math.cos(angle)*Bubble.SPEED);
    } else {
      this.setXSpeed(0);
      this.setYSpeed(Bubble.SPEED);
    }
  }

  /**
   * pops the Bubble
   */
  public void pop() {
    this.setState(State.POPPING);
    this.setXSpeed(0);
    this.setYSpeed(0);
  }

  /**
   * drops the Bubble
   */
  public void drop() {
    this.setState(State.DROPPING);
    this.setXSpeed(0);
    this.setYSpeed(Bubble.SPEED);
  }

  /**
   * returns x value
   */
  public double getX() {
    return this.x;
  }
  /**
   * sets x value
   *
   * @param x  double value to set x to
   */
  public void setX(double x) {
    this.x = x;
    this.updateObservers();
  }

  /**
   * returns y value
   */
  public double getY() {
    return this.y;
  }
  /**
   * sets y value
   *
   * @param y  double value to set y to
   */
  public void setY(double y) {
    this.y = y;
    this.updateObservers();
  }

  /**
   * returns xSpeed value
	 */
  public double getXSpeed() {
    return this.xSpeed;
  }
  /**
   * sets xSpeed value
   *
   * @param xSpeed  double value to set xSpeed to
   */
  public void setXSpeed(double xSpeed) {
    this.xSpeed = xSpeed;
  }

  /**
   * returns ySpeed value
   */
  public double getYSpeed() {
    return this.xSpeed;
  }
  /**
   * sets ySpeed value
   *
   * @param ySpeed  double value to set ySpeed to
   */
  public void setYSpeed(double ySpeed) {
    this.ySpeed = ySpeed;
  }

  /**
   * returns Color value
   */
  public ColorChoice getColor() {
    return this.color;
  }

  /**
   * returns state
   */
  public State getState() {
    return this.state;
  }
  /**
   * sets state
   *
   * @param state  State enum value to set state to
   */
  public void setState(State state) {
    this.state = state;
  }
}
