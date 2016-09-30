/*
 * File: SpeedUp.java
 *
 * Class: SpeedUp
 *
 * Version: 0.0.1
 *
 * Date: September 29th, 2016
 *
 */


package bust_a_move.bubble.powerup;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import bust_a_move.bubble.Bubble;
import bust_a_move.gamestate.GameConfig;


/**
 * SpeedUp is a PowerUp that speeds up Bubbles fired from the Cannon.
 *
 * @author Calvin Nhieu
 *
 */
public class SpeedUp extends PowerUp {
  /**
   * Creates SpeedUp instance.
   * @param bubble  wrapped Bubble component.
   */
  public SpeedUp(Bubble bubble) {
    super(bubble);
  }

  /**
   * Draws the PowerUp'd Bubble.
   * @param g  Java Graphics instance
   */
  public final void draw(final Graphics g) {
    this.bubble.draw(g);
    g.setColor(Color.black);
    g.drawString("+", (int) this.getX() + 7, (int) this.getY() + 7);
  }

  /**
   * Pops subsequent PowerUp decorators and the root bubble.
   * Increases Cannon firing speed.
   */
  public final void pop() {
    this.bubble.pop();
    if (Bubble.SPEED < GameConfig.MAX_BUBBLE_SPEED) {
      Bubble.setSPEED(Bubble.SPEED + GameConfig.BUBBLE_SPEEDUP);
    }
  }
}
