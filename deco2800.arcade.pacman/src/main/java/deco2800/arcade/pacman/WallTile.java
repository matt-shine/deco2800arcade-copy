package deco2800.arcade.pacman;

public class WallTile extends Tile {

	public static void main(String[] args) {
		WallTile a = new WallTile(8);
		//a.north = a;
		System.out.println(a.getNorthType());
	}
	
	
	public WallTile(int sideLength) {
		super(sideLength);
	}

}
