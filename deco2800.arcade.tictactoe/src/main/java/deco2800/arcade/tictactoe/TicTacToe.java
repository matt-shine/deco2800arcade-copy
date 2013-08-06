package deco2800.arcade.tictactoe;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

import deco2800.arcade.client.GameClient;
import deco2800.arcade.client.network.NetworkClient;
import deco2800.arcade.model.Achievement;
import deco2800.arcade.model.Game;
import deco2800.arcade.model.Player;
import deco2800.arcade.model.Game.ArcadeGame;

/**
 * This is a broken implementation of tic-tac-toe.
 * 
 * @author s4266435
 *
 */

@ArcadeGame(id="tictactoe")
public class TicTacToe extends GameClient {
	private Texture dropImage;
	private Texture bucketImage;
	private Sound dropSound;
	private Music rainMusic;
	private SpriteBatch batch;
	private OrthographicCamera camera;
	private Rectangle bucket;
	private Array<Rectangle> raindrops;
	long lastDropTime;
	   
	public TicTacToe(Player player, NetworkClient networkClient){
		super(player, networkClient);
		//this.gameId = "TicTacToe";
		//this.name = "Tic Tac Toe";
	}
   
	/** 
	 * The create method initialises images, sound effects, music, camera, bucket and raindrop arrays.
	 * 
	 * @param None
	 * @return void
	 */
	@Override
	public void create() {
		// load the images for the droplet and the bucket, 64x64 pixels each
		dropImage = new Texture(Gdx.files.classpath("resources/droplet.png"));
		bucketImage = new Texture(Gdx.files.classpath("resources/bucket.png"));
		
		// Load the drop sound effect and the rain background "music"
		dropSound = Gdx.audio.newSound(Gdx.files.internal("resources/drop.wav"));
		rainMusic = Gdx.audio.newMusic(Gdx.files.internal("resources/rain.mp3"));

		// Start the playback of the background music immediately
		rainMusic.setLooping(true);
		rainMusic.play();

		// create the camera and the SpriteBatch
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);
		batch = new SpriteBatch();

  		// create a Rectangle to logically represent the bucket
  		bucket = new Rectangle();
  		bucket.x = 800 / 2 - 64 / 2; // center the bucket horizontally
  		bucket.y = 20; // bottom left corner of the bucket is 20 pixels above the bottom screen edge
  		bucket.width = 64;
  		bucket.height = 64;

  		// create the raindrops array and spawn the first raindrop
  		raindrops = new Array<Rectangle>();
  		spawnRaindrop();
  		
	}

	/**
	 * Spawns a raindrop.
	 */
	private void spawnRaindrop() {
		Rectangle raindrop = new Rectangle();
		raindrop.x = MathUtils.random(0, 800-64);
		raindrop.y = 480;
		raindrop.width = 64;
		raindrop.height = 64;
		raindrops.add(raindrop);
		lastDropTime = TimeUtils.nanoTime();
	}

	/**
	 * Renders the raindrop.
	 */
	@Override
	public void render() {
		
		// clear the screen with a dark blue color. The
		// arguments to glClearColor are the red, green
		// blue and alpha component in the range [0,1]
		// of the color to be used to clear the screen.
		Gdx.gl.glClearColor(0, 0, 0.2f, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		// tell the camera to update its matrices.
		camera.update();
  
		// tell the SpriteBatch to render in the
		// coordinate system specified by the camera.
		batch.setProjectionMatrix(camera.combined);

		// begin a new batch and draw the bucket and
		// all drops
		batch.begin();
		batch.draw(bucketImage, bucket.x, bucket.y);
		for(Rectangle raindrop: raindrops) {
			batch.draw(dropImage, raindrop.x, raindrop.y);
		}
		batch.end();
  
		// process user input
		if(Gdx.input.isTouched()) {
			Vector3 touchPos = new Vector3();
			touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
			camera.unproject(touchPos);
			bucket.x = touchPos.x - 64 / 2;
		}
		if(Gdx.input.isKeyPressed(Keys.LEFT)) bucket.x -= 200 * Gdx.graphics.getDeltaTime();
		if(Gdx.input.isKeyPressed(Keys.RIGHT)) bucket.x += 200 * Gdx.graphics.getDeltaTime();
  
		// make sure the bucket stays within the screen bounds
		if(bucket.x < 0) bucket.x = 0;
		if(bucket.x > 800 - 64) bucket.x = 800 - 64;
		
		// check if we need to create a new raindrop
		if(TimeUtils.nanoTime() - lastDropTime > 1000000000) spawnRaindrop();
		
		// move the raindrops, remove any that are beneath the bottom edge of
		// the screen or that hit the bucket. In the later case we play back
		// a sound effect as well.
		Iterator<Rectangle> iter = raindrops.iterator();
		while(iter.hasNext()) {
			Rectangle raindrop = iter.next();
			raindrop.y -= 200 * Gdx.graphics.getDeltaTime();
			if(raindrop.y + 64 < 0) iter.remove();
			if(raindrop.overlaps(bucket)) {
				dropSound.play();
				iter.remove();
			}
		}
	}
   
	/**
	 * Free the resources used in the class
	 */
	@Override
	public void dispose() {
		// dispose of all the native resources
		dropImage.dispose();
		bucketImage.dispose();
		dropSound.dispose();
		rainMusic.dispose();
		batch.dispose();
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	
	//Reusable list of achievements
	private static Set<Achievement> achievements = new HashSet<Achievement>();
	static {
		Achievement winGame = new Achievement("Win a game of Tic Tac Toe");
		achievements.add(winGame);
	}


	private static final Game game;
	static {
		game = new Game();
		game.gameId = "tictactoe";
		game.name = "Tic Tac Toe";
		game.availableAchievements = achievements;
	}

	public Game getGame() {
		return game;
	}
		
}
