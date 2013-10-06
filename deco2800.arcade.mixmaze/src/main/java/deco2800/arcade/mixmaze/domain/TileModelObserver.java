package deco2800.arcade.mixmaze.domain;

public interface TileModelObserver {

	void updateBoxer(int id);

	void updateWall(int direction, boolean isBuilt);
	
	void updateType(ItemModel.Type type);
}