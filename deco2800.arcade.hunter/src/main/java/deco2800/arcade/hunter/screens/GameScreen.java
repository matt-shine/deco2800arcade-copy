package deco2800.arcade.hunter.screens;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import deco2800.arcade.hunter.EntityHandler;
import deco2800.arcade.hunter.Hunter;
import deco2800.arcade.hunter.Hunter.Config;
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
	private SpriteBatch batch = new SpriteBatch();
	private SpriteBatch staticBatch = new SpriteBatch();
	private BitmapFont font = new BitmapFont(); //Can specify font here if we don't want to use the default
	public EntityHandler entityHandler;
	private Music musicResource;
	private float stateTime;	
	private float counter;
	private int multiplier;
	private long animalTime;
	private long itemTime;
	private long mapEntityTime;
	private long attackTime;
	
	public GameScreen(Hunter hunter) {
	
		
		this.hunter = hunter;
		
		// Initialise camera
		camera = new OrthographicCamera();
		camera.setToOrtho(false, Hunter.State.screenWidth, Hunter.State.screenHeight);
		camera.update();
		batch.setProjectionMatrix(camera.combined);
		
        //Generate random seed and feed it to the random number generator here.
        //If you want to make it work the same as a previous run, manually set the seed at the start
        Hunter.State.randomGenerator = new Random((long) (Math.random() * Long.MAX_VALUE));

        int numPanes = (int) (Math.ceil(Hunter.State.screenWidth / (double) Config.PANE_SIZE_PX) + 1);
        foregroundLayer = new ForegroundLayer(1, numPanes, this);
		backgroundLayer = new BackgroundLayer(0, this);
		spriteLayer = new SpriteLayer((float) 0.6, this);

		entityHandler = new EntityHandler(entities);
		
		// Spawn entities
		player = new Player(new Vector2(128, 5 * Config.TILE_SIZE), 64, 128, this);
		Animal animal = new Animal(new Vector2(800, 10*Config.TILE_SIZE), 128, 64, false,"hippo", entityHandler.getAnimalAnimation("hippo"), this);
		Animal prey = new Animal(new Vector2(700,10*Config.TILE_SIZE),128,64,true,"lion", entityHandler.getAnimalAnimation("lion"), this);
		Items item = new Items(new Vector2(Config.TILE_SIZE*6, 5*Config.TILE_SIZE), 64, 64, "Invulnerability",entityHandler.getItemTexture("Invulnerability"),this);

		entities.add(player);
		hunter.incrementAchievement("hunter.beginner");
		entities.add(animal);
		entities.add(prey);
		entities.add(item);
		
		// Plays the music
		if(Hunter.State.getPreferencesManager().isMusicEnabled() && Hunter.State.getPreferencesManager().isSoundEnabled()){
			FileHandle musicFile = Gdx.files.internal("gamemusic.mp3");
			musicResource = Gdx.audio.newMusic(musicFile);
			musicResource.setVolume(hunter.getPreferencesManager().getVolume());
			musicResource.setLooping(true);
			musicResource.play();
		}
		
		stateTime = 0f;
		counter = 0f;
		multiplier = 1;
		animalTime = 0;
		itemTime = 0;
		mapEntityTime = 0;
		attackTime = 0;
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
        batch.dispose();
        staticBatch.dispose();
        musicResource.dispose();
        font.dispose();
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

			if (Hunter.State.gameSpeed < Config.MAX_SPEED) {
				Hunter.State.gameSpeed++;
			}
		}
	}

	@Override
	public void render(float delta) {
		if (!Hunter.State.paused) {
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
			
			if (animalTime + 2500 <= System.currentTimeMillis()){
				if (Hunter.State.randomGenerator.nextFloat() >= 0.5){
					createAnimals();
				}
				animalTime = System.currentTimeMillis();
			}
			if (itemTime + 5000 <= System.currentTimeMillis()){
				if (Hunter.State.randomGenerator.nextFloat() >= 0.5){
					createItems(Hunter.State.randomGenerator.nextBoolean());
				}
				itemTime = System.currentTimeMillis();
			}
			if (mapEntityTime + 4000 <= System.currentTimeMillis()){
				if (Hunter.State.randomGenerator.nextFloat() >= 0.5){
					createMapEntity();
				}
				mapEntityTime = System.currentTimeMillis();
			}
			
			
		}
        //Draw everything
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
	

	public void setMultiplier(int m){
		multiplier = m;
	}
	
	public void addScore(int score){
		player.addScore(score);
	}
	
	public void addAnimalKilled(){
		player.addAnimalKilled();
	}
	
	public EntityCollection getEntites(){
		return entities;
	}
	
	private void createMapEntity() {
		entities.add(new MapEntity(new Vector2(player.getX() + Hunter.State.screenWidth, getForeground().getColumnTop(player.getX() + Hunter.State.screenWidth)),64,64, "spike trap", entityHandler.getMapEntity("spike trap"), this));
	}
	
	private void createAnimals(){
		String[] anims= {"hippo","lion","zebra"};
		String animal = anims[Hunter.State.randomGenerator.nextInt(3)];
		entities.add(new Animal(new Vector2(player.getX() + Config.PANE_SIZE_PX, getForeground().getColumnTop(player.getX() + Config.PANE_SIZE_PX)),64,64,Hunter.State.randomGenerator.nextBoolean(), animal, entityHandler.getAnimalAnimation(animal), this));
	}
	
	private void createItems(boolean weapon){
		String[] textures = {"DoublePoints", "ExtraLife", "Invulnerability", "Coin","Bow","Spear","Trident"};
		String item =  textures[Hunter.State.randomGenerator.nextInt(7)];
		entities.add(new Items(new Vector2(player.getX()+Config.PANE_SIZE_PX, getForeground().getColumnTop(player.getX()+Config.PANE_SIZE_PX)), 64, 64, item,entityHandler.getItemTexture(item),this));
	}

	private void pollInput() {
		if (Gdx.input.isKeyPressed(Keys.SHIFT_LEFT)) {
			// Attack
			player.attack();
			if (player.getWeapon() == "Bow" && attackTime + Config.PLAYER_ATTACK_COOLDOWN <= System.currentTimeMillis()){
				entities.add(new MapEntity(new Vector2(player.getX() + player.getWidth(), player.getY()+20),8,8, "arrow", entityHandler.getMapEntity("arrow"), this));
				attackTime = System.currentTimeMillis();
			}
		}

		if (Gdx.input.isKeyPressed(Keys.V)){
			gameOver();      //TEMPORARY, DON'T FORGET TO REMOVE! TODO
		}
				
		if (Gdx.input.isKeyPressed(Keys.SPACE) && player.isGrounded() && !player.isDead()) {
			// Jump
			player.jump();
			Sound jump = Gdx.audio.newSound(Gdx.files.internal("jump.wav"));
			if (Hunter.State.getPreferencesManager().isSoundEnabled()){
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
		drawWeaponAmmo(batch);
	}

	private void drawScore(SpriteBatch batch) {
		int x = Hunter.State.screenWidth / 2;
		int y = Hunter.State.screenHeight - 16;
		CharSequence scoreText = Integer.toString(player.getCurrentScore())+ "     X " + multiplier + " MULTIPLIER";
		font.draw(batch, scoreText, x, y);
	}
	
	private void drawDistance(SpriteBatch batch) {
		int x = 16;
		int y = Hunter.State.screenHeight - 16;
		CharSequence scoreText = Float.toString(player.getCurrentDistance()) + "m";
		font.draw(batch, scoreText, x, y);
	}
	
	private void drawLives(SpriteBatch batch) {
		Texture lives = new Texture("textures/lives.png");
		TextureRegion life = new TextureRegion(lives);
		for (int x = 1; x <= player.getLives(); x++) {
			batch.draw(life,
					(Hunter.State.screenWidth - (x * (life.getRegionWidth() / 2f + 16))),
					(Hunter.State.screenHeight - (life.getRegionHeight() / 2f) - 16),
                    life.getRegionWidth() / 2f, life.getRegionHeight() / 2f);
		}
	}

	private void drawWeaponAmmo(SpriteBatch batch){
		int x = 16;
		int y = 16;
		CharSequence ammoText = "Ammo: " + player.getWeaponAmmo();
		font.draw(batch, ammoText, x, y);
	}
	
	/**
	 * Moves the cameras along with the player
	 */
	private void moveCamera() {
		camera.position.set(player.getX() - (player.getWidth() / 2)
				+ Hunter.State.screenWidth / 3, player.getY()
				- (player.getHeight() / 2) + Hunter.State.screenHeight / 3, 0);

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

	public void gameOver(){
		musicResource.stop();
//		hunter.highscore.addMultiScoreItem("Distance", (int)player.getCurrentDistance());
//		hunter.highscore.addMultiScoreItem("Number", player.getCurrentScore());
//		hunter.highscore.sendMultiScoreItems();
		hunter.setScreen(new GameOverScreen(hunter, player.getCurrentDistance(),player.getCurrentScore(),player.getAnimalsKilled()));
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
