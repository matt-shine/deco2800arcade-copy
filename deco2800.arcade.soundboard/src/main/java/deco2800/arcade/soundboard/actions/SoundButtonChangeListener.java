package deco2800.arcade.soundboard.actions;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import deco2800.arcade.client.replay.ReplayHandler;
import deco2800.arcade.client.replay.ReplayNodeFactory;
import deco2800.arcade.soundboard.SoundboardScreen;
import deco2800.arcade.soundboard.model.SoundFileHolder;

/**
 * Change Listener for sound buttons
 * @author Aaron Hayes
 */
public class SoundButtonChangeListener extends ChangeListener {
    private SoundFileHolder sound;
    private ReplayHandler replayHandler;
    private int index;
    private SoundboardScreen soundboardScreen;

    /**
     * Basic Constructor
     * @param soundFileHolder SoundFileHolder
     * @param replayHandler ReplayHandler
     * @param index Array Location of sound
     * @param soundboardScreen SoundBoardScreen the button is located on
     */
    public SoundButtonChangeListener(SoundFileHolder soundFileHolder, ReplayHandler replayHandler, int index, SoundboardScreen soundboardScreen) {
        sound = soundFileHolder;
        this.replayHandler = replayHandler;
        this.index = index;
        this.soundboardScreen = soundboardScreen;
    }

    @Override
    public void changed(ChangeEvent changeEvent, Actor actor) {
        sound.togglePlaying();

        if (soundboardScreen.isRecording()) {
            replayHandler.pushEvent(
                    ReplayNodeFactory.createReplayNode(
                    "sound_pushed",
                    sound.isLoop() ? SoundboardScreen.LOOPS : SoundboardScreen.SAMPLES, index
            ));
        }
    }
}
