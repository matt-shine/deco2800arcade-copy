package deco2800.arcade.burningskies.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class PowerUp extends EntityAnimated {
	
	float stateTime;

	// Recommend to implement animation only to one specific class  
	public PowerUp() {
		super(new Texture(Gdx.files.internal("items/test_ani.png")), 10, 7);
		//TODO: TEST CODE REMOVE
		setX(500);
		setY(500);
	}

	@Override
	public void move(float delta) {
		// TODO Auto-generated method stub
		
	}

}
