package deco2800.arcade.cyra.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import deco2800.arcade.cyra.world.Sounds;

public class WarningOverlay extends Enemy {
	
	private Vector2 initPos;
	private float wrapLength;
	private float scale;
	private boolean leftRepeat;
	private int count2;
	
	private float count;

	public WarningOverlay(Vector2 startPos,float wrapLength, float scale) {
		super(0, 0, new Vector2(startPos), 5*scale, 1*scale);
		this.scale = scale;
		this.wrapLength = wrapLength;
		this.initPos = new Vector2(startPos);
		count = 0f;
		count2=0;
		leftRepeat = true;
		advanceDuringScenes=true;
		Sounds.playWarningSound(0.5f);
	}

	@Override
	public Array<Enemy> advance(float delta, Player ship, float rank,
			OrthographicCamera cam) {
		position.x -= delta * 50f;
		if (leftRepeat && position.x < initPos.x - wrapLength) {
			leftRepeat = false;
		} else if (!leftRepeat && position.x < initPos.x - wrapLength - 10f) {
			position.x = initPos.x;
			leftRepeat = true;
		}
		count += delta;
		if (count > 0.6f) {
			Sounds.playWarningSound(0.5f);
			count = 0f;
			count2++;
			if (count2==4) {
				position.x = -40;
			}
		}
		return null;
	}

	public Vector2 getPositionOnScreen(float pixelsPerUnit) {
		Vector2 out = new Vector2(Gdx.graphics.getWidth()-pixelsPerUnit*(initPos.x-position.x), Gdx.graphics.getHeight()/2);
		return out;
	}
	
	public boolean isLeftRepeat() {
		return leftRepeat;
	}
	
	public float getScale() {
		return scale;
	}
	
	@Override
	public boolean isSolid() {
		return false;
	}

	@Override
	public void handleNoTileUnderneath() {
		;
		
	}
	
	@Override
	public Array<Rectangle> getPlayerDamageBounds() {
		return new Array<Rectangle>();
	}
	
	@Override
	public void handleDamage(boolean fromRight) {
		
	}
	
	

}
