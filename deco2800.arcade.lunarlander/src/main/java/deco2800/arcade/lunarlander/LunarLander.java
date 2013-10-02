package deco2800.arcade.lunarlander;

import java.util.*;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

import deco2800.arcade.model.Game;
import deco2800.arcade.model.Game.ArcadeGame;
import deco2800.arcade.model.Player;
import deco2800.arcade.client.GameClient;
import deco2800.arcade.client.network.NetworkClient;

@ArcadeGame(id="LunarLander")
public class LunarLander extends GameClient {
		
	private OrthographicCamera camera;
	public static final int SCREENHEIGHT = 800;
	public static final int SCREENWIDTH = 1200;
	
	private SpriteBatch batch;
	private ShapeRenderer shapeRenderer;
	private Texture landerTexture;
	private Texture backgroundTexture;
	private Texture moon;
	private TextureRegion backgroundTextureRegion; // draws portion of background png file
	private BitmapFont font; // draws text 
	//Texture[] backgroundTextures;
	
	// lander constants
	private float landerWidth; // the width of the lander png
	private float landerHeight; // the height of the lander png
	private float landerX; // the initial position of the lander - x coordinate
	private float landerY; // the initial postiion of the lander - y coordinate
	private float landerXnew;
	private float landerYnew;
	private float velY;
	private float velX;
	
	
	//terrain generation
	private List<List<Integer>> terrain;
	private boolean randomMap; //currently used in LunarLanderTerrain file, will be reimplemented upon creation of menu.
	private boolean gameOver;
	
	Texture textureSolid;

	
	// statistics to be displayed on game screen
	private int score;
	private int fuel;
	private int speed;
	private int time;
	
	// speed of movement when lander is moved left or right 
	private float sideSpeed = 40.0f;
	// speed of movement when lander is moved downwards
	private float downwardSpeed = 80.0f;
	// speed of movement when lander is moving downwards of its own accord
	private float downwardDrift = 20.0f;
	
	private NetworkClient networkClient;
	
	/**
	 * Basic constructor for Lunar Lander 
	 * @param player The name of the player
	 * @param networkClient The network client for sending/receiving messages to/from the server
	 */
	
	public LunarLander(Player player, NetworkClient networkClient) {
		super(player, networkClient);
        this.networkClient = networkClient;  
	}
	
	
	/**
	 * Creates the game
	 */
	@Override
	public void create() {
			
		// setting up various Gdx tools
		batch = new SpriteBatch();
		backgroundTexture = new Texture(Gdx.files.internal("lunarlanderassets/rose_nebula.png")); //loads background file
		backgroundTextureRegion = new TextureRegion(backgroundTexture, 1200, 800); //creates a portion of that to display
		moon = new Texture(Gdx.files.internal("lunarlanderassets/moon.png"));
		landerTexture = new Texture(Gdx.files.internal("lunarlanderassets/lander.png")); // loads lander png file
		font = new BitmapFont(); // creates a new font to use for text
		shapeRenderer = new ShapeRenderer();

		camera = new OrthographicCamera();
		camera.setToOrtho(false, SCREENWIDTH, SCREENHEIGHT);
		
		// setting up various constants 
		landerX = 600;
		landerY = 750;
		landerWidth = 30;
		landerHeight = 30;
		velY = 1;
		velX = 0;
		speed = 1;
		fuel = 1000;
		
		randomMap = true;
		gameOver = false;
		
		//creates the map using the method in LunarLanderTerrain file
		terrain = LunarLanderTerrain.createMap();
	}

	/**
	 * Render the current state of the game and process updates
	 */
	@Override
	public void render() {
		
		//downwards movement of lander
		if(!(gameOver == true)){
			velY += 0.005;
			landerY -= (0.05 + velY);
		}
		
		//sideways velocity
		if(velX > 0){
			velX = velX / 2;
		}
		
		
		// move lander right
		if(!(gameOver == true)){
			
			// move lander left
			if ((Gdx.input.isKeyPressed(Keys.A)) || (Gdx.input.isKeyPressed(Keys.LEFT))) {
				velX -= 0.02;
				//landerX -= Gdx.graphics.getDeltaTime() * sideSpeed;
			}
			
			if ((Gdx.input.isKeyPressed(Keys.D)) || (Gdx.input.isKeyPressed(Keys.RIGHT))) {
				velX += 0.02;
		    	//landerX += Gdx.graphics.getDeltaTime() * sideSpeed;
		    }
			
		    // boost the lander's speed
			if ((Gdx.input.isKeyPressed(Keys.W)) || (Gdx.input.isKeyPressed(Keys.UP))) {
				while(fuel > 0){
					velY -= 0.01;
					fuel -= 1;
				}
			}			
			
		}
		
		//for debugging purposes, can be used when game is over or not
		if (Gdx.input.isKeyPressed(Keys.Q)) {
			System.out.println("debug button has been pressed!");
		}
		
		// clear screen, create background using texture
	    Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
	    batch.begin();
	    batch.draw(backgroundTextureRegion, 0, 0);
	    batch.draw(landerTexture, landerX, landerY, landerWidth, landerHeight);    
	    
	    // display values on screen
	    font.setColor(Color.WHITE);
	    font.draw(batch, "Score: " + Integer.toString(score), SCREENWIDTH - 200, SCREENHEIGHT - 40);
	    font.draw(batch, "Remaining fuel: " + Integer.toString(fuel), SCREENWIDTH - 200, SCREENHEIGHT - 60);
	    font.draw(batch, "Current speed: " + Integer.toString(speed), SCREENWIDTH - 200, SCREENHEIGHT - 80);
	    font.draw(batch, "Time spent: " + Integer.toString(time), SCREENWIDTH - 200, SCREENHEIGHT - 100);
	    
	    batch.end();

	    camera.update();
	    
	    shapeRenderer.setProjectionMatrix(camera.combined);
	    batch.setProjectionMatrix(camera.combined);
	    
	    //renders the map using the list of lists "terrain"
	    LunarLanderTerrain.renderMap(terrain);
	    
	    //check for collision
	    for (int i = 1; i < terrain.size(); i++){
	    	if(landerX > terrain.get(i).get(0) && landerX < terrain.get(i).get(2)){
	    		if(landerY < terrain.get(i).get(1)){
	    			System.out.println("Collided with the ground! Fatal!");
	    			landerTexture = new Texture(Gdx.files.internal("lunarlanderassets/landerExplode1.png"));
	    			gameOver = true;
	    			landerY += 0.25;
	    			velY = 0;
	    		}
	    	}else if(landerX > terrain.get(0).get(0) && landerX < terrain.get(0).get(2)){
	    		if(landerY - 5 < terrain.get(0).get(1)){
	    			System.out.println("You win!");
	    			gameOver = true;
	    			landerY += 0.25;
	    			velY = 0;
	    		}
	    	}
	    }
	    
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

	
	private static final Game game;
	static {
		game = new Game();
		game.id = "lunarlander";
		game.name = "LunarLander";
        game.description = "Can your Lunar Lander make it to the surface safely?";
	}
	
	public Game getGame() {
		return game;
	}
	
}
