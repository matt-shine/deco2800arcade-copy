package deco2800.arcade.breakout;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;

import deco2800.arcade.model.Game;
import deco2800.arcade.model.Game.ArcadeGame;
import deco2800.arcade.model.Player;
import deco2800.arcade.breakout.screens.*;
import deco2800.arcade.client.AchievementClient;
import deco2800.arcade.client.ArcadeSystem;
import deco2800.arcade.client.GameClient;
import deco2800.arcade.client.network.NetworkClient;
import deco2800.arcade.client.highscores.HighscoreClient;
import deco2800.arcade.accolades.AccoladeSystem;

/**
 * The game client
 * @author Carlie Smits and Naveen Kumar
 *
 */
@ArcadeGame(id = "Breakout")
public class Breakout extends GameClient {

		SplashScreen splashScreen;
		GameScreen gamescreen;
		MenuScreen MenuScreen;
		LevelScreen1 LevelScreen1;
		LevelScreen2 LevelScreen2;
		HelpScreen1 helpscreen1;
		RankingScreen RankingScreen;
		//ModelScreen modelscreen;
		HelpScreen2 helpscreen2;



	/*
	 * Creates private instance variables for each element of The
	 */
	public String player;
	private NetworkClient networkClient;
	private AchievementClient achievementClient;
	HighscoreClient highscoreUser;
	private AccoladeSystem accolades;
	
	// Screen Parameters
	public static final int SCREENHEIGHT = 720;
	public static final int SCREENWIDTH = 1280;
	



	public Breakout(Player player, NetworkClient networkClient) {
		super(player, networkClient);
		this.player = player.getUsername();
		this.networkClient = networkClient;
		this.achievementClient = new AchievementClient(networkClient);
		this.highscoreUser = new HighscoreClient(player.getUsername(), "Breakout", networkClient);
		this.accolades = new AccoladeSystem();
	}

	/**
	 * change to the screen to the splashscreen.
	 */
	@Override
	public void create() {
		super.create();
		
        //add the overlay listeners
        this.getOverlay().setListeners(new Screen() {

			@Override
			public void dispose() {
			}

			@Override
			public void hide() {
				resume();
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
			public void show() {
				pause();
			}
			
        });
		splashScreen = new SplashScreen(this);
		MenuScreen=new MenuScreen(this);
		gamescreen = new GameScreen(this);
		LevelScreen1=new LevelScreen1(this);
		LevelScreen2=new LevelScreen2(this);
		helpscreen1=new HelpScreen1(this);
		helpscreen2=new HelpScreen2(this);		
		modelscreen=new ModelScreen(this);
		RankingScreen=new RankingScreen(this);
		//modelscreen=new ModelScreen(this);

		setScreen(splashScreen);
		HighscoreClient player1 = new HighscoreClient(player, "Breakout", networkClient);
	}

	@Override
	public void dispose() {
		
		splashScreen.dispose();
		gamescreen.dispose();
		LevelScreen1.dispose();
		MenuScreen.dispose();
		helpscreen1.dispose();
		
		super.dispose();
	}

	/**
	 * Pauses Application
	 */
	@Override
	public void pause() {
		super.pause();
	}

	/**
	 * Resumes Application from a Paused State
	 */
	@Override
	public void resume() {
		super.resume();
	}

	/**
	 * Renders game mechanics.
	 */
	public void render() {
		 super.render();

	}

	@Override
	public Game getGame() {
		return game;
	}

	public String playerName() {
		return player;
	}

	/**
	 * Provides details about the game to the Arcade system.
	 */
	private static final Game game;
	static {
		game = new Game();
		game.id = "breakout";
		game.name = "Breakout";
		game.description = "Bounce the ball off your paddle to keep it from falling off the bottom of the screen.";
	}
	
	public HighscoreClient getHighScoreClient() {
		return this.highscoreUser;
	}
	
}
