
package deco2800.arcade.junglejump.platform;

public class Platform {
	
	/**
	 * Platform constructor
	 * Takes width, height and X and Y position as parameters
	 */
	public void platform(int pWidth, int pHeight, int pX, int pY) {
		private int width = pWidth;
		private int height = pHeight;
		private int xPos = pX;
		private int yPos = pY;
		private bool active = false;
	}
	
	/**
	 * Gets the width of the platform
	 */
	public int getWidth() {
		return this.width;
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
	
}
