package deco2800.arcade.snakeLadderModel;

import deco2800.arcade.snakeLadder.SnakeLadder;
import deco2800.arcade.snakeLadderGameState.WaitingState;


public class ScoreRule implements Rule {

	/***
	 * Updating the score of the game player and print it out on the scoreLabel
	 * @param playerNum 
	 * @param rule
	 * @param context
	 */
	@Override
	public void excuteRules(int playerNum, String rule, SnakeLadder context) {
		processRule(playerNum, rule, context);
		renderRule(playerNum, context);
		
	}

	/**
	 * @param playerNum
	 * @param context
	 */
	public void renderRule(int playerNum, SnakeLadder context) {
		context.getScoreLabels().get(playerNum).setText(""+context.gamePlayers[playerNum].getScore());
		context.statusMessage = "Throw the dice again";
	}

	/**
	 * @param playerNum
	 * @param rule
	 * @param context
	 * @throws NumberFormatException
	 */
	public void processRule(int playerNum, String rule, SnakeLadder context)
			throws NumberFormatException {
		// workaround: Integer.parseInt doesn't allow a leading + (it only allows a leading -), so
		// test for that case ourselves
		if (rule.charAt(0) == '+') {
			rule = rule.substring(1);
		}

		context.gamePlayers[playerNum].setScore(Integer.parseInt(rule));
		context.gameState = new WaitingState();
		context.taketurns();
	}

}
