/*
 * File: GameConfig.java
 * Class: GameConfig
 * Version: 0.0.1
 * Date: September 27th, 2016
 */


package bustamove.screen.config;

import bustamove.bubble.Bubble;

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
     * Vertical Position of the eigth line to display on the screen.
     */
    public static final int EIGTH_LINE = 320;
    /**
     * Vertical Position of the eleventh line to display on the screen.
     */
    public static final int ELEVENTH_LINE = 440;
    /**
     * Vertical Position of the twelfht line to display on the screen.
     */
    public static final int TWELFTH_LINE = 480;
    /**
     * Vertical Line Distance.
     */
    public static final int LINE_DIST = 40;
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
     * X position of something with a margin of 10 to the left.
     */
    public static final int MARGIN_LEFT = 10;
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
     * PowerUp feature flag.
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
    /**
     * Amount of entries stored in the high scores.
     */
    public static final int HIGHSCORE_ENTRIES = 10;
    /**
     * The offset used for displaying scores in the high scores screen.
     */
    public static final double HIGHSCORE_NAME_OFFSET = 0.3;
    /**
     * The offset used for displaying names in the high scores screen.
     */
    public static final double HIGHSCORE_SCORE_OFFSET = 0.6;
    /**
     * The location for the high scores file.
     */
    public static final String HIGHSCORE_FILE = "res/highscores.txt";
    /**
     * Max player name length.
     */
    public static final int MAX_NAME_LENGTH = 13;
}