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

import java.util.LinkedList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import bustamove.game.Highscore;

/**
 * Generates a HighscoreScreen as a instance of GameState.
 *
 * @author Justin Segond
 */
public class HighscoreScreen extends BasicGameState {
    /**
     * Main menu Button.
     */
    private Button mainmenu;
    /**
     * The list of text elements that store the high score names.
     */
    private LinkedList<Text> names = new LinkedList<Text>();
    /**
     * The list of text elements that store the high score scores.
     */
    private LinkedList<Text> scores = new LinkedList<Text>();
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
        mainmenu = new Button("Main menu", GameConfig.FIRST_LINE,
                GameConfig.WIDTH2, GameConfig.HEIGHT);
        mainmenu.centerButton(game);
        mainmenu.addGameStateChangeAction(stateBasedGame,
                GameState.MAIN_MENU);
    }

    /**
     * Called when BasicGameState is entered.
     * @param game the game container
     * @param stateBasedGame the state based game
     * @throws SlickException any type of slick exception
     */
    public final void enter(final GameContainer game,
            final StateBasedGame stateBasedGame) throws SlickException {
        names.clear();
        scores.clear();
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
            scores.addLast(scoreText);
            names.addLast(nameText);
        }
    }

    /**
     * Renders the BasicGameState.
     * @param game the game container
     * @param stateBasedGame the state based game
     * @param graphics Graphics object
     * @throws SlickException any type of slick exception
     */
    public final void render(final GameContainer game,
            final StateBasedGame stateBasedGame, final Graphics graphics)
            throws SlickException {
        for (Text t : names) {
            t.draw(graphics);
        }
        for (Text t : scores) {
            t.draw(graphics);
        }
        mainmenu.draw(graphics);
    }

    /**
     * Updates the BasicGameState.
     * @param game the game container
     * @param stateBasedGame the state based game
     * @param i delta of time exceeded
     * @throws SlickException any type of slick exception
     */
    public final void update(final GameContainer game,
            final StateBasedGame stateBasedGame, final int i)
            throws SlickException {
        Input input = game.getInput();
        if (input.isMousePressed(Input.MOUSE_LEFT_BUTTON)
                && mainmenu.isInBounds(input)) {
            mainmenu.click();
        }
    }

}
