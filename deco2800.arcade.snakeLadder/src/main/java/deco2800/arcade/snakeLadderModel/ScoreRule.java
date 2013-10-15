package deco2800.arcade.snakeLadderModel;

import deco2800.arcade.snakeLadder.SnakeLadder;
import deco2800.arcade.snakeLadderGameState.WaitingState;


public class ScoreRule implements Rule {

	/***
	 * Updating the score of the game player and print it out on the scoreLabel
	 * @param gp the Game Player its referring to
	 */
	@Override
	public void excuteRules(int playerNum, String rule, SnakeLadder context) {
		context.gamePlayers[playerNum].setScore(Integer.parseInt(rule));
		context.getScoreLabels().get(playerNum).setText(""+context.gamePlayers[playerNum].getScore());
		context.gameState = new WaitingState();
		context.statusMessage = "Throw the dice again";
		context.taketurns();
	}

}
