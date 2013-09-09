package deco2800.arcade.junglejump;


public class Goal extends Vine{
	
	public Goal(int pX, int pY, int pWidth, int pHeight) {
		super(pX, pY, pWidth, pHeight);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * When goal is activated the level
	 * should finish
	 */
	public void onActive() {
		// run animation for beating the level
		// tell controller to end this level and start next
	}

}
