package deco2800.arcade.junglejump.GUI;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javax.security.auth.login.Configuration;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.UnsupportedAudioFileException;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.FPSLogger;
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
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

import deco2800.arcade.junglejump.Level;
import deco2800.arcade.junglejump.LevelContainer;
import deco2800.arcade.junglejump.Platform;
import deco2800.arcade.model.Game;
import deco2800.arcade.model.Game.ArcadeGame;
import deco2800.arcade.model.Player;
//import deco2800.arcade.breakout.Breakout.GameState;
import deco2800.arcade.client.Arcade;
import deco2800.arcade.client.ArcadeSystem;
import deco2800.arcade.client.GameClient;
import deco2800.arcade.client.network.NetworkClient;
import deco2800.arcade.client.AchievementClient;
import deco2800.arcade.model.Achievement;
import deco2800.arcade.model.AchievementProgress;
/**
 * Main class for Jungle Jump Game Instantiates game with scene, player and
 * assets
 * 
 */
@ArcadeGame(id = "junglejump")
public class junglejump extends GameClient implements InputProcessor {
	//FIXME difficult to read - add some whitespace etc. and some methods need shrinking
	// MENU ENUMS
	public float NEW_GAME = 242;
	public float CONTINUE = (float) (242 - 37.5);
	public float LEVEL_SELECT = (float) (242 - 37.5 * 2);
	public float ACHIEVEMENTS = (float) (242 - 37.5 * 3);
	public float OPTIONS = (float) (242 - 37.5 * 4);
	public float QUIT = (float) (242 - 37.5 * 5);

	private enum GameState {
		AT_MENU, INPROGRESS, GAMEOVER, ACHIEVEMENTS
	}
	
	int monkeyLength = 40;

	private GameState gameState;
	PerspectiveCamera cam;
	private static SpriteBatch batch;

	Frustum camGone = new Frustum();
	private World world;
	// Store details about the activity of junglejump and the players
	public static final String messages = junglejump.class.getSimpleName();
	// FPS Animation helper
	private FPSLogger fpsLogger;
	private Player player;
	private OrthographicCamera camera;
	public static final int SCREENHEIGHT = 480;
	public static final int SCREENWIDTH = 800;
	float butX;
	float butY;
	public static float monkeyX;
	public static float monkeyY;
	float monkeyYoriginal;
	boolean movingLeft, movingRight;
	int monkeyRun = 0;
	boolean leap = true;
	boolean sit = false;
	boolean jumping = false;
	float velocity = 5.0f;
	boolean correct = false;
	boolean onPlatform, isFalling = false;
	
//	public int currentLevelIndex = 0;
	static LevelContainer currentCont = new LevelContainer();
	public static Level currentLevel = currentCont.getLevel(currentCont.currentLevel);
//	public static int currentWorld = 0;
	
	public static float monkeyDefaultX;
	public static float monkeyDefaultY;

	Texture texture;
	Clip clip;
	Texture monkeySit, monkeyRun1, monkeyRun2;
	Texture monkeySitLEFT, monkeyRun1LEFT, monkeyRun2LEFT;
	Texture monkeySitRIGHT, monkeyRun1RIGHT, monkeyRun2RIGHT;
	Texture gameBackground, platform;
	ShapeRenderer shapeRenderer;

	/* ACHIEVEMENT VARIABLES */
	ArrayList testArray;
	BitmapFont achievementTitleFont, achievementFont;
	
	Music themeMusic;
	Clip menuSound, jump, die, levelup, loselife, collect;

	public static void main(String[] args) {
		ArcadeSystem.goToGame("junglejump");
	}

