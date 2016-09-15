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
import nl.tudelft.bust_a_move20162017.bust_a_move_framework.bubble.Bubble.ColorChoice;

/**
 * The arena class maintains a LinkedList of ArrayList data structure to store the bubble objects. 
 *  
 * @author Winer Bao
 *
 */
public class Arena {
	
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
	private final int OFFSET = DIAMETER / 2;
	
	/**
	 * Creates Arena for bubbles storage and render variables
	 * 
	 * @param xVal		X coordinate of the background
	 * @param yVal		Y coordinate of the background
	 * @param height_t	height of the background
	 * @param width_t	width of the background
	 * 
	 */
	public Arena(int xVal, int yVal, int height_t, int width_t) {
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
	 * Returns the height of the Arena
	 * @return height
	 */
	public int getHeight() {
		return height;
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
		int row = 0;
		int column = 0;
		
		/* Check and add if a new row is needed */
		if(bubble2DArray.size() != (row+1)) {
			if(bubble2DArray.peekLast() == null || bubble2DArray.peekLast().length != WIDTH_BUBBLES ) {
				bubble2DArray.add(new Bubble[WIDTH_BUBBLES]);
			} else {
				bubble2DArray.add(new Bubble[WIDTH_BUBBLES-1]);
			}
		}
		
		row = getRow(shotBubble.getY());
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
	public int getColumn(double xpos, double ypos) {
		int row = getRow(ypos);
		int column = 0;
		if(bubble2DArray.get(row).length == WIDTH_BUBBLES) {
			column = (int)Math.floor((xpos -xPos) / DIAMETER);
		} else {
			if((xpos - xPos) >= OFFSET) {
				column = (int)Math.floor(((xpos - xPos) - OFFSET) / DIAMETER);
			} 
		}
		return column;
	}
	
	/**
	 * A function that calculates the row with a given y position.
	 * @param ypos
	 * @return row number
	 */
	public int getRow(double ypos) {
		return (int)Math.floor((ypos - yPos) / DIAMETER);
	}
	
	/**
	 * Checks if the 2D array is empty. The player wins the level when the arena is empty.
	 * @return True when the array is empty. False when the array is not empty. 
	 * 
	 */
	public boolean isArenaEmtpy() {
		if (bubble2DArray.size() <= 0) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Checks if the 2D array is full. The player losses when the arena is full.
	 * @return True when the array is full. False when the array is not full.
	 */
	public boolean isArenaFull() {
		if (bubble2DArray.size() > 12) {
			return true;
		} else {
			return false;
		}
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
		
		/* Check if 3 or more bubbles are connected */
		if(popList.size() >= 3) {
			for(int i = 0; i < popList.size(); i++) { 
				int row = 0; 
				int column = 0;
				boolean empty = true;
				Bubble bubbleToPop = popList.pop();
				
				dropBubbles(popList);
				bubbleToPop.pop();
				
				row = getRow(popBubble.getY());
				column = getColumn(popBubble.getX(), popBubble.getY());
				bubble2DArray.get(row)[column] = null;
				
				/* Remove row if empty */
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
	 * @param ignoreList Stores the bubbles that is about to pop
	 * 
	 */
	private void dropBubbles(LinkedList<Bubble> ignoreList) {
		LinkedList<Bubble> dropList = new LinkedList<Bubble>(); 
		int row = 0;
		int column = 0;
		boolean empty = true;
		
		for(int i = 0; i < ignoreList.size(); i++) {
			dropList = checkBubblesToDrop(ignoreList.get(i), dropList, ignoreList, ignoreList);
		}
		
		if(dropList == null) {
			return;
		}
		
		for(int i = 0; i < dropList.size(); i++) {
			Bubble bubbleToDrop = dropList.pop();
			bubbleToDrop.drop();

			row = getRow(bubbleToDrop.getY());
			column = getColumn(bubbleToDrop.getX(), bubbleToDrop.getY());
			bubble2DArray.get(row)[column] = null;
			
			/* Remove row if empty */
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
	 * Stores a bubble in the 2D array. This will overwrite the previous bubble at the location.
	 * @param bubble	Bubble to be stored
	 */
	public void addBubble(Bubble bubble) {
		int row = 0;
		int column = 0;
		
		/* Check and add if a new row is needed */
		if(bubble2DArray.size() != (row+1)) {
			if(bubble2DArray.peekLast() == null || bubble2DArray.peekLast().length != WIDTH_BUBBLES ) {
				bubble2DArray.add(new Bubble[WIDTH_BUBBLES]);
			} else {
				bubble2DArray.add(new Bubble[WIDTH_BUBBLES-1]);
			}
		}
		
		row = getRow(bubble.getY());
		column = getColumn(bubble.getX(), bubble.getY());
		
		bubble2DArray.get(row)[column] = bubble;
	}
	
	/**
	 * Adds a new row of bubbles after the cannon shots 10 times. The new
	 * row is added to the top of the Arena and saved in the graph.
	 */
	public void addBubbleRow() {

		if(get_BubbleArray().size() >= HEIGHT_BUBBLES) {
			// Lose the game
			
		}
		int offset = 0;
		Bubble[] bubbleRow;
		if(get_BubbleArray().size() > 0 && get_BubbleArray().getFirst().length == WIDTH_BUBBLES) {
			offset = DIAMETER / 2;
			bubbleRow = new Bubble[WIDTH_BUBBLES - 1];
		}
		else {

			bubbleRow = new Bubble[WIDTH_BUBBLES];
		}
			
		Random rand = new Random();
		// TODO: Store the value of a Random() class somewhere instead of making
		// a new instance every time.
		
		ColorChoice[] colors = ColorChoice.values();
		// TODO: should get a list of currently available colors,
		// therefore we should make a method that returns all colors on the map.
		
		for(int i = 0; i < bubbleRow.length; i++) {
			// 
			int bubbleX = (DIAMETER * i) + offset + xPos;
			int bubbleY = 0 + yPos;
			int colorInt = rand.nextInt(ColorChoice.values().length);
			bubbleRow[i] =  new Bubble(bubbleX, bubbleY, ColorChoice.values()[colorInt]);
		}
		
		// Move all the other bubbles down by diameter
		for(Bubble[] row : bubble2DArray) {
			for(int i = 0; i < row.length; i++){
				if(row[i] == null) continue;
				double currentY = row[i].getY();
				row[i].setY(currentY + (double)DIAMETER);
			}
		}
		
		bubble2DArray.addFirst(bubbleRow);
	}
	
	/**
	 * A function that returns a list of all the colors of bubbles
	 * still on the playing field.
	 * 
	 * @return List<ColorChoice>
	 */
	public LinkedList<ColorChoice> getColorsOnArena() {
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
	 * @param lastBubble	checks the neighbors of this bubble.
	 */
	private LinkedList<Bubble> checkBubblesToPop(Bubble lastBubble, LinkedList<Bubble> popList) {
		Bubble[] neighbors;
		int row = getRow(lastBubble.getY());
		int column = getColumn(lastBubble.getX(), lastBubble.getY());
		boolean empty = true;

		neighbors = getNeighbors(row, column);
		popList.add(lastBubble);
		
		for(Bubble b: neighbors) {
			if(b != null) {
				empty = false;
				break;
			}
		}
	
		if(empty) {
			return popList;
		}
		
		for(Bubble b: neighbors) {
			if(b.getColor() == lastBubble.getColor() 
			   && !popList.contains(b)) {
				popList = checkBubblesToPop(b, popList);
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
	private LinkedList<Bubble> checkBubblesToDrop(Bubble lastBubble, LinkedList<Bubble> dropList, LinkedList<Bubble> ignoreList, LinkedList<Bubble> visitedList) {
		Bubble[] neighbors;
		int row = getRow(lastBubble.getY());
		int column = getColumn(lastBubble.getX(), lastBubble.getY());
		boolean empty = true;
		
		neighbors = getNeighbors(row, column);
		
		for(Bubble b: neighbors) {
			if(b != null && !visitedList.contains(b)) {
				empty = false;
				break;
			}
		}
	
		if(empty) {
			if(lastBubble.getY() != 0) {
				if(!visitedList.contains(lastBubble)) {
					dropList.add(lastBubble);
				}	
			}
			return dropList;
		}
		
		if(!visitedList.contains(lastBubble)) {
			visitedList.add(lastBubble);
		}

		for(Bubble b: neighbors) {
			if(!dropList.contains(b) || !visitedList.contains(b)) {
				dropList = checkBubblesToDrop(b, dropList, ignoreList, visitedList);
			}
		}
		
		for(Bubble b: neighbors) {
			if(dropList.contains(b) && !ignoreList.contains(lastBubble)) {
				dropList.add(lastBubble);
				break;
			}
		}
		
		return dropList;
	}
	
	/**
	 * Retrieves the neighbors of the Bubble 
	 * @param row		row-th entry of the linkedlist
	 * @param column	column-th entry of the array
	 * @return			array with the neighbor bubbles. If a neighbor does not exist, a null pointer is stored.
	 */
	private Bubble[] getNeighbors(int row, int column) {
		Bubble[] neighbors = new Bubble[6];
		
		neighbors[0] = (row != 0) ? (bubble2DArray.get(row-1)[column]) : null;
		neighbors[1] = (row != 0 || column != HEIGHT_BUBBLES) ? (bubble2DArray.get(row-1)[column+1]) : null;
		neighbors[2] = (column != 0) ? (bubble2DArray.get(row)[column-1]) : null;
		neighbors[3] = (column != HEIGHT_BUBBLES) ? (bubble2DArray.get(row)[column+1]) : null;
		neighbors[4] = (row != WIDTH_BUBBLES) ? (bubble2DArray.get(row+1)[column]) : null;
		neighbors[5] = (row != WIDTH_BUBBLES || column != HEIGHT_BUBBLES) ? (bubble2DArray.get(row+1)[column+1]) : null;
		
		return neighbors;
	}
	
	public void draw(Graphics g) {
		for(Bubble[] row : bubble2DArray) {
			for(Bubble b : row) {
				b.draw(g);
			}
		}
	}
	
}
