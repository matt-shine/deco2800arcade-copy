package deco2800.arcade.lunarlander;

import java.util.*;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Array;

import deco2800.arcade.client.ArcadeSystem;
import deco2800.arcade.model.Game;
import deco2800.arcade.model.Game.ArcadeGame;
import deco2800.arcade.model.Player;
import deco2800.arcade.client.GameClient;
import deco2800.arcade.client.network.NetworkClient;

// Welcome to Lunar Lander! 

@ArcadeGame(id="lunarlander")
public class LunarLander extends GameClient {

	private enum GameState {
		AT_MENU, IN_GAME, GAME_PAUSED, GAME_OVER_LOSE,
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
	private Texture asteroidTexture;
	private Texture backgroundTexture1;
	private Texture backgroundTexture2;
	private Texture backgroundTexture3;
	private Texture backgroundTexture4;
	private Texture pauseScreen;
	private Texture splashScreenBackground;
	private Texture gameOverBackground;
	private Texture gameOverWinBackground;
	private Texture flames;
	Texture textureSolid;
	private TextureRegion backgroundTextureRegion; 
	private BitmapFont font;
	Sound flameSound;

	// lander variables
	private float landerWidth; // the width of the lander png
	private float landerHeight; // the height of the lander png
	private float landerX; // initial position of the lander - x coordinate
	private float landerY; // initial position of the lander - y coordinate
	private float velY;
	private float velXleft;
	private float velXright;

	//	//asteroid stuff
	//	private float asteroidX;
	//	private float asteroidY;
		
	// terrain generation
	private List<List<Integer>> terrain;
	private List<List<Integer>> asteroidArray;
	private boolean gameOver;
	private boolean upKey;

	// statistics to be displayed on game screen
	private int score;
	private int fuel;
	private long speed;
	private int time;

	Array<Texture> backgrounds = new Array<Texture>();

	// speed of movement when lander is moved left or right 
	private float sideSpeed = 40.0f;

	/**
	 * Basic constructor for Lunar Lander 
	 * @param player The name of the player
	 * @param networkClient 
	 * The network client for sending/receiving messages to/from the server
	 */
	public LunarLander(Player player, NetworkClient networkClient) {
		super(player, networkClient);
		this.networkClient = networkClient;  
	}

	/**
	 * Creates the game: overlay listeners, various Gdx tools,
	 * some initial variables, initial game state.
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

		
		// setting up various GDX tools	
		batch = new SpriteBatch();
		backgroundTexture1 = new Texture(Gdx.files.internal
				("lunarlanderassets/rose_nebula.png")); 
		backgroundTexture2 = new Texture(Gdx.files.internal
				("lunarlanderassets/deep_space.png")); 
		backgroundTexture3 = new Texture(Gdx.files.internal
				("lunarlanderassets/pelican_nebula.png")); 
		backgroundTexture4 = new Texture(Gdx.files.internal
				("lunarlanderassets/stars.png"));
		backgrounds.add(backgroundTexture1);
		backgrounds.add(backgroundTexture2);
		backgrounds.add(backgroundTexture3);
		backgrounds.add(backgroundTexture4);
		pauseScreen = new Texture(Gdx.files.internal
				("lunarlanderassets/pause_screen.png"));
		splashScreenBackground = new Texture(Gdx.files.internal
				("lunarlanderassets/LL_splash.png"));
		gameOverBackground = new Texture(Gdx.files.internal
				("lunarlanderassets/game_over_screen.png"));
		gameOverWinBackground = new Texture(Gdx.files.internal
				("lunarlanderassets/game_over_win.png"));
		backgroundTextureRegion = new TextureRegion
				(backgrounds.random(), 0, 0, 1200, 800); 
		flames = new Texture(Gdx.files.internal
				("lunarlanderassets/flames.png"));
		landerTexture = new Texture(Gdx.files.internal
				("lunarlanderassets/lander.png")); 
		asteroidTexture = new Texture(Gdx.files.internal
				("lunarlanderassets/asteroid.png"));
		font = new BitmapFont(Gdx.files.internal
				("lunarlanderassets/game_font_half.fnt"),
				Gdx.files.internal
				("lunarlanderassets/game_font_half_0.png"), false); 
		shapeRenderer = new ShapeRenderer();
		camera = new OrthographicCamera();
		camera.setToOrtho(false, SCREENWIDTH, SCREENHEIGHT);
		flameSound = Gdx.audio.newSound(Gdx.files.internal
				("lunarlanderassets/flame.ogg"));

		// setting up game variables 
		score = 0;
		fuel = 1000;
		//creates the map 
		terrain = LunarLanderTerrain.createMap();
		asteroidArray = LunarLanderTerrain.generateAsteroidArray();
		//set game state
		gameState = GameState.AT_MENU;
	}

	/**
	 * Creates a new random map when a player has finished a 
	 * game and wishes to play again
	 */
	private void newMap() {
		backgroundTextureRegion = new TextureRegion
				(backgrounds.random(), 0, 0, 1200, 800);
		terrain = LunarLanderTerrain.createMap();
	}

