/*
 * File: BubbleGenerator.java
 *
 * Class: BubbleGenerator
 *
 * Version: 0.0.1
 *
 * Date: September 7th, 2016
 *
 */

package nl.tudelft.bust_a_move20162017.bust_a_move_framework.bubble;

import nl.tudelft.bust_a_move20162017.bust_a_move_framework.bubble.BubbleChoice;
/**
 * The BubbleGenerator class is an interface to create Bubble instances
 *
 * @author Calvin Nhieu
 *
 */
public class BubbleGenerator {
  private Arena arena;

  /**
   * Creates BubbleGenerator instance
   *
   * @param Arena instance
   *
   */
  public BubbleGenerator(Arena arena) {
    this.arena = arena;
  }

  /**
   * @return Bubble instance with supplied Bubble.ColorChoice
   *
   * @param x  double value for starting x position
   * @param y  double value for starting y position
   * @param color  ColorChoice enum value for bubble's color
   */
  public Bubble spawn(double x, double y, Bubble.ColorChoice color) {
    return new Bubble(x, y, color);
  }
  /**
   * @return Bubble instance of random color
   *
   * @param x  double value for starting x position
   * @param y  double value for starting y position
   * @param forCannon  boolean value true if Bubble is for cannon, else false
   */
  public Bubble spawn(double x, double y, boolean forCannon) {
    Bubble.ColorChoice color;

    if (forCannon) {
      // TODO: determine colors from arena
      double r = Math.random();
      if (r < 0.25) {
        color = Bubble.ColorChoice.RED;
      } else if (r < 0.5) {
        color = Bubble.ColorChoice.BLUE;
      } else if (r < 0.75) {
        color = Bubble.ColorChoice.YELLOW;
      } else if (r < 1) {
        color = Bubble.ColorChoice.GREEN;
      }
    } else {
      double r = Math.random();
      if (r < 0.25) {
        color = Bubble.ColorChoice.RED;
      } else if (r < 0.5) {
        color = Bubble.ColorChoice.BLUE;
      } else if (r < 0.75) {
        color = Bubble.ColorChoice.YELLOW;
      } else if (r < 1) {
        color = Bubble.ColorChoice.GREEN;
      }
    }

    return new Bubble(x, y, color);
  }

}
