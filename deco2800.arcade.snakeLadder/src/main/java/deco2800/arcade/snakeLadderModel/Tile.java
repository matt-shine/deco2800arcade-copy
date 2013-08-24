package deco2800.arcade.snakeLadderModel;

import java.awt.Point;

public abstract class Tile {
	
	protected int index;
	protected Point coordinate; 
	protected String rule;
	protected int dimension;
	
	public Tile(int index, int dimension, String rule)
	{
		this.index = index;
		this.rule = rule;
		this.dimension = dimension;
		iniCoordinate(index);
	}
	
	public void setIndex(int n){this.index = n;}
    public int getIndex(){return index; }
    public void setRule(String rule) { this.rule = rule;}
	public String getRule() { return rule;	}
	public void setDimension(int dimension){this.dimension = dimension;}
    public int getDimension(){return dimension; }
	
	public int getCoorX(){return coordinate.x;}
	public int getCoorY(){return coordinate.y;}
	
	// initialize the coordinate based on position index
	public abstract Point iniCoordinate(int index);
}
