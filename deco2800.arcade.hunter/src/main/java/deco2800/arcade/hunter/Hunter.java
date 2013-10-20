package deco2800.arcade.hunter;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.math.Vector2;
import deco2800.arcade.client.highscores.HighscoreClient;
import deco2800.arcade.client.network.NetworkClient;
import deco2800.arcade.hunter.screens.SplashScreen;
import deco2800.arcade.model.Game;
import deco2800.arcade.model.Game.ArcadeGame;
import deco2800.arcade.model.Player;

import java.util.Random;

@ArcadeGame(id = "hunter")
public class Hunter extends PlatformerGame {
    private static PreferencesManager prefManage;

    private final HighscoreClient highscore;


    public static final class Config {
        public static final int TILE_SIZE = 64;
        public static final int PANE_SIZE = 16;
        public static final int PANE_SIZE_PX = TILE_SIZE * PANE_SIZE;
        public static final int MAX_SPEED = 1024;
        public static final int SPEED_INCREASE_COUNTDOWN_START = 3;
        public static final int PANES_PER_TYPE = 4; //Number of map panes each map type should be used for

        public static final float CHANCE_OF_CLOUDS = 0.05f;
        public static final float CHANCE_OF_TREES = 0.021f;
        public static final float CLOUD_MIN_SPEED = 0.3f;
        public static final float CLOUD_MAX_SPEED = 0.7f;
        public static final float TREE_MIN_SPEED = 0.4f;
        public static final float TREE_MAX_SPEED = 0.8f;

        public static final int PLAYER_ATTACK_TIMEOUT = 300; // Attack timeout in msec
        public static final int PLAYER_ATTACK_COOLDOWN = 600;
        public static final long PLAYER_BLINK_TIMEOUT = 1000;

        public static final int SCREEN_WIDTH = 1280;
        public static final int SCREEN_HEIGHT = 720;
    }

    public static class State {
        private static boolean paused = false;
        private static int gameSpeed = 512;
        private static final float gravity = 2 * 9.81f;
        private static Random randomGenerator;
        /**
         * Velocity of the player
         */

        public static Vector2 playerVelocity = new Vector2(1, 0);

        public static PreferencesManager getPreferencesManager() {
            return prefManage;
        }

        public static boolean isPaused() {
            return paused;
        }

        public static int getGameSpeed() {
            return gameSpeed;
        }

        public static void setGameSpeed(int s) {
            gameSpeed = s;
        }

        public static float getGravity() {
            return gravity;
        }

        public static Random getRandomGenerator() {
            return randomGenerator;
        }

        public static void setRandomGenerator(Random r) {
            randomGenerator = r;
        }

        public static Vector2 getPlayerVelocity() {
            return playerVelocity;
        }

        public static void setPlayerVelocity(Vector2 v) {
            playerVelocity = v;
        }
    }

    private static final Game GAME;

    static {
        GAME = new Game();
        GAME.id = "hunter";
        GAME.name = "Hunter Game";
        GAME.description = "A 2D platformer running game where you hunt animals before they eat you!";
    }

    @Override
    public Game getGame() {
        return GAME;
    }

    public Hunter(Player player, NetworkClient networkClient) {
        super(player, networkClient);
        prefManage = new PreferencesManager();
		highscore = new HighscoreClient(player.getUsername(),"Hunter",networkClient);
        State.paused = false;
    }

    /**
     * Returns the preferences manager of the game which stores all the player configurable option
     * settings
     *
     * @return PreferencesManager
     */
    public PreferencesManager getPreferencesManager() {
        return prefManage;
    }

    @Override
    public void create() {
        this.getOverlay().setListeners(new Screen() {
            @Override
            public void hide() {
                // Unpause your game here
                State.paused = false;
            }

            @Override
            public void show() {
                // Pause your game here
                State.paused = true;
            }

            @Override
            public void pause() {
            }

            @Override
            public void render(float arg0) {
            }

            @Override
            public void resize(int arg0, int arg1) {
            }

            @Override
            public void resume() {
            }

            @Override
            public void dispose() {
            }
        });
        setScreen(new SplashScreen(this));
    }

    public HighscoreClient getHighscore() {
        return highscore;
    }
}
