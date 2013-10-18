package deco2800.arcade.burningskies.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import deco2800.arcade.burningskies.screen.PlayScreen;

//TODO abstract?
public class BoringEnemy extends Enemy {
	
	public BoringEnemy(int health, Texture image, Vector2 pos, Vector2 dir, PlayScreen screen, PlayerShip player, long points) {
		super(health, image, pos, dir, screen, player, points);

		homing = true;
		accelIntensity = (float) 0.95;
	}
}
