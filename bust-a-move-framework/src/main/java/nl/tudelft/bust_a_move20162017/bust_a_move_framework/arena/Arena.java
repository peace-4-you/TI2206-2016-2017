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

import java.util.LinkedList;

import nl.tudelft.bust_a_move20162017.bust_a_move_framework.bubble.BubbleStorage;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import nl.tudelft.bust_a_move20162017.bust_a_move_framework.App;
import nl.tudelft.bust_a_move20162017.bust_a_move_framework.bubble.Bubble;

/**
 * TODO: new description for functioning of this class
 * The arena class maintains a LinkedList of ArrayList data structure to store the bubble objects.
 *
 * @author Winer Bao
 */
public class Arena {

    private int xPos;
    private int yPos;
    private int height;
    private int width;
    private BubbleStorage bubbleStorage;


    /* Keep track how many bubbleStorage are shot */
    private int bubbleCount;

    /* Temporarily Bubble diameter variable */
    private final int DIAMETER = (int) Bubble.DIAMETER;
    private final double OFFSET = ((double) DIAMETER / (double) 2.0);
    /* Max amount of bubbleStorage that fit in the vertical axis */
    private final int HEIGHT_BUBBLES = 12;

    private LinkedList<Bubble> droppingBubbles = new LinkedList<Bubble>();

    /**
     * Creates Arena for bubbleStorage storage and render variables
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
        bubbleStorage = new BubbleStorage(xPos, yPos);
        bubbleCount = 0;

        App.game.log.log("Arena initialised");
        //Level 1

    }

    public BubbleStorage getBubbles() {
        return bubbleStorage;
    }

    /**
     * Calculates where the shot bubble lands on the arena. The location is
     * saved in the graph. It also calls the pop bubbleStorage method.
     *
     * @param shotBubble The bubble shot by the cannon
     */
    public void landBubble(Bubble shotBubble) {
        int row = bubbleStorage.getRow(shotBubble.getY());
        int column = 0;

		/* Check and add if a new row is needed */
        while (bubbleStorage.size() <= row) {
            bubbleStorage.addEmptyBubbleRowBelow();
        }

        column = bubbleStorage.getColumn(shotBubble.getX(), shotBubble.getY());
        bubbleStorage.get(row)[column] = shotBubble;
        shotBubble.land((double) this.xPos + (column * DIAMETER) + (this.bubbleStorage.get(row).length == WIDTH_BUBBLES ? 0 : DIAMETER / 2), (double) this.yPos + (row * (DIAMETER * Math.tan(60) + OFFSET + 2)));

        // popBubbles(shotBubble);

        bubbleCount++;
        if (bubbleCount > 10) {
            bubbleCount = 0;
            bubbleStorage.addBubbleRow();
        }
    }


    /**
     * Checks for 3 (or more) connected bubbleStorage. If so, it signals the bubble objects to
     * pop and removes it from the graph. It also calls the drop bubbleStorage method.
     *
     * @param popBubble The bubble to be popped
     */
    public void popBubbles(Bubble popBubble) {
        LinkedList<Bubble> popList = new LinkedList<Bubble>();

        popList = checkBubblesToPop(popBubble, popList);

		/* Check if 3 or more bubbleStorage are connected */
        if (popList.size() >= 3) {
            App.game.player.score.scoreBubblesPopped((int) popList.size());
            while (popList.size() != 0) {
                int row = 0;
                int column = 0;
                boolean empty = true;
                Bubble bubbleToPop = popList.pop();

                //dropBubbles(popList);
                //bubbleToPop.pop();
                bubbleStorage.removeBubble(bubbleToPop, false);
            }
            checkBubblesToDrop();
        }

    }

    /**
     * Checks which bubble needs to be popped using recursive calls.
     *
     * @param lastBubble checks the neighbors of this bubble.
     */
    private LinkedList<Bubble> checkBubblesToPop(Bubble lastBubble, LinkedList<Bubble> popList) {
        Bubble[] neighbors;
        int row = bubbleStorage.getRow(lastBubble.getY());
        int column = bubbleStorage.getColumn(lastBubble.getX(), lastBubble.getY());
        boolean empty = true;

        neighbors = bubbleStorage.getNeighbors(row, column);
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
        App.game.log.log("Checking for bubbleStorage to drop");


        if (!bubbleStorage.isEmpty() && bubbleStorage.get(0) != null) {
            for (Bubble b : bubbleStorage.get(0)) {
                visited.add(b);
            }
        }

        // now do recursive call
        LinkedList<Bubble> newVisited = checkBubblesToDrop(visited);

        // loop over each bubble to see if newVisited contains it
        // if it does not, remove the bubble
        for (int i = 0; i < bubbleStorage.size(); i++) {
            Bubble[] row = bubbleStorage.get(i);
            for (Bubble bubble : row) {
                if (!newVisited.contains(bubble)) {
                    bubbleStorage.removeBubble(bubble, true);

                    dropBubble(bubble);

                    dropped_bubbles++;
                }
            }
        }
        if (dropped_bubbles > 0) {
            App.game.player.score.scoreBubblesDropped(dropped_bubbles);
        }
        return visited;
    }


    public void dropBubble(Bubble bubble) {
        if (bubble != null && !droppingBubbles.contains(bubble)) {
            bubble.setState(Bubble.State.DROPPING);
            droppingBubbles.add(bubble);
        }
    }

    /**
     * Checks for bubbleStorage to drop using a recursive algorithm.
     *
     * @param visited List of already visisted bubbles
     * @return list of bubbles that might get dropped.
     */
    private LinkedList<Bubble> checkBubblesToDrop(LinkedList<Bubble> visited) {
        boolean addedSomething = false;
        for (int i = 0; i < visited.size(); i++) {
            Bubble b = visited.get(i);
            if (b == null) continue;
            int row = bubbleStorage.getRow(b.getX());
            int col = bubbleStorage.getColumn(b.getX(), b.getY());
            Bubble[] neighbours = bubbleStorage.getNeighbors(row, col));
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
     * Returns the total amount of bubbleStorage in the storage.
     *
     * @return bubbleCount
     */
    public int getBubbleCount() {
        return bubbleCount;
    }

    /**
     * Draw the boundary and the defeat line.
     *
     * @param g canvas to draw on.
     */
    public void draw(Graphics g) {
        bubbleStorage.draw(g);
        g.setColor(Color.white);
        g.drawRect((float) xPos, (float) yPos, (float) width, (float) height);
        float yPosLine = (float) (HEIGHT_BUBBLES * ((DIAMETER * Math.tan(60)) + OFFSET + 2) + yPos + 5);
        g.drawLine((float) xPos, yPosLine, (float) xPos + width, yPosLine);
    }
}
