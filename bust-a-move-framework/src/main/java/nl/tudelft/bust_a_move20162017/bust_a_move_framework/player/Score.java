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

import nl.tudelft.bust_a_move20162017.bust_a_move_framework.App;

/**
 * The Game class represents a game entity.
 *
 * @author Maurice Willemsen
 */

public class Score {
	
	private int score;

	public Score() {
		App.getGame().log.log(this,"Score initialised");
		this.score = 0;
	}

	/**
	 * sets the player's score
	 *
	 * @param score
	 *            integer value to set score to
	 *
	 */

	public void setScore(int score) {
		App.getGame().log.log(this,"Score Set to " + score);
		this.score = score;
	}

	/**
	 * sets the player's score to default values
	 *
	 */

	public void reset() {
		App.getGame().log.log(this,"Score Reset");
		this.score = 0;
	}
	
	/**
	 * add to the player's score
	 *
	 * @param score
	 *            integer value to add to score
	 *
	 */

	public void addScore(int score) {
		App.getGame().log.log(this,"Score added with " + score);
		this.score += score;
	}

	/**
	 * add to the player's score by algorithm
	 *
	 * @param score
	 *            integer value to add to score
	 *
	 */

	public void scoreBubblesPopped(int bubbles) {
		if (bubbles > 0) {
			int score = 10 * bubbles;
			App.getGame().log.log(this,bubbles + " Bubbles Popped.  Score added with " + score);
			this.score += score;
		}
	}

	/**
	 * add to the player's score by algorithm
	 *
	 * @param score
	 *            integer value to add to score
	 *
	 */

	public void scoreBubblesDropped(int bubbles) {
		if (bubbles > 0) {
			int score = (int) (20 * Math.pow(2, bubbles));
			App.getGame().log.log(this,bubbles + " bBubbles Dropped. Score added with " + score);
			this.score += score;
		}
	}

	/**
	 * @return int score
	 */

	public int getScore() {
		return this.score;
	}
}
