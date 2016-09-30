/*
 * File: ShortScope.java
 *
 * Class: ShortScope
 *
 * Version: 0.0.1
 *
 * Date: September 30th, 2016
 *
 */


package bust_a_move.bubble.powerup;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import bust_a_move.App;
import bust_a_move.bubble.Bubble;
import bust_a_move.game.Cannon;
import bust_a_move.gamestate.GameConfig;

/**
 * ShortScope is a PowerUp that shortens the Cannon's scope.
 *
 * @author Calvin Nhieu
 *
 */
public class ShortScope extends PowerUp {
  /**
   * Creates ShortScope instance.
   * @param bubble  wrapped Bubble component.
   */
  public ShortScope(Bubble bubble) {
    super(bubble);
  }

  /**
   * Draws the PowerUp'd Bubble.
   * @param g  Java Graphics instance
   */
  public final void draw(final Graphics g) {
    this.bubble.draw(g);
    g.setColor(Color.black);
    g.drawString("-C", (int) this.getX() + 7, (int) this.getY() + 7);
  }

  /**
   * Pops subsequent PowerUp decorators and the root bubble.
   * Decreases Cannon scope.
   */
  public final void pop() {
    Cannon cannon = App.getGame().cannon;
    int length = cannon.getLength();

    this.bubble.pop();
    if (length > GameConfig.MIN_CANNON_LENGTH) {
      cannon.setLength(length - GameConfig.CANNON_LENGTH_INCREMENT);
    }
  }
}
