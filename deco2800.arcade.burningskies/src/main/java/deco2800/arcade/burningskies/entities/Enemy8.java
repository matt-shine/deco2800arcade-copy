package deco2800.arcade.burningskies.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import deco2800.arcade.burningskies.entities.bullets.Enemy8Pattern;
import deco2800.arcade.burningskies.screen.PlayScreen;

public class Enemy8 extends Enemy {
	
	private Enemy8Pattern pattern;
	float timer = 0f;
	float interval =3f;
	float length = 0.25f;
	
	public Enemy8(int health, Texture image, Vector2 pos, Vector2 dir, PlayScreen screen, PlayerShip player, long points) {
		super(health, image, pos, dir, screen, player, points);

		homing = true;
		accelIntensity = (float) 0.80;
		pattern = new Enemy8Pattern(this, player, screen);
	}
	
	@Override
	protected void fire(float delta) {
		pattern.onRender(delta);
	}
	
	@Override
	public void onRender(float delta) {
		super.onRender(delta);
		move(delta);
		timer += delta;
		if(timer > interval) {
			pattern.start();
		}
	    if (pattern.isFiring()) {
	    	fire(delta);
	    	pattern.stop();
	    }
	    if (timer >= (interval + length)) {
	    	timer = timer % (interval + length);
	    }	
	}
}

