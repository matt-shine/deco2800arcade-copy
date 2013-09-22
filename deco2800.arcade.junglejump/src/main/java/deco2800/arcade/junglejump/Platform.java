
package deco2800.arcade.junglejump;

import com.badlogic.gdx.graphics.Texture;

import deco2800.arcade.junglejump.GUI.junglejump;

public class Platform {
	
	private int width, height, xPos, yPos;
	private boolean active;
	public boolean climbable = false;
	private Texture platText;
	private boolean inverted;
	private enum world {
		WORLD_ONE, WORLD_TWO, WORLD_THREE
	}
	private world currentWorld;
	private char platType;
	
	/**
	 * Platform constructor
	 * Takes width, height and X and Y position as parameters
	 */
	public Platform(char type, boolean flipped, int pX, int pY, int pWidth, int pHeight) {
		this.width = pWidth;
		this.height = pHeight;
		this.xPos = pX;
		this.yPos = pY;
		this.active = false;
		this.inverted = flipped;
		currentWorld = world.WORLD_ONE; // Placeholder 
		platType = type;
		setTexture(type);
	}
	
	public void setTexture(int type) {
		String platformType = "";
		switch(type) {
		case '-': 
			platformType = "branch";
			break;
		case '<':
			platformType = "branch_end";
			break;
		case '>':
			platformType = "branch_right";
			break;
		case '^': // Goal Vine
			platformType = "goal";
			break;
		case 'j': // Vine
			platformType = "vine_short";
			break;
		case 't': // short tree
			platformType = "treetop_short";
			break;
		case 'T': // Big tree
			platformType = "treetop";
			break;
		case '|': // long trunk
			platformType = "trunk_long";
			break;
		case 'i':
			platformType = "trunk_short";
			break;
		default:
			platformType = "branch_short";
			break;
		}
		
		platText = new Texture("junglejumpassets/world1/" + platformType + ".png");
		
	}
	
	/**
	 * Gets the width of the platform
	 */
	public int getWidth() {
		return this.width;
	}
	
	public Texture getTexture() {
		// Texture changes depending on world
		/* switch(currentWorld) {
		case WORLD_ONE:
			platText = new Texture("junglejumpassets/branch.png");
			break;
		case WORLD_TWO:
			// World 2 texture
			break;
		case WORLD_THREE:
			// World 3 texture
			break;
		} */
		return this.platText;
	}
	
	/**
	 * Gets the height of the platform
	 */
	public int getHeight() {
		return this.height;
	}
	
	/**
	 * Gets the X Position of platform
	 */
	public int getX() {
		return this.xPos;
	}
	
	/**
	 * Gets the Y position of platform
	 */
	public int getY() {
		return this.yPos;
	}
	
	
	
	/**
	 * Sets the width of the platform
	 */
	public void setWidth(int newWidth) {
		this.width = newWidth;
	}
	
	/**
	 * Sets the height of the platform
	 */
	public void setHeight(int newHeight) {
		this.height = newHeight;
	}
	
	/**
	 * Sets a new X position of platform
	 */
	public void setX(int newX) {
		this.xPos = newX;
	}
	
	/**
	 * Sets a new Y position of platform
	 */
	public void setY(int newY) {
		this.yPos = newY;
	}
	
	/**
	 * Sets the platform to be active.
	 * 
	 * Platforms are activated when stood on
	 * and deactivated when jumped off.
	 * 
	 * This is useful for dynamic platforms that
	 * move or change when stood on.
	 */
	public void setActive() {
		if(this.platType == '^') {
			LevelContainer.nextLevel();
		}
		this.active = true;
	}
	
	/**
	 * Sets the platform to no longer be active
	 */
	public void setInactive() {
		this.active = false;
	}
	
	/**
	 * What happens each frame when platform is active.
	 * Does nothing on standard platform
	 */
	public void onActive() {
		return;
	}
	
}
