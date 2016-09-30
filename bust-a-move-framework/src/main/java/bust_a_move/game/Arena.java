/*
 * File: Arena.java
 *
 * Class: Arena
 *
 * Version: 0.0.1
 *
 * Date: September 12th 2016
 *
 */

package bust_a_move.game;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import bust_a_move.App;
import bust_a_move.bubble.Bubble;
import bust_a_move.bubble.BubbleStorage;
import bust_a_move.bubble.Collision;

/**
 * TODO: new description for functioning of this class The arena class maintains
 * a LinkedList of ArrayList data structure to store the bubble objects.
 *
 * @author Winer Bao
 */
public class Arena {

	/**
	 * X position of arena.
	 */
	private int xPos;
	/**
	 * Y position of arena.
	 */
	private int yPos;
	/**
	 * height of arena.
	 */
	private final int height;
	/**
	 * width of arena.
	 */
	private final int width;
	/**
	 * bubbleStorage class to store all bubbles.
	 */
	private BubbleStorage bubbleStorage;
	/**
	 * TODO Check. Temporarily Bubble diameter variable.
	 */
	private static final int DIAMETER = (int) Bubble.DIAMETER;
	/**
	 * Max amount of bubbleStorage that fit in the vertical axis.
	 */
	private static final double OFFSET = DIAMETER / 2;
	/**
	 * Collision instance.
	 */
	private Collision collide;

	/**
	 * Creates Arena for bubbleStorage storage and render variables.
	 *
	 * @param xVal
	 *            X coordinate of the background
	 * @param yVal
	 *            Y coordinate of the background
	 * @param height_t
	 *            height of the background
	 * @param width_t
	 *            width of the background
	 */
	public Arena(int xVal, int yVal, int height_t, int width_t) {
		xPos = xVal;
		yPos = yVal;
		height = height_t;
		width = width_t;
		bubbleStorage = new BubbleStorage(xPos, yPos, this);
		collide = new Collision(bubbleStorage, this);
		App.getGame().log.log(this, "Arena initialised");
	}

	/**
	 * checks in collision on collison.
	 * 
	 * @param bubble
	 *            to check on collision
	 */

	public void checkCollision(Bubble bubble) {
		collide.checkCollision(bubble);
	}

	/**
	 * getter for the collide.
	 * 
	 * @return collide instance in object.
	 */

	public Collision getCollision() {
		return collide;
	}

	/**
	 * Sets the x position of the Arena background. This position is the most
	 * top-left pixel of the background.
	 *
	 * @param xVal
	 *            integer value to set xPos to
	 */
	public final void set_xPos(final int xVal) {
		xPos = xVal;
	}

	/**
	 * Returns xPos value.
	 *
	 * @return xPos
	 */
	public final int get_xPos() {
		return xPos;
	}

	/**
	 * Sets the y position of the Arena background. This position is the most
	 * top-left pixel of the background.
	 *
	 * @param yVal
	 *            integer value to set yPos to
	 */
	public final void set_yPos(final int yVal) {
		yPos = yVal;
	}

	/**
	 * Returns yPos value.
	 *
	 * @return yPos
	 */
	public final int get_yPos() {
		return yPos;
	}

	/**
	 * Returns the width.
	 *
	 * @return width
	 */
	public final int getWidth() {
		return width;
	}

	/**
	 * Returns the height of the Arena.
	 *
	 * @return height
	 */
	public final int getHeight() {
		return height;
	}

	/**
	 * Return the bubbleStorage.
	 *
	 * @return BubbleStorage instance
	 */
	public final BubbleStorage getBubbleStorage() {
		return bubbleStorage;
	}

	/**
	 * Draw the boundary and the defeat line.
	 *
	 * @param g
	 *            canvas to draw on.
	 */
	public final void draw(Graphics g) {
		bubbleStorage.draw(g);
		collide.draw(g);
		g.setColor(Color.white);
		g.drawRect(xPos, yPos, width, height);
		float yPosLine = (float) (bubbleStorage.getHeight() * ((DIAMETER * Math
			.tan(60)) + OFFSET + 2) + yPos + 5);
		g.drawLine(xPos, yPosLine, xPos + width, yPosLine);

	}
}