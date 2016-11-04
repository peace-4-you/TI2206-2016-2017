package bustamove.bubble;

import bustamove.game.GameData;
import bustamove.player.Score;
import bustamove.system.Log;

import java.util.LinkedList;

/**
 * Created by Jason Xie on 27/10/2016.
 */
public class PopBehaviour {

    private static final int POP_AMOUNT = 3;
    private BubbleStorage bubbleStorage;
    private Score scorekeeper;
    private GameData gamehead;

    /**
     * Constructor for popbehaviour.
     *
     * @param score score to be updated to
     */
    public PopBehaviour(final Score score) {
        this.scorekeeper = score;
    }

    /**
     * Sets the bubblestorage.
     *
     * @param bubbles bubblestorage object.
     */
    public final void setBubbleStorage(final BubbleStorage bubbles) {
        bubbleStorage = bubbles;
    }

    /**
     * Sets the gamehead for the popbehaviour.
     *
     * @param game gamedata object to set to
     */
    public final void setGameHead(final GameData game) {
        gamehead = game;
    }

    /**
     * Checks for connected bubbles of the same colour.
     * If amount > 3, then they are popped at the end/
     *
     * @param popBubble The bubble to be popped
     */
    public final void popBubbles(final Bubble popBubble) {
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
     * Checks which bubble needs to be popped using recursive calls.
     *
     * @param lastBubble checks the neighbors of this bubble.
     * @param popList    list of to be popped bubbles
     * @return LinkedList with all bubbles that should be popped.
     */
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

    /**
     * Checks which bubble needs to be dropped using recursive calls.
     *
     * @return LinkedList of bubbles to be dropped.
     */
    private LinkedList<Bubble> checkBubblesToDrop() {
        int dropCount = 0;
        LinkedList<Bubble> visited = new LinkedList<Bubble>();
        Log.getInstance().log(this, "Checking for bubbles to drop");


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
                bubbleStorage.dropBubble(curr);
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

    /**
     * Pops a single bubble and removes it from the graph.
     *
     * @param bubble Bubble to pop
     */
    public final void popBubble(final Bubble bubble) {
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
    public final void popRowBomb(final Bubble bubble) {
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
     * Pops an OBomb, pops all neighboring bubbles.
     *
     * @param bubble OBomb to be popped
     */
    public final void popOBomb(final Bubble bubble) {
        int col = bubbleStorage.getColumn(bubble.getX(), bubble.getY());
        int row = bubbleStorage.getRow(bubble.getY());
        Bubble[] neighbors = bubbleStorage.getNeighbors(row, col);
        for (Bubble b : neighbors) {
            if (b != null) {
                popBubble(b);
            }
        }
        checkBubblesToDrop();
    }
}