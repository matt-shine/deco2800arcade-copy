package deco2800.arcade.burningskies;

import java.io.IOException;
import java.util.*;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

public class Configuration {
	
	// Volumes stored as integers from 0-100 inclusive, represents % max volume
	private static int masterVolume;
	private static int effectsVolume;
	private static int backgroundVolume;
	// Difficulty stored as integer from 0 to 4, with 4 being insane mode
	private static int difficulty;
	// Hashmap of action:keyPress pairs
	private HashMap<String, String> keybindings;
	
	/*
	 * Writes to the configuration file. Specifically, it writes masterVolume,
	 * effectsVolume, backgroundVolume and difficulty to the configuration file.
	 */
	public static void writeConfig() {
		
		FileHandle configFile = Gdx.files.external("BurningSkies/config.cfg");
		
		String lineOne = "masterVolume:" + getMasterVolume() + System.getProperty("line.separator");
		String lineTwo = "effectsVolume:" + getEffectsVolume() + System.getProperty("line.separator");
		String lineThree = "backgroundVolume:" + getBackgroundVolume() + System.getProperty("line.separator");
		String lineFour = "difficulty:" + getDifficulty() + System.getProperty("line.separator");
		
		if (!configFile.exists()) {
			try {
				configFile.file().createNewFile();
			} catch (IOException e) {
				/* Ignore exception. Since configFile does not currently exist, 
				 	it throws an exception saying file does not exist, 
					yet it continues to create said file anyway.
				*/
			}
		}
		
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
	
	/*
	 * Writes to the keybinding and configuration files. The keybinding files 
	 * store binding setups defined by the user in separate text files, then 
	 * appends the name of said file to the configuration file.
	 */
	public void writeBinds() {
	}
	
	/*
	 * Reads from the specified keybinding file. Adds each binding into a
	 * <String, String> HashMap.
	 */
	public void readBinds() {
	}
	
	public static int getMasterVolume() {
		return Configuration.masterVolume;
	}
	
	public static int getEffectsVolume() {
		return Configuration.effectsVolume;
	}
	
	public static int getBackgroundVolume() {
		return Configuration.backgroundVolume;
	}
	
	public static int getDifficulty() {
		return Configuration.difficulty;
	}
	
	public HashMap<String, String> getKeybindings() {
		return this.keybindings;
	}
	
	public void setMasterVolume(int volume) {
		Configuration.masterVolume = volume;
	}

	public void setEffectsVolume(int volume) {
		Configuration.effectsVolume = volume;
	}
	
	public void setBackgroundVolume(int volume) {
		Configuration.backgroundVolume = volume;
	}
	
	public void setDifficulty(int difficulty) {
		Configuration.difficulty = difficulty;
	}
	
	public void setKeybindings(String action, String key){
	}
}
