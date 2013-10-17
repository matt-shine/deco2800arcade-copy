package deco2800.arcade.pong;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.math.Vector2;

import deco2800.arcade.client.ArcadeSystem;

/**
 * Takes user input for paddle in pong.
 * @see Paddle
 */
public class LocalUserPaddle extends Paddle {

	public static final int KBPADDLESPEED = 200;
    
	/**
	 * Sets the user's paddle position
	 * @param position
	 */
	public LocalUserPaddle(Vector2 position) {
		super(position);
	}

    private static int sign(double x) {
        if(x == 0) return 0;
        else if(x > 0) return 1;
        else return -1;
    }

	/**
	* Updates direction of ball based on the direction the paddle was moving
	*/
	
	@Override
	public void update(Ball ball) {
		super.update(ball);
        direction = 0;
    	//Move the left paddle (mouse)
    	if (Gdx.input.isTouched()) {
    		Vector2 touchPos = new Vector2();
    		touchPos.set(Gdx.input.getX(), Gdx.input.getY());
            double prevY = bounds.y;
    		bounds.y = -touchPos.y - bounds.height / 2 + Pong.SCREENHEIGHT;
            direction += sign(bounds.y - prevY);
            ArcadeSystem.getCurrentGame().sendStateUpdate();
        }

    	//Move the left paddle (keyboard)
    	if(Gdx.input.isKeyPressed(Keys.UP)) {
            move(KBPADDLESPEED * Gdx.graphics.getDeltaTime());//leftPaddle.bounds.y += KBPADDLESPEED * Gdx.graphics.getDeltaTime();
            direction -= 1;
        }

    	if(Gdx.input.isKeyPressed(Keys.DOWN)) {
            move(-KBPADDLESPEED * Gdx.graphics.getDeltaTime());//leftPaddle.position.y -= KBPADDLESPEED * Gdx.graphics.getDeltaTime();
            direction += 1;
        }
	}

}
