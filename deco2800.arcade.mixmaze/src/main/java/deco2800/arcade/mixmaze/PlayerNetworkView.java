package deco2800.arcade.mixmaze;

import deco2800.arcade.mixmaze.domain.view.IPlayerModel;

public interface PlayerNetworkView {

	void updateScore(int score);

	void updateBrick(int amount);

	void updatePick(boolean hasPick);

	void updateTnt(boolean hasTnt);

	void updateAction(IPlayerModel.Action action);

	void updateDirection(int direction);

	void updatePosition(int x, int y);

}
