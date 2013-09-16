package deco2800.arcade.lunarlander;

import java.awt.Point;
import java.util.*;
import java.lang.*;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
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
network.NetworkClient;

@ArcadeGame(id="LunarLander")
public class LunarLander extends GameClient {
	
	private OrthographicCamera camera;
	//private GameState gameState;
	public static final int SCREENHEIGHT = 800;
	public static final int SCREENWIDTH = 1200; 
	
	private ShapeRenderer shapeRenderer;
	private SpriteBatch batch;
	private Texture backgroundTexture;
	private BitmapFont font;
	
	//physics
	private Texture landerTexture;
	private float landerX;
	private float landerY;
	private float acceleration;
	private float initialPositionX;
	private float initialPositionY;
	private Texture lander;
	private int initPosition;
	private double angle;
	private double speed;
	private double scaleX;
	private double scaleY;
	private double velocityX;
	private double velocityY;
	private int acceleration;
	private int finalX;
	private int finalY;
	private int gravity;
	private boolean moving;
	
	//terrain generation
	private Point[] terrainCoords;
	private float coord0X;
	private float coord0Y;
	
	//info
	private int score;
	private int fuel;
	//private int speed;
	private int time;
	private float pixelsPerSecond = 40.0f;
	private float downwardSpeed = 30.0f;
	
	//topwatch stopwatch;
	
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
				
		batch = new SpriteBatch();		
		backgroundTexture = new Texture(Gdx.files.internal("lunarlanderassets/stars.png"));
		landerTexture = new Texture(Gdx.files.internal("lunarlanderassets/lander.png"));
		font = new BitmapFont();
		shapeRenderer = new ShapeRenderer();

		camera = new OrthographicCamera();
		camera.setToOrtho(false, SCREENWIDTH, SCREENHEIGHT);
		
		//physics
		initialPositionY = 600;
		initialPositionX = 400;
		acceleration = 0;
		initPosition= 600;
		angle = 5;
		speed = 0;
		acceleration = 2;
		scaleX = Math.cos(angle);
		scaleY = Math.sin(angle);
		velocityX = (speed*scaleX);
		velocityY = (speed*scaleY);
		gravity = 3;
		moving = true;
		
		//terrain generation, not working at the moment
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
		
		shapeRenderer.line(coord0X, coord0Y, coord0X, coord0Y);
	
		
		//info
		//physics
		score = 0;
		fuel = 1000;
		//speed = 0;
		time = 0;
		
		landerX = 60;
		landerY = 50;
		// = new Stopwatch();
		
		
		
        //add the overlay listeners
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
/*
	@Override
	public void dispose() {
		super.dispose();
	}
	@Override
	public void pause() {
		super.pause();
	}

	*/
	/**
	 * Render the current state of the game and process updates
	 */
	@Override
	public void render() {
				
		if(Gdx.input.isKeyPressed(Keys.A)) { //working
			//System.out.println("D pressed");
			initialPositionX -= Gdx.graphics.getDeltaTime() * pixelsPerSecond;
		}
		
	    if(Gdx.input.isKeyPressed(Keys.D)) {
	    	initialPositionX += Gdx.graphics.getDeltaTime() * pixelsPerSecond;
	    }
	    
		if(Gdx.input.isKeyPressed(Keys.W)) {
			initialPositionY += Gdx.graphics.getDeltaTime() * pixelsPerSecond;
		}
		
		if(Gdx.input.isKeyPressed(Keys.S)) {
			initialPositionY -= Gdx.graphics.getDeltaTime() * pixelsPerSecond;
		}
		
		// clear screen, create background using texture
	    Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
	    batch.begin();
	    batch.draw(backgroundTexture, 0, 0, 1200, 800);
	    batch.draw(landerTexture, initialPositionX, initialPositionY, landerX, landerY);
	    
	    batch.draw(lander, (initPosition + finalX), (initPosition + finalY), 50, 50);	    
	    
	    batch.draw(lander, (initPosition + finalX), (initPosition + finalY), 50, 50);	    
	    
	    font.setColor(Color.WHITE);
	    font.draw(batch, "Score: " + Integer.toString(score), SCREENWIDTH - 200, SCREENHEIGHT - 40);
	    font.draw(batch, "Remaining fuel: " + Integer.toString(fuel), SCREENWIDTH - 200, SCREENHEIGHT - 60);
	    //font.draw(batch, "Current speed: " + Integer.toString(speed), SCREENWIDTH - 200, SCREENHEIGHT - 80);
	    font.draw(batch, "Time spent: " + Integer.toString(time), SCREENWIDTH - 200, SCREENHEIGHT - 100);
	    
	    batch.end();

	    camera.update();
	    
	    shapeRenderer.setProjectionMatrix(camera.combined);
	    batch.setProjectionMatrix(camera.combined);
	    
	    //Begin drawing of shapes
	    shapeRenderer.begin(ShapeType.Line);
	    
	    // this draws a line - it needs to happen after you call the shapeRenderer.begin method
	    // and ends with the shapeRenderer.end
	    //shapeRenderer.line(SCREENWIDTH/2, SCREENHEIGHT/2, 50, 50);
	 	    
	    //End drawing of shapes
	    shapeRenderer.begin(ShapeType.FilledRectangle);
	    shapeRenderer.end();
	    
	    //Slightly more complicated gravity function, hopefully this will increase logarithmically
	    //When the lander is at or lower than 20 pixels, it stops, otherwise it calculates the next position.
	    if(!(initPosition + finalY <= 20) && moving == true){
	    	
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
	    
	    //if(!(initialPositionY - acceleration < 0) && initPositionY != 0){
	    /*while(initialPositionY > 0) {
	    	initialPositionY = initialPositionY + 1;
	    }*/
	    	velocityY = velocityY - gravity;
	    	speed = speed + acceleration;
		    velocityX = (int) (speed * scaleX);
		    velocityY = (int) (speed * scaleY);
		    finalX = (int) (finalX + velocityX);
		    finalY = (int) (finalY + velocityY);
		    moving = false;
		    
	    }else {
	    	//"moving" is only used here to slow the lander down, otherwise the infinite loop is too fast.
	    	moving = true;
	    }
	    
		super.render();
		
	}


	/**
	 * Create an update object to send to the server notifying of a score change or game outcome
	 * @return The Game Status Update.
	 *//*
	private GameStatusUpdate createScoreUpdate() {
		GameStatusUpdate update = new GameStatusUpdate();
		update.gameId = game.id;
		update.username = players[0];
		//TODO Should also send the score!
		return update;
	}

*/
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
