/*
 * File: cannon.java
 * Class: Cannon
 *
 * Version: 0.0.3
 * Date: September 12th, 2016
 */

package nl.tudelft.bust_a_move20162017.bust_a_move_framework.cannon;

import nl.tudelft.bust_a_move20162017.bust_a_move_framework.bubble.Bubble;
import nl.tudelft.bust_a_move20162017.bust_a_move_framework.bubble.BubbleGenerator;
import nl.tudelft.bust_a_move20162017.bust_a_move_framework.game.Game;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

/**
 * The Cannon class represents a cannon entity.
 *
 * @author Maurice Willemsen
 */

public class Cannon {

	private int X;
	private int Y;
	private int Xlaunch;
	private int Ylaunch;
	private int Xload;
	private int Yload;
	private int SIZE;
	private Color cannonColour;

	private int ANGLE;
	private Bubble currBubble;
	private Bubble nextBubble;
	private Game game;

	public int TIME_SHOT_FIRED;

	/**
	 * Creates Cannon instance
	 */

	public Cannon(Game game) {
		this.X = 320;
		this.Y = 450;
		this.Xlaunch = 280;
		this.Ylaunch = 465;
		this.Xload = 320;
		this.Yload = 450;
		this.SIZE = 40;
		this.ANGLE = 0;
		this.cannonColour = Color.red;
		this.game = game;
		this.ANGLE = 0;
		this.nextBubble = getNextBubble();
		this.loadNextBubble();
		this.loadNextBubble();
	}

	/**
	 * Load next bubble to current bubble and asks for a new bubble.
	 */

	private void loadNextBubble() {
		this.currBubble = this.nextBubble;
		this.nextBubble = this.getNextBubble();
		this.currBubble.setX(this.Xload);
		this.currBubble.setY(this.Yload);
		this.game.bubbleslist.add(this.nextBubble);
	}

	/**
	 * Determines next bubble
	 */

	private Bubble getNextBubble() {
		BubbleGenerator nextBubbleGen = new BubbleGenerator();
		Bubble nextBubble = nextBubbleGen.spawn(this.Xlaunch, this.Ylaunch, 1);
		return nextBubble;
	}

	/**
	 * Fires current bubble and loads next bubble
	 */

	public void fire() {
		this.currBubble.fire(this.ANGLE);
		this.loadNextBubble();
		this.TIME_SHOT_FIRED = 0;
	}

	/**
	 * updates the Cannon angle per inputstep
	 */

	public void stepUp() {
		if (this.ANGLE <= 60) {
			this.ANGLE += 1;
		}
	}

	/**
	 * updates the Cannon angle per inputstep
	 */

	public void stepDown() {
		if (this.ANGLE >= -60) {
			this.ANGLE -= 1;
		}
	}

	/**
	 * draws the Cannon
	 * 
	 * 
	 * 
	 */

	public void draw(Graphics g) {
		g.setColor(this.cannonColour);
		// TODO Make a nicer cannon
		g.drawLine(this.X, this.Y, (int) (this.X + Math.cos(Math.toRadians(this.ANGLE + 90)) * this.SIZE),
				(int) (this.Y - Math.sin(Math.toRadians(this.ANGLE + 90)) * this.SIZE));
	}
}
