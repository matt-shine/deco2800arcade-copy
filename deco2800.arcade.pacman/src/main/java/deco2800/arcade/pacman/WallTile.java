package deco2800.arcade.pacman;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class WallTile extends Tile {

//	public static void main(String[] args) {
//		WallTile a = new WallTile(8);
//		//a.north = a;
//		System.out.println(a.getNorthType());
//	}
	//Game map should be 28x31 tiles
	/** An integer representing the type of wallTile
	 *  
	 */
	private int type;
	//sprite sheet
	private TextureRegion[][] wallSprites;
	
	public WallTile(int type) {
		super();
		this.type = type;
		Texture temp = new Texture(Gdx.files.internal("wallsAndPellets.png"));
		// splits into columns and rows then puts them into one array in order
		wallSprites= TextureRegion.split(temp, 8, 8);
	}
	
	// draws the wall tile
	// I was thinking of the opposite array coordinates when i created these, so possible 
	// it goes [col num][row num]
	public void render(SpriteBatch batch, float x, float y) {
		if (type == 1) {
			batch.draw(wallSprites[0][1], x, y);
		} else if (type == 2) {
			batch.draw(wallSprites[1][0], x, y);
		}
	}
	

}
