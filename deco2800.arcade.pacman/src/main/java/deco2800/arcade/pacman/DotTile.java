package deco2800.arcade.pacman;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class DotTile extends Tile {

	private final boolean energiser; //true means energiser, false means normal dot
	private boolean exists; //true if not eaten, false if eaten
	
	public DotTile(char type) {
		super();
		if (type == 'P') {
			energiser = true;
		} else {
			energiser = false;
		}
		exists = true;
	}
	
	public void eaten() {
		exists = false;
		//write more about getting score, need to set elsewhere that it stops rendering TODO
	}
	
	public void render(SpriteBatch batch, float x, float y) {
		if (exists) {
			if (energiser) {
				batch.draw(tileSprites[1][1], x, y, sideLength, sideLength);	
			} else {
				batch.draw(tileSprites[4][1], x, y, sideLength, sideLength);
			} 
		} 
	}
}
