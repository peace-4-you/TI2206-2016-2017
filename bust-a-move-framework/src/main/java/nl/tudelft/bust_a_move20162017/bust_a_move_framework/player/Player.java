/*
 * File: Player.java
 * Class: Player
 *
 * Version: 0.0.1
 * Date: September 8th, 2016
 */

package nl.tudelft.bust_a_move20162017.bust_a_move_framework.player;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

/**
 * The Player class represents a player entity.
 *
 * @author Calvin Nhieu
 */
public class Player {
	private String name;
	private int score;
	private int combo;

	public Player(String name) {
		this.name = name;
		this.score = 0;
		this.combo = 1;
	}

	/**
	 * @return String name
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * sets the player's name
	 *
	 * @param name
	 *            String value to set name to
	 *
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return int score
	 */
	public int getScore() {
		return this.score;
	}

	/**
	 * sets the player's score
	 *
	 * @param name
	 *            integer value to set score to
	 *
	 */
	public void setScore(int score) {
		this.score = score;
	}

	/**
	 * @return int combo
	 */
	public int getCombo() {
		return this.combo;
	}

	/**
	 * sets the player's combo
	 *
	 * @param name
	 *            integer value to set combo to
	 *
	 */
	public void setCombo(int combo) {
		this.combo = combo;
	}

	/**
	 * Sets the player's members to default values
	 */
	public void reset() {
		this.score = 0;
		this.combo = 1;
	}

	/**
	 * Draws player info
	 */

	public void draw(Graphics g) {
		g.setColor(Color.white);
		g.drawString("Name:" + this.name, 10, 40);
		g.drawString("Score:" + this.score, 10, 70);
		g.drawString("Combo:" + this.combo, 10, 100);

	}
}
