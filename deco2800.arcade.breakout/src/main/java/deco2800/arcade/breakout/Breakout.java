package deco2800.arcade.breakout;

import com.badlogic.gdx.Gdx;
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
import deco2800.arcade.breakout.PongBall;
import deco2800.arcade.client.ArcadeSystem;
import deco2800.arcade.client.GameClient;
import deco2800.arcade.client.network.NetworkClient;
import deco2800.arcade.client.ArcadeSystem;

/**
 * 
 * 
 * 
 * 
 */
@ArcadeGame(id = "Breakout")
public class Breakout extends GameClient {
	SplashScreen splashScreen;
	GameScreen gamescreen;
	menuscreen MenuScreen;
	levelscreen LevelScreen;
	achivementscreen AchivementScreen;
	rankingscreen RankingScreen;


	/*
	 * Creates private instance variables for each element of The
	 */
	private String player;
	
	// Screen Parameters
	public static final int SCREENHEIGHT = 720;
	public static final int SCREENWIDTH = 1280;


	public Breakout(Player player, NetworkClient networkClient) {
		super(player, networkClient);
		this.player = player.getUsername();
		
		// this.nc = networkClient;
	}

	/**
	 * change to the screen to the splashscreen.
	 */
	@Override
	public void create() {
		super.create();
		
		splashScreen = new SplashScreen(this);
		MenuScreen=new menuscreen(this);
		gamescreen = new GameScreen(this);
		LevelScreen=new levelscreen(this);
		AchivementScreen=new achivementscreen(this);
		RankingScreen=new rankingscreen(this);
		setScreen(splashScreen);
	}

	@Override
	public void dispose() {
		
		splashScreen.dispose();
		gamescreen.dispose();
		LevelScreen.dispose();
		MenuScreen.dispose();
		AchivementScreen.dispose();
		RankingScreen.dispose();
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
	
}
