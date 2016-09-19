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

public class Score {
	private int score;

	public Score() {
		this.score = 7;
	}
	
	/**
	 * sets the player's score
	 *
	 * @param score
	 *            integer value to set score to
	 *
	 */
	
	public void setScore(int score){
		this.score = score;
	}
	
	/**
	 * add to the player's score
	 *
	 * @param score
	 *            integer value to add to score
	 *
	 */
	
	public void addScore(int score){
		this.score += score;
	}
	
	/**
	 * @return int score
	 */	
	
	public int getScore(){
		return this.score;
	}
}
