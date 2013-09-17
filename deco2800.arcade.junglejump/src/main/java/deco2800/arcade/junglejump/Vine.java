package deco2800.arcade.junglejump;


public class Vine extends Platform{
	
	public Vine(int type, boolean flipped, int pX, int pY, int pWidth, int pHeight) {
		super(type, flipped, pX, pY, pWidth, pHeight);
		// TODO Auto-generated constructor stub
	}

	// Functions like a regular platform but is climbable
	boolean climbable = true;

}
