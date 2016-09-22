/*
 * File: cannon.java
 * Class: Cannon
 *
 * Version: 0.0.3
 * Date: September 12th, 2016
 */

package nl.tudelft.bust_a_move20162017.bust_a_move_framework.cannon;

import nl.tudelft.bust_a_move20162017.bust_a_move_framework.App;
import nl.tudelft.bust_a_move20162017.bust_a_move_framework.bubble.Bubble;
import nl.tudelft.bust_a_move20162017.bust_a_move_framework.bubble.BubbleFactory;
import nl.tudelft.bust_a_move20162017.bust_a_move_framework.game.Game;
import nl.tudelft.bust_a_move20162017.bust_a_move_framework.gamestate.Button;

import java.util.LinkedList;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

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
	private BubbleFactory bubblegen;

	private int TIME_SHOT_FIRED;
	private int TIME_PASSED_KEY_RIGHT;
	private int TIME_PASSED_KEY_LEFT;
	private static final int TIME_TO_SHOOT = 5000;
	private static final int TIME_DISPLAY_FIRE_WARNING = 1500;
	private boolean display_warning;

	/**
	 * Creates Cannon instance
	 */

	public Cannon(Game game) {
		game.log.log(this, "Cannon initialised");
		this.X = 320;
		this.Y = 530;
		this.Xlaunch = (int) (375-Bubble.DIAMETER/2);
		this.Ylaunch = (int) (555-Bubble.DIAMETER/2);
		this.Xload =  (int) (320-Bubble.DIAMETER/2);
		this.Yload = (int) (530-Bubble.DIAMETER/2);
		this.SIZE = 80;
		this.ANGLE = 0;
		this.cannonColour = Color.red;
		this.game = game;
		this.ANGLE = 0;
		this.bubblegen = game.getBubbleGen();
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
		game.log.log(this,"Next bubble loaded to cannon");
		Bubble nextBubble = this.bubblegen.create((double) this.Xlaunch, (double) this.Ylaunch, true);
		return nextBubble;
	}

	/**
	 * Fires current bubble and loads next bubble
	 */

	public void fire() {
		game.log.log(this, "Cannon fired a bubble");
		this.currBubble.fire(this.ANGLE);
		this.loadNextBubble();
		this.TIME_SHOT_FIRED = 0;
		this.display_warning = false;
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
	 * update the cannon the Cannon
	 */
	
	public void update(GameContainer container, int delta) {
		TIME_SHOT_FIRED += delta;

		if (container.getInput().isKeyDown(Input.KEY_RIGHT)) {
			if (container.getInput().isKeyPressed(Input.KEY_RIGHT)){
				game.log.log(this, "Cannon moving to the right");
			}
			this.TIME_PASSED_KEY_RIGHT += delta;
			if (this.TIME_PASSED_KEY_RIGHT > 10) {
				stepDown();
				this.TIME_PASSED_KEY_RIGHT = 0;
			}
		} else {
			this.TIME_PASSED_KEY_RIGHT = 0;
		}
		if (container.getInput().isKeyDown(Input.KEY_LEFT)) {
			if (container.getInput().isKeyPressed(Input.KEY_LEFT)){
				game.log.log(this, "Cannon moving to the left");
			}
			this.TIME_PASSED_KEY_LEFT += delta;
			if (this.TIME_PASSED_KEY_LEFT > 10) {
				stepUp();
				this.TIME_PASSED_KEY_LEFT = 0;
			}
		} else {
			this.TIME_PASSED_KEY_LEFT = 0;
		}
		if (container.getInput().isKeyPressed(Input.KEY_UP)) {
			fire();
		}

		if(this.TIME_SHOT_FIRED > TIME_TO_SHOOT - TIME_DISPLAY_FIRE_WARNING) {
			display_warning = true;
		}

		if (this.TIME_SHOT_FIRED > TIME_TO_SHOOT) {
			game.log.log(this, "Time elapsed, shooting automatically");
			fire();
			TIME_SHOT_FIRED = 0;
			display_warning = false;
		}
	}

	public void draw(Graphics g) {
		g.setColor(this.cannonColour);
		// TODO Make a nicer cannon
		g.drawLine(this.X, this.Y, (int) (this.X + Math.cos(Math.toRadians(this.ANGLE + 90)) * this.SIZE),
				(int) (this.Y - Math.sin(Math.toRadians(this.ANGLE + 90)) * this.SIZE));
		if(display_warning) {
			g.drawString("Hurry up!", 225, 500);
		}
	}
}
