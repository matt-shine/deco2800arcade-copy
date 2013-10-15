package deco2800.arcade.burningskies.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public abstract class PowerUp extends Entity {
	
	float stateTime;

	// Recommend to implement animation only to one specific class  
	public PowerUp(String iconPath) {
		super(new Texture(Gdx.files.internal(iconPath)));//, 10, 7, 0.15f);
		//TODO: TEST CODE REMOVE
		setX((float) (Math.random()*1280));
		setY((float) (Math.random()*720f));
	}

	public abstract void powerOn(PlayerShip player);

}