	public junglejump(Player player, NetworkClient networkClient) {

		super(player, networkClient);
		this.networkClient = networkClient; // this is a bit of a hack
		Gdx.input.setCatchBackKey(true);
		Gdx.input.setInputProcessor(this);
		butX = 488f;
		butY = 242f;
		monkeyDefaultX = 60f;
		monkeyDefaultY = 80f;
		monkeyX = monkeyDefaultX;
		monkeyY = monkeyDefaultY;
		monkeyYoriginal = 0f;
		// Replace "file" with chosen music
		try {
			File file = new File("junglejumpassets/soundtrack.wav");
			FileHandle fileh = new FileHandle(file);
			AudioInputStream audioIn = AudioSystem.getAudioInputStream(file);
			Clip clip = AudioSystem.getClip();
			clip.open(audioIn);
			clip.start();
			clip.loop(clip.LOOP_CONTINUOUSLY);

			// System.out.println(file.getCanonicalPath());
			// themeMusic = Gdx.audio.newMusic(fileh);
			// themeMusic.setLooping(true);
			// themeMusic.play();
		} catch (Exception e) {
			Gdx.app.log(junglejump.messages,
					"Audio File for Theme Music Not Found");
		}
		try {
			File file2 = new File("junglejumpassets/menu.wav");
			AudioInputStream audioIn2 = AudioSystem.getAudioInputStream(file2);
			menuSound = AudioSystem.getClip();
			menuSound.open(audioIn2);
		} catch (Exception e) {
			// IO Exception or problem with sound format
		}

		createWorld();

	}

	/*
	 * World for holding the Jungle Jump Game Includes Physics for handling
	 * movement and gravity
	 */
	private void createWorld() {

	}

	@Override
	public void create() {
		super.create();
		System.out.println(System.getProperty("user.dir"));
		texture = new Texture(("junglejumpassets/mainscreen.png"));
		monkeySit = new Texture(("junglejumpassets/monkeySit.png"));
		monkeySitRIGHT = new Texture(("junglejumpassets/monkeySit.png"));
		monkeySitLEFT = new Texture(("junglejumpassets/monkeySitLEFT.png"));
		monkeyRun1 = new Texture(("junglejumpassets/monkeyRun1.png"));
		monkeyRun1RIGHT = new Texture(("junglejumpassets/monkeyRun1.png"));
		monkeyRun1LEFT = new Texture(("junglejumpassets/monkeyRun1LEFT.png"));
		monkeyRun2 = new Texture(("junglejumpassets/monkeyRun2.png"));
		monkeyRun2RIGHT = new Texture(("junglejumpassets/monkeyRun2.png"));
		monkeyRun2LEFT = new Texture(("junglejumpassets/monkeyRun2LEFT.png"));
		gameBackground = new Texture(("junglejumpassets/gameBackground2.png"));
		//platform = new Texture("junglejumpassets/platform.png");
		
		/* ACHIEVEMENT STUFF */
		testArray = new ArrayList();
		testArray.add("achievement1");
		testArray.add("achievement2");
		testArray.add("achievement3");
		testArray.add("achievement4");
		testArray.add("achievement5");
		
		AchievementClient achClient = this.getAchievementClient();
		ArrayList<Achievement> achievements = achClient.achievementsForGame(this.getGame());
		AchievementProgress playerProgress = achClient.progressForPlayer(this.getPlayer());
		
		for(Achievement ach : achievements) {
		    System.out.println(ach.name);
		}
		
		achievementTitleFont = new BitmapFont(false);
		achievementFont = new BitmapFont(false);
		
		Gdx.app.log(junglejump.messages, "Launching Game");
		camera = new OrthographicCamera();
		camera.setToOrtho(false, SCREENWIDTH, SCREENHEIGHT);
		batch = new SpriteBatch();
		shapeRenderer = new ShapeRenderer();
		
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
		// Game begins at Main Menu
		gameState = GameState.AT_MENU;
	}

	@Override
	public void dispose() {
		super.dispose();
	}

	public void resize(int w, int h) {
		Gdx.app.log(junglejump.messages, "Resizing game width " + w
				+ " height " + h);
	}

