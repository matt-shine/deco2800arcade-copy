package deco2800.arcade.burningskies.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public class DemoBullet extends Bullet {

	public DemoBullet(Affinity affinity, int damage, Ship parent,
			Player player, Vector2 initialPosition, float direction, Texture image) {
		super(affinity, damage, parent, player, initialPosition, direction, image);
		velocity = new Vector2(150,150);
		setRotation(direction);
	}

}
