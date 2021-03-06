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

import bustamove.bubble.SimpleBubble;
import bustamove.bubble.Bubble.ColorChoice;
import bustamove.bubble.Bubble;
import bustamove.bubble.powerup.PowerUp;
import bustamove.screen.config.GameConfig;
import bustamove.system.Log;
import bustamove.system.SoundHandler;

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
    private static final int X_LAUNCH =
            (int) (375 - Bubble.DIAMETER / 2);
    private static final int Y_LAUNCH =
            (int) (555 - Bubble.DIAMETER / 2);
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
     * Variables for Key Input Handling.
     */
    private static final int[][] INPUTKEY = {{Input.KEY_LEFT, Input.KEY_RIGHT,
            Input.KEY_UP}, {Input.KEY_A, Input.KEY_D, Input.KEY_W}};
    private static final String[] MOVE_TEXT = {"left", "right"};
    /**
     * Time delta for last shot fired and movement left and right.
     */
    private int timeShotFired;
    private int[] timePassedKey;
    /**
     * A warning is displayed at the last milliseconds before
     * automatic shooting.
     */
    private static final int TIME_DISPLAY_FIRE_WARNING = 1500;
    /**
     * Idle time after which the cannon will shot automatically.
     */
    private int timeToShoot;
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
     *
     * @param g             gamedata object for reference
     * @param drawingoffset amount of drawing pixels to shift
     * @param modelnr       amount of players in the game
     */
    public Cannon(final GameData g, final int drawingoffset,
                  final int modelnr) {
        Log.getInstance().log(this, "Cannon initialised");
        this.cannonColour = Color.red;
        this.game = g;
        this.offset = drawingoffset;
        this.playernr = modelnr;
        this.nextBubble = getNextBubble();
        this.loadNextBubble();
        this.loadNextBubble();
        this.timePassedKey = new int[2];

        switch (g.getDifficulty()) {
            case EASY:
                timeToShoot = GameConfig.TIME_TO_SHOOT_EASY;
                break;
            case NORMAL:
                timeToShoot = GameConfig.TIME_TO_SHOOT_NORMAL;
                break;
            case HARD:
                timeToShoot = GameConfig.TIME_TO_SHOOT_HARD;
                break;
            default:
                timeToShoot = GameConfig.TIME_TO_SHOOT_NORMAL;
                break;
        }
    }

    /**
     * Load next bubble to current bubble and asks for a new bubble.
     */
    private void loadNextBubble() {
        this.currBubble = this.nextBubble;
        this.nextBubble = this.getNextBubble();
        this.currBubble.setX(Cannon.X_LOAD + this.offset);
        this.currBubble.setY(Cannon.Y_LOAD);
        this.game.getBubbles().addFirst(this.nextBubble);
    }

    /**
     * Determines next bubble.
     *
     * @return newly created bubble
     */
    private Bubble getNextBubble() {
        Log.getInstance().log(this, "Next bubble loaded to cannon");
        ColorChoice c = SimpleBubble.randomColor(
                game.getBubbleStorage().getColorsOnArena());
        Bubble newBubble = new SimpleBubble(
                (float) Cannon.X_LAUNCH + this.offset, (float) Cannon.Y_LAUNCH,
                c, true);
        if (GameConfig.ENABLE_POWERUPS) {
            newBubble = PowerUp.apply(newBubble);
        }
        return newBubble;
    }

    /**
     * Fires current bubble and loads next bubble.
     */
    public final void fire() {
        Log.getInstance().log(this, "Cannon fired a bubble");
        SoundHandler.getInstance().playFireSound();
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
        if (this.angle < RIGHT_ANGLE_LIMIT) {
            SoundHandler.getInstance().playClickSound();
            this.angle += 1;
        }
    }

    /**
     * Updates the Cannon angle per input step.
     */
    public final void stepDown() {
        if (this.angle > LEFT_ANGLE_LIMIT) {
            SoundHandler.getInstance().playClickSound();
            this.angle -= 1;
        }
    }

    /**
     * Select the side of the cannon which should be updated.
     *
     * @param side integer of the side.
     *             0 = move left;
     *             1 = move right;
     */
    private void step(final int side) {
        if (side == 0) {
            stepUp();
        } else if (side == 1) {
            stepDown();
        }
    }

    /**
     * Update the cannon.
     *
     * @param container game container
     * @param delta     time passed
     * @param gamehead  the gamedata where its part of
     */
    public final void update(final GameContainer container, final int delta,
                             final GameData gamehead) {
        timeShotFired += delta;
        game = gamehead;
        for (int i = 0; i < 2; i++) {
            if (container.getInput().isKeyDown(INPUTKEY[playernr - 1][i])) {
                this.timePassedKey[i] += delta;
                if (this.timePassedKey[i] > INPUT_SCAN_DELAY) {
                    step(i);
                    this.timePassedKey[i] = 0;
                }
            }
            if (container.getInput().isKeyPressed(INPUTKEY[playernr - 1][i])) {
                String log = "Cannon moving to the " + MOVE_TEXT[i];
                Log.getInstance().log(this, log);
            }
        }
        if (container.getInput().isKeyPressed(INPUTKEY[playernr - 1][2])) {
            fire();
        }
        if (this.timeShotFired > timeToShoot - TIME_DISPLAY_FIRE_WARNING) {
            displayWarning = true;
        }
        if (this.timeShotFired > timeToShoot) {
            Log.getInstance().log(this, "Time elapsed, "
                    + "shooting automatically");
            fire();
            timePassedKey[0] = 0;
            timePassedKey[1] = 0;
            timeShotFired = 0;
            displayWarning = false;
        }
    }

    /**
     * Draws the graphics on the screen.
     *
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
     *
     * @return int Cannon length
     */
    public final int getLength() {
        return this.length;
    }

    /**
     * Setter method: length.
     *
     * @param l Cannon length
     */
    public final void setLength(final int l) {
        this.length = l;
    }
}
