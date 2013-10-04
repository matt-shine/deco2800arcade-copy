package deco2800.arcade.breakout.powerup;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

import deco2800.arcade.breakout.GameScreen;

public class DecreasePaddle extends Powerup{

	private final String img = "decreasepaddle.png";
	private Sprite sprite = new Sprite(new Texture(Gdx.files.classpath("imgs/" + img)));
	private GameScreen context;
	public DecreasePaddle(GameScreen gs) {
		context = gs;
	}
	//TODO: Create a timer so that the paddle returns to the original size after a certain time has passed
	public void applyPowerup() {
		System.out.println("Decrease paddle");
		//context.getPaddle().decreaseSize();
		context.getPaddle().setWidth(context.getPaddle().getWidth()/2);
	}
	
	public Sprite getSprite() {
		return this.sprite;
	}
}
