package deco2800.arcade.mixmaze.domain;

import deco2800.arcade.mixmaze.domain.view.IPickModel;

public class PickModel extends ItemModel implements IPickModel {
	public PickModel() {
		super(ItemType.PICK);
	}
}
