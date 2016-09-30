/*
 * File: PausedScreen.java
 * Class: PausedScreen
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
 * Generates a PausedScreen as a instance of GameState.
 * @author Jason Xie, Maurice Willemsen
 */
public class PausedScreen extends BasicGameState implements Observer,Screen {
    /**
     * Resume Button.
     */
    private Button resume;
    /**
     * Quit Button.
     */
    private Button quit;
    /**
     * Contains the player's name.
     */
    private Text nameText;
    /**
     * Contains the player's score.
     */
    private Text scoreText;
    /**
     * Contains the pause message.
     */
    private Text pauseText;
    /**
     * Integer of score, should be updated by observer.
     */
    private int score;
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
                           final StateBasedGame stateBasedGame)
            throws SlickException {
        pauseText = new Text("Game Paused", GameConfig.FIRST_LINE);
        pauseText.centerText(game);
        nameText = new Text("Player: " + App.getGame().player.getName(),
                GameConfig.SECOND_LINE);
        nameText.centerText(game);
        scoreText = new Text("Score: " + this.score, GameConfig.THIRD_LINE);
        scoreText.centerText(game);
        resume = new Button("Resume", GameConfig.FOURTH_LINE, GameConfig.WIDTH2,
                GameConfig.HEIGHT);
        resume.centerButton(game);
        quit = new Button("Quit", GameConfig.FIFTH_LINE, GameConfig.WIDTH2,
                GameConfig.HEIGHT);
        quit.centerButton(game);
        App.getGame().player.getScore().addAsObserver(this);
    }
    /**
     * Renders the BasicGameState.
     * @param game the game container
     * @param stateBasedGame the state based game
     * @param graphics Graphics object
     * @throws SlickException any type of slick exception
     */
    public final void render(final GameContainer game,
                             final StateBasedGame stateBasedGame,
                             final Graphics graphics)
            throws SlickException {
        pauseText.draw(graphics);
        nameText.draw(graphics);
        scoreText.draw(graphics);
        resume.draw(graphics);
        quit.draw(graphics);
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
        nameText.setText("Player: " + App.getGame().player.getName());
        nameText.centerText(game);
        scoreText.setText("Score: " + this.score);
        scoreText.centerText(game);
        Input input = game.getInput();
        if (input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
            if (resume.isInBounds(input)) {
                stateBasedGame.enterState(GameState.GAME_ACTIVE,
                        new FadeOutTransition(), new FadeInTransition());
            }
            if (quit.isInBounds(input)) {
                stateBasedGame.enterState(GameState.MAIN_MENU,
                        new FadeOutTransition(), new FadeInTransition());
            }
        }
    }
    /**
     * Updates the Observer.
     * @param o the observable item
     * @param arg the observable argument
     */
    public final void update(final Observable o, final Object arg) {
        this.score = (Integer) arg;
    }
}
