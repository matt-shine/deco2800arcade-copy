package deco2800.arcade.snakeLadderModel;

import com.badlogic.gdx.Gdx;

import deco2800.arcade.snakeLadder.SnakeLadder;

public class MovingState extends GameState {

	@Override
	public void handleInput(SnakeLadder context) {
		int turn=context.getturns();
		//player's turn
		if(turn%2==0)
		{
			movePlayer(context,context.gamePlayers[0]);	    	
	    	//If the ball gets to the left edge then player 2 wins
	    	if (context.gamePlayers[0].getBounds().x <= (60-20f) && context.gamePlayers[0].getBounds().y >= (540)) {
	    		context.gamePlayers[0].reset();
	    		context.statusMessage = "You Win! ";
	    		context.gameState = GameState.GAMEOVER;
	    	}
		}
		//AI's turn
		else if (turn%2==1)
		{
			movePlayer(context,context.gamePlayers[1]);	  
	    	//If the ball gets to the left edge then player 2 wins
	    	if (context.gamePlayers[1].getBounds().x <= (60-20f) && context.gamePlayers[1].getBounds().y >= (540)) {
	    		context.gamePlayers[1].reset();
	    		context.statusMessage = "AI Wins! ";
	    		context.gameState = GameState.GAMEOVER;
	    	}
		}
	}

	/**
	 * @param context
	 */
	private void movePlayer(SnakeLadder context, GamePlayer gamePlayer) {
		gamePlayer.move(Gdx.graphics.getDeltaTime());
		if(Math.abs(context.getMap().getTileList()[gamePlayer.newposition()].getCoorX() - gamePlayer.getBounds().x) <(1f)&&Math.abs(context.getMap().getTileList()[gamePlayer.newposition()].getCoorY() - gamePlayer.getBounds().y) <(1f))
		{
		    	//context.stopPoint();
//		    	context.updateScore(gamePlayer);
				gamePlayer.reset();
				context.gamePlayers[1].reset();
				// If we've reached the victory point then update the display
				if (gamePlayer.getBounds().x <= (60-20f) && gamePlayer.getBounds().y >= (540)) {			   
					context.gameState = new GameOverState();
				    //Update the game state to the server
				    //networkClient.sendNetworkObject(createScoreUpdate());
				} 
				if (context.gamePlayers[1].getBounds().x<=(60-20f)&&context.gamePlayers[1].getBounds().y>=540){
					context.gameState = new GameOverState();
				}
				else {
					// No winner yet, get ready for another point
					context.gameState = new RuleExcutingState();
//					context.statusMessage = "Throw the dice again";
//					context.taketurns();
					
				}
		}

		//If the player reaches the end of each line , move up to another line
		if (gamePlayer.getBounds().x >= (600-20f) || gamePlayer.getBounds().x <=0){
			gamePlayer.moveUp();
		}
	}
}
