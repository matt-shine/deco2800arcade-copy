package deco2800.arcade.burningskies.entities;

import java.util.ArrayList;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class Level extends Image {
	
	// Our map timer
	public float timer = 0;
	// How long the map lasts
	private final float mapTime;
	// A list of enemy spawn times
	private ArrayList<Float> spawnTimes;

	
	public Level(String filename) {
		//TODO: Remove this and make it dynamic
		super(new Texture(Gdx.files.internal("images/maps/space_720p.png")));
		mapTime = 0; //TODO: load from map file
		//TODO: Load spawn times
		spawnTimes = new ArrayList<Float>();
	}
	
	
	@Override
	public void act(float delta) {
		//TODO: Spawn enemies if needed, etc
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
	
	public float getTimer() {
		return timer;
	}
}
