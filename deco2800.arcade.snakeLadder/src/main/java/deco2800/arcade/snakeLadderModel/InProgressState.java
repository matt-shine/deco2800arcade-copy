package deco2800.arcade.snakeLadderModel;

import com.badlogic.gdx.Gdx;

import deco2800.arcade.snakeLadder.SnakeLadder;

public class InProgressState extends GameState {

	@Override
	public void handleInput(SnakeLadder context) {
		context.gamePlayer.move(Gdx.graphics.getDeltaTime());
        context.AIPlayer.move(Gdx.graphics.getDeltaTime());
		if(Math.abs(context.getMap().getTileList()[context.gamePlayer.newposition()].getCoorX() - context.gamePlayer.getBounds().x) <(1f)&&Math.abs(context.getMap().getTileList()[context.gamePlayer.newposition()].getCoorY() - context.gamePlayer.getBounds().y) <(1f))
	    {
		    	context.stopPoint();
	    }

    	//If the player reaches the end of each line , move up to another line
    	if (context.gamePlayer.getBounds().x >= (600-20f) || context.gamePlayer.getBounds().x <=0){
    		context.gamePlayer.moveUp();
    	}	    	
    	//If the ball gets to the left edge then player 2 wins
    	if (context.gamePlayer.getBounds().x <= (60-20f) && context.gamePlayer.getBounds().y >= (540)) {
    		context.gamePlayer.reset();
    		context.statusMessage = "You Win! ";
    		context.gameState = GameState.GAMEOVER;
    	}
    	
    	
    	if(Math.abs(context.getMap().getTileList()[context.AIPlayer.newposition()].getCoorX() - context.AIPlayer.getBounds().x) <(1f)&&Math.abs(context.getMap().getTileList()[context.AIPlayer.newposition()].getCoorY() - context.AIPlayer.getBounds().y) <(1f))
	    {
		    	context.stopPoint();
	    }

    	//If the player reaches the end of each line , move up to another line
    	if (context.AIPlayer.getBounds().x >= (600-20f) || context.AIPlayer.getBounds().x <=0){
    		context.AIPlayer.moveUp();
    	}	    	
    	//If the ball gets to the left edge then player 2 wins
    	if (context.AIPlayer.getBounds().x <= (60-20f) && context.AIPlayer.getBounds().y >= (540)) {
    		context.AIPlayer.reset();
    		context.statusMessage = "You Lose! ";
    		context.gameState = GameState.GAMEOVER;
    	}
	}

}