	/**
	 * Re-initialises variables for a new game
	 */
	private void startVars(int fuel, int score) {
		// setting up various constants 
		landerX = 600;
		landerY = 750;
		landerWidth = 30;
		landerHeight = 30;
		velY = 1;
		velXleft = 0;
		velXright = 0;
		//asteroidX = 500;
		//asteroidY = 500;
		speed = 1;
		this.fuel = fuel;
		this.score = score;
		gameOver = false;
		upKey = false;
		gameState = GameState.IN_GAME;	
	}

	/**
	 * Draws the lunar lander texture
	 */
	private void drawLander() {
		batch.begin();
		batch.draw(landerTexture, landerX, landerY, landerWidth, landerHeight);
		batch.end();
	}

	/**
	 * Draws the asteroids
	 */
	private void drawAsteroids(){
		batch.begin();
		for (int i = 0; i < asteroidArray.size(); i++){
			batch.draw(asteroidTexture, asteroidArray.get(i).get(0), 
					asteroidArray.get(0).get(1), 20, 20);
		}
		batch.end();
	}

	/**
	 * Draws the planet surface terrain
	 */
	private void drawMap() {
		//renders the map using the list of lists "terrain"
		LunarLanderTerrain.renderMap(terrain);
	}

	/**
	 * Draws the background - a texture selected randomly from
	 * an array of PNGs
	 */
	private void drawBackground(TextureRegion backgroundTextureRegion) {
		batch.begin();
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT); 
		batch.draw(backgroundTextureRegion, 0, 0);
		if(upKey == true) {
			batch.draw(flames, landerX + 10, landerY - 5, 10, 10);
			upKey = false;
		}		
		// display values on screen
		font.setColor(new Color(0.4f, 0.99f, 0.4f, 1f)); 
		font.draw(batch, "Score: " + Integer.toString(score), 
				SCREENWIDTH - 300, SCREENHEIGHT - 40);
		font.draw(batch, "Remaining fuel: " + Integer.toString(fuel), 
				SCREENWIDTH - 300, SCREENHEIGHT - 60);
		font.draw(batch, "Current speed: " + Long.toString(speed), 
				SCREENWIDTH - 300, SCREENHEIGHT - 80);
		font.draw(batch, "Time spent: " + Integer.toString(time), 
				SCREENWIDTH - 300, SCREENHEIGHT - 100);
		batch.end();		
	}

