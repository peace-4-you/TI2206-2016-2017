
/*
 * File: cannonmodel.java
 * Class: CannonModel
 *
 * Version: 0.0.1
 * Date: September 8th, 2016
 */

package nl.tudelft.bust_a_move20162017.bust_a_move_framework.cannonview;

import nl.tudelft.bust_a_move20162017.bust_a_move_framework.bubblemodel.BubbleModel;
import java.awt.Color;
import java.awt.Graphics;
import java.util.Observable;
import java.util.Observer;



public class CannonView implements Observer {

	private int X;
	private int Y;
	private int SIZE;
	private Color cannonColour;

	private int ANGLE;
	private BubbleModel currBubble;
	private BubbleModel nextBubble;

	/**
	 * Creates Cannon instance
	 *
	 * @param X
	 *            int value for starting x position
	 * @param Y
	 *            int value for starting y position
	 * @param SIZE
	 *            int value for cannons length
	 * @param cannonColour
	 *            Color value for cannons colour
	 */

	public CannonView() {
		this.X = 320;
		this.Y = 20;
		this.SIZE = 20;
		this.cannonColour = new Color(0x000000);
	}

	/**
	 * draws the Cannon
	 * 
	 * @param g
	 *            Java Graphics instance
	 */

	public void draw(Graphics g) {
		g.setColor(this.cannonColour);
		// TODO Make a nicer cannon
		g.drawLine(this.X, this.Y, (int) (this.X + Math.cos(this.ANGLE) * this.SIZE),
				(int) (this.X + Math.sin(this.ANGLE) * this.SIZE));
		g.setColor(nextBubble.drawColor);
		g.fillOval(100, 10, (int) BubbleModel.DIAMETER);
		g.setColor(currBubble.drawColor);
		g.fillOval(200, 10, (int) BubbleModel.DIAMETER);
	}


	/**
	   *
	   * @param o  changed Observable
	   */

	public void update(Observable o, Object arg) {
		this.ANGLE = o.ANGLE;
		this.currBubble = o.currBubble;
		this.nextBubble = o.nextBubble;	
	}
}
