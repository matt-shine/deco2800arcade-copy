package deco2800.arcade.snakeLadderRulePlugin;

import deco2800.arcade.snakeLadder.SnakeLadder;
import deco2800.arcade.snakeLadderGameState.WaitingState;
import deco2800.arcade.snakeLadderModel.Rule;

public class ThrowDiceRule implements Rule {

	@Override
	public void excuteRules(int playerNum, String rule, SnakeLadder context) {
		context.gameState = new WaitingState();
	}

}
