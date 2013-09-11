package deco2800.arcade.gamelibrary;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import deco2800.arcade.client.GameClient;

/**
 * @author Aaron Hayes
 */
public class PlayButtonActionHandler extends ChangeListener {

    private LibraryScreen screen;
    private GameClient gameClient;
    private boolean down = false;

    public PlayButtonActionHandler(LibraryScreen libraryScreen) {
        screen = libraryScreen;
        gameClient = null;
    }


    /**
     * Grid View Play Buttons
     * @param libraryScreen library screen
     * @param game Game
     */
    public PlayButtonActionHandler(LibraryScreen libraryScreen, GameClient game) {
        screen = libraryScreen;
        gameClient = game;
    }


    @Override
    public void changed(ChangeEvent changeEvent, Actor actor) {
        down = !down;
        if (gameClient != null) {
            screen.setSelectedGame(gameClient);
        }

        screen.setGameSelected();
    }
}
