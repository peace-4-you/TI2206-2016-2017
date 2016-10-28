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


package bustamove.bubble.powerup;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import bustamove.bubble.Bubble;
import bustamove.game.Cannon;
import bustamove.screen.config.GameConfig;

/**
 * ShortScope is a PowerUp that shortens the Cannon's scope.
 *
 * @author Calvin Nhieu
 */
public class ShortScope extends PowerUp {
    /**
     * Creates ShortScope instance.
     *
     * @param bubble wrapped Bubble component.
     */
    public ShortScope(final Bubble bubble) {
        super(bubble);
    }

    /**
     * Draws the PowerUp'd Bubble.
     *
     * @param g Java Graphics instance
     */
    public final void draw(final Graphics g) {
        getBubble().draw(g);
        g.setColor(Color.black);
        g.drawString("-C", this.getX() + getpowerupOffset(),
                this.getY() + getpowerupOffset());
    }

    /**
     * Pops subsequent PowerUp decorators and the root bubble.
     * Decreases Cannon scope.
     */
    public final void pop() {
        Cannon cannon = this.getGameHead().getCannon();
        int length = cannon.getLength();

        getBubble().pop();
        if (length > GameConfig.MIN_CANNON_LENGTH) {
            cannon.setLength(length - GameConfig.CANNON_LENGTH_INCREMENT);
        }
    }
}
