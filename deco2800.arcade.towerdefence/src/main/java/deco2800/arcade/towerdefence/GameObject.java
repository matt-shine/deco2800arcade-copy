package deco2800.arcade.towerdefence;

public interface GameObject {
	//Returns whether the object has a health characteristic. Great for determining divine objects that can't die or whether something should move or not.
	public boolean isLiving();

	//Returns the current x,y entry of the objects position on the isometric grid.
	public Position position();
	
	
	
}
