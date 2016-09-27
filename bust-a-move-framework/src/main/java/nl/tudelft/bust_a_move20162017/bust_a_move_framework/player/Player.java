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

import nl.tudelft.bust_a_move20162017.bust_a_move_framework.App;

/**
 * The Player class represents a player entity.
 *
 * @author Calvin Nhieu, Maurice Willemsen
 */
public class Player {
	private String name;
	private int combo;
	public Score score;

	public Player(String name) {
		App.getGame().log.log(this,"Player initialiased");
		this.setName(name);
		this.score = new Score();
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
		App.getGame().log.log(this,"Name " + this.name + " changed to " + name);
		this.name = name;
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
		App.getGame().log.log(this,"Combo set to " + combo);
		this.combo = combo;
	}

	/**
	 * Sets the player's members to default values
	 */
	public void reset() {
		App.getGame().log.log(this,"Player Reset");
		this.score.reset();
		this.combo = 1;
	}

	/**
	 * Draws player info
	 */

	public void draw(Graphics g) {
		g.setColor(Color.white);
		g.drawString("Name:" + this.name, 10, 40);
		g.drawString("Score:" + this.score.getScore(), 10, 70);
		g.drawString("Combo:" + this.combo, 10, 100);

	}
}
