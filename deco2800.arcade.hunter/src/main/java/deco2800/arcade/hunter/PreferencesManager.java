package deco2800.arcade.hunter;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class PreferencesManager {

	private static final String PREF_VOLUME = "volume";
	private static final String PREF_MUSIC_ENABLED = "music.enabled";
	private static final String PREF_SOUND_ENABLED = "sound.enabled";

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

	
}
