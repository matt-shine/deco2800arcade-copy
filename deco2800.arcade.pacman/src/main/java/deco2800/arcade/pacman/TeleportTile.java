package deco2800.arcade.pacman;

public class TeleportTile extends Tile {

	private Tile target;
	private Tile[][] grid;
	
	public TeleportTile(GameMap gameMap) {
		super(gameMap);
		
		// DO stuff to set targets...
		
	}

	public void configureTargets (){
		grid = gameMap.getGrid();
		for (int i = 0; i < grid.length; i++){
			for (int j = 0; j < grid.length; j++){
				if (grid[i][j].getClass() == TeleportTile.class &&
						grid[i][j] != this){
					setTarget(grid[i][j]);
				}
			}
		}
	}
	public Tile getTarget() {
		return target;
	}

	public int getTargetX(){
		return gameMap.getTilePos(target).getX();	
	}
	public int getTargetY(){
		return gameMap.getTilePos(target).getY();	
	}
	
	public void setTarget(Tile target) {
		this.target = target;
	}
	
	public String toString() {
		return "Teleport" + super.toString();
	}

	//no render method needed as tile is blank and can use superclass method
}
