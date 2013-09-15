package deco2800.arcade.hunter.model;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.math.Vector2;

import deco2800.arcade.platformergame.model.Entity;

public class Items extends Entity {

	private Animation anim;
	
	private enum Type {
		WEAPON, ITEM
	}

	private Type type;

	public Items(Vector2 pos, float width, float height, boolean weapon) {
		super(pos, width, height);
		if (weapon)
			type = Type.WEAPON;
		else
			type = Type.ITEM;
		loadImage();
	}

	private void loadImage() {
		if (type == Type.WEAPON){
			//Load a random weapon 
		}else{
			//Load a random item
		}
	}
	
	
	
}
