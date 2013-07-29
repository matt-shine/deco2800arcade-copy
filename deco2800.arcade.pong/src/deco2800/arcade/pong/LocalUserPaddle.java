package deco2800.arcade.pong;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.math.Vector2;

/**
 * User paddle class
 * @author s4291158
 *
 */
public class LocalUserPaddle extends Paddle {
	
	//Base speed of user paddle
	public static final int KBPADDLESPEED = 200;
	
	/**
	 * Basic constructor for user paddle
	 * @param position
	 */
	public LocalUserPaddle(Vector2 position) {
		super(position);
	}
	
	/**
	 * Updates user paddle position
	 * @param ball
	 */
	@Override
	public void update(Ball ball) {
    	//Move the left paddle (mouse)
    	if (Gdx.input.isTouched()) {
    		Vector2 touchPos = new Vector2();
    		touchPos.set(Gdx.input.getX(), Gdx.input.getY());
    		//camera.unproject(touchPos);
    		bounds.y = touchPos.y - bounds.height / 2;
    	}

    	//Move the left paddle (keyboard)
    	if(Gdx.input.isKeyPressed(Keys.UP)) move(KBPADDLESPEED * Gdx.graphics.getDeltaTime());//leftPaddle.bounds.y += KBPADDLESPEED * Gdx.graphics.getDeltaTime();
    	if(Gdx.input.isKeyPressed(Keys.DOWN)) move(-KBPADDLESPEED * Gdx.graphics.getDeltaTime());//leftPaddle.position.y -= KBPADDLESPEED * Gdx.graphics.getDeltaTime();


	}

}
