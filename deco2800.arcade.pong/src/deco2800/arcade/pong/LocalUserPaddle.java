package deco2800.arcade.pong;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.math.Vector2;

public class LocalUserPaddle extends Paddle {

	public static final int KBPADDLESPEED = 200;
	
	public LocalUserPaddle(Vector2 position) {
		super(position);
	}
	
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
