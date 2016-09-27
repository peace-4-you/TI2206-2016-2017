/*
 * File: Text.java
 * Class: Text
 *
 * Version: 0.0.3
 */
package nl.tudelft.bust_a_move20162017.bust_a_move_framework.gamestate;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.GameContainer;

import java.awt.Font;

/**
 * @author Maurice Willemsen
 */
public class Text {

	/**
	 * String Value of the text.
	 */
	private String text;
	/**
	 * X position of the left point of the text
	 */
	private float xPos;
	/**
	 * Y position of the top point of the text
	 */
	private float yPos;
	/**
	 * Width of the text to be displayed
	 */
	private float tWidth;
	/**
	 * The font the text should be displayed in.
	 */
	private TrueTypeFont font = new TrueTypeFont(new Font("Verdana", Font.BOLD,
		GameConfig.TEXT_SIZE), true);

	/**
	 * Constructor for a text without x parameter.
	 *
	 * @param _text
	 *            text that will be displayed
	 * @param y
	 *            y coordinate of the text
	 */

	public Text(final String _text, final float y) {
		assert _text != null;
		assert y >= 0;

		this.text = _text;
		xPos = 0;
		yPos = y;
		tWidth = font.getWidth(this.text);
	}

	/**
	 * Constructor with parameter for x coordinate.
	 *
	 * @param _text
	 *            text that will be displayed
	 * @param x
	 *            x coordinate of the text
	 * @param y
	 *            y coordinate of the text
	 */

	public Text(final String _text, final float x, final float y) {
		this(_text, y);
		assert x >= 0;
		xPos = x;
	}

	/**
	 * Draw the text centered.
	 *
	 * @param g
	 *            Graphics object to draw on/in
	 */

	public final void draw(final Graphics g) {
		g.setColor(Color.white);
		font.drawString(xPos, yPos, text, Color.white);
	}

	/**
	 * Center the text on the xPos-axis(so center it vertically) for the lazy
	 * people.
	 *
	 * @param game
	 *            to get the width of
	 */

	public final void centerText(final GameContainer game) {
		xPos = (game.getWidth() - tWidth) / 2;
	}

	/**
	 * Set the text.
	 * 
	 * @param _text
	 *            the string to set
	 */

	public final void setText(final String _text) {
		this.text = _text;
	}
}
