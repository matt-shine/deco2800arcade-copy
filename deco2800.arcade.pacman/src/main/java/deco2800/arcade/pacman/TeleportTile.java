package deco2800.arcade.pacman;

public final class TeleportTile extends Tile {

	private Tile target;
	
	public TeleportTile(GameMap gameMap) {
		super(gameMap);
	}
	
	public Tile getTarget() {
		return target;
	}

	public int getTargetX(){
		return gameMap.getTileCoords(target).getX();	
	}
	
	public int getTargetY(){
		return gameMap.getTileCoords(target).getY();	
	}
	
	public void setTarget(Tile target) {
		this.target = target;
	}
	
	public String toString() {
		return "Teleport" + super.toString();
	}

	//no render method needed as tile is blank and can use superclass method
}
