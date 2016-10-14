/*
 * File: Collision.java
 *
 * Class: Collision
 *
 * Version: 0.0.1
 *
 * Date: September 29th, 2016
 *
 */


package bustamove.bubble;

import bustamove.gamestate.GameConfig;
import bustamove.player.Score;
import bustamove.system.Log;
import org.newdawn.slick.Graphics;

import bustamove.App;
import bustamove.game.Arena;
import bustamove.game.GameData;

import java.util.Iterator;
import java.util.LinkedList;

/**
 * This class contains the bubble collision detection algorithm.
 *
 * Created by Jason Xie on 29/09/2016.
 */
public final class Collision {
    /**
     * Minimum amount of bubbles that need to be connected before pop.
     */
    private static final int POP_AMOUNT = 3;
    private static final int ADD_ROW_COUNT = 10;
    /**
     * LinkedList for temporarily storing dropping bubbles.
     */
    private LinkedList<Bubble> droppingBubbles;
    /**
     * Shot bubbles counter for adding a new row on top.
     */
    private int bubbleCount;
    /**
     * The object to check collisions on.
     */
    private BubbleStorage bubbleStorage;
    /**
     * The container of this object.
     */
    private Arena arenaCont;
    private GameData gamehead;
    private Score scorekeeper;

    /**
     * Constructor for creating an object just for checking collisions.
     *
     * @param bubbles container to check collisions on
     * @param arena   container that contains this object
     * @param score   container that keeps the score of a GameData object
     */
    public Collision(final BubbleStorage bubbles, final Arena arena,
                     final Score score) {
        this.bubbleStorage = bubbles;
        this.arenaCont = arena;
        this.scorekeeper = score;
        droppingBubbles = new LinkedList<Bubble>();
        bubbleCount = 0;
    }

    /**
     * Update GameData object.
     *
     * @param game GameData object to store
     */
    public void setGameHead(final GameData game) {
        this.gamehead = game;
    }

    /**
     * Wrapper for checking the collisions.
     *
     * @param bubble Bubble to check collisions on.
     */
    public void checkCollision(final Bubble bubble) {
        checkWallCollision(bubble);
        if (bubble.getY() <= 0) {
            landBubble(bubble);
            popBubbles(bubble);
        }
        checkGridCollision(bubble);
    }

    /**
     * Check collision between bubble and arena wall.
     *
     * @param bubble Bubble to check collisions on.
     */
    private void checkWallCollision(final Bubble bubble) {
        double xPos = bubble.getX();
        double wallPos = arenaCont.getxPos();
        if (xPos <= wallPos
                || xPos + Bubble.DIAMETER >= wallPos + arenaCont.getWidth()) {
            bubble.hitWall();
        }
    }

    /**
     * Check collisions between bubble and the bubbleStorage grid.
     *
     * @param shotBubble bubble that was fired.
     */
    private void checkGridCollision(final Bubble shotBubble) {
        collisionLoop:
        for (int i = 0; i < bubbleStorage.size(); i++) {
            for (int j = 0; j < bubbleStorage.get(i).length; j++) {
                Bubble bubble = bubbleStorage.get(i)[j];
                if (bubble != null && shotBubble != bubble
                        && bubble.getState() == Bubble.State.LANDED
                        && shotBubble.getBoundingBox().intersects(
                        bubble.getBoundingBox())) {
                    Log.log(this, "Fired Bubble collision! "
                            + shotBubble.getColor() + " with "
                            + bubble.getColor());
                    landBubble(shotBubble);
                    popBubbles(shotBubble);
                    break collisionLoop;
                }
            }
        }
    }

    /**
     * Calculates where the shot bubble lands on the  The location is
     * saved in the graph. It also calls the pop bubbles method.
     *
     * @param shotBubble The bubble shot by the cannon
     */
    private void landBubble(final Bubble shotBubble) {
        int row = bubbleStorage.getRow(shotBubble.getY());
        int column = 0;

        while (bubbleStorage.size() <= row) {
            bubbleStorage.addEmptyBubbleRowBelow();
        }

        column = bubbleStorage.getColumn(shotBubble.getX(), shotBubble.getY());
        bubbleStorage.get(row)[column] = shotBubble;

        double offset = GameConfig.COLUMN_OFFSET;
        if (bubbleStorage.get(row).length % 2 == 0) {
            offset = 0;
        }
        float xPos = (float) (column * Bubble.DIAMETER + offset);
        float yPos = (float) (row * GameConfig.ROW_OFFSET);
        shotBubble.land(xPos + arenaCont.getxPos(), yPos + arenaCont.getyPos());

        bubbleCount++;
        if (bubbleCount > ADD_ROW_COUNT) {
            bubbleCount = 0;
            bubbleStorage.addBubbleRow();
        }
    }

    /**
     * Checks for connected bubbles of the same colour.
     * If amount > 3, then they are popped at the end/
     *
     * @param popBubble The bubble to be popped
     */
    public void popBubbles(final Bubble popBubble) {
        LinkedList<Bubble> popList = new LinkedList<Bubble>();

        popList = checkBubblesToPop(popBubble, popList);

        /* Check if 3 or more bubbles are connected */
        if (popList.size() >= POP_AMOUNT) {
          while (popList.size() != 0) {
                Bubble bubbleToPop = popList.pop();

                popBubble(bubbleToPop);
            }
            checkBubblesToDrop();
        }

    }

