package deco2800.arcade.pong;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Paddle {

	static final float WIDTH = 10f;
	static final float INITHEIGHT = 64f;
	
	//Vector2 position = new Vector2();
	Rectangle bounds = new Rectangle();
	
	public Paddle(Vector2 position) {
//		this.position.x = position.x;
//		this.position.y = position.y;
		this.bounds.x = position.x;
		this.bounds.y = position.y;
		this.bounds.width = WIDTH;
		this.bounds.height = INITHEIGHT;
	}
	
	public void move(float y) {
		bounds.y += y;
	}
	
	public void setPosition(Vector2 newPosition) {
		bounds.x = newPosition.x;
		bounds.y = newPosition.y;
	}
	
	public void move(Vector2 delta) {
		bounds.x += delta.x;
		bounds.y += delta.y;
	}
	
}
