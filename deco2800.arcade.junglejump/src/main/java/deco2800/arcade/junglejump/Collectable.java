package deco2800.arcade.junglejump;


public class Collectable {
	private boolean collected = false;
	
	public Collectable() {
		
	}
	
	public boolean found() {
		return this.collected;
	}
	
	public void setCollected() {
		this.collected = true;
	}

}
