package deco2800.arcade.soundboard.actions;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import deco2800.arcade.soundboard.model.SoundFileHolder;

/**
 * Change Listener for sound buttons
 * @author Aaron Hayes
 */
public class SoundButtonChangeListener extends ChangeListener {
    private SoundFileHolder sound;

    /**
     * Basic Constructor
     * @param soundFileHolder SoundFileHolder
     */
    public SoundButtonChangeListener(SoundFileHolder soundFileHolder) {
        sound = soundFileHolder;
    }

    @Override
    public void changed(ChangeEvent changeEvent, Actor actor) {
        sound.togglePlaying();
    }
}
