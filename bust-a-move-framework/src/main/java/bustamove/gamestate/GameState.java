/*
 * File: GameState.java
 *
 * Class: GameState
 *
 * Version: 0.0.1
 *
 * Date: September 15th, 2016
 */


package bustamove.gamestate;

/**
 * Contains static integervalues of all GameStates.
 * @author Jason Xie
 */
public abstract class GameState {
    /**
     * Integer number of StartScreen game state.
     */
    public static final int START_SCREEN = 1;
    /**
     * Integer number of MainMenu game state.
     */
    public static final int MAIN_MENU = 2;
    /**
     * Integer number of GameActive game state.
     */
    public static final int GAME_ACTIVE = 3;
    /**
     * Integer number of PausedScreen game state.
     */
    public static final int PAUSE_SCREEN = 4;
    /**
     * Integer number of VictoryScreen game state.
     */
    public static final int WIN_SCREEN = 5;
    /**
     * Integer number of DefeatScreen game state.
     */
    public static final int DEFEAT_SCREEN = 6;
    /**
     * Integer number of setting name screen.
     */
    public static final int NAME_SCREEN = 7;
    /**
     * Integer number of setting names screen.
     */
    public static final int NAMES_SCREEN = 8;
    /**
     * Integer number of the high scores screen.
     */
    public static final int HIGHSCORES_SCREEN = 9;
}
