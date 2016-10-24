/*
 * File: HighscoreScreen.java
 *
 * Class: HighscoreScreen
 *
 * Version: 0.0.3
 *
 * Date: September 26th, 2016
 */


package bustamove.gamestate;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import bustamove.game.Highscore;

/**
 * Generates a HighscoreScreen as a instance of GameState.
 * @author Justin Segond
 */
public class HighscoreScreen extends Screen {
    /**
     * The highscore object.
     */
    private Highscore highscores;

    /**
     * Getter method: for the GameState ID.
     * @return integer of BasicGameState number.
     */
    public final int getID() {
        return GameState.HIGHSCORES_SCREEN;
    }

    /**
     * Initializes a highscore screen object.
     * @param hs The highscore object to use for this screen.
     */
    public HighscoreScreen(final Highscore hs) {
        this.highscores = hs;
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
        Button mainmenu = new Button("Main menu", GameConfig.FIRST_LINE,
                GameConfig.WIDTH2, GameConfig.HEIGHT);
        mainmenu.addGameStateChangeAction(stateBasedGame,
                GameState.MAIN_MENU);
        buttons.add(mainmenu);
        super.center();
    }

    /**
     * Called when BasicGameState is entered.
     * @param game the game container
     * @param stateBasedGame the state based game
     * @throws SlickException any type of slick exception
     */
    public final void enter(final GameContainer game,
            final StateBasedGame stateBasedGame) throws SlickException {
        texts.clear();
        int nameLeftOffset = (int) (game.getWidth()
                * GameConfig.HIGHSCORE_NAME_OFFSET);
        int scoreLeftOffset = (int) (game.getWidth()
                * GameConfig.HIGHSCORE_SCORE_OFFSET);
        int textYpos = GameConfig.SECOND_LINE;
        for (int i = 0; i < GameConfig.HIGHSCORE_ENTRIES; i++) {
            int score = highscores.getScore(i);
            String name = highscores.getName(i);
            if (score == 0) {
                break;
            }
            Text nameText = new Text((i + 1) + ": " + name,
                    nameLeftOffset, textYpos);
            Text scoreText = new Text(Integer.toString(score),
                    scoreLeftOffset, textYpos);
            textYpos += GameConfig.HEIGHT;
            texts.add(nameText);
            texts.add(scoreText);           
        }
    }
}
