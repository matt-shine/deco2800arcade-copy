package deco2800.arcade.gamelibrary;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import deco2800.arcade.model.Game;

/**
 * @author Aaron Hayes
 */
public class GameButtonActionHandler extends ChangeListener {

    private LibraryScreen screen;
    private boolean down = false;
    private Game game;
	private TextButton button;
	
    public GameButtonActionHandler(LibraryScreen libraryScreen, Game g, TextButton b) {
        screen = libraryScreen;
        game = g;
		button = b;
    }


    @Override
    public void changed(ChangeEvent changeEvent, Actor actor) {
        down = !down;
		if(screen.getCurrentButton() != null) {
			screen.getCurrentButton().setChecked(false);
		}
		screen.setCurrentButton(button);
        screen.setSelectedGame(game);
    }
}
