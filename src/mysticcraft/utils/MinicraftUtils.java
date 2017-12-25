package mysticcraft.utils;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.FloatControl;

import org.apache.commons.io.FileUtils;

import com.google.gson.JsonObject;

import mysticcraft.main.Game;
import mysticcraft.main.Reference;
import tk.jidgu.util.Sys;
import tk.jidgu.util.Utils;

public class MinicraftUtils {
	private static final String OS = Sys.osName;
	private static final String OS_ARCH = Sys.osArch;
	private static final String imageFileFormatJSONTags = "mysticcraft.image_file_format";

	public static String getOSName() {
		return OS;
	}

	public static boolean isWindows() {
		return OS.contains("Win");
	}

	public static boolean isMac() {
		return OS.contains("Mac");
	}

	public static boolean isUnix() {
		return OS.contains("Nix") || OS.contains("Nux") || OS.contains("Aix");
	}

	public static String getOSArchitecture() {
		return OS_ARCH;
	}

	@SuppressWarnings("deprecation")
	public static void takeScreenshot(int width, int height, String file) {
		Sys.println("Taking screenshot.....", System.out);
		try {
			Thread.sleep(30);
			if (Game.GAME_JSON_FILE.exists()) {
				if (FileUtils.readFileToString(Game.GAME_JSON_FILE).contains(imageFileFormatJSONTags)) {
					Object obj = Game.jsonParser.parse(new FileReader(Game.GAME_JSON_FILE));
					JsonObject jsonObj = (JsonObject) obj;
					String imageFileFormat = (String) jsonObj.get(imageFileFormatJSONTags).getAsString();
					Utils.takeAndSaveScreenshot(0, 0, width, height, Reference.SCREENSHOT_DIRECTORY, file, imageFileFormat);
				} else {
					Utils.takeAndSaveScreenshot(0, 0, width, height, Reference.SCREENSHOT_DIRECTORY, file, "png");
				}
			} else {
				Utils.takeAndSaveScreenshot(0, 0, width, height, Reference.SCREENSHOT_DIRECTORY, file, "png");
			}
			Thread.sleep(30);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Sys.println("Screenshot had been sucessfully taked.", System.out);
	}

	public class MusicPlayer implements Runnable {

		private ArrayList<String> musicFiles;
		private int currentSongIndex;

		public MusicPlayer(String... files) {
			musicFiles = new ArrayList<String>();
			for (String file : files)
				musicFiles.add(Reference.RESOURCE_DIRECTORY + file + ".wav");
		}

		private void playSound(String fileName) {
			try {
				File soundFile = new File(fileName);
				AudioInputStream ais = AudioSystem.getAudioInputStream(soundFile);
				AudioFormat format = ais.getFormat();
				DataLine.Info info = new DataLine.Info(Clip.class, format);
				Clip clip = (Clip) AudioSystem.getLine(info);
				clip.open(ais);
				FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
				gainControl.setValue(-10);
				clip.start();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		@Override
		public void run() {
			playSound(musicFiles.get(currentSongIndex));
		}
	}
}
