/*
 * File: cannon.java
 * Class: Cannon
 *
 * Version: 0.0.1
 * Date: September 8th, 2016
 */

package nl.tudelft.bust_a_move20162017.bust_a_move_framework.cannon;

import nl.tudelft.bust_a_move20162017.bust_a_move_framework.bubble.Bubble;

import java.awt.Color;
import java.awt.Graphics;

/**
 * The Cannon class represents a cannon entity.
 *
 * @author Maurice Willemsen
 */

public class Cannon {

	private int ANGLE;

	private Bubble.ColorChoice currBubbleColour;

	private Bubble.ColorChoice nextBubbleColour;

	private int X;

	private int Y;

	private int SIZE;

	private Color cannonColour;
	

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

	public void main() {
		this.X = 320;
		this.Y = 20;
		this.SIZE = 20;
		this.cannonColour = new Color(0x000000);

		this.ANGLE = 0;
		this.nextBubbleColour = getNextColour();
		this.loadNextBubble();
	}

	/**
	 * Load next bubble to current bubble and asks new colour.
	 */

	private void loadNextBubble() {
		this.currBubbleColour = this.nextBubbleColour;
		this.nextBubbleColour = this.getNextColour();
	}

	/**
	 * Determines next colour
	 */

	private Bubble.ColorChoice getNextColour() {
		// TODO Needs to refer to bubble checking function
		Bubble.ColorChoice nextColor = Bubble.ColorChoice.BLUE;
		return nextColor;
	}

	/**
	 * create a new bubble object and loads next bubble
	 */

	public void fire() {
		// TODO Create new Bubble object
		this.loadNextBubble();

	}

	/**
	 * updates the Cannon angle per inputstep
	 */
	public void stepUp() {
		if (this.ANGLE <= 60) {
			this.ANGLE++;
		}
	}

	/**
	 * updates the Cannon angle per inputstep
	 */

	public void stepDown() {
		if (this.ANGLE >= 60) {
			this.ANGLE--;
		}
	}

	/**
	 * draws the Cannon
	 * 
	 * @param g
	 *            Java Graphics instance
	 */

	public void drawCannon(Graphics g) {
		g.setColor(this.cannonColour);
		// TODO Make a nicer cannon
		g.drawLine(this.X, this.Y, (int) (this.X + Math.cos(this.ANGLE) * this.SIZE),
				(int) (this.X + Math.sin(this.ANGLE) * this.SIZE));
	}

	/**
	 * draws the Next Bubbles
	 * 
	 * @param g
	 *            Java Graphics instance
	 */

	public void drawNextBubbles(Graphics g) {
		// TODO Draw the current and next bubble in the bottom of the screen.
	}
}