	public void render() {
		// Clears the screen - not sure if this is needed
		switch (gameState) {
		case AT_MENU:
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
		case INPROGRESS:
			Gdx.gl.glClearColor(0f, 1f, 0f, 1f);
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
			batch.setProjectionMatrix(camera.combined);
			shapeRenderer.setProjectionMatrix(camera.combined);
			if (movingLeft) {
				monkeyX -= 2;
				monkeyRun--;
				if (!isOnPlatform(monkeyX, monkeyY) && !jumping) {
					isFalling = true;
				} else isFalling = false;
			}
			if (movingRight) {
				monkeyX += 2;
				monkeyRun++;
				if (!isOnPlatform(monkeyX, monkeyY) && !jumping) {
					isFalling = true;
				} else isFalling = false;
			}
			if ((monkeyY < 3)) {
				isFalling = false;
			}
			if (isFalling) {
				if (isOnPlatform(monkeyX, monkeyY)) {
					isFalling = false;
				} else monkeyY += -9.8f / 2f;
				if(monkeyY <= 5) {
					killMonkey();
				}
			}
			if (jumping) {
				velocity = (velocity - 9.8f / 75f);
				//System.out.println("monkeyY " + monkeyY + " monkeyYor " + monkeyYoriginal + " == " + (monkeyY > monkeyYoriginal) +(monkeyY + velocity < 1f) + velocity + " " + monkeyY);
				System.out.println(monkeyY > monkeyYoriginal);
				System.out.println(!isOnPlatform(monkeyX, monkeyY)); //this is false :(
				System.out.println(monkeyY + " " + monkeyYoriginal);
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
					isFalling = false;
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
			// Add platforms from platform coordinate array
//			currentLevel = LevelContainer.getLevel(currentLevelIndex);
//			for(int i=0; i<currentLevel.platformAmount(); i++) {
//				Platform p = currentLevel.getPlatforms().get(i);
//				float platY = p.getY() + (currentLevelIndex * SCREENHEIGHT); // Because levels are stacked on each other
//				batch.draw(p.getTexture(), p.getX(), platY);
//			}
			for (Platform p : currentLevel.getPlatforms()) {
				if(p.getX() >= 1000) {
					p.setX(p.getX()-1000);
				}
				batch.draw(p.getTexture(), p.getX(), p.getY(), p.getWidth(), p.getHeight());
			}
			//batch.draw(platform, 100, 50);

			batch.end();
			camera.update();
			super.render();
			break;
		case GAMEOVER:
			break;
		case ACHIEVEMENTS:
			Gdx.gl.glClearColor(0f, 1f, 0f, 1f);
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
			batch.setProjectionMatrix(camera.combined);
			shapeRenderer.setProjectionMatrix(camera.combined);
			batch.begin();
			// Draw achievement labels and graphics here
			achievementTitleFont.draw(batch, "Achievements", 100, 460);
			batch.end();
			camera.update();
			super.render();
			break;
		}

	}
	
	public void killMonkey() {
		monkeyY = 100;
		monkeyX = 10;
	}
	
	public static void drawLevel() {
		batch.begin();
		System.out.println(currentLevel);
		for (Platform p : currentLevel.getPlatforms()) {
			batch.draw(p.getTexture(), p.getX(), p.getY(), p.getWidth(), p.getHeight());
		}
		batch.end();
	}
	
	public boolean isOnPlatform(float x, float y) {
		for (Platform p : currentLevel.getPlatforms()) {
			if (x > (p.getX() - p.getWidth()/2 - monkeyLength/2) && x < (p.getX()+p.getWidth()/2 + // Check platform dimensions
					monkeyLength/2) && y <= p.getY() + p.getHeight() && y >= p.getY()-p.getHeight()/2) {
				
				if(y >= p.getY() && y < p.getY()+p.getHeight()/2) { // If the monkey's colliding with the platform, place him on top
					monkeyY = p.getY() + p.getHeight();
				} else if(y < p.getY()) { // monkey is hitting bottom of platform
					monkeyY = p.getY() - p.getHeight();
				}
				p.setActive();
				return true;
			} else {
				p.setInactive();
			}
		} return false;
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
				super.dispose();
			}
			if (butY == NEW_GAME) {
				gameState = GameState.INPROGRESS;
			}
			if (butY == ACHIEVEMENTS) {
				gameState = GameState.ACHIEVEMENTS;
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
			// Jump
			if (!jumping) {
				velocity = 5.0f;
				monkeyYoriginal = monkeyY - 1f;
				monkeyY+=1.1f;
				correct = true;
				jumping = true;
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

}
