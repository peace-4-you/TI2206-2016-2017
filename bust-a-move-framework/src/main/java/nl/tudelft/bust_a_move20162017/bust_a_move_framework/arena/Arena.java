/* 
 * File: Arena.java
 * 
 * Class: Arena
 * 
 * Version: 0.0.1
 * 
 * Date: September 7th 2016
 * 
 */


package nl.tudelft.bust_a_move20162017.bust_a_move_framework.arena;

import nl.tudelft.bust_a_move20162017.bust_a_move_framework.graph.Graph;

/**
 * The arena class maintains a graph data structure to store the bubble objects. 
 *  
 * @author Winer Bao
 *
 */
public class Arena<T> {
	private int xPos;
	private int yPos;
	private int height;
	private int width;
	private final Graph storageGraph;
	
	/**
	 * Creates Arena for bubbles storage and render variables
	 */
	Arena(int xVal, int yVal, int height_t, int width_t) {
		xPos = xVal;
		yPos = yVal;
		height = height_t;
		width = width_t;
		storageGraph = new Graph<T, Integer>(false);
	}
	
	/**
	 * Sets the x position of the Arena background. This position is the most 
	 * top-left pixel of the background.
	 * 
	 * @param xVal	integer value to set xPos to
	 * 
	 */
	public void set_xPos(int xVal) {
		xPos = xVal;
	}
	
	/**
	 * Returns xPos value
	 */
	public int get_xPos() {
		return xPos;
	}
	
	/**
	 * Sets the y position of the Arena background. This position is the most 
	 * top-left pixel of the background.
	 * 
	 * @param yVal	integer value to set yPos to
	 * 
	 */
	public void set_yPos(int yVal) {
		yPos = yVal;
	}
	
	/**
	 * Returns yPos value
	 */
	public int get_yPos() {
		return yPos;
	}
	
}
