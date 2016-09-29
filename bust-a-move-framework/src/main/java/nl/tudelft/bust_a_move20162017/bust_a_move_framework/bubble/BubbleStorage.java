package nl.tudelft.bust_a_move20162017.bust_a_move_framework.bubble;

import nl.tudelft.bust_a_move20162017.bust_a_move_framework.App;
import nl.tudelft.bust_a_move20162017.bust_a_move_framework.arena.Arena;
import org.newdawn.slick.Graphics;

import java.util.LinkedList;
import java.util.Random;

/**
 * Created by Jason Xie on 26/09/2016.
 */
public final class BubbleStorage {

    /**
     * Max amount of columns width wise.
     */
    public final int widthStorage = 8;
    /**
     * Max amount of rows height wise.
     */
    public final int heightStorage = 12;
    /**
     * Starting amount of rows.
     */
    private final int startRows = 5;

    /**
     * Diameter of a bubble.
     */
    private final int diameter = (int) Bubble.DIAMETER;
    /**
     * The radius of a bubble.
     */
    private final double bubbleOffset = Bubble.DIAMETER / 2;

    /**
     * The offset of Y between each row.
     */
    public final double rowOffset =
            (double) diameter * Math.tan(60) + bubbleOffset + 2;

    /**
     * Structure that contains all the rows of bubbles.
     */
    private LinkedList<Bubble[]> bubble2DArray;

    /**
     * The x coordinate of the left bound of the arena.
     */
    private int xBound;
    /**
     * The y coordinate of the upper bound of the arean.
     */
    private int yBound;

    private Arena arena;

    /**
     * Constructor for bubble storage.
     *
     * @param x x-coordinate of upper left corner of the arena
     * @param y y-coordinate of upper left corner of the arena
     */
    public BubbleStorage(int x, int y, Arena are) {
        bubble2DArray = new LinkedList<Bubble[]>();
        xBound = x;
        yBound = y;
        arena = are;
        App.getGame().log.log("Creating a BubbleStorage.");
        for (int i = 0; i < startRows; i++) {
            addBubbleRow(true);
        }
    }

    /**
     * Adds a new row of bubbles to the arena.
     */
    public void addBubbleRow() {
        addBubbleRow(false);
    }

    /**
     * Adds a new row of bubbles after the cannon shots 10 times. The new
     * row is added to the top of the Arena and saved in the graph.
     *
     * @param useAllColors boolean for using all colors possible or not.
     */
    public void addBubbleRow(boolean useAllColors) {

        App.getGame().log.log("Adding a row of bubbles to the storage.");

        Bubble[] bubbleRow = addEmptyBubbleRowAbove();
        double offset = 0;
        if (bubbleRow.length != widthStorage) {
            offset = this.bubbleOffset;
        }

        LinkedList<Bubble.ColorChoice> colors;
        if (!useAllColors) {
            colors = getColorsOnArena();
        } else {
            colors = new LinkedList<Bubble.ColorChoice>();
            for (Bubble.ColorChoice c : Bubble.ColorChoice.values()) {
                colors.add(c);
            }
        }
        // TODO: should get a list of currently available colors
        Random rand = new Random();
        for (int i = 0; i < bubbleRow.length; i++) {
            //
            int xPos = (int) ((diameter * i) + offset + xBound);
            int yPos = 0 + yBound;
            Bubble.ColorChoice color = colors.get(rand.nextInt(colors.size()));
            bubbleRow[i] = new Bubble(xPos, yPos, color, false);
        }
    }

    /**
     * Adds a new row below the current playing field.
     *
     * @return the row added
     */
    public Bubble[] addEmptyBubbleRowBelow() {
        Bubble[] bubbleRow;
        if (bubble2DArray.size() > 0
                && bubble2DArray.getLast().length == widthStorage) {
            bubbleRow = new Bubble[widthStorage - 1];
        } else {
            bubbleRow = new Bubble[widthStorage];
        }
        bubble2DArray.addLast(bubbleRow);
        return bubbleRow;
    }

    /**
     * Adds a new row below the current playing field.
     *
     * @return the row added
     */
    public Bubble[] addEmptyBubbleRowAbove() {
        // Move all the other bubbles down by diameter
        for (Bubble[] row : bubble2DArray) {
            for (int i = 0; i < row.length; i++) {
                if (row[i] == null) {
                    continue;
                }
                double currentY = row[i].getY();
                row[i].setY(currentY + rowOffset);
            }
        }

        Bubble[] bubbleRow;
        if (bubble2DArray.size() > 0
                && bubble2DArray.getFirst().length == widthStorage) {
            bubbleRow = new Bubble[widthStorage - 1];
        } else {
            bubbleRow = new Bubble[widthStorage];
        }
        bubble2DArray.addFirst(bubbleRow);
        return bubbleRow;
    }

