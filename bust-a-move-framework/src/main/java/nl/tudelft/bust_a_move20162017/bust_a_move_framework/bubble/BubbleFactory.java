/*
 * File: BubbleFactory.java
 *
 * Class: BubbleFactory
 *
 * Version: 0.0.3
 *
 * Date: September 13th, 2016
 *
 */

package nl.tudelft.bust_a_move20162017.bust_a_move_framework.bubble;

import nl.tudelft.bust_a_move20162017.bust_a_move_framework.arena.Arena;
import java.util.LinkedList;
/**
 * The BubbleFactory class is an interface to create Bubble instances
 *
 * @author Calvin Nhieu
 *
 */
public class BubbleFactory {
  private Arena arena;

  /**
   * Creates BubbleFactory instance
   *
   * @param Arena instance
   *
   */
  public BubbleFactory(Arena arena) {
    this.arena = arena;
  }

  /**
   * @return Bubble instance with supplied Bubble.ColorChoice
   *
   * @param x  double value for starting x position
   * @param y  double value for starting y position
   * @param color  ColorChoice enum value for bubble's color
   */
  public Bubble create(double x, double y, Bubble.ColorChoice color) {
    return new Bubble(x, y, color);
  }
  /**
   * @return Bubble instance of random color
   *
   * @param x  double value for starting x position
   * @param y  double value for starting y position
   * @param forCannon  boolean value true if Bubble is for cannon, else false
   */
  public Bubble create(double x, double y, boolean forCannon) {
    Bubble.ColorChoice color = null;

    if (forCannon) {
      // uncomment when ready arena is ready
      // LinkedList<Bubble.ColorChoice> colors = arena.getColorsOnArena();
      // double interval = 1/colors.size();
      // double r = Math.random();
      //
      // for (int i = 1; i < colors.size(); i++) {
      //   if (r < i*interval) {
      //     color = colors.at(i-1);
      //     break;
      //   }
      // }

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
