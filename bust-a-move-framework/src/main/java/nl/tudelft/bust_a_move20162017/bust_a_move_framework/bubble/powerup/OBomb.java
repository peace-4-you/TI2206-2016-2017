/*
 * File: OBomb.java
 *
 * Class: OBomb
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
 * OBomb is a PowerUp that pops its neighbors in an 'O' shape on pop.
 *
 * @author Calvin Nhieu
 *
 */
public class OBomb extends PowerUp {
  /**
   * Creates OBomb instance.
   * @param bubble  wrapped Bubble component.
   */
  public OBomb(Bubble bubble) {
    super(bubble);
  }

  /**
   * Draws the PowerUp'd Bubble.
   * @param g  Java Graphics instance
   */
  public final void draw(final Graphics g) {
    this.bubble.draw(g);
    g.setColor(Color.black);
    g.drawString("OB", (int) this.getX(), (int) this.getY());
  }

  /**
   * Pops subsequent PowerUp decorators and the root bubble.
   * Pops neighbors.
   */
  public final void pop() {
    this.bubble.pop();
  }
}
