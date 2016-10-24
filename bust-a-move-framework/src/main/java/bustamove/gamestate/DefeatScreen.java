/*
 * File: DefeatScreen.java
 *
 * Class: DefeatScreen
 *
 * Version: 0.0.3
 *
 * Date: September 26th, 2016
 */


package bustamove.gamestate;

import java.util.ArrayList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import bustamove.App;
import bustamove.game.Highscore;
import bustamove.util.PlayerObserver;

/**
 * Generates a DefeatScreen as a instance of GameState.
 *
 * @author Jason Xie, Maurice Willemsen
 */
public class DefeatScreen extends BasicGameState implements PlayerObserver {
    /**
     * All the buttons.
     */
    private ArrayList<Button> buttons;
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
     * The player 1 high score text.
     */
    private Text player1highscore;
    /**
     * The player 2 high score text.
     */
    private Text player2highscore;
    /**
     * Contains the player 2 score.
     */
    private Text player2score;
    /**
     * The player 1s score.
     */
    private int player1Score;
    /**
     * The player 2s score.
     */
    private int player2Score;
    /**
     * The player 1s name.
     */
    private String player1Name;
    /**
     * The player 2s name.
     */
    private String player2Name;
    /**
     * Contains the fail message.
     */
    private Text failedText;
    /**
     * Amount of players.
     */
    private int players = 0;
    /**
     * GameContainer.
     */
    private GameContainer gamecontainer;
    /**
     * The highscores object.
     */
    private Highscore highscores;

    /**
     * Getter method: for the GameState ID.
     * @return integer of BasicGameState number.
     */
    public final int getID() {
        return GameState.DEFEAT_SCREEN;
    }

    /**
     * Creates a new DefeatScreen object.
     * @param highscore The highscore to use
     */
    public DefeatScreen(final Highscore highscore) {
        this.highscores = highscore;
    }

    /**
     * Called when BasicGameState initializes.
     * @param game the game container
     * @param stateBasedGame the state based game
     * @throws SlickException any type of slick exception
     */
    public final void init(final GameContainer game,
            final StateBasedGame stateBasedGame) throws SlickException {
        buttons = new ArrayList<Button>();
        gamecontainer = game;
        failedText = new Text("You Failed", GameConfig.FIRST_LINE);
        failedText.centerText(game);
        player1name = new Text("Player: ", GameConfig.SECOND_LINE);
        player1name.centerText(game);
        player1score = new Text("Score: ", GameConfig.THIRD_LINE);
        player1score.centerText(game);
        player2name = new Text("Player:", GameConfig.SIXT_LINE);
        player2name.centerText(game);
        player2score = new Text("Score:", GameConfig.SEVENTH_LINE);
        player2score.centerText(game);
        player1highscore = new Text("", GameConfig.EIGTH_LINE);
        player1highscore.centerText(game);
        player2highscore = new Text("", GameConfig.NINTH_LINE);
        player2highscore.centerText(game);
        Button restart = new Button("Restart", GameConfig.FOURTH_LINE,
                GameConfig.WIDTH2, GameConfig.HEIGHT);
        restart.centerButton(game);
        restart.addAction(new Runnable() {
            public void run() {
                try {
                    App.getGame().destroyGame();
                    if (players == 2) {
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
        Button main = new Button("Go to Main Menu", GameConfig.FIFTH_LINE,
                GameConfig.WIDTH3, GameConfig.HEIGHT);
        main.addAction(new Runnable() {
            public void run() {
                try {
                    App.getGame().destroyGame();
                } catch (SlickException e) {
                    e.printStackTrace();
                }
            }
        });
        main.addGameStateChangeAction(stateBasedGame,
                GameState.MAIN_MENU);
        main.centerButton(game);
        buttons.add(restart);
        buttons.add(main);
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
        failedText.draw(graphics);
        player1name.draw(graphics);
        player1score.draw(graphics);
        player1highscore.draw(graphics);
        player2highscore.draw(graphics);
        for (Button b : buttons) {
            b.draw(graphics);
        }
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
        gamecontainer = game;
        Input input = game.getInput();
        if (input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
            for (Button b : buttons) {
                if (b.isInBounds(input)) {
                    b.click();
                }
            }
        }
    }

    /**
     * Centering all buttons and texts in the screen.
     */
    public final void center() {
        for (Button b : buttons) {
            b.centerButton(gamecontainer);
        }
        failedText.centerText(gamecontainer);
        player1name.centerText(gamecontainer);
        player1score.centerText(gamecontainer);
        player2name.centerText(gamecontainer);
        player2score.centerText(gamecontainer);
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
            this.player1Score = score;
            this.player1Name = name;
            this.player1name.setText("Player: " + name);
            this.player1score.setText("Score: " + score);
        } else if (number == 2) {
            this.player2Score = score;
            this.player2Name = name;
            this.player2name.setText("Player: " + name);
            this.player2score.setText("Score: " + score);
        }
        this.center();
    }

    /**
     * Called when BasicGameState is entered.
     * @param game the game container
     * @param stateBasedGame the state based game
     * @throws SlickException any type of slick exception
     */
    public final void enter(final GameContainer game,
            final StateBasedGame stateBasedGame) throws SlickException {
        if (highscores.addEntryAndSave(player1Name, player1Score)) {
            player1highscore.setText(player1Name
                    + " achieved a highscore of " + player1Score + "!");
            player1highscore.centerText(game);
        }
        if (players == 2
            && highscores.addEntryAndSave(player2Name, player2Score)) {
            player2highscore.setText(player2Name
                    + " achieved a highscore of "
                    + player2Score + "!");
            player2highscore.centerText(game);
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
