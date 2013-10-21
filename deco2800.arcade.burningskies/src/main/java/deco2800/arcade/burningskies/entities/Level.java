package deco2800.arcade.burningskies.entities;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import deco2800.arcade.burningskies.screen.PlayScreen;

public class Level extends Image {
	
	// Our map timer
	public float timer = 0;
	// How long the map lasts
	private final float mapTime;
	// A list of enemy spawn times
	private ArrayList<Float> spawnTimes;

	private PlayerShip player;
	
	private static Texture parallax[] = {
		new Texture(Gdx.files.internal("images/maps/parallax0.png")),
		new Texture(Gdx.files.internal("images/maps/parallax1.png")),
		new Texture(Gdx.files.internal("images/maps/parallax2.png"))
	};

	
	public Level(PlayScreen screen) {
		//TODO: Remove this and make it dynamic
		super(new Texture(Gdx.files.internal("images/maps/space_720p.png")));
		this.player = screen.getPlayer();
		mapTime = 0; //TODO: load from map file
		//TODO: Load spawn times
		spawnTimes = new ArrayList<Float>();
	}
	
	@Override
	public void draw(SpriteBatch batch, float alpha) {
		super.draw(batch, alpha);
		// For the amount of effort this took, shit this is pretty
		batch.draw(parallax[0], player.getX()/1280*128*-1, player.getY()/720*72*-1, 1408, 792);
		batch.draw(parallax[1], player.getX()/1280*320*-1, player.getY()/720*180*-1, 1600, 900);
		batch.draw(parallax[2], player.getX()/1280*512*-1, player.getY()/720*288*-1, 1792, 1008);
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
