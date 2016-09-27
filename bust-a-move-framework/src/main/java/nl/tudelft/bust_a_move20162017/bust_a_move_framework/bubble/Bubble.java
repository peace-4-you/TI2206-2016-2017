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


package nl.tudelft.bust_a_move20162017.bust_a_move_framework.bubble;

import java.util.LinkedList;
import java.util.Random;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Circle;

import nl.tudelft.bust_a_move20162017.bust_a_move_framework.App;

/**
 * The Bubble class represents a single bubble entity
 *
 * @author Calvin Nhieu
 *
 */
public class Bubble {
  public static final double DIAMETER = 35;
  public static final double SPEED = 3;
  private static final Color RED_COLOR = Color.red;
  private static final Color BLUE_COLOR = Color.blue;
  private static final Color YELLOW_COLOR = Color.yellow;
  private static final Color GREEN_COLOR = Color.green;
  private double x;
  private double y;
  private double xSpeed;
  private double ySpeed;
  private ColorChoice color;
  private Color drawColor;
  private State state;
  private Circle boundingBox;

  public enum State {
    NEW, LANDED, FIRING, POPPING, DROPPING
  }
  public enum ColorChoice {
      RED, BLUE, YELLOW, GREEN
  }

  /**
   * Creates Bubble instance
   *
   * @param x  double value for starting x position
   * @param y  double value for starting y position
   * @param color  ColorChoice enum value for bubble's color
   * @param forCannon  boolean describing if Bubble is for Cannon or not (map otherwise)
   */
  public Bubble(double x, double y, ColorChoice color, boolean forCannon) {
    this.x = x;
    this.y = y;
    this.xSpeed = 0;
    this.ySpeed = 0;
    this.color = color;
    this.state = forCannon ? State.NEW : State.LANDED;
    this.boundingBox = new Circle((float) (x+Bubble.DIAMETER/2), (float) (y+Bubble.DIAMETER/2), (float) Bubble.DIAMETER/2);

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
  }

/**
   * draws the Bubble
   *
   * @param g  Java Graphics instance
   */
  public void draw(Graphics g) {
    g.setColor(this.drawColor);
    g.fillOval((int) this.x, (int) this.y, (int) Bubble.DIAMETER, (int) Bubble.DIAMETER);
  }

  /**
   * updates the Bubble's position per game tick
   */
  public void move() {
    double nextX = this.getX() + this.getXSpeed();
    double nextY = this.getY() + this.getYSpeed();

    switch(this.state) {
      case NEW:
        break;
      case LANDED:
        break;
      case FIRING:
        this.setX(nextX);
        this.setY(nextY);
        break;
      case POPPING:
        break;
      case DROPPING:
        this.setX(nextX);
        this.setY(nextY);
        break;
    }
  }

    /**
     * stops the Bubble, adjusts bubble's position
     *
     * @param landed at adjusted x position
     * @param landed at adjusted y position
     */
    public void land(double x, double y) {
        this.setState(State.LANDED);
        this.setXSpeed(0);
        this.setYSpeed(0);

        App.game.log.log("Adjusting bubble land position to: (" + (int) x + " ; " + (int) y + ") from: (" + (int) this.getX() + " ; " + (int) this.getY() + ")");
        this.setX(x);
        this.setY(y);
    }

  /**
   * wall collision detection, inverts ball's xSpeed
   */
  public void hitWall() {
    if (this.getState() == State.FIRING) {
      this.setXSpeed(-this.getXSpeed());
    }
  }

  /**
   * fire the Bubble
   */
  public void fire(int angle) {
    this.setState(State.FIRING);
    this.setXSpeed(Math.cos(Math.toRadians(angle + 90)) * Bubble.SPEED);
    this.setYSpeed(-Math.sin(Math.toRadians(angle + 90)) * Bubble.SPEED);
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
    this.boundingBox.setX((float) x);
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
    this.boundingBox.setY((float) y);
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
    return this.ySpeed;
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

  /**
   * returns boundingBox
   */
  public Circle getBoundingBox() {
    return this.boundingBox;
  }

  /**
   * Creates a bubble object with a random color chosen from the list of colors.
   * @param x position of the bubble
   * @param y position of the bubble
   * @param colors list of possible bubble colors
   * @param forCannon
   * @return
   */
  public static Bubble randomColor(double x, double y, LinkedList<ColorChoice> colors, boolean forCannon) {
	  if(colors.size() == 0) return null;
	  ColorChoice color = colors.get((int) Math.floor(Math.random() * colors.size()));

	  return new Bubble(x, y, color, forCannon);
  }
}
