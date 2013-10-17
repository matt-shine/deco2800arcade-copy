package deco2800.arcade.snakeLadderGameState;

import com.badlogic.gdx.Gdx;

import deco2800.arcade.snakeLadder.SnakeLadder;
import deco2800.arcade.snakeLadderModel.GamePlayer;

public class MovingState extends GameState {

	@Override
	public void handleInput(SnakeLadder context) {
		//get turns, to check whether is the player or the AI
		int turn=context.getturns();
		int playerIndex = turn%context.gamePlayers.length;
		// Move the player accordingly
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
					//check if the player is local player instead of AI
					if (playerIndex == 0) {
						//add one achievement to winGame achievement
				    	context.incrementAchievement("snakeLadder.winGame");
				    }
					// Winner comes out, change to GameOverState
					context.gameState=new GameOverState();

				}
				else 
				{
					// No winner yet, user reaches the destination position. Change to RuleExcutingState to run the rules at destination position
					context.gameState = new RuleExcutingState();					
				}
		}

		//If the player reaches the right end of each line , move up to another line
		if (gamePlayer.getBounds().x >= (600-20f))
		{
			gamePlayer.getBounds().x -= 0.1;
			gamePlayer.moveUp();
		}
		
		//If the player reaches the left end of each line , move up to another line
		if(gamePlayer.getBounds().x <=0)
		{
			gamePlayer.getBounds().x += 0.1;
			gamePlayer.moveUp();
		}
		
	}
}
