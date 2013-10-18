package deco2800.arcade.snakeLadderGameState;

import deco2800.arcade.snakeLadder.SnakeLadder;
import deco2800.arcade.snakeLadderModel.GamePlayer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent;

public class WaitingState extends GameState {

	@Override
	public void handleInput(SnakeLadder context) {	
		int playerIndex=context.getturns();
		GamePlayer gamePlayer = context.gamePlayers[playerIndex];
		if(gamePlayer.getStopForNumOfRound()==0)
		{
			if(gamePlayer.isAI())
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
		else
		{
			context.taketurns();
			gamePlayer.setStopForNumOfRound(gamePlayer.getStopForNumOfRound()-1);
			context.gameState = new WaitingState();
		}
	}

	/**
	 * @param context
	 * @param playerIndex 
	 */
	public void transitToMoving(SnakeLadder context, int playerIndex) {
		 context.gamePlayers[playerIndex].initializeVelocity();
		 context.getDice(playerIndex).rollDice();
		 context.gameState = new MovingState();
		 context.statusMessage = null;     		
		 context.gamePlayers[playerIndex].getDnumber(context.getDice(playerIndex).getDiceNumber());
	}

}
