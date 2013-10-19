package deco2800.arcade.burningskies.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import deco2800.arcade.burningskies.entities.bullets.Level1EnemyPattern;
import deco2800.arcade.burningskies.screen.PlayScreen;

//TODO abstract?
public class Level1Enemy extends Enemy {
	
	private Level1EnemyPattern pattern;
	
	public Level1Enemy(int health, Texture image, Vector2 pos, Vector2 dir, PlayScreen screen, 
			PlayerShip player, long points, int difficulty) {
		super(health, image, pos, dir, screen, player, points, difficulty);

		homing = true;
		if (difficulty < 3) {
			accelIntensity = 0.65f;
		} else {
			accelIntensity = 0.35f +  0.1f*(difficulty%5);
		}
		pattern = new Level1EnemyPattern(this, player, screen, difficulty);
		pattern.start();
	}
	
	@Override
	protected void fire(float delta) {
		pattern.onRender(delta);
	}
}
