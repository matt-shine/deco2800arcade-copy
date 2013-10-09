package deco2800.arcade.gamelibrary;

import deco2800.arcade.model.Player;
import org.junit.Assert;
import org.junit.Test;

/**
 * JUnit tests for Game Library
 * @author Aaron Hayes
 */
public class TestLibrary {

    /**
     * Test that a Game Library can be created.
     */
    @Test
    public void testBasicConstructor() {
        GameLibrary gameLibrary = new GameLibrary(null, null);
    }

    /**
     * Test Get Player
     */
    @Test
    public void testGetPlyer() {
        Player player = new Player(1, "Test Player", null);
        GameLibrary gameLibrary = new GameLibrary(player, null);
        Assert.assertEquals(player, gameLibrary.getPlayer());
    }

    /**
     * Test Empty Games List
     *  - List should be empty with null network connection
     */
    @Test
    public void testGetGames() {
        GameLibrary gameLibrary = new GameLibrary(null, null);
        Assert.assertEquals(0, gameLibrary.getAvailableGames().size());
    }
}
