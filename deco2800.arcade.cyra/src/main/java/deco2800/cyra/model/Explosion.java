package deco2800.cyra.model;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class Explosion extends Enemy {
	
	public static final float WIDTH = 2f;
	public static final float HEIGHT = 2f;

	private float count;
	private int frame;
	
	public Explosion(Vector2 pos) {
		this(pos, new Vector2(0,0));
		
	}
	
	public Explosion(Vector2 pos, Vector2 vel) {
		super(0, 0, pos, WIDTH, HEIGHT);
		
		count = 0f;
		frame = 0;
		this.velocity = vel;
	}

	@Override
	public Array<Enemy> advance(float delta, Player ship, float rank) {
		position.add(new Vector2(velocity).mul(delta));
		count += delta;
		if (count > 0.1f) {
			frame++;
			count = 0f;
			if (frame == 6) {
				isDead = true;
			}
		}
		return null;
	}

	@Override
	public boolean isSolid() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void handleNoTileUnderneath() {
		// TODO Auto-generated method stub
		
	}
	
	public Array<Rectangle> getPlayerDamageBounds() {
		return new Array<Rectangle>();
	}
	
	public int getFrame() {
		return frame;
	}
	
	@Override
	public void handleDamage(boolean fromRight) {
		;
	}
	
}
