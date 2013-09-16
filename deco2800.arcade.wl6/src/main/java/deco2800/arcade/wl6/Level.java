package deco2800.arcade.wl6;

import java.util.ArrayList;

public class Level {

	private ArrayList<Integer> terrain = new ArrayList<Integer>();
	private ArrayList<Integer> doodads = new ArrayList<Integer>();
	
	public Level(String jsonArray) {
		for (int i = 0; i < 64 * 64; i++) {
			terrain.add((int)Math.round(Math.random() * 4));
		}
	}
	
	public int getTerrainAt(int x, int y) {
		return terrain.get(x + y * 64);
	}
	
	public int getDoodadAt(int x, int y) {
		return doodads.get(x + y * 64);
	}
	
}
