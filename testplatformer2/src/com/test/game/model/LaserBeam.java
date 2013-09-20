package com.test.game.model;

import java.util.Arrays;

import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

//this probably shouldn't extend Enemy but not sure how else to do it within reasonable time
public class LaserBeam extends Enemy {

	private static final float DEFAULT_LENGTH = 30f;
	private static final float OPENING_LENGTH = 4f;
	
	//private Polygon collision;
	private boolean stopWhenHitSolid; // just going to ignore this because it'll make it too complicated and I don't need it right now
	private float currentWidth;
	private Vector2 initPos;
	private float rotation;
	private float count;
	private float maxWidth;
	
	
	public LaserBeam(float rotation, Vector2 initPos, float maxWidth,
			boolean stopWhenHitSolid) {
		super(0, rotation, new Vector2(initPos.x-5f, initPos.y-5f), 10f, 10f);
		//collision = new Polygon();
		this.stopWhenHitSolid = stopWhenHitSolid;
		currentWidth = 0.01f;
		this.initPos = initPos;
		this.rotation = rotation;
		count = -3f;
		this.maxWidth = maxWidth;
		System.out.println("Made new laser beam");
	}
	
	
	
	public Polygon getLaserBounds() {
		if (currentWidth < 0.1f) {
			return null;
		}
		Polygon collision = new Polygon();
		collision.setOrigin(initPos.x, initPos.y);
		//set points as though facing directly up
		/*float[] vertices = {currentWidth/2, OPENING_LENGTH,
				-currentWidth/2, OPENING_LENGTH,
				currentWidth/2, DEFAULT_LENGTH,
				-currentWidth/2, DEFAULT_LENGTH};*/
		float[] vertices = {initPos.x, initPos.y,
				initPos.x+currentWidth/2, initPos.y+OPENING_LENGTH,
				initPos.x+currentWidth/2, initPos.y+DEFAULT_LENGTH,
				initPos.x-currentWidth/2, initPos.y+DEFAULT_LENGTH,
				initPos.x-currentWidth/2, initPos.y+OPENING_LENGTH
				
				};
		
		collision.setVertices(vertices);
		
		//rotate to correct rotation
		collision.rotate(-90+ rotation);
		System.out.println(Arrays.toString(collision.getVertices()));
		return collision;
	}

	@Override
	public Array<Enemy> advance(float delta, Ship ship, float rank) {

		count += delta;
		if (count >= 0 && count <= 1.5f) {
			currentWidth = (maxWidth)/(2.5f-count);
			
		}
		if (count >= 2.5) {
			currentWidth = maxWidth/(count-1.5f);
		}
		return null;
	}

	@Override
	public Array<Rectangle> getPlayerDamageBounds() {
		Array<Rectangle> playerDamageRectangle = new Array<Rectangle>();
		return playerDamageRectangle;
	}
	
	@Override
	public boolean isSolid() {
		return false;
	}

	@Override
	public void handleNoTileUnderneath() {
		
		
	}
	
}