	/**
	 * Detects whether the lander has collided with the terrain
	 */
	private void checkCollision() {
		//check for collision
		for (int i = 1; i < terrain.size(); i++){
			if (isPointBelowLine(landerX, landerY, terrain.get(i).get(0),
					terrain.get(i).get(1), terrain.get(i).get(2), 
					terrain.get(i).get(3)) ||
					isPointBelowLine(landerX + landerWidth, landerY, 
							terrain.get(i).get(0),
							terrain.get(i).get(1), terrain.get(i).get(2), 
							terrain.get(i).get(3)) || 
							hasCollidedWithAsteroid(landerX, landerY)) {
				gameOver = true;
				gameState = GameState.GAME_OVER_LOSE;
			} else if(landerX > terrain.get(0).get(0) && landerX < 
					terrain.get(0).get(2)){
				if(landerY < terrain.get(0).get(1)){
					score += 10; //not incrementing properly					
					gameOver = true;
					velY = 0;		
					gameState = GameState.GAME_OVER_WIN;
				}
			}
		}
	}

	/**
	 * Detects whether the lander has collided with an asteroid
	 */
	private boolean hasCollidedWithAsteroid(float landerX2, float landerY2) {
		for (int i = 0; i < asteroidArray.size(); i++){
			if ((landerX2 > asteroidArray.get(i).get(0) && 
					landerX2 < asteroidArray.get(i).get(0) + 20) &&
					(landerY2 > asteroidArray.get(i).get(1) && 
							landerY2 < asteroidArray.get(i).get(1) + 20)){

				gameOver = true;
				velY = 0;
				gameState = GameState.GAME_OVER_LOSE;
				gameOverLoseScreen();
			}
		}
		return false;
	}

	/**
	 * Specifies the lander's movements upon various keys being pressed
	 * by the player
	 */
	private void landerMovement() {

		if(!(gameOver == true)) {
			// move lander left
			if ((Gdx.input.isKeyPressed(Keys.A)) || 
					(Gdx.input.isKeyPressed(Keys.LEFT))) {
				landerX -= Gdx.graphics.getDeltaTime() * sideSpeed;
				velXleft += 0.005;
				fuel -= 0.025;
			}
			// move lander right
			if ((Gdx.input.isKeyPressed(Keys.D)) || 
					(Gdx.input.isKeyPressed(Keys.RIGHT))) {
				landerX += Gdx.graphics.getDeltaTime() * sideSpeed;
				velXright += 0.005;
				fuel -= 0.025;
			}
			// boost the lander's speed
			if ((Gdx.input.isKeyPressed(Keys.W)) || 
					(Gdx.input.isKeyPressed(Keys.UP))) {
				if(fuel > 0){
					velY -= 0.015;
					fuel -= 0.05;
					//flameSound.setVolume(1);
					flameSound.play(0.01f);
					upKey = true;
				}
			}
		}
	}

	/**
	 * Specifies the velocity of the lander
	 */
	private void velocity() {
		if(!(gameOver == true) ) {
			velY += 0.005;
			landerY -= (0.10 + velY);
			landerX += velXright;
			landerX -= velXleft;

			if (velXleft > 0){
				velXleft -= 0.001;
			} else if(velXright > 0) {
				velXright -= 0.001;
			}
			//moves asteroids
			for (int i = 0; i < asteroidArray.size(); i++){
				int tempAsteroidX = asteroidArray.get(i).get(0);
				int tempAsteroidY = asteroidArray.get(i).get(1);
				tempAsteroidX -= 0.10;
				tempAsteroidY -= 0.10;
				asteroidArray.get(i).clear();
				asteroidArray.get(i).add(tempAsteroidX);
				asteroidArray.get(i).add(tempAsteroidY);
			}
		}
	}

	/**
	 * Clears the current screen and displays the game's splash screen
	 */
	private void splashScreen() {
		batch.begin();
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		batch.setProjectionMatrix(camera.combined);
		batch.draw(splashScreenBackground, 0, 0, 1200, 800);
		batch.end();
		camera.update();
		super.render();
	}

	/**
	 * Clears the current screen and displays the relevent screen
	 * for losing a game; offers the option to play again or quit the game
	 */
	private void gameOverLoseScreen() {
		batch.begin();
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		batch.setProjectionMatrix(camera.combined);
		batch.draw(gameOverBackground, 0, 0, 1200, 800);
		font.draw(batch, "Final score: " + Integer.toString(score), 
				SCREENWIDTH - 710, SCREENHEIGHT - 340);
		batch.end();
		camera.update();
		super.render();
	}

