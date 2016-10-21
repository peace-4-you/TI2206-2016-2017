/*
 * File: BubbleStorage.java
 *
 * Class: BubbleStorage
 *
 * Version: 0.0.1
 *
 * Date: September 26th, 2016
 *
 */


package bustamove.bubble;

import bustamove.gamestate.GameConfig;
import bustamove.system.Log;
import bustamove.bubble.Bubble.ColorChoice;
import org.newdawn.slick.Graphics;
import bustamove.game.Arena;

import java.util.LinkedList;
import java.util.Random;

/**
 * Created by Jason Xie on 26/09/2016.
 */
public final class BubbleStorage {
    /**
     * BubbleStorage variables.
     */
    private static final int WIDTH_STORAGE = 8;
    private static final int HEIGHT_STORAGE = 12;
    private static final int START_ROWS = 5;
    private static final int NEIGHBOURS_AMOUNT = 6;
    /**
     * Structure that contains all the rows of bubbles.
     */
    private LinkedList<Bubble[]> bubble2DArray;
    /**
     * The x and ycoordinate of the left bound of the arena.
     */
    private int xBound;
    private int yBound;
    /**
     * Container of this storage.
     */
    private Arena arenaCont;

    /**
     * Constructor for bubble storage.
     *
     * @param x     x-coordinate of upper left corner of the arena
     * @param y     y-coordinate of upper left corner of the arena
     * @param arena arena container of this bubblestorage object
     */
    public BubbleStorage(final int x, final int y, final Arena arena) {
        bubble2DArray = new LinkedList<Bubble[]>();
        xBound = x;
        yBound = y;
        arenaCont = arena;
        Log.getInstance().log(this, "Creating a BubbleStorage.");
        for (int i = 0; i < START_ROWS; i++) {
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
     * row is added to the top of the arenaCont and saved in the graph.
     *
     * @param useAllColors boolean for using all colors possible or not.
     */
    private void addBubbleRow(final boolean useAllColors) {

        Log.getInstance().log(this, "Adding a row of bubbles to the storage.");

        Bubble[] bubbleRow = addEmptyBubbleRowAbove();
        double offset = 0;
        if (bubbleRow.length != WIDTH_STORAGE) {
            offset = GameConfig.COLUMN_OFFSET;
        }

        LinkedList<ColorChoice> colors;
        if (!useAllColors) {
            colors = getColorsOnArena();
        } else {
            colors = new LinkedList<ColorChoice>();
            for (ColorChoice c
                    : ColorChoice.values()) {
                colors.add(c);
            }
        }
        Random rand = new Random();
        for (int i = 0; i < bubbleRow.length; i++) {
            //
            int xPos = (int) (Bubble.DIAMETER * i + offset + xBound);
            int yPos = yBound;
            ColorChoice color =
                    colors.get(rand.nextInt(colors.size()));
            bubbleRow[i] = new SimpleBubble(xPos, yPos, color, false);
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
                && bubble2DArray.getLast().length == WIDTH_STORAGE) {
            bubbleRow = new Bubble[WIDTH_STORAGE - 1];
        } else {
            bubbleRow = new Bubble[WIDTH_STORAGE];
        }
        bubble2DArray.addLast(bubbleRow);
        return bubbleRow;
    }

    /**
     * Adds a new row below the current playing field.
     *
     * @return the row added
     */
    private Bubble[] addEmptyBubbleRowAbove() {
        BubbleStorageIterator it = iterator();
        while (it.hasNext()) {
            Bubble curr = it.next();
            float currentY = curr.getY();
            curr.setY(currentY + (float) GameConfig.ROW_OFFSET);
        }

        Bubble[] bubbleRow;
        if (bubble2DArray.size() > 0
                && bubble2DArray.getFirst().length == WIDTH_STORAGE) {
            bubbleRow = new Bubble[WIDTH_STORAGE - 1];
        } else {
            bubbleRow = new Bubble[WIDTH_STORAGE];
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
    public LinkedList<ColorChoice> getColorsOnArena() {
        LinkedList<ColorChoice> colorList =
                new LinkedList<ColorChoice>();

        BubbleStorageIterator it = iterator();
        while (it.hasNext()) {
            Bubble curr = it.next();
            if (!colorList.contains(curr.getColor())) {
                colorList.add(curr.getColor());
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
        return bubble2DArray.size() == 0;
    }

    /**
     * Checks if the 2D array is full. The player losses when the arena is full.
     *
     * @return return boolean indicating whether storage is full or not
     */
    public boolean isFull() {
        return bubble2DArray.size() >= HEIGHT_STORAGE;
    }

    /**
     * Removes a bubble.
     *
     * @param bubble bubble to be removed
     */
    public void removeBubble(final Bubble bubble) {
        for (Bubble[] row : bubble2DArray) {
            boolean stop = false;
            for (int i = 0; i < row.length; i++) {
                if (row[i] == bubble) {
                    row[i] = null;
                    stop = true;
                }
            }
            if (stop) {
                break;
            }
        }
        LinkedList<Bubble[]> rowsToRemove =
                new LinkedList<Bubble[]>();
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
                arenaCont.getCollision().dropBubble(b);
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
        Bubble[] neighbors = new Bubble[NEIGHBOURS_AMOUNT];

        int height = bubble2DArray.size();
        if (height <= row) {
            return neighbors;
        }
        Bubble[] currRow = bubble2DArray.get(row);
        int offset = 1;
        if (currRow.length == WIDTH_STORAGE) {
            offset = 0;
        }

        // Neighbours on upper row
        int index = 0;
        if (row > 0) {
            Bubble[] upperRow = bubble2DArray.get(row - 1);
            if (column - 1 + offset >= 0) {
                neighbors[index] = upperRow[column - 1 + offset];
            }
            index++;
            if (column + offset < upperRow.length) {
                neighbors[index] = upperRow[column + offset];
            }
            index++;
        }

        // Neighbours on same row
        if (column - 1 >= 0) {
            neighbors[index] = currRow[column - 1];
        }
        index++;
        if (column + 1 < currRow.length) {
            neighbors[index] = currRow[column + 1];
        }
        index++;

        // Neighbours on lower row.
        if (row + 1 < height) {
            Bubble[] upperRow = bubble2DArray.get(row + 1);
            if (column - 1 + offset >= 0) {
                neighbors[index] = upperRow[column - 1 + offset];
            }
            index++;
            if (column + offset < upperRow.length) {
                neighbors[index] = upperRow[column + offset];
            }
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
    public int getRow(final double ypos) {
        return (int) Math.round((ypos - yBound) / GameConfig.ROW_OFFSET);
    }

    /**
     * Return the column number based on the given x and y position.
     *
     * @param xpos x coordinate of bubble
     * @param ypos y coordinate of bubble
     * @return column number
     */
    public int getColumn(final double xpos, final double ypos) {
        int row = getRow(ypos);
        int column = 0;

        if (bubble2DArray.peek() == null) {
            return 0;
        }

        int rowWidth = bubble2DArray.get(row).length;
        double relativeX = xpos - xBound;
        if (rowWidth == WIDTH_STORAGE) {
            column = (int) Math.round((relativeX) / Bubble.DIAMETER);
            return Math.min(column, WIDTH_STORAGE - 1);
        } else {
            if (relativeX >= GameConfig.COLUMN_OFFSET) {
                column = (int) Math.round((relativeX - GameConfig.COLUMN_OFFSET)
                        / Bubble.DIAMETER);
            }
            return Math.min(column, (WIDTH_STORAGE - 1) - 1);
        }
    }

    /**
     * Returns the amount of bubbles allowed width wise.
     *
     * @return width of storage
     */
    public int getWidth() {
        return WIDTH_STORAGE;
    }

    /**
     * Returns the amount of bubbles allowed heightwise.
     *
     * @return height of storage
     */
    public int getHeight() {
        return HEIGHT_STORAGE;
    }

    /**
     * Draw all bubbles that are in the bubbleStorage.
     *
     * @param g canvas to draw on
     */
    public void draw(final Graphics g) {
        BubbleStorageIterator it = iterator();
        while (it.hasNext()) {
            Bubble curr = it.next();
            curr.draw(g);
        }
    }

    /**
     * creates a new BubbleStorageIterator.
     *
     * @return a BubbleStorageIterator
     */
    public BubbleStorageIterator iterator() {
        return new BubbleStorageIterator(bubble2DArray);
    }
}