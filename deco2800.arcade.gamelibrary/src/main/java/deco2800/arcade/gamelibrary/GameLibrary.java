package deco2800.arcade.gamelibrary;

import com.badlogic.gdx.Screen;
import deco2800.arcade.client.ArcadeSystem;
import deco2800.arcade.client.GameClient;
import deco2800.arcade.client.UIOverlay;
import deco2800.arcade.client.network.NetworkClient;
import deco2800.arcade.model.Game;
import deco2800.arcade.model.Game.InternalGame;
import deco2800.arcade.model.Game.ArcadeGame;
import deco2800.arcade.model.LibraryStyle;
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
    private ArrayList<GameClient> gameList;

    public GameLibrary(Player player1, NetworkClient networkClient1) {
        super(player1, networkClient1);
        player = player1;
        networkClient = networkClient1;
        gameList = new ArrayList<GameClient>();
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

    @Override
    public void render() {
        super.render();
    }

    @Override
    public UIOverlay getOverlay() {
        return super.getOverlay();
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
     * @param forceUpdate Force the Game Library
     * @return gameSet
     */
    private ArrayList<GameClient> getAvailableGames(boolean forceUpdate) {
        if (forceUpdate) loadGameList();
        return getAvailableGames();
    }

    /**
     * Get lists of games available to users on the server
     * @return gameSet
     */
    public ArrayList<GameClient> getAvailableGames() {
        return gameList;
    }


    /**
     * Loads games on the server available to the user into the gamelibrary
     */
    private void loadGameList() {
        //Set<Game> playerGames = player.getGames();
        Set<GameClient> serverGameClients = ArcadeSystem.getGameClientList();

        //for (GameClient gameClient : serverGameClients) {
        //    Game game = gameClient.getGame();
        //    if (!playerGames.contains(game)) serverGameClients.remove(gameClient);
        //}

        orderGameSet(serverGameClients);
    }

    /**
     * Order a set of Games
     * @param set Set of Games
     */
    private void orderGameSet(Set<GameClient> set) {
        ArrayList<GameClient> arrayList = new ArrayList<GameClient>(set);
        //Collections.sort(arrayList);
        gameList = arrayList;
    }

    public Player getPlayer() {
        return player;
    }

    public void switchViews() {
        switch (player.getLibraryStyle().getLayout()) {
            case LibraryStyle.LIST_VIEW:
                updateScreen(new ListScreen(this));
                break;
            case LibraryStyle.GRID_VIEW:
                updateScreen(new GridScreen(this));
                break;
            default:
                updateScreen(new ListScreen(this));
                break;
        }
    }
}
