package deco2800.arcade.snakeLadderModel;

import java.awt.Point;

public class TileLvl1 extends Tile {

	public TileLvl1(int index, int dimension, char c)
	{
		super(index,dimension,c);
	}
	
	@Override
	public Point iniCoordinate(int index) {
		int y = (index / 10) * this.dimension + this.dimension/2;
		int x = 0;
		
		// if it is even row
		if ((index/10) % 2 == 0)
		{
			x = this.dimension * (index%10 -1) + this.dimension /2 ;
		}
		else 
		{
			x = this.dimension * 10 - (this.dimension * (index%10 -1) + this.dimension /2);
		}
		
		return new Point(x,y);
	}

}
