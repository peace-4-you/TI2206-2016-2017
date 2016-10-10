package bust_a_move.game;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;
import bust_a_move.App;
import bust_a_move.bubble.Bubble;
import bust_a_move.player.Player;

public class GameData implements Observer{
	
	public enum GameDataState{RUNNING,WON,LOOSE};
	
	private GameDataState state;
		
	/**
	 * Score of the game, updated by observer.
	 */
	private int score;
	/**
	 * Cannon instance for the game.
	 */
	public Cannon cannon;
	/**
	 * Arraylist of bubbles.
	 */
	public ArrayList<Bubble> bubbleslist;
	/**
	 * Player instance for the game.
	 */
	public Player player;
	/**
	 * Arena instance for in the game.
	 */
	public Arena arena;
	
	/**
	 * X position for the arena.
	 */
	private static final int arena_x = 180;
	/**
	 * Y position for the arena.
	 */
	private static final int arena_y = 0;
	/**
	 * width for the arena.
	 */
	private static final int arena_width = 531;
	/**
	 * height  for the arena.
	 */
	private static final int arena_height = 280;
	
	private int offset;
	
	private int player_nr;
	
	public GameData(int offset, int nr) {
		App.getGame().log.log(this, "GameData initialised");
		this.offset = offset;
		this.player_nr = nr;
		this.state = GameDataState.RUNNING;		
		this.bubbleslist = new ArrayList<Bubble>();
		this.player = new Player("Player1",this.offset,this.player_nr);
		this.player.registerObserver((PlayerObserver)App.getPauseScreen());
		this.player.registerObserver((PlayerObserver)App.getDefeatScreen());
		this.arena = new Arena(GameData.arena_x+this.offset, GameData.arena_y, GameData.arena_width, GameData.arena_height,this.player.getScore());
		this.cannon = new Cannon(this,this.offset,this.player_nr);
	}
	
	
	public final void update(final GameContainer container, final StateBasedGame sbg, final int delta)
			throws SlickException {
		if(this.state == GameDataState.RUNNING){
			this.cannon.update(container, delta);
		}

		for (Bubble b1 : bubbleslist) {
			b1.move();
			if (b1.getState() == Bubble.State.FIRING) {
				arena.checkCollision(b1);
			}
		}
		if (arena.getBubbleStorage().isFull()) {
			this.state = GameDataState.LOOSE;
		}
		else if (arena.getBubbleStorage().isEmpty()) {
			this.state = GameDataState.WON;
		}

	}

	/**
	 * Updates the BasicGameState.
	 * 
	 * @param game
	 *            the game container
	 * @param stateBasedGame
	 *            the state based game
	 * @param i
	 *            delta of time exceeded
	 * @throws SlickException
	 *             any type of slick exception
	 */

	public final void render(final GameContainer game, final StateBasedGame stateBasedGame, final Graphics g)
			throws SlickException {
		g.setColor(Color.white);
		g.drawString("Score:" + this.score, 10 + this.offset, 70);
		g.drawString("Fire power: x" + (double) Math.round(Bubble.SPEED * 10 / 3) / 10, 10 + this.offset, 130);
		cannon.draw(g);
		player.draw(g);
		arena.draw(g);
		for (Bubble bubble : this.bubbleslist) {
			if (bubble.getState() != Bubble.State.LANDED) {
				bubble.draw(g);
			}
		}
	}
	
	/**
	 * Method to return the arena object inside game class.
	 *
	 * @return Arena object inside game class
	 */

	public final Arena getArena() {
		return this.arena;
	}
	
	/**
	 * Method to return the player object inside gamedata class.
	 *
	 * @return Player object inside gamedata class
	 */
	
	
	public final Player getPlayer(){
		return this.player;
	}
	
	/**
	 * Updates the Observer.
	 * 
	 * @param o
	 *            the observable item
	 * @param arg
	 *            the observable argument
	 */

	public final void update(final Observable o, final Object arg) {
		this.score = (Integer) arg;
	}
	
	public GameDataState getState(){
		return this.state;
	}
}

