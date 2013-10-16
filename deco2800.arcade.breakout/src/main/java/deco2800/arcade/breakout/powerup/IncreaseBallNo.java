package deco2800.arcade.breakout.powerup;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import deco2800.arcade.breakout.GameScreen;

public class IncreaseBallNo extends Powerup{
	private final String img = "increaseballno.png";
	private Sprite sprite = new Sprite(new Texture(Gdx.files.classpath("imgs/" + img)));
	private GameScreen context;
	public IncreaseBallNo(GameScreen gs) {
		context = gs;
	}
	
	public void applyPowerup() {
		System.out.println("Increase # of balls");
		Vector2 position = new Vector2(context.getPaddle().getPaddleX(), 
				context.getPaddle().getPaddleY());
		context.createNewBall(position);
	}
	
	public Sprite getSprite() {
		return this.sprite;
	}
}
