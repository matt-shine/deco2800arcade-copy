package deco2800.arcade.breakout;

import deco2800.arcade.breakout.screens.GameScreen;

public abstract class GameState {

	public abstract void handleState(GameScreen context);
}
