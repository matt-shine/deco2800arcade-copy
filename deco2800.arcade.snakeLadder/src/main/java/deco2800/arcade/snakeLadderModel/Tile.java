package deco2800.arcade.snakeLadderModel;

import java.awt.Point;

import com.badlogic.gdx.graphics.Texture;

/**
 * @author li.tang
 * This is the class for storing tile
 */
public class Tile {
	
	private int index;
	private Point coordinate; 
	private String rule;
	private static final int dimension = 60;
	private Texture tileTexture; 
	
	public Tile(int index, String rule)
	{
		this.index = index;
		this.rule = rule;
		this.coordinate = iniCoordinate(index);
	}
	
	public void setIndex(int n){this.index = n;}
    public int getIndex(){return index; }
    public void setRule(String rule) { this.rule = rule;}
	public String getRule() { return rule;	}
    public static int getDimension(){return dimension; }
    public void setTexture(Texture t){this.tileTexture = t; }
    public Texture getTexture(){return this.tileTexture ;}
	
	public int getCoorX(){return coordinate.x;}
	public int getCoorY(){return coordinate.y;}
	
	/**
	 * initialize the coordinate based on position index
	 * @param index tile index
	 * @return
	 */
	public Point iniCoordinate(int index) {
		index = index - 1;
		int y = (index / 10) * dimension ;
		int x = 0;
		
		// if it is even row
		if ((index/10) % 2 == 0)
		{
			x = dimension * (index%10);
		}
		else 
		{
			x = dimension * 10 - dimension * (index%10 +1) ;
		}
		
		return new Point(x,y);
	}
}
