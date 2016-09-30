/*
 * File: LongScope.java
 *
 * Class: LongScope
 *
 * Version: 0.0.1
 *
 * Date: September 30th, 2016
 *
 */


package nl.tudelft.bust_a_move20162017.bust_a_move_framework.bubble;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import nl.tudelft.bust_a_move20162017.bust_a_move_framework.App;
import nl.tudelft.bust_a_move20162017.bust_a_move_framework.cannon.Cannon;
import nl.tudelft.bust_a_move20162017.bust_a_move_framework.gamestate.GameConfig;

/**
 * LongScope is a PowerUp that lengthens the Cannon's scope.
 *
 * @author Calvin Nhieu
 *
 */
public class LongScope extends PowerUp {
  /**
   * Creates LongScope instance.
   * @param bubble  wrapped Bubble component.
   */
  public LongScope(Bubble bubble) {
    super(bubble);
  }

  /**
   * Draws the PowerUp'd Bubble.
   * @param g  Java Graphics instance
   */
  public final void draw(final Graphics g) {
    this.bubble.draw(g);
    g.setColor(Color.black);
    g.drawString("+C", (int) this.getX() + 7, (int) this.getY() + 7);
  }

  /**
   * Pops subsequent PowerUp decorators and the root bubble.
   * Increases Cannon scope.
   */
  public final void pop() {
    Cannon cannon = App.getGame().cannon;
    int length = cannon.getLength();

    this.bubble.pop();
    if (length < GameConfig.MAX_CANNON_LENGTH) {
      cannon.setLength(length + GameConfig.CANNON_LENGTH_INCREMENT);
    }
  }
}
