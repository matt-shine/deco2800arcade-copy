package deco2800.arcade.hunter.model;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import deco2800.arcade.hunter.Hunter.Config;
import deco2800.arcade.hunter.platformergame.Entity;
import deco2800.arcade.hunter.platformergame.EntityCollection;
import deco2800.arcade.hunter.platformergame.EntityCollision;
import deco2800.arcade.hunter.platformergame.EntityCollision.CollisionType;

public class Items extends Entity {
	
	/**
	 * The Texture of the item
	 */
	private Texture texture;
	
	/**
	 * String array of possible power ups
	 */
	private String[] powerups = {"DoublePoints", "ExtraLife", "Invulnerability"};
	
	/**
	 * String array of possible weapons
	 */
	private String[] weapons = {"KnifeandFork","Spear","Trident"};
	
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

	public Items(Vector2 pos, float width, float height, boolean weapon) {
		super(pos, width, height);
		//Randomises item depending on type
		if (weapon){
			type = Type.WEAPON;
			switch(Config.randomGenerator.nextInt(3)){
			case 0:item = weapons[0];
				break;
			case 1:item = weapons[1];
				break;
			case 2:item = weapons[2];
				break;
			}
		}
		else{
			type = Type.POWERUP;
			switch(Config.randomGenerator.nextInt(3)){
			case 0:item = powerups[0];
				break;
			case 1:item = powerups[1];
				break;
			case 2:item = powerups[2];
				break;
			}
		}
		loadImage();
	}

	/**
	 * Loads the texture image for the item
	 */
	private void loadImage() {
		texture = new Texture("textures/Items/" + item + ".png");
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
