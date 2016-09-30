/*
 * File: RowBomb.java
 *
 * Class: RowBomb
 *
 * Version: 0.0.1
 *
 * Date: September 28th, 2016
 *
 */


package nl.tudelft.bust_a_move20162017.bust_a_move_framework.bubble;

import nl.tudelft.bust_a_move20162017.bust_a_move_framework.App;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;


/**
 * RowBomb is a PowerUp that pops its respective row on pop.
 *
 * @author Calvin Nhieu
 *
 */
public class RowBomb extends PowerUp {
  /**
   * Creates RowBomb instance.
   * @param bubble  wrapped Bubble component.
   */
  public RowBomb(Bubble bubble) {
    super(bubble);
  }

  /**
   * Draws the PowerUp'd Bubble.
   * @param g  Java Graphics instance
   */
  public final void draw(final Graphics g) {
    this.bubble.draw(g);
    g.setColor(Color.black);
    g.drawString("RB", (int) this.getX() + 7, (int) this.getY() + 7);
  }

  /**
   * Pops subsequent PowerUp decorators and the root bubble.
   * Pops respective row.
   */
  public final void pop() {
    this.bubble.pop();
    App.getGame().getArena().popRowBomb(this);
  }
}
