/*
 * File: DefeatScreen.java
 * Class: DefeatScreen
 *
 * Version: 0.0.3
 * Date: September 26th, 2016
 */

package nl.tudelft.bust_a_move20162017.bust_a_move_framework.gamestate;

import java.util.Observable;
import java.util.Observer;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

import nl.tudelft.bust_a_move20162017.bust_a_move_framework.App;

/**
 * Generates a DefeatScreen as a instance of GameState
 * @author Jason Xie, Maurice Willemsen
 */

public class DefeatScreen extends BasicGameState implements Observer {

    private Button restart;
    private Button mainmenu;
	private Text nameText;
	private Text scoreText;
	private Text failedText;
    private int	score;
    
    /**
     * @return integer of BasicGameState number
     */

    public int getID() {
        return 6;
    }
    
    /**
     * Called when BasicGameState initializes
     */

    public void init(GameContainer game, StateBasedGame stateBasedGame) throws SlickException {
        restart = new Button("Restart", 170, 120, 30);
        restart.centerButton(game);
        mainmenu = new Button("Go to Main Menu", 205, 200, 30);
        mainmenu.centerButton(game);
        failedText = new Text("You Failed",30);
        failedText.centerText(game);
		nameText = new Text("Player: " + App.getGame().player.getName(),90);
        nameText.centerText(game);
        scoreText = new Text("Score: " + this.score,120);
        scoreText.centerText(game);
        App.getGame().player.score.addAsObserver(this);
    }
    
    /**
     * Renders the BasicGameState
     */

    public void render(GameContainer game, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
        failedText.draw(graphics); 		
        nameText.draw(graphics);          
        scoreText.draw(graphics); 
        restart.draw(graphics);
        mainmenu.draw(graphics);
    }
    
    /**
     * Updates the BasicGameState
     */

    public void update(GameContainer game, StateBasedGame stateBasedGame, int i) throws SlickException {
        nameText.setText("Player: " + App.getGame().player.getName());
        nameText.centerText(game);
        scoreText.setText("Score: " + this.score);
        scoreText.centerText(game);
        Input input = game.getInput();
        if (input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
            if (restart.isInBounds(input)) {
                stateBasedGame.getState(GameState.GAME_ACTIVE).init(game, stateBasedGame);
                stateBasedGame.enterState(GameState.GAME_ACTIVE, new FadeOutTransition(), new FadeInTransition());
            }
            if (mainmenu.isInBounds(input)) {
            	stateBasedGame.getState(GameState.GAME_ACTIVE).init(game, stateBasedGame);
                stateBasedGame.enterState(GameState.MAIN_MENU, new FadeOutTransition(), new FadeInTransition());
            }
        }
    }
    
    /**
     * Updates the Observer
     */

	public void update(Observable o, Object arg) {
		this.score = (Integer) arg;
	}
}
