package deco2800.arcade.gamelibrary;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import deco2800.arcade.model.Game;

/**
 * GridView Hover Action Handler
 * @author David McInnes
 */
public class GridViewHoverActionHandler extends ClickListener {

    private LibraryScreen screen;
    private Game game;
    private boolean down = false;

    public GridViewHoverActionHandler(LibraryScreen libraryScreen) {
        screen = libraryScreen;
        game = null;
    }


    /**
     * Grid View Play Buttons
     * @param libraryScreen library screen
     * @param g Game
     */
    public GridViewHoverActionHandler(LibraryScreen libraryScreen, Game g) {
        screen = libraryScreen;
        game = g;
    }

    /**
     * Grid View Play Buttons
     * @param libraryScreen library screen
     * @param gameID ArcadeID of the game
     */
    public GridViewHoverActionHandler(LibraryScreen libraryScreen, String gameID) {
        screen = libraryScreen;
        Game newGame = new Game();
        newGame.id = gameID;
        game = newGame;
    }

    @Override
    public void enter(InputEvent event, float x, float y, int pointer, Actor actor) {
        screen.setDescriptonText(game.getDescription());
    }

    @Override
    public void exit(InputEvent event, float x, float y, int pointer, Actor actor) {
        screen.setDescriptonText("");
    }
}