package deco2800.arcade.snakeLadderModel;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import deco2800.arcade.snakeLadder.SnakeLadder;

public class ReadyState extends GameState {

	@Override
	public void handleInput(SnakeLadder context) {
		if (Gdx.input.isTouched()) {
    		//context.startPoint();
    		
    		context.getDice().rollDice();	
    		int i=context.getturns();
    		if(i%2==0)
    		{
    		 context.gamePlayer.initializeVelocity();
    		 context.gameState = new InProgressState();
     		 context.statusMessage = null;     		
     		 context.gamePlayer.getDnumber(context.getDice().getDiceNumber());
   
    		}
    		else
    		{
    		 context.gamePlayer.velocity.x=0;
    		 context.AIPlayer.initializeVelocity();
    		 context.gameState = new InProgressState();
     		 context.statusMessage = null;
     		 context.AIPlayer.getDnumber2(context.getDice().getDiceNumber());
    		
    		}
    	

    	}
	}

}
