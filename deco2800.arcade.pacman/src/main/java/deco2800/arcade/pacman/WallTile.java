package deco2800.arcade.pacman;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class WallTile extends Tile {

	//Game map should be 28x31 tiles
	/** A char representing the type of wallTile. Same char used in file  
	 */
	private final char type;
	
	public WallTile(GameMap gameMap, char type) {
		super(gameMap);
		this.type = type;
		this.setMovers(new ArrayList<Mover>());
//		if (type == 'Q') {
//			this.setMovers(new ArrayList<Mover>());
//		} else {
//			this.setMovers(null);
//		}
	}	
	
	public void render(SpriteBatch batch, float x, float y) {
		int row = 7;
		int col = 1;
		switch(type) {
		case 'A': row = 0; col = 0; break;
		case 'B': row = 0; break;
		case 'C': row = 0; col = 2; break;
		case 'J': row = 0; col = 3; break;
		case 'D': row = 1; col = 0; break;
		case 'E': row = 1; col = 2; break;
		case 'K': row = 1; col = 3; break;
		case 'F': row = 2; col = 0; break;
		case 'G': row = 2; break;
		case 'H': row = 2; col = 2; break;
		case 'L': row = 2; col = 3; break;
		case 'a': row = 3; col = 0; break;
		case 'b': row = 3; break;
		case 'c': row = 3; col = 2; break;
		case 'M': row = 3; col = 3; break;
		case 'd': row = 4; col = 0; break;
		case 'e': row = 4; col = 2; break;
		case 'R': row = 4; col = 3; break;
		case 'f': row = 5; col = 0; break;
		case 'g': row = 5; break;
		case 'h': row = 5; col = 2; break;
		case 'S': row = 5; col = 3; break;
		case '1': row = 6; col = 0; break;
		case '2': row = 6; break;
		case '3': row = 6; col = 2; break;
		case 'w': row = 6; col = 3; break;
		case '4': col = 0; break;
		case '5': col = 2; break;
		case 'x': col = 3; break;
		case '6': row = 8; col = 0; break;
		case '7': row = 8; break;
		case '8': row = 8; col = 2; break;
		case 'y': row = 8; col = 3; break;
		case '9': row = 9; col = 0; break;
		case 'Q': row = 9; break;
		case '0': row = 9; col = 2; break;
		case 'z': row = 9; col = 3; break;
		case 'W': row = 10; col = 0; break;
		case 'X': row = 10; break;
		case 'Y': row = 10; col = 2; break;
		case 'Z': row = 10; col = 3; break;
		}
		batch.draw(tileSprites[row][col], x, y, sideLength, sideLength);
	}
	
	public String toString() {
		return "Wall" + super.toString();
	}
}
