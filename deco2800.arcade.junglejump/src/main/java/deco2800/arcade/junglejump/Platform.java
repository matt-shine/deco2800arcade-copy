
package deco2800.arcade.junglejump;

import com.badlogic.gdx.graphics.Texture;

import deco2800.arcade.junglejump.GUI.junglejump;

public class Platform {
	
	private int width, height, xPos, yPos;
	private boolean active;
	private boolean climbable = false;
	private Texture platText;
	private enum world {
		WORLD_ONE, WORLD_TWO, WORLD_THREE
	}
	private world currentWorld;
	/**
	 * Platform constructor
	 * Takes width, height and X and Y position as parameters
	 */
	public Platform(int pX, int pY, int pWidth, int pHeight) {
		this.width = pWidth;
		this.height = pHeight;
		this.xPos = pX;
		this.yPos = pY;
		this.active = false;
		currentWorld = world.WORLD_ONE; // Placeholder 
	}
	
	/**
	 * Gets the width of the platform
	 */
	public int getWidth() {
		return this.width;
	}
	
	public Texture getTexture() {
		// Texture changes depending on world
		switch(currentWorld) {
		case WORLD_ONE:
			platText = new Texture("junglejumpassets/platform.png");
			break;
		case WORLD_TWO:
			// World 2 texture
			break;
		case WORLD_THREE:
			// World 3 texture
			break;
		}
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
