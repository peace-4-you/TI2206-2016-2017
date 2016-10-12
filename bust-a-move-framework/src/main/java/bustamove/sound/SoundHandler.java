package bustamove.sound;

import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

import bustamove.system.Log;

public class SoundHandler {

	private static Music backgroundMusic;
	
	private static float volume = 0.5f;
	
	private static Sound fireSound;
	private static Sound popSound;
	
	public static void init() { 
		try {
			backgroundMusic = new Music("res/music.ogg");
			backgroundMusic.loop();
			backgroundMusic.setVolume(volume);
			fireSound = new Sound("res/fire.ogg");
			popSound = new Sound("res/pop.ogg");
		} catch(SlickException e) {
			Log.log("Error loading a sound.");
		}
	}
	
	
	public static void playFireSound() {
		if(fireSound != null)
			fireSound.play();
	}
	
	public static void playPopSound() {
		if(popSound != null) 
			popSound.play();
	}
}
