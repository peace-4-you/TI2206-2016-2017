/*
 * File: cannon.java
 * Class: Cannon
 *
 * Version: 0.0.3
 * Date: September 12th, 2016
 */

package nl.tudelft.bust_a_move20162017.bust_a_move_framework.cannon;

import nl.tudelft.bust_a_move20162017.bust_a_move_framework.bubble.Bubble;
import nl.tudelft.bust_a_move20162017.bust_a_move_framework.bubble.PowerUp;
import nl.tudelft.bust_a_move20162017.bust_a_move_framework.game.Game;
import nl.tudelft.bust_a_move20162017.bust_a_move_framework.gamestate.GameConfig;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;

/**
 * The Cannon class represents a cannon entity.
 *
 * @author Maurice Willemsen
 */

public class Cannon {
    /**
     * X coordinate of the cannon.
     */
    private final int x = 320;
    /**
     * Y coordinate of the cannon.
     */
    private final int y = 530;
    /**
     * X coordinate of the next bubble.
     */
    private final int xLaunch = (int) (375 - Bubble.DIAMETER / 2);
    /**
     * Y coordinate of the next bubble.
     */
    private final int yLaunch = (int) (555 - Bubble.DIAMETER / 2);
    /**
     * X coordinate of the current bubble.
     */
    private final int xLoad  = (int) (320 - Bubble.DIAMETER / 2);
    /**
     * Y coordinate of the current bubble.
     */
    private final int yLoad = (int) (530 - Bubble.DIAMETER / 2);
    /**
     * The size/length of the cannon barrel.
     */
    private final int size = 80;
    /**
     * The color of the cannon barrel.
     */
    private Color cannonColour;
    /**
     * The angle of the cannon.
     */
    private int angle = 0;
    /**
     * Current bubble in the cannon.
     */
    private Bubble currBubble;
    /**
     * Next bubble in line.
     */
    private Bubble nextBubble;
    /**
     * Game reference.
     */
    private Game game;
    /**
     * Time delta between now and last time a bubble is shot.
     */
    private int timeShotFired;
    /**
     * Time delta between now and last time the right arrow key was pressed.
     */
    private int timePassedKeyRight;
    /**
     * Time delta between now and last time the left arrow key was pressed.
     */
    private int timePassedKeyLeft;
    /**
     * Idle time after which the cannon will shot automatically.
     */
    private static final int TIME_TO_SHOOT = 5000;
    /**
     * A warning is displayed at the last milliseconds before
     * automatic shooting.
     */
    private static final int TIME_DISPLAY_FIRE_WARNING = 1500;
    /**
     * Flag to enable warning display.
     */
    private boolean displayWarning;
    /**
     * Right angle limit.
     */
    private final int rightAngleLimit = 60;
    /**
     * Left angle limit.
     */
    private final int leftAngleLimit = -60;
    /**
     * Input scan delay.
     */
    private final int inputScanDelay = 10;
    /**
     * Angle offset.
     */
    private final int angleOffset = 90;
    /**
     * X coordinate of auto shoot warning message.
     */
    private final int displayWarningX = 225;
    /**
     * Y coordinate of auto shoot warning message.
     */
    private final int displayWarningY = 500;
    /**
     * Creates Cannon instance.
     * @param g game object for reference
     */
    public Cannon(final Game g) {
        g.log.log(this, "Cannon initialised");
        this.cannonColour = Color.red;
        this.game = g;
        this.nextBubble = getNextBubble();
        this.loadNextBubble();
        this.loadNextBubble();
    }
    /**
     * Load next bubble to current bubble and asks for a new bubble.
     */
    private void loadNextBubble() {
        this.currBubble = this.nextBubble;
        this.nextBubble = this.getNextBubble();
        this.currBubble.setX(this.xLoad);
        this.currBubble.setY(this.yLoad);
        this.game.bubbleslist.add(this.nextBubble);
    }
    /**
     * Determines next bubble.
     * @return newly created bubble
     */
    private Bubble getNextBubble() {
        game.log.log(this, "Next bubble loaded to cannon");
        Bubble newBubble = Bubble.randomColor((double) this.xLaunch,
                (double) this.yLaunch, game.arena.getColorsOnArena(), true);
        if (GameConfig.ENABLE_POWERUPS) {
            newBubble = PowerUp.apply(newBubble);
        }

        return newBubble;
    }
    /**
     * Fires current bubble and loads next bubble.
     */
    public final void fire() {
        game.log.log(this, "Cannon fired a bubble");
        this.currBubble.fire(this.angle);
        this.loadNextBubble();
        this.timeShotFired = 0;
        this.displayWarning = false;
    }
    /**
     * Updates the Cannon angle per input step.
     */
    public final void stepUp() {
        if (this.angle <= rightAngleLimit) {
            this.angle += 1;
        }
    }
    /**
     * Updates the Cannon angle per input step.
     */
    public final void stepDown() {
        if (this.angle >= leftAngleLimit) {
            this.angle -= 1;
        }
    }

    /**
     * Update the cannon.
     * @param container game container
     * @param delta time passed
     */
    public final void update(final GameContainer container, final int delta) {
        timeShotFired += delta;
        if (container.getInput().isKeyDown(Input.KEY_RIGHT)) {
            if (container.getInput().isKeyPressed(Input.KEY_RIGHT)) {
                game.log.log(this, "Cannon moving to the right");
            }
            this.timePassedKeyRight += delta;
            if (this.timePassedKeyRight > inputScanDelay) {
                stepDown();
                this.timePassedKeyRight = 0;
            }
        } else {
            this.timePassedKeyRight = 0;
        }
        if (container.getInput().isKeyDown(Input.KEY_LEFT)) {
            if (container.getInput().isKeyPressed(Input.KEY_LEFT)) {
                game.log.log(this, "Cannon moving to the left");
            }
            this.timePassedKeyLeft += delta;
            if (this.timePassedKeyLeft > inputScanDelay) {
                stepUp();
                this.timePassedKeyLeft = 0;
            }
        } else {
            this.timePassedKeyLeft = 0;
        }
        if (container.getInput().isKeyPressed(Input.KEY_UP)) {
            fire();
        }
        if (this.timeShotFired > TIME_TO_SHOOT - TIME_DISPLAY_FIRE_WARNING) {
            displayWarning = true;
        }
        if (this.timeShotFired > TIME_TO_SHOOT) {
            game.log.log(this, "Time elapsed, "
                    + "shooting automatically");
            fire();
            timePassedKeyRight = 0;
            displayWarning = false;
        }
    }

    /**
     * Draws the graphics on the screen.
     * @param g graphics
     */
    public final void draw(final Graphics g) {
        g.setColor(this.cannonColour);
        // TODO Make a nicer cannon
        g.drawLine(this.x, this.y, (int) (this.x + Math.cos(Math.toRadians(
                this.angle + angleOffset)) * this.size), (int) (this.y
                - Math.sin(Math.toRadians(this.angle + angleOffset))
                * this.size));
        if (displayWarning) {
            g.drawString("Hurry up!", displayWarningX, displayWarningY);
        }
    }
}
