package deco2800.arcade.mixmaze.domain;

import java.util.ArrayList;
import java.util.List;

/*
 * WallModel represents a active/inactive wall on a tile.
 */
public class WallModel {
	private int direction;
	private List<TileModel> tiles;
	private boolean built;
	private PlayerModel builder;
	
	public int getDirection() {
		return direction;
	}
	
	public void addTile(TileModel tile) {
		if(tiles.contains(tile)) {
			throw new IllegalStateException("The tile is already present.");
		}
		tiles.add(tile);
	}
	
	public boolean isBuilt() {
		return built;
	}
	
	public PlayerModel getBuilder() {
		return builder;
	}
	
	public void build(PlayerModel player) {
		if(player == null) {
			throw new IllegalArgumentException("player cannot be null.");
		}
		
		if(built) {
			throw new IllegalStateException("The wall is already built.");
		}
		
		built = true;
		builder = player;
		checkTiles(player);
	}
	
	public void destroy(PlayerModel player) {
		if(!built) {
			throw new IllegalStateException("The wall is not built.");
		}
		
		built = false;
		builder = null;
		checkTiles(player);
	}
	
	private void checkTiles(PlayerModel player) {
		if(player == null) {
			throw new IllegalArgumentException("player cannot be null.");
		}
		
		for(TileModel tile : tiles) {
			tile.checkBox(player);
		}
	}
	
	public WallModel(int dir) {
		if(!Direction.isDirection(dir)) {
			throw Direction.NOTADIRECTION;
		}
		
		direction = dir;
		tiles = new ArrayList<TileModel>();
	}
}
