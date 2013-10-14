package deco2800.arcade.gamelibrary;

import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import deco2800.arcade.model.Game;
import deco2800.arcade.model.Player;

/**
 * GDX Screen class for the game Library
 * @author Aaron Hayes
 */
public interface LibraryScreen {

    /**
     * Switch between List and Grid Views
     */
    public void changeView();

    /**
     * Get Player
     * @return Player Arcade Player
     */
    public Player getPlayer();

    /**
     * Update Currently Selected Game
     * @param game Game
     */
    public void setSelectedGame(Game game);

    /**
     * Set Game Selected
     */
    public void setGameSelected();

    /**
     * Set the currently selected button
     * @param textButton TextButton
     */
    public void setCurrentButton(TextButton textButton);

    /**
     * Fetch the button currently selected by user
     * @return Button currently selected by user
     */
    public Button getCurrentButton();

    /**
     * Show more games if screen is full
     */
    public void showMore();

    public void setDescriptonText(String text);
}
