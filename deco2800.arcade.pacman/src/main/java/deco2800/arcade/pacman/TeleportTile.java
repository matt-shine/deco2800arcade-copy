package deco2800.arcade.pacman;

public class TeleportTile extends Tile {

	private Tile target;
	
	public TeleportTile() {
		super();
	}

	public Tile getTarget() {
		return target;
	}

	public void setTarget(Tile target) {
		this.target = target;
	}

	//no render method needed as tile is blank and can use superclass method
}
