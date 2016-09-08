/*
 * File: gamemodel.java
 * Class: GameModel
 *
 * Version: 0.0.1
 * Date: September 8th, 2016
 */

package nl.tudelft.bust_a_move20162017.bust_a_move_framework.gamemodel;

import nl.tudelft.bust_a_move20162017.bust_a_move_framework.cannonmodel.CannonModel;
import nl.tudelft.bust_a_move20162017.bust_a_move_framework.arena.ArenaModel;
import nl.tudelft.bust_a_move20162017.bust_a_move_framework.player.PlayerModel;
import java.awt.Color;
import java.awt.Graphics;
import java.util.Observable;


/**
 * The GameModel class represents a gamemodel entity.
 *
 * @author Maurice Willemsen
 */

public class GameModel extends Observable  {

	public enum State {
		START, PLAYING, FAILED, WON
	}

	private int LEVEL;
	private int DIFFICULTY;
	public State state;	
	private CannonModel cannonmodel;
	private ArenaModel arenamodel;
	private PlayerModel playermodel;
	
	/**
	 * Creates GameModel instance
	 *
	 */

	public GameModel() {		
		this.LEVEL = 1;
		this.DIFFICULTY = 1;		
		this.state = State.START;		
		this.playermodel = new PlayerModel();
		this.updateObservers();
	}
	
	
	  /**
	   * updates subscribed Observers
	   */
	  public void updateObservers() {	 
	    this.notifyObservers();
	  }
	
	/**
	 * Starts game, changes state to PLAYING, creates new arena and cannon instance
	 */

	private void startGame() {
		this.state = State.PLAYING;
		this.cannonmodel = new CannonModel();
		this.arenamodel = new ArenaModel();
		this.updateObservers();
	}
	
	/**
	 * Ends game, changes state to WON
	 */

	private void wonGame() {
		this.state = State.WON;
		this.updateObservers();
	}
	
	/**
	 * Ends game, changes state to FAILED
	 */

	private void failedGame() {
		this.state = State.FAILED;
		this.updateObservers();
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
}
