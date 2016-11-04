package bustamove.system;

import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

/**
 * The SoundHandler class plays all the sounds in the game.
 *
 * @author Justin Segond, Winer Bao
 */

public final class SoundHandler {
    /**
     * Singleton variable.
     */
    private static SoundHandler uniqueSoundHandler = new SoundHandler();
    /**
     * The background music.
     */
    private Music backgroundMusic;
    /**
     * The volume at which the backgound audio will be played (between 0 and 1).
     */
    private float volumeBackground = 0;
    /**
     * The volume at which the sound effects audio will be played
     * (between 0 and 1).
     */
    private float volumeEffects = 0;
    /**
     * The cannon firing sound.
     */
    private Sound fireSound;
    /**
     * The bubble popping sound.
     */
    private Sound popSound;
    /**
     * The button clicking sound.
     */
    private Sound clickSound;
    /**
     * The game winning sound.
     */
    private Sound winSound;
    /**
     * The game losing sound.
     */
    private Sound loseSound;

    /**
     * Creates sound handler object.
     */
    private SoundHandler() {
    }

    /**
     * Singleton returns the unique instance.
     *
     * @return unique singleton instance
     */
    public static synchronized SoundHandler getInstance() {
        return uniqueSoundHandler;
    }

    /**
     * Initializes the SoundHandler static class.
     */
    public void init() {
        try {
            backgroundMusic = new Music("res/music.ogg");
            backgroundMusic.loop();
            backgroundMusic.setVolume(volumeBackground);
            fireSound = new Sound("res/fire.ogg");
            popSound = new Sound("res/pop.ogg");
            clickSound = new Sound("res/click.ogg");
            winSound = new Sound("res/win.ogg");
            loseSound = new Sound("res/lose.ogg");
        } catch (SlickException e) {
            Log.getInstance().log(this, "Error loading a sound.");
        } catch (UnsatisfiedLinkError e) {
            e.printStackTrace();
            Log.getInstance().log(this, "No LWJGL in java.library.path");
        }
    }

    /**
     * Plays a firing sound.
     */
    public void playFireSound() {
        if (fireSound != null) {
            fireSound.play(1.0f, volumeEffects);
        }
    }

    /**
     * Plays a popping sound.
     */
    public void playPopSound() {
        if (popSound != null) {
            popSound.play(1.0f, volumeEffects);
        }
    }

    /**
     * Plays a button clicking sound.
     */
    public void playClickSound() {
        if (clickSound != null) {
            clickSound.play(1.0f, volumeEffects);
        }
    }

    /**
     * Plays a game winning sound.
     */
    public void playWinSound() {
        if (winSound != null) {
            winSound.play(1.0f, volumeEffects);
        }
    }

    /**
     * Plays a game losing sound.
     */
    public void playLoseSound() {
        if (loseSound != null) {
            loseSound.play(1.0f, volumeEffects);
        }
    }

    /**
     * Returns the volume of the game background music.
     *
     * @return volume of the game
     */
    public float getVolumeBackground() {
        return volumeBackground;
    }

    /**
     * Returns the volume of the game sound effects.
     *
     * @return volume of the game
     */
    public float getVolumeEffects() {
        return volumeEffects;
    }

    /**
     * Sets the volume of the game background music.
     *
     * @param vol volume to set to
     */
    public void setVolumeBackground(final float vol) {
        volumeBackground = vol;
        if (backgroundMusic != null) {
            backgroundMusic.setVolume(volumeBackground);
        }
    }

    /**
     * Sets the volume of the game sound effects.
     *
     * @param vol volume to set to
     */
    public void setVolumeEffects(final float vol) {
        volumeEffects = vol;
    }

    /**
     * Increases the volume of the background.
     *
     * @param increment amount of volume increase
     */
    public void increaseVolumeBackground(final float increment) {
        volumeBackground += increment;
        if (volumeBackground > 1) {
            volumeBackground = 1;
        }
    }

    /**
     * Increases the volume of the sound effects.
     *
     * @param increment amount of volume increase
     */
    public void increaseVolumeEffects(final float increment) {
        volumeEffects += increment;
        if (volumeEffects > 1) {
            volumeEffects = 1;
        }
    }

    /**
     * Decreases the volume of the background.
     *
     * @param decrement amount of volume increase
     */
    public void decreaseVolumeBackground(final float decrement) {
        volumeBackground -= decrement;
        if (volumeBackground < 0) {
            volumeBackground = 0;
        }
        setVolumeBackground(volumeBackground);
    }

    /**
     * Increases the volume of the sound effects.
     *
     * @param decrement amount of volume increase
     */
    public void decreaseVolumeEffects(final float decrement) {
        volumeEffects -= decrement;
        if (volumeEffects < 0) {
            volumeEffects = 0;
        }
    }
}