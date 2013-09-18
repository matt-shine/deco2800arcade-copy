package deco2800.arcade.snakeLadderModel;

import java.awt.Point;

import com.badlogic.gdx.graphics.Texture;

public class Tile {
	
	protected int index;
	protected Point coordinate; 
	protected String rule;
	protected int dimension;
	protected Texture tileTexture; 
	
	public Tile(int index, int dimension, String rule)
	{
		this.index = index;
		this.rule = rule;
		this.dimension = dimension;
		this.coordinate = iniCoordinate(index);
	}
	
	public void setIndex(int n){this.index = n;}
    public int getIndex(){return index; }
    public void setRule(String rule) { this.rule = rule;}
	public String getRule() { return rule;	}
	public void setDimension(int dimension){this.dimension = dimension;}
    public int getDimension(){return dimension; }
    public void setTexture(Texture t){this.tileTexture = t; }
    public Texture getTexture(){return this.tileTexture ;}
	
	public int getCoorX(){return coordinate.x;}
	public int getCoorY(){return coordinate.y;}
	
	// initialize the coordinate based on position index
	public Point iniCoordinate(int index) {
		index = index - 1;
		int y = (index / 10) * this.dimension ;
		int x = 0;
		
		// if it is even row
		if ((index/10) % 2 == 0)
		{
			x = this.dimension * (index%10);
		}
		else 
		{
			x = this.dimension * 10 - this.dimension * (index%10 +1) ;
		}
		
		return new Point(x,y);
	}
}
