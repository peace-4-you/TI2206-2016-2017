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
   * @return BubbleModel instance with supplied BubbleModel.ColorChoice
   *
   * @param x  double value for starting x position
   * @param y  double value for starting y position
   * @param color  ColorChoice enum value for bubble's color
   */
  public BubbleModel spawn(double x, double y, BubbleModel.ColorChoice color) {
    return new BubbleModel(x, y, color);
  }
  /**
   * @return BubbleModel instance of random color
   *
   * @param x  double value for starting x position
   * @param y  double value for starting y position
   * @param forCannon  boolean value true if Bubble is for cannon, else false
   */
  public BubbleModel spawn(double x, double y, boolean forCannon) {
    BubbleModel.ColorChoice color;

    if (forCannon) {
      // TODO: determine colors from arena
      double r = Math.random();
      if (r < 0.25) {
        color = BubbleModel.ColorChoice.RED;
      } else if (r < 0.5) {
        color = BubbleModel.ColorChoice.BLUE;
      } else if (r < 0.75) {
        color = BubbleModel.ColorChoice.YELLOW;
      } else if (r < 1) {
        color = BubbleModel.ColorChoice.GREEN;
      }
    } else {
      double r = Math.random();
      if (r < 0.25) {
        color = BubbleModel.ColorChoice.RED;
      } else if (r < 0.5) {
        color = BubbleModel.ColorChoice.BLUE;
      } else if (r < 0.75) {
        color = BubbleModel.ColorChoice.YELLOW;
      } else if (r < 1) {
        color = BubbleModel.ColorChoice.GREEN;
      }
    }

    return new BubbleModel(x, y, color);
  }

}
