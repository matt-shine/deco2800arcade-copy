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
	
	Rectangle bounds = new Rectangle(); //The position (x,y) and dimensions (width,height) of the tablebg
	
	ArrayList<ArrayList<Disc>> discArray = new ArrayList<ArrayList<Disc>>(6);
	
	
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
		
		for ( int i = 0; i<6; i++ ) {
			ArrayList<Disc> internal = new ArrayList<Disc>(7);
			for ( int j=0; j<7; j++ ) {
				internal.add( new Disc() );
			}
			discArray.add(internal);
		}
	}
	
	public void SetupDiscs(){
		for(int i=0; i < 6; i++) {
			for(int j=0;j < 7; j++){
				discArray.get(i).get(j).setColor(0, 0, 0, 1);
				discArray.get(i).get(j).bounds.x = this.bounds.x + (5 + DISCRADIUS*2) * (j) + (DISCRADIUS + 5);
				discArray.get(i).get(j).bounds.y = this.bounds.y + (5 + DISCRADIUS*2) * (i) + (DISCRADIUS + 5);
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
    	
    	for (int i = (Connect4.TABLEROWS - 1); i >= 0; i--) {
    		if (discArray.get(i).get(colNo).isSetPlayer1 || discArray.get(i).get(colNo).isSetPlayer2) {
    			if (i == (Connect4.TABLEROWS - 1)) {
    				return false;
    			} else {
    				rowToSet = i + 1;//i+1
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
    		discArray.get(rowToSet).get(colNo).setColor(1,0,0,1);
    	} else {
    		discArray.get(rowToSet).get(colNo).isSetPlayer2 = true;
    		discArray.get(rowToSet).get(colNo).setColor(0,0,1,1);
    	}
    	return true; //successful
    }
    
    /**
     * Check field for winning combination
     * Takes -> the current player to check
     * Returns -> wether the player has won
     */
    
    public boolean checkFieldWinner(int playerToCheck){
    	for (int i = (Connect4.TABLEROWS - 1); i >= 0; i--) {
    		for (int j = (Connect4.TABLECOLS - 1); j >= 0; j--) {
    			if (playerToCheck == 0) {
    				if (discArray.get(i).get(j).isSetPlayer1) {
    					//check down
    					if (i >= 3) {
    						//check straight down
    						if (discArray.get(i-1).get(j).isSetPlayer1) {
    							if (discArray.get(i-2).get(j).isSetPlayer1) {
    								if (discArray.get(i-3).get(j).isSetPlayer1) {
    									//player 1 won the game - 4 connected
    									return true;
    								}
    							}
    						}
    						//check left diagonal
    						if (j >= 3) {
    							if (discArray.get(i-1).get(j-1).isSetPlayer1) {
    								if (discArray.get(i-2).get(j-2).isSetPlayer1) {
    									if (discArray.get(i-3).get(j-3).isSetPlayer1) {
    										return true;
    									}
    								}
    							}
    						}
    						//check right diagonal
    						if (j <= (Connect4.TABLEROWS) - 4) {
        						if (discArray.get(i-1).get(j+1).isSetPlayer1) {
        							if (discArray.get(i-2).get(j+2).isSetPlayer1) {
        								if (discArray.get(i-3).get(j+3).isSetPlayer1) {
        									return true;
        								}
        							}
        						}
    						}
    					}
    					//check up
    					if (i <= (Connect4.TABLEROWS - 4)) {
    						//check straight up
    						if (discArray.get(i+1).get(j).isSetPlayer1) {
    							if (discArray.get(i+2).get(j).isSetPlayer1) {
    								if (discArray.get(i+3).get(j).isSetPlayer1) {
    									//player 1 won the game - 4 connected
    									return true;
    								}
    							}
    						}
    						//check left diagonal
    						if (j >= 3) {
    							if (discArray.get(i+1).get(j-1).isSetPlayer1) {
    								if (discArray.get(i+2).get(j-2).isSetPlayer1) {
    									if (discArray.get(i+3).get(j-3).isSetPlayer1) {
    										return true;
    									}
    								}
    							}
    						}
    						//check right diagonal
    						if (j <= (Connect4.TABLEROWS) - 4) {
        						if (discArray.get(i+1).get(j+1).isSetPlayer1) {
        							if (discArray.get(i+2).get(j+2).isSetPlayer1) {
        								if (discArray.get(i+3).get(j+3).isSetPlayer1) {
        									return true;
        								}
        							}
        						}
    						}
    					}
    					//check left
    					if (j >= 3) {
    						if (discArray.get(i).get(j-1).isSetPlayer1) {
    							if (discArray.get(i).get(j-2).isSetPlayer1){
    								if (discArray.get(i).get(j-3).isSetPlayer1){
    									//player 1 won the game - 4 connected
    									return true;
    								}
    							}
    						}
    					}
    					//check right
    					if (j <= (Connect4.TABLEROWS) - 4) {
    						if (discArray.get(i).get(j+1).isSetPlayer1) {
    							if (discArray.get(i).get(j+2).isSetPlayer1){
    								if (discArray.get(i).get(j+3).isSetPlayer1){
    									//player 1 won the game - 4 connected
    									return true;
    								}
    							}
    						}
    					}
    				}
    			} else if (playerToCheck == 1) {
    				if (discArray.get(i).get(j).isSetPlayer2) {
    					//check down
    					if (i >= 3) {
    						//check straight down
    						if (discArray.get(i-1).get(j).isSetPlayer2) {
    							if (discArray.get(i-2).get(j).isSetPlayer2) {
    								if (discArray.get(i-3).get(j).isSetPlayer2) {
    									//player 1 won the game - 4 connected
    									return true;
    								}
    							}
    						}
    						//check left diagonal
    						if (j >= 3) {
    							if (discArray.get(i-1).get(j-1).isSetPlayer2) {
    								if (discArray.get(i-2).get(j-2).isSetPlayer2) {
    									if (discArray.get(i-3).get(j-3).isSetPlayer2) {
    										return true;
    									}
    								}
    							}
    						}
    						//check right diagonal
    						if (j <= (Connect4.TABLEROWS) - 4) {
        						if (discArray.get(i-1).get(j+1).isSetPlayer2) {
        							if (discArray.get(i-2).get(j+2).isSetPlayer2) {
        								if (discArray.get(i-3).get(j+3).isSetPlayer2) {
        									return true;
        								}
        							}
        						}
    						}
    					}
    					//check up
    					if (i <= (Connect4.TABLEROWS - 4)) {
    						//check straight up
    						if (discArray.get(i+1).get(j).isSetPlayer2) {
    							if (discArray.get(i+2).get(j).isSetPlayer2) {
    								if (discArray.get(i+3).get(j).isSetPlayer2) {
    									//player 1 won the game - 4 connected
    									return true;
    								}
    							}
    						}
    						//check left diagonal
    						if (j >= 3) {
    							if (discArray.get(i+1).get(j-1).isSetPlayer2) {
    								if (discArray.get(i+2).get(j-2).isSetPlayer2) {
    									if (discArray.get(i+3).get(j-3).isSetPlayer2) {
    										return true;
    									}
    								}
    							}
    						}
    						//check right diagonal
    						if (j <= (Connect4.TABLEROWS) - 4) {
        						if (discArray.get(i+1).get(j+1).isSetPlayer2) {
        							if (discArray.get(i+2).get(j+2).isSetPlayer2) {
        								if (discArray.get(i+3).get(j+3).isSetPlayer2) {
        									return true;
        								}
        							}
        						}
    						}
    					}
    					//check left
    					if (j >= 3) {
    						if (discArray.get(i).get(j-1).isSetPlayer2) {
    							if (discArray.get(i).get(j-2).isSetPlayer2){
    								if (discArray.get(i).get(j-3).isSetPlayer2){
    									//player 1 won the game - 4 connected
    									return true;
    								}
    							}
    						}
    					}
    					//check right
    					if (j <= (Connect4.TABLEROWS) - 4) {
    						if (discArray.get(i).get(j+1).isSetPlayer2) {
    							if (discArray.get(i).get(j+2).isSetPlayer2){
    								if (discArray.get(i).get(j+3).isSetPlayer2){
    									//player 1 won the game - 4 connected
    									return true;
    								}
    							}
    						}
    					}
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
        shapeRenderer.setColor(renderColourRed,
                               renderColourGreen,
                               renderColourBlue,
                               renderColourAlpha);
        shapeRenderer.filledRect(this.bounds.x, this.bounds.y, this.bounds.width, this.bounds.height);
    }
    
    public void renderDiscs(ShapeRenderer shapeRenderer){
    	int i = 0;
    	int j = 0;
    	
    	for(i = 0; i < 6; i++){
    		for(j = 0; j < 7; j++){
    			discArray.get(i).get(j).render(shapeRenderer);
    		}
    	}
    	
    }
	
}
