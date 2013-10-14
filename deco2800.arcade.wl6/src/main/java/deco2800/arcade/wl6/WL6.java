package deco2800.arcade.wl6;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;

import deco2800.arcade.model.Game;
import deco2800.arcade.model.Game.ArcadeGame;
import deco2800.arcade.model.Player;
import deco2800.arcade.client.GameClient;
import deco2800.arcade.client.network.NetworkClient;
import deco2800.arcade.client.AchievementClient;
import deco2800.arcade.wl6.screen.MainGameScreen;
import deco2800.arcade.wl6.screen.MenuScreen;
import deco2800.arcade.wl6.screen.SplashScreen;


@ArcadeGame(id="Wolfenstein 3D")
public class WL6 extends GameClient {

    private static Game GAME = null;
    @SuppressWarnings("unused")
    private NetworkClient networkClient;
    @SuppressWarnings("unused")
    private AchievementClient achievementClient;

    private Screen currentScreen;
    private MainGameScreen gameScreen;
    @SuppressWarnings("unused")
    private MenuScreen menuScreen;
    @SuppressWarnings("unused")
    private SplashScreen splashScreen;

    public static int MAP_DIM = 64;

    public WL6(Player player, NetworkClient networkClient) {
        super(player, networkClient);
        this.networkClient = networkClient;
        this.achievementClient = new AchievementClient(networkClient);
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
        currentScreen = gameScreen;
        this.setScreen(currentScreen);

        super.create();

    }

    @Override
    public void dispose() {
        Gdx.input.setCursorCatched(false);
        currentScreen.dispose();
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
        GAME.name = "Wolfenstein 3D";
        GAME.description = "Killin' Natzis";
    }

    public Game getGame() {
        return GAME;
    }



}
