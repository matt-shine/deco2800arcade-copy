package deco2800.arcade.arcadeui.store;

import deco2800.arcade.model.Game;
import deco2800.arcade.model.Player;

/**
 * Interface for the Store Screen.
 * @author Addison Gourluck
 */
public interface StoreScreen {

    /**
     * Disable background and create pop-up for transactions.
     */
    public void popup();

    /**
     * Get currently logged in player.
     * @return Player currentPlayer
     */
    public Player getPlayer();

    /**
     * Set selected game, which will be the current focus of the store.
     * This will be different from screen to screen.
     * HOME: Displayed in the "featured" bar. Set by search or clicking on grid.
     * GAME: The featured is the game currently being looked at.
     * BUY: N/A
     * PROFILE: N/A
     * @param String game
     */
    public void setSelected(String game);
    
    /**
     * Get selected game, which is the current focus of the store.
     * @return Game
     */
    public Game getSelected();

    /**
     * Adds 'amount' tokens to players account, for the game 'game'.
     * @return boolean success
     * @param int amount
     * @param Game game
     */
    public boolean buyTokens(int amount, Game game);
}