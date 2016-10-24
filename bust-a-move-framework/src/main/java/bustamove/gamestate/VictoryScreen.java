/*
 * File: VictoryScreen.java
 *
 * Class: VictoryScreen
 *
 * Version: 0.0.3
 *
 * Date: September 26th, 2016
 */

package bustamove.gamestate;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;
import bustamove.App;

/**
 * Generates a VictoryScreen as a instance of GameState.
 * @author Jason Xie, Maurice Willemsen
 */
public class VictoryScreen extends ScoreScreen {
    /**
     * Getter method: for the GameState ID.
     * @return integer of BasicGameState number.
     */
    public final int getID() {
        return GameState.WIN_SCREEN;
    }

    /**
     * Called when BasicGameState initializes.
     * @param game the game container
     * @param stateBasedGame the state based game
     * @throws SlickException any type of slick exception
     */
    public final void init(final GameContainer game,
            final StateBasedGame stateBasedGame) throws SlickException {
        initScoreScreen(game, stateBasedGame);
        Text wonText = new Text("You Won", GameConfig.FIRST_LINE);
        Button restart = new Button("Restart", GameConfig.SIXT_LINE,
                GameConfig.WIDTH1, GameConfig.HEIGHT);
        restart.addAction(new Runnable() {
            public void run() {
                try {
                    App.getGame().destroyGame();
                    if (getPlayerAmount() == 2) {
                         App.getGame().start2Player();
                    } else {
                         App.getGame().start1Player();
                    }
                } catch (SlickException e) {
                    e.printStackTrace();
                }
            }
        });
        restart.addGameStateChangeAction(stateBasedGame,
                GameState.GAME_ACTIVE);
        getButtons().add(restart);
        getTexts().add(wonText);
        super.center();
    }
}
