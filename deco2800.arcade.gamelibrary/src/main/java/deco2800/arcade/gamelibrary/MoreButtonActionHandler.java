package deco2800.arcade.gamelibrary;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

/**
 * Action handler for more buttons
 * @author Aaron Hayes
 */
public class MoreButtonActionHandler extends ChangeListener {

    private LibraryScreen screen;
    private boolean down = false;
	private TextButton button;

    /**
     * Constructor
     * @param libraryScreen The Library Screen
     * @param b The Button
     */
    public MoreButtonActionHandler(LibraryScreen libraryScreen, TextButton b) {
        screen = libraryScreen;
		button = b;
    }


    @Override
    public void changed(ChangeEvent changeEvent, Actor actor) {
        down = !down;
		if(screen.getCurrentButton() != null) {
			screen.getCurrentButton().setChecked(false);
		}
		screen.setCurrentButton(button);

        screen.showMore();
    }
}
