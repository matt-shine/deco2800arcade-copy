package deco2800.arcade.gamelibrary;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

/**
 * @author Aaron Hayes
 */
public class PlayButtonActionHandler extends ChangeListener {

    private LibraryScreen screen;
    private boolean down = false;

    public PlayButtonActionHandler(LibraryScreen libraryScreen) {
        screen = libraryScreen;
    }


    @Override
    public void changed(ChangeEvent changeEvent, Actor actor) {
        down = !down;
        screen.setGameSeletcted();
    }
}
