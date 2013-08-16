package deco2800.arcade.breakout;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * 
 * @author Naveen
 *
 */

public abstract class Paddle{
	
	//public static float width = 64f;
	public static float width = 128f;
	public static float height = 10f;

	
	Rectangle paddleShape = new Rectangle();
	
	public Paddle(Vector2 position) {
		this.paddleShape.x = position.x;
		this.paddleShape.y = position.y;
		this.paddleShape.width = width;
		this.paddleShape.height = height;
	}
	
	public void movement(float horizontal){
		paddleShape.x += horizontal;
	}
	
	
	public void render(ShapeRenderer render){
		render.setColor(Color.RED);
		render.filledRect(paddleShape.x, paddleShape.y, paddleShape.width, paddleShape.height);
	}
	
	public void setPosition(Vector2 iniPosition){
		paddleShape.x = iniPosition.x;
		paddleShape.y = iniPosition.y;
	}
	
	public void update(PongBall ball){
		if(paddleShape.x > Breakout.SCREENWIDTH - paddleShape.width)
			paddleShape.x = Breakout.SCREENWIDTH - paddleShape.width;
		else if(paddleShape.x < 0)
			paddleShape.x = 0;
	}
}