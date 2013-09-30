package deco2800.arcade.junglejump.GUI;

import java.io.File;
import java.io.IOException;
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
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Frustum;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

import deco2800.arcade.model.Achievement;
import deco2800.arcade.model.Game;
import deco2800.arcade.model.Game.ArcadeGame;
import deco2800.arcade.model.Player;
//import deco2800.arcade.breakout.Breakout.GameState;
import deco2800.arcade.client.Arcade;
import deco2800.arcade.client.ArcadeSystem;
import deco2800.arcade.client.GameClient;
import deco2800.arcade.client.network.NetworkClient;

/**
 * Main class for Jungle Jump Game Instantiates game with scene, player and
 * assets
 * 
 */
@ArcadeGame(id = "junglejump")
public class junglejump extends GameClient implements InputProcessor {
	// MENU ENUMS
	public float NEW_GAME = 242;
	public float CONTINUE = (float) (242 - 37.5);
	public float LEVEL_SELECT = (float) (242 - 37.5*2);
	public float ACHIEVEMENTS = (float) (242 - 37.5*3);
	public float OPTIONS = (float) (242 - 37.5*4);
	public float QUIT = (float) (242 - 37.5*5);
	private enum GameState {
		AT_MENU, INPROGRESS, GAMEOVER
	}
	private GameState gameState;
	PerspectiveCamera cam;
    private SpriteBatch batch;

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
	Texture texture;
	ShapeRenderer shapeRenderer;

	Music themeMusic;
	Clip menuSound, jump, die, levelup, loselife, collect;
	
	
	public static void main (String [] args) {
		ArcadeSystem.goToGame("junglejump");
	}

	public junglejump(Player player, NetworkClient networkClient) {

		super(player, networkClient);
        this.networkClient = networkClient; //this is a bit of a hack
		Gdx.input.setCatchBackKey(true);
		Gdx.input.setInputProcessor(this);
		butX = 488f;
		butY = 242f;
		// Replace "file" with chosen music
		try {
			File file = new File("junglejumpassets/soundtrack.wav");
			FileHandle fileh = new FileHandle(file);
			AudioInputStream audioIn = AudioSystem.getAudioInputStream(file);
			Clip clip = AudioSystem.getClip();
			clip.open(audioIn);
			clip.start();
			clip.loop(clip.LOOP_CONTINUOUSLY);
			
//			System.out.println(file.getCanonicalPath());
//			themeMusic = Gdx.audio.newMusic(fileh);
//			themeMusic.setLooping(true);
//			themeMusic.play();
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
		Gdx.app.log(junglejump.messages, "Launching Game");
		camera = new OrthographicCamera();
		camera.setToOrtho(false, SCREENWIDTH, SCREENHEIGHT);
		batch = new SpriteBatch();
	    shapeRenderer = new ShapeRenderer();

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
		    shapeRenderer.filledRect (butX, butY, 232, 30, Color.CLEAR, Color.RED, Color.CLEAR, Color.RED);
		    shapeRenderer.end();
		    Gdx.gl.glDisable(GL10.GL_BLEND);
			camera.update();
			super.render();
			break;
		case INPROGRESS:
			break;
		case GAMEOVER:
			break;
		}
		
	}
	@Override
	public void pause() {
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
		camera.setToOrtho(false, SCREENWIDTH, SCREENHEIGHT);
		Gdx.app.log(junglejump.messages, "Pause");
	}

	@Override
	public void resume() {
		super.resume();
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

//	 

	@Override
	public boolean keyDown(int keycode) {
		if (keycode == Keys.LEFT) {
			// Move left
		} if (keycode == Keys.ENTER) {
			if (butY == QUIT) {
				super.dispose();
			} if (butY == NEW_GAME) {
				gameState = GameState.INPROGRESS;
			}
		} if (keycode == Keys.RIGHT) {
			// Move right
		} if (keycode == Keys.SPACE) {
//			System.out.println("SPACE DOWN");
//			butX += 10f;
			// Jump
		} if (keycode == Keys.UP) {
			if (butY < NEW_GAME) {
				menuSound.start();
				butY += 37.5;
				menuSound.stop();
			}
			// Climb
		} if (keycode == Keys.DOWN) {
			if (butY > QUIT) {
				menuSound.start();
				butY -= 37.5;
				menuSound.stop();
			}
			// Climb down
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
			// Move left STOP
		} if (keycode == Keys.RIGHT) {
			// Move right STOP
		} if (keycode == Keys.SPACE) {
//			System.out.println("SPACE UP");
//			butX += 10f;
			// Jump STOP
		} if (keycode == Keys.UP) {
			// Climb STOP
		} if (keycode == Keys.DOWN) {
			// Climb down STOP
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
