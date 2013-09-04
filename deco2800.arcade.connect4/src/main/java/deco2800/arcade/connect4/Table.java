package deco2800.arcade.connect4;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import java.util.*;

public class Table {

	//leaving a gap of 5f between disc positions
	public static final float WIDTH = 320f; //How big is the table (its a rectangle)
	public static final float HEIGHT = 280f;
	public static final float DISCRADIUS = 20f;
	public static final float INITIALSPEED = 200; // How fast is the ball going at the start of a point
	public static final float BOUNCEINCREMENT = 1.1f; // How much does the ball speed up each time it gets hit
	
	// These refer to the table, so moved them into this class
	public static final int TABLECOLS = 7;
	public static final int TABLEROWS = 6;
	
	Rectangle bounds = new Rectangle(); //The position (x,y) and dimensions (width,height) of the tablebg
	
	private ArrayList<ArrayList<Disc>> discArray = new ArrayList<ArrayList<Disc>>(6);
	
	
	private float renderColourRed;
    private float renderColourGreen;
    private float renderColourBlue;
    private float renderColourAlpha;
	
	/**
	 * Basic constructor for Table. Set position and dimensions to the default
	 */
	public Table() {
		bounds.x = 100;
		bounds.y = 20;
		bounds.height = HEIGHT;
		bounds.width = WIDTH;
		this.setColor(1,1,0,1);
		
		for ( int i = 0; i<Table.TABLEROWS; i++ ) {
			ArrayList<Disc> internal = new ArrayList<Disc>(Table.TABLECOLS);
			for ( int j=0; j<Table.TABLECOLS; j++ ) {
				internal.add( new Disc() );
			}
			discArray.add(internal);
		}
	}
	
	public void SetupDiscs(){
		for(int i=0; i < Table.TABLEROWS; i++) {
			for(int j=0;j < Table.TABLECOLS; j++){
				discArray.get(i).get(j).setColor(0, 0, 0, 1);
				discArray.get(i).get(j).bounds.x = this.bounds.x + (5 + DISCRADIUS*2) * (j) + (DISCRADIUS + 5);
				discArray.get(i).get(j).bounds.y = this.bounds.y + (5 + DISCRADIUS*2) * (i) + (DISCRADIUS + 5);
			}
		}
	}
	
	public void resetDiscs() {
		for(int i=0; i < Table.TABLEROWS; i++) {
			for(int j=0;j < Table.TABLECOLS; j++){
				discArray.get(i).get(j).setState( Disc.EMPTY );
			}
		}
	}
	
	/**
     * Set the colour of the rendered table.
     * @param r Red (0-1)
     * @param g Green (0-1)
     * @param b Blue (0-1)
     * @param a Alpha (0-1)
     */
    public void setColor(float r, float g, float b, float a)
    {
        renderColourRed = r;
        renderColourGreen = g;
        renderColourBlue = b;
        renderColourAlpha = a;
    }
    
    /** 
     * Find the position to place the next disc
     */
    public boolean placeDisc(int colNo, int playerTurn) {
    	int rowToSet = 0;
    	
    	for (int i = (Table.TABLEROWS - 1); i >= 0; i--) {
    		//if (discArray.get(i).get(colNo).isSetPlayer1 || discArray.get(i).get(colNo).isSetPlayer2) {
    		if (discArray.get(i).get(colNo).getState() != Disc.EMPTY) {
    			if (i == (Table.TABLEROWS - 1)) {
    				return false;
    			} else {
    				rowToSet = i + 1;
    			}
    			break;
    		} else {
    			if ( i == 0) {
    				rowToSet = 0;
    			}
    		}
    	}
    	
    	if (playerTurn == 0){
    		discArray.get(rowToSet).get(colNo).isSetPlayer1 = true;
    		discArray.get(rowToSet).get(colNo).setState( Disc.PLAYER1 );
    	} else {
    		discArray.get(rowToSet).get(colNo).isSetPlayer2 = true;
    		discArray.get(rowToSet).get(colNo).setState( Disc.PLAYER2 );
    	}
    	return true; //successful
    }
    

    public boolean checkFieldWinner(int playerToCheck) {
    	
    	for ( int r = 0; r < Table.TABLEROWS; r++ ) {
    		for ( int c = 0; c < Table.TABLECOLS; c++ ) {
    			
    			for ( int dr = -1; dr <= 1; dr++ ) {
    				for ( int dc = -1; dc <= 1; dc++ ) {
    					if ( ( dr == 0 ) && ( dc == 0 ) ) {
    						continue;
    					}
    					
    					boolean otherDiscFound = false;
    					for ( int dist = 0; dist < 4; dist++ ) {
    						if ( r + dr*dist >= Table.TABLEROWS 
    								|| r + dr*dist < 0
    								|| c + dc*dist >= Table.TABLECOLS 
    								|| c + dc*dist < 0 ) {
    							otherDiscFound = true;
    	    					break;
    						}
    	    				if ( discArray.get( r + dr*dist ).get( c + dc*dist ).getState() != playerToCheck ) {
    	    					otherDiscFound = true;
    	    					break;
    	    				}
    	    			}
    					
    					if ( otherDiscFound ) {
    						continue;
    					}
    					
    					return true;
    				}
    			}
    		}
    	}
    	
    	return false;
    }
    
    /**
     * Render the table.
     * @param shapeRenderer The current {@link ShapeRenderer} instance.
     */
    public void render(ShapeRenderer shapeRenderer)
    {
    	shapeRenderer.begin(ShapeType.FilledRectangle);
        shapeRenderer.setColor(renderColourRed,
                               renderColourGreen,
                               renderColourBlue,
                               renderColourAlpha);
        shapeRenderer.filledRect(this.bounds.x, this.bounds.y, this.bounds.width, this.bounds.height);
        shapeRenderer.end();
        renderDiscs(shapeRenderer);
    }
    
    private void renderDiscs(ShapeRenderer shapeRenderer){
    	int i = 0;
    	int j = 0;
    	
    	shapeRenderer.begin(ShapeType.FilledCircle);
	    
    	for(i = 0; i < 6; i++){
    		for(j = 0; j < 7; j++){
    			discArray.get(i).get(j).render(shapeRenderer);
    		}
    	}
    	
    	shapeRenderer.end();
    	
    }
	
}
