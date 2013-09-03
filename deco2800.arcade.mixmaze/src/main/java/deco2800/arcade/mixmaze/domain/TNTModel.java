package deco2800.arcade.mixmaze.domain;

public class TNTModel extends ItemModel {

	protected TNTModel(TileModel spawnedOn) {
		super(spawnedOn);
	}

	@Override
	public Type getType() {
		return ItemModel.Type.TNT;
	}
}
