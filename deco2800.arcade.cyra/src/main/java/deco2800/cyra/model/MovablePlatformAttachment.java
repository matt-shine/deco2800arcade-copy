package deco2800.cyra.model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public class MovablePlatformAttachment extends MovablePlatform {

	private MovableEntity parent;
	private Vector2 targetOffset;
	public MovablePlatformAttachment(Texture texture, float width,
			float height, MovableEntity parent, Vector2 targetOffset) {
		super(texture, new Vector2(parent.position.x+targetOffset.x, parent.position.y+targetOffset.y), width, height, 0);
		this.parent = parent;
		this.targetOffset = targetOffset;
		
	}
	
	@Override
	public void update(Ship ship) {
		super.update(ship);
		position = new Vector2(parent.position.x+targetOffset.x, parent.position.y+targetOffset.y);
	}

}
