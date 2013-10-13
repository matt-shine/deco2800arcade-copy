package deco2800.arcade.lunarlander;

import java.util.*;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.utils.Array;


import deco2800.arcade.client.Arcade;
import deco2800.arcade.client.ArcadeSystem;
import deco2800.arcade.model.Game;
import deco2800.arcade.model.Game.ArcadeGame;
import deco2800.arcade.model.Player;
import deco2800.arcade.client.GameClient;
import deco2800.arcade.client.network.NetworkClient;

@ArcadeGame(id="LunarLander")
public class LunarLander extends GameClient {
	
	private enum GameState {
		AT_MENU, IN_GAME, GAME_PAUSED, CONTINUE_GAME, NEW_GAME, GAME_OVER_LOSE,
		GAME_OVER_WIN
	}
	// GDX tools
	private GameState gameState;
	private OrthographicCamera camera;
	public static final int SCREENHEIGHT = 800;
	public static final int SCREENWIDTH = 1200;
	private SpriteBatch batch;
	private ShapeRenderer shapeRenderer;
	private Texture landerTexture;
	private Texture explodeTexture;
	private Texture backgroundTexture1;
	private Texture backgroundTexture2;
	private Texture backgroundTexture3;
	private Texture backgroundTexture4;
	private Texture backgroundTexture5;
	private Texture surfaceTexture;
	private Texture pauseScreen;
	private Texture splashScreenBackground;
	private Texture gameOverBackground;
	private Texture gameOverWinBackground;
	private Texture moon;
	private Texture flames;
	private TextureRegion backgroundTextureRegion; 
	private BitmapFont font;  
	
	// lander constants
	private float landerWidth; // the width of the lander png
	private float landerHeight; // the height of the lander png
	private float landerX; // the initial position of the lander - x coordinate
	private float landerY; // the initial postiion of the lander - y coordinate
	private float velY;
	private float velXleft;
	private float velXright;

	//terrain generation
	private List<List<Integer>> terrain;
	private boolean randomMap; //currently used in LunarLanderTerrain file, will be reimplemented upon creation of menu
	private boolean gameOver;
	private boolean upKey;

	private boolean gamePaused;
	
	Music flameSound;
	Texture textureSolid;

	// statistics to be displayed on game screen
	private int score;
	private int fuel;
	private long speed;
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
		backgroundTexture1 = new Texture(Gdx.files.internal("lunarlanderassets/rose_nebula.png")); 
		//backgroundTexture2 = new Texture(Gdx.files.internal("lunarlanderassets/carina_nebula.png")); 
		backgroundTexture3 = new Texture(Gdx.files.internal("lunarlanderassets/deep_space.png")); 
		backgroundTexture4 = new Texture(Gdx.files.internal("lunarlanderassets/pelican_nebula.png")); 
		backgroundTexture5 = new Texture(Gdx.files.internal("lunarlanderassets/stars.png")); 
		Array<Texture> backgrounds = new Array<Texture>();
		backgrounds.add(backgroundTexture1);
		//backgrounds.add(backgroundTexture2);
		backgrounds.add(backgroundTexture3);
		backgrounds.add(backgroundTexture4);
		backgrounds.add(backgroundTexture5);
		surfaceTexture = new Texture(Gdx.files.internal("lunarlanderassets/moon_surface2.png"));
		pauseScreen = new Texture(Gdx.files.internal("lunarlanderassets/pause_screen.png"));
		splashScreenBackground = new Texture(Gdx.files.internal("lunarlanderassets/LL_splash.png"));
		gameOverBackground = new Texture(Gdx.files.internal("lunarlanderassets/game_over_screen.png"));
		gameOverWinBackground = new Texture(Gdx.files.internal("lunarlanderassets/game_over_win.png"));
		backgroundTextureRegion = new TextureRegion(backgrounds.random(), 0, 0, 1200, 800); 
		moon = new Texture(Gdx.files.internal("lunarlanderassets/moon.png"));
		flames = new Texture(Gdx.files.internal("lunarlanderassets/flames.png"));
		landerTexture = new Texture(Gdx.files.internal("lunarlanderassets/lander.png")); 
		explodeTexture = new Texture(Gdx.files.internal("lunarlanderassets/landerExplode1.png"));
		font = new BitmapFont(Gdx.files.internal("lunarlanderassets/game_font_half.fnt"),
				Gdx.files.internal("lunarlanderassets/game_font_half_0.png"), false); 
		shapeRenderer = new ShapeRenderer();
		camera = new OrthographicCamera();
		camera.setToOrtho(false, SCREENWIDTH, SCREENHEIGHT);
		flameSound = Gdx.audio.newMusic(Gdx.files.internal("lunarlanderassets/flame.ogg"));

