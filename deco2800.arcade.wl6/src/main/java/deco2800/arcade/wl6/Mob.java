package deco2800.arcade.wl6;

import com.badlogic.gdx.math.Vector2;

public class Mob extends Doodad {
	
	private float angle = 0;
	private Vector2 vel = new Vector2();
    private GameModel game;
	
	public Mob(int uid) {
		super(uid);
	}
	
	@Override
	public void tick(GameModel game) {
        this.game = game;
        setPos(getPos(), vel);
	}

    // Checks to see if the mob is trying to go through an obstructing block
    public void setPos(Vector2 pos, Vector2 vel) {
        int x = (int) (pos.x + vel.x * 2);
        int y = (int) (pos.y + vel.y * 2);
        if (WL6Meta.hasObscuringBlockAt(x, y, game.getMap()) &&
                !WL6Meta.hasDoorAt(x, y, game.getMap())) {
            setPos(new Vector2(pos.x, pos.y));
        } else {
            setPos(new Vector2(pos.x + vel.x, pos.y + vel.y));
        }
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
