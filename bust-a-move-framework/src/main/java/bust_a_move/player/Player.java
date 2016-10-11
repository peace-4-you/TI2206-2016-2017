/*
 * File: Player.java
 * Class: Player
 *
 * Version: 0.0.1
 * Date: September 8th, 2016
 */

package bust_a_move.player;

import java.util.Vector;
import bust_a_move.log.Log;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import bust_a_move.App;
import bust_a_move.game.PlayerObservable;
import bust_a_move.game.PlayerObserver;

/**
 * The Player class represents a player entity.
 * 
 * @author Calvin Nhieu, Maurice Willemsen
 */
public class Player implements PlayerObservable {
	/**
	 * Contains the name of the player.
	 */
	private String name;
	/**
	 * Contains the current combo multiplier.
	 */
	private int combo;
	/**
	 * Contains a Score object.
	 */
	private Score score;
	/**
	 * X coordinate where the name will be printed.
	 */
	private static final float NAME_XPOS = 10;
	/**
	 * Y coordinate where the name will be printed.
	 */
	private static final float NAME_YPOS = 40;
	/**
	 * X coordinate where the combo value will be printed.
	 */
	private static final float COMBO_XPOS = 10;
	/**
	 * Y coordinate where the combo value will be printed.
	 */
	private static final float COMBO_YPOS = 100;


	private final int offset;

	private final int id;

	private Vector<PlayerObserver> observers;

	/**
	 * Constructor of Player class.
	 * 
	 * @param s
	 *            player's name
	 */
	public Player(final String s, int offset, int id) {
		Log.log(this, "Player initialiased");
		this.observers = new Vector<PlayerObserver>();
		this.offset = offset;
		this.id = id;
		this.setName(s);
		this.score = new Score(this);
		this.combo = 1;
	}

 


	/**
	 * Getter method: returns the name of the player.
	 * 
	 * @return String name
	 */
	public final String getName() {
		return this.name;
	}

	/**
	 * Setter method: for the player's name.
	 * 
	 * @param s
	 *            String value to set name to
	 */
	public final void setName(final String s) {
		Log.log(this, "Name " + this.name + " changed to " + s);
		this.name = s;
		this.notifyObserver();
	}
	

    /**
     * Sets the player's members to default values.
     */
	
    public final void reset() {
        Log.log(this, "Player Reset");
        this.score.reset();
        this.combo = 1;
    }
    

	/**
	 * Getter method: for combo value.
	 * 
	 * @return int combo
	 */
    
	public final int getCombo() {
		return this.combo;
	}

	/**
	 * Setter method: for the player's combo.
	 * 
	 * @param comboVal
	 *            integer value to set combo to
	 */

    public final void setCombo(final int comboVal) {
        Log.log(this, "Combo set to " + comboVal);
        this.combo = comboVal;
    }

	/**
	 * Getter method: for Score object.
	 * 
	 * @return Score object
	 */
	public final Score getScore() {
		return score;
	}


	/**
	 * Draws player info.
	 * 
	 * @param g
	 *            object
	 */
	public final void draw(final Graphics g) {
		g.setColor(Color.white);
		g.drawString("Name:" + this.name, NAME_XPOS + this.offset, NAME_YPOS);
		g.drawString("Combo:" + this.combo, COMBO_XPOS + this.offset,
			COMBO_YPOS);
	}

	public void notifyObserver() {
		for (PlayerObserver o : observers) {
			o.update(this.id, this.name, this.score.getScore());
		}
	}

	public void notifyAmountObserver(int amount) {
		for (PlayerObserver o : observers) {
			o.update(amount);
		}
	}

	public void registerObserver(PlayerObserver o) {
		this.observers.addElement(o);
	}

	public void removeObserver(PlayerObserver o) {
		this.observers.remove(o);
	}
}
