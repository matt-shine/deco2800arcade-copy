package deco2800.arcade.breakout.powerup;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

import deco2800.arcade.breakout.GameScreen;

public class SlowBall extends Powerup {
	private final String img = "slowerball.png";
	private Sprite sprite = new Sprite(new Texture(Gdx.files.classpath("imgs/" + img)));
	private GameScreen context;
	public SlowBall(GameScreen gs) {
		context = gs;
	}
	//TODO: Create a timer that resets the ball so that the ball returns to original speed
	public void applyPowerup() {
		if (context.getNumSlowBallsActivated() != 0) {
			context.incrementScore(20 * context.getLevel());
			return;
		}
		context.incrementNumSlowBallsActivated();
		if (context.getBall() != null) {
			context.getBall().slowBall();
		}
		if (context.getPowerupBall() != null) {
			context.getPowerupBall().slowBall();
		}
	}
		
	
	public Sprite getSprite() {
		return this.sprite;
	}
}
