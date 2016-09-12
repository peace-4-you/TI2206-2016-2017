/* 
 * File: Arena.java
 * 
 * Class: Arena
 * 
 * Version: 0.0.1
 * 
 * Date: September 12th 2016
 * 
 */


package nl.tudelft.bust_a_move20162017.bust_a_move_framework.arena;

import java.util.ArrayList;
import java.util.LinkedList;

import nl.tudelft.bust_a_move20162017.bust_a_move_framework.bubble.*;

/**
 * The arena class maintains a LinkedList of ArrayList data structure to store the bubble objects. 
 *  
 * @author Winer Bao
 *
 */
public class ArenaModel {
	
	private int xPos;
	private int yPos;
	private int height;
	private int width;
	private LinkedList<BubbleModel[]> bubble2DArray; 
	
	/* Max amount of bubbles that fit in the horizontal axis */
	private final int WIDTH_BUBBLES = 8;		
	
	/* Max amount of bubbles that fit in the vertical axis */
	private final int HEIGHT_BUBBLES = 12;	
	
	/* Keep track how many bubbles are shot */
	private int bubbleCount;
	
	/* Temporarily Bubble diameter variable */
	private final int DIAMETER = 40; 
	
	/**
	 * Creates Arena for bubbles storage and render variables
	 * 
	 * @param xVal		X coordinate of the background
	 * @param yVal		Y coordinate of the background
	 * @param height_t	height of the background
	 * @param width_t	width of the background
	 * 
	 */
	public ArenaModel(int xVal, int yVal, int height_t, int width_t) {
		
		xPos = xVal;
		yPos = yVal;
		height = height_t;
		width = width_t;
		bubble2DArray = new LinkedList<BubbleModel[]>();
		bubbleCount = 0;
		
		//Level 1
		for(int i = 0; i < 5; i++) {
			addBubbleRow();
		}
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
	 * Returns the storage 2D array in which all the bubbles are stored.
	 * 
	 * @return bubble2DArray
	 */
	public LinkedList<BubbleModel[]> get_BubbleArray() {
		
		return bubble2DArray;
	}
	
	/**
	 * Calculates where the shot bubble lands on the arena. The location is 
	 * saved in the graph. It also calls the pop bubbles method. 
	 * 
	 * @param shotBubble The bubble shot by the cannon
	 * 
	 */
	public void landBubble(BubbleModel shotBubble) {
		
		int OFFSET = DIAMETER / 2; 				// Temporarily  
		int row = shotBubble.getY() / DIAMETER;
		int column;
		
		if(bubble2DArray.size() <= (row + 1)) {
			// Even bubbles in a row
			if(bubble2DArray.get(row).length == WIDTH_BUBBLES) {
				column = shotBubble.getX()/ DIAMETER;
			} else { // Uneven bubbles in a row
				column = (shotBubble.getX() + OFFSET) / DIAMETER;
			}
		} else {
			if(bubble2DArray.get(row-1).length == WIDTH_BUBBLES) {
				column = (shotBubble.getX() + OFFSET) / DIAMETER;
			} else { 
				column = shotBubble.getX()/ DIAMETER;
			}
		}
		
		while(bubble2DArray.size() != row) {
			if((bubble2DArray.peekLast() != null) && (bubble2DArray.peekLast().length == WIDTH_BUBBLES)){
				bubble2DAra
			}
		}
		
		bubbleCount++;
		if(bubbleCount > 10) {
			bubbleCount = 1;
			addBubbleRow();
		}
	}
	
	/**
	 * Checks for 3 (or more) connected bubbles. If so, it signals the bubble objects to 
	 * pop and removes it from the graph. It also calls the drop bubbles method. 
	 * 
	 * @param popBubble The bubble to be popped
	 * 
	 */
	private void popBubbles(Vertex<BubbleModel, Integer> popBubble) {
		LinkedList<Vertex<BubbleModel,Integer>> popList = new LinkedList<Vertex<BubbleModel,Integer>>();
		
		checkBubblesToPop(popBubble);
		
		if(popList.size() >= 3) {
			
			for(int i = 0; i < popList.size(); i++) {
				Vertex<BubbleModel,Integer> bubbleToPop = popList.pop();
				dropBubbles(bubbleToPop);
				bubbleToPop.getdata().pop();
				graph.removeVertex(bubbleToPop);
			}
		}
	}
	
	/**
	 * Checks if bubbles needs to be dropped. If so, it signal the bubble object to drop 
	 * and remove it from the graph.
	 * 
	 * @param dropBubble The bubble to be dropped
	 * 
	 */
	private void dropBubbles(Vertex<BubbleModel,Integer> dropBubble) {
		LinkedList<Vertex<BubbleModel,Integer>> dropList = new LinkedList<Vertex<BubbleModel,Integer>>();
		
		checkBubblesToDrop(bubbleToDrop, dropList);
		for(int i = 0; i < dropList.size(); i++) {
			Vertex<BubbleModel,Integer> bubbleToDrop = dropList.pop();
			bubbleToDrop.getdata().drop();
			graph.removeVertex(bubbleToDrop);
		}
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
	 * Checks which bubble needs to be popped using recursive calls.
	 * 
	 * @param lastBubble	checks the neighbors of this bubble.
	 * 
	 */
	private void checkBubblesToPop(Vertex<BubbleModel,Integer> lastBubble, LinkedList<Vertex<BubbleModel,Integer>> popList) {
		Vertex<BubbleModel,Integer>[] neightbors = lastBubble.getNeighbors();
		
		popList.add(lastBubble);
		
		if(neightbors.length == 0) {
			return;
		}
		
		for(int i = 0; i < neightbors.length; i++) {
			Vertex<BubbleModel,Integer> bubble_vtx = neightbors[i];
			
			if(bubble_vtx.getdata().getColor() == lastBubble.getdata().getColor() 
			   && !popList.contains(bubble_vtx)) {
				checkbubblesToPop(bubble_vtx);
			}
		}
		return;
	}
	
	/**
	 * Checks which bubble needs to be dropped using recursive calls.	
	 * 
	 * @param lastBubble	checks the neighbors of this bubble.
	 * 
	 */
	private void checkBubblesToDrop(Vertex<BubbleModel,Integer> lastBubble, LinkedList<Vertex<BubbleModel,Integer>> dropList) {
		Vertex<BubbleModel,Integer>[] neightbors = lastBubble.getNeighbors();
		
		if(neightbors.length == 0) {
			return;
		}
		
		for(int i = 0; i < neightbors.length; i++) {
			Vertex<BubbleModel,Integer> bubble_vtx = neightbors[i];
			
			if(bubble_vtx.getdata().getY() < lastBubble.getdata().getY()) {
				dropList.add(lastBubble);
				checkbubblesToDrop(bubble_vtx);
			}
		}
		return;
	}
	
}
