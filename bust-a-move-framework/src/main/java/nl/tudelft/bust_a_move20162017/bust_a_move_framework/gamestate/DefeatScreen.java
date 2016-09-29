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
 * Generates a DefeatScreen as a instance of GameState.
 * @author Jason Xie, Maurice Willemsen
 */
public class DefeatScreen extends BasicGameState implements Observer {
    /**
     * Restart Button.
     */
    private Button restart;
    /**
     * Mainmenu Button.
     */
    private Button mainmenu;
    /**
     * Contains the player name.
     */
    private Text nameText;
    /**
     * Contains the player's score.
     */
    private Text scoreText;
    /**
     * Contains the fail message.
     */
    private Text failedText;
    /**
     * Player's score, should be updated by observer.
     */
    private int score;

    /**
     * Getter method: for the GameState ID.
     * @return integer of BasicGameState number.
     */
    public final int getID() {
        return GameState.DEFEAT_SCREEN;
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
        failedText = new Text("You Failed", GameConfig.FIRST_LINE);
        failedText.centerText(game);
        nameText = new Text("Player: " + App.getGame().player.getName(),
                GameConfig.SECOND_LINE);
        nameText.centerText(game);
        scoreText = new Text("Score: " + this.score, GameConfig.THIRD_LINE);
        scoreText.centerText(game);
        restart = new Button("Restart", GameConfig.FOURTH_LINE,
                GameConfig.WIDTH2, GameConfig.HEIGHT);
        restart.centerButton(game);
        mainmenu = new Button("Go to Main Menu", GameConfig.FIFTH_LINE,
                GameConfig.WIDTH3, GameConfig.HEIGHT);
        mainmenu.centerButton(game);
        App.getGame().player.score.addAsObserver(this);
    }
    /**
     * Renders the BasicGameState.
     * @param game the game container
     * @param stateBasedGame  the state based game
     * @param graphics Graphics object
     * @throws SlickException any type of slick exception
     */
    public final void render(final GameContainer game,
                             final StateBasedGame stateBasedGame,
                             final Graphics graphics)
            throws SlickException {
        failedText.draw(graphics);
        nameText.draw(graphics);
        scoreText.draw(graphics);
        restart.draw(graphics);
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
        nameText.setText("Player: " + App.getGame().player.getName());
        nameText.centerText(game);
        scoreText.setText("Score: " + this.score);
        scoreText.centerText(game);
        Input input = game.getInput();
        if (input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
            if (restart.isInBounds(input)) {
                stateBasedGame.getState(GameState.GAME_ACTIVE).init(game,
                        stateBasedGame);
                stateBasedGame.enterState(GameState.GAME_ACTIVE,
                        new FadeOutTransition(), new FadeInTransition());
            }
            if (mainmenu.isInBounds(input)) {
                stateBasedGame.getState(GameState.GAME_ACTIVE).init(game,
                        stateBasedGame);
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
