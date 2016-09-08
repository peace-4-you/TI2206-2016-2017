/*
 * File: game.java
 * Class: Game
 *
 * Version: 0.0.1
 * Date: September 8th, 2016
 */

package nl.tudelft.bust_a_move20162017.bust_a_move_framework.game;

import nl.tudelft.bust_a_move20162017.bust_a_move_framework.cannon.Cannon;
import nl.tudelft.bust_a_move20162017.bust_a_move_framework.arena.Arena;
import nl.tudelft.bust_a_move20162017.bust_a_move_framework.player.Player;
import java.awt.Color;
import java.awt.Graphics;

/**
 * The Game class represents a game entity.
 *
 * @author Maurice Willemsen
 */

public class Game {

	private enum State {
		START, PLAYING, FAILED, WON
	}

	public int WIDTH;

	public int HEIGHT;

	private int LEVEL;

	private int DIFFICULTY;

	private Cannon cannon;

	private Arena arena;

	private Player player;

	private State state;

	private Color backGroundcolor;

	private Graphics g;
	
	/**
	 * Creates Game instance
	 *
	 * @param WIDTH
	 *            int value for screen width
	 * @param HEIGHT
	 *            int value for screen height
	 * @param backgroundColour
	 *            Color value for background
	 */

	public void main(Graphics g) {
		this.WIDTH = 640;
		this.HEIGHT = 480;
		this.backGroundcolor = new Color(0xFFFFFF);
		
		this.LEVEL = 1;
		this.DIFFICULTY = 1;

		this.state = State.START;		
		this.player = new Player();
		this.g = g;
	}
	
	/**
	 * Starts game, changes state to PLAYING, creates new arena and cannon instance
	 */

	private void startGame() {
		this.state = State.PLAYING;
		this.cannon = new Cannon();
		this.arena = new Arena();
	}
	
	/**
	 * Ends game, changes state to WON
	 */

	private void wonGame() {
		this.state = State.WON;
	}
	
	/**
	 * Ends game, changes state to FAILED
	 */

	private void failedGame() {
		this.state = State.FAILED;
	}
	
	/**
	 * Asks for new level and new start of game.
	 */

	private void startNewLevel() {
		this.levelUp();
		this.startGame();
	}
	
	/**
	 * Level up
	 */

	private void levelUp() {
		this.LEVEL++;
	}
	
	/**
	 * Drop all bubbles one line and creates new bubbles
	 */

	public void newLine() {
		// TODO Drop all bubbles one line
		// TODO Add random amount of random bubbles to top line.
	}
	
	/**
	 * Updates the game
	 */

	public void update() {
		switch (this.state) {
		case START:
			// TODO Show startscreen
			break;
		case PLAYING:
			cannon.drawCannon(this.g);
			// TODO Show playscreen including arena and cannon
			break;
		case WON:
			// TODO Show wonscreen including score, level, and button for next
			// level
			break;
		case FAILED:
			// TODO Show failedscreen including score, level, and button for
			// restart level
			break;
		}
	}
}
