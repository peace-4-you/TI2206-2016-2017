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

import nl.tudelft.bust_a_move20162017.bust_a_move_framework.App;
import nl.tudelft.bust_a_move20162017.bust_a_move_framework.bubble.*;
import nl.tudelft.bust_a_move20162017.bust_a_move_framework.bubble.Bubble.ColorChoice;

/**
 * The arena class maintains a LinkedList of ArrayList data structure to store the bubble objects.
 *
 * @author Winer Bao
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
    private final int DIAMETER = (int) Bubble.DIAMETER;
    private final double OFFSET = ((double)DIAMETER / (double)2.0);

    private LinkedList<Bubble> droppingBubbles = new LinkedList<Bubble>();

    /**
     * Creates Arena for bubbles storage and render variables
     *
     * @param xVal     X coordinate of the background
     * @param yVal     Y coordinate of the background
     * @param height_t height of the background
     * @param width_t  width of the background
     */
    public Arena(int xVal, int yVal, int height_t, int width_t) {
        xPos = xVal;
        yPos = yVal;
        height = height_t;
        width = width_t;
        bubble2DArray = new LinkedList<Bubble[]>();
        bubbleCount = 0;

        App.getGame().log.log("Arena initialised");
        //Level 1
        for (int i = 0; i < 5; i++) {
            addBubbleRow(true);
        }
    }

    /**
     * Sets the x position of the Arena background. This position is the most
     * top-left pixel of the background.
     *
     * @param xVal integer value to set xPos to
     */
    public void set_xPos(int xVal) {
        xPos = xVal;
    }

    /**
     * Returns xPos value
     *
     * @return xPos
     */
    public int get_xPos() {
        return xPos;
    }

    /**
     * Sets the y position of the Arena background. This position is the most
     * top-left pixel of the background.
     *
     * @param yVal integer value to set yPos to
     */
    public void set_yPos(int yVal) {
        yPos = yVal;
    }

    /**
     * Returns yPos value
     *
     * @return yPos
     */
    public int get_yPos() {
        return yPos;
    }

    /**
     * Returns the width
     *
     * @return width
     */
    public int getWidth() {
        return width;
    }

    /**
     * Returns the height of the Arena
     *
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
     */
    public void landBubble(Bubble shotBubble) {
        int row = getRow(shotBubble.getY());
        int column = 0;

		/* Check and add if a new row is needed */
        while (bubble2DArray.size() <= row) {
        	addEmptyBubbleRowBelow();
        }

        column = getColumn(shotBubble.getX(), shotBubble.getY());
        bubble2DArray.get(row)[column] = shotBubble;
        shotBubble.land((double) this.xPos + (column * DIAMETER) + (this.bubble2DArray.get(row).length == WIDTH_BUBBLES ? 0 : DIAMETER / 2), (double) this.yPos + (row * (DIAMETER * Math.tan(60) + OFFSET + 2)));

        // popBubbles(shotBubble);

        bubbleCount++;
        if (bubbleCount > 10) {
            bubbleCount = 0;
            addBubbleRow();
        }
    }

    /**
     * Gets the column
     *
     * @param xpos
     * @param ypos
     * @return
     */
    public int getColumn(double xpos, double ypos) {
        int row = getRow(ypos);
        int column = 0;

        int rowWidth = 0;
        // get the amount of bubbles the row should have
        if(bubble2DArray.peekFirst() == null) return 0; // ERROR: Trying to get a column while there are no rows
        int firstRowLength = bubble2DArray.getFirst().length;
        if(row % 2 == 0) {
        	rowWidth = firstRowLength;
        } else {
        	if(firstRowLength == WIDTH_BUBBLES) {
        		rowWidth = WIDTH_BUBBLES - 1;
        	} else {
        		rowWidth = WIDTH_BUBBLES;
        	}
        }
        if (rowWidth == WIDTH_BUBBLES) {
            column = (int) Math.round((xpos - xPos) / DIAMETER);
            return Math.min(column, WIDTH_BUBBLES - 1);
        } else {
            if ((xpos - xPos) >= OFFSET) {
                column = (int) Math.round(((xpos - xPos) - OFFSET) / DIAMETER);
            }
            return Math.min(column, (WIDTH_BUBBLES - 1) - 1);
        }
    }

    /**
     * A function that calculates the row with a given y position.
     *
     * @param ypos
     * @return row number
     */
    public int getRow(double ypos) {
        return (int) Math.round((ypos - yPos) / (DIAMETER * Math.tan(60) + OFFSET + 2));
    }

    /**
     * Checks if the 2D array is empty. The player wins the level when the arena is empty.
     *
     * @return True when the array is empty. False when the array is not empty.
     */
    public boolean isArenaEmpty() {
        if (bubble2DArray.size() <= 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Checks if the 2D array is full. The player losses when the arena is full.
     *
     * @return True when the array is full. False when the array is not full.
     */
    public boolean isArenaFull() {
        if (bubble2DArray.size() >= 12) {
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
     */
    public void popBubbles(Bubble popBubble) {
        LinkedList<Bubble> popList = new LinkedList<Bubble>();

        popList = checkBubblesToPop(popBubble, popList);

		/* Check if 3 or more bubbles are connected */
        if (popList.size() >= 3) {
            App.getGame().player.getScore().scoreBubblesPopped((int) popList.size());
            while (popList.size() != 0) {
                int row = 0;
                int column = 0;
                boolean empty = true;
                Bubble bubbleToPop = popList.pop();

                //dropBubbles(popList);
                //bubbleToPop.pop();
                removeBubble(bubbleToPop, false);
            }
            checkBubblesToDrop();
        }

    }

    /**
     * Stores a bubble in the 2D array. This will overwrite the previous bubble at the location.
     *
     * @param bubble Bubble to be stored
     */
    public void addBubble(Bubble bubble) {
        int row = getRow(bubble.getY());
        int column = 0;

		/* Check and add if a new row is needed */
        while (bubble2DArray.size() <= row) {
            addEmptyBubbleRowBelow();
        }

        column = getColumn(bubble.getX(), bubble.getY());

        bubble2DArray.get(row)[column] = bubble;
    }


    /**
     * Adds a new row of bubbles to the arena
     */
    public void addBubbleRow() {
        addBubbleRow(false);
    }

    /**
     * Adds a new row of bubbles after the cannon shots 10 times. The new
     * row is added to the top of the Arena and saved in the graph.
     *
     * @param useAllColors
     */
    public void addBubbleRow(boolean useAllColors) {

        App.getGame().log.log("Adding a row of bubbles to the arena");


        Bubble[] bubbleRow = addEmptyBubbleRowAbove();
        double offset = 0;
        if(bubbleRow.length != WIDTH_BUBBLES) offset = OFFSET;

        Random rand = new Random();
        // TODO: Store the value of a Random() class somewhere instead of making
        // a new instance every time.

        LinkedList<ColorChoice> colors;
        if (!useAllColors) colors = getColorsOnArena();
        else {
            colors = new LinkedList<ColorChoice>();
            for (ColorChoice c : ColorChoice.values()) {
                colors.add(c);
            }
        }
        // TODO: should get a list of currently available colors,
        // therefore we should make a method that returns all colors on the map.

        for (int i = 0; i < bubbleRow.length; i++) {
            int bubbleX = (int) ((DIAMETER * i) + offset + xPos);
            int bubbleY = 0 + yPos;
            int colorInt = rand.nextInt(colors.size());
            Bubble bubble = new Bubble(bubbleX, bubbleY, colors.get(colorInt), false);
            bubbleRow[i] = new RowBomb(bubble);
        }
    }

    /**
     * Adds a new row below the current playing field
     *
     * @return the row added
     */
    public Bubble[] addEmptyBubbleRowBelow() {
        Bubble[] bubbleRow;
        if (get_BubbleArray().size() > 0 && get_BubbleArray().getLast().length == WIDTH_BUBBLES) {
            bubbleRow = new Bubble[WIDTH_BUBBLES - 1];
        } else {
            bubbleRow = new Bubble[WIDTH_BUBBLES];
        }
        bubble2DArray.addLast(bubbleRow);
        return bubbleRow;
    }

    /**
     * Adds a new row below the current playing field
     *
     * @return the row added
     */
    public Bubble[] addEmptyBubbleRowAbove() {


        // Move all the other bubbles down by diameter
        for (Bubble[] row : bubble2DArray) {
            for (int i = 0; i < row.length; i++) {
                if (row[i] == null) continue;
                double currentY = row[i].getY();
                row[i].setY(currentY + (((double) DIAMETER * Math.tan(60)) + OFFSET + 2));
            }
        }

        Bubble[] bubbleRow;
        if (get_BubbleArray().size() > 0 && get_BubbleArray().getFirst().length == WIDTH_BUBBLES) {
            bubbleRow = new Bubble[WIDTH_BUBBLES - 1];
        } else {
            bubbleRow = new Bubble[WIDTH_BUBBLES];
        }
        bubble2DArray.addFirst(bubbleRow);
        return bubbleRow;
    }


    /**
     * Removes a bubble
     *
     * @param bubble
     */
    public void removeBubble(Bubble bubble, boolean dropping) {

        for (Bubble[] row : bubble2DArray) {
            boolean finished = false;
            if (finished) break;
            for (int i = 0; i < row.length; i++) {
                if (row[i] == bubble) {
                    row[i] = null;
                    finished = true;
                }
            }
        }
        LinkedList<Bubble[]> rowsToRemove = new LinkedList<Bubble[]>();
        // remove empty rows
        boolean alreadyRemovedRow = false;
        for (Bubble[] row : bubble2DArray) {
            boolean empty = true;
            for (Bubble b : row) {
                if (b != null) empty = false;
            }
            if (empty || alreadyRemovedRow) {
            	rowsToRemove.add(row);
            	alreadyRemovedRow = true;
            }
        }

        for (Bubble[] row : rowsToRemove) {
        	for(Bubble b : row) {
        		dropBubble(b);
        	}
            bubble2DArray.remove(row);
        }

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
        for (Bubble[] row : bubble2DArray) {
            for (Bubble bub : row) {
                if (bub != null && !colorList.contains(bub.getColor())) {
                    colorList.add(bub.getColor());
                }
            }
        }
        return colorList;
    }

    /**
     * Checks which bubble needs to be popped using recursive calls.
     *
     * @param lastBubble checks the neighbors of this bubble.
     */
    private LinkedList<Bubble> checkBubblesToPop(Bubble lastBubble, LinkedList<Bubble> popList) {
        Bubble[] neighbors;
        int row = getRow(lastBubble.getY());
        int column = getColumn(lastBubble.getX(), lastBubble.getY());
        boolean empty = true;

        neighbors = getNeighbors(row, column);
        popList.add(lastBubble);

        for (Bubble b : neighbors) {
            if (b != null) {
                empty = false;
                break;
            }
        }

        if (empty) {
            return popList;
        }

        for (Bubble b : neighbors) {
            if (b != null) {
                if (b.getColor() == lastBubble.getColor()
                        && !popList.contains(b)) {
                    popList = checkBubblesToPop(b, popList);
                }
            }
        }
        return popList;
    }

    /**
     * Checks which bubble needs to be dropped using recursive calls.
     */
    public LinkedList<Bubble> checkBubblesToDrop() {
        int dropped_bubbles = 0;
        LinkedList<Bubble> visited = new LinkedList<Bubble>();
        App.getGame().log.log("Checking for bubbles to drop");


        if (!bubble2DArray.isEmpty() && bubble2DArray.get(0) != null) {
            for (Bubble b : bubble2DArray.get(0)) {
                visited.add(b);
            }
        }

        // now do recursive call
        LinkedList<Bubble> newVisited = checkBubblesToDrop(visited);

        // loop over each bubble to see if newVisited contains it
        // if it does not, remove the bubble
        for (int i = 0; i < bubble2DArray.size(); i++) {
            Bubble[] row = bubble2DArray.get(i);
            for (Bubble bubble : row) {
                if (!newVisited.contains(bubble)) {
                    removeBubble(bubble, true);

                    dropBubble(bubble);

                    dropped_bubbles++;
                }
            }
        }
        if (dropped_bubbles > 0) {
            App.getGame().player.getScore().scoreBubblesDropped(dropped_bubbles);
        }

        return visited;
        //return null;
    }


    public void dropBubble(Bubble bubble) {
    	if(bubble != null && !droppingBubbles.contains(bubble)) {
            bubble.setState(Bubble.State.DROPPING);
            droppingBubbles.add(bubble);
    	}
    }

    /**
     * Checks for bubbles to drop using a recursive algorithm
     *
     * @param visited
     * @return
     */
    private LinkedList<Bubble> checkBubblesToDrop(LinkedList<Bubble> visited) {
        boolean addedSomething = false;
        for (int i = 0; i < visited.size(); i++) {
            Bubble b = visited.get(i);
            if (b == null) continue;
            Bubble[] neighbours = getNeighbors(getRow(b.getY()), getColumn(b.getX(), b.getY()));
            for (Bubble nb : neighbours) {
                if (!visited.contains(nb)) {
                    visited.addLast(nb);
                    addedSomething = true;
                }
            }
        }

        if (!addedSomething) return visited;

        // else do a recursive call with the new list
        visited = checkBubblesToDrop(visited);

        return visited;


    }

    /**
     * Retrieves the neighbors of the Bubble
     *
     * @param row    row-th entry of the linkedlist
     * @param column column-th entry of the array
     * @return array with the neighbor bubbles. If a neighbor does not exist, a null pointer is stored.
     */
    private Bubble[] getNeighbors(int row, int column) {
        Bubble[] neighbors = new Bubble[6];

        int offset = (bubble2DArray.get(row).length == WIDTH_BUBBLES) ? 0 : 1;
        // UP-LEFT neighbour
        try {
        	neighbors[0] = bubble2DArray.get(row - 1)[column - 1 + offset];
        } catch(Exception e) {
        	//System.out.println("top left neighbour of ("+row+","+column+") does not exist");
        }

        // UP-RIGHT neighbour
        try {
        	neighbors[1] = bubble2DArray.get(row - 1)[column + offset];
        } catch(Exception e) {
        	//System.out.println("top right neighbour of ("+row+","+column+") does not exist");
        }

        // LEFT neighbour
        try {
        	neighbors[2] = bubble2DArray.get(row)[column - 1];
        } catch(Exception e) {
        	//System.out.println("left neighbour of ("+row+","+column+") does not exist");
        }

        // RIGHT neighbour
        try {
        	neighbors[3] = bubble2DArray.get(row)[column + 1];
        } catch(Exception e) {
        	//System.out.println("right neighbour of ("+row+","+column+") does not exist");
        }

        // BOTTOM-LEFT neighbour
        try {
        	neighbors[4] = bubble2DArray.get(row + 1)[column - 1 + offset];
        } catch(Exception e) {
        	//System.out.println("bottom left neighbour of ("+row+","+column+") does not exist");
        }

        // BOTTOM-RIGHT neighbour
        try {
        	neighbors[5] = bubble2DArray.get(row + 1)[column + offset];
        } catch(Exception e) {
        	//System.out.println("bottom right neighbour of ("+row+","+column+") does not exist");
        }
        // OLD METHOD
        /*
        int height = bubble2DArray.size();
        if (height <= row) {
            return neighbors;
        }
        int width = bubble2DArray.get(row).length;

        neighbors[0] = (column != 0) ? (bubble2DArray.get(row)[column - 1]) : null;
        neighbors[3] = (column < width - 1) ? (bubble2DArray.get(row)[column + 1]) : null;

        //TODO: Make this pretty and get rid of magic numbers.
        if (width == 8) {
            neighbors[1] = (row != 0 && column != 0) ? (bubble2DArray.get(row - 1)[column - 1]) : null;
            neighbors[2] = (row != 0 && column < width - 1) ? (bubble2DArray.get(row - 1)[column]) : null;
            neighbors[4] = (row < height - 1 && column < width - 1) ? (bubble2DArray.get(row + 1)[column]) : null;
            neighbors[5] = (row < height - 1 && column != 0) ? (bubble2DArray.get(row + 1)[column - 1]) : null;
        } else {
            neighbors[1] = (row != 0 && column < WIDTH_BUBBLES) ? (bubble2DArray.get(row - 1)[column]) : null;
            neighbors[2] = (row != 0 && column < width - 1) ? (bubble2DArray.get(row - 1)[column + 1]) : null;
            neighbors[4] = (row < height - 1 && column < width - 1) ? (bubble2DArray.get(row + 1)[column + 1]) : null;
            neighbors[5] = (row < height - 1) ? (bubble2DArray.get(row + 1)[column]) : null;
        }*/

        return neighbors;
    }

    public void draw(Graphics g) {
        for (Bubble[] row : bubble2DArray) {
            for (Bubble b : row) {
                if (b != null) {
                    b.draw(g);
                }
            }
        }
        for(Bubble dropBubble : droppingBubbles) {
        	if(dropBubble.getY() > 1000) continue;// TODO: remove the bubble from the list
        	dropBubble.setY(dropBubble.getY() + 3);
        	dropBubble.draw(g);
        }
        g.setColor(Color.white);
        g.drawRect((float) xPos, (float) yPos, (float) width, (float) height);
        float yPosLine = (float) (HEIGHT_BUBBLES * ((DIAMETER * Math.tan(60)) + OFFSET + 2) + yPos + 5);
        g.drawLine((float) xPos, yPosLine, (float) xPos + width, yPosLine);
    }
}
