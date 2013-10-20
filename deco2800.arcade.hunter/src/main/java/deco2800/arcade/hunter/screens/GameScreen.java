package deco2800.arcade.hunter.screens;

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
import deco2800.arcade.client.GameClient;
import deco2800.arcade.hunter.EntityHandler;
import deco2800.arcade.hunter.Hunter;
import deco2800.arcade.hunter.Hunter.Config;
import deco2800.arcade.hunter.PhysicsHandler;
import deco2800.arcade.hunter.model.*;
import deco2800.arcade.hunter.platformergame.Entity;
import deco2800.arcade.hunter.platformergame.EntityCollection;

import java.util.Random;

/**
 * A Hunter game for use in the Arcade
 *
 * @author Nessex, DLong94
 */
public class GameScreen implements Screen {
    private final OrthographicCamera camera;
    private final Hunter hunter;
    private final EntityCollection entities = new EntityCollection();
    private final Player player;
    private final BackgroundLayer backgroundLayer;
    private final SpriteLayer spriteLayer;
    private final ForegroundLayer foregroundLayer;
    private float speedIncreaseCountdown = Config.SPEED_INCREASE_COUNTDOWN_START;
    private final SpriteBatch batch = new SpriteBatch();
    private final SpriteBatch staticBatch = new SpriteBatch();
    private final BitmapFont font = new BitmapFont(); //Can specify font here if we don't want to use the default
    public final EntityHandler entityHandler;
    private Music musicResource;
    private float stateTime;
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
        backgroundLayer = new BackgroundLayer(0);
        spriteLayer = new SpriteLayer((float) 0.6, this);

        entityHandler = new EntityHandler(entities);

        // Spawn player
        player = new Player(new Vector2(128, 5 * Config.TILE_SIZE), 64, 128, this);
        entities.add(player);

        // Plays the music
        if (Hunter.State.getPreferencesManager().isMusicEnabled() && Hunter.State.getPreferencesManager().isSoundEnabled()) {
            FileHandle musicFile = Gdx.files.internal("gamemusic.mp3");
            musicResource = Gdx.audio.newMusic(musicFile);
            musicResource.setVolume(hunter.getPreferencesManager().getVolume());
            musicResource.setLooping(true);
            musicResource.play();
        }

        multiplier = 1;

