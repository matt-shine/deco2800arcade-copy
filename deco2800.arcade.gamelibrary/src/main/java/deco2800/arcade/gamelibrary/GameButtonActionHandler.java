package deco2800.arcade.gamelibrary;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import deco2800.arcade.client.GameClient;

/**
 * @author Aaron Hayes
 */
public class GameButtonActionHandler extends ChangeListener {

    private LibraryScreen screen;
    private boolean down = false;
    private final GameClient gameClient;

    public GameButtonActionHandler(LibraryScreen libraryScreen, final GameClient gameClient1) {
        screen = libraryScreen;
        gameClient = gameClient1;
    }


    @Override
    public void changed(ChangeEvent changeEvent, Actor actor) {
        down = !down;
        screen.setSelectedGame(gameClient);
    }
}
