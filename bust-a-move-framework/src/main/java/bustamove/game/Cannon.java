/*
 * File: cannon.java
 *
 * Class: Cannon
 *
 * Version: 0.0.3
 *
 * Date: September 12th, 2016
 */

package bustamove.game;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;

import bustamove.bubble.Bubble;
import bustamove.bubble.powerup.PowerUp;
import bustamove.gamestate.GameConfig;
import bustamove.sound.SoundHandler;
import bustamove.system.Log;

/**
 * The Cannon class represents a cannon entity.
 *
 * @author Maurice Willemsen
 */
public class Cannon {
    /**
     * X and Y coordinate of the cannon.
     */
    private static final int X = 320;
    private static final int Y = 530;
    /**
     * X and Y coordinate of the next bubble.
     */
    private static final int X_LAUNCH = (int) (375 - Bubble.DIAMETER / 2);
    private static final int Y_LAUNCH = (int) (555 - Bubble.DIAMETER / 2);
    /**
     * X and Y coordinate of the current bubble.
     */
    private static final int X_LOAD = (int) (320 - Bubble.DIAMETER / 2);
    private static final int Y_LOAD = (int) (530 - Bubble.DIAMETER / 2);
    /**
     * Cannon related variables.
     */
    private int length = GameConfig.DEFAULT_CANNON_LENGTH;
    private Color cannonColour;
    private int angle = 0;

    /**
     * Variables for the current and next bubble.
     */
    private Bubble currBubble;
    private Bubble nextBubble;
    /**
     * GameData reference.
     */
    private GameData game;

    /**
     * Time delta for last shot fired and movement left and right.
     */
    private int timeShotFired;
    private int timePassedKeyRight;
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
     * Angle limit of the cannon.
     */
    private static final int RIGHT_ANGLE_LIMIT = 60;
    private static final int LEFT_ANGLE_LIMIT = -60;
    /**
     * Input scan delay.
     */
    private static final int INPUT_SCAN_DELAY = 10;
    /**
     * Angle offset.
     */
    private static final int ANGLE_OFFSET = 90;
    /**
     * X and Ycoordinate of auto shoot warning message.
     */
    private static final int DISPLAY_WARNING_X = 225;
    private static final int DISPLAY_WARNING_Y = 500;

    /**
     * DataModel drawing offset and player number.
     */
    private final int offset;
    private final int playernr;

    /**
     * Creates Cannon instance.
     * @param g gamedata object for reference
     * @param drawingoffset amount of drawing pixels to shift
     * @param modelnr       amount of players in the game
     */
    public Cannon(final GameData g, final int drawingoffset,
                  final int modelnr) {
        Log.log(this, "Cannon initialised");
        this.cannonColour = Color.red;
        this.game = g;
        this.offset = drawingoffset;
        this.playernr = modelnr;
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
        this.currBubble.setX(Cannon.X_LOAD + this.offset);
        this.currBubble.setY(Cannon.Y_LOAD);
        this.game.getBubbles().add(this.nextBubble);
    }

    /**
     * Determines next bubble.
     * @return newly created bubble
     */
    private Bubble getNextBubble() {
        Log.log(this, "Next bubble loaded to cannon");
        Bubble newBubble = Bubble.randomColor(
                (float) Cannon.X_LAUNCH + this.offset, (float) Cannon.Y_LAUNCH,
                game.getArena().getBubbleStorage().getColorsOnArena(), true);
        if (GameConfig.ENABLE_POWERUPS) {
            newBubble = PowerUp.apply(newBubble);
        }
        return newBubble;
    }

    /**
     * Fires current bubble and loads next bubble.
     */
    public final void fire() {
        Log.log(this, "Cannon fired a bubble");
        SoundHandler.playFireSound();
        this.currBubble.setGameHead(game);
        this.currBubble.fire(this.angle);
        this.loadNextBubble();
        this.timeShotFired = 0;
        this.displayWarning = false;
    }

    /**
     * Updates the Cannon angle per input step.
     */
    public final void stepUp() {
        if (this.angle <= RIGHT_ANGLE_LIMIT) {
            SoundHandler.playClickSound();
            this.angle += 1;
        }
    }

