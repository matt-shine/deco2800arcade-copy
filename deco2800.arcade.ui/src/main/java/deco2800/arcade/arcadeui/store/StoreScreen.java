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
    void popup();

    /**
     * Get currently logged in player.
     * @return Player
     */
    Player getPlayer();

    /**
     * Set selected game, which will be the current focus of the store.
     * This will be different from screen to screen.
     * HOME: Displayed in the "featured" bar. Set by search or clicking on grid.
     * GAME: The featured is the game currently being looked at.
     * BUY: N/A
     * PROFILE: N/A
     * @param String game
     */
    void setSelected(String game);
    
    /**
     * Get selected game, which is the current focus of the store.
     * @return Game
     */
    Game getSelected();

    /**
     * Adds 'amount' tokens to players account.
     * @return boolean
     * @param int amount
     */
    boolean buyTokens(int amount);
    
    /**
     * Buys 'game' on players account.
     * @return boolean
     * @param Game game
     */
    boolean buyGame(Game game);
    
    /**
     * Adds/removes 'game' to current players wishlist. Returns true upon
     * adding, and false upon removing.
     * @return boolean
     * @param Game game
     */
    boolean addWishlist(Game game);
}