		// setting up various constants 
		landerX = 600;
		landerY = 750;
		landerWidth = 30;
		landerHeight = 30;
		velY = 1;
		velXleft = 0;
		velXright = 0;
		speed = 1;
		fuel = 1000;
		randomMap = true;
		gameOver = false;
		upKey = false;
		gamePaused = false;

		//creates the map using the method in LunarLanderTerrain file
		terrain = LunarLanderTerrain.createMap();
		gameState = GameState.AT_MENU;
	}

	/**
	 * Render the current state of the game and process updates
	 */
	@Override
	public void render() {		
		switch (gameState) {
		case AT_MENU: 
			// draw splash screen, very simple right now
			batch.begin();
			Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
			batch.setProjectionMatrix(camera.combined);
			batch.draw(splashScreenBackground, 0, 0, 1200, 800);
			batch.end();
			camera.update();
			super.render();
			if ((Gdx.input.isKeyPressed(Keys.ENTER))) {
				gameState = GameState.IN_GAME;
			}
			if ((Gdx.input.isKeyPressed(Keys.ESCAPE))) {
				ArcadeSystem.goToGame(ArcadeSystem.UI);
			}
			break;
		case IN_GAME:	
			batch.begin();
			Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
			batch.draw(backgroundTextureRegion, 0, 0);
			batch.draw(landerTexture, landerX, landerY, landerWidth, landerHeight);
			if(upKey == true) {
				batch.draw(flames, landerX + 10, landerY - 5, 10, 10);
				upKey = false;
			}
			// display values on screen
			font.setColor(new Color(0.4f, 0.99f, 0.4f, 1f)); 
			font.draw(batch, "Score: " + Integer.toString(score), SCREENWIDTH - 300, SCREENHEIGHT - 40);
			font.draw(batch, "Remaining fuel: " + Integer.toString(fuel), SCREENWIDTH - 300, SCREENHEIGHT - 60);
			font.draw(batch, "Current speed: " + Long.toString(speed), SCREENWIDTH - 300, SCREENHEIGHT - 80);
			font.draw(batch, "Time spent: " + Integer.toString(time), SCREENWIDTH - 300, SCREENHEIGHT - 100);
			batch.end();
			camera.update();
			shapeRenderer.setProjectionMatrix(camera.combined);
			batch.setProjectionMatrix(camera.combined);			
			// pause game
			if ((Gdx.input.isKeyPressed(Keys.SPACE))) {
				gameState = GameState.GAME_PAUSED;
			}
			//calculates velocity of lander  && gamePaused == true
			if(!(gameOver == true) ) {
				velY += 0.01;
				landerY -= (0.10 + velY);
				landerX += velXright;
				landerX -= velXleft;

				if (velXleft > 0){
					velXleft -= 0.001;
				} else if(velXright > 0) {
					velXright -= 0.001;
				}	
			}	
			if(!(gameOver == true)) {

				// move lander left
				if ((Gdx.input.isKeyPressed(Keys.A)) || (Gdx.input.isKeyPressed(Keys.LEFT))) {
					landerX -= Gdx.graphics.getDeltaTime() * sideSpeed;
					velXleft += 0.01;
					fuel -= 0.025;
				}
				// move lander right
				if ((Gdx.input.isKeyPressed(Keys.D)) || (Gdx.input.isKeyPressed(Keys.RIGHT))) {
					landerX += Gdx.graphics.getDeltaTime() * sideSpeed;
					velXright += 0.01;
					fuel -= 0.025;
				}
				// boost the lander's speed
				if ((Gdx.input.isKeyPressed(Keys.W)) || (Gdx.input.isKeyPressed(Keys.UP))) {
					if(fuel > 0){
						velY -= 0.015;
						fuel -= 0.05;
						flameSound.setVolume(1);
						flameSound.play();
						upKey = true;
					}
				}
			}
			speed += velY * 1; // should be *=
			//for debugging purposes, can be used when game is over or not
			if (Gdx.input.isKeyPressed(Keys.Q)) {
				System.out.println("debug button has been pressed!");
			}
			//renders the map using the list of lists "terrain"
			LunarLanderTerrain.renderMap(terrain);
			// Amy's attempts to fill the map with texture. Drawing triangle is hard!
			/*batch.begin();
			for (int i = 1; i < terrain.size(); i++){
			   	if (terrain.get(i).get(1) > terrain.get(i).get(3)){
			   		batch.draw(surfaceTexture, terrain.get(i).get(0), 0, terrain.get(i).get(2) - terrain.get(i).get(0), terrain.get(i).get(3));
			   	} else if (terrain.get(i).get(1) < terrain.get(i).get(3)){
			   		batch.draw(surfaceTexture, terrain.get(i).get(0), 0, terrain.get(i).get(2) - terrain.get(i).get(0), terrain.get(i).get(1));
			   	}
			}
			batch.draw(surfaceTexture, terrain.get(0).get(0), 0, terrain.get(0).get(2), terrain.get(0).get(3));
			for (int i = 1; i < terrain.size(); i++){
			   	if (terrain.get(i).get(1) > terrain.get(i).get(3)){			    	
					batch.draw(surfaceTexture, terrain.get(i).get(0), terrain.get(i).get(1), terrain.get(i).get(2), terrain.get(i).get(3), terrain.get(i).get(0), terrain.get(i).get(3));		
		    	}else if (terrain.get(i).get(1) < terrain.get(i).get(3)){
					batch.draw(surfaceTexture, terrain.get(i).get(0), terrain.get(i).get(1), terrain.get(i).get(2), terrain.get(i).get(3), terrain.get(i).get(2), terrain.get(i).get(1));		
		    	}
		    }
		 batch.end();*/
		 //check for collision
		 	for (int i = 1; i < terrain.size(); i++){
				if (isPointBelowLine(landerX, landerY, terrain.get(i).get(0),
						terrain.get(i).get(1), terrain.get(i).get(2), terrain.get(i).get(3)) ||
						isPointBelowLine(landerX + landerWidth, landerY, terrain.get(i).get(0),
								terrain.get(i).get(1), terrain.get(i).get(2), terrain.get(i).get(3))) {
					landerTexture.dispose();
					batch.begin();
					batch.draw(explodeTexture, landerX, landerY, landerWidth, landerHeight);
					batch.end();
					System.out.println("Collided with the ground!");
					gameOver = true;
					gameState = GameState.GAME_OVER_LOSE;
				} else if(landerX > terrain.get(0).get(0) && landerX < terrain.get(0).get(2)){
					if(landerY - 5 < terrain.get(0).get(1)){
						System.out.println("You win!");
						//score += 10; not incrementing properly
						gameOver = true;
						velY = 0;
						gameState = GameState.GAME_OVER_WIN;
					}
				}
			}
			super.render();
			break;			
		case GAME_PAUSED:
			gamePaused = true;
			// show overlay with options to quit or resume
			batch.begin();
			//Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
			//batch.setProjectionMatrix(camera.combined);
			batch.draw(pauseScreen, 100, 100);
			batch.end();
			if ((Gdx.input.isKeyPressed(Keys.SPACE))) {
				pauseScreen.dispose();
				gamePaused = false;
				gameState = GameState.IN_GAME;
			}
			break;
		case CONTINUE_GAME:
			break;
		case NEW_GAME: 
			//gameState = GameState.IN_GAME;
			break;
		case GAME_OVER_LOSE:
			// game over screen
			batch.begin();
			Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
			batch.setProjectionMatrix(camera.combined);
			batch.draw(gameOverBackground, 0, 0, 1200, 800);
			font.draw(batch, "Final score: " + Integer.toString(score), SCREENWIDTH - 700, SCREENHEIGHT - 340);
			batch.end();
			camera.update();
			super.render();
			if ((Gdx.input.isKeyPressed(Keys.ENTER))) {
				//fuel = 1000;
				//gameState = GameState.NEW_GAME;
			}
			if ((Gdx.input.isKeyPressed(Keys.ESCAPE))) {
				gameOverBackground.dispose();
				ArcadeSystem.goToGame(ArcadeSystem.UI);
			}
			break;
		case GAME_OVER_WIN:
			// game over screen
			batch.begin();
			Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
			batch.setProjectionMatrix(camera.combined);
			batch.draw(gameOverWinBackground, 0, 0, 1200, 800);
			font.draw(batch, "Final score: " + Integer.toString(score), SCREENWIDTH - 715, SCREENHEIGHT - 350);
			batch.end();
			camera.update();
			super.render();
			if ((Gdx.input.isKeyPressed(Keys.ENTER))) {
				//fuel = 1000;
				//gameState = GameState.NEW_GAME;
			}
			if ((Gdx.input.isKeyPressed(Keys.ESCAPE))) {
				gameOverWinBackground.dispose();
				ArcadeSystem.goToGame(ArcadeSystem.UI);
			}
			break;
		}
	}


	private boolean isPointBelowLine(double px, double py, double x1, double y1, double x2, double y2) {
		if (px < x1 || px > x2)
			return false;

		double m = (y2 - y1)/(x2 - x1);
		double c = y1 - (m*x1);
		double lineY = m*px + c;

		return py <= lineY;
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
