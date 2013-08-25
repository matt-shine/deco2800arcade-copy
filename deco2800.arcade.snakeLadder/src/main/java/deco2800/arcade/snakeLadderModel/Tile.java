package deco2800.arcade.snakeLadderModel;

import java.awt.Point;

import com.badlogic.gdx.graphics.Texture;

public abstract class Tile {
	
	protected int index;
	protected Point coordinate; 
	protected char rule;
	protected int dimension;
	protected Texture tileTexture; 
	
	public Tile(int index, int dimension, char rule)
	{
		this.index = index;
		this.rule = rule;
		this.dimension = dimension;
		this.coordinate = iniCoordinate(index);
	}
	
	public void setIndex(int n){this.index = n;}
    public int getIndex(){return index; }
    public void setRule(char rule) { this.rule = rule;}
	public char getRule() { return rule;	}
	public void setDimension(int dimension){this.dimension = dimension;}
    public int getDimension(){return dimension; }
    public void setTexture(Texture t){this.tileTexture = t; }
    public Texture getTexture(){return this.tileTexture ;}
	
	public int getCoorX(){return coordinate.x;}
	public int getCoorY(){return coordinate.y;}
	
	// initialize the coordinate based on position index
	public abstract Point iniCoordinate(int index);
}
