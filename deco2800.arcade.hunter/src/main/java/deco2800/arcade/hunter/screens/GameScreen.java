package deco2800.arcade.hunter.screens;

import java.util.ArrayList;
import java.util.Collections;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import deco2800.arcade.hunter.Hunter;
import deco2800.arcade.hunter.Hunter.Config;
import deco2800.arcade.hunter.model.Animal;
import deco2800.arcade.hunter.model.BackgroundLayer;
import deco2800.arcade.hunter.model.ForegroundLayer;
import deco2800.arcade.hunter.model.Player;
import deco2800.arcade.hunter.model.SpriteLayer;
import deco2800.arcade.platformergame.model.Entity;
import deco2800.arcade.platformergame.model.EntityCollection;
import deco2800.arcade.platformergame.model.EntityCollision;

/**
 * A Hunter game for use in the Arcade
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
	
	private float gameSpeed = 64;
	private boolean paused = false;
	
	private SpriteBatch batch = new SpriteBatch();
	private SpriteBatch backgroundBatch = new SpriteBatch();
	
	public GameScreen(Hunter hunter){
		this.hunter = hunter;
		//Initialise camera
		camera = new OrthographicCamera();
		camera.setToOrtho(false, Config.screenWidth, Config.screenHeight);
		camera.update();
		batch.setProjectionMatrix(camera.combined);
		
		backgroundLayer = new BackgroundLayer(0);
		spriteLayer = new SpriteLayer((float) 0.6);

		int numPanes = (int) (Math.ceil(Config.screenWidth / Config.PANE_SIZE_PX) + 1);
		foregroundLayer = new ForegroundLayer(1, numPanes);

		
		player = new Player(new Vector2(128, 5 * Config.TILE_SIZE), 64, 128);
		Animal animal = new Animal(new Vector2(200,10),128,128,false,"hippo");

		entities.add(player);
		//entities.add(animal);
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

	@Override
	public void render(float delta) {
		if (!paused) {
			pollInput();
			
			moveCamera();
			
			entities.updateAll(delta);
			
			checkMapCollisions();
			checkEntityCollisions();
			
			backgroundLayer.update(delta, gameSpeed);
			spriteLayer.update(delta, gameSpeed);
			foregroundLayer.update(delta, camera.position.x);
			
			backgroundBatch.begin();
			backgroundLayer.draw(backgroundBatch);
			backgroundBatch.end();
			
			batch.begin();
			spriteLayer.draw(batch);
			foregroundLayer.draw(batch);
			
			entities.drawAll(batch);
			
//			drawLives();
			
			batch.end();
		}
		
	}
	
	private void pollInput() {
		if (Gdx.input.isKeyPressed(Keys.SHIFT_LEFT)){
			//Attack
		}
		
		if (Gdx.input.isKeyPressed(Keys.SPACE) && player.isGrounded()) {
			//Jump
			player.jump();
		}
		
	}
	
	private void drawLives() {
		Texture lives = new Texture("textures/lives.png");
		TextureRegion life = new TextureRegion(lives, 64,64);
		for(int x = 1; x<=player.getLives();x++){
			batch.draw(life, (Config.screenWidth-(x * life.getRegionWidth())), (Config.screenHeight - (x * life.getRegionHeight())));
		}
	}
	
	private void moveCamera() {
		camera.position.set(player.getX() - (player.getWidth() / 2) + Config.screenWidth / 3, 
				player.getY() - (player.getHeight() / 2) + Config.screenHeight / 3,
				0);
		
		camera.update();
		batch.setProjectionMatrix(camera.combined);
	}
	
	private void checkMapCollisions() {
		//Check map collisions for every entity that can collide with the map (should be player, animals & projectiles)
		
		//Move the item so that it no longer intersects with the map
		
		int i;
		
		for (Entity e : entities) {
			e.getCollider().clear();
			
			//Right edge
			
			float right = e.getX() + e.getWidth();
			//float top = e.getY() + e.getHeight();
			
//			for (i = (int) e.getY(); i <= top; i += Config.TILE_SIZE) {
//				if (foregroundLayer.getCollisionTileAt((int) right, i) != 0) {
//					e.setX((float) ((Math.floor(right / Config.TILE_SIZE) * Config.TILE_SIZE) - e.getWidth()));
//				}
//			}
//			
			//Top edge
//			for (i = (int) e.getX(); i <= right; i += Config.TILE_SIZE) {
				
//				if (foregroundLayer.getCollisionTileAt(i, (int) top) != 0) {
//					e.setY((float) ((Math.floor(top / Config.TILE_SIZE) * Config.TILE_SIZE) - e.getHeight()));
//				}
//			}
			
			
			//Bottom edge
			for (i = (int) right; i >= (int) e.getX(); i -= Config.TILE_SIZE) {
				boolean breakOut = false;
				int tile = foregroundLayer.getCollisionTileAt(i, e.getY());
				float slopeHeight = 0;
				switch (tile) {
					case 0:
						//Air, do nothing
						break;
					case 1:
						//Solid tile, do something
						e.getCollider().bottom = true;
						e.setY((float) (Math.ceil(e.getY() / Config.TILE_SIZE) * Config.TILE_SIZE) + 0.5f);
						breakOut = true;
						break;
					case 2:
						//   /_|  slope
						if (i == (int) right) {
							slopeHeight = (float) (Math.floor(e.getY() / Config.TILE_SIZE) * Config.TILE_SIZE + (e.getX() % Config.TILE_SIZE));
							if (e.getY() < slopeHeight) {
								e.setY(slopeHeight + 0.5f);
								e.getCollider().bottom = true;
							}
							breakOut = true;
						}
						break;
					case 3:
						//   |_\ slope
						if (i <= (int) e.getX()) {
							slopeHeight = (float) (Math.floor(e.getY() / Config.TILE_SIZE) * Config.TILE_SIZE + (Config.TILE_SIZE - 1 - e.getX() % Config.TILE_SIZE));
							if (e.getY() < slopeHeight) {
								e.setY(slopeHeight + 0.1f);
								e.getCollider().bottom = true;
							}
							
							breakOut = true;
						}
						break;
					case 4:
						//   |-/ slope
						break;
					case 5:
						//   \-| slope
						break;
					default:
						//Outside the map, or invalid tile.
						break;
				}
				
				if (breakOut) {
					break;
				}
				
			}
		}
		
	}
	
	private void checkEntityCollisions() {
		//Make a list of collision events
		//At the end process them all one after another
		/*
		 * precedence should be
		 * 
		 * 
		 * Player ->| left edge of screen
		 * Prey ->| left edge of screen
		 * Predator ->| left edge of screen
		 * Item ->| Player
		 * PlayerProjectile ->| Animal   (player melee attacks also PlayerProjectiles)
		 * WorldProjectile ->| Player    (animal melee attacks also WorldProjectiles)
		 * MapEntity ->| Animal
		 * MapEntity ->| WorldProjectile
		 * MapEntity ->| PlayerProjectile
		 * MapEntity ->| Player
		 * 
		 * use an enum for collision types and iterate through a sorted list
		 */
		
		ArrayList<EntityCollision> collisions = new ArrayList<EntityCollision>();
		
		for (Entity e : entities) {
			collisions.addAll(e.getCollisions(entities));
//			collisions.add(new EntityCollision(e, e, CollisionType.PLAYER_C_LEFT_EDGE)); //EXAMPLE, REMOVEME TODO
//			ArrayList<EntityCollision> ec = e.getCollisions();
//			for (EntityCollision c : ec) {
//				collisions.add(c);
//			}
		}
		//Add events here
		
		Collections.sort(collisions);
		
		for (EntityCollision c : collisions) {
			//Handle the collision
			c.getEntityOne().handleCollision(c.getEntityTwo());
		}
	}
	
	public ForegroundLayer getForeground() {
		return foregroundLayer;
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
