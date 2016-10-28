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

import bustamove.screen.config.GameConfig;
import bustamove.system.Log;
import bustamove.game.Arena;

/**
 * This class contains the bubble collision detection algorithm.
 * <p>
 * Created by Jason Xie on 29/09/2016.
 */
public final class Collision {
    /**
     * Count for when a new row should be added.
     */
    private static final int ADD_ROW_COUNT = 10;

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
    private PopBehaviour popBehaviour;

    /**
     * Constructor for creating an object just for checking collisions.
     *
     * @param arena container that contains this object
     * @param pop   object that contains the pop behaviours.
     */
    public Collision(final Arena arena, final BubbleStorage bubbles, final PopBehaviour pop) {
        this.arenaCont = arena;
        bubbleStorage = bubbles;
        this.popBehaviour = pop;
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
            popBehaviour.popBubbles(bubble);
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
                || xPos + Bubble.DIAMETER
                >= wallPos + arenaCont.getWidth()) {
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
                    Log.getInstance().log(this, "Fired Bubble collision! "
                            + shotBubble.getColor() + " with "
                            + bubble.getColor());
                    landBubble(shotBubble);
                    popBehaviour.popBubbles(shotBubble);
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

}
