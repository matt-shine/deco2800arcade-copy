package deco2800.arcade.pacman;

import java.util.ArrayList;
import java.util.List;

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
	protected GameMap gameMap;

	// thinking gameMap should become the model- currently Tile only has it
	// because it needs it for the toString() method, but most eveyrthing else
	// seems to need it
	public Tile(GameMap gameMap) {
		moversHere = new ArrayList<Mover>();
		this.gameMap = gameMap;
		sideLength = gameMap.getTileSideLength();
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
	}
	
}
