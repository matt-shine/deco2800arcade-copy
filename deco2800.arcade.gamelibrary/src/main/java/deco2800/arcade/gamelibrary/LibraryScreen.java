package deco2800.arcade.gamelibrary;

import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import deco2800.arcade.client.GameClient;
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
     * @param gameClient Game
     */
    public void setSelectedGame(final GameClient gameClient);

    /**
     * Set Game Selected
     */
    public void setGameSelected();

    public void setCurrentButton(TextButton textButton);

    public Button getCurrentButton();
}
