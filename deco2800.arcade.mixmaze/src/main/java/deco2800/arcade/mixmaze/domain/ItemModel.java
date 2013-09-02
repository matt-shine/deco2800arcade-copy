package deco2800.arcade.mixmaze.domain;

public abstract class ItemModel {
	private int amount;
	
	public int getAmount() {
		return amount;
	}
	
	public ItemModel(int amnt) {
		amount = amnt;
	}
}
