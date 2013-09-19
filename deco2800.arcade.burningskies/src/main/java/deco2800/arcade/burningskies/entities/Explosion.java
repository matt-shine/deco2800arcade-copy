package deco2800.arcade.burningskies.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class Explosion extends EntityAnimated {
	
	float length;
	
	public Explosion(Texture walkSheet, int cols, int rows, float speed, float x, float y) {
		super(walkSheet, cols, rows, speed);
		length = rows*cols*speed;
		setX(x);
		setY(y);
	}
	
	//for testing
	public Explosion(float x, float y) {
		this(new Texture(Gdx.files.internal("images/explosion_placeholder.png")), 8, 1, 0.1f, x, y);
	}

	@Override
	public void onRender(float delta) {
		if(stateTime > length) this.remove();
	}

}
