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


package bust_a_move.bubble.powerup;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import bust_a_move.App;
import bust_a_move.bubble.Bubble;


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
    public RowBomb(Bubble bubble) {
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
        g.drawString("RB", (int) this.getX() + 7, (int) this.getY() + 7);
    }

    /**
     * Pops subsequent PowerUp decorators and the root bubble.
     * Pops respective row.
     */
    public final void pop() {
        this.bubble.pop();
        App.getGame().getArena().getCollision().popRowBomb(this);
    }
}
