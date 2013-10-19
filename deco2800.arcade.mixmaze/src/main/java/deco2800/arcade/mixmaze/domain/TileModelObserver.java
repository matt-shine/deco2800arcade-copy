package deco2800.arcade.mixmaze.domain;

public interface TileModelObserver {
	void updateBoxer(int id);

	void updateType(ItemModel.Type type);
}