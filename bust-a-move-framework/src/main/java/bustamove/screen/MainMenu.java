/*
 * File: MainMenu.java
 *
 * Class: MainMenu
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
import bustamove.screen.config.GameConfig;
import bustamove.screen.config.GameState;

/**
 * Generates a MainMenu as a instance of GameState.
 *
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
     *
     * @param game           the game container
     * @param stateBasedGame the state based game
     * @throws SlickException any type of slick exception
     */
    public final void init(final GameContainer game,
                           final StateBasedGame stateBasedGame)
            throws SlickException {
        initBasicScreen(game);
        // 1 player button
        Button play1player = new Button("1 Player", GameConfig.SECOND_LINE,
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
        // Achievements button
        Button achievements = new Button("Achievements",
                GameConfig.FIFTH_LINE, GameConfig.WIDTH2,
                GameConfig.HEIGHT);
        achievements.addGameStateChangeAction(stateBasedGame,
                GameState.ACHIEVEMENTS_SCREEN);
        // Option button
        Button option = new Button("Options", GameConfig.SIXT_LINE,
                GameConfig.WIDTH2, GameConfig.HEIGHT);
        option.addGameStateChangeAction(stateBasedGame,
                GameState.OPTION_SCREEN);
        // Credits button
        Button credits = new Button("Credits", GameConfig.SEVENTH_LINE,
                GameConfig.WIDTH2, GameConfig.HEIGHT);
        credits.addGameStateChangeAction(stateBasedGame,
                GameState.CREDITS_SCREEN);
        // quit button
        Button quit = new Button("Quit", GameConfig.EIGTH_LINE,
                GameConfig.WIDTH1, GameConfig.HEIGHT);
        quit.addAction(new Runnable() {
            public void run() {
                App.getStatistics().write();
                game.exit();
            }
        });
        getButtons().add(play1player);
        getButtons().add(play2players);
        getButtons().add(highScores);
        getButtons().add(achievements);
        getButtons().add(option);
        getButtons().add(credits);
        getButtons().add(quit);
        super.center();
    }
}
