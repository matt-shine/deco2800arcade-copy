package deco2800.arcade.hunter.model;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import deco2800.arcade.platformergame.model.Entity;
import deco2800.arcade.platformergame.model.EntityCollection;
import deco2800.arcade.platformergame.model.EntityCollision;
import deco2800.arcade.platformergame.model.EntityCollision.CollisionType;

public class Items extends Entity {

	private Texture texture;
	
	
	private String[] powerups = {"DoublePoints", "ExtraLife", "Invulnerability"};
	private String[] weapons = {"KnifeAndFork","Spear","Trident"};
	
	private String item;
	private enum Type {
		WEAPON, ITEM
	}

	private Type type;

	public Items(Vector2 pos, float width, float height, boolean weapon) {
		super(pos, width, height);
		Random rnd = new Random();
		if (weapon){
			type = Type.WEAPON;
			switch(rnd.nextInt(2)){
			case 0:item = weapons[0];
			case 1:item = weapons[1];
			case 2:item = weapons[2];
			}
		}
		else{
			type = Type.ITEM;
			switch(rnd.nextInt(2)){
			case 0:item = powerups[0];
			case 1:item = powerups[1];
			case 2:item = powerups[2];
			}
		}
		loadImage();
	}

	private void loadImage() {
		texture = new Texture("textures/Items" + item + ".png");
	}
	
	@Override
	public ArrayList<EntityCollision> getCollisions(EntityCollection entities){
		ArrayList<EntityCollision> collisions = new ArrayList<EntityCollision>();
		for(Entity e:entities){
			if (this.getX() <= 0) collisions.add(new EntityCollision(this,null,CollisionType.ITEM_C_LEFT_EDGE));
		}
		return collisions;
	}
	
	@Override
	public void handleCollision(Entity e){
		if (e == null) {
			System.out.println("Destroy the animal");
		}
	}
	
}
