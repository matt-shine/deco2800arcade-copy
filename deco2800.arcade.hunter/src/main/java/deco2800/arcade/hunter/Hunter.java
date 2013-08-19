package deco2800.arcade.hunter;

import java.util.HashSet;
import java.util.Set;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;

import deco2800.arcade.model.Achievement;
import deco2800.arcade.model.Game;
import deco2800.arcade.model.Game.ArcadeGame;
import deco2800.arcade.model.Player;
import deco2800.arcade.protocol.achievement.AddAchievementRequest;
import deco2800.arcade.protocol.game.GameStatusUpdate;
import deco2800.arcade.client.GameClient;
import deco2800.arcade.client.network.NetworkClient;
/**
 * A Hunter game for use in the Arcade
 * @author jsteel8, Nessex
 *
 */
@ArcadeGame(id="hunter")
public class Hunter extends GameClient {
	
	private OrthographicCamera camera;
	
	private String username = new String(); // The names of the players: the local player is always players[0]

	private HunterGame game;

	private enum States = {
		SPLASH_SCREEN, //also loading screen
		MENU,
		GAME_RUNNING,
		GAME_PAUSED,
		GAME_ENDED
	};

	private int state = 0;
	
	public static final int SCREENHEIGHT = 480;
	public static final int SCREENWIDTH = 800;

	//Reusable list of achievements
	private static Set<Achievement> achievements = new HashSet<Achievement>();
	static {
		Achievement huntTwenty = new Achievement("Hunt down 20 animals in one game."); //Example achievement
		achievements.add(huntTwenty);
		Achievement surviveOneMin = new Achievement("Survive for one minute.");
		achievements.add(surviveOneMin);
	}
	
	//Network client for communicating with the server.
	//Should games reuse the client of the arcade somehow? Probably!
	private NetworkClient networkClient;

	/**
	 * Basic constructor for the Hunter game
	 * @param userName The name of the player
	 * @param client The network client for sending/receiving messages to/from the server
	 */
	public Hunter(Player player, NetworkClient networkClient) {
		super(player, networkClient);
		username = player.getUsername();
		this.networkClient = networkClient; //this is a bit of a hack
	}
	
	/**
	 * Creates the game
	 */
	@Override
	public void create() {
		super.create();
		
		//Initialise camera
		camera = new OrthographicCamera();
		camera.setToOrtho(false, SCREENWIDTH, SCREENHEIGHT);

		startGame();
	}

	@Override
	public void dispose() {
		super.dispose();
	}

	@Override
	public void pause() {
		super.pause();
	}

	/**
	 * Render the current state of the game and process updates
	 */
	@Override
	public void render() {
		//Black background
		Gdx.gl.glClearColor(0, 0, 0, 1);
	    Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

	    camera.update();
	    
	    switch (state) {
	    	case States.GAME_RUNNING:
	    		game.render();
	    }

		super.render();	
	}
	
	/**
	 * Create an update object to send to the server notifying of a score change or game outcome
	 * @return
	 */
	private GameStatusUpdate createScoreUpdate() {
		GameStatusUpdate update = new GameStatusUpdate();
		update.gameId = game.gameId;
		update.username = username;
		//TODO Should also send the score!
		return update;
	}

	@Override
	public void resize(int arg0, int arg1) {
		super.resize(arg0, arg1);
	}

	@Override
	public void resume() {
		super.resume();
	}

	private void startGame() {
		game = new HunterGame();
		state = States.GAME_RUNNING;
	}
	
	private static final Game game;
	static {
		game = new Game();
		game.gameId = "Hunter";
		game.name = "Hunter";
		game.availableAchievements = achievements;
	}
	
	public Game getGame() {
		return game;
	}
	
}
