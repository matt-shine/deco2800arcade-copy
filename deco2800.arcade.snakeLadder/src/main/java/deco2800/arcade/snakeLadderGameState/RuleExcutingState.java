package deco2800.arcade.snakeLadderGameState;

import deco2800.arcade.snakeLadder.SnakeLadder;
import deco2800.arcade.snakeLadderModel.*;

public class RuleExcutingState extends GameState {

	@Override
	public void handleInput(SnakeLadder context) {
		int turn=context.getturns();
		int playerIndex = turn%context.gamePlayers.length;
		String rule = context.getMap().getTileList()[context.gamePlayers[playerIndex].newposition()].getRule();
		
		//Snake and Ladder rules are two special rules hard-coded into game
		if(rule.startsWith("S")||rule.startsWith("L"))
		{
			Rule r = new LadderSnakeRule();
			r.excuteRules(playerIndex, rule, context);
		}
		//search the implementation class for the plugin rules
		else if(!rule.equals("."))
		{
			try {
				Rule r = (Rule) Class.forName("deco2800.arcade.snakeLadderModel."+context.getRuleMapping().get(rule).getImplementationClass()).newInstance();
				r.excuteRules(playerIndex, rule, context);
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		//after excuting all the rule go back to waiting state
		context.gameState = new WaitingState();
		context.statusMessage = "Throw the dice again";
		context.taketurns();
	}

	/***
	 * Updating the score of the game player and print it out on the scoreLabel
	 * @param gp the Game Player its referring to
	 */
	public void excuteRules(int playerNum, String rule, SnakeLadder context,GamePlayer context2){
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
