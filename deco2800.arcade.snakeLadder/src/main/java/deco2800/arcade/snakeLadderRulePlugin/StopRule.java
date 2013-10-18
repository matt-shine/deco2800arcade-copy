package deco2800.arcade.snakeLadderRulePlugin;

import deco2800.arcade.snakeLadder.SnakeLadder;
import deco2800.arcade.snakeLadderGameState.WaitingState;
import deco2800.arcade.snakeLadderModel.GamePlayer;
import deco2800.arcade.snakeLadderModel.Rule;

public class StopRule implements Rule {

	@Override
	public void excuteRules(int playerNum, String rule, SnakeLadder context) {
		GamePlayer pg = context.gamePlayers[playerNum];
		int numRoundStop = Integer.parseInt(rule.substring(1));
		pg.setStopForNumOfRound(pg.getStopForNumOfRound()+numRoundStop);
		context.gameState = new WaitingState();
		context.taketurns();
		context.statusMessage = "Throw the dice again";
	}

}
