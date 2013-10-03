package deco2800.arcade.mixmaze;

import deco2800.arcade.mixmaze.domain.view.IItemModel.ItemType;

public interface TileNetworkView {

	void updateBoxer(int id);

	void updateWall(int direction, boolean isBuilt);

	void updateType(ItemType type);

}
