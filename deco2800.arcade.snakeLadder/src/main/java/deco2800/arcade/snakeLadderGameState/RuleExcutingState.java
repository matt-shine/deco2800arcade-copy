package deco2800.arcade.snakeLadderGameState;

import deco2800.arcade.snakeLadder.SnakeLadder;

import com.badlogic.gdx.Gdx;

public class RuleExcutingState extends GameState {

	@Override
	public void handleInput(SnakeLadder context) {
		int turn=context.getturns();
		int playerIndex = turn%context.gamePlayers.length;
		String rule = context.getMap().getTileList()[context.gamePlayers[playerIndex].newposition()].getRule();
		this.excuteRules(playerIndex, rule, context);
		//after excuting all the rule go back to waiting state
		context.gameState = new WaitingState();
		context.statusMessage = "Throw the dice again";
		context.taketurns();
	}

	/***
	 * Updating the score of the game player and print it out on the scoreLabel
	 * @param gp the Game Player its referring to
	 */
	public void excuteRules(int playerNum, String rule, SnakeLadder context){
		if (isScore(rule))
		{
			context.gamePlayers[playerNum].setScore(Integer.parseInt(rule));
			context.getScoreLabels().get(playerNum).setText(""+context.gamePlayers[playerNum].getScore());
		}
	}
	
	/***
	 * Check whether the rule is score related or not
	 * @param rule The rule of the tile the player is currently in
	 * @return true if the rule have something to do with score, false otherwise
	 */
	private boolean isScore (String rule) {
	    try { 
	        Integer.parseInt(rule); 
	    } catch(NumberFormatException e) { 
	        return false; 
	    }
	    // only got here if we didn't return false
	    return true;
	}
}
