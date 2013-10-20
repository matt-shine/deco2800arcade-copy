package deco2800.arcade.cyra.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class BulletHomingDestructible extends BulletSimple {

	private float count;
	private int homedCount;
	
	
	public BulletHomingDestructible(float speed, Vector2 pos,
			float width, float height, Vector2 direction, Graphic graphic) {
		//uses the rotation from the direction 
		super(speed, direction.angle(), pos, width, height, direction, graphic);
		count = 0;
		homedCount = 0;
		advanceDuringScenes=true;
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
	public Array<Enemy> advance(float delta, Player ship, float rank, OrthographicCamera cam) {
		count += delta;
		if (count >= 0.2f) {
			//Determine angle to the player 
			Vector2 intendedDirection = new Vector2((ship.getPosition().x+ ship.getWidth()/2) - (position.x+width/2),
					(ship.getPosition().y +ship.getHeight()/2)- (position.y+height/2));
			float intendedAngle = intendedDirection.angle();
			float currentAngle = velocity.angle();
			float angleDifference = intendedAngle - currentAngle;
			if (Math.abs(angleDifference) > 180) {
				//System.out.println("OVER 180!!!!!!!!!!!!!!!!");
				angleDifference = -angleDifference/Math.abs(angleDifference) * Math.abs(180-angleDifference);
			}
			float maxRotation;
			//System.out.println("*** homedCount = "+homedCount);
			if (homedCount < 5) {
				maxRotation = 40f + 5* rank;
			} else if (homedCount > 20) {
				maxRotation = 0.1f;
			} else {
				maxRotation = 20f + 2.5f*rank;
			}
			if (Math.abs(angleDifference) > maxRotation) {
				if (angleDifference >0) {
					velocity.rotate(maxRotation);
				} else {
					velocity.rotate(-maxRotation);
				}
				rotation += maxRotation;
			} else {
				//if (angleDifference > 0) {
					velocity.rotate(angleDifference);
				//} else {
					velocity.rotate(-angleDifference);
				//}
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
