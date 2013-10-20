package deco2800.arcade.junglejump.GUI;


import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.UnsupportedAudioFileException;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Frustum;

import deco2800.arcade.junglejump.Level;
import deco2800.arcade.junglejump.LevelContainer;
import deco2800.arcade.junglejump.Platform;
import deco2800.arcade.model.Game;
import deco2800.arcade.model.Game.ArcadeGame;
import deco2800.arcade.model.Player;
import deco2800.arcade.client.ArcadeSystem;
import deco2800.arcade.client.GameClient;
import deco2800.arcade.client.network.NetworkClient;
import deco2800.arcade.client.AchievementClient;
import deco2800.arcade.model.Achievement;
/**
 * Main class for Jungle Jump Game Instantiates game with scene, player and
 * assets
 *
 */
@ArcadeGame(id = "junglejump")
public class junglejump extends GameClient implements InputProcessor {
	// MENU ENUMS
	public float NEW_GAME 			= 242;
	public float CONTINUE 			= (float) (242 - 37.5);
	public float LEVEL_SELECT 		= (float) (242 - 37.5 * 2);
	public float ACHIEVEMENTS 		= (float) (242 - 37.5 * 3);
	public float OPTIONS 			= (float) (242 - 37.5 * 4);
	public float QUIT 				= (float) (242 - 37.5 * 5);
	
	/* Player Stats */
	public int BANANAS_FOUND = 0;
	public static int deaths = 0;
	int SPEED_MULTIPLIER = 1;
	int monkeyLength = 35;
	int monkeyHeight = 40;
	public static float monkeyX;
	public static float monkeyY;
	float monkeyYoriginal;
	boolean movingLeft, movingRight;
	int monkeyRun = 0;
	boolean leap = true;
	boolean sit = false;
	public static boolean onVine = false;
	boolean jumping = false;
	
	/* Game Sate Enum - Used for Menu Handling */
	private enum GameState {
		AT_MENU, INPROGRESS, GAMEOVER, ACHIEVEMENTS, CONTINUE, PAUSE, OPTIONS,
		SELECT_LEVEL
	}
	private GameState gameState;
	
	/* Camera and Rendering Variables */
	PerspectiveCamera cam;
	private static SpriteBatch batch;
	Frustum camGone = new Frustum();
	public static int world;
	// Store details about the activity of junglejump and the players
	public static final String messages = junglejump.class.getSimpleName();
	private OrthographicCamera camera;
	public static final int SCREENHEIGHT = 480;
	public static final int SCREENWIDTH = 800;
	float butX;
	float butY;
	Texture texture;
	Clip clip;
	Texture monkeySit, monkeyRun1, monkeyRun2;
	Texture monkeySitLEFT, monkeyRun1LEFT, monkeyRun2LEFT;
	Texture monkeySitRIGHT, monkeyRun1RIGHT, monkeyRun2RIGHT;
	public static Texture gameBackground;
	public static Texture platform, levelText, hyphenText, livesText, levelNumText, 
	worldNumText, livesNumText;
	ShapeRenderer shapeRenderer;
	Music themeMusic;
	Clip menuSound, jump, die, levelup, loselife, collect;
	private SpriteBatch batchContinue;
	
	/* Monkey Info */
	float velocity = 5.0f;
	boolean correct = false;
	boolean onPlatform, isFalling = false;
	public static int lives = 3;
	public static float monkeyDefaultX;
	public static float monkeyDefaultY;

	/* Level Storage and Achievment Vars */
	private static LevelContainer currentCont;
	public static Level currentLevel;
	ArrayList<Achievement> achievementArray;

	/* Fonts used for Menus */
	BitmapFont achievementTitleFont;
	BitmapFont achievementIDFont;
	BitmapFont achievementNameFont;
	BitmapFont achievementDescriptionFont;
	BitmapFont achievementThresholdFont;
	Texture achievementIconTexture;
	String levelSelectText;