        hunter.incrementAchievement("hunter.beginnings");
        hunter.incrementAchievement("hunter.smallsteps");
        hunter.incrementAchievement("hunter.expert");
    }

    @Override
    public void dispose() {
        batch.dispose();
        staticBatch.dispose();
        musicResource.dispose();
        font.dispose();
        MapPaneRenderer.dispose();
        backgroundLayer.dispose();
        foregroundLayer.dispose();
        player.dispose();
        spriteLayer.dispose();
        entityHandler.dispose();
    }

    @Override
    public void hide() {
        //Auto-generated method stub

    }

    @Override
    public void pause() {
        //Auto-generated method stub

    }

    /**
     * Increases the game speed
     *
     * @param delta The current delta of the game
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
            update(delta);
        }


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

    /**
     * Update the state of the game
     * @param delta delta time since the last update
     */
    private void update(float delta) {
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

        checkSpawnTimers();
    }

    /**
     * Check the spawn timers for animals, items and map entities.
     */
    private void checkSpawnTimers() {
        if (animalTime + 2500 <= System.currentTimeMillis()) {
            if (Hunter.State.randomGenerator.nextFloat() >= 0.5) {
                createAnimals();
            }
            animalTime = System.currentTimeMillis();
        }
        if (itemTime + 4500 <= System.currentTimeMillis()) {
            if (Hunter.State.randomGenerator.nextFloat() >= 0.5) {
                createItems();
            }
            itemTime = System.currentTimeMillis();
        }
        if (mapEntityTime + 4000 <= System.currentTimeMillis()) {
            if (Hunter.State.randomGenerator.nextFloat() >= 0.5) {
                createMapEntity();
            }
            mapEntityTime = System.currentTimeMillis();
        }
    }

    /**
     * @param m - Integer of multiplier to be set to
     */
    public void setMultiplier(int m) {
        multiplier = m;
    }

    /**
     * @param score - Adds integer of player score
     */
    public void addScore(int score) {
        player.addScore(score);
    }

    /**
     * Adds another animal killed
     */
    public void addAnimalKilled() {
        player.addAnimalKilled();
    }

    /**
     * @return EntityCollection of entities in the game screen
     */
    public EntityCollection getEntities() {
        return entities;
    }

	/*
	 * 
	 * Creates New Entities
	 * 
	 */
	
	/**
	 * Creates a new map entity
	 */
	private void createMapEntity() {
		String[] mapEntities = {"bomb", "net", "spike trap", "deathShroom"};
		String mapEntity = mapEntities[Hunter.State.randomGenerator.nextInt(4)];
		entities.add(new MapEntity(new Vector2(player.getX() + Hunter.State.screenWidth, getForeground().getColumnTop(player.getX() + Hunter.State.screenWidth)),entityHandler.getMapEntity(mapEntity).getWidth(),entityHandler.getMapEntity(mapEntity).getHeight(), mapEntity, entityHandler.getMapEntity(mapEntity), this));
	}
	
	/**
	 * Creates a new animal entity
	 */
	private void createAnimals(){
		String[] animals = {"hippo", "lion", "zebra"};
		String animal = animals[Hunter.State.randomGenerator.nextInt(3)];
		entities.add(new Animal(new Vector2(player.getX() + Config.PANE_SIZE_PX, getForeground().getColumnTop(player.getX() + Config.PANE_SIZE_PX)), 128, 128, animal, entityHandler.getAnimalAnimation(animal), this));
	}
	
	/**
	 * Creates a new Item
	 */
	private void createItems(){
		String[] textures = {"DoublePoints", "ExtraLife", "Invulnerability", "Coin","Bow","Spear","Trident"};
		String item =  textures[Hunter.State.randomGenerator.nextInt(7)];
		entities.add(new Item(new Vector2(player.getX()+Config.PANE_SIZE_PX, getForeground().getColumnTop(player.getX()+Config.PANE_SIZE_PX)), 64, 64, item,entityHandler.getItemTexture(item)));
	}

	/**
	 * Checks for player input
	 */
	private void pollInput() {
		if (Gdx.input.isKeyPressed(Keys.SHIFT_LEFT)) {
			// Attack
			player.attack();
			if (player.getWeapon().equals("Bow") && attackTime + Config.PLAYER_ATTACK_COOLDOWN <= System.currentTimeMillis()){
				entities.add(new MapEntity(new Vector2(player.getX() + player.getWidth(), player.getY()+20),8,8, "arrow", entityHandler.getMapEntity("arrow"), this));
				attackTime = System.currentTimeMillis();
			}
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

	/*
	 * 
	 * Drawing Static Game UI
	 * 
	 */

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

    /**
     * Draws the player's score on to the screen
     *
     * @param batch SpriteBatch to use for drawing.
     */
    private void drawScore(SpriteBatch batch) {
        int x = Hunter.State.screenWidth / 2;
        int y = Hunter.State.screenHeight - 16;
        CharSequence scoreText = Integer.toString(player.getCurrentScore()) + "     X " + multiplier + " MULTIPLIER";
        font.draw(batch, scoreText, x, y);
    }

    /**
     * Draws the distance the player has travelled
     *
     * @param batch SpriteBatch to use for drawing.
     */
    private void drawDistance(SpriteBatch batch) {
        int x = 16;
        int y = Hunter.State.screenHeight - 16;
        CharSequence scoreText = Float.toString(player.getCurrentDistance()) + "m";
        font.draw(batch, scoreText, x, y);
    }

    /**
     * Draws the amount of lives the player has left
     *
     * @param batch SpriteBatch to use for drawing.
     */
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

    /**
     * Draws ammo left on the player's weapon
     *
     * @param batch SpriteBatch to use for drawing.
     */
    private void drawWeaponAmmo(SpriteBatch batch) {
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

    /**
     * Ends the game
     */
    public void gameOver() {
        musicResource.stop();
        try{
	        if (hunter.highscore.getUserHighScore(true, "Number").score <= player.getCurrentScore()){
	        	hunter.highscore.storeScore("Number", player.getCurrentScore());
	        }
	        if (hunter.highscore.getUserHighScore(true, "Distance").score <= (int)player.getCurrentDistance()){
				hunter.highscore.addMultiScoreItem("Distance", (int)player.getCurrentDistance());
				hunter.highscore.sendMultiScoreItems();
	        }
        }catch(NullPointerException npe){
        	hunter.highscore.storeScore("Number", player.getCurrentScore());
        	hunter.highscore.addMultiScoreItem("Distance", (int)player.getCurrentDistance());
			hunter.highscore.sendMultiScoreItems();
        }finally{
        	hunter.setScreen(new GameOverScreen(hunter, player.getCurrentDistance(), player.getCurrentScore(), player.getAnimalsKilled()));
        }
    }

    /**
     * Removes the entity from the entity from the GameScreen
     *
     * @param e - Entity to be removed
     */
    public void removeEntity(Entity e) {
        entities.remove(e);
    }

    @Override
    public void resize(int width, int height) {
        //Auto-generated method stub

    }

    @Override
    public void resume() {
        //Auto-generated method stub

    }

    @Override
    public void show() {
        //Auto-generated method stub

    }

    public GameClient getGameReference() {
        return hunter;
    }
}
