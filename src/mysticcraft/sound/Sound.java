package mysticcraft.sound;

import java.applet.Applet;
import java.applet.AudioClip;

import mysticcraft.main.Game;
import mysticcraft.main.Reference;

public class Sound {
	public static final Sound buttonClick = new Sound("/buttonClick.wav");

	public static final Sound playerHurt = new Sound("/playerHurt.wav"); // creates
																			// a
																			// sound
																			// from
																			// playerhurt.wav
																			// file
	public static final Sound playerDeath = new Sound("/playerDeath.wav"); // creates
																			// a
																			// sound
																			// from
																			// death.wav
																			// file
	public static final Sound monsterHurt = new Sound("/monsterHurt.wav"); // creates
																			// a
																			// sound
																			// from
																			// monsterhurt.wav
																			// file
	public static final Sound test = new Sound("/test.wav"); // creates a sound
																// from test.wav
																// file
	public static final Sound pickup = new Sound("/pickup.wav"); // creates a
																	// sound
																	// from
																	// pickup.wav
																	// file
	public static final Sound bossDeath = new Sound("/bossDeath.wav"); // creates
																		// a
																		// sound
																		// from
																		// bossdeath.wav
																		// file
	public static final Sound craft = new Sound("/craft.wav"); // creates a
																// sound from
																// craft.wav
																// file

	// New sounds
	// public static final Sound zombieHurt = new Sound("/zombieHurt.wav");
	// public static final Sound zombieDeath = new Sound("/zombieDeath.wav");
	public static final Sound healGlove = new Sound("/healGlove.wav");
	public static final Sound music = new Sound("/music.wav");

	private AudioClip clip; // Creates a audio clip to be played

	private Sound(String name) {
		try {
			clip = Applet.newAudioClip(Sound.class.getResource(Reference.RESOURCE_LOCATION + name)); // tries
			// to
			// load
			// the
			// audio
			// clip
			// from
			// the
			// name
			// you
			// gave
			// above.
		} catch (Throwable e) {
			e.printStackTrace(); // else it will throw an error
		}
	}

	public void play() {
		if (Game.enablesSound) {
			try {
				clip.play(); // plays the sound clip when called
			} catch (Throwable e) {
				e.printStackTrace();
			}
		} else {

		}
	}

	public void stop() {
		// if (Game.enablesSound) {
		try {
			clip.stop();
		} catch (Throwable e) {
			e.printStackTrace();
		}
		// } else {

		// }
	}
}