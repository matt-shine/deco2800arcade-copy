package deco2800.arcade.hunter.model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import deco2800.arcade.hunter.platformergame.Entity;
import deco2800.arcade.hunter.platformergame.EntityCollection;
import deco2800.arcade.hunter.platformergame.EntityCollision;
import deco2800.arcade.hunter.platformergame.EntityCollision.CollisionType;
import deco2800.arcade.hunter.screens.GameScreen;

import java.util.ArrayList;

public class MapEntity extends Entity {

	private Texture text;

	private String classType = "MapEntity";

	private int moveSpeed;

	private String Type;

	private GameScreen gameScreen;

	public MapEntity(Vector2 pos, float width, float height, String file,
			Texture texture, GameScreen game) {
		super(pos, width, height);
		this.text = texture;
		if (file.equals("arrow")) {
			moveSpeed = 40;
		} else {
			moveSpeed = 0;
		}
		this.Type = file;
		this.gameScreen = game;
	}

	public String getEntityType() {
		return Type;
	}

	@Override
	public void update(float delta) {
		if (moveSpeed != 0) {
			setX(getX() + moveSpeed);
		}
	}

	/**
	 * Checks for collisions
	 */
	@Override
	public ArrayList<EntityCollision> getCollisions(EntityCollection entities) {
		ArrayList<EntityCollision> collisions = new ArrayList<EntityCollision>();
		for (Entity e : entities) {
			if (this.getX() <= 0)
				collisions.add(new EntityCollision(this, null,
						CollisionType.MAP_ENTITY_C_LEFT_EDGE));
			if (e.getType().equals("Animal")) {
				collisions.add(new EntityCollision(this, e,
						CollisionType.MAP_ENTITY_C_ANIMAL));
			}
		}
		return collisions;
	}

	/**
	 * Handles Collisions
	 */
	@Override
	public void handleCollision(Entity e, EntityCollection entities) {
		if (e == null) {
			entities.remove(this);
		}
	}

	@Override
	public String getType() {
		return classType;
	}

	@Override
	public void draw(SpriteBatch batch, float stateTime) {
		batch.draw(text, getX(), getY(), 64, 32);
	}

}
