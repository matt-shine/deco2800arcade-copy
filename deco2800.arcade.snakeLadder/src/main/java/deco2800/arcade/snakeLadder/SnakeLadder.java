package deco2800.arcade.snakeLadder;

import java.io.*;
import java.util.*;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

import deco2800.arcade.client.ArcadeSystem;
import deco2800.arcade.client.GameClient;
import deco2800.arcade.client.network.NetworkClient;
import deco2800.arcade.model.Game;
import deco2800.arcade.model.Player;
import deco2800.arcade.model.Game.ArcadeGame;
import deco2800.arcade.snakeLadderModel.*;

/**
 * This is the main class for game snake&Ladder
 * @author s4310055,s43146400,s43146884
 *
 */

@ArcadeGame(id="snakeLadder")
public class SnakeLadder extends GameClient {
	
	private OrthographicCamera camera;
	private SpriteBatch batch;
	private Texture backgroundBoard;
	private List<Tile> tileList; 
	private HashMap<Character,String> ruleTextureMapping;
    private GamePlayer gamePlayer;
    private enum GameState {
		READY,
		INPROGRESS,
		GAMEOVER
	}
	private GameState gameState;
	private String[] players = new String[2]; // The names of the players: the local player is always players[0]

	private ShapeRenderer shapeRenderer;
	private BitmapFont font;
	private String statusMessage;
	
	//Network client for communicating with the server.
	//Should games reuse the client of the arcade somehow? Probably!
	private NetworkClient networkClient;

	public SnakeLadder(Player player, NetworkClient networkClient) {
		super(player, networkClient);
		players[0] = player.getUsername();
		players[1] = "Player 2"; //TODO eventually the server may send back the opponent's actual username
        this.networkClient = networkClient; //this is a bit of a hack
	}

	/**
	 * Creates the game
	 */
	@Override
	public void create() {
		//add the overlay listeners
        this.getOverlay().setListeners(new Screen() {

			@Override
			public void dispose() {
			}

			@Override
			public void hide() {
				
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
				
			}
        });
        
		super.create();
		
		//Initialise camera
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 1280, 800);
		batch = new SpriteBatch();
		
		//loading of background game board
		backgroundBoard = new Texture(Gdx.files.classpath("assets/board.png"));
		//loading player icon

//		gamePlayer =new Texture(Gdx.files.classpath("assets/player.png"));

		
		//initialise rule texture mapping
		ruleTextureMapping = new HashMap<Character,String>();
		ruleTextureMapping.put('+',"plus_10.png");
		ruleTextureMapping.put('*', "plus_20.png");
		ruleTextureMapping.put('-', "minus_10.png");
		ruleTextureMapping.put('/', "minus_20.png");
		ruleTextureMapping.put('#', "stop.png");
		
		//loading game map
		tileList = new ArrayList<Tile>();
		loadMap(tileList,"assets/lvl1.txt");
		System.out.println("success");
		

		// create the game player
		gamePlayer = new GamePlayer();

		shapeRenderer = new ShapeRenderer();
		font = new BitmapFont();
		font.setScale(2);
		//Initialise the game state
		gameState = GameState.READY;
		statusMessage = "Click to start!";
	}
	
	@Override
	public void dispose() {
		super.dispose();
	}

	@Override
	public void pause() {
		super.pause();
	}

	@Override
	public void render() {	
		//Black background
				Gdx.gl.glClearColor(0, 0, 0, 1);
			    Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		// tell the camera to update its matrices.
		camera.update();
		// tell the SpriteBatch to render in the
		// coordinate system specified by the camera.
		shapeRenderer.setProjectionMatrix(camera.combined);
		batch.setProjectionMatrix(camera.combined);
		
		 //Begin drawing of shapes
	    shapeRenderer.begin(ShapeType.FilledRectangle);
	    //Begin batch
	    batch.begin();
		batch.draw(backgroundBoard,0,0);
		for(Tile t:tileList)
		{
			if(t.getRule()!='.')
			{
				batch.draw(t.getTexture(),t.getCoorX(),t.getCoorY());
			}
		}	    
		 //If there is a current status message (i.e. if the game is in the ready or gameover state)
	    // then show it in the middle of the screen
	    if (statusMessage != null) {
	    	font.setColor(Color.WHITE);
	    	font.draw(batch, statusMessage, 200, 300);
	    	if (gameState == GameState.GAMEOVER) {
	    		font.draw(batch, statusMessage+" Click to exit", 200, 300);
	    	}
	    }
		batch.end();
		//Render gamePlayer 
		gamePlayer.render(shapeRenderer);
		 //End drawing of shapes
	    shapeRenderer.end();

		
		 switch(gameState) {
		    
		    case READY: //Ready to start a new point
		    	if (Gdx.input.isTouched()) {
		    		startPoint();
		    	}
		    	break;
		    	
		    case INPROGRESS: 
		    	gamePlayer.move(Gdx.graphics.getDeltaTime());
		    	//If the player reaches the end of each line , move up to another line
		    	if (gamePlayer.bounds.x >= (600-20f) || gamePlayer.bounds.x <=0){
		    		gamePlayer.moveUp();
		    	}	    	
		    	//If the ball gets to the left edge then player 2 wins
		    	if (gamePlayer.bounds.x <= (60-20f) && gamePlayer.bounds.y >= (540)) {
		    		gamePlayer.reset();
		    		statusMessage = "You Win! ";
		    		gameState = GameState.GAMEOVER;
		    		//statusMessage = "Win!";
		    				//+ "Click to exit!";
		    		//endPoint(1);
		    	}
		    	break;
		    case GAMEOVER: //The game has been won, wait to exit
		    	if (Gdx.input.isTouched()) {
		    		gameOver();
		    		ArcadeSystem.goToGame(ArcadeSystem.UI);
		    	}
		    	break;
		    }
		
		super.render();
		
	}
	
	private void endPoint(int winner) {
		gamePlayer.reset();
	}
	
	private void startPoint() {
		// TODO Auto-generated method stub
		gamePlayer.initializeVelocity();
		gameState = GameState.INPROGRESS;
		statusMessage = null;
	}

	@Override
	public Game getGame() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void resize(int width, int height) {
		super.resize(width*2, height*2);
	}

	@Override
	public void resume() {
		super.resume();
	}
	
	private boolean loadMap(List<Tile> tileList, String filePath) {
		FileHandle handle = Gdx.files.classpath(filePath);
		BufferedReader file = handle.reader(2048);
		
		int counter = 0;
		try {
			String line = "";
			while((line = file.readLine()) != null)
			{
				for(int i=0;i<line.length();i++)
				{
					int index =0;
					if(counter%2==0)
					{
						index = (10-counter)*10-i;
					}
					else
					{
						index = (10-counter-1)*10+i+1;
					}
					Tile t = new TileLvl1(index,60,line.charAt(i));
					if(t.getRule()!='.')
					{
						t.setTexture(new Texture(Gdx.files.classpath("assets/"+ruleTextureMapping.get(t.getRule()))));
					}
					tileList.add(t);
				}
				counter++;
			}
			file.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}
}