	public static void main(String[] args) {
		ArcadeSystem.goToGame("junglejump");
	}
	/**
	 * Initialize Variables and Create Player 
	 * 
	 * @param player
	 * @param networkClient
	 */
	public junglejump(Player player, NetworkClient networkClient) {
		/* Initialize Variables */
		super(player, networkClient);
		this.networkClient = networkClient; 
		Gdx.input.setCatchBackKey(true);
		Gdx.input.setInputProcessor(this);
		butX = 488f;
		butY = 242f;
		monkeyDefaultX = 60f;
		monkeyDefaultY = 80f;
		monkeyX = monkeyDefaultX;
		monkeyY = monkeyDefaultY;
		monkeyYoriginal = 0f;
		
		/* Initialize Background Music and Menu Sounds */
		URL path = this.getClass().getResource("/");		
		try {
			String resource = path.toString().replace(".arcade/build/classes/main/", 
					".arcade.junglejump/src/main/").replace("file:", "") + 
					"resources/soundtrack.wav";
			System.out.println(resource);
			File file = new File(resource);
			new FileHandle(file);
			AudioInputStream audioIn = AudioSystem.getAudioInputStream(file);
			this.clip = AudioSystem.getClip();
			clip.open(audioIn);
			clip.start();
			clip.loop(Clip.LOOP_CONTINUOUSLY);

		} catch (Exception e) {
			Gdx.app.log(junglejump.messages,
					"Audio File for Theme Music Not Found");
		}
		try {
			String resource = path.toString().replace(".arcade/build/classes/main/", 
					".arcade.junglejump/src/main/").replace("file:", "") + 
					"resources/menu.wav";
			File file2 = new File(resource);
			AudioInputStream audioIn2 = AudioSystem.getAudioInputStream(file2);
			menuSound = AudioSystem.getClip();
			menuSound.open(audioIn2);
		} catch (Exception e) {
			// IO Exception or problem with sound format
		}
	}

	@Override
	/**
	 * Loads Textures and other Images for use in the world. Fonts are
	 * created for menu systems and achievements.
	 * 
	 */
	public void create() {
		super.create();
		/* Textures for World and Monkey */
		texture 			= new Texture(Gdx.files.internal("mainscreen2.png"));
		monkeySit 			= new Texture(Gdx.files.internal("monkeySit.png"));
		monkeySitRIGHT 		= new Texture(Gdx.files.internal("monkeySit.png"));
		monkeySitLEFT 		= new Texture(Gdx.files.internal("monkeySitLEFT.png"));
		monkeyRun1 			= new Texture(Gdx.files.internal("monkeyRun1.png"));
		monkeyRun1RIGHT 	= new Texture(Gdx.files.internal("monkeyRun1.png"));
		monkeyRun1LEFT 		= new Texture(Gdx.files.internal("monkeyRun1LEFT.png"));
		monkeyRun2 			= new Texture(Gdx.files.internal("monkeyRun2.png"));
		monkeyRun2RIGHT 	= new Texture(Gdx.files.internal("monkeyRun2.png"));
		monkeyRun2LEFT 		= new Texture(Gdx.files.internal("monkeyRun2LEFT.png"));
		gameBackground 		= new Texture(Gdx.files.internal("gameBackground2.png"));
		levelText 			= new Texture(Gdx.files.internal("level.png"));
		hyphenText 			= new Texture(Gdx.files.internal("-.png"));
		livesText 			= new Texture(Gdx.files.internal("lives.png"));
		worldNumText 		= new Texture(Gdx.files.internal("1.png"));
		livesNumText 		= new Texture(Gdx.files.internal("3.png"));
		levelNumText 		= new Texture(Gdx.files.internal("1.png"));
		
		/* Achievement Handler and Game creator */
		AchievementClient achClient 	= this.getAchievementClient();
		Game myGame 					= this.getGame();
		achievementArray 				= new ArrayList<Achievement>();
		achievementArray 				= achClient.achievementsForGame(myGame);
		if (achievementArray.isEmpty()) {
			System.out.println("list of achievements is empty. will be " +
					"fixed with merge with master (NEW ACHIEVEMENT API NEEDED)");
			achievementArray.add(new Achievement("-1", "Empty Achievement List", "issue is in the " +
					"achievement api of our branch. we have to merge master in to our branch " +
					"when we can", 0, "I.D10t"));
		}
		
		/* Achievement Fonts */
		achievementTitleFont       = new BitmapFont(false);
		achievementIDFont          = new BitmapFont(false);
		achievementNameFont        = new BitmapFont(false);
		achievementDescriptionFont = new BitmapFont(false);
		achievementThresholdFont   = new BitmapFont(false);
		achievementIconTexture     = new Texture(("monkeySit.png"));

		/* Sets up logging for game and Camera */
		Gdx.app.log(junglejump.messages, "Launching Game");
		camera = new OrthographicCamera();
		camera.setToOrtho(false, SCREENWIDTH, SCREENHEIGHT);
		batch = new SpriteBatch();
		batchContinue = new SpriteBatch();
		shapeRenderer = new ShapeRenderer();

		// Game begins at Main Menu
		gameState = GameState.AT_MENU;
	}

