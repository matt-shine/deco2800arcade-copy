package deco2800.arcade.snakeLadderGameState;

import com.badlogic.gdx.Gdx;

import deco2800.arcade.snakeLadder.SnakeLadder;
import deco2800.arcade.snakeLadderModel.GamePlayer;

public class MovingState extends GameState {

	@Override
	public void handleInput(SnakeLadder context) {
		int turn=context.getturns();
		int playerIndex = turn%context.gamePlayers.length;
		//int playerIndex=turn;
		movePlayer(context,context.gamePlayers[playerIndex]);	    	
	}

	/**
	 * @param context
	 */
	private void movePlayer(SnakeLadder context, GamePlayer gamePlayer) {
		gamePlayer.move(Gdx.graphics.getDeltaTime());
		int turn=context.getturns();
		int playerIndex = turn%context.gamePlayers.length;
		//player stop move once he get relatively closed to desitination position
		if(Math.abs(context.getMap().getTileList()[gamePlayer.newposition()].getCoorX() - gamePlayer.getBounds().x) <(1f)&&Math.abs(context.getMap().getTileList()[gamePlayer.newposition()].getCoorY() - gamePlayer.getBounds().y) <(1f))
		{				
				gamePlayer.reset();
				if(gamePlayer.newposition()==99)
				{
					context.gameState=new GameOverState();	
					//check if the player is local player instead of AI
					if (playerIndex == 0) {
						//add one achievement to winGame achievement
				    	context.incrementAchievement("snakeLadder.winGame");
				    }
				}
				else 
				{
					// No winner yet, get ready for another point
					context.gameState = new RuleExcutingState();					
				}
		}

		//If the player reaches the end of each line , move up to another line
		if ((gamePlayer.getBounds().x >= (600-20f) || gamePlayer.getBounds().x <=0))
		{
			gamePlayer.moveUp();
		}
		
	}
}
