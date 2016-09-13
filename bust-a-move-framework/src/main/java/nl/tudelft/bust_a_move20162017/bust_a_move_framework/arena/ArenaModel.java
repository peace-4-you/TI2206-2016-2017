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
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Random;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

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
	private LinkedList<Bubble[]> bubble2DArray; 
	
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
		bubble2DArray = new LinkedList<Bubble[]>();
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
	 * Returns the width
	 * @return width
	 */
	public int getWidth() {
		
		return width;
	}
	
	/**
	 * Returns the storage 2D array in which all the bubbles are stored.
	 * 
	 * @return bubble2DArray
	 */
	public LinkedList<Bubble[]> get_BubbleArray() {
		
		return bubble2DArray;
	}
	
	/**
	 * Calculates where the shot bubble lands on the arena. The location is 
	 * saved in the graph. It also calls the pop bubbles method. 
	 * 
	 * @param shotBubble The bubble shot by the cannon
	 * 
	 */
	public void landBubble(Bubble shotBubble) {
		int row = getRow(shotBubble.getY());
		int column = 0;
		
		if(bubble2DArray.size() != (row+1)) {
			if(bubble2DArray.peekLast() == null || bubble2DArray.peekLast().length != WIDTH_BUBBLES ) {
				bubble2DArray.add(new Bubble[WIDTH_BUBBLES]);
			} else {
				bubble2DArray.add(new Bubble[WIDTH_BUBBLES-1]);
			}
		}
		
		column = getColumn(shotBubble.getX(), shotBubble.getY());
		
		bubble2DArray.get(row)[column] = shotBubble;
		popBubbles(shotBubble);
		
		bubbleCount++;
		if(bubbleCount > 10) {
			bubbleCount = 1;
			addBubbleRow();
		}
	}
	
	/**
	 * Gets the column 
	 * @param xpos
	 * @param ypos
	 * @return
	 */
	public int getColumn(int xpos, int ypos) {
		int OFFSET = DIAMETER / 2;
		int row = ypos / DIAMETER;
		int column = 0;
		if(bubble2DArray.get(row).length == WIDTH_BUBBLES) {
			column = xpos/ DIAMETER;
		} else {
			if(xpos >= OFFSET) {
				column = (xpos - OFFSET) / DIAMETER;
			} 
		}
		return column;
	}
	
	/**
	 * A function that calculates the row with a given y position.
	 * @param ypos
	 * @return row number
	 */
	public int getRow(int ypos) {
		return ypos / DIAMETER;
	}
	
	/**
	 * Checks for 3 (or more) connected bubbles. If so, it signals the bubble objects to 
	 * pop and removes it from the graph. It also calls the drop bubbles method. 
	 * 
	 * @param popBubble The bubble to be popped
	 * 
	 */
	private void popBubbles(Bubble popBubble) {
		LinkedList<Bubble> popList = new LinkedList<Bubble>();
		
		popList = checkBubblesToPop(popBubble, popList);
		
		if(popList.size() >= 3) {
			for(int i = 0; i < popList.size(); i++) {
				int OFFSET = DIAMETER / 2; 				// Temporarily  
				int row = 0; 
				int column = 0;
				boolean empty = true;
				Bubble bubbleToPop = popList.pop();
				dropBubbles(bubbleToPop);
				bubbleToPop.pop();
				
				row = getRow(popBubble.getY());
				column = getColumn(popBubble.getX(), popBubble.getY());
				bubble2DArray.get(row)[column] = null;
				
				for(Bubble b: bubble2DArray.get(row)) {
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
	private void dropBubbles(Bubble dropBubble) {
		LinkedList<Bubble> dropList = new LinkedList<Bubble>();
		int OFFSET = DIAMETER / 2; 				// Temporarily  
		int row = dropBubble.getY() / DIAMETER;
		int column = 0;
		boolean empty = true;
		
		dropList = checkBubblesToDrop(dropBubble, dropList);
		
		if(dropList == null) {
			return;
		}
		
		for(int i = 0; i < dropList.size(); i++) {
			Bubble bubbleToDrop = dropList.pop();
			bubbleToDrop.drop();


			column = getColumn(dropBubble.getX(), dropBubble.getY());
			bubble2DArray.get(row)[column] = null;
			
			for(Bubble b: bubble2DArray.get(row)) {
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

		if(get_BubbleArray().size() >= HEIGHT_BUBBLES) {
			// Lose the game
			
		}
		// TODO: Use the width of the first row and check if it is a wide row
		// or a smaller row, like hexagonal grid.
		Bubble[] bubbleRow;
		if(get_BubbleArray().getFirst().length == WIDTH_BUBBLES)
			bubbleRow = new Bubble[getWidth() - 1];
		else
			bubbleRow = new Bubble[getWidth()];
			
		
		
		Random rand = new Random();
		// TODO: Store the value of a Random() class somewhere instead of making
		// a new instance every time.
		
		ColorChoice[] colors = ColorChoice.values();
		// TODO: should get a list of currently available colors,
		// therefore we should make a method that returns all colors on the map.
		
		for(int i = 0; i < getWidth(); i++) {
			// 
			int bubbleX = DIAMETER * i;
			int bubbleY = 0;
			int colorInt = rand.nextInt(ColorChoice.values().length);
			Bubble b = new Bubble(bubbleX, bubbleY, ColorChoice.values()[colorInt]);
			
		}
		
		// TODO: should we push the y coordinate of all other bubbles down by DIAMETER?
		
		get_BubbleArray().add(bubbleRow);
	}
	
	/**
	 * A function that returns a list of all the colors of bubbles
	 * still on the playing field.
	 * 
	 * @return List<ColorChoice>
	 */
	public List<ColorChoice> getColorsOnArena() {
		// TODO: maybe optimize this and save the result of the
		// list of colors somewhere, and update whenever a bubble pops
		LinkedList<ColorChoice> colorList = new LinkedList<ColorChoice>();
		for(Bubble[] row : bubble2DArray) {
			for(Bubble bub : row) {
				if(!colorList.contains(bub.getColor())) {
					colorList.add(bub.getColor());
				}
			}
		}
		return colorList;
	}
	
	/**
	 * Checks which bubble needs to be popped using recursive calls.
	 * 
	 * @param lastBubble	checks the neighbors of this bubble.
	 * 
	 */
	private LinkedList<Bubble> checkBubblesToPop(Bubble lastBubble, LinkedList<Bubble> popList) {
		Bubble[] neightbors = new Bubble[6];
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
		
		for(Bubble b: neightbors) {
			if(b != null) {
				empty = false;
				break;
			}
		}
	
		if(empty) {
			return popList;
		}
		
		for(Bubble b: neightbors) {
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
	private LinkedList<Bubble> checkBubblesToDrop(Bubble lastBubble, LinkedList<Bubble> dropList) {
		Bubble[] neightbors = new Bubble[6];
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
		
		for(Bubble b: neightbors) {
			if(b != null) {
				empty = false;
				break;
			}
		}
	
		if(empty) {
			if(lastBubble.getY() == OFFSET) {
				return null;
			} else {
				return dropList;
			}
		}

		for(Bubble b: neighbors) {
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