	@Override
	public void dispose() {
		super.dispose();
	}
	
	/**
	 * Resize JungleJump World
	 */
	public void resize(int w, int h) {
		super.resize(w,h);
		Gdx.app.log(junglejump.messages, "Resizing game width " + w
				+ " height " + h);
	}
	/**
	 * Depending on Game State, Render the world accordingly.
	 * Choices: AT_MENU, INPROGRESS, GAMEOVER, PAUSE, OPTIONS, CONTINUE
	 * 
	 */
	public void render() {
		switch (gameState) {
		/* At the Main Menu */
		case AT_MENU:
			// Clears stage and colour buffers
			Gdx.gl.glClearColor(0f, 1f, 0f, 1f);
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
			batch.setProjectionMatrix(camera.combined);
			shapeRenderer.setProjectionMatrix(camera.combined);
			batch.begin();
			batch.draw(texture, 0, 0, 800, 480);
			batch.end();
			Gdx.gl.glEnable(GL10.GL_BLEND);
			Gdx.gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
			shapeRenderer.begin(ShapeType.FilledRectangle);
			shapeRenderer.filledRect(butX, butY, 232, 30, Color.CLEAR,
					Color.RED, Color.CLEAR, Color.RED);
			shapeRenderer.end();
			Gdx.gl.glDisable(GL10.GL_BLEND);
			camera.update();
			super.render();
			break;
		/* Playing the Game */
		case INPROGRESS:
			// Clears stage and colour buffers
			Gdx.gl.glClearColor(0f, 1f, 0f, 1f);
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
			batch.setProjectionMatrix(camera.combined);
			shapeRenderer.setProjectionMatrix(camera.combined);
			if (movingLeft) {
				monkeyX -= 2 * SPEED_MULTIPLIER;
				monkeyRun--;
				if (!isOnPlatform(monkeyX, monkeyY) && !jumping) {
					isFalling = true;
				} else isFalling = false;
			}
			if (movingRight) {
				monkeyX += 2 * SPEED_MULTIPLIER;
				monkeyRun++;
				if (!isOnPlatform(monkeyX, monkeyY) && !jumping) {
					isFalling = true;
				} else isFalling = false;
			}
			if (isFalling) {
				if (isOnPlatform(monkeyX, monkeyY)) {
					isFalling = false;
				} else monkeyY += (-9.8f / 2f);
			}
			if(monkeyY <= 0) {
				playDeathSound();
				killMonkey();
			}
			if (jumping) {
				velocity = (velocity - 9.8f / 75f);
				if ((monkeyY > monkeyYoriginal) && (!isOnPlatform(monkeyX, ++monkeyY))) {
					if (monkeyY + velocity < 1f) {
						monkeyY = 0;
					} else {
						monkeyY += velocity;
					}
					if (correct) {
						monkeyYoriginal += 1.3f;
						correct = false;
					}
				} else {
					System.out.println(true);
					isFalling = true;	// Changed this to true as monkey would be falling
										// Gets rid of floating in air glitch
					jumping = false;
				}
			}
			batch.begin();
			batch.draw(gameBackground, 0, 0, 800, 480);
			if ((!movingLeft && !movingRight) || (movingLeft && movingRight)) {
				batch.draw(monkeySit, monkeyX, monkeyY, 50, 50);
			} else if (monkeyRun % 10 == 0) {
				if (leap && sit) {
					sit = false;
				} else if (leap && !sit) {
					// leap = true;
					leap = false;
				} else if (!leap && sit) {
					sit = false;
				} else if (!leap && !sit) {
					sit = true;
					leap = true;
				} else {
					sit = true;
				}
			} if (sit && !((!movingLeft && !movingRight) || (movingLeft && movingRight))) {
				batch.draw(monkeySit, monkeyX, monkeyY, 50, 50);
			} else if (leap && !sit && !((!movingLeft && !movingRight) || (movingLeft && movingRight))) {
				batch.draw(monkeyRun2, monkeyX, monkeyY, 50, 50);
			} else if (!leap && !sit && !((!movingLeft && !movingRight) || (movingLeft && movingRight))) {
				batch.draw(monkeyRun1, monkeyX, monkeyY, 50, 50);
			}
			
			drawLevel();
			
			batch.draw(levelText, 5, 5, 80, 30);
			batch.draw(hyphenText, 105, 5, 30, 30);
			batch.draw(livesText, 5, 30, 80, 30);
			batch.draw(levelNumText, 125, 5, 30, 30);
			batch.draw(worldNumText, 85, 5, 30, 30);
			batch.draw(livesNumText, 85, 30, 30, 30);
			// Draws Instructions on Top Right and Top Left of screen
			achievementTitleFont.draw(batch, "Press P to PAUSE", SCREENWIDTH-250, SCREENHEIGHT-10);
			achievementTitleFont.draw(batch, "BACKSPACE for MENU", SCREENWIDTH-250, SCREENHEIGHT-30);
			achievementTitleFont.draw(batch, ("Bananas found: " + BANANAS_FOUND), SCREENWIDTH-500, SCREENHEIGHT-10);


			batch.end();
			camera.update();
			super.render();
			break;
		/* Game has ended */
		case GAMEOVER:
			break;
		/* Pause Selected */
		case PAUSE:
			batch.setProjectionMatrix(camera.combined);
			batch.begin();
			achievementTitleFont.draw(batch, "PAUSED", SCREENWIDTH/2, SCREENHEIGHT/2);
			batch.end();         
			break;
		/* At Options Menu */
		case OPTIONS:
			Gdx.gl.glClearColor(0f, 0f, 0f, 0f);
			batchContinue.setProjectionMatrix(camera.combined);
			// Clear Buffers for Drawing of Options Menu
			Gdx.gl.glEnable(GL10.GL_BLEND);
			Gdx.gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
			shapeRenderer.begin(ShapeType.FilledRectangle);
			shapeRenderer.filledRect(227, 117, 266, 106, Color.BLACK,
					Color.BLACK, Color.BLACK, Color.BLACK);
			shapeRenderer.filledRect(230, 120, 260, 100, Color.BLUE,
					Color.BLUE, Color.BLUE, Color.BLUE);
			shapeRenderer.end();
			Gdx.gl.glDisable(GL10.GL_BLEND);
			batchContinue.begin();
			// Text for Options Menu
			achievementTitleFont.draw(batchContinue, "OPTIONS", 240, 200);
			achievementTitleFont.draw(batchContinue, "1. Toggle Music ON/OFF.", 290, 170);
			achievementTitleFont.draw(batchContinue, "2. Back to Menu.", 290, 145);

			batchContinue.end();
			camera.update();
			super.render();
			break;
		/* Continue last game */
		case CONTINUE:
			Gdx.gl.glClearColor(0f, 0f, 0f, 0f);
			batchContinue.setProjectionMatrix(camera.combined);
			// Load Previous game? If yes, continue to game, if not go back to menu.
			Gdx.gl.glEnable(GL10.GL_BLEND);
			Gdx.gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
			shapeRenderer.begin(ShapeType.FilledRectangle);
			shapeRenderer.filledRect(227, 117, 266, 106, Color.BLACK,
					Color.BLACK, Color.BLACK, Color.BLACK);
			shapeRenderer.filledRect(230, 120, 260, 100, Color.BLUE,
					Color.BLUE, Color.BLUE, Color.BLUE);
			shapeRenderer.end();
			Gdx.gl.glDisable(GL10.GL_BLEND);
			batchContinue.begin();
			// Prompt to Continue previous Game
			achievementTitleFont.draw(batchContinue, "Are you sure you want to continue?", 240, 200);
			achievementTitleFont.draw(batchContinue, "Y or N?", 310, 150);

			batchContinue.end();
			camera.update();
			super.render();
			break;
		/* Achievements Menu */
		case ACHIEVEMENTS:
			// Clear Buffers and prepare for drawing of Achievement List
			Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
			batch.setProjectionMatrix(camera.combined);
			shapeRenderer.setProjectionMatrix(camera.combined);
			batch.begin();
			// Draw achievement labels and graphics here
			achievementTitleFont.draw(batch, "Achievements", 100, SCREENHEIGHT); /* TITLE */
			achievementTitleFont.draw(batch, "Press \"BACKSPACE\" to go back to the main menu.", 100, SCREENHEIGHT-20); /* navigation */
			// loop through achievements and draw relevant information
			for (int i = 0; i <= achievementArray.size() - 1; i++) {
				// draw achievement information set out
				// draw achievement id in a small font (unimportant information for most users)
				achievementIDFont.draw(batch, achievementArray.get(i).id, 10, 460 + ((i + 1)*-40));
				achievementNameFont.draw(batch, achievementArray.get(i).name, 100, 460 + ((i + 1)*-55));
				achievementIDFont.draw(batch, achievementArray.get(i).description, 100, 460 + ((i + 1)*-95));
				achievementThresholdFont.draw(batch,Integer.toString(achievementArray.get(i).awardThreshold),SCREENWIDTH - 100,460 + ((i + 1)*-50));
				// do something for the achievement icon
				batch.draw(achievementIconTexture,40,460 + ((i + 1)*-90),32,32);
			}
			batch.end();
			camera.update();
			super.render();
			break;
		case SELECT_LEVEL:
			Gdx.gl.glClearColor(0f, 0f, 0f, 0f);
			batchContinue.setProjectionMatrix(camera.combined);
		
			Gdx.gl.glEnable(GL10.GL_BLEND);
			Gdx.gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
			shapeRenderer.begin(ShapeType.FilledRectangle);
			shapeRenderer.filledRect(227, 117, 266, 106, Color.BLACK,
					Color.BLACK, Color.BLACK, Color.BLACK);
			shapeRenderer.filledRect(230, 120, 260, 100, Color.BLUE,
					Color.BLUE, Color.BLUE, Color.BLUE);
			shapeRenderer.end();
			Gdx.gl.glDisable(GL10.GL_BLEND);
			batchContinue.begin();
			// Prompt to Continue previous Game
			achievementTitleFont.draw(batchContinue, levelSelectText, 240, 200);
			achievementTitleFont.draw(batchContinue, "Press 1 to 5", 310, 150);

			batchContinue.end();
			camera.update();
			super.render();
			break;
		}

	}
	
