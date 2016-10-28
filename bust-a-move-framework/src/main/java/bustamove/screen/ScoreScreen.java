package bustamove.screen;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import bustamove.App;
import bustamove.screen.attributes.Button;
import bustamove.screen.attributes.Text;
import bustamove.screen.config.GameConfig;
import bustamove.screen.config.GameState;
import bustamove.util.PlayerObserver;

/**
 * Abstract Screen that contains basics for a score screen.
 * @author Maurice Willemsen
 */
public abstract class ScoreScreen extends Screen implements PlayerObserver {
    /**
     * Amount of players.
     */
    private int players = 0;
    /**
     * Contains the player's name.
     */
    private String[] playernameString;
    /**
     * Contains the player's texts.
     */
    private Text[] playername;
    /**
     * Contains the player's score.
     */
    private Text[] playerscore;

    /**
     * Getter method: for the GameState ID.
     * @return integer of BasicGameState number.
     */
    public abstract int getID();

    /**
     * Called from extending classes.
     * Calls the top class screen initializer.
     * Inits a basic Score Screen.
     * @param stateBasedGame the state based game
     * @param game the game container
     */
    public final void initScoreScreen(final GameContainer game,
            final StateBasedGame stateBasedGame) {
        initBasicScreen(game);
        playername = new Text[2];
        playernameString = new String[2];
        playerscore = new Text[2];
        playername[0] = new Text("Player: ", GameConfig.SECOND_LINE);
        playername[1] = new Text("Player: ", GameConfig.FOURTH_LINE);
        playerscore[0] = new Text("Score: ", GameConfig.THIRD_LINE);
        playerscore[1] = new Text("Score: ", GameConfig.FIFTH_LINE);
        Button main = new Button("Go to Main Menu", GameConfig.SEVENTH_LINE,
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
        getTexts().add(playername[0]);
        getTexts().add(playername[1]);
        getTexts().add(playerscore[0]);
        getTexts().add(playerscore[1]);
        getButtons().add(main);
    }

    /**
     * Updated by Observable. Sets data
     * @param number integer of player number
     * @param name String of player name
     * @param score integer of player score
     */
    public final void update(final int number, final String name,
                             final int score) {
        if (number == 1 || number == 2) {
            this.playername[number - 1].setText("Player: " + name);
            this.playernameString[number - 1] = name;
            this.playerscore[number - 1].setText("Score: " + score);
        }
        this.center();
    }

    /**
     * Updated by Observable, sets amount of playerdata.
     * @param playeramount integer about amount of players
     */
    public final void update(final int playeramount) {
        players = playeramount;
        if (playeramount == 1) {
            getTexts().remove(playername[1]);
            getTexts().remove(playerscore[1]);
        } else if (playeramount == 2) {
            getTexts().add(playername[1]);
            getTexts().add(playerscore[1]);
        }
    }

    /**
     * Protected getter for the amount of players.
     * @return int of playeramount
     */
    protected final int getPlayerAmount() {
        return players;
    }

    /**
     * Protected getter the player names.
     * @return String array of playernames
     */
    protected final String[] getPlayerNames() {
        return playernameString;
    }
}
