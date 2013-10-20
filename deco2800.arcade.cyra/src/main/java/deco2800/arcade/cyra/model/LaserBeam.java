package deco2800.arcade.cyra.model;

import java.util.Arrays;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import deco2800.arcade.cyra.world.Sounds;

public class LaserBeam extends Enemy {

	public static final float DEFAULT_LENGTH = 30f;
	private static final float OPENING_LENGTH = 4f;
	
	private boolean stopWhenHitSolid; // just going to ignore this because it'll make it too complicated and I don't need it right now
	private float currentWidth;
	private Vector2 initPos;
	private float count;
	private float maxWidth;
	
	
	public LaserBeam(float rotation, Vector2 initPos, float maxWidth,
			boolean stopWhenHitSolid, float timeToBegin) {
		super(0, rotation, new Vector2(initPos.x-5f, initPos.y-5f), 10f, 10f);
		//collision = new Polygon();
		this.stopWhenHitSolid = stopWhenHitSolid;
		currentWidth = 0.01f;
		this.initPos = initPos;
		//this.rotation = rotation;
		count = -timeToBegin;
		this.maxWidth = maxWidth;
		advanceDuringScenes=true;
		Sounds.playLaserSound(0.5f);
	}
	
	
	/**
	 * Get the bounds of the LaserBeam that can hurt the player as Polygon
	 * @return Polygon bounds to hurt player
	 */
	public Polygon getLaserBounds() {
		if (currentWidth < 0.1f) {
			return null;
		}
		//set points as though facing directly up
		float[] vertices = {initPos.x, initPos.y,
				initPos.x+currentWidth/2, initPos.y+OPENING_LENGTH,
				initPos.x+currentWidth/2, initPos.y+DEFAULT_LENGTH,
				initPos.x-currentWidth/2, initPos.y+DEFAULT_LENGTH,
				initPos.x-currentWidth/2, initPos.y+OPENING_LENGTH
				
				};
		Polygon collision = new Polygon(vertices);
		
		collision.setOrigin(initPos.x, initPos.y);
		
		
		//rotate to correct rotation
		collision.rotate(-90+ rotation);
		
		return collision;
	}

	@Override
	public Array<Enemy> advance(float delta, Player ship, float rank, OrthographicCamera cam) {
		count += delta;
		float endTime = 1f;
		if (count >= 0 && count <= endTime) {
			currentWidth = (maxWidth)/(1f+endTime-count);
			
		}
		if (count >= endTime + 1f) {
			currentWidth = maxWidth/(count-endTime);
			if (currentWidth <= maxWidth * 0.66) {
				position.x = -100f;
				Sounds.stopLaserSound();
				
			}
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
	public void handleDamage(boolean fromRight) {
		;
	}
	
	@Override
	public void handleNoTileUnderneath() {
		
		
	}
	
	public float getCurrentWidth() {
		return currentWidth;
	}
	
	public Vector2 getOriginPosition() {
		return new Vector2(initPos.x, initPos.y);
	}
	
	public void setOriginPosition(Vector2 position) {
		initPos = new Vector2(position);
	}
	
	public float getMaxWidth() {
		return maxWidth;
	}
	
	
}
