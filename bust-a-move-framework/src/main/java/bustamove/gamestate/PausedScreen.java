/*
 * File: PausedScreen.java
 *
 * Class: PausedScreen
 *
 * Version: 0.0.3
 *
 * Date: September 26th, 2016
 */
package bustamove.gamestate;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

/**
 * Generates a PausedScreen as a instance of GameState.
 * @author Jason Xie, Maurice Willemsen
 */
public class PausedScreen extends ScoreScreen {
    /**
     * Getter method: for the GameState ID.
     * @return integer of BasicGameState number.
     */
    public final int getID() {
        return GameState.PAUSE_SCREEN;
    }

    /**
     * Called when BasicGameState initializes.
     * @param game the game container
     * @param stateBasedGame the state based game
     * @throws SlickException any type of slick exception
     */
    public final void init(final GameContainer game,
            final StateBasedGame stateBasedGame) throws SlickException {
    	super.init(game, stateBasedGame);
        Text pauseText = new Text("Game Paused", GameConfig.FIRST_LINE);        
        Button resume = new Button("Resume", GameConfig.FOURTH_LINE,
                GameConfig.WIDTH2, GameConfig.HEIGHT);
        resume.addGameStateChangeAction(stateBasedGame,
                GameState.GAME_ACTIVE);       
        buttons.add(resume);
        texts.add(pauseText);
        super.center();
    }
}
