package deco2800.arcade.mixmaze.domain;

public class PickModel extends ItemModel {

	protected PickModel(TileModel spawnedOn) {
		super(spawnedOn);
	}

	@Override
	public Type getType() {
		return ItemModel.Type.PICK;
	}
}
