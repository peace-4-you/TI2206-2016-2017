/*
 * File: Score.java
 * Class: Score
 *
 * Version: 0.0.1
 * Date: September 19th, 2016
 */

/**
 * The Score class represents a score entity
 *
 * @author Maurice Willemsen
 */

package nl.tudelft.bust_a_move20162017.bust_a_move_framework.player;

import java.util.Observable;
import java.util.Observer;

import nl.tudelft.bust_a_move20162017.bust_a_move_framework.App;

/**
 * The Game class represents a game entity.
 * @author Maurice Willemsen
 */
public class Score extends Observable {
    /**
     * Integer variable to store the score value.
     */
    private int score;
    /**
     * Default points a popped bubble is worth.
     */
    private static final int DEFAULT_POPPED_POINTS = 10;
    /**
     * Default points a dropped bubble is worth.
     */
    private static final int DEFAULT_DROPPED_POINTS = 20;
    /**
     * Constructor of the Score class.
     */
    public Score() {
        App.getGame().log.log(this, "Score initialised");
        this.score = 0;
        this.updateObserver();
    }

    /**
     * sets the player's score.
     * @param scoreVal integer value to set score to
     */
    public final void setScore(final int scoreVal) {
        App.getGame().log.log(this, "Score Set to " + scoreVal);
        this.score = scoreVal;
        this.updateObserver();
    }

    /**
     * Sets the player's score to default values.
     */
    public final void reset() {
        App.getGame().log.log(this, "Score Reset");
        this.score = 0;
        this.updateObserver();
    }
    /**
     * Add to the player's score.
     * @param scoreVal integer value to add to score
     */
    public final void addScore(final int scoreVal) {
        App.getGame().log.log(this, "Score added with " + scoreVal);
        this.score += scoreVal;
        this.updateObserver();
    }
    /**
     * Add popped bubble points to the player's score by algorithm.
     * Each popped bubble is worth DEFAULT_POINTS points.
     * @param bubbles number of bubbles popped
     */
    public final void scoreBubblesPopped(final int bubbles) {
        if (bubbles > 0) {
            int scoreVal = DEFAULT_POPPED_POINTS * bubbles;
            App.getGame().log.log(this, bubbles + " Bubbles Popped.  "
                    + "Score added with " + scoreVal);
            this.score += scoreVal;
            this.updateObserver();
        }
    }
    /**
     * Add dropped bubble points to the player's score by algorithm.
     * Each dropped bubble is worth
     * @param bubbles number of bubbles popped
     */
    public final void scoreBubblesDropped(final int bubbles) {
        if (bubbles > 0) {
            int scoreVal = (int) (DEFAULT_DROPPED_POINTS
                    * Math.pow(2, bubbles));
            App.getGame().log.log(this, bubbles + " bBubbles Dropped. "
                    + "Score added with " + scoreVal);
            this.score += scoreVal;
            this.updateObserver();
        }
    }

    /**
     * Getter method: returns the score.
     * @return int score
     */
    public final int getScore() {
        return this.score;
    }
    /**
     * Updates connected observers.
     */
    private void updateObserver() {
        this.setChanged();
        this.notifyObservers(this.score);
    }
    /**
     * Adds class object to observer list.
     * @param o Class Object
     */
    public final void addAsObserver(final Object o) {
        this.addObserver((Observer) o);
    }
    /**
     * Deletes class object from observer list.
     * @param o Class Object
     */
    public final void deleteAsObser(final Object o) {
        this.deleteObserver((Observer) o);
    }
}
