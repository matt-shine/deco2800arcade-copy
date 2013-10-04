package deco2800.arcade.pacman;

import java.util.List;
/**
 * An abstract class for objects that can move and collide *
 */
public abstract class Mover {

	//the coordinates of the bottom left corner of the pacman/ghost
	protected int x; 
	protected int y;
	protected int height; 
	protected int width;
	protected Tile currentTile; //current tile of the pacman/ghost
	
	public Mover(Tile startTile, int x, int y) {
		currentTile = startTile;
		this.x = x;
		this.y = y;		
	}
	
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
	
	public int getHeight() {
		return height;
	}


	public void setHeight(int height) {
		this.height = height;
	}


	public int getWidth() {
		return width;
	}


	public void setWidth(int width) {
		this.width = width;
	}

	public Tile getCurrentTile() {
		return currentTile;
	}

	public void setCurrentTile(Tile currentTile) {
		this.currentTile = currentTile;
	}
	
	/**
	 * Overrides toString() so that trying to print the list won't crash the program
	 */
	@Override
	public String toString() {
		return new String("Object of " + this.getClass() + " at (" + x + "," + y + "), width=" + width + ", height=" + height);
	}
}
