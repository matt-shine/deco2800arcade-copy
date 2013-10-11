package deco2800.arcade.gamelibrary;

import com.badlogic.gdx.Screen;
import deco2800.arcade.client.ArcadeSystem;
import deco2800.arcade.client.GameClient;
import deco2800.arcade.client.UIOverlay;
import deco2800.arcade.client.network.NetworkClient;
import deco2800.arcade.client.network.listener.LibraryResponseListener;
import deco2800.arcade.model.Game;
import deco2800.arcade.model.Game.InternalGame;
import deco2800.arcade.model.Game.ArcadeGame;
import deco2800.arcade.model.LibraryStyle;
import deco2800.arcade.model.Player;
import deco2800.arcade.protocol.game.GameLibraryRequest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
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

    private LibraryResponseListener responseListener;

    private Screen curentScreen;
    private List<Game> gameList;

    /**
     * Basic Constructor for a game library
     * @param player1 Player
     * @param networkClient1 Network client
     */
    public GameLibrary(Player player1, NetworkClient networkClient1) {
        super(player1, networkClient1);
        player = player1;
        networkClient = networkClient1;
        responseListener = new LibraryResponseListener();

        if (networkClient != null) {
            networkClient.addListener(responseListener);
        }
        gameList = new ArrayList<Game>();
        loadGameList();
    }


    @Override
    public void create() {
        ArcadeSystem.openConnection();
        switchViews();
        super.create();

        /*this.getOverlay().setListeners(new Screen() {
            @Override
            public void hide() {
                //Unpause your game here
            }

            @Override
            public void show() {
                //Pause your game here
            }

            @Override
            public void pause() {}
            @Override
            public void render(float arg0) {}
            @Override
            public void resize(int arg0, int arg1) {}
            @Override
            public void resume() {}
            @Override
            public void dispose() {}
        });   */
    }

    /**
     * Update the current screen being display
     */
    public void updateScreen(Screen screen) {
        //if (curentScreen != null) curentScreen.dispose();
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
     * @return gameSet
     */
    public List<Game> getAvailableGames() {
        return gameList;
    }


    /**
     * Loads games on the server available to the user into the gamelibrary
     */
    private void loadGameList() {
        if (player == null || networkClient == null) {
            return;
        }
        //Set<Game> playerGames = player.getGames();
        Set<Game> serverGames = null;

        if (ArcadeSystem.getArcadeGames() == null) {
            ArcadeSystem.requestGames();
        } else {
            serverGames = ArcadeSystem.getArcadeGames();
        }

        if (serverGames != null) {
            orderGameSet(serverGames);
        }
    }

    /**
     * Create a server Library Request
     */
    private GameLibraryRequest createRequest() {
        return new GameLibraryRequest();
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

    /**
     * Get Current Player
     * @return player
     */
    @Override
    public Player getPlayer() {
        return player;
    }

    /**
     * Switch between list and grid views
     */
    public void switchViews() {
        // Refresh games list on swaps
        createRequest();
        loadGameList();

        switch (player.getLibraryStyle().getLayout()) {
            case LibraryStyle.LIST_VIEW:
                updateScreen(new ListScreen(this, false));
                break;
            case LibraryStyle.GRID_VIEW:
                updateScreen(new GridScreen(this));
                break;
            default:
                updateScreen(new ListScreen(this, false));
                break;
        }
    }

    /**
     * Show next page of games in list view
     */
    public void showMore() {
        updateScreen(new ListScreen(this, true));
    }

    /**
     * Show previous page of games in list view
     */
    public void showLess() {
        updateScreen(new ListScreen(this, false));
    }
}
