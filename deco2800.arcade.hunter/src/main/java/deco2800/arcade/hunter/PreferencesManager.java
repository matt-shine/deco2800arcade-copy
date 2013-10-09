package deco2800.arcade.hunter;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class PreferencesManager {

	private static final String PREF_VOLUME = "volume";
	private static final String PREF_MUSIC_ENABLED = "music.enabled";
	private static final String PREF_SOUND_ENABLED = "sound.enabled";
	private static final String[] HIGH_SCORE_LIST = { "HIGHSCORE1",
			"HIGHSCORE2", "HIGHSCORE3" };

	public PreferencesManager() {
	}

	public Preferences getPreferences() {
		return Gdx.app.getPreferences("hunter");
	}

	/**
	 * Checks if the sound setting is enabled
	 * 
	 * @return Boolean sound setting is enabled
	 */
	public boolean isSoundEnabled() {
		return getPreferences().getBoolean(PREF_SOUND_ENABLED, true);
	}

	/**
	 * Sets the sound setting
	 * 
	 * @param soundEffectsEnabled
	 *            Boolean whether the sound setting is enabled
	 */
	public void setSoundEnabled(boolean soundEffectsEnabled) {
		getPreferences().putBoolean(PREF_SOUND_ENABLED, soundEffectsEnabled);
		getPreferences().flush();
	}

	/**
	 * Checks if music setting is enabled
	 * 
	 * @return Boolean if music setting is enabled
	 */
	public boolean isMusicEnabled() {
		return getPreferences().getBoolean(PREF_MUSIC_ENABLED, true);
	}

	/**
	 * Sets the music setting
	 * 
	 * @param musicEnabled
	 *            Boolean whether the sound setting is enabled
	 */
	public void setMusicEnabled(boolean musicEnabled) {
		getPreferences().putBoolean(PREF_MUSIC_ENABLED, musicEnabled);
		getPreferences().flush();
	}

	/**
	 * Returns the volume setting
	 * 
	 * @return float volume control
	 */
	public float getVolume() {
		return getPreferences().getFloat(PREF_VOLUME, 0.5f);
	}

	/**
	 * Sets the volume setting
	 * 
	 * @param volume
	 *            Float of which the volume is set at
	 */
	public void setVolume(float volume) {
		getPreferences().putFloat(PREF_VOLUME, volume);
		getPreferences().flush();
	}

	/**
	 * Returns a list of highscores in the form of HashMap
	 * 
	 * @return HashMap of highscores
	 */
	public HashMap<String, Integer> getHighScore() {
		HashMap<String, Integer> highScoreList = new HashMap<String, Integer>();
		for (int x = 0; x < 3; x++) {
			highScoreList.put(HIGH_SCORE_LIST[x],
					getPreferences().getInteger(HIGH_SCORE_LIST[x], 1));
		}
		return highScoreList;
	}

	/**
	 * Adds a highscore, the highscore is checked to see whether it can be
	 * classified in the top 3
	 * 
	 * @param score
	 *            Integer of the high score to be added
	 */
	public void addHighScore(int score) {
		HashMap<String, Integer> highScoreList = getHighScore();
		if (highScoreList.get(HIGH_SCORE_LIST[0]) < score) {
			getPreferences().putInteger(HIGH_SCORE_LIST[0], score);
			getPreferences().flush();
		} else if (highScoreList.get(HIGH_SCORE_LIST[1]) < score) {
			getPreferences().putInteger(HIGH_SCORE_LIST[1], score);
			getPreferences().flush();
		} else if (highScoreList.get(HIGH_SCORE_LIST[2]) < score) {
			getPreferences().putInteger(HIGH_SCORE_LIST[2], score);
		}

	}
}
