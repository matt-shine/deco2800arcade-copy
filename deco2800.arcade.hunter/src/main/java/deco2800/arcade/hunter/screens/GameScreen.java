package deco2800.arcade.hunter.screens;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import deco2800.arcade.hunter.Hunter;
import deco2800.arcade.hunter.Hunter.Config;
import deco2800.arcade.hunter.MusicManager.HunterMusic;
import deco2800.arcade.hunter.PhysicsHandler;
import deco2800.arcade.hunter.model.Animal;
import deco2800.arcade.hunter.model.BackgroundLayer;
import deco2800.arcade.hunter.model.ForegroundLayer;
import deco2800.arcade.hunter.model.Player;
import deco2800.arcade.hunter.model.SpriteLayer;
import deco2800.arcade.platformergame.model.EntityCollection;

/**
 * A Hunter game for use in the Arcade
 * 
 * @author Nessex, DLong94
 * 
 */
public class GameScreen implements Screen {
	private OrthographicCamera camera;
	private Hunter hunter;
	private EntityCollection entities = new EntityCollection();
	private Player player;
	private BackgroundLayer backgroundLayer;
	private SpriteLayer spriteLayer;
	private ForegroundLayer foregroundLayer;
	private float speedIncreaseCountdown = Config.SPEED_INCREASE_COUNTDOWN_START;
	private boolean paused = false;
	private SpriteBatch batch = new SpriteBatch();
	private SpriteBatch staticBatch = new SpriteBatch();
	private BitmapFont font = new BitmapFont(); //Can specify font here if we don't want to use the default
	private ArrayList<Animal> animalsKilled = new ArrayList<Animal>();
	private float stateTime;	
	
	public GameScreen(Hunter hunter) {
	
	
		this.hunter = hunter;
		// Plays the music
		hunter.getMusicManager().play(HunterMusic.GAME);
		// Initialise camera
		camera = new OrthographicCamera();
		camera.setToOrtho(false, Config.screenWidth, Config.screenHeight);
		camera.update();
		batch.setProjectionMatrix(camera.combined);

        //Generate random seed and feed it to the random number generator here.
        //If you want to make it work the same as a previous run, manually set the seed at the start
        Config.randomGenerator = new Random((long) (Math.random() * Long.MAX_VALUE));

		backgroundLayer = new BackgroundLayer(0);
		spriteLayer = new SpriteLayer((float) 0.6);

		int numPanes = (int) (Math.ceil(Config.screenWidth
				/ Config.PANE_SIZE_PX) + 1);
		foregroundLayer = new ForegroundLayer(1, numPanes);

		// Spawn entities
		player = new Player(new Vector2(128, 5 * Config.TILE_SIZE), 64, 128);
		Animal animal = new Animal(new Vector2(200, 5*Config.TILE_SIZE), 128, 128, false,
				"hippo");

		entities.add(player);
		hunter.incrementAchievement("hunter.beginner");
		entities.add(animal);
		
		stateTime = 0f;
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	/**
	 * Increases the game speed
	 * 
	 * @param delta
	 *            The current delta of the game
	 */
	private void increaseGameSpeed(float delta) {
		speedIncreaseCountdown -= delta;

		if (speedIncreaseCountdown <= 0) {
			speedIncreaseCountdown = Config.SPEED_INCREASE_COUNTDOWN_START;

			if (Config.gameSpeed < Config.MAX_SPEED) {
				Config.gameSpeed++;
			}
		}
	}

	@Override
	public void render(float delta) {
		if (!paused) {
			pollInput();

			stateTime += Gdx.graphics.getDeltaTime();
			
			increaseGameSpeed(delta);

			moveCamera();

			entities.updateAll(delta);

			PhysicsHandler.checkMapCollisions(entities, foregroundLayer);
			// PhysicsHandler.checkEntityCollisions(entities);

			backgroundLayer.update(delta, camera.position);
			spriteLayer.update(delta, camera.position);
			foregroundLayer.update(delta, camera.position);

			staticBatch.begin();
			backgroundLayer.draw(staticBatch);
			staticBatch.end();

			batch.begin();
			spriteLayer.draw(batch);
			foregroundLayer.draw(batch);

			entities.drawAll(batch, stateTime);

			batch.end();

			staticBatch.begin();
			drawGameUI(staticBatch);
			staticBatch.end();
		}
	}
	
	private ArrayList<Animal> getAnimalsKilled() {
		return animalsKilled;
	}
	

	private void pollInput() {
		if (Gdx.input.isKeyPressed(Keys.SHIFT_LEFT)) {
			// Attack
			player.attack();
		}

		if (Gdx.input.isKeyPressed(Keys.ESCAPE)){
			gameOver();
		}
		
		if (Gdx.input.isKeyPressed(Keys.SPACE) && player.isGrounded()) {
			// Jump
			player.jump();
		}

	}

	/**
	 * Draws the lives of the player on screen
	 * 
	 * @param batch SpriteBatch to use for drawing.
	 */
	private void drawGameUI(SpriteBatch batch) {
		drawLives(batch);
		drawScore(batch);
		drawDistance(batch);
	}
	
	private void drawScore(SpriteBatch batch) {
		int x = Config.screenWidth / 2;
		int y = Config.screenHeight - 16;
		CharSequence scoreText = Integer.toString(player.getCurrentScore());
		font.draw(batch, scoreText, x, y);
	}
	
	private void drawDistance(SpriteBatch batch) {
		int x = 16;
		int y = Config.screenHeight - 16;
		CharSequence scoreText = Float.toString(player.getCurrentDistance()) + "m";
		font.draw(batch, scoreText, x, y);
	}
	
	private void drawLives(SpriteBatch batch) {
		Texture lives = new Texture("textures/lives.png");
		TextureRegion life = new TextureRegion(lives);
		for (int x = 1; x <= player.getLives(); x++) {
			batch.draw(life,
					(Config.screenWidth - (x * (life.getRegionWidth() / 2f + 16))),
					(Config.screenHeight - (life.getRegionHeight() / 2f) - 16),
                    life.getRegionWidth() / 2f, life.getRegionHeight() / 2f);
		}
	}

	/**
	 * Moves the cameras along with the player
	 */
	private void moveCamera() {
		camera.position.set(player.getX() - (player.getWidth() / 2)
				+ Config.screenWidth / 3, player.getY()
				- (player.getHeight() / 2) + Config.screenHeight / 3, 0);

		camera.update();
		batch.setProjectionMatrix(camera.combined);
	}

	/**
	 * Returns foreground layer
	 * 
	 * @return ForegroundLayer of the game
	 */
	public ForegroundLayer getForeground() {
		return foregroundLayer;
	}

	private void gameOver(){
		hunter.setScreen(new GameOverScreen(hunter, player.getCurrentDistance(),player.getCurrentScore(),getAnimalsKilled().size()));
	}
	
	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void show() {
		// TODO Auto-generated method stub

	}
}
