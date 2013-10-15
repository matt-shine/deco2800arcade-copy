package deco2800.arcade.burningskies.entities;

import com.badlogic.gdx.graphics.Texture;

public abstract class PowerUp extends Entity {
	
	float stateTime;

	// Recommend to implement animation only to one specific class  
	public PowerUp(Texture texture, float x, float y) {
		super(texture);//, 10, 7, 0.15f);
		//TODO: TEST CODE REMOVE
		setX(x);
		setY(y);
	}

	public abstract void powerOn(PlayerShip player);

}
