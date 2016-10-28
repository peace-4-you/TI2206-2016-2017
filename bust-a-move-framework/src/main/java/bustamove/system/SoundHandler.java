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
    private static volatile SoundHandler uniqueSoundHandler;
    /**
     * The background music.
     */
    private static Music backgroundMusic;
    /**
     * The volume at which all audio will be played (between 0 and 1).
     */
    private static float volume = 0;
    /**
     * The cannon firing sound.
     */
    private static Sound fireSound;
    /**
     * The bubble popping sound.
     */
    private static Sound popSound;
    /**
     * The button clicking sound.
     */
    private static Sound clickSound;
    /**
     * The game winning sound.
     */
    private static Sound winSound;
    /**
     * The game losing sound.
     */
    private static Sound loseSound;

    /**
     * Initializes the SoundHandler static class.
     */
    public void init() {
        try {
            backgroundMusic = new Music("res/music.ogg");
            backgroundMusic.loop();
            backgroundMusic.setVolume(volume);
            fireSound = new Sound("res/fire.ogg");
            popSound = new Sound("res/pop.ogg");
            clickSound = new Sound("res/click.ogg");
            winSound = new Sound("res/win.ogg");
            loseSound = new Sound("res/lose.ogg");
        } catch (SlickException e) {
            Log.getInstance().log(this, "Error loading a sound.");
        }
    }

    /**
     * Creates sound handler object.
     */
    private SoundHandler() { }

    /**
     * Singleton returns the unique instance.
     * @return unique singleton instance
     */
    public static SoundHandler getInstance() {
        if (uniqueSoundHandler == null) {
            synchronized (SoundHandler.class) {
                if (uniqueSoundHandler == null) {
                    uniqueSoundHandler = new SoundHandler();
                }
            }
        }
        return uniqueSoundHandler;
    }

    /**
     * Plays a firing sound.
     */
    public void playFireSound() {
        if (fireSound != null) {
            fireSound.play(1.0f, volume);
        }
    }

    /**
     * Plays a popping sound.
     */
    public void playPopSound() {
        if (popSound != null) {
            popSound.play(1.0f, volume);
        }
    }

    /**
     * Plays a button clicking sound.
     */
    public void playClickSound() {
        if (clickSound != null) {
            clickSound.play(1.0f, volume);
        }
    }

    /**
     * Plays a game winning sound.
     */
    public void playWinSound() {
        if (winSound != null) {
            winSound.play(1.0f, volume);
        }
    }

    /**
     * Plays a game losing sound.
     */
    public void playLoseSound() {
        if (loseSound != null) {
            loseSound.play(1.0f, volume);
        }
    }

    /**
     * Returns the volume of the game.
     * @return volume of the game
     */
    public float getVolume() {
        return volume;
    }

    /**
     * Sets the volume of the game.
     * @param vol volume to set to
     */
    public void setVolume(final float vol) {
        volume = vol;
        backgroundMusic.setVolume(volume);
    }
}