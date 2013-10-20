package deco2800.arcade.soundboard;

import deco2800.arcade.soundboard.model.SoundFileHolder;
import org.junit.Assert;
import org.junit.Test;

/**
 * JUnit Tests for Sound File Holder
 * @author Aaron Hayes
 */
public class TestSoundFileHolder {
    /**
     * Test that a Sound File Holder can be made
     */
    @Test
    public void testConstructor() {
        SoundFileHolder soundFileHolder = new SoundFileHolder("SoundboardAssets/loops/creepy_loop.wav", "Creepy Test" , true);
        SoundFileHolder soundFileHolder2 = new SoundFileHolder("SoundboardAssets/loops/creepy_loop.wav", "Creepy Test" , false);
        SoundFileHolder soundFileHolder3 = new SoundFileHolder("SoundboardAssets/loops/creepy_loop.wav", null , false);
        SoundFileHolder soundFileHolder4 = new SoundFileHolder(null, null , false);
    }

    /**
     * Test the label is being stored correctly
     */
    @Test
    public void testLabel() {
        SoundFileHolder soundFileHolder = new SoundFileHolder("SoundboardAssets/loops/creepy_loop.wav", "Creepy Test" , true);
        Assert.assertEquals("Creepy Test", soundFileHolder.getLabel());
        SoundFileHolder soundFileHolder2 = new SoundFileHolder("SoundboardAssets/loops/creepy_loop.wav", null, true);
        Assert.assertEquals(null, soundFileHolder2.getLabel());
        SoundFileHolder soundFileHolder3 = new SoundFileHolder("SoundboardAssets/loops/creepy_loop.wav", "", true);
        Assert.assertEquals("", soundFileHolder3.getLabel());
    }

    /**
     * Test the looping is being stored correctly
     */
    @Test
    public void testLooping() {
        SoundFileHolder soundFileHolder = new SoundFileHolder("SoundboardAssets/loops/creepy_loop.wav", "Creepy Test" , true);
        Assert.assertEquals(true, soundFileHolder.isLoop());
        SoundFileHolder soundFileHolder2 = new SoundFileHolder("SoundboardAssets/loops/creepy_loop.wav", "Creepy Test" , false);
        Assert.assertEquals(false, soundFileHolder2.isLoop());
    }

    /**
     * Test if sounds are toggled playing
     */
    @Test
    public void testToggling() {
        SoundFileHolder soundFileHolderBasic = new SoundFileHolder("SoundboardAssets/loops/creepy_loop.wav", "Creepy Test" , true);
        Assert.assertEquals(false, soundFileHolderBasic.isPlaying());

        SoundFileHolder soundFileHolder = new SoundFileHolder("SoundboardAssets/loops/creepy_loop.wav", "Creepy Test" , true);
        soundFileHolder.togglePlaying();
        Assert.assertEquals(true, soundFileHolder.isPlaying());

        SoundFileHolder soundFileHolder2 = new SoundFileHolder("SoundboardAssets/loops/creepy_loop.wav", "Creepy Test" , true);
        soundFileHolder2.togglePlaying();
        soundFileHolder2.togglePlaying();
        Assert.assertEquals(false, soundFileHolder2.isPlaying());

        SoundFileHolder soundFileHolder3 = new SoundFileHolder("SoundboardAssets/loops/creepy_loop.wav", "Creepy Test" , true);
        soundFileHolder3.togglePlaying();
        soundFileHolder3.togglePlaying();
        soundFileHolder3.togglePlaying();
        Assert.assertEquals(true, soundFileHolder3.isPlaying());

        SoundFileHolder soundFileHolder4 = new SoundFileHolder("SoundboardAssets/loops/creepy_loop.wav", "Creepy Test" , true);
        soundFileHolder4.togglePlaying();
        soundFileHolder4.togglePlaying();
        soundFileHolder4.togglePlaying();
        soundFileHolder4.togglePlaying();
        Assert.assertEquals(false, soundFileHolder4.isPlaying());

        SoundFileHolder soundFileHolder5 = new SoundFileHolder("SoundboardAssets/loops/creepy_loop.wav", "Creepy Test" , false);
        Assert.assertEquals(false, soundFileHolder5.isPlaying());

        SoundFileHolder soundFileHolder6 = new SoundFileHolder("SoundboardAssets/loops/creepy_loop.wav", "Creepy Test" , false);
        soundFileHolder6.togglePlaying();
        Assert.assertEquals(false, soundFileHolder6.isPlaying());

        SoundFileHolder soundFileHolder7 = new SoundFileHolder("SoundboardAssets/loops/creepy_loop.wav", "Creepy Test" , false);
        soundFileHolder7.togglePlaying();
        soundFileHolder7.togglePlaying();
        Assert.assertEquals(false, soundFileHolder7.isPlaying());
    }

}
