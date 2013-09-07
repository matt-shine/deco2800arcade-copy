package deco2800.arcade.burningskies.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import deco2800.arcade.burningskies.entities.Bullet.Affinity;

public class DemoPattern extends BulletPattern {
	
	private int angle = 0;
	private Texture image;
	private Texture image2;
	
	public DemoPattern(Stage stage, Ship emitter) {
		super(stage, emitter);
		image = new Texture(Gdx.files.internal("images/energy_ball_1.png"));
		image2 = new Texture(Gdx.files.internal("images/energy_ball_2.png"));
		interval = (float) 0.01;
	}
	
	public void fire(float lag, float x, float y) {
		SpiralBullet bullet = new SpiralBullet(Affinity.PLAYER, 10, null, null, new Vector2(x,y), angle, 1,  image);
		stage.addActor(bullet);
		SpiralBullet bullet2 = new SpiralBullet(Affinity.PLAYER, 10, null, null, new Vector2(x,y), angle+180, -1, image2);
		stage.addActor(bullet2);
		bullet.act(lag);
		bullet2.act(lag);
		angle = (angle+5);
	}
}
