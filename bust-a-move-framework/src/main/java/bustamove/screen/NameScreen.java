package bustamove.screen;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.gui.TextField;
import org.newdawn.slick.state.StateBasedGame;

import bustamove.App;
import bustamove.screen.attributes.Button;
import bustamove.screen.config.GameConfig;
import bustamove.screen.config.GameState;

/**
 * Abstract method contains basics for a name input screen.
 * @author Maurice Willemsen
 */
public abstract class NameScreen extends Screen {
    /**
     * Textfields for input names.
     */
    private TextField[] namefields;
    /**
     * Getter method: for the GameState ID.
     * @return integer of BasicGameState number.
     */
    public abstract int getID();

    /**
     * Called from extending classes.
     * Calls the top class screen initializer.
     * Inits a basic Score Screen.
     * @param game the game container
     * @param stateBasedGame the state based game
     */
    public final void initNameScreen(final GameContainer game,
            final StateBasedGame stateBasedGame) {
        initBasicScreen(game);
        namefields = new TextField[2];
        Button main = new Button("Go to Main Menu", GameConfig.EIGTH_LINE,
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
        getButtons().add(main);
        super.center();
    }

    /**
     * Getter method for the namefields.
     * @return TextField array namefields.
     */
    protected final TextField[] getNamefields() {
        return namefields;
    }
}
