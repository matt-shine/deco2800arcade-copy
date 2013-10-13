package deco2800.cyra.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class BulletSimple extends Enemy {

	public static enum Graphic {
		FIRE
	}
	
	private Graphic graphic;
	
	public BulletSimple(float speed, float rotation, Vector2 pos, float width,
			float height, Vector2 direction, Graphic graphic) {
		super(speed, rotation, pos, width, height);
		this.graphic = graphic;
		this.velocity = direction;
		advanceDuringScenes=true;
	}

	

	@Override
	public boolean isSolid() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void handleTopOfMovingPlatform(MovablePlatform movablePlatform) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void handleXCollision(Rectangle tile) {
		// remove the bullet
		position.x = -40;
		velocity = new Vector2(0,0);
		
	}

	@Override
	public void handleYCollision(Rectangle tile, boolean onMovablePlatform,
			MovablePlatform movablePlatform) {
		//remove the bullet
		position.x = -40;
		velocity = new Vector2(0,0);
		
	}

	@Override
	public void handleNoTileUnderneath() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void handleDamage(boolean fromRight) {
		;
	}
	
	public Graphic getGraphic() {
		return graphic;
	}

	@Override
	public Array<Enemy> advance(float delta, Player ship, float rank, OrthographicCamera cam) {
		//System.out.println("Velocity="+velocity+" speed=" + speed);
		position.add(velocity.nor().mul(Gdx.graphics.getDeltaTime() * speed));
		velocity.mul(1/(Gdx.graphics.getDeltaTime()*speed));
		return null;
	}

}
