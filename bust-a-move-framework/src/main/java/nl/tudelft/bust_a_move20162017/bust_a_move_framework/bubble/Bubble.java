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

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Circle;

import nl.tudelft.bust_a_move20162017.bust_a_move_framework.App;

/**
 * The Bubble class represents a single bubble entity.
 *
 * @author Calvin Nhieu
 *
 */
public class Bubble {
  /**
   * Diameter of a bubble.
   */
  public static final double DIAMETER = 35;
  /**
   * Standard speed of a bubble.
   */
  public static final double SPEED = 3;
  /**
   * Red color constant.
   */
  private static final Color RED_COLOR = Color.red;
  /**
   * Blue color constant.
   */
  private static final Color BLUE_COLOR = Color.blue;
  /**
   * Yellow color constant.
   */
  private static final Color YELLOW_COLOR = Color.yellow;
  /**
   * Green color constant.
   */
  private static final Color GREEN_COLOR = Color.green;
  /**
   * Angle offset constant.
   */
  private static final int ANGLE_OFFSET = 90;
  /**
   * X coordinate of the bubble. This is the top left of the bubble.
   */
  private double x;
  /**
   * Y coordinate of the bubble. This is the top left of the bubble.
   */
  private double y;
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
   * Enum of all the values a State variable can take.
   */
  public enum State {
    /**
     * Bubble is newly created.
     */
    NEW,
    /**
     * Bubble is saved in the bubble storage.
     */
    LANDED,
    /**
     * Bubble is traveling across the arena.
     */
    FIRING,
    /**
     * Bubble is popping.
     */
    POPPING,
    /**
     * Bubble is dropping.
     */
    DROPPING
  }

  /**
   * Enum of all the values a Colorchoice variable can take.
   */
  public enum ColorChoice {
    /**
     * Red color.
     */
    RED,
    /**
     * Blue color.
     */
    BLUE,
    /**
     * Yellow color.
     */
    YELLOW,
    /**
     * Green color.
     */
    GREEN
  }

  /**
   * Creates Bubble instance.
   * @param xPos  double value for starting x position
   * @param yPos  double value for starting y position
   * @param c  ColorChoice enum value for bubble's color
   * @param forCannon  boolean describing if Bubble is for
   *                   Cannon or not (map otherwise)
   */
  public Bubble(final double xPos, final double yPos, final ColorChoice c,
                final boolean forCannon) {
    this.x = xPos;
    this.y = yPos;
    this.xSpeed = 0;
    this.ySpeed = 0;
    this.color = c;
    if (forCannon) {
      this.state = State.NEW;
    } else {
      this.state = State.LANDED;
    }
    this.boundingBox = new Circle((float) (x + Bubble.DIAMETER / 2),
            (float) (y + Bubble.DIAMETER / 2), (float) Bubble.DIAMETER / 2);

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
        //TODO: throw exception?
        break;
    }
  }

  /**
   * Draws the Bubble.
   * @param g  Java Graphics instance
   */
  // Might have abstract or final tag.
  public final void draw(final Graphics g) {
    g.setColor(this.drawColor);
    g.fillOval((int) this.x, (int) this.y, (int) Bubble.DIAMETER,
            (int) Bubble.DIAMETER);
  }

  /**
   * Updates the Bubble's position per game tick.
   */
  public final void move() {
    double nextX = this.getX() + this.getXSpeed();
    double nextY = this.getY() + this.getYSpeed();

    switch (this.state) {
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
      default:
        //TODO: throw exception?
        break;
    }
  }

  /**
   * Stops the Bubble, adjusts bubble's position.
   *
   * @param xPos at adjusted x position
   * @param yPos at adjusted y position
   */
  public final void land(final double xPos, final double yPos) {
    this.setState(State.LANDED);
    this.setXSpeed(0);
    this.setYSpeed(0);
    App.getGame().log.log("Adjusting bubble land position to: (" + (int) x
            + " ; " + (int) y + ") from: (" + (int) this.getX() + " ; "
            + (int) this.getY() + ")");
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
   * Fires the Bubble by setting the state, xSpeed and ySpeed.
   * @param angle the angle at which the bubble is fired at
   */
  public final void fire(final int angle) {
    this.setState(State.FIRING);
    this.setXSpeed(Math.cos(Math.toRadians(angle + ANGLE_OFFSET))
            * Bubble.SPEED);
    this.setYSpeed(-Math.sin(Math.toRadians(angle + ANGLE_OFFSET))
            * Bubble.SPEED);
  }

  /**
   * Pops the Bubble by setting state to POPPING and setting xSpeed
   * and ySpeed to zero.
   */
  public final void pop() {
    this.setState(State.POPPING);
    this.setXSpeed(0);
    this.setYSpeed(0);
  }

  /**
   * Drops the Bubble by setting state to DROPPING and setting xSpeed
   * to zero and ySpeed to SPEED.
   */
  public final void drop() {
    this.setState(State.DROPPING);
    this.setXSpeed(0);
    this.setYSpeed(Bubble.SPEED);
  }

  /**
   * Getter method: for X coordinate of the bubble.
   * @return x value
   */
  public final double getX() {
    return this.x;
  }
  /**
   * Setter method: for the X ooordinate of the bubble.
   * @param xPos  double value to set x to
   */
  public final void setX(final double xPos) {
    this.x = xPos;
    this.boundingBox.setX((float) xPos);
  }

  /**
   * Getter method: for the Y coordinate of the bubble.
   * @return y value
   */
  public final double getY() {
    return this.y;
  }
  /**
   * Setter method: for the Y coordinate of the bubble.
   * @param yPos  double value to set y to
   */
  public final void setY(final double yPos) {
    this.y = yPos;
    this.boundingBox.setY((float) yPos);
  }

  /**
   * Getter method: for the xSpeed value.
   * @return xSpeed value
   */
  public final double getXSpeed() {
    return this.xSpeed;
  }
  /**
   * Setter method: for the xSpeed value.
   * @param xSPEED  double value to set xSpeed to
   */
  public final void setXSpeed(final double xSPEED) {
    this.xSpeed = xSPEED;
  }

  /**
   * Getter method: for the ySpeed value.
   * @return  ySpeed value
   */
  public final double getYSpeed() {
    return this.ySpeed;
  }
  /**
   * Setter method: for the ySpeed value.
   * @param ySPEED  double value to set ySpeed to
   */
  public final void setYSpeed(final double ySPEED) {
    this.ySpeed = ySPEED;
  }

  /**
   * Getter method: for the Color value.
   * @return color value
   */
  public final ColorChoice getColor() {
    return this.color;
  }

  /**
   * Getter method: for the state.
   * @return state value
   */
  public final State getState() {
    return this.state;
  }
  /**
   * Setter method: for the state.
   * @param newState  State enum value to set state to
   */
  public final void setState(final State newState) {
    this.state = newState;
  }

  /**
   * Getter method: for the boundingBox.
   * @return circle object
   */
  public final Circle getBoundingBox() {
    return this.boundingBox;
  }
  /**
   * Creates a bubble object with a random color chosen from the list of colors.
   * @param x X coordinate of the new bubble
   * @param y Y coordinate of the new bubble
   * @param colors list of possible bubble colors
   * @param forCannon boolean describing if Bubble is for Cannon or not
   *                  (map otherwise)
   * @return  newly created bubble
   */
  public static Bubble randomColor(final double x, final double y,
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