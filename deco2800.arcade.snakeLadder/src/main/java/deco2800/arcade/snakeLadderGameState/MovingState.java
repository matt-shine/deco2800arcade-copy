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
    	if (context.gamePlayers[playerIndex].getBounds().x <= (60-20f) && context.gamePlayers[0].getBounds().y >= (540)) {
    		context.gamePlayers[playerIndex].reset();
    		context.statusMessage = context.gamePlayers[playerIndex].getPlayerName() +" Win! ";
    		context.gameState = GameState.GAMEOVER;
    	}
	}

	/**
	 * @param context
	 */
	private void movePlayer(SnakeLadder context, GamePlayer gamePlayer) {
		gamePlayer.move(Gdx.graphics.getDeltaTime());
		if(context.getMap().getTileList()[gamePlayer.newposition()].getIndex()>=99)
		{
			context.gameState=new GameOverState();
		}
		//player stop move once he get relatively closed to desitination position
		if(Math.abs(context.getMap().getTileList()[gamePlayer.newposition()].getCoorX() - gamePlayer.getBounds().x) <(1f)&&Math.abs(context.getMap().getTileList()[gamePlayer.newposition()].getCoorY() - gamePlayer.getBounds().y) <(1f))
		{
				gamePlayer.reset();
				if (gamePlayer.getBounds().x <= (60-20f) && gamePlayer.getBounds().y >= (540)) {			   
					context.gameState = new GameOverState();
				} 
				else {
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
