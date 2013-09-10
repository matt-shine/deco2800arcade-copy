package deco2800.arcade.burningskies.entities;

import java.util.ArrayList;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class GameMap extends Image {
	
	// Our map timer
	public float timer = 0;
	// How long the map lasts
	private final float mapTime;
	// How fast the map travels across the screen
	private final float mapSpeed;
	// A list of enemy spawn times
	private ArrayList<Float> spawnTimes;

	
	public GameMap(String filename) {
		//TODO: Remove this and make it dynamic
		super(new Texture(Gdx.files.internal("maps/demomap.png")));
		//TODO: load mapfile, initialise TileMap and textures etc
		mapTime = 0; //TODO: load from map file
		mapSpeed = 40; //TODO: load from map file
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
		setY(getY() - delta*mapSpeed);
	}
	
	public float getMapTime() {
		return mapTime;
	}
	
	public float getMapSpeed() {
		return mapSpeed;
	}

}
