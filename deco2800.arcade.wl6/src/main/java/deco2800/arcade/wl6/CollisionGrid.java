package deco2800.arcade.wl6;

import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

public class CollisionGrid {

    private int[][] terrain = new int[64][64];

    /**
     * 
     */
    public CollisionGrid() {
    }


    /**
     * 
     * @param x
     * @param y
     * @return
     */
    public int getSolidAt(int x, int y) {
        if (x >= 64 || x < 0 || y >= 64 || y < 0) return 0;
        return terrain[x][y];
    }
    
    

    /**
     * 
     * @param x
     * @param y
     * @return
     */
    public void setSolidAt(int x, int y, int v) {
        if (x >= 64 || x < 0 || y >= 64 || y < 0) return;
        terrain[x][y] = v;
    }
    
    
    
    
    /**
     * returns potential collisions for an object in grid square x, y
     * @param x
     * @param y
     * @return
     */
    public ArrayList<Rectangle2D> getSquaresNear(int x, int y) {
    	ArrayList<Rectangle2D> r = new ArrayList<Rectangle2D>();
    	
    	for (int i = x - 1; i < x + 2; i++) {
    		for (int j = y - 1; j < y + 2; j++) {
        		if (getSolidAt(i, j) != 0) {
        			r.add(new Rectangle2D.Float(i, j, 1, 1));
        		}
        	}
    	}
    	return r;
    }
    

}
