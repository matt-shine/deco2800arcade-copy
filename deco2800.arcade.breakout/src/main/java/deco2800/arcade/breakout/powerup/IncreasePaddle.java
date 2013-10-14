package deco2800.arcade.breakout.powerup;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

import deco2800.arcade.breakout.GameScreen;

public class IncreasePaddle extends Powerup{

	private final String img = "increasepaddle.png";
	private Sprite sprite = new Sprite(new Texture(Gdx.files.classpath("imgs/" + img)));
	private GameScreen context;
	public IncreasePaddle(GameScreen gs) {
		context = gs;
	}
	//TODO: Create a timer so that the paddle returns to the original size after a certain time has passed 
	public void applyPowerup() {
		System.out.println("Increase paddle");
		//context.getPaddle().increaseSize();
		context.getPaddle().setWidth(context.getPaddle().getWidth()*2);
	}
	
	public Sprite getSprite() {
		return this.sprite;
	}
}
