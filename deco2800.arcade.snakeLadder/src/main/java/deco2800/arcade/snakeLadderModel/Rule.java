package deco2800.arcade.snakeLadderModel;

import deco2800.arcade.snakeLadder.SnakeLadder;

public abstract interface Rule {
	/**
	 * Implement this abstract method to define the actions to be performed when the corresponding
	 * rule instance is instantiated
	 * @param playerNum define the player index
	 * @param rule rule value displayed in map
	 * @param context the main snakeLadder game context
	 */
	public abstract void excuteRules(int playerNum, String rule, SnakeLadder context);
}
