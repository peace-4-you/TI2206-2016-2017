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

import java.util.LinkedList;
import nl.tudelft.bust_a_move20162017.bust_a_move_framework.graph.*;
import nl.tudelft.bust_a_move20162017.bust_a_move_framework.bubble.*;

/**
 * The arena class maintains a graph data structure to store the bubble objects. 
 *  
 * @author Winer Bao
 *
 */
public class ArenaModel {
	private int xPos;
	private int yPos;
	private int height;
	private int width;
	private final Graph<BubbleModel, Integer> storageGraph;
	private LinkedList<BubbleModel> topBubbleList; 
	
	/* Amount of bubbles that fit in the horizontal axis */
	private final int width_hex;		
	
	/* Amount of bubbles that fit in the vertical axis */
	private final int height_hex;	
	
	/* Keep track how many bubbles are shot */
	private int bubbleCount;
	
	/**
	 * Creates Arena for bubbles storage and render variables
	 */
	public ArenaModel(int xVal, int yVal, int height_t, int width_t) {
		xPos = xVal;
		yPos = yVal;
		height = height_t;
		width = width_t;
		storageGraph = new Graph<BubbleModel, Integer>(false);
		topBubbleList = new LinkedList<BubbleModel>();
		
		/* TODO: calculate width_hex and height_hex */
		width_hex = 8;
		height_hex = 12;
		
		bubbleCount = 0;
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
	 * 
	 * @return xPos
	 * 
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
	 * 
	 * @return yPos
	 * 
	 */
	public int get_yPos() {
		return yPos;
	}
	
	/**
	 * Returns the storage graph in which all the bubbles are stored.
	 * 
	 * @return storageGraph
	 */
	public Graph<BubbleModel, Integer> get_Graph() {
		return storageGraph;
	}
	
	/**
	 * Calculates where the shot bubble lands on the arena. The location is 
	 * saved in the graph. It checks if there are 3 or more bubbles connected
	 * together and calls the pop and drop bubbles methods. 
	 * 
	 * @param shotBubble The bubble shot by the cannon
	 * 
	 */
	public void landBubble(BubbleModel shotBubble) {
		storageGraph.addVertex(shotBubble);

		bubbleCount++;
		if(bubbleCount > 10) {
			bubbleCount = 1;
			addBubbleRow();
		}
	}
	
	/**
	 * Signals the bubble object to pop.
	 * 
	 * @param popBubble The bubble to be popped
	 * 
	 */
	private void popBubble(BubbleModel popBubble) {
		popBubble.pop();
	}
	
	/**
	 * Signal the bubble object to drop
	 * 
	 * @param dropBubble The bubble to be dropped
	 * 
	 */
	private void dropBubble(BubbleModel dropBubble) {
		dropBubble.drop();
	}
	
	/**
	 * Adds a new row of bubbles after the cannon shots 10 times. The new
	 * row is added to the top of the Arena and saved in the graph.
	 */
	private void addBubbleRow() {
		BubbleGenerator bubbleGen = new BubbleGenerator(this);
		for(int i = 0; i < width_hex; i++) {
			/* TODO: Add new Bubble objects to top */
		}
	}
	
	/**
	 * Checks which bubble needs to be popped.
	 * 
	 * @param lastBubble	Last bubble added to the graph.
	 * 
	 * @return An array of BubbleModel containing the bubbles to be popped. 
	 */
	private BubbleModel[] checkBubblesToPop(BubbleModel lastBubble) {
		BubbleModel[] array;
		
		
		array = new BubbleModel[1];
		//Check 3 or more connected bubbles
		return array;
	}
	
	/**
	 * Checks which bubble needs to be dropped.	
	 * 
	 * @return An array of BubbleModel containing the bubbles to be dropped.
	 */
	private BubbleModel[] checkBubblesToDrop() {
		BubbleModel[] array;
		
		array = new BubbleModel[1];
		//Check drop bubbles
		return array;
	}
	
}
