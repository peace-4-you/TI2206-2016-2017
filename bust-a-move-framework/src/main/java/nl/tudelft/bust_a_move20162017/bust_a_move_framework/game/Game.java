package nl.tudelft.bust_a_move20162017.bust_a_move_framework.game;

import nl.tudelft.bust_a_move20162017.bust_a_move_framework.cannon.Cannon;
import nl.tudelft.bust_a_move20162017.bust_a_move_framework.arena.Arena;

public class Game {

	public int WIDTH;
	
	public int HEIGHT;
	
	private int LEVEL;
	
	private int DIFFICULTY;
	
	private boolean IsPlaying;
	
	private Cannon cannon;	
	
	private Arena arena;	

	public void main() {
		this.LEVEL = 1;
		this.DIFFICULTY = 1;
		this.WIDTH = 640;
		this.HEIGHT = 480;
		this.IsPlaying = false;	
	}
	
	public void startGame(){
		this.IsPlaying = true;
		cannon = new Cannon();  
		arena = new Arena();
	}
	
	public void startNewLevel(){
		this.levelUp();
		this.startGame();
	}
	
	private void levelUp(){
		this.LEVEL++;
	}
}
