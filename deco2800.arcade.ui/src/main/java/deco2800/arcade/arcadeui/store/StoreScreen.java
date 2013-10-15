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
     * Set selected game, displayed in the "featured" bar.
     * @param Game game
     */
    public void setSelected(String game);
    
    /**
     * Get selected game, displayed in the "featured" bar.
     * @return Game game
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