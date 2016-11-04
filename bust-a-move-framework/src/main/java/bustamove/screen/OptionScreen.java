package bustamove.screen;

import bustamove.screen.attributes.Button;
import bustamove.screen.attributes.Text;
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
     * Constants.
     */
    private static final float VOLUME_DOWN_XPOS = (640 - GameConfig.WIDTH2) / 2;
    private static final float VOLUME_UP_XPOS = VOLUME_DOWN_XPOS + 100;
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
        Text backgroundMusicText = new Text("Background Music",
                GameConfig.FIRST_LINE);
        getTexts().add(backgroundMusicText);
        Button muteBackground = getMuteUnmuteBackgroundButton();
        getButtons().add(muteBackground);
        Text soundEffectsText = new Text("Sound Effects",
                GameConfig.THIRD_LINE);
        getTexts().add(soundEffectsText);
        Button muteEffects = getMuteUnmuteSoundEffectButton();
        getButtons().add(muteEffects);
        Button back = new Button("Back", GameConfig.SEVENTH_LINE,
                GameConfig.WIDTH2, GameConfig.HEIGHT);
        back.addGameStateChangeAction(stateBasedGame,
                GameState.MAIN_MENU);
        getButtons().add(back);
        Text volumeText = new Text("Volume", GameConfig.FIFTH_LINE);
        getTexts().add(volumeText);
        super.center();
        Button[] volumeButtons = getVolumeUpDownButton();
        getButtons().add(volumeButtons[0]);
        getButtons().add(volumeButtons[1]);
    }

    /**
     * Create a mute or unmute Background button.
     * @return Button
     */
    private Button getMuteUnmuteBackgroundButton() {
        final Button button;

        final boolean isMuted = SoundHandler.getInstance()
                .getVolumeBackground() == 0;

        if (isMuted) {
            button = new Button("Unmute", GameConfig.SECOND_LINE,
                    GameConfig.WIDTH2, GameConfig.HEIGHT);
        } else {
            button = new Button("Mute", GameConfig.SECOND_LINE,
                    GameConfig.WIDTH2, GameConfig.HEIGHT);
        }

        button.addAction(new Runnable() {
            public void run() {
                final boolean isMuted = SoundHandler.getInstance()
                        .getVolumeBackground() == 0;

                if (isMuted) {
                    button.setText("Mute");
                    SoundHandler.getInstance().setVolumeBackground(1);
                } else {
                    button.setText("Unmute");
                    SoundHandler.getInstance().setVolumeBackground(0);
                }
            }
        });

        return button;
    }

    /**
     * Create a mute or unmute sound effect button.
     * @return Button
     */
    private Button getMuteUnmuteSoundEffectButton() {
        final Button button;

        final boolean isMuted = SoundHandler.getInstance()
                .getVolumeEffects() == 0;

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
                        .getVolumeEffects() == 0;

                if (isMuted) {
                    button.setText("Mute");
                    SoundHandler.getInstance().setVolumeEffects(1);
                } else {
                    button.setText("Unmute");
                    SoundHandler.getInstance().setVolumeEffects(0);
                }
            }
        });

        return button;
    }

    /**
     * Create a volume up and down button.
     * @return Button
     */
    private Button[] getVolumeUpDownButton() {
        final Button[] button = new Button[2];
        final float volumeStep = 0.1f;

        button[0] = new Button("-", VOLUME_DOWN_XPOS, GameConfig.SIXT_LINE,
                    GameConfig.WIDTH0, GameConfig.HEIGHT);
        button[1] = new Button("+", VOLUME_UP_XPOS, GameConfig.SIXT_LINE,
                    GameConfig.WIDTH0, GameConfig.HEIGHT);

        button[0].addAction(new Runnable() {
            public void run() {
                float currentVolume = SoundHandler.getInstance()
                        .getVolumeBackground();
                float newVolume = currentVolume - volumeStep;
                final boolean backgroundIsMuted = SoundHandler.getInstance()
                        .getVolumeBackground() == 0;
                final boolean effectsIsMuted = SoundHandler.getInstance()
                        .getVolumeEffects() == 0;

                if (!backgroundIsMuted) {
                    SoundHandler.getInstance().setVolumeBackground(newVolume);
                }
                if (!effectsIsMuted) {
                    SoundHandler.getInstance().setVolumeEffects(newVolume);
                }
            }
        });

        button[1].addAction(new Runnable() {
            public void run() {
                float currentVolume = SoundHandler.getInstance()
                        .getVolumeBackground();
                float newVolume = currentVolume + volumeStep;
                final boolean backgroundIsMuted = SoundHandler.getInstance()
                        .getVolumeBackground() == 0;
                final boolean effectsIsMuted = SoundHandler.getInstance()
                        .getVolumeEffects() == 0;

                if (!backgroundIsMuted) {
                    SoundHandler.getInstance().setVolumeBackground(newVolume);
                }
                if (!effectsIsMuted) {
                    SoundHandler.getInstance().setVolumeEffects(newVolume);
                }
            }
        });

        return button;
    }
}
