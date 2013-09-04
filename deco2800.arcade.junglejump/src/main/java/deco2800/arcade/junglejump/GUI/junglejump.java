package deco2800.arcade.junglejump.GUI;

import java.util.HashSet;
import java.util.Set;

import javax.security.auth.login.Configuration;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.math.Frustum;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

import deco2800.arcade.model.Achievement;
import deco2800.arcade.model.Game;
import deco2800.arcade.model.Game.ArcadeGame;
import deco2800.arcade.model.Player;
import deco2800.arcade.client.GameClient;
import deco2800.arcade.client.network.NetworkClient;

/**
 * Main class for Jungle Jump Game Instantiates game with scene, player and
 * assets
 * 
 */
@ArcadeGame(id = "junglejump")
public class junglejump extends GameClient implements InputProcessor {
	PerspectiveCamera cam;
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

	Music themeMusic;
	Sound jump, die, levelup, loselife, collect;

	public junglejump(Player player, NetworkClient networkClient) {
		super(player, networkClient);
		Gdx.input.setCatchBackKey(true);
		Gdx.input.setInputProcessor(this);
		// Replace "file" with chosen music
		try {
			themeMusic = Gdx.audio.newMusic(Gdx.files.internal(("file")));
			themeMusic.setLooping(true);
			themeMusic.play();
		} catch (Exception e) {
			Gdx.app.log(junglejump.messages,
					"Audio File for Theme Music Not Found");
		}
		// TODO: Additional sound files will be loaded here (jump, die, levelup
		// etc)

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
		Gdx.app.log(junglejump.messages, "Launching Game");
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
		Gdx.gl.glClearColor(0f, 1f, 0f, 1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		camera.update();
		// Logs current FPS
		fpsLogger.log();
		super.render();
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

	private void processInput() {

	}

	@Override
	public boolean keyDown(int keycode) {
		if (keycode == Keys.LEFT) {
			// Move left
		} if (keycode == Keys.RIGHT) {
			// Move right
		} if (keycode == Keys.SPACE) {
			// Jump
		} if (keycode == Keys.UP) {
			// Climb
		} if (keycode == Keys.DOWN) {
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
