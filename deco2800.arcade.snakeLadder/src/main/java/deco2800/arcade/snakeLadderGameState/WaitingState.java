package deco2800.arcade.snakeLadderGameState;

import deco2800.arcade.snakeLadder.SnakeLadder;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent;

public class WaitingState extends GameState {

	@Override
	public void handleInput(SnakeLadder context) {
		
    		//context.startPoint();
    		
    			
    		int turn=context.getturns();
    		if(turn%2==0)
    		{
    			if(context.diceButton.isPressed())
    			{
    			 context.getDice(0).rollDice();
   	    		 context.gamePlayers[0].initializeVelocity();
   	    		 context.gameState = new MovingState();
   	     		 context.statusMessage = null;     		
   	     		 context.gamePlayers[0].getDnumber(context.getDice(0).getDiceNumber());
    			}
        
//    			if (Gdx.input.isTouched()) {
//    			 context.getDice(0).rollDice();
//	    		 context.gamePlayers[0].initializeVelocity();
//	    		 context.gameState = new MovingState();
//	     		 context.statusMessage = null;     		
//	     		 context.gamePlayers[0].getDnumber(context.getDice(0).getDiceNumber());
//    			}
    		}
    		else if(turn%2==1)
    		{
    			context.getDice(1).rollDice();
	    		context.gamePlayers[0].getVelocity().x=0;
	    		context.gamePlayers[1].initializeVelocity();
	    		context.gameState = new MovingState();
	     		context.statusMessage = null;
	     		context.gamePlayers[1].getDnumber(context.getDice(1).getDiceNumber());
    		}
		

	}

}
