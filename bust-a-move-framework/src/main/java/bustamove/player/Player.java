/*
 * File: Player.java
 *
 * Class: Player
 *
 * Version: 0.0.1
 *
 * Date: September 8th, 2016
 */


package bustamove.player;

import java.util.Vector;

import bustamove.screen.config.GameConfig;
import bustamove.system.Log;
import bustamove.util.PlayerObservable;
import bustamove.util.PlayerObserver;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

/**
 * The Player class represents a player entity.
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
     * Drawing offset and id number of the GameModel the cannon belongs to.
     */
    private final int offset;
    private final int id;
    /**
     * Vector of all Observer that are subscribed.
     */
    private Vector<PlayerObserver> observers;

    /**
     * Constructor of Player class.
     * @param s player's name
     * @param drawingoffset the x offset that drawings get
     * @param playerid the id of the player in the game
     */
    public Player(final String s, final int drawingoffset, final int playerid) {
        Log.getInstance().log(this, "Player initialiased");
        this.observers = new Vector<PlayerObserver>();
        this.offset = drawingoffset;
        this.id = playerid;
        this.setName(s);
        this.score = new Score(this);
        this.combo = 1;
    }

    /**
     * Getter method: returns the name of the player.
     * @return String name
     */
    public final String getName() {
        return this.name;
    }

    /**
     * Setter method: for the player's name.
     * @param s String value to set name to
     */
    public final void setName(final String s) {
        String formatted = Character.toTitleCase(s.charAt(0)) + s.substring(1);
        Log.getInstance().log(this, "Name " + this.name
                + " changed to " + formatted);
        this.name = formatted;
        this.notifyObserver();
    }

    /**
     * Sets the player's members to default values.
     */
    public final void reset() {
        Log.getInstance().log(this, "Player Reset");
        this.score.reset();
        this.combo = 1;
    }

    /**
     * Getter method: for combo value.
     * @return int combo
     */
    public final int getCombo() {
        return this.combo;
    }

    /**
     * Setter method: for the player's combo.
     * @param comboVal integer value to set combo to
     */
    public final void setCombo(final int comboVal) {
        Log.getInstance().log(this, "Combo set to " + comboVal);
        this.combo = comboVal;
    }

    /**
     * Getter method: for Score object.
     * @return Score object
     */
    public final Score getScore() {
        return score;
    }

    /**
     * Draws player info.
     * @param g object
     */
    public final void draw(final Graphics g) {
        g.setColor(Color.white);

        g.drawString("Combo:" + this.combo, GameConfig.MARGIN_LEFT
                + this.offset, GameConfig.FOURTH_LINE);
    }

    /**
     * Notify's all observers.
     */
    public final void notifyObserver() {
        for (PlayerObserver o : observers) {
            o.update(this.id, this.name, this.score.getScore());
        }
    }

    /**
     * Notify's about the amount of observables.
     * @param amount integer of the amount of observables.
     */
    public final void notifyAmountObserver(final int amount) {
        for (PlayerObserver o : observers) {
            o.update(amount);
        }
    }

    /**
     * Register a observer to the observable.
     * @param o A playerObserver object.
     */
    public final void registerObserver(final PlayerObserver o) {
        this.observers.addElement(o);
    }

    /**
     * Remove a observer from the observable.
     * @param o A playerObserver object.
     */
    public final void removeObserver(final PlayerObserver o) {
        this.observers.remove(o);
    }

    /**
     * Getter Method for the observer.
     * @return observers a vector with the observers registered
     */
    public final Vector<PlayerObserver> getObservers() {
        return this.observers;
    }
}