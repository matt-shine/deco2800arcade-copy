package java.deco2800.arcade.library;

import com.badlogic.gdx.Screen;
import deco2800.arcade.client.ArcadeSystem;
import deco2800.arcade.client.GameClient;
import deco2800.arcade.client.network.NetworkClient;
import deco2800.arcade.model.Game;
import deco2800.arcade.model.Game.InternalGame;
import deco2800.arcade.model.Game.ArcadeGame;
import deco2800.arcade.model.Player;

import java.util.HashSet;
import java.util.Set;


/**
 * Game Library Class
 * @author Aaron Hayes
 */
@InternalGame
@ArcadeGame(id="gamelibrary")
public class GameLibrary extends GameClient {

    private static final Game game;
    static {
        game = new Game();
        game.name = "Game Library";
        game.description = "Library for available games";
    }

    private Player player;
    private NetworkClient networkClient;

    private Screen curentScreen;
    private Set<Game> gameSet;

    public GameLibrary(Player player1, NetworkClient networkClient1) {
        super(player1, networkClient1);
        player = player1;
        networkClient = networkClient1;
        gameSet = new HashSet<Game>();
        loadGameList();
    }


    @Override
    public void create() {
        ArcadeSystem.openConnection();
        curentScreen = new LibraryScreen();
        setScreen(curentScreen);
        super.create();
    }



    /**
     * Get Game
     * @return GameLibrary game
     */
    public Game getGame() {
        return game;
    }

    /**
     * Get lists of games available to users on the server
     * @return gameSet
     */
    public Set<Game> getAvailableGames() {
        return gameSet;
    }

    /**
     * Loads games on the server available to the user into the library
     */
    private void loadGameList() {
        Set<Game> playerGames = player.getGames();
        Set<GameClient> serverGameClients = ArcadeSystem.getGameList();

        gameSet.clear();

        for (GameClient gameClient : serverGameClients) {
            Game game = gameClient.getGame();
            if (playerGames.contains(game)) gameSet.add(game);
        }

    }


}
