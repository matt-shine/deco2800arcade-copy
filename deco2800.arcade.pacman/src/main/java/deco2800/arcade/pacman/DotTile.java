package deco2800.arcade.pacman;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class DotTile extends Tile {

	private final boolean energiser; //true means energiser, false means normal dot
	private boolean exists; //true if not eaten, false if eaten
	
	public DotTile(GameMap gameMap, char type) {
		super(gameMap);
		if (type == 'P') {
			energiser = true;
		} else {
			energiser = false;
		}
		exists = true;
	}
	
	public boolean isEnergiser(){
		return energiser;
	}
	
	public boolean isEaten(){
		return !exists;
	}
	
	public void eaten() {
		exists = false;
	}
	
	public void render(SpriteBatch batch, float x, float y) {
		if (exists) {
			if (energiser) {
				batch.draw(PacView.tileSprites[1][1], x, y, sideLength, sideLength);	
			} else {
				batch.draw(PacView.tileSprites[4][1], x, y, sideLength, sideLength);
			} 
		} 
	}
	
	public String toString() {
		return "Dot" + super.toString();
	}
}
