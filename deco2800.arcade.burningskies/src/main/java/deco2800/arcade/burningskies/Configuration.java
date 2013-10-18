package deco2800.arcade.burningskies;

import java.util.*;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

public class Configuration {
	
	// Volumes stored as integers from 0-100 inclusive, represents % max volume
	private static int masterVolume = 50;
	private static int effectsVolume = 50;
	private static int backgroundVolume = 50;
	// Difficulty stored as integer from 0 to 4, with 4 being insane mode
	private static int difficulty = 2;
	private static Map<Long, String> highScoresMap = new TreeMap<Long, String>();
	
	/* 
	 * Initialises all variables on game start up. Checks if config file exists, 
	 * creates new config file with default settings if non existent, otherwise
	 * reads in config file.
	 */
	public static void initialise() {
		if (!Gdx.files.external("BurningSkies/config.cfg").exists()) {
			writeConfig();
		} else {
			readConfig();
		}
	}
	
	/*
	 * Writes to the configuration file. Specifically, it writes masterVolume,
	 * effectsVolume, backgroundVolume and difficulty to the configuration file.
	 */
	public static void writeConfig() {
		
		FileHandle configFile = Gdx.files.external("BurningSkies/config.cfg");
		
		String lineOne = "masterVolume:" + masterVolume + System.getProperty("line.separator");
		String lineTwo = "effectsVolume:" + effectsVolume + System.getProperty("line.separator");
		String lineThree = "backgroundVolume:" + backgroundVolume + System.getProperty("line.separator");
		String lineFour = "difficulty:" + difficulty + System.getProperty("line.separator");
		
		configFile.writeString(lineOne + lineTwo + lineThree + lineFour, false);
	}
	
	/*
	 * Reads from the configuration file. Specifically, it reads masterVolume,
	 * effectsVolume, backgroundVolume and difficulty from the configuration 
	 * file.
	 */
	public static void readConfig() {
		FileHandle configFile = Gdx.files.external("BurningSkies/config.cfg");
		String text = configFile.readString();
		
		String[] options = text.split(System.getProperty("line.separator"));
		for (int i = 0; i < options.length; i++) {
			String[] currentOption = options[i].split(":");
			int temp = Integer.parseInt(currentOption[1]);
			switch(i) {
			case 0:
				if (temp > 100) {
					masterVolume = 100;
				} else if (temp < 0) {
					masterVolume = 0;
				} else {
					masterVolume = Integer.parseInt(currentOption[1]); 
				}
				break;
			case 1:
				if (temp > 100) {
					effectsVolume = 100;
				} else if (temp < 0) {
					effectsVolume = 0;
				} else {
					effectsVolume = Integer.parseInt(currentOption[1]); 
				}
				break;
			case 2:
				if (temp > 100) {
					backgroundVolume = 100;
				} else if (temp < 0) {
					backgroundVolume = 0;
				} else {
					backgroundVolume = Integer.parseInt(currentOption[1]); 
				}
				break;
			case 3:
				if (temp > 4) {
					difficulty = 3;
				} else if (temp < 0) {
					difficulty = 0;
				} else {
					difficulty = Integer.parseInt(currentOption[1]);
				}
				break;
			default:
				System.out.println("Unexpected configuration encountered.");
			}
		}
	}
	
	public static void writeLocalHighScores() {
		FileHandle scoresFile = Gdx.files.external("BurningSkies/high_scores.txt");
		StringBuilder toWriteStrings = new StringBuilder();
		int limit = 0;
		
		for (Map.Entry<Long, String> entry : highScoresMap.entrySet()) {
			//We only care about the top five scores
			if (limit < 5) {
				toWriteStrings.append(entry.getValue() + ":" + entry.getKey() + System.getProperty("line.separator"));
				limit++;
			} else {
				break;
			}
		}
		scoresFile.writeString(toWriteStrings.toString(), false);
	}
	
	public static void readLocalHighScores() {
		FileHandle scoresFile = Gdx.files.external("BurningSkies/high_scores.txt");
		if (scoresFile.exists()) {
			String scores = scoresFile.readString();
			
			highScoresMap = new TreeMap<Long, String>();
			
			String[] scoresArray = scores.split(System.getProperty("line.separator"));
			
			for (int i = 0; i < scoresArray.length && i < 5; i++) {
				String[] currentScore = scoresArray[i].split(":");
				highScoresMap.put(Long.parseLong(currentScore[1]), currentScore[0]);
			}
		} else {
			System.out.println("No scores exist.");
		}
	}
		
	public static int getMasterVolumeInt() {
		return masterVolume;
	}
	
	public static int getEffectsVolumeInt() {
		return effectsVolume;
	}
	
	public static int getBackgroundVolumeInt() {
		return backgroundVolume;
	}
	
	public static float getEffectsVolume() {
		return ((float)(Configuration.masterVolume * Configuration.effectsVolume))/10000;
	}
	
	public static float getBackgroundVolume() {
		return ((float)(Configuration.masterVolume * Configuration.backgroundVolume))/10000;
	}
	
	public static int getDifficulty() {
		return Configuration.difficulty;
	}
	
	public static void setMasterVolume(int volume) {
		Configuration.masterVolume = volume;
	}

	public static void setEffectsVolume(int volume) {
		Configuration.effectsVolume = volume;
	}
	
	public static void setBackgroundVolume(int volume) {
		Configuration.backgroundVolume = volume;
	}
	
	public static void setDifficulty(int difficulty) {
		Configuration.difficulty = difficulty;
	}
	
	public static void addScore(String name, long score) {
		long inverseScore = score * -1;
		while (highScoresMap.containsKey(inverseScore)) {
			inverseScore += 1;
		}
		highScoresMap.put(new Long(inverseScore), name);
		writeLocalHighScores();
	}
	
	public static Map<Long, String> getScores() {
		return highScoresMap;
	}
	
}
