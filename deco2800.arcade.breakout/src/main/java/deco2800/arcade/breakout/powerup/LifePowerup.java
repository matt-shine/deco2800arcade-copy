package deco2800.arcade.breakout.powerup;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

import deco2800.arcade.breakout.GameScreen;

public class LifePowerup extends Powerup{

	private final String img = "solidbrick.png";
	private Sprite sprite = new Sprite(new Texture(Gdx.files.classpath("imgs/" + img)));
	private GameScreen context;
	
	public LifePowerup(GameScreen gs) {
		context = gs;
	}
	
	public void applyPowerup() {
		context.incrementLives(1);
	}
	
	public Sprite getSprite() {
		return this.sprite;
	}
	
}
