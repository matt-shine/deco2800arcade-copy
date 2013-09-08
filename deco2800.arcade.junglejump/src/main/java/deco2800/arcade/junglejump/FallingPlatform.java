package deco2800.arcade.junglejump;

public class FallingPlatform extends Platform{
	
	public FallingPlatform(int pX, int pY, int pWidth, int pHeight) {
		super(pX, pY, pWidth, pHeight);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Makes the platform fall when active
	 */
	public void onActive() {
		int fallSpeed = 1;
		
		int pos = this.getX();
		pos -= fallSpeed;
		this.setX(pos);
	}

}
