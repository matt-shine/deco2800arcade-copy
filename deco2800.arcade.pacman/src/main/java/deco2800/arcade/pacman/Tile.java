package deco2800.arcade.pacman;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * A square in the grid of pacman. Can be either a dot, energiser, fruit, wall,
 * door to ghost pen or teleporter TODO work out how slowtiles (ghosts slow down
 * near teleporters) and fruit works
 */
public class Tile {

	protected int sideLength; // length of side of square- should be
											// same for all tiles
	private List<Mover> moversHere; // list of pacman/ghosts for whom this is
									// the current tile
	private GameMap gameMap;

	// thinking gameMap should become the model- currently Tile only has it
	// because it needs it for the toString() method, but most eveyrthing else
	// seems to need it
	public Tile(GameMap gameMap) {
		moversHere = new ArrayList<Mover>();
		this.gameMap = gameMap;
		sideLength = gameMap.getTileSideLength();
	}

	public void render(SpriteBatch batch, float x, float y) {
		batch.draw(PacView.tileSprites[7][1], x, y, sideLength, sideLength);
	}	

	public List<Mover> getMovers() {
		return moversHere;
	}

	public void setMovers(List<Mover> list) {
		moversHere = list;
	}

	public void removeMover(Mover mover) {
		moversHere.remove(mover);
	}

	public void addMover(Mover mover) {
		moversHere.add(mover);
	}

	public String toString() {
		return "Tile [" + gameMap.getTilePos(this).getX() + ", "
				+ gameMap.getTilePos(this).getY() + "] at ("
				+ gameMap.getTileCoords(this).getX() + ", " + 
				gameMap.getTileCoords(this).getY() + ")";
	}

	// Equals method which compares tiles based on position
	public boolean equals(Tile tile) {
		return gameMap.getTilePos(this).getX() == gameMap.getTilePos(tile)
				.getX()
				&& gameMap.getTilePos(this).getY() == gameMap.getTilePos(tile)
						.getY();
		// System.out.println("old: ("
		// + gameMap.getTilePos(this).getX() + ","
		// + gameMap.getTilePos(this).getY() + ")  new: ("
		// + gameMap.getTilePos(tile).getX() + ","
		// + gameMap.getTilePos(tile).getY() + ")");
	}
	// copy of massive switch statement in case it turns out to be necessary:
	// get rid of non wall tiles here and put them in the tile render method
	// add T for teleporter, r for fruit and s for start position for pacman
	/**
	 * switch(type) { case 'A': row = 0; col = 0; break; case 'B': row = 0;
	 * break; case 'C': row = 0; col = 2; break; case 'J': row = 0; col = 3;
	 * break; case 'D': row = 1; col = 0; break; case 'P': row = 1; break; case
	 * 'E': row = 1; col = 2; break; case 'K': row = 1; col = 3; break; case
	 * 'F': row = 2; col = 0; break; case 'G': row = 2; break; case 'H': row =
	 * 2; col = 2; break; case 'L': row = 2; col = 3; break; case 'a': row = 3;
	 * col = 0; break; case 'b': row = 3; break; case 'c': row = 3; col = 2;
	 * break; case 'M': row = 3; col = 3; break; case 'd': row = 4; col = 0;
	 * break; case 'p': row = 4; break; case 'e': row = 4; col = 2; break; case
	 * 'R': row = 4; col = 3; break; case 'f': row = 5; col = 0; break; case
	 * 'g': row = 5; break; case 'h': row = 5; col = 2; break; case 'S': row =
	 * 5; col = 3; break; case '1': row = 6; col = 0; break; case '2': row = 6;
	 * break; case '3': row = 6; col = 2; break; case 'w': row = 6; col = 3;
	 * break; case '4': col = 0; break; case ' ': break; case '5': col = 2;
	 * break; case 'x': col = 3; break; case '6': row = 8; col = 0; break; case
	 * '7': row = 8; break; case '8': row = 8; col = 2; break; case 'y': row =
	 * 8; col = 3; break; case '9': row = 9; col = 0; break; case 'Q': row = 9;
	 * break; case '0': row = 9; col = 2; break; case 'z': row = 9; col = 3;
	 * break; case 'W': row = 10; col = 0; break; case 'X': row = 10; break;
	 * case 'Y': row = 10; col = 2; break; case 'Z': row = 10; col = 3; break; }
	 */
}
