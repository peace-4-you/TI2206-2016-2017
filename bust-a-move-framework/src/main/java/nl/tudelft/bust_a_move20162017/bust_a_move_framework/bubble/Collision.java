package nl.tudelft.bust_a_move20162017.bust_a_move_framework.bubble;

import nl.tudelft.bust_a_move20162017.bust_a_move_framework.App;
import nl.tudelft.bust_a_move20162017.bust_a_move_framework.arena.Arena;
import org.newdawn.slick.Graphics;

import java.util.LinkedList;

/**
 * Created by Jason Xie on 29/09/2016.
 */
public class Collision {

    private BubbleStorage bubbleStorage;
    private Arena arena;
    private LinkedList<Bubble> droppingBubbles = new LinkedList<Bubble>();
    private int bubbleCount;

    public Collision(BubbleStorage bubbleStorage, Arena arena) {
        this.bubbleStorage = bubbleStorage;
        this.arena = arena;
        bubbleCount = 0;

    }

    public void checkCollision(Bubble bubble) {
        checkWallCollision(bubble);
        if (bubble.getY() <= 0) {
            landBubble(bubble);
            popBubbles(bubble);
        }
        checkGridCollision(bubble);
    }

    private void checkWallCollision(Bubble bubble) {
        if (bubble.getX() <= arena.get_xPos()
                || bubble.getX() + Bubble.DIAMETER >= arena.get_xPos() + arena.getWidth()) {
            bubble.hitWall();
        }
    }

    private void checkGridCollision(Bubble shotBUbble) {
        collisionLoop:
        for (int i = 0; i < bubbleStorage.size(); i++) {
            for (int j = 0; j < bubbleStorage.get(i).length; j++) {
                Bubble bubble = bubbleStorage.get(i)[j];
                if (bubble != null && shotBUbble != bubble) {
                    if (bubble.getState() == Bubble.State.LANDED) {
                        if (shotBUbble.getBoundingBox().intersects(bubble.getBoundingBox())) {
                            App.getGame().log.log(this, "Fired Bubble collision! " + shotBUbble.getColor() + " with " + bubble.getColor());
                            landBubble(shotBUbble);
                            popBubbles(shotBUbble);
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
    public void landBubble(Bubble shotBubble) {
        int row = bubbleStorage.getRow(shotBubble.getY());
        int column = 0;

		/* Check and add if a new row is needed */
        while (bubbleStorage.size() <= row) {
            bubbleStorage.addEmptyBubbleRowBelow();
        }

        column = bubbleStorage.getColumn(shotBubble.getX(), shotBubble.getY());
        bubbleStorage.get(row)[column] = shotBubble;
        shotBubble.land((double) arena.get_xPos() + (column * Bubble.DIAMETER) + (bubbleStorage.get(row).length == bubbleStorage.widthStorage ? 0 : Bubble.DIAMETER / 2), (double) arena.get_yPos() + (row * bubbleStorage.rowOffset));

        // popBubbles(shotBubble);

        bubbleCount++;
        if (bubbleCount > 10) {
            bubbleCount = 0;
            bubbleStorage.addBubbleRow();
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
        App.getGame().log.log("Checking for bubbles to drop");


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
                    bubbleStorage.removeBubble(bubble);

                    dropBubble(bubble);

                    dropped_bubbles++;
                }
            }
        }
        if (dropped_bubbles > 0) {
            App.getGame().player.score.scoreBubblesDropped(dropped_bubbles);
        }

        return visited;
        //return null;
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
            Bubble[] neighbours = bubbleStorage.getNeighbors(bubbleStorage.getRow(b.getY()), bubbleStorage.getColumn(b.getX(), b.getY()));
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

    public void dropBubble(Bubble bubble) {
        if (bubble != null && !droppingBubbles.contains(bubble)) {
            bubble.setState(Bubble.State.DROPPING);
            droppingBubbles.add(bubble);
        }
    }

    public void draw(Graphics g) {
        for (Bubble dropBubble : droppingBubbles) {
            //TODO: change 1000, to use the height of the windows/arena
            if (dropBubble.getY() > 1000) {
                continue;
            }
            // TODO: remove the bubble from the list
            dropBubble.setY(dropBubble.getY() + Bubble.SPEED);
            dropBubble.draw(g);
        }
    }

}
