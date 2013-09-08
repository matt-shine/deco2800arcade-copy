package deco2800.arcade.gamelibrary;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

/**
 * @author Aaron Hayes
 */
public class ViewSwitchButtonActionHandler extends ChangeListener {

    private LibraryScreen screen;
    private boolean down = false;
    private int style;

    public ViewSwitchButtonActionHandler(LibraryScreen libraryScreen, int style) {
        screen = libraryScreen;
        this.style = style;
    }


    @Override
    public void changed(ChangeEvent changeEvent, Actor actor) {
        down = !down;

        screen.getPlayer().getLibraryStyle().setLayout(style);
        screen.changeView();
    }
}
