/*
 * File: GameState.java
 * Class: GameState
 *
 * Version: 0.0.1
 * Date: September 15th, 2016
 */

package nl.tudelft.bust_a_move20162017.bust_a_move_framework.gamestate;

/**
 * Contains static integervalues of all GameStates.
 * 
 * @author Jason Xie
 */

public abstract class GameState {
	/**
	 * Integer number of game state.
	 */
	public static final int START_SCREEN = 1;
	/**
	 * Integer number of game state.
	 */
	public static final int MAIN_MENU = 2;
	/**
	 * Integer number of game state.
	 */
	public static final int GAME_ACTIVE = 3;
	/**
	 * Integer number of game state.
	 */
	public static final int PAUSE_SCREEN = 4;
	/**
	 * Integer number of game state.
	 */
	public static final int WIN_SCREEN = 5;
	/**
	 * Integer number of game state.
	 */
	public static final int DEFEAT_SCREEN = 6;
}
