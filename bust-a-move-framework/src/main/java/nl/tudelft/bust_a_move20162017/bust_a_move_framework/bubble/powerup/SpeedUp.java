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


package nl.tudelft.bust_a_move20162017.bust_a_move_framework.bubble;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import nl.tudelft.bust_a_move20162017.bust_a_move_framework.gamestate.GameConfig;

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
    g.drawString("+", (int) this.getX()+7, (int) this.getY()+7);
  }

  /**
   * Pops subsequent PowerUp decorators and the root bubble.
   * Increases Cannon firing speed.
   */
  public final void pop() {
    this.bubble.pop();
    Bubble.setSPEED(Bubble.SPEED*GameConfig.BUBBLE_SPEEDUP);
  }
}
