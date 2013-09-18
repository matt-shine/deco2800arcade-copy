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
	private Point[] terrainCoords;
	private float coord0X;
	private float coord0Y;
	
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
		initialPositionY = 700;
		initialPositionX = 50;
		landerX = 50;
		landerY = 40;		
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
		
		landerPadLeftX = 100;
		landerPadLeftY = 100;
		landerPadRightX = 150;
		landerPadRightY = 100;
		
		/*//terrain generation, not working at the moment
		terrainCoords[0] = new Point(500,500);
		terrainCoords[1] = new Point(200,200);
		terrainCoords[2] = new Point(300,300);
		terrainCoords[3] = new Point(400,200);
		terrainCoords[4] = new Point(500,100);
		
		coord0X = (float) terrainCoords[0].getX();
		coord0Y = (float) terrainCoords[0].getY();
		System.out.println("----------------");
		System.out.println(coord0X);
		System.out.println(coord0Y);
		
		shapeRenderer.line(coord0X, coord0Y, coord0X, coord0Y);*/
	
		// sets initial values to be displayed  
		score = 0;
		fuel = 1000;
		speed = 0;
		time = 0;
		
        //add the overlay listeners - not sure if we really need this!
        this.getOverlay().setListeners(new Screen() {

			@Override
			public void dispose() {
			}

			@Override
			public void hide() {
				//TODO: unpause pong
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
				//TODO: unpause pong
			}
        });
        
        
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
	    shapeRenderer.line(landerPadLeftX, landerPadLeftY, landerPadRightX, landerPadRightY);
	 	    
	    //End drawing of shapes
	    shapeRenderer.end();
	    
	    //Slightly more complicated gravity function, hopefully this will increase logarithmically
	    //When the lander is at or lower than 20 pixels, it stops, otherwise it calculates the next position.
	    /*if(!(initPosition + finalY <= 20) && moving == true){
	    	
	    case INPROGRESS: //Point is underway, ball is moving
	    	
	    case GAMEOVER: //The game has been won, wait to exit
	    	if (Gdx.input.isTouched()) {
	    		gameOver();
	    		ArcadeSystem.goToGame(ArcadeSystem.UI);
	    	}
	    	break;
	    }*/
	    
	    //Simple gravity function, hopefully this will increase logarithmically
	    // I think the speed will only increase if the player presses a key that
	    // will make the lander speed up, so I've just commented this out until we
	    // work out how to do that! 
	    //speed = speed + 1;
	    
	    //very basic downwards movement of the lander
	    if(!((initialPositionY - 0.25) < 20)) {
	    	initialPositionY -= 0.25;
	    	System.out.println(initialPositionY);
	    	System.out.println(landerPadLeftY);
	    }
	    
	/*    	velocityY = velocityY - gravity;
	    	speed = speed + acceleration;
		    velocityX = (int) (speed * scaleX);
		    velocityY = (int) (speed * scaleY);
		    finalX = (int) (finalX + velocityX);
		    finalY = (int) (finalY + velocityY);
		    moving = false;
		    
	    }else {
	    	//"moving" is only used here to slow the lander down, otherwise the infinite loop is too fast.
	    	moving = true;
	    }*/
	    
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