	public static void drawLevel() {
		batch.begin();
		int size = currentLevel.platformAmount();
		for (int i = 0; i < size; i++) {
			Platform p = currentLevel.getPlatforms().get(i);
			batch.draw(p.getTexture(), p.getX(), p.getY(), p.getWidth(), p.getHeight());
		}
		batch.end();
	}

	public static void killMonkey() {
		monkeyY = 100;
		monkeyX = 10;
		lives--;
		deaths++;
		if(deaths > 100) {
			// TODO achievement for deaths
		}
		if(lives > 0) {
		} else {
			// TODO Change to gameover screen 
			lives = 5;
		}
		livesNumText = new Texture(("" + lives + ".png"));
	}
	
	public  void playPickupSound() {
		URL path = this.getClass().getResource("/");
		try{ 
			String resource = path.toString().replace(".arcade/build/classes/main/", 
					".arcade.junglejump/src/main/").replace("file:", "") + 
					"resources/pickup.wav";
			System.out.println(resource);
			File file = new File(resource);
			new FileHandle(file);
			AudioInputStream audioIn = AudioSystem.getAudioInputStream(file);
			Clip clip = AudioSystem.getClip();
			clip.open(audioIn);
			clip.start();
		} catch (Exception e) {
			Gdx.app.log(junglejump.messages,
					"Audio File for Banana Music Not Found");
		}
	}
	public void playDeathSound() {
		URL path = this.getClass().getResource("/");
		try{ 
			String resource = path.toString().replace(".arcade/build/classes/main/", 
					".arcade.junglejump/src/main/").replace("file:", "") + 
					"resources/death.wav";
			System.out.println(resource);
			File file = new File(resource);
			new FileHandle(file);
			AudioInputStream audioIn = AudioSystem.getAudioInputStream(file);
			Clip clip = AudioSystem.getClip();
			clip.open(audioIn);
			clip.start();
		} catch (Exception e) {
			Gdx.app.log(junglejump.messages,
					"Audio File for Death Music Not Found");
		}
	}

