
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
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;
import bustamove.App;
import bustamove.player.PlayerObserver;


/**
 * Generates a VictoryScreen as a instance of GameState.
 * @author Jason Xie, Maurice Willemsen
 */
public class VictoryScreen extends BasicGameState implements PlayerObserver {
    /**
     * Main Button.
     */
    private Button main;
    /**
     * Restart Button.
     */
    private Button restart;
    /**
     * Contains the player's name.
     */
    private Text player1name;
    /**
     * Contains the player's score.
     */
    private Text player1score;
    /**
     * Contains the player 2 name.
     */
    private Text player2name;
    /**
     * Contains the player 2 score.
     */
    private Text player2score;
    /**
     * Contains the fail message.
     */
    private Text wonText;
    /**
     * Amount of players.
     */
    private int players = 0;

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
        wonText = new Text("You Won", GameConfig.FIRST_LINE);
        wonText.centerText(game);
        player1name = new Text("Player: ", GameConfig.SECOND_LINE);
        player1name.centerText(game);
        player1score = new Text("Score: ", GameConfig.THIRD_LINE);
        player1score.centerText(game);
        player2name = new Text("Player:", GameConfig.SIXT_LINE);
        player2name.centerText(game);
        player2score = new Text("Score:", GameConfig.SEVENTH_LINE);
        player2score.centerText(game);
        main = new Button("Main Menu", GameConfig.FOURTH_LINE,
                GameConfig.WIDTH2, GameConfig.HEIGHT);
        main.centerButton(game);
        restart = new Button("Restart", GameConfig.FIFTH_LINE,
                GameConfig.WIDTH1, GameConfig.HEIGHT);
        restart.centerButton(game);
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
        wonText.draw(graphics);
        player1name.draw(graphics);
        player1score.draw(graphics);
        restart.draw(graphics);
        main.draw(graphics);
        if (this.players == 2) {
            player2name.draw(graphics);
            player2score.draw(graphics);
        }
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
        player1name.centerText(game);
        player1score.centerText(game);
        player2name.centerText(game);
        player2score.centerText(game);
        Input input = game.getInput();
        if (input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
            if (main.isInBounds(input)) {
                App.getGame().destroyGame();
                stateBasedGame.enterState(GameState.MAIN_MENU,
                        new FadeOutTransition(), new FadeInTransition());
            }
            if (restart.isInBounds(input)) {
                App.getGame().destroyGame();
                if (players == 2) {
                    App.getGame().start2Player();
                } else {
                    App.getGame().start1Player();
                }
                stateBasedGame.enterState(GameState.GAME_ACTIVE,
                        new FadeOutTransition(), new FadeInTransition());
            }
        }
    }

    /**
     * Updated by Observable. Sets data
     * @param number integer of player number
     * @param name String of player name
     * @param score integer of player score
     */
    public final void update(final int number, final String name,
            final int score) {
        if (number == 1) {
            this.player1name.setText("Player: " + name);
            this.player1score.setText("Score: " + score);
        } else if (number == 2) {
            this.player2name.setText("Player: " + name);
            this.player2score.setText("Score: " + score);
        }
    }

    /**
     * Updated by Observable, sets amount of playerdata.
     * @param playeramount integer about amount of players
     */
    public final void update(final int playeramount) {
        this.players = playeramount;
    }
}
