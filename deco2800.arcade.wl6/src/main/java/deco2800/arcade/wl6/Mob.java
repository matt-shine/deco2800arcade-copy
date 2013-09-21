package deco2800.arcade.wl6;

import com.badlogic.gdx.math.Vector2;

public class Mob extends Doodad {
	
	private float angle = 0;
	private Vector2 vel = new Vector2();
	
	public Mob() {
		super();
	}
	
	@Override
	public void tick(GameModel game) {
		Vector2 pos = getPos();
		this.setPos(new Vector2(pos.x + vel.x, pos.y + vel.y));
	}
	
	public float getAngle() {
		return angle;
	}

	public void setAngle(float angle) {
		this.angle = angle;
	}
	
	public void canSee(Doodad d) {
		//TODO
	}

	public Vector2 getVel() {
		return vel.cpy();
	}

	public void setVel(Vector2 vel) {
		this.vel = vel.cpy();
	}
	
}
