package deco2800.arcade.gamelibrary;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import deco2800.arcade.client.GameClient;
import deco2800.arcade.model.Game;

/**
 * @author Aaron Hayes
 */
public class PlayButtonActionHandler extends ChangeListener {

    private LibraryScreen screen;
    private Game game;
    private boolean down = false;

    public PlayButtonActionHandler(LibraryScreen libraryScreen) {
        screen = libraryScreen;
        game = null;
    }


    /**
     * Grid View Play Buttons
     * @param libraryScreen library screen
     * @param g Game
     */
    public PlayButtonActionHandler(LibraryScreen libraryScreen, Game g) {
        screen = libraryScreen;
        game = g;
    }


    @Override
    public void changed(ChangeEvent changeEvent, Actor actor) {
        down = !down;
        if (game != null) {
            screen.setSelectedGame(game);
        }

        screen.setGameSelected();
    }
}
