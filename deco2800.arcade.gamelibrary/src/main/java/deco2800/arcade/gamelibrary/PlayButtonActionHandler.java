package deco2800.arcade.gamelibrary;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import deco2800.arcade.client.GameClient;

/**
 * @author Aaron Hayes
 */
public class PlayButtonActionHandler extends ChangeListener {

    private LibraryScreen screen;
    private GameClient game;
    private boolean down = false;

    public PlayButtonActionHandler(LibraryScreen libraryScreen) {
        screen = libraryScreen;
        game = null;
    }


    /**
     * Grid View Play Buttons
     * @param libraryScreen library screen
     * @param gameClient Game
     */
    public PlayButtonActionHandler(LibraryScreen libraryScreen, GameClient gameClient) {
        screen = libraryScreen;
        game = gameClient;
    }


    @Override
    public void changed(ChangeEvent changeEvent, Actor actor) {
        down = !down;

        if (game != null) {
            screen.updateGame(game);
        }

        screen.setGameSelected();
    }
}
