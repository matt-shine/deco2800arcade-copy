package deco2800.arcade.mixmaze.domain;


public interface PlayerModelObserver {

	void updateScore(int score);

	void updateBrick(int amount);

	void updatePick(boolean hasPick);

	void updateTnt(boolean hasTnt);

	void updateAction(PlayerModel.Action action);

	void updateDirection(int direction);

	void updatePosition(int x, int y);

}