    /**
     * A function that returns a list of all the colors of bubbles.
     * still on the playing field.
     *
     * @return List<ColorChoice>
     */
    public LinkedList<Bubble.ColorChoice> getColorsOnArena() {
        // TODO: add Colorlist to the variable on top.
        LinkedList<Bubble.ColorChoice> colorList =
                new LinkedList<Bubble.ColorChoice>();

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
     * Checks if the 2D array is empty.
     * The player wins the level when the arena is empty.
     *
     * @return return boolean indicating whether storage is empty or not
     */
    public boolean isEmpty() {
        return bubble2DArray.size() <= 0;
    }

    /**
     * Checks if the 2D array is full. The player losses when the arena is full.
     *
     * @return return boolean indicating whether storage is full or not
     */

    public boolean isFull() {
        return bubble2DArray.size() >= heightStorage;
    }

    /**
     * Removes a bubble.
     *
     * @param bubble bubble to be removed
     */
    public void removeBubble(Bubble bubble) {
        //TODO: Remove dropbubble from here. This method should be called after

        for (Bubble[] row : bubble2DArray) {
            boolean finished = false;
            if (finished) {
                break;
            }
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
                if (b != null) {
                    empty = false;
                }
            }
            if (empty || alreadyRemovedRow) {
                rowsToRemove.add(row);
                alreadyRemovedRow = true;
            }
        }

        for (Bubble[] row : rowsToRemove) {
            for (Bubble b : row) {
                arena.getCollision().dropBubble(b);
            }
            bubble2DArray.remove(row);
        }
    }

    /**
     * Retrieves the neighbors of the Bubble.
     *
     * @param row    row-th entry of the linkedlist
     * @param column column-th entry of the array
     * @return array of surrounding bubble neighbours
     */
    public Bubble[] getNeighbors(final int row, final int column) {
        //TODO: Create pattern
        Bubble[] neighbors = new Bubble[6];

        int height = bubble2DArray.size();
        if (height <= row) {
            return neighbors;
        }
        int offset = (bubble2DArray.get(row).length == widthStorage) ? 0 : 1;
        // UP-LEFT neighbour
        try {
            neighbors[0] = bubble2DArray.get(row - 1)[column - 1 + offset];
        } catch (Exception e) {
            //System.out.println("top left neighbour of ("+row+","+column+") does not exist");
        }

        // UP-RIGHT neighbour
        try {
            neighbors[1] = bubble2DArray.get(row - 1)[column + offset];
        } catch (Exception e) {
            //System.out.println("top right neighbour of ("+row+","+column+") does not exist");
        }

        // LEFT neighbour
        try {
            neighbors[2] = bubble2DArray.get(row)[column - 1];
        } catch (Exception e) {
            //System.out.println("left neighbour of ("+row+","+column+") does not exist");
        }

        // RIGHT neighbour
        try {
            neighbors[3] = bubble2DArray.get(row)[column + 1];
        } catch (Exception e) {
            //System.out.println("right neighbour of ("+row+","+column+") does not exist");
        }

        // BOTTOM-LEFT neighbour
        try {
            neighbors[4] = bubble2DArray.get(row + 1)[column - 1 + offset];
        } catch (Exception e) {
            //System.out.println("bottom left neighbour of ("+row+","+column+") does not exist");
        }

        // BOTTOM-RIGHT neighbour
        try {
            neighbors[5] = bubble2DArray.get(row + 1)[column + offset];
        } catch (Exception e) {
            //System.out.println("bottom right neighbour of ("+row+","+column+") does not exist");
        }

        return neighbors;
    }


    /**
     * Return a Bubble[] row.
     *
     * @param n index of row to get
     * @return Bubble[] is n is valid, else null.
     */
    public Bubble[] get(final int n) {
        if (n < bubble2DArray.size()) {
            return bubble2DArray.get(n);
        }
        return null;
    }

    /**
     * Returns the height of the storage.
     *
     * @return height of storage
     */
    public int size() {
        return bubble2DArray.size();
    }

    /**
     * Return the row number based on given y position.
     *
     * @param ypos y coordinate of a bubble
     * @return row number
     */
    public int getRow(double ypos) {
        return (int) Math.round((ypos - yBound) / rowOffset);
    }

    /**
     * Return the column number based on the given x and y position.
     * TODO: refactor to using Point class when Bubble has been refactored.
     *
     * @param xpos x coordinate of bubble
     * @param ypos y coordinate of bubble
     * @return column number
     */
    public int getColumn(double xpos, double ypos) {
        int row = getRow(ypos);
        int column = 0;

        int rowWidth = 0;
        if (bubble2DArray.peekFirst() == null) {
            return 0;
        }

        int firstRowLength = bubble2DArray.getFirst().length;
        if (row % 2 == 0) {
            rowWidth = firstRowLength;
        } else {
            if (firstRowLength == widthStorage) {
                rowWidth = widthStorage - 1;
            } else {
                rowWidth = widthStorage;
            }
        }
        double relativeX = xpos - xBound;
        if (rowWidth == widthStorage) {
            column = (int) Math.round((relativeX) / diameter);
            return Math.min(column, widthStorage - 1);
        } else {
            if (relativeX >= bubbleOffset) {
                column = (int) Math.round((relativeX - bubbleOffset) / diameter);
            }
            return Math.min(column, (widthStorage - 1) - 1);
        }
    }

    /**
     * Draw all bubbles that are in the bubbleStorage.
     *
     * @param g canvas to draw on
     */
    public void draw(Graphics g) {
        for (Bubble[] row : bubble2DArray) {
            for (Bubble b : row) {
                if (b != null) {
                    b.draw(g);
                }
            }
        }
    }
}
