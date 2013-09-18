package deco2800.arcade.lunarlander;

import java.awt.Point;
import java.util.*;
import java.lang.*;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.Input.Keys;

import deco2800.arcade.model.Game;
import deco2800.arcade.model.Game.ArcadeGame;
import deco2800.arcade.model.Player;
import deco2800.arcade.protocol.game.GameStatusUpdate;
import deco2800.arcade.client.ArcadeSystem;
import deco2800.arcade.client.GameClient;
import deco2800.arcade.client.network.NetworkClient;


@ArcadeGame(id="LunarLander")
public class LunarLander extends GameClient {
		
	private OrthographicCamera camera;
	public static final int SCREENHEIGHT = 800;
	public static final int SCREENWIDTH = 1200; 
	
	// Gdx tools
	private ShapeRenderer shapeRenderer; // draws shapes
	private SpriteBatch batch; 
	private Texture landerTexture; // draws lander png file
	private Texture backgroundTexture; // draws background png file
	private TextureRegion backgroundTextureRegion; // draws portion of background png file
	private BitmapFont font; // draws text 
	//Texture[] backgroundTextures;
	
	// lander constants
	private float landerX; // the width of the lander png
	private float landerY; // the height of the lander png
	private float initialPositionX; // the initial position of the lander - x coordinate
	private float initialPositionY; // the initial postiion of the lander - y coordinate
	
	private float acceleration;
	private double angle;
	//private double speed;
	private double scaleX;
	private double scaleY;
	private double velocityX;
	private double velocityY;
	private int finalX;
	private int finalY;
	private int gravity;
	private boolean moving;
	
	//terrain generation
	private List<List<Integer>> terrain;
	
	// coordinates of the lander pad
	private float landerPadLeftX;
	private float landerPadLeftY;
	private float landerPadRightX;
	private float landerPadRightY;

	
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
		// loads background png file
		backgroundTexture = new Texture(Gdx.files.internal("lunarlanderassets/rose_nebula.png"));
		// creates a portion of that background to display
		backgroundTextureRegion = new TextureRegion(backgroundTexture, 1200, 800);
		// loads lander png file
		landerTexture = new Texture(Gdx.files.internal("lunarlanderassets/lander.png"));
		// creates a new font to use for text
		font = new BitmapFont();
		shapeRenderer = new ShapeRenderer();

		camera = new OrthographicCamera();
		camera.setToOrtho(false, SCREENWIDTH, SCREENHEIGHT);
		
		// setting up various constants 
		initialPositionY = 750;
		initialPositionX = 600;
		landerX = 30;
		landerY = 30;		
		acceleration = 0;
		angle = 5;
		speed = 1;
		acceleration = 2;
		scaleX = Math.cos(angle);
		scaleY = Math.sin(angle);
		velocityX = (speed*scaleX);
		velocityY = (speed*scaleY);
		gravity = 3;
		moving = true;
		
