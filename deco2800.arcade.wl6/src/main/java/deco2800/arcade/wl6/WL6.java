package deco2800.arcade.wl6;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;

import deco2800.arcade.model.Game;
import deco2800.arcade.model.Game.ArcadeGame;
import deco2800.arcade.model.Player;
import deco2800.arcade.client.GameClient;
import deco2800.arcade.client.highscores.HighscoreClient;
import deco2800.arcade.client.network.NetworkClient;
import deco2800.arcade.client.AchievementClient;


@ArcadeGame(id="Wolfenstein 3D")
public class WL6 extends GameClient {

    private static Game GAME = null;
    @SuppressWarnings("unused")
    private NetworkClient networkClient;
    @SuppressWarnings("unused")
    private AchievementClient achievementClient;
    private HighscoreClient highscoreClient = null;
    
    private MainGameScreen gameScreen;
    private MenuScreen menuScreen;
    private SplashScreen splashScreen;

    public static int MAP_DIM = 64;

    public WL6(Player player, NetworkClient networkClient) {
        super(player, networkClient);
        this.networkClient = networkClient;
        this.achievementClient = new AchievementClient(networkClient);
        //highscoreClient = new HighscoreClient(player.getUsername(), "Wolfenstein", networkClient);
    }

    /**
     * Creates the game
     */
    @Override
    public void create() {

        //add the overlay listeners
        this.getOverlay().setListeners(new Screen() {

            @Override
            public void render(float arg0) {
            }

            @Override
            public void resize(int width, int height) {
            }

            @Override
            public void show() {
                gameScreen.setOverlayPause(true);
                Gdx.input.setCursorCatched(false);
            }

            @Override
            public void hide() {
                gameScreen.setOverlayPause(false);
            }

            @Override
            public void pause() {
            }

            @Override
            public void resume() {
            }

            @Override
            public void dispose() {
            }

        });

        gameScreen = new MainGameScreen(this);
        menuScreen = new MenuScreen(this);
        splashScreen = new SplashScreen(this);
        this.setScreen(splashScreen);

        super.create();

    }

    @Override
    public void dispose() {
        Gdx.input.setCursorCatched(false);
        super.dispose();
    }

    @Override
    public void pause() {
        super.pause();
        Gdx.input.setCursorCatched(false);
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void resize(int w, int h) {
        super.resize(w, h);
    }

    @Override
    public void resume() {
        super.resume();
    }

    public void toggleDebugMode() {
        gameScreen.toggleDebugMode();
    }



    static {
        GAME = new Game();
        GAME.id = "Wolfenstein 3D";
        GAME.name = "Wolfenstein Time";
        GAME.description = "Fight through a maze of classic wolfenstein levels against the iceking and his minions.";
    }

    public Game getGame() {
        return GAME;
    }

    
    public void goToMenu() {
    	this.setScreen(menuScreen);
    }
    
    
    public void goToGame() {
    	this.setScreen(gameScreen);
    }

    
    
    

}
