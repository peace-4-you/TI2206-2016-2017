/*
 * File: FixCannon.java
 *
 * Class: FixCannon
 *
 * Version: 0.0.1
 *
 * Date: September 29th, 2016
 *
 */


package nl.tudelft.bust_a_move20162017.bust_a_move_framework.bubble;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;


/**
 * FixCannon is a PowerUp that restricts the Cannon to 3 firing
 * positions.
 *
 * @author Calvin Nhieu
 *
 */
public class FixCannon extends PowerUp {
  /**
   * Creates FixCannon instance.
   * @param bubble  wrapped Bubble component.
   */
  public FixCannon(Bubble bubble) {
    super(bubble);
  }

  /**
   * Draws the PowerUp'd Bubble.
   * @param g  Java Graphics instance
   */
  public final void draw(final Graphics g) {
    this.bubble.draw(g);
    g.setColor(Color.black);
    g.drawString("FC", (int) this.getX(), (int) this.getY());
  }

  /**
   * Pops subsequent PowerUp decorators and the root bubble.
   * Temporarily restricts the Cannon to 3 firing positions.
   */
  public final void pop() {
    this.bubble.pop();
  }
}
