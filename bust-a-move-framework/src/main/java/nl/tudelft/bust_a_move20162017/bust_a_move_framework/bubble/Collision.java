package nl.tudelft.bust_a_move20162017.bust_a_move_framework.bubble;

import nl.tudelft.bust_a_move20162017.bust_a_move_framework.App;
import nl.tudelft.bust_a_move20162017.bust_a_move_framework.arena.Arena;
import org.newdawn.slick.Graphics;

import java.util.Iterator;
import java.util.LinkedList;

/**
 * Created by Jason Xie on 29/09/2016.
 */
public final class Collision {

    /**
     * Minimum amount of bubbles that need to be connected before pop.
     */
    private final int popAmount = 3;

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
     * The container of this ojbect.
     */
    private Arena arenaCont;


    /**
     * Constructor for creating an object just for checking collisions.
     *
     * @param bubbles container to check collisions on
     * @param arena   container thtat contains this object
     */
    public Collision(final BubbleStorage bubbles, final Arena arena) {
        this.bubbleStorage = bubbles;
        this.arenaCont = arena;
        droppingBubbles = new LinkedList<Bubble>();
        bubbleCount = 0;
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
        double wallPos = arenaCont.get_xPos();
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
                if (bubble != null && shotBubble != bubble) {
                    if (bubble.getState() == Bubble.State.LANDED) {
                        if (shotBubble.getBoundingBox().intersects(bubble.getBoundingBox())) {
                            App.getGame().log.log(this, "Fired Bubble collision! " + shotBubble.getColor() + " with " + bubble.getColor());
                            landBubble(shotBubble);
                            popBubbles(shotBubble);
                            break collisionLoop;
                        }
                    }
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
        //TODO: This should not be calculated here.
        double offset = (bubbleStorage.get(row).length % 2 == 0) ? 0 : Bubble.DIAMETER / 2;
        double xPos = arenaCont.get_xPos() + column * Bubble.DIAMETER + offset;
        double yPos = arenaCont.get_yPos() + row * bubbleStorage.getRowOffset();
        shotBubble.land(xPos, yPos);

        //Is not part of the functionalty that this class offers, should be thus moved.
        bubbleCount++;
        if (bubbleCount > 10) {
            bubbleCount = 0;
            bubbleStorage.addBubbleRow();
        }
    }

    /**
     * Checks for bubbles that can be popped.
     * Requires at least 3 connected bubbles of same colour.
     *
     * @param popBubble The bubble to be popped
     */
    private void popBubbles(final Bubble popBubble) {
        LinkedList<Bubble> popList = new LinkedList<Bubble>();

        popList = checkBubblesToPop(popBubble, popList);

        if (popList.size() >= popAmount) {
            App.getGame().player.score.scoreBubblesPopped((int) popList.size());
            while (popList.size() != 0) {
                Bubble bubbleToPop = popList.pop();
                bubbleStorage.removeBubble(bubbleToPop);
            }
            checkBubblesToDrop();
        }

    }

    /**
     * Checks which bubble needs to be popped using recursive calls.
     *
     * @param lastBubble checks the neighbors of this bubble.
     * @param popList    list of to be popped bubbles
     * @return LinkedList with all bubbles that should be popped.
     */
    private LinkedList<Bubble> checkBubblesToPop(final Bubble lastBubble,
                                                 LinkedList<Bubble> popList) {
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
     *
     * @return LinkedList of bubbles to be dropped.
     */
    private LinkedList<Bubble> checkBubblesToDrop() {
        int dropCount = 0;
        LinkedList<Bubble> visited = new LinkedList<Bubble>();
        App.getGame().log.log("Checking for bubbles to drop");


        if (!bubbleStorage.isEmpty() && bubbleStorage.get(0) != null) {
            for (Bubble b : bubbleStorage.get(0)) {
                visited.add(b);
            }
        }

        // Recursive call
        LinkedList<Bubble> newVisited = checkBubblesToDrop(visited);

        // loop over each bubble to see if newVisited contains it
        // if it does not, remove the bubble
        for (int i = 0; i < bubbleStorage.size(); i++) {
            Bubble[] row = bubbleStorage.get(i);
            for (Bubble bubble : row) {
                if (!newVisited.contains(bubble)) {
                    bubbleStorage.removeBubble(bubble);

                    dropBubble(bubble);

                    dropCount++;
                }
            }
        }
        if (dropCount > 0) {
            App.getGame().player.score.scoreBubblesDropped(dropCount);
        }

        return visited;
    }

    /**
     * Checks for bubbles to drop using a recursive algorithm.
     *
     * @param visited LinkedList with already visited bubbles
     * @return LinkedList with bubbles to be dropped
     */
    private LinkedList<Bubble> checkBubblesToDrop(LinkedList<Bubble> visited) {
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
        }

        // else do a recursive call with the new list
        visited = checkBubblesToDrop(visited);

        return visited;


    }

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
        App.getGame().log.log("" + droppingBubbles.size());
        while (ite.hasNext()) {
            Bubble bubble = ite.next();
            //TODO: change this number to the height of the game window
            if (bubble.getY() > 580) {
                ite.remove();
                continue;
            }
            bubble.setY(bubble.getY() + Bubble.SPEED);
            bubble.draw(g);
        }
    }

}
