package deco2800.arcade.checkers;

import com.badlogic.gdx.math.Vector2;

/**
 * Takes user input for paddle in pong.
 * @see Paddle
 */
public class UserPieces extends Pieces {

	public static final int KBPADDLESPEED = 200;
	
	/**
	 * Sets the user's paddle position
	 * @param position
	 */
	public UserPieces(Vector2 position) {
		super(position);
	}

	/**
	* Updates direction of ball based on the direction the paddle was moving
	*/
	
//	@Override
//	public void update(Ball ball) {
//		super.update(ball);
 //   	//Move the left paddle (mouse)
   // 	if (Gdx.input.isTouched()) {
    //		Vector2 touchPos = new Vector2();
    	//	touchPos.set(Gdx.input.getX(), Gdx.input.getY());
    //		bounds.y = -touchPos.y - bounds.height / 2 + Checkers.SCREENHEIGHT;
    	//}

    	//Move the left paddle (keyboard)
   // 	if(Gdx.input.isKeyPressed(Keys.UP)) move(KBPADDLESPEED * Gdx.graphics.getDeltaTime());//leftPaddle.bounds.y += KBPADDLESPEED * Gdx.graphics.getDeltaTime();
    //	if(Gdx.input.isKeyPressed(Keys.DOWN)) move(-KBPADDLESPEED * Gdx.graphics.getDeltaTime());//leftPaddle.position.y -= KBPADDLESPEED * Gdx.graphics.getDeltaTime();
	//}

}
