package deco2800.arcade.burningskies.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import deco2800.arcade.burningskies.entities.Bullet.Affinity;

public class DemoPattern extends BulletPattern {
	
	private int angle = 0;
	private Texture image;
	
	public DemoPattern(Stage stage, Ship emitter) {
		super(stage, emitter);
		image = new Texture(Gdx.files.internal("images/placeholder_bullet.png"));
		interval = (float) 0.01;
	}
	
	public void fire(float lag) {
		int x = 0, y = 0;
		if(emitter == null) {
			x = (int) (stage.getWidth()/2);
			y = (int) (stage.getHeight()/2);
		}
		DemoBullet bullet = new DemoBullet(Affinity.PLAYER, 10, null, null, new Vector2(x,y), angle, image);
		stage.addActor(bullet);
		angle = (angle+5)%360;
		bullet.act(lag);
	}

}
