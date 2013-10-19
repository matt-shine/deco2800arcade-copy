package deco2800.arcade.hunter.model;

import com.badlogic.gdx.math.Vector2;
import deco2800.arcade.hunter.Hunter;
import deco2800.arcade.hunter.platformergame.Entity;

public class BackgroundSprite extends Entity {
    public float speedModifier;

	public BackgroundSprite(Vector2 pos, float width, float height, float speedModifier) {
		super(pos, width, height);
        this.speedModifier = speedModifier;
	}
	
	@Override
	public void update(float delta) {
		setX(getX() - Hunter.State.playerVelocity.x * 0.1f * delta * speedModifier);
	}
}
