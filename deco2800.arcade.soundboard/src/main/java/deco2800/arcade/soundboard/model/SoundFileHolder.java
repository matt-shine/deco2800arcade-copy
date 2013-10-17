package deco2800.arcade.soundboard.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;


/**
 * Wrapper to easily store multiple sound files
 * @author Aaron Hayes
 */
public class SoundFileHolder implements Comparable<SoundFileHolder> {

    public static final float DEFAULT_VOLUME = 0.35f;
    public static final float SAMPLE_VOLUME = 1.0f;
    public static final float MAX_VOLUME = 1.0f;

    private boolean loop = false;
    private Music sample;
    private String label;
    private boolean playing = false;

    /**
     * Basic constructor
     * @param file path of file containing sound sample
     */
    public SoundFileHolder(String file, String label, boolean loop) {
        this.loop = loop;
        this.label = label;

        try {
            sample = Gdx.audio.newMusic(Gdx.files.classpath(file));
            sample.setVolume(DEFAULT_VOLUME);
            sample.setLooping(this.loop);
        } catch (Exception e) {
            // Gdx could not open audio file
        }
    }

    /**
     * Check loop status of sound
     * @return loop
     */
    public boolean isLoop() {
        return loop;
    }

    /**
     * Set loop status
     * @param loop boolean
     */
    public void setLoop(boolean loop) {
        this.loop = loop;
        if (sample != null) {
            sample.setLooping(this.loop);
        }
    }

    /**
     * Set the sample's volume
     */
    public void setVolume(float volume) {
        if (sample != null) {
            if (volume > MAX_VOLUME) {
                sample.setVolume(MAX_VOLUME);
            } else {
                sample.setVolume(volume);
            }
        }
    }

    /**
     * Play the sample
     */
    public void play() {
        if (sample != null) {
            sample.play();
        }
        if (isLoop()) {
            playing = true;
        }
    }

    /**
     * Stop playing sample
     */
    public void stop() {
        if (sample != null) {
            sample.stop();
        }
        playing = false;
    }

    /**
     * Get Label
     * @return label
     */
    public String getLabel() {
        return label;
    }

    /**
     * Toggle playing music
     */
    public void togglePlaying() {
        if (playing) {
            stop();
        } else {
            play();
        }
    }

    @Override
    public int compareTo(SoundFileHolder sfh) {
        return (label.toUpperCase()).compareTo(sfh.label.toUpperCase());
    }

    /**
     * Get playing status
     * @return playing
     */
    public boolean isPlaying() {
        return playing;
    }
}
