package deco2800.arcade.hunter.model;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import deco2800.arcade.platformergame.model.Entity;
import deco2800.arcade.platformergame.model.EntityCollection;
import deco2800.arcade.platformergame.model.EntityCollision;
import deco2800.arcade.platformergame.model.EntityCollision.CollisionType;

public class MapEntity extends Entity{

	private Texture text;
	
	private String classType = "MapEntity";
	
	public MapEntity(Vector2 pos, float width, float height) {
		super(pos, width, height);
		text = new Texture("textures/spike trap.png");
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
		}
		return collisions;
	}

	/**
	 * Handles Collisions
	 */
	@Override
	public void handleCollision(Entity e, EntityCollection entities) {
		if (e == null)
			entities.remove(this);
	}
	
	@Override
	public String getType(){
		return classType;
	}
	
	@Override
	public void draw(SpriteBatch batch, float stateTime){
		batch.draw(text,getX(),getY(),128,64);
	}

}
