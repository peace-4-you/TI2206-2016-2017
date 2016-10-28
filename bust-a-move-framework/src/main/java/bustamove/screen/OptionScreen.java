package bustamove.screen;

import bustamove.screen.attributes.Button;
import bustamove.screen.config.GameConfig;
import bustamove.screen.config.GameState;
import bustamove.system.Log;
import bustamove.system.SoundHandler;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

/**
 * The OptionScreen class display the option menu.
 * @author Winer bao
 */
public class OptionScreen extends Screen {
    /**
     * Getter method: for the GameState ID.
     * @return integer of BasicGameState number.
     */
    public final int getID() {
        return GameState.OPTION_SCREEN;
    }

    /**
     * Initializes a option screen object.
     */
    public OptionScreen() {
        Log.getInstance().log(this, "Option Screen initialised");
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
        initBasicScreen(game);
        Button mute = getMuteUnmuteButton();
        getButtons().add(mute);
        Button back = new Button("Back", GameConfig.FIFTH_LINE,
                GameConfig.WIDTH2, GameConfig.HEIGHT);
        back.addGameStateChangeAction(stateBasedGame,
                GameState.MAIN_MENU);
        getButtons().add(back);
        super.center();
    }

    /**
     * Create a mute or unmute button.
     * @return Button
     */
    private Button getMuteUnmuteButton() {
        final Button button;

        final boolean isMuted = SoundHandler.getInstance().getVolume() == 0;

        if (isMuted) {
            button = new Button("Unmute", GameConfig.FOURTH_LINE,
                    GameConfig.WIDTH2, GameConfig.HEIGHT);
        } else {
            button = new Button("Mute", GameConfig.FOURTH_LINE,
                    GameConfig.WIDTH2, GameConfig.HEIGHT);
        }

        button.addAction(new Runnable() {
            public void run() {
                final boolean isMuted = SoundHandler.getInstance()
                        .getVolume() == 0;

                if (isMuted) {
                    button.setText("Mute");
                    SoundHandler.getInstance().setVolume(1);
                } else {
                    button.setText("Unmute");
                    SoundHandler.getInstance().setVolume(0);
                }
            }
        });

        return button;
    }
}
