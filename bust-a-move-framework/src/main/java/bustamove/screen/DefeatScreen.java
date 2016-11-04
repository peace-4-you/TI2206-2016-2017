/*
 * File: DefeatScreen.java
 *
 * Class: DefeatScreen
 *
 * Version: 0.0.3
 *
 * Date: September 26th, 2016
 */
package bustamove.screen;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import bustamove.App;
import bustamove.screen.attributes.Button;
import bustamove.screen.attributes.Text;
import bustamove.screen.config.GameConfig;
import bustamove.screen.config.GameState;
import bustamove.game.GameData.GameDifficulty;

/**
 * Generates a DefeatScreen as a instance of GameState.
 *
 * @author Jason Xie, Maurice Willemsen
 */
public class DefeatScreen extends ScoreScreen {
    /**
     * Getter method: for the GameState ID.
     *
     * @return integer of BasicGameState number.
     */
    public final int getID() {
        return GameState.DEFEAT_SCREEN;
    }

    /**
     * Called when BasicGameState initializes.
     *
     * @param game           the game container
     * @param stateBasedGame the state based game
     * @throws SlickException any type of slick exception
     */
    public final void init(final GameContainer game,
                           final StateBasedGame stateBasedGame)
            throws SlickException {
        initScoreScreen(game, stateBasedGame);
        Text failedText = new Text("You Failed", GameConfig.FIRST_LINE);
        Button restart = new Button("Restart", GameConfig.TENTH_LINE,
                GameConfig.WIDTH2, GameConfig.HEIGHT);
        restart.addAction(new Runnable() {
            public void run() {
                try {
                    GameDifficulty difficulty = App.getGame().getGameData()
                        .get(0).getDifficulty();
                    App.getGame().destroyGame();
                    String player1name = getPlayerNames()[0];
                    if (getPlayerAmount() == 2) {
                        String player2name = getPlayerNames()[1];
                        App.getGame().start2Player(difficulty);
                        App.getGame().getGameData().get(1).getPlayer()
                                .setName(player2name);
                    } else {
                        App.getGame().start1Player(difficulty);
                    }
                    App.getGame().getGameData().get(0).getPlayer()
                            .setName(player1name);
                } catch (SlickException e) {
                    e.printStackTrace();
                }
            }
        });
        restart.addGameStateChangeAction(stateBasedGame,
                GameState.GAME_ACTIVE);
        getButtons().add(restart);
        getTexts().add(failedText);
        super.center();
    }
}
