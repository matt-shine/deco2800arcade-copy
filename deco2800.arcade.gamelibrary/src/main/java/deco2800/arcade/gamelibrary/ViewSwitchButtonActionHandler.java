package deco2800.arcade.gamelibrary;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

/**
 * View Switch Button Action Handler
 * @author Aaron Hayes
 */
public class ViewSwitchButtonActionHandler extends ChangeListener {

    private LibraryScreen screen;
    private boolean down = false;
    private int style;

    /**
     * Constructor
     * @param libraryScreen The Library Screen
     * @param style The Style (Either List or Grid View)
     */
    public ViewSwitchButtonActionHandler(LibraryScreen libraryScreen, int style) {
        screen = libraryScreen;
        this.style = style;
    }


    @Override
    public void changed(ChangeEvent changeEvent, Actor actor) {
        down = !down;

        screen.getPlayer().updateLibraryLayout(style);
        screen.changeView();
    }
}
