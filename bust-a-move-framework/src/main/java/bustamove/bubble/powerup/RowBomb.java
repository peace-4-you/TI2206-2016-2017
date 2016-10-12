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


package bustamove.bubble.powerup;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import bustamove.bubble.Bubble;

/**
 * RowBomb is a PowerUp that pops its respective row on pop.
 *
 * @author Calvin Nhieu
 */
public class RowBomb extends PowerUp {
    /**
     * Creates RowBomb instance.
     *
     * @param bubble wrapped Bubble component.
     */
    public RowBomb(final Bubble bubble) {
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
        g.drawString("RB", this.getX() + getpowerupOffset(),
                this.getY() + getpowerupOffset());
    }

    /**
     * Pops subsequent PowerUp decorators and the root bubble.
     * Pops respective row.
     */
    public final void pop() {
        this.bubble.pop();
        this.gamehead.getArena().getCollision().popRowBomb(this);
    }
}
