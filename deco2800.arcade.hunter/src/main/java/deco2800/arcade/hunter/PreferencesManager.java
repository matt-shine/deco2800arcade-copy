package deco2800.arcade.hunter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class PreferencesManager {

	private static final String PREF_VOLUME = "volume";
    private static final String PREF_MUSIC_ENABLED = "music.enabled";
    private static final String PREF_SOUND_ENABLED = "sound.enabled";
    
	public PreferencesManager(){}
	
	public Preferences getPreferences(){
		return Gdx.app.getPreferences("hunter");
	}
	
	public boolean isSoundEnabled()
    {
        return getPreferences().getBoolean( PREF_SOUND_ENABLED, true );
    }

    public void setSoundEnabled(
        boolean soundEffectsEnabled )
    {
        getPreferences().putBoolean( PREF_SOUND_ENABLED, soundEffectsEnabled );
        getPreferences().flush();
    }

    public boolean isMusicEnabled()
    {
        return getPreferences().getBoolean( PREF_MUSIC_ENABLED, true );
    }

    public void setMusicEnabled(
        boolean musicEnabled )
    {
        getPreferences().putBoolean( PREF_MUSIC_ENABLED, musicEnabled );
        getPreferences().flush();
    }

    public float getVolume()
    {
        return getPreferences().getFloat( PREF_VOLUME, 0.5f );
    }

    public void setVolume(
        float volume )
    {
        getPreferences().putFloat( PREF_VOLUME, volume );
        getPreferences().flush();
    }
}
