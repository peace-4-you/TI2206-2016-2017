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
 *
 * @author Maurice Willemsen
 */
public abstract class ScoreScreen extends Screen implements PlayerObserver {
    /**
     * Amount of players.
     */
    private int players = 2;
    /**
     * Contains the player's name.
     */
    private String[] playernameString;
    /**
     * Contains the player's stats.
     */
    private Text[] playername;
    private Text[] playerscore;
    private Text[] droppedbubbles;
    private Text[] poppedbubbles;

    /**
     * Getter method: for the GameState ID.
     *
     * @return integer of BasicGameState number.
     */
    public abstract int getID();

    /**
     * Called from extending classes.
     * Calls the top class screen initializer.
     * Inits a basic Score Screen.
     *
     * @param stateBasedGame the state based game
     * @param game           the game container
     */
    public final void initScoreScreen(final GameContainer game,
                                      final StateBasedGame stateBasedGame) {
        initBasicScreen(game);
        playername = new Text[2];
        playernameString = new String[2];
        playerscore = new Text[2];
        droppedbubbles = new Text[2];
        poppedbubbles = new Text[2];
        playername[0] = new Text("Player: ", GameConfig.SECOND_LINE);
        playername[1] = new Text("Player: ", GameConfig.SIXT_LINE);
        playerscore[0] = new Text("Score: ", GameConfig.THIRD_LINE);
        playerscore[1] = new Text("Score: ", GameConfig.SEVENTH_LINE);
        droppedbubbles[0] = new Text("Dropped: ", GameConfig.FOURTH_LINE);
        droppedbubbles[1] = new Text("Dropped: ", GameConfig.EIGTH_LINE);
        poppedbubbles[0] = new Text("Popped: ", GameConfig.FIFTH_LINE);
        poppedbubbles[1] = new Text("Popped: ", GameConfig.NINETH_LINE);
        Button main = new Button("Go to Main Menu", GameConfig.ELEVENTH_LINE,
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
        getTexts().add(droppedbubbles[0]);
        getTexts().add(droppedbubbles[1]);
        getTexts().add(poppedbubbles[0]);
        getTexts().add(poppedbubbles[1]);
        getButtons().add(main);
    }

    /**
     * Updated by Observable. Sets data
     *
     * @param number integer of player number
     * @param name   String of player name
     * @param score  integer of player score
     * @param dropped amount of dropped bubbles
     * @param popped amount of dropped bubbles
     */
    public final void update(final int number, final String name,
                             final int score, final int dropped,
                                     final int popped) {
        if (number == 1 || number == 2) {
            this.playername[number - 1].setText("Player: " + name);
            this.playernameString[number - 1] = name;
            this.playerscore[number - 1].setText("Score: " + score);
            this.droppedbubbles[number - 1].setText("Dropped: " + dropped);
            this.poppedbubbles[number - 1].setText("Popped: " + popped);
        }
        this.center();
    }

    /**
     * Updated by Observable, sets amount of playerdata.
     *
     * @param playeramount integer about amount of players
     */
    public final void update(final int playeramount) {
        if (playeramount == 1 && players != 1) {
            getTexts().remove(playername[1]);
            getTexts().remove(playerscore[1]);
            getTexts().remove(droppedbubbles[1]);
            getTexts().remove(poppedbubbles[1]);
            players = 1;
        } else if (playeramount == 2 && players != 2) {
            getTexts().add(playername[1]);
            getTexts().add(playerscore[1]);
            getTexts().add(droppedbubbles[1]);
            getTexts().add(poppedbubbles[1]);
            players = 2;
        }
    }

    /**
     * Protected getter for the amount of players.
     *
     * @return int of playeramount
     */
    protected final int getPlayerAmount() {
        return players;
    }

    /**
     * Protected getter the player names.
     *
     * @return String array of playernames
     */
    protected final String[] getPlayerNames() {
        return playernameString;
    }
}
