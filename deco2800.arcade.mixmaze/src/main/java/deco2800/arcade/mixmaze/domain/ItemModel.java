package deco2800.arcade.mixmaze.domain;

public abstract class ItemModel {
	private TileModel tileSpawned;
	
	public void pickUpItem() {
		tileSpawned.pickUpItem();
	}
	
	protected ItemModel(TileModel spawnedOn) {
		tileSpawned = spawnedOn;
	}
}
