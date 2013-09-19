package deco2800.arcade.burningskies.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class PowerUp extends EntityAnimated {
	
	float stateTime;

	// Recommend to implement animation only to one specific class  
	public PowerUp() {
		super(new Texture(Gdx.files.internal("images/misc/test_ani.png")), 10, 7, 0.15f);
		//TODO: TEST CODE REMOVE
		setX(500);
		setY(500);
	}

	@Override
	public void onRender(float delta) {
		// TODO Auto-generated method stub
		
	}

}
