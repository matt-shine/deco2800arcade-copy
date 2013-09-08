package deco2800.arcade.gamelibrary;

import com.badlogic.gdx.Screen;
import deco2800.arcade.client.ArcadeSystem;
import deco2800.arcade.client.GameClient;
import deco2800.arcade.client.network.NetworkClient;
import deco2800.arcade.model.Game;
import deco2800.arcade.model.Game.InternalGame;
import deco2800.arcade.model.Game.ArcadeGame;
import deco2800.arcade.model.Player;

import java.util.ArrayList;
import java.util.Collections;
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
        game.id = "gamelibrary";
        game.description = "Library for available games";
    }

    private Player player;
    private NetworkClient networkClient;

    private Screen curentScreen;
    private ArrayList<Game> gameList;

    public GameLibrary(Player player1, NetworkClient networkClient1) {
        super(player1, networkClient1);
        player = player1;
        networkClient = networkClient1;
        gameList = new ArrayList<Game>();
        //loadGameList();
    }


    @Override
    public void create() {
        ArcadeSystem.openConnection();
        updateScreen(new LibraryScreen(this));
        super.create();
    }

    /**
     * Update the current screen being display
     */
    public void updateScreen(Screen screen) {
        curentScreen = screen;
        setScreen(curentScreen);
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
     * @param forceUpdate Force the Game Library
     * @return gameSet
     */
    public ArrayList<Game> getAvailableGames(boolean forceUpdate) {
        if (forceUpdate) loadGameList();
        return getAvailableGames();
    }

    /**
     * Get lists of games available to users on the server
     * @return gameSet
     */
    public ArrayList<Game> getAvailableGames() {
        return gameList;
    }


    /**
     * Loads games on the server available to the user into the gamelibrary
     */
    private void loadGameList() {
        Set<Game> playerGames = player.getGames();
        Set<GameClient> serverGameClients = ArcadeSystem.getGameList();

        for (GameClient gameClient : serverGameClients) {
            Game game = gameClient.getGame();
            if (!playerGames.contains(game)) playerGames.remove(game);
        }

        orderGameSet(playerGames);
    }

    /**
     * Order a set of Games
     * @param set Set of Games
     */
    private void orderGameSet(Set<Game> set) {
        ArrayList<Game> arrayList = new ArrayList<Game>(set);
        Collections.sort(arrayList);
        gameList = arrayList;
    }

    public Player getPlayer() {
        return player;
    }
}
