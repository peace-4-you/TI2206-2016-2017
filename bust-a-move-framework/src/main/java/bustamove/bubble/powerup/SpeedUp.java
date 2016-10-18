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


package bustamove.bubble.powerup;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import bustamove.bubble.AbstractBubble;
import bustamove.gamestate.GameConfig;


/**
 * SpeedUp is a PowerUp that speeds up AbstractBubbles fired from the Cannon.
 *
 * @author Calvin Nhieu
 */
public class SpeedUp extends PowerUp {
    /**
     * Creates SpeedUp instance.
     *
     * @param bubble wrapped AbstractBubble component.
     */
    public SpeedUp(final AbstractBubble bubble) {
        super(bubble);
    }

    /**
     * Draws the PowerUp'd AbstractBubble.
     *
     * @param g Java Graphics instance
     */
    public final void draw(final Graphics g) {
        this.bubble.draw(g);
        g.setColor(Color.black);
        g.drawString("+", this.getX() + getpowerupOffset(),
                this.getY() + getpowerupOffset());
    }

    /**
     * Pops subsequent PowerUp decorators and the root bubble.
     * Increases Cannon firing speed.
     */
    public final void pop() {
        this.bubble.pop();
        if (getGameHead().getBubbleSpeed() < GameConfig.MAX_BUBBLE_SPEED) {
            getGameHead().setBubbleSpeed(getGameHead().getBubbleSpeed()
                    + GameConfig.BUBBLE_SPEEDUP);
        }
    }
}
