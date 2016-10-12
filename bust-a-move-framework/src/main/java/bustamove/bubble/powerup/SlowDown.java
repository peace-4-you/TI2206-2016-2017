/*
 * File: SlowDown.java
 *
 * Class: SlowDown
 *
 * Version: 0.0.1
 *
 * Date: September 29th, 2016
 *
 */


package bustamove.bubble.powerup;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import bustamove.bubble.Bubble;
import bustamove.gamestate.GameConfig;


/**
 * SlowDown is a PowerUp that slows down Bubbles fired from the Cannon.
 *
 * @author Calvin Nhieu
 */
public class SlowDown extends PowerUp {
    /**
     * Creates SlowDown instance.
     *
     * @param bubble wrapped Bubble component.
     */
    public SlowDown(final Bubble bubble) {
        super(bubble);
    }

    /**
     * Draws the PowerUp'd Bubble.
     *
     * @param g Java Graphics instance
     */
    public final void draw(final Graphics g) {
        this.bubble.draw(g);
        g.setColor(Color.black);
        g.drawString("-", this.getX() + getpowerupOffset(),
                this.getY() + getpowerupOffset());
    }

    /**
     * Pops subsequent PowerUp decorators and the root bubble.
     * Decreases Cannon firing speed.
     */
    public final void pop() {
        this.bubble.pop();
      if (gamehead.getBubbleSpeed() > GameConfig.MIN_BUBBLE_SPEED) {
            gamehead.setBubbleSpeed(gamehead.getBubbleSpeed()
                    - GameConfig.BUBBLE_SPEEDUP);
      }
    }
}
