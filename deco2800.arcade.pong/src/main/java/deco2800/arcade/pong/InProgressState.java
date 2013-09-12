package deco2800.arcade.pong;

import com.badlogic.gdx.Gdx;


public class InProgressState extends GameState {

	@Override
	public void handleInput(Pong context) {
		//Move the left paddle (mouse)
    	context.getLeftPaddle().update(context.getBall());
    	
    	//Move the right paddle (automatic)
    	context.getRightPaddle().update(context.getBall());
    	
    	//Move the ball
    	//ball.bounds.x -= ball.velocity.x * Gdx.graphics.getDeltaTime();
    	context.getBall().move(Gdx.graphics.getDeltaTime());
    	//If the ball hits a paddle then bounce it
    	if (context.getBall().bounds.overlaps(context.getLeftPaddle().bounds) || context.getBall().bounds.overlaps(context.getRightPaddle().bounds)) {
    		context.getBall().bounceX();
    	}
    	//Bounce off the top or bottom of the screen
    	if (context.getBall().bounds.y <= 0 || context.getBall().bounds.y >= context.SCREENHEIGHT-Ball.WIDTH) {
    		context.getBall().bounceY();
    	}
    	
    	//If the ball gets to the left edge then player 2 wins
    	if (context.getBall().bounds.x <= 0) {
    		context.endPoint(1);
    	} else if (context.getBall().bounds.x + Ball.WIDTH > context.SCREENWIDTH) { 
    		//If the ball gets to the right edge then player 1 wins
    		context.endPoint(0);
    	}
	}

}
