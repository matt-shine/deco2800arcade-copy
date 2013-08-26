package deco2800.arcade.platformergame.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public abstract class Player extends MovableEntity{
	
	/*Need to initialise these? TODO*/
	public static final float SPEED = 0;
	public static final float JUMP_VELOCITY = 0;
	public static final float WIDTH = 0;
	public static final float HEIGHT = 0;
	public static final float JUMP_TIME = 0;
	public static final float GRAVITY = 0;
	public static final float MAX_FALL_VELOCITY = 0;
	
	private static float rotation;
	private boolean facingRight;
	float jumpTime;

	
	
	public Player(Vector2 position) {
		super(SPEED, rotation, position, WIDTH, HEIGHT);
		// TODO Auto-generated constructor stub
	}

	public void resetJumpTime() {
		jumpTime = JUMP_TIME;
	}
	
	public void clearJumpTime() {
		jumpTime = 0f;
	}
	
	/* ----- Getter methods ----- */
	public Rectangle getProjectionRect() {
		Rectangle rect = new Rectangle (position.x, position.y, width, height);
		rect.x += velocity.x*Gdx.graphics.getDeltaTime();
		rect.y += velocity.y*Gdx.graphics.getDeltaTime();

		return rect;
	}
	
	public Rectangle getXProjectionRect() {
		Rectangle rect = new Rectangle (position.x, position.y, width, height);
		rect.x += velocity.x*Gdx.graphics.getDeltaTime();

		return rect;
	}
	
	public Rectangle getYProjectionRect() {
		Rectangle rect = new Rectangle (position.x, position.y, width, height);
		rect.y += velocity.y*Gdx.graphics.getDeltaTime();
	
		return rect;
	}
	
	public boolean isFacingRight() {
		return facingRight;
	}
	
	/* ----- Setter methods ----- */
	public void setFacingRight(boolean right) {
		facingRight = right;
	}
	
	public void update() {
		super.update();
	}
}