    /**
     * Updates the Cannon angle per input step.
     */
    public final void stepDown() {
        if (this.angle >= LEFT_ANGLE_LIMIT) {
            SoundHandler.playClickSound();
            this.angle -= 1;
        }
    }

    /**
     * Update the cannon.
     * @param container game container
     * @param delta time passed
     * @param gamehead the gamedata where its part of
     */
    public final void update(final GameContainer container, final int delta,
            final GameData gamehead) {
        timeShotFired += delta;
        game = gamehead;
        if (this.playernr == 1) {
            if (container.getInput().isKeyDown(Input.KEY_RIGHT)) {
                if (container.getInput().isKeyPressed(Input.KEY_RIGHT)) {
                    Log.log(this, "Cannon moving to the right");
                }
                this.timePassedKeyRight += delta;
                if (this.timePassedKeyRight > INPUT_SCAN_DELAY) {
                    stepDown();
                    this.timePassedKeyRight = 0;
                }
            } else {
                this.timePassedKeyRight = 0;
            }
            if (container.getInput().isKeyDown(Input.KEY_LEFT)) {
                if (container.getInput().isKeyPressed(Input.KEY_LEFT)) {
                    Log.log(this, "Cannon moving to the left");
                }
                this.timePassedKeyLeft += delta;
                if (this.timePassedKeyLeft > INPUT_SCAN_DELAY) {
                    stepUp();
                    this.timePassedKeyLeft = 0;
                }
            } else {
                this.timePassedKeyLeft = 0;
            }
            if (container.getInput().isKeyPressed(Input.KEY_UP)) {
                fire();
            }
        } else if (this.playernr == 2) {
            if (container.getInput().isKeyDown(Input.KEY_D)) {
                if (container.getInput().isKeyPressed(Input.KEY_D)) {
                    Log.log(this, "Cannon moving to the right");
                }
                this.timePassedKeyRight += delta;
                if (this.timePassedKeyRight > INPUT_SCAN_DELAY) {
                    stepDown();
                    this.timePassedKeyRight = 0;
                }
            } else {
                this.timePassedKeyRight = 0;
            }
            if (container.getInput().isKeyDown(Input.KEY_A)) {
                if (container.getInput().isKeyPressed(Input.KEY_A)) {
                    Log.log(this, "Cannon moving to the left");
                }
                this.timePassedKeyLeft += delta;
                if (this.timePassedKeyLeft > INPUT_SCAN_DELAY) {
                    stepUp();
                    this.timePassedKeyLeft = 0;
                }
            } else {
                this.timePassedKeyLeft = 0;
            }
            if (container.getInput().isKeyPressed(Input.KEY_W)) {
                fire();
            }
        }

        if (this.timeShotFired > TIME_TO_SHOOT - TIME_DISPLAY_FIRE_WARNING) {
            displayWarning = true;
        }

        if (this.timeShotFired > TIME_TO_SHOOT) {
            Log.log(this, "Time elapsed, " + "shooting automatically");
            fire();
            timePassedKeyRight = 0;
            timePassedKeyLeft = 0;
            timeShotFired = 0;
            displayWarning = false;
        }
    }

    /**
     * Draws the graphics on the screen.
     * @param g graphics
     */
    public final void draw(final Graphics g) {
        g.setColor(this.cannonColour);
        g.drawLine(Cannon.X + this.offset, Cannon.Y,
                (int) (Cannon.X + this.offset
                        + Math.cos(Math.toRadians(this.angle + ANGLE_OFFSET))
                                * this.length),
                (int) (Cannon.Y
                        - Math.sin(Math.toRadians(this.angle + ANGLE_OFFSET))
                                * this.length));
        if (displayWarning) {
            g.drawString("Hurry up!", DISPLAY_WARNING_X + this.offset,
                    DISPLAY_WARNING_Y);
        }
    }

    /**
     * Getter method: length.
     * @return int Cannon length
     */
    public final int getLength() {
        return this.length;
    }

    /**
     * Setter method: length.
     * @param l Cannon length
     */
    public final void setLength(final int l) {
        this.length = l;
    }
}
