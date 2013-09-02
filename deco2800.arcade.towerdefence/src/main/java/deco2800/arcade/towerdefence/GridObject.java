package deco2800.arcade.towerdefence;

public interface GridObject {
	//Returns whether the object has a health characteristic. Great for determining divine objects that can't die or potentially whether something should move or not.
	public boolean isLiving();

	//Returns the grid the object belongs to.
	public Grid grid();
	
	//Returns the x and y coordinates of the object.
	public Position position();
	
	//Returns whether the object should be drawn to the grid.
	public boolean visible();
	
	//Render the object invisible, remove any persistent effects and then remove the object from the model.
	public void destroy();
	
	//Returns whether instantiating or destroying an object has an effect on the grid or other objects.
	public boolean hasGlobalEffect();
	
	//Returns whether the object has the ability to place status effects on the grid or other objects at any time.
	public boolean hasEffects();
	
	//Returns whether the object currently has collision or not.
	public boolean isPhysical();
	
	//All grid objects must have an opaqueness value for drawing.
	public void setOpaqueness();
}