	public boolean isOnPlatform(float x, float y) {
		int size = currentLevel.platformAmount();
		for (int i = 0; i < size; i++) {
			Platform p = currentLevel.getPlatforms().get(i);
			// Check x and y are within the platform boundaries and monkey is on it
			if (p.platType != '=' && x > (p.getX() - monkeyLength)
					&& x < (p.getX()+p.getWidth() - 10)
					&& y <= p.getY() + p.getHeight() // Top of platform
					&& y >= p.getY() - monkeyHeight) {				// Bottom of platform
				
				p.setActive();
				
				if( p.platType == 'b') {
					p.visible = false;
					// TODO change this to a function to count bananas
					BANANAS_FOUND++;
					System.out.println(BANANAS_FOUND);
					p.setY(10000);
					
					// Play banana sound
					playPickupSound();
					
					return true;
				} else {

					// If the monkey's colliding with the platform, place him on top
					if(y > (p.getY() + p.getHeight()/2)
							&& y < p.getY() + p.getHeight()) {
						System.out.println("resetting monkey");
						onPlatform = true;
						monkeyY = p.getY() + p.getHeight();
						jumping = false;
						return true;
					}
					
					jumping = false;
					
					// Check if monkey hits left side of platform
					if(x > (p.getX() - monkeyLength) && x < p.getX() + p.getWidth()/2 - monkeyLength
							&& y <= p.getY() + p.getHeight()/2) {
						monkeyX = p.getX() - monkeyLength;
						onPlatform = false;
						isFalling = true;
						return false;
					}
					
					// Same for right side
					if(x > (p.getX() + p.getWidth()/2) &&
							x < (p.getX() + p.getWidth() - 10)
							&& y <= p.getY() + p.getHeight()/2) {
						monkeyX = p.getX() + p.getWidth() -10;
						onPlatform = false;
						isFalling = true;
						return false;
					}
					
	
					// If monkey hits bottom of platform tough titties
					/*if(y >= p.getY() - monkeyHeight
							&& y <= p.getY()) {
						monkeyY = p.getY() - monkeyHeight;
						p.setInactive();
						onPlatform = false;
						isFalling = true;
						return false;
					}*/
					return true;
				}
			}
		} 
		return false;
	}

