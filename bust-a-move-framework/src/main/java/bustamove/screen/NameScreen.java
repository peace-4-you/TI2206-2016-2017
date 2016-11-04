package bustamove.screen;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.gui.TextField;
import org.newdawn.slick.state.StateBasedGame;

import bustamove.App;
import bustamove.system.Log;
import bustamove.screen.attributes.Button;
import bustamove.screen.attributes.Text;
import bustamove.screen.config.GameConfig;
import bustamove.screen.config.GameState;
import bustamove.game.GameData.GameDifficulty;

/**
 * Abstract method contains basics for a name input screen.
 *
 * @author Maurice Willemsen
 */
public abstract class NameScreen extends Screen {
    /**
     * Textfields for input names.
     */
    private TextField[] namefields;
    /**
     * Selected game difficulty.
     */
    private GameDifficulty difficulty;
    /**
     * Text to display difficulty.
     */
    private Text difficultyText;


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
     * @param game           the game container
     * @param stateBasedGame the state based game
     */
    public final void initNameScreen(final GameContainer game,
                                     final StateBasedGame stateBasedGame) {
        initBasicScreen(game);
        namefields = new TextField[2];
        difficulty = GameDifficulty.NORMAL;
        difficultyText = new Text("" + difficulty, GameConfig.SEVENTH_LINE);
        Button main = new Button("Go to Main Menu", GameConfig.ELEVENTH_LINE,
                GameConfig.WIDTH3, GameConfig.HEIGHT);
        Button difficultyUp = new Button("^", GameConfig.SIXT_LINE,
            GameConfig.WIDTH0, GameConfig.HEIGHT);
        Button difficultyDown = new Button("v", GameConfig.EIGTH_LINE,
            GameConfig.WIDTH0, GameConfig.HEIGHT);

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
        difficultyUp.addAction(new Runnable() {
            public void run() {
                setDifficulty(difficulty.next());
                center();
                Log.getInstance().log(this,
                    "Difficulty set to " + getDifficulty());
            }
        });
        difficultyDown.addAction(new Runnable() {
            public void run() {
                setDifficulty(difficulty.prev());
                center();
                Log.getInstance().log(this,
                    "Difficulty set to " + getDifficulty());
            }
        });

        getButtons().add(main);
        getButtons().add(difficultyUp);
        getButtons().add(difficultyDown);
        super.center();
    }

    /**
     * Getter method for the namefields.
     *
     * @return TextField array namefields.
     */
    protected final TextField[] getNamefields() {
        return namefields;
    }
    /**
     * Getter method for the game difficulty.
     *
     * @return GameDifficulty difficulty.
     */
    protected final GameDifficulty getDifficulty() {
        return difficulty;
    }
    /**
     * Sets game difficulty and text.
     *
     * @param d GameDifficulty difficulty to set.
     */
    protected final void setDifficulty(GameDifficulty d) {
        difficulty = d;
        difficultyText.setText("" + d);
    }
    /**
     * Getter method for the difficulty Text.
     *
     * @return difficultyText Text.
     */
    protected final Text getDifficultyText() {
        return difficultyText;
    }
}
