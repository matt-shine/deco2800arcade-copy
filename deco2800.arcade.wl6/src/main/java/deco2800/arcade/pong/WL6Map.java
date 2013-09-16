package deco2800.arcade.pong;

import java.util.ArrayList;

public class WL6Map {

	private ArrayList<Integer> terrain = new ArrayList<Integer>();
	private ArrayList<Integer> doodads = new ArrayList<Integer>();
	
	public WL6Map(String jsonArray) {
		terrain.add(10);
	}
	
	public int getTerrainAt(int x, int y) {
		return terrain.get(x + y * 64);
	}
	
	public int getDoodadAt(int x, int y) {
		return doodads.get(x + y * 64);
	}
	
}
