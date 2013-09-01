package deco2800.arcade.mixmaze.domain;

public class BrickModel extends ItemModel {
	public final static int MAXBRICKS = 10;
	private int amount;
	
	public int getAmount() {
		return amount;
	}
	
	public void setAmount(int number) {
		if(number < 0 && number > MAXBRICKS) {
			throw new IllegalArgumentException("number must be positive and less than or equal to MAXBRICKS.");
		}
		amount = number;
	}
	
	public void addAmount(int number) {
		int addedAmount = amount + number;
		if(addedAmount < 0 && addedAmount > MAXBRICKS) {
			throw new IllegalArgumentException("number must result in a amount that is positive and less than or equal to MAXBRICKS.");
		}
		amount = addedAmount;
	}
	
	public BrickModel(TileModel spawnedOn, int number) {
		super(spawnedOn);
		amount = number;
	}
}
