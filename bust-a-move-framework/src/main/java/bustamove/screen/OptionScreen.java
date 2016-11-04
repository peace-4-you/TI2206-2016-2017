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
 *
 * @author Winer bao
 */
public class OptionScreen extends Screen {
    /**
     * Constants.
     */
    private static final float VOLUME_DOWN_XPOS = (640 - GameConfig.WIDTH2) / 2;
    private static final float VOLUME_UP_XPOS = VOLUME_DOWN_XPOS + 100;

    private SoundHandler soundHandler;
    private static final float VOLUME_STEP = 0.1f;

    private boolean backgroundMuted = true;
    private boolean effectsMuted = true;

    private Button muteBackground;
    private Button muteEffects;

    private Text backgroundVolumeText;
    private Text effectsVolumeText;

    /**
     * Getter method: for the GameState ID.
     *
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
     *
     * @param game           the game container
     * @param stateBasedGame the state based game
     * @throws SlickException any type of slick exception
     */
    public final void init(final GameContainer game,
                           final StateBasedGame stateBasedGame)
            throws SlickException {
        initBasicScreen(game);
        soundHandler = SoundHandler.getInstance();
        backgroundVolumeText = new Text("Background Music: "
                + soundHandler.getVolumeBackground(),
                GameConfig.FIRST_LINE);
        getTexts().add(backgroundVolumeText);
        muteBackground = getMuteUnmuteBackgroundButton();
        getButtons().add(muteBackground);
        effectsVolumeText = new Text("Sound Effects: "
                + soundHandler.getVolumeEffects(),
                GameConfig.THIRD_LINE);
        getTexts().add(effectsVolumeText);
        muteEffects = getMuteUnmuteSoundEffectButton();
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
     *
     * @return Button
     */
    private Button getMuteUnmuteBackgroundButton() {
        final Button button;

        if (backgroundMuted) {
            button = new Button("Unmute", GameConfig.SECOND_LINE,
                    GameConfig.WIDTH2, GameConfig.HEIGHT);
        } else {
            button = new Button("Mute", GameConfig.SECOND_LINE,
                    GameConfig.WIDTH2, GameConfig.HEIGHT);
        }

        button.addAction(new Runnable() {
            public void run() {
                if (backgroundMuted) {
                    backgroundMuted = false;
                    button.setText("Mute");
                    backgroundVolumeText.setText("Background Music: 1.0");
                    SoundHandler.getInstance().setVolumeBackground(1);
                } else {
                    backgroundMuted = true;
                    button.setText("Unmute");
                    backgroundVolumeText.setText("Background Music: 0.0");
                    SoundHandler.getInstance().setVolumeBackground(0);
                }
            }
        });

        return button;
    }

    /**
     * Create a mute or unmute sound effect button.
     *
     * @return Button
     */
    private Button getMuteUnmuteSoundEffectButton() {
        final Button button;

        if (effectsMuted) {
            button = new Button("Unmute", GameConfig.FOURTH_LINE,
                    GameConfig.WIDTH2, GameConfig.HEIGHT);
        } else {
            button = new Button("Mute", GameConfig.FOURTH_LINE,
                    GameConfig.WIDTH2, GameConfig.HEIGHT);
        }

        button.addAction(new Runnable() {
            public void run() {
                if (effectsMuted) {
                    effectsMuted = false;
                    button.setText("Mute");
                    effectsVolumeText.setText("Sound Effects: 1.0");
                    SoundHandler.getInstance().setVolumeEffects(1);
                } else {
                    effectsMuted = true;
                    button.setText("Unmute");
                    effectsVolumeText.setText("Sound Effects: 0.0");
                    SoundHandler.getInstance().setVolumeEffects(0);
                }
            }
        });

        return button;
    }

    /**
     * Create a volume up and down button.
     *
     * @return Button
     */
    private Button[] getVolumeUpDownButton() {
        final Button[] button = new Button[2];

        button[0] = new Button("-", VOLUME_DOWN_XPOS, GameConfig.SIXT_LINE,
                GameConfig.WIDTH0, GameConfig.HEIGHT);
        button[1] = new Button("+", VOLUME_UP_XPOS, GameConfig.SIXT_LINE,
                GameConfig.WIDTH0, GameConfig.HEIGHT);

        button[0].addAction(new Runnable() {
            public void run() {
                if (!backgroundMuted) {
                    soundHandler.decreaseVolumeBackground(VOLUME_STEP);
                    updateBackgroundText();
                    if (soundHandler.getVolumeBackground() == 0) {
                        muteBackground.setText("Unmute");
                        backgroundMuted = true;
                    }
                }
                if (!effectsMuted) {
                    soundHandler.decreaseVolumeEffects(VOLUME_STEP);
                    updateEffectsText();
                    if (soundHandler.getVolumeEffects() == 0) {
                        muteEffects.setText("Unmute");
                        effectsMuted = true;
                    }
                }
            }
        });

        button[1].addAction(new Runnable() {
            public void run() {
                if (!backgroundMuted) {
                    soundHandler.increaseVolumeBackground(VOLUME_STEP);
                    updateBackgroundText();
                }
                if (!effectsMuted) {
                    soundHandler.increaseVolumeEffects(VOLUME_STEP);
                    updateEffectsText();
                }
            }
        });
        return button;
    }

    /**
     * Updates the text for the background music.
     */
    public final void updateBackgroundText() {
        String backgroundvolumeText = String.format("Background Music: %.1f",
                soundHandler.getVolumeBackground());
        backgroundVolumeText.setText(backgroundvolumeText);
    }

    /**
     * Updates the text for the sound effects.
     */
    public final void updateEffectsText() {
        String effectvolumeText = String.format("Sound Effects: %.1f",
                soundHandler.getVolumeEffects());
        effectsVolumeText.setText(effectvolumeText);
    }
}
