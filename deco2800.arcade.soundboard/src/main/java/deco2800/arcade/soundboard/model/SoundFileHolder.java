package deco2800.arcade.soundboard.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.files.FileHandle;

/**
 * Wrapper to easily store multiple sound files
 * @author Aaron Hayes
 */
public class SoundFileHolder implements Comparable<SoundFileHolder> {

    public static final float VOLUME = 0.75f;
    private boolean loop = false;
    private FileHandle filePath;
    private Music sample;
    private String label;
    private boolean playing = false;

    /**
     * Basic constructor
     * @param file path of file containing sound sample
     */
    public SoundFileHolder(String file, String label, boolean loop) {
        filePath = new FileHandle(file);
        sample = Gdx.audio.newMusic(filePath);
        sample.setVolume(VOLUME);
        this.loop = loop;
        this.label = label;

        sample.setLooping(this.loop);
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
        sample.setLooping(this.loop);
    }

    /**
     * Set the sample's volume
     */
    public void setVolume(float volume) {
        sample.setVolume(volume);
    }

    /**
     * Play the sample
     */
    public void play() {
        sample.play();
        playing = true;
    }

    /**
     * Stop playing sample
     */
    public void stop() {
        sample.stop();
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
}
