package deco2800.arcade.snakeLadderModel;

import deco2800.arcade.snakeLadder.SnakeLadder;

public abstract interface Rule {
	public abstract void excuteRules(int playerNum, String rule, SnakeLadder context);
}
