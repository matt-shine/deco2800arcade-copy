package deco2800.arcade.hunter.screens;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
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
import deco2800.arcade.hunter.model.Items;
import deco2800.arcade.hunter.model.MapEntity;
import deco2800.arcade.hunter.model.Player;
import deco2800.arcade.hunter.model.SpriteLayer;
import deco2800.arcade.hunter.platformergame.Entity;
import deco2800.arcade.hunter.platformergame.EntityCollection;

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
	private float counter;
	private float timer;
	private int multiplier;
	
	public GameScreen(Hunter hunter) {
	
		
		this.hunter = hunter;
		// Plays the music
		if(Config.getPreferencesManager().isMusicEnabled() && Config.getPreferencesManager().isSoundEnabled()){
			hunter.getMusicManager().play(HunterMusic.GAME);
		}
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

		int numPanes = (int) (Math.ceil(Config.screenWidth / (double) Config.PANE_SIZE_PX) + 1);
		foregroundLayer = new ForegroundLayer(1, numPanes);

		// Spawn entities
		player = new Player(new Vector2(128, 5 * Config.TILE_SIZE), 64, 128);
		Animal animal = new Animal(new Vector2(800, 10*Config.TILE_SIZE), 128, 64, false,"hippo");
		Animal prey = new Animal(new Vector2(700,10*Config.TILE_SIZE),128,64,true,"lion");
		Items item = new Items(new Vector2(Config.TILE_SIZE*6, 5*Config.TILE_SIZE), 64, 64, false);

		entities.add(player);
		hunter.incrementAchievement("hunter.beginner");
		entities.add(animal);
		entities.add(prey);
		entities.add(item);
		
		stateTime = 0f;
		counter = 0f;
		multiplier = 1;
		
		
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
			PhysicsHandler.checkEntityCollisions(entities);

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
			
			if (stateTime - counter >= 10f){
				createAnimals();
				counter += 10f;
			}else{
				if(stateTime%4f <= 0.05f){
					if(Config.randomGenerator.nextFloat() >= 0.6)
						createAnimals();
				}
				if(stateTime%6f <= 0.05f){
					if(Config.randomGenerator.nextFloat() >= 0.7)
						createItems(false);
				}
				if(stateTime%8f <= 0.05f){
					if(Config.randomGenerator.nextFloat()>=0.7)
						createMapEntity();
				}
				if(stateTime%10f <= 0.05f){
					if(Config.randomGenerator.nextFloat() >= 0.5)
						createItems(true);
				}
			}
			timer -= delta;
			if (timer <= 0f && timer > -1f){
				player.setInvulnerability(false);
				player.setMultiplier(1);
			}
			if (timer <= -1f){
				checkBuffs();
			}
			
		}
	}
	

	/**
	 * Checks if any player buffs are applied
	 */
	private void checkBuffs() {
		if (player.isInvulnerable()){
			timer = 5f;
		}else if(player.getMultiplier() != 1){
			timer = 5f;
			multiplier = player.getMultiplier();
		}
	}

	/**
	 * Gets the number of animals that were killed by the player
	 * @return int animalsKilled
	 */
	private ArrayList<Animal> getAnimalsKilled() {
		return animalsKilled;
	}
	
	private void createMapEntity() {
		entities.add(new MapEntity(new Vector2(player.getX()+Config.screenWidth, getForeground().getColumnTop(player.getX()+Config.screenWidth)),64,64));
	}
	
	private void createAnimals(){
		String[] anims= {"hippo","lion","zebra"};
		System.out.println("New Animal");
//		entities.add(new Animal(new Vector2(player.getX()+Config.screenWidth, getForeground().getColumnTop(player.getX()+Config.screenWidth)),64,64,Config.randomGenerator.nextBoolean(), anims[Config.randomGenerator.nextInt(3)]));
	}
	
	private void createItems(boolean weapon){
		System.out.println("New Item");
//		entities.add(new Items(new Vector2(player.getX()+Config.screenWidth, getForeground().getColumnTop(player.getX()+Config.screenWidth)), 64, 64, weapon));
	}

	private void pollInput() {
		if (Gdx.input.isKeyPressed(Keys.SHIFT_LEFT)) {
			// Attack
			player.attack();
			
		}

		if (Gdx.input.isKeyPressed(Keys.V)){
			gameOver();
		}
				
		if (Gdx.input.isKeyPressed(Keys.SPACE) && player.isGrounded()) {
			// Jump
			player.jump();
			Sound jump = Gdx.audio.newSound(Gdx.files.internal("jump.wav"));
			if (Config.getPreferencesManager().isSoundEnabled()){
				jump.play(hunter.getPreferencesManager().getVolume());
			}
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
		CharSequence scoreText = Integer.toString(player.getCurrentScore())+ "     X " + multiplier + " MULTIPLIER";
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
	
	public void removeEntity(Entity e){
		entities.remove(e);
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
