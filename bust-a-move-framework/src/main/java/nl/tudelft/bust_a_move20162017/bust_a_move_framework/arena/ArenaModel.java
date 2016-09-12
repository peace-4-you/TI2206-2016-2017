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
		int column = 0;
		
		if(bubble2DArray.size() != (row+1)) {
			if(Bubble2DArray.peekLast == null || Bubble2DArray.peekLast.length != WIDTH_BUBBLES ) {
				bubble2DArray.add(new BubbleModel[WIDTH_BUBBLES]);
			} else {
				bubble2DArray.add(new BubbleModel[WIDTHBUBBLES-1]);
			}
		}
		
		if(bubble2DArray.get(row).length == WIDTH_BUBBLES) {
			column = shotBubble.getX()/ DIAMETER;
		} else {
			if(shotBubble.getX() >= OFFSET) {
				column = (shotBubble.getX() - OFFSET) / DIAMETER;
			} 
		}
		
		bubble2DArray.get(row)[column] = shotBubble;
		popBubbles(shotBubble);
		
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
	private void popBubbles(BubbleModel popBubble) {
		LinkedList<BubbleModel> popList = new LinkedList<BubbleModel>;
		int OFFSET = DIAMETER / 2; 				// Temporarily  
		int row = popBubble.getY() / DIAMETER;
		int column = 0;
		boolean empty = true;
		
		popList = checkBubblesToPop(popBubble, popList);
		
		if(popList.size() >= 3) {
			for(int i = 0; i < popList.size(); i++) {
				BubbleModel bubbleToPop = popList.pop();
				dropBubbles(bubbleToPop);
				bubbleToPop.pop();
				
				if(bubble2DArray.get(row).length == WIDTH_BUBBLES) {
					column = popBubble.getX()/ DIAMETER;
				} else {
					if(popBubble.getX() >= OFFSET) {
						column = (popBubble.getX() - OFFSET) / DIAMETER;
					} 
				}
				bubble2DArray.get(row)[column] = null;
				
				for(BubbleModel b: bubble2DArray.get(row)) {
					if (b != null) {
						empty = false;
						break;
					}
				}
				
				if(empty) {
					bubble2DArray.remove(row);
				}
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
	private void dropBubbles(BubbleModel dropBubble) {
		LinkedList<BubbleModel> dropList = new LinkedList<BubbleModel>();
		int OFFSET = DIAMETER / 2; 				// Temporarily  
		int row = dropBubble.getY() / DIAMETER;
		int column = 0;
		boolean empty = true;
		
		dropList = checkBubblesToDrop(dropBubble, dropList);
		
		if(dropList == null) {
			return;
		}
		
		for(int i = 0; i < dropList.size(); i++) {
			BubbleModel bubbleToDrop = dropList.pop();
			bubbleToDrop.drop();

			if(bubble2DArray.get(row).length == WIDTH_BUBBLES) {
				column = dropBubble.getX()/ DIAMETER;
			} else {
				if(dropBubble.getX() >= OFFSET) {
					column = (dropBubble.getX() - OFFSET) / DIAMETER;
				} 
			}
			bubble2DArray.get(row)[column] = null;
			
			for(BubbleModel b: bubble2DArray.get(row)) {
				if (b != null) {
					empty = false;
					break;
				}
			}
			
			if(empty) {
				bubble2DArray.remove(row);
			}
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
	private LinkedList<BubbleModel> checkBubblesToPop(BubbleModel lastBubble, LinkedList<BubbleModel> popList) {
		BubbleModel[] neightbors = new BubbleModel[6];
		int OFFSET = DIAMETER / 2; 				// Temporarily  
		int row = lastBubble.getY() / DIAMETER;
		int column = 0;
		boolean empty = true;
		
		if(bubble2DArray.get(row).length == WIDTH_BUBBLES) {
			column = lastBubble.getX()/ DIAMETER;
		} else {
			if(lastBubble.getX() >= OFFSET) {
				column = (lastBubble.getX() - OFFSET) / DIAMETER;
			} 
		}
		
		neightbors[0] = bubble2DArray.get(row-1)[column];
		neightbors[1] = bubble2DArray.get(row-1)[column+1];
		neightbors[2] = bubble2DArray.get(row)[column-1];
		neightbors[3] = bubble2DArray.get(row)[column+1];
		neightbors[4] = bubble2DArray.get(row+1)[column];
		neightbors[5] = bubble2DArray.get(row+1)[column+1];
		
		popList.add(lastBubble);
		
		for(BubbleModel b: neightbors) {
			if(b != null) {
				empty = false;
				break;
			}
		}
	
		if(empty) {
			return popList;
		}
		
		for(BubbleModel b: neightbors) {
			if(b.getColor() == lastBubble.getColor() 
			   && !popList.contains(b)) {
				popList = checkbubblesToPop(b, popList);
			}
		}
		return popList;
	}
	
	/**
	 * Checks which bubble needs to be dropped using recursive calls.	
	 * 
	 * @param lastBubble	checks the neighbors of this bubble.
	 * 
	 */
	private LinkedList<BubbleModel> checkBubblesToDrop(BubbleModel lastBubble, LinkedList<BubbleModel> dropList, LinkedList<BubbleModel> ignoreList) {
		BubbleModel[] neightbors = new BubbleModel[6];
		int OFFSET = DIAMETER / 2; 				// Temporarily  
		int row = lastBubble.getY() / DIAMETER;
		int column = 0;
		boolean empty = true;
		
		if(bubble2DArray.get(row).length == WIDTH_BUBBLES) {
			column = lastBubble.getX()/ DIAMETER;
		} else {
			if(lastBubble.getX() >= OFFSET) {
				column = (lastBubble.getX() - OFFSET) / DIAMETER;
			} 
		}
		//limit
		neightbors[0] = bubble2DArray.get(row-1)[column];
		neightbors[1] = bubble2DArray.get(row-1)[column+1];
		neightbors[2] = bubble2DArray.get(row)[column-1];
		neightbors[3] = bubble2DArray.get(row)[column+1];
		neightbors[4] = bubble2DArray.get(row+1)[column];
		neightbors[5] = bubble2DArray.get(row+1)[column+1];
		
		for(BubbleModel b: neightbors) {
			if(b != null) {
				empty = false;
				break;
			}
		}
	
		if(empty) {
			if(dropBubble.getY() == OFFSET) {
				return null;
			} else {
				return dropList;
			}
		}

		for(BubbleModel b: neighbors) {
			if(!dropList.contains(b)) {
				dropList = checkbubblesToDrop(b, dropList);
			}
		}
		
		if(dropList != null) {
			dropList.add(lastBubble);
		}
		
		return dropList;
	}
	
}
