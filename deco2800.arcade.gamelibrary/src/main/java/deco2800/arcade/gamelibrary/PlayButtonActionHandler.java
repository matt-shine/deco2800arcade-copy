package deco2800.arcade.gamelibrary;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import deco2800.arcade.model.Game;

/**
 * Play Button Action Handler
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

    /**
     * Grid View Play Buttons
     * @param libraryScreen library screen
     * @param gameID ArcadeID of the game
     */
    public PlayButtonActionHandler(LibraryScreen libraryScreen, String gameID) {
        screen = libraryScreen;
        Game newGame = new Game();
        newGame.id = gameID;
        game = newGame;
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
