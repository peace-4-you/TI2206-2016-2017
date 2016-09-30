/*
 * File: GameConfig.java
 * Class: GameConfig
 * Version: 0.0.1
 * Date: September 27th, 2016
 */

package bust_a_move.gamestate;

import bust_a_move.bubble.Bubble;

/**
 * Contains static integervalues of all GameConfig.
 * @author Maurice
 */
public abstract class GameConfig {
    /**
     * Vertical Position of the first line to display on the screen.
     */
    public static final int FIRST_LINE = 40;
    /**
     * Vertical Position of the second line to display on the screen.
     */
    public static final int SECOND_LINE = 80;
    /**
     * Vertical Position of the third line to display on the screen.
     */
    public static final int THIRD_LINE = 120;
    /**
     * Vertical Position of the fourth line to display on the screen.
     */
    public static final int FOURTH_LINE = 160;
    /**
     * Vertical Position of the fourth line to display on the screen.
     */
    public static final int FIFTH_LINE = 200;
    /**
     * Vertical Position of the fifth line to display on the screen.
     */
    public static final int SIXT_LINE = 240;
    /**
     * Vertical Position of the seventh line to display on the screen.
     */
    public static final int SEVENTH_LINE = 280;
    /**
     * Width of something to display on the screen.
     */
    public static final int WIDTH1 = 100;
    /**
     * Width of something to display on the screen.
     */
    public static final int WIDTH2 = 150;
    /**
     * Width of something to display on the screen.
     */
    public static final int WIDTH3 = 200;
    /**
     * Height of standard button to display on the screen.
     */
    public static final int HEIGHT = 30;
    /**
     * X position of something with width 200 to be displayed in the center of
     * the screen.
     */
    public static final int CENTRAL = 220;
    /**
     * Text Size.
     */
    public static final int TEXT_SIZE = 20;
    /**
     * Horizontal distance between each bubble in the arena.
     */
    public static final double COLUMN_OFFSET = Bubble.DIAMETER / 2.0;
    /**
     * Vertical distance between each bubble in the arena.
     */
    public static final double ROW_OFFSET = (double) Bubble.DIAMETER
            * Math.tan(60) + COLUMN_OFFSET + 2;
    /**
     * Bubble PowerUp feature flag.
     */
    public static final boolean ENABLE_POWERUPS = true;
    /**
     * Default Bubble speed.
     */
    public static final double DEFAULT_BUBBLE_SPEED = 3.0;
    /**
     * Maximum Bubble speed.
     */
    public static final double MAX_BUBBLE_SPEED = 7.5;
    /**
     * Minimum Bubble speed.
     */
    public static final double MIN_BUBBLE_SPEED = 1;
    /**
     * Bubble speed up/slow down factor.
     */
    public static final double BUBBLE_SPEEDUP = 0.5;
    /**
     * Default Cannon length.
     */
    public static final int DEFAULT_CANNON_LENGTH = 80;
    /**
     * Maximum Cannon scope length.
     */
    public static final int MAX_CANNON_LENGTH = 200;
    /**
     * Minimum Cannon scope length.
     */
    public static final int MIN_CANNON_LENGTH = 0;
    /**
     * Cannon length increment value.
     */
    public static final int CANNON_LENGTH_INCREMENT = 20;
}
