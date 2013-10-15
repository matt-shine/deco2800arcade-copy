package deco2800.arcade.burningskies.entities.bullets;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import deco2800.arcade.burningskies.entities.PlayerShip;
import deco2800.arcade.burningskies.entities.Ship;

public class BombBullet extends Bullet {

	public BombBullet(Affinity affinity, int damage, Ship parent,
			PlayerShip player, Vector2 initialPosition, float direction, Texture image) {
		super(affinity, damage, parent, player, initialPosition, direction, image);
		velocity = new Vector2(400, 0);
	}

}