    /**
     * Pops a single bubble and removes it from the graph.
     *
     * @param bubble Bubble to pop
     */
    public void popBubble(final Bubble bubble) {
        bubble.setGameHead(this.gamehead);
        this.scorekeeper.scoreBubblesPopped(1);
        bubbleStorage.removeBubble(bubble);
        bubble.pop();
    }

    /**
     * Pops a RowBomb, pops a row of bubbles.
     *
     * @param bubble RowBomb to be popped
     */
    public void popRowBomb(final Bubble bubble) {
        int row = bubbleStorage.getRow(bubble.getY());
        if (row < bubbleStorage.size()) {
            for (Bubble b : bubbleStorage.get(row)) {
                if (b != null) {
                    popBubble(b);
                }
            }
        }
        checkBubblesToDrop();
    }

    /**
     * Checks which bubble needs to be popped using recursive calls.
     *
     * @param lastBubble checks the neighbors of this bubble.
     * @param popList    list of to be popped bubbles
     * @return LinkedList with all bubbles that should be popped.
     */
    //CHECKSTYLE:OFF: FinalParameters
    private LinkedList<Bubble> checkBubblesToPop(
            final Bubble lastBubble, LinkedList<Bubble> popList) {
        Bubble[] neighbors;
        double xPos = lastBubble.getX();
        double yPos = lastBubble.getY();
        int row = bubbleStorage.getRow(yPos);
        int column = bubbleStorage.getColumn(xPos, yPos);
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
            if (b != null && b.getColor() == lastBubble.getColor()
                    && !popList.contains(b)) {
                    popList = checkBubblesToPop(b, popList);
            }
        }
        return popList;
    }
    //CHECKSTYLE:ON: FinalParameters

    /**
     * Checks which bubble needs to be dropped using recursive calls.
     *
     * @return LinkedList of bubbles to be dropped.
     */
    private LinkedList<Bubble> checkBubblesToDrop() {
        int dropCount = 0;
        LinkedList<Bubble> visited = new LinkedList<Bubble>();
        Log.log(this, "Checking for bubbles to drop");


        if (!bubbleStorage.isEmpty() && bubbleStorage.get(0) != null) {
            for (Bubble b : bubbleStorage.get(0)) {
                visited.add(b);
            }
        }

        // Recursive call
        LinkedList<Bubble> newVisited = checkBubblesToDrop(visited);

        // loop over each bubble to see if newVisited contains it
        // if it does not, remove the bubble
        BubbleStorageIterator it = bubbleStorage.iterator();
        Bubble curr;
        while (it.hasNext()) {
            curr = it.next();
            if (!newVisited.contains(curr)) {
                bubbleStorage.removeBubble(curr);
                dropBubble(curr);
                dropCount++;
            }
        }
        if (dropCount > 0) {
            scorekeeper.scoreBubblesDropped(dropCount);
        }

        return visited;
    }

    /**
     * Checks for bubbles to drop using a recursive algorithm.
     *
     * @param visited LinkedList with already visited bubbles
     * @return LinkedList with bubbles to be dropped
     */
    //CHECKSTYLE:OFF: FinalParameters
    private LinkedList<Bubble> checkBubblesToDrop(
            LinkedList<Bubble> visited) {
        boolean addedSomething = false;
        for (int i = 0; i < visited.size(); i++) {
            Bubble b = visited.get(i);
            if (b == null) {
                continue;
            }
            int row = bubbleStorage.getRow(b.getY());
            int column = bubbleStorage.getColumn(b.getX(), b.getY());
            Bubble[] neighbours = bubbleStorage.getNeighbors(row, column);
            for (Bubble nb : neighbours) {
                if (!visited.contains(nb)) {
                    visited.addLast(nb);
                    addedSomething = true;
                }
            }
        }

        if (!addedSomething) {
            return visited;
        } else {
            visited = checkBubblesToDrop(visited);
            return visited;
        }
    }
    //CHECKSTYLE:ON: FinalParameters

    /**
     * Drop a bubble.
     *
     * @param bubble Object to be dropped
     */
    public void dropBubble(final Bubble bubble) {
        if (bubble != null && !droppingBubbles.contains(bubble)) {
            bubble.setState(Bubble.State.DROPPING);
            droppingBubbles.add(bubble);
        }
    }

    /**
     * Draw all dropping bubbles.
     *
     * @param g canvas to draw on
     */
    public void draw(final Graphics g) {
        Iterator<Bubble> ite = droppingBubbles.iterator();
        while (ite.hasNext()) {
            Bubble bubble = ite.next();
            if (bubble.getY() > App.getGameHeight()) {
                ite.remove();
                continue;
            }
            bubble.setY(bubble.getY() + (float) bubble.getDropSpeed());
            bubble.draw(g);
        }
    }

}
