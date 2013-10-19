package deco2800.arcade.burningskies.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import deco2800.arcade.burningskies.entities.bullets.Enemy8Pattern;
import deco2800.arcade.burningskies.screen.PlayScreen;

public class Enemy8 extends Enemy {
	
	private Enemy8Pattern pattern;
	
	float interval =3f;
	float length;
	float timer = 0;
	
	public Enemy8(int health, Texture image, Vector2 pos, Vector2 dir, PlayScreen screen, 
			PlayerShip player, long points, int difficulty) {
		super(health, image, pos, dir, screen, player, points, difficulty);

		homing = true;
		if (difficulty < 3) {
			accelIntensity = 0.7f;
		} else {
			accelIntensity = 0.2f +  0.2f*(difficulty%5);
		}	
		pattern = new Enemy8Pattern(this, player, screen);
		
		this.length = 0.12f * difficulty;
	}
	
	@Override
	protected void fire(float delta) {
		pattern.onRender(delta);
	}
	
	@Override
	public void onRender(float delta) {
		super.onRender(delta);
		timer -= delta;
		if(timer <= 0) {
			if(pattern.isFiring()) {
				pattern.stop();
				timer += interval;
			} else {
				pattern.start();
				timer += length;
			}
		}
	}
}