	/**
	 * Clears the current screen and displays the relevent screen
	 * for winning a game; offers the option to play again or quit the game
	 */
	private void gameOverWinScreen() {
		batch.begin();
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		batch.setProjectionMatrix(camera.combined);
		batch.draw(gameOverWinBackground, 0, 0, 1200, 800);
		font.draw(batch, "Final score: " + Integer.toString(score),
				SCREENWIDTH - 715, SCREENHEIGHT - 350);
		batch.end();
		camera.update();
		super.render();
	}

	/**
	 * Determines whether the lander texture has collided with the line
	 * that is the limit of the terrain
	 */
	private boolean isPointBelowLine(double px, double py, double x1, 
			double y1, double x2, double y2) {
		if (px < x1 || px > x2)
			return false;

		double m = (y2 - y1)/(x2 - x1);
		double c = y1 - (m*x1);
		double lineY = m*px + c;

		return py <= lineY;
	}

	/**
	 * Render the game according to its state; may be at the game menu,
	 * in the game, having lost a game, having won a game.
	 */
	@Override
	public void render() {	
		switch (gameState) {
		case AT_MENU: 
			// draw splash screen
			splashScreen();
			if ((Gdx.input.isKeyPressed(Keys.ENTER))) {
				startVars(fuel, score);
				gameState = GameState.IN_GAME;
			}
			if ((Gdx.input.isKeyPressed(Keys.ESCAPE))) {
				ArcadeSystem.goToGame(ArcadeSystem.UI);
			}
			break;
		case IN_GAME:	
			drawBackground(backgroundTextureRegion);
			drawLander();
			drawAsteroids();
			drawMap();
			camera.update();
			shapeRenderer.setProjectionMatrix(camera.combined);
			batch.setProjectionMatrix(camera.combined);		
			// pause game
			if ((Gdx.input.isKeyPressed(Keys.SPACE))) {
				gameState = GameState.GAME_PAUSED;
			}
			velocity();
			landerMovement();
			speed += velY * 1; // should be *=
			//for debugging purposes, can be used when game is over or not
			if (Gdx.input.isKeyPressed(Keys.Q)) {
			}	
			//check for collision
			checkCollision();
			super.render();
			break;			
		case GAME_PAUSED:
			// not working 
			//gamePaused = true;
			// show overlay with options to quit or resume
			batch.begin();
			//batch.setProjectionMatrix(camera.combined);
			batch.draw(pauseScreen, 100, 100);
			batch.end();
			if ((Gdx.input.isKeyPressed(Keys.SPACE))) {
				pauseScreen.dispose();
				//gamePaused = false;
				gameState = GameState.IN_GAME;
			}
			break;
		case GAME_OVER_LOSE:
			// game over screen
			gameOverLoseScreen();
			if ((Gdx.input.isKeyPressed(Keys.ENTER))) { 
				fuel = 1000;
				score = 0;
				startVars(fuel, score);
				Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
				newMap();
				gameState = GameState.IN_GAME;
			}
			if ((Gdx.input.isKeyPressed(Keys.ESCAPE))) {
				gameOverBackground.dispose();
				ArcadeSystem.goToGame(ArcadeSystem.UI);
			}
			break;
		case GAME_OVER_WIN:
			// game over screen
			gameOverWinScreen();
			if ((Gdx.input.isKeyPressed(Keys.ENTER))) {
				fuel = 1000;
				startVars(fuel, score);
				Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
				newMap();
				gameState = GameState.IN_GAME;
			}
			if ((Gdx.input.isKeyPressed(Keys.ESCAPE))) {
				gameOverWinBackground.dispose();
				ArcadeSystem.goToGame(ArcadeSystem.UI);
			}
			break;
		}
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
		game.description = 
				"Can your Lunar Lander make it to the surface safely?";
	}

	public Game getGame() {
		return game;
	}

}

