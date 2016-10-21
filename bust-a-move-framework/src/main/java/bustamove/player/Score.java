/*
 * File: Score.java
 *
 * Class: Score
 *
 * Version: 0.0.1
 *
 * Date: September 19th, 2016
 */


package bustamove.player;
import bustamove.system.Log;

/**
 * The Score class represents a score entity.
 *
 * @author Maurice Willemsen
 */
public class Score {
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
     * Player owner.
     */
    private final Player owner;

    /**
     * Constructor of the Score class.
     * @param player the owner of this score
     */
    public Score(final Player player) {
        Log.getInstance().log(this, "Score initialised");
        this.score = 0;
        this.owner = player;
        this.owner.notifyObserver();
    }

    /**
     * sets the player's score.
     * @param scoreVal integer value to set score to
     */
    public final void setScore(final int scoreVal) {
        Log.getInstance().log(this, "Score Set to " + scoreVal);
        this.score = scoreVal;
        this.owner.notifyObserver();
    }

    /**
     * Sets the player's score to default values.
     */
    public final void reset() {
        Log.getInstance().log(this, "Score Reset");
        this.score = 0;
        this.owner.notifyObserver();
    }

    /**
     * Add to the player's score.
     * @param scoreVal integer value to add to score
     */
    public final void addScore(final int scoreVal) {
        Log.getInstance().log(this, "Score added with " + scoreVal);
        this.score += scoreVal;
        this.owner.notifyObserver();
    }

    /**
     * Add popped bubble points to the player's score by algorithm. Each popped
     * bubble is worth DEFAULT_POINTS points.
     * @param bubbles number of bubbles popped
     */
    public final void scoreBubblesPopped(final int bubbles) {
        if (bubbles > 0) {
            int scoreVal = DEFAULT_POPPED_POINTS * bubbles;
            Log.getInstance().log(this, bubbles + " Bubbles Popped.  "
                    + "Score added with " + scoreVal);
            this.score += scoreVal;
            this.owner.notifyObserver();
        }
    }

    /**
     * Add dropped bubble points to the player's score by algorithm. Each
     * dropped bubble is worth
     * @param bubbles number of bubbles popped
     */
    public final void scoreBubblesDropped(final int bubbles) {
        if (bubbles > 0) {
            int scoreVal = (int) (DEFAULT_DROPPED_POINTS
                    * Math.pow(2, bubbles));
            Log.getInstance().log(this, bubbles + " bBubbles Dropped. "
                    + "Score added with " + scoreVal);
            this.score += scoreVal;
            this.owner.notifyObserver();
        }
    }

    /**
     * Getter method: returns the score.
     * @return int score
     */
    public final int getScore() {
        return this.score;
    }
}
