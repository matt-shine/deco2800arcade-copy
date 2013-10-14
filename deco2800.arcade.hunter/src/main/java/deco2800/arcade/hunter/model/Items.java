package deco2800.arcade.hunter.model;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import deco2800.arcade.hunter.platformergame.Entity;
import deco2800.arcade.hunter.platformergame.EntityCollection;
import deco2800.arcade.hunter.platformergame.EntityCollision;
import deco2800.arcade.hunter.platformergame.EntityCollision.CollisionType;

public class Items extends Entity {
	
	/**
	 * The Texture of the item
	 */
	private Texture texture;
	
	private String classType = "Items";
	
	/**
	 * Name of the item
	 */
	private String item;
	public enum Type {
		WEAPON, POWERUP
	}
	
	/**
	 * Type of item: Weapon or Power up
	 */
	private Type type;

	public Items(Vector2 pos, float width, float height, String item, Texture text) {
		super(pos, width, height);
		this.item = item;
		this.texture = text;
		if (item != "DoublePoints" && item != "ExtraLife" && item != "Invulnerability"){
			this.type = Type.WEAPON;
		}else{
			this.type = Type.POWERUP;
		}
		
	}

	
	/**
	 * Checks collisions with other entities
	 */
	@Override
	public ArrayList<EntityCollision> getCollisions(EntityCollection entities){
		ArrayList<EntityCollision> collisions = new ArrayList<EntityCollision>();
		for(Entity e : entities){
			if (this.getX() <= 0) collisions.add(new EntityCollision(this,null,CollisionType.ITEM_C_LEFT_EDGE));
		}
		return collisions;
	}
	
	/**
	 * Handles collisions with other entities
	 */
	@Override
	public void handleCollision(Entity e, EntityCollection entities){
		if (e == null) {
			entities.remove(this);
		}
	}
	
	@Override
	public void draw(SpriteBatch batch, float stateTime){
		batch.draw(texture, getX(), getY(), getWidth(), getHeight());
	}
	
	@Override
	public String getType(){
		return classType;
	}
	
	public Type getItemType(){
		return type;
	}
	
	public String getItem(){
		return item;
	}
}
