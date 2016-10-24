/*
 * File: MainMenu.java
 *
 * Class: MainMenu
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
 * Generates a MainMenu as a instance of GameState.
 * @author Jason Xie, Maurice Willemsen
 */
public class MainMenu extends Screen {
    /**
     * @return integer of BasicGameState number.
     */
    public final int getID() {
        return GameState.MAIN_MENU;
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
        // 1 player button
        Button play1player = new Button("1 hhPlayer", GameConfig.SECOND_LINE,
                GameConfig.WIDTH2, GameConfig.HEIGHT);
        play1player.addGameStateChangeAction(stateBasedGame,
                GameState.NAME_SCREEN);
        // 2 player button
        Button play2players = new Button("2 Players", GameConfig.THIRD_LINE,
                GameConfig.WIDTH2, GameConfig.HEIGHT);
        play2players.addGameStateChangeAction(stateBasedGame,
                GameState.NAMES_SCREEN);
        // High scores button
        Button highScores = new Button("Highscores", GameConfig.FOURTH_LINE,
                GameConfig.WIDTH2, GameConfig.HEIGHT);
        highScores.addGameStateChangeAction(stateBasedGame,
                GameState.HIGHSCORES_SCREEN);
        // quit button
        Button quit = new Button("Quit", GameConfig.FIFTH_LINE,
                GameConfig.WIDTH1, GameConfig.HEIGHT);
        quit.addAction(new Runnable() {
            public void run() {
                game.exit();
            }
        });
        buttons.add(play1player);
        buttons.add(play2players);
        buttons.add(highScores);
        buttons.add(quit);
        super.center();
    }
}
