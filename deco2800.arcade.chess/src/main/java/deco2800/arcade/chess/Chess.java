package deco2800.arcade.chess;

import java.util.*;

import deco2800.arcade.client.GameClient;
import deco2800.arcade.client.network.NetworkClient;
import deco2800.arcade.model.Player;
import deco2800.arcade.model.Game.ArcadeGame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;

import deco2800.arcade.model.Game;
import deco2800.arcade.model.Game.ArcadeGame;
import deco2800.arcade.model.Player;
import deco2800.arcade.protocol.game.GameStatusUpdate;
import deco2800.arcade.client.GameClient;
import deco2800.arcade.client.network.NetworkClient;


@ArcadeGame(id="chess")
public class Chess extends GameClient {
	boolean players_move;
	boolean playing;
	private String[] players = new String[2]; // The names of the players: the local player is always players[0]
	
	private OrthographicCamera camera;
	
	public static final int SCREENHEIGHT = 480;
	public static final int SCREENWIDTH = 800;
	
	private ShapeRenderer shapeRenderer;
	private SpriteBatch batch;
	private BitmapFont font;

	private String statusMessage;
	
	private Texture chessBoard;
	
	//Network client for communicating with the server.
	//Should games reuse the client of the arcade somehow? Probably!
	private NetworkClient networkClient;
	
	private Texture texture;

	/**
	 * Initialises a new game 
	 */
	public Chess(Player player, NetworkClient networkClient) {
		super(player, networkClient);
		Board board = new Board();
		this.networkClient = networkClient; //this is a bit of a hack
		players[0] = player.getUsername();
		players[1] = "Player 2"; //TODO eventually the server may send back the opponent's actual username
	}
	
	@Override
	public void create() {
		super.create();
		//Initialise camera
		camera = new OrthographicCamera();
		camera.setToOrtho(false, SCREENWIDTH, SCREENHEIGHT);
		
		//Necessary for rendering
		shapeRenderer = new ShapeRenderer();
		font = new BitmapFont();
		font.setScale(2);
		batch = new SpriteBatch();
		
		Texture.setEnforcePotImages(false);
		
		
		/////Can't find this files directory to load/////
		// load the images for the droplet and the bucket, 512x512 pixels each
		//texture = new Texture(Gdx.files.classpath("srcPictures/chessboard.png"));
		chessBoard = new Texture(Gdx.files.classpath("imgs/chessboard.png"));
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
		
		
		//White background
		Gdx.gl.glClearColor(255, 255, 255, 1);
	    Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

	    // tell the camera to update its matrices.
	    camera.update();
	    
	    shapeRenderer.setProjectionMatrix(camera.combined);
	    batch.setProjectionMatrix(camera.combined);
	    
	    batch.begin();
	    batch.draw(chessBoard, 0, 0);
	    batch.end();
	    
	    super.render();
	    
	}
	
	@Override
	public void resize(int arg0, int arg1) {
		super.resize(arg0, arg1);
	}

	@Override
	public void resume() {
		super.resume();
	}
	
	
	
	/**
	 * Ends the game
	 */
	private void finishGame() {
		
	}
	
	/**
	 * Ends the game declaring the forfeiting player the loser
	 * 
	 * @param team
	 * 		The team that is forfeiting the match
	 * 				- False means white
	 * 				- True means black
	 */
	private void forfeit(boolean team) {
		
	}

	@Override
	public deco2800.arcade.model.Game getGame() {
		// TODO Auto-generated method stub
		return null;
	}
}
