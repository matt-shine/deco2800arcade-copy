package deco2800.arcade.hunter.model;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.openal.Mp3.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import deco2800.arcade.hunter.Hunter;
import deco2800.arcade.hunter.platformerGame.Entity;
import deco2800.arcade.hunter.platformerGame.EntityCollection;
import deco2800.arcade.hunter.platformerGame.EntityCollision;
import deco2800.arcade.hunter.platformerGame.EntityCollision.CollisionType;
import deco2800.arcade.hunter.screens.GameScreen;

public class MapEntity extends Entity {
	private Texture texture;
	private final int moveSpeed;
	private String mapEntityType;
	private final GameScreen gameScreen;
	private long explodeTime;
	private boolean explode = false;

    /**
     * @param pos initial position of the MapEntity
     * @param width initial width of the entity
     * @param height initial height of the entity
     * @param mapEntityType the name of the MapEntity, eg. "spike trap".
     * @param texture Texture containing the image of the map entity
     * @param gameScreen the GameScreen which is controlling this game instance
     */
	public MapEntity(Vector2 pos, float width, float height, String mapEntityType,
			Texture texture, GameScreen gameScreen) {
		super(pos, width, height);
		this.texture = texture;
        moveSpeed = (mapEntityType.equals("arrow")) ? 40 : 0;

		this.mapEntityType = mapEntityType;
		this.gameScreen = gameScreen;
	}

    /**
     * @return the type of map entity, e.g. "spike trap".
     */
	public String getEntityType() {
		return mapEntityType;
	}

    /**
     * Update the position and state of this map entity
     * @param delta delta time since the last game render
     */
	@Override
	public void update(float delta) {
		if (moveSpeed != 0) {
			setX(getX() + moveSpeed);
		}
		if (explodeTime + 1000 <= System.currentTimeMillis() && explode){
			gameScreen.removeEntity(this);
		}
	}

    /**
     * Checks collisions with other entities in the given collection of entities
     * @param entities collection of entities to search for collisions within
     */
	@Override
	public List<EntityCollision> getCollisions(EntityCollection entities) {
		List<EntityCollision> collisions = new ArrayList<EntityCollision>();
		for (Entity e : entities) {
			if (this.getX() <= 0) {
				collisions.add(new EntityCollision(this, null,
						CollisionType.MAP_ENTITY_C_LEFT_EDGE));
            }
			if (e.getType().equals("Animal")) {
				collisions.add(new EntityCollision(this, e,
						CollisionType.MAP_ENTITY_C_ANIMAL));
			}
		}
		return collisions;
	}

    /**
     * Handles collisions with other entities
     * @param e the entity this entity collided with.
     * @param entities the collection of entities which contains both this
     *                 entity and the entity it collided with.
     *
     */
	@Override
	public void handleCollision(Entity e, EntityCollection entities) {
		if (e == null) {
			entities.remove(this);
		}
	}

    /**
     * @return the type of entity this is
     */
	@Override
	public String getType() {
		return "MapEntity";
	}

    /**
     * Draw the current entity to the given sprite batch
     * @param batch sprite batch to draw to
     * @param stateTime currently unused by this entity type
     */
	@Override
	public void draw(SpriteBatch batch, float stateTime) {
		batch.draw(texture, getX(), getY(), texture.getWidth(), texture.getHeight());
	}

    /**
     * To be called any time an instance of this class is destroyed.
     */
    public void dispose() {
        texture.dispose();
    }

    /**
     * Used by the bomb map entity type, draw the explosion and play back the
     * explosion sound.
     */
    public void explode(){
    	if (this.mapEntityType.equals("bomb")){
    		explodeTime = System.currentTimeMillis();
    		texture = gameScreen.getEntityHandler().getMapEntity("explosion");
    		explode = true;
    		this.mapEntityType = "explosion";
    		if (Hunter.State.getPreferencesManager().isSoundEnabled()){
    			Sound explosion = (Sound) Gdx.audio.newSound(Gdx.files.internal("explosion.mp3"));
    			explosion.play(Hunter.State.getPreferencesManager().getVolume());
    		}
    	}
    }
    
}
