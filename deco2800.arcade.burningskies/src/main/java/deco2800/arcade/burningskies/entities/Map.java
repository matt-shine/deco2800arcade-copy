package deco2800.arcade.burningskies.entities;

import java.util.ArrayList;

import com.badlogic.gdx.scenes.scene2d.Actor;

public class Map extends Actor {
	
	public float timer;
	private final float mapTime;
	private final float mapSpeed;
	private ArrayList<Float> spawnTimes;
	
	public Map(String filename) {
		//TODO: load mapfile, initialise TileMap and textures etc
		mapTime = 0; //TODO: load from map file
		mapSpeed = 0;
		//TODO: Load spawn times
		spawnTimes = new ArrayList<Float>();
	}
	
	@Override
	public void act(float delta) {
		//TODO: Move the map, spawn enemies if needed, etc
		timer += delta;
		for(float time : spawnTimes) {
			if(timer > time) {
				//TODO: SPAWN ENEMY
				spawnTimes.remove(time);
			}
		}
	}
	
	public float getMapTime() {
		return mapTime;
	}

}