	@Override
	public void pause() {
		// add the overlay listeners
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

		// Initialise camera
		camera = new OrthographicCamera();
		camera.setToOrtho(false, SCREENWIDTH, SCREENHEIGHT);
		Gdx.app.log(junglejump.messages, "Pause");
	}

	@Override
	public void resume() {
		super.resume();
		clip.start();
		Gdx.app.log(junglejump.messages, "Resume");
	}

	private static final Game game;
	static {
		game = new Game();
		game.id = "junglejump";
		game.name = "Jungle Jump";
		game.description = "Help Jay find Jay's Jane";
	}

	public Game getGame() {
		return game;
	}


	@Override
	public boolean keyDown(int keycode) {
		if (keycode == Keys.LEFT) {
			monkeySit = monkeySitLEFT;
			monkeyRun1 = monkeyRun1LEFT;
			monkeyRun2 = monkeyRun2LEFT;
			movingLeft = true;
			// Move left
		}
		if (keycode == Keys.ENTER) {
			if (butY == QUIT) {
				Gdx.app.exit();
			}
			if (butY == NEW_GAME) {
				monkeyX = monkeyDefaultX;
				monkeyY = monkeyDefaultY;
				// Reset Bananas, Platforms and Level
				currentCont = new LevelContainer();
				getCurrentCont();
				getCurrentCont();
				currentLevel = LevelContainer.getLevel(LevelContainer.getCurrentLevel());
				gameState = GameState.INPROGRESS;
				butY = -1; // Should stop newgame from being accessed in game
			}
			if (butY == ACHIEVEMENTS) {
				gameState = GameState.ACHIEVEMENTS;
			}
			if (butY == CONTINUE) {
				gameState = GameState.CONTINUE;
			}
			if (butY == OPTIONS) {
				gameState = GameState.OPTIONS;
			}
			if (butY == LEVEL_SELECT) {
				levelSelectText = "Which world?";
				gameState = GameState.SELECT_LEVEL;
			}
		}
		if (keycode == Keys.BACKSPACE) {
			// pressed backspace
			if (gameState == GameState.ACHIEVEMENTS || gameState == GameState.INPROGRESS) {
				// load main menu
				gameState = GameState.AT_MENU;
			}
		}
		if (keycode == Keys.RIGHT) {
			// Move right
			monkeySit = monkeySitRIGHT;
			monkeyRun1 = monkeyRun1RIGHT;
			monkeyRun2 = monkeyRun2RIGHT;
			movingRight = true;
		}
		if (keycode == Keys.SPACE) {
			if (butY == NEW_GAME) {
				monkeyX = monkeyDefaultX;
				monkeyY = monkeyDefaultY;
				// Reset Bananas, Platforms and Level
				currentCont = new LevelContainer();
				getCurrentCont();
				getCurrentCont();
				currentLevel = LevelContainer.getLevel(LevelContainer.getCurrentLevel());
				gameState = GameState.INPROGRESS;
				butY = -1; // Should stop newgame from being accessed in game
			}
			// Jump
			if (!jumping && !isFalling) {
				velocity = 5.0f;
				monkeyYoriginal = monkeyY - 1f;
				monkeyY+=1.1f;
				correct = true;
				jumping = true;
				
				
				// Play sound effect for jumping
				URL path = this.getClass().getResource("/");
				try{ 
					String resource = path.toString().replace(".arcade/build/classes/main/", 
							".arcade.junglejump/src/main/").replace("file:", "") + 
							"resources/jump.wav";
					System.out.println(resource);
					File file = new File(resource);
					new FileHandle(file);
					AudioInputStream audioIn = AudioSystem.getAudioInputStream(file);
					Clip clip = AudioSystem.getClip();
					clip.open(audioIn);
					clip.start();
				} catch (Exception e) {
					Gdx.app.log(junglejump.messages,
							"Audio File for Jump Music Not Found");
				}
			}
		}
		if (keycode == Keys.UP) {
			if (gameState != GameState.INPROGRESS) {
				if (butY < NEW_GAME) {
					menuSound.start();
					butY += 37.5;
					menuSound.stop();
				}
			}

			// Climb
		}
		if (keycode == Keys.NUM_1) {
			if (gameState == GameState.OPTIONS){
				if (clip.isActive()) {
					clip.stop();
				} else {
					URL path = this.getClass().getResource("/");
					String resource = path.toString().replace(".arcade/build/classes/main/", 
							".arcade.junglejump/src/main/").replace("file:", "") + 
							"resources/soundtrack.wav";
					File file = new File(resource);
					new FileHandle(file);
					AudioInputStream audioIn;
					try {
						audioIn = AudioSystem.getAudioInputStream(file);
						this.clip = AudioSystem.getClip();
						clip.open(audioIn);
						clip.start();
						clip.loop(Clip.LOOP_CONTINUOUSLY);
					} catch (Exception e) {
						Gdx.app.log(junglejump.messages, "No File Found for Background Music");
					}
					
					//clip.loop(Clip.LOOP_CONTINUOUSLY);
				}
			}
		}
		if (keycode == Keys.NUM_2) {
			if (gameState == GameState.OPTIONS){
				gameState = GameState.AT_MENU;
			}
		}
		if (keycode == Keys.Y) {
			if (gameState == GameState.CONTINUE) {
				if (monkeyX != monkeyDefaultX)
				gameState = GameState.INPROGRESS;
			} else {
				gameState = GameState.INPROGRESS;
			}
		}
		if (keycode == Keys.N) {
			if (gameState == GameState.CONTINUE) {
				gameState = GameState.AT_MENU;
			}
		}
		if (keycode == Keys.DOWN) {
			// Climb down
			if (gameState != GameState.INPROGRESS) {
				if (butY > QUIT) {
					menuSound.start();
					butY -= 37.5;
					menuSound.stop();
				}
			}
		}
		if (keycode == Keys.P) {
			if (gameState == GameState.PAUSE || gameState == GameState.INPROGRESS) {
				gameState = (gameState != GameState.PAUSE) ? GameState.PAUSE : GameState.INPROGRESS;
			}
		}
		return true;
	}

	@Override
	public boolean keyTyped(char keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		if (keycode == Keys.LEFT) {
			movingLeft = false;
			// Move left STOP
		}
		if (keycode == Keys.RIGHT) {
			movingRight = false;
			// Move right STOP
		}
		if (keycode == Keys.SPACE) {
			// System.out.println("SPACE UP");
			// butX += 10f;
			// Jump STOP
		}
		if (keycode == Keys.UP) {
			// Climb STOP
		}
		if (keycode == Keys.DOWN) {
			// Climb down STOP
		}
		if (keycode == Keys.ESCAPE) {
			clip.stop();
		}
		return true;
	}

	@Override
	public boolean mouseMoved(int arg0, int arg1) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchUp(int arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub
		return false;
	}

	public static LevelContainer getCurrentCont() {
		return currentCont;
	}

	public static void setCurrentCont(LevelContainer currentCont) {
		junglejump.currentCont = currentCont;
	}

}
