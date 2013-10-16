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
		int turn=context.getturns();
		int playerIndex = turn%context.gamePlayers.length;

	
		if(context.gamePlayers[playerIndex].isAI())
		{
			transitToMoving(context,playerIndex);
		}
		else
		{
			if(context.diceButton.isPressed())
			{
			 transitToMoving(context,playerIndex);
			}
		}
	}

	/**
	 * @param context
	 * @param playerIndex 
	 */
	public void transitToMoving(SnakeLadder context, int playerIndex) {
		context.getDice(playerIndex).rollDice();
		 context.gamePlayers[playerIndex].initializeVelocity();
		 context.gameState = new MovingState();
		 context.statusMessage = null;     		
		 context.gamePlayers[playerIndex].getDnumber(context.getDice(playerIndex).getDiceNumber());
	}

}
