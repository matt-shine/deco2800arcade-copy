package deco2800.arcade.pacman;


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
	
	public void dotEaten() {
		exists = false;
	}
	
	public String toString() {
		return "Dot" + super.toString();
	}
}
