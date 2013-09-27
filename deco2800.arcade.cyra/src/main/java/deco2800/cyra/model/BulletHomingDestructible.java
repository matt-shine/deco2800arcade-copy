package deco2800.cyra.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class BulletHomingDestructible extends BulletSimple {

	private float count;
	private int homedCount;
	
	
	public BulletHomingDestructible(float speed, float rotation, Vector2 pos,
			float width, float height, Vector2 direction, Graphic graphic) {
		super(speed, rotation, pos, width, height, direction, graphic);
		count = 0;
		homedCount = 0;
	}

	@Override
	public void handleDamage(boolean fromRight) {
		//remove bullet
		position.x = -40;
		velocity = new Vector2(0,0);
	}
	
	@Override
	public boolean isSolid() {
		return false;
	}
	
	@Override
	public Array<Enemy> advance(float delta, Ship ship, float rank) {
		count += delta;
		if (count >= 0.2f) {
			//Determine angle to the player 
			Vector2 intendedDirection = new Vector2((ship.getPosition().x+ ship.getWidth()/2) - (position.x+width/2),
					(ship.getPosition().y +ship.getHeight()/2)- (position.y+height/2));
			float intendedAngle = intendedDirection.angle();
			float currentAngle = velocity.angle();
			float angleDifference = intendedAngle - currentAngle;
			float maxRotation;
			//System.out.println("*** homedCount = "+homedCount);
			if (homedCount < 5) {
				maxRotation = 40f + 5* rank;
			} else if (homedCount > 20) {
				maxRotation = 0.1f;
			} else {
				maxRotation = 20f + 2.5f*rank;
			}
			if (angleDifference > maxRotation) {
				velocity.rotate(maxRotation);
				rotation += maxRotation;
			} else {
				velocity.rotate(angleDifference);
				rotation += angleDifference;
			}
			//System.out.println("Rotation now " + rotation);
			homedCount++;
			count = 0;
		}
		
		
		//System.out.println("Velocity="+velocity+" speed=" + speed);
		position.add(velocity.nor().mul(Gdx.graphics.getDeltaTime() * speed));
		velocity.mul(1/(Gdx.graphics.getDeltaTime()*speed));
		return null;
	}
}