		landerPadLeftX = 25;
		landerPadLeftY = 50;
		landerPadRightX = 100;
		landerPadRightY = 100;
		terrain = createMap();
	}

	/**
	 * Render the current state of the game and process updates
	 */
	@Override
	public void render() {
			
		// move lander left
		if ((Gdx.input.isKeyPressed(Keys.A)) || (Gdx.input.isKeyPressed(Keys.LEFT))) { 
			initialPositionX -= Gdx.graphics.getDeltaTime() * sideSpeed;
		}
		
		// move lander right
	    if ((Gdx.input.isKeyPressed(Keys.D)) || (Gdx.input.isKeyPressed(Keys.RIGHT))) {
	    	initialPositionX += Gdx.graphics.getDeltaTime() * sideSpeed;
	    }
		
	    // boost the lander's speed
		if ((Gdx.input.isKeyPressed(Keys.W)) || (Gdx.input.isKeyPressed(Keys.UP))) {
			initialPositionY -= Gdx.graphics.getDeltaTime() * downwardSpeed;
		}
		
		// for development purposes
		if (Gdx.input.isKeyPressed(Keys.Q)){
			createMap();
		}
		
		// clear screen, create background using texture
	    Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
	    batch.begin();
	    batch.draw(backgroundTextureRegion, 0, 0);
	    batch.draw(landerTexture, initialPositionX, initialPositionY, landerX, landerY);
	    	    
	    //batch.draw(landerTexture, (initPosition + finalX), (initPosition + finalY), 50, 50);	    
	    
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
	    
	    //Begin drawing of shapes
	    shapeRenderer.begin(ShapeType.Line);
	    Gdx.gl.glLineWidth(5);
	    // this draws a line - it needs to happen after you call the shapeRenderer.begin method
	    // and ends with the shapeRenderer.end
	    //shapeRenderer.line(landerPadLeftX, landerPadLeftY, landerPadRightX, landerPadRightY);
	    
	    shapeRenderer.line(terrain.get(0).get(0), terrain.get(0).get(1), terrain.get(0).get(2), terrain.get(0).get(3));
	    shapeRenderer.line(terrain.get(1).get(0), terrain.get(1).get(1), terrain.get(1).get(2), terrain.get(1).get(3));
	    
	    //End drawing of shapes
	    shapeRenderer.end();
	    
	    /*if(!(initPosition + finalY <= 20) && moving == true){
	    	
	    case INPROGRESS: //Point is underway, ball is moving
	    	
	    case GAMEOVER: //The game has been won, wait to exit
	    	if (Gdx.input.isTouched()) {
	    		gameOver();
	    		ArcadeSystem.goToGame(ArcadeSystem.UI);
	    	}
	    	break;
	    }*/
	    
	    //very basic downwards movement of the lander
	    if(!((initialPositionY - 0.25) < 20)) {
	    	initialPositionY -= 0.25;
	    }
	    
	/*    	velocityY = velocityY - gravity;
	    	speed = speed + acceleration;
		    velocityX = (int) (speed * scaleX);
		    velocityY = (int) (speed * scaleY);
		    finalX = (int) (finalX + velocityX);
		    finalY = (int) (finalY + velocityY);
		    moving = false;
	*/
	    
		super.render();
		
	}
	
	public List<List<Integer>> createMap(){
		
		List<List<Integer>> terrain = new ArrayList<List<Integer>>();
		
		//makes a random X coordinate for landing pad
		Random randomX = new Random();  
		int startX = 100;     // beginning of range  
		int endX = 700;      // end of range 
		
		//makes a random Y coordinate for landing pad, this code will be smaller soon...
		Random randomY = new Random();  
		int startY = 50;     // beginning of range  
		int endY = 200;      // end of range
		
		int landingPadY = startY + randomY.nextInt( endY - startY + 1 );
		int landingPadX1 = startX + randomX.nextInt( endX - startX + 1 );
		int landingPadX2 = landingPadX1 + 50;
		
		//creation of landingPad, it will be the first element in the array
		//TO DO, change color of landing pad
		terrain.add(new ArrayList<Integer>());
		terrain.get(0).add(landingPadX1);
		terrain.get(0).add(landingPadY);
		terrain.get(0).add(landingPadX2);
		terrain.get(0).add(landingPadY);
		
		//creating the X and Y for the next line
		int pointAX = (25 + new Random().nextInt( 50 - 25 + 1 )) + terrain.get(0).get(2);
		int pointAY = 100 + new Random().nextInt( 400 - 100 + 1 );
		
		//adds the line to the right of landing pad, using the right side of the landing pad as the initial X and Y
		terrain.add(new ArrayList<Integer>());
		terrain.get(1).add(terrain.get(0).get(2));
		terrain.get(1).add(terrain.get(0).get(3));
		terrain.get(1).add(pointAX);
		terrain.get(1).add(pointAY);
		
		return terrain;
	}
	
	//creates a random length of a line, used by createMap()
	public int randomLength(){
		int randomLength = 10 + new Random().nextInt( 50 - 10 + 1 );
		return randomLength;
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
