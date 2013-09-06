package deco2800.teamgameover.model;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenEquations;
import aurelienribon.tweenengine.TweenManager;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class Walker extends Enemy{
	public final static float WALKER_WIDTH = 0.3f;
	public final static float WALKER_HEIGHT = 0.5f;
	public final static float FRAME_LENGTH = 0.5f;
	
	private TweenManager manager;
	
	private Array<WalkerPart> parts = new Array<WalkerPart>(8); 
	private float frameCount = 0;
	private int keyFrame = 0;

	public Walker(Vector2 pos) {
		super(0, 0, pos, WALKER_WIDTH, WALKER_HEIGHT);
		/*parts.add(new WalkerPart(0, 0, new Vector2(getPosition().x-31.375f/32f, getPosition().y-41.875f/32f)));
		parts.add(new WalkerPart(0, 0, new Vector2(getPosition().x-33.25f/32f, getPosition().y-42.00f/32f)));
		parts.add(new WalkerPart(0, 0, new Vector2(getPosition().x-31.375f/32f, getPosition().y-41.875f/32f)));
		parts.add(new WalkerPart(0, 0, new Vector2(getPosition().x-31.75f/32f, getPosition().y-41.125f/32f)));
		parts.add(new WalkerPart(0, 0, new Vector2(getPosition().x-30.75f/32f, getPosition().y-41.5f/32f)));
		parts.add(new WalkerPart(0, 0, new Vector2(getPosition().x-30.125f/32f, getPosition().y-42.25f/32f)));
		parts.add(new WalkerPart(0, 0, new Vector2(getPosition().x-30.54545f/32f, getPosition().y-40.3636f/32f)));
		parts.add(new WalkerPart(0, 0, new Vector2(getPosition().x-30.9091f/32f, getPosition().y-41.4546f/32f)));
		*/
		parts.add(new WalkerPart(0, 0, new Vector2(getPosition().x, getPosition().y)));
		parts.add(new WalkerPart(1, 0, new Vector2(getPosition().x, getPosition().y)));
		parts.add(new WalkerPart(2, 0, new Vector2(getPosition().x, getPosition().y)));
		parts.add(new WalkerPart(3, 0, new Vector2(getPosition().x, getPosition().y)));
		parts.add(new WalkerPart(4, 0, new Vector2(getPosition().x, getPosition().y)));
		parts.add(new WalkerPart(5, 0, new Vector2(getPosition().x, getPosition().y)));
		parts.add(new WalkerPart(6, 0, new Vector2(getPosition().x, getPosition().y)));
		parts.add(new WalkerPart(7, 0, new Vector2(getPosition().x, getPosition().y)));
		
		Tween.registerAccessor(WalkerPart.class, new PartTween());
		manager = new TweenManager();
	}

	public WalkerPart getPart(int i) {
		return parts.get(i);
	}
	
	@Override
	public void advance(float delta, Ship ship, float rank) {
		//walking animation test
		frameCount += delta;
		if (frameCount > FRAME_LENGTH) {
			keyFrame++;
			frameCount = 0f;
			if (keyFrame == 1) {
				//System.out.println("Keyframe 1");
				Tween.to(parts.get(0), PartTween.POSITION_ROTATION,FRAME_LENGTH).target(getPosition().x+6.09f/64f, getPosition().y-28.696f/64f, 26.565f).ease(
						TweenEquations.easeNone).start(manager);
				Tween.to(parts.get(1), PartTween.POSITION_ROTATION,FRAME_LENGTH).target(getPosition().x+0.2898f/64f, getPosition().y-20.0f/64f, 28.369f).ease(
						TweenEquations.easeNone).start(manager);
				Tween.to(parts.get(2), PartTween.POSITION_ROTATION,FRAME_LENGTH).target(getPosition().x-12.754f/64f, getPosition().y-22.899f/64f, -(360f-334.33f)).ease(
						TweenEquations.easeNone).start(manager);
				Tween.to(parts.get(3), PartTween.POSITION_ROTATION,FRAME_LENGTH).target(getPosition().x-8.1159f/64f, getPosition().y-16.876f/64f, -(360f-340.403f)).ease(
						TweenEquations.easeNone).start(manager);
				Tween.to(parts.get(4), PartTween.POSITION_ROTATION,FRAME_LENGTH).target(getPosition().x-11.540f/64f, getPosition().y+28.91f/64f, 0f).ease(
						TweenEquations.easeNone).start(manager);
				Tween.to(parts.get(5), PartTween.POSITION_ROTATION,FRAME_LENGTH).target(getPosition().x+4.267f/64f, getPosition().y-1f/64f, -(360f-328.806f)).ease(
						TweenEquations.easeNone).start(manager);
				Tween.to(parts.get(6), PartTween.POSITION_ROTATION,FRAME_LENGTH).target(getPosition().x-8.909f/64f, getPosition().y-4.455f/64f, 0f).ease(
						TweenEquations.easeNone).start(manager);
				Tween.to(parts.get(7), PartTween.POSITION_ROTATION,FRAME_LENGTH).target(getPosition().x-13.125f/64f, getPosition().y-4.193f/64f, 26.814f).ease(
						TweenEquations.easeNone).start(manager);
				/*Tween.to(parts.get(0), PartTween.POSITION_ROTATION,0.2f).target(getPosition().x-0.8f, getPosition().y+9.0f, 31.402f).ease(
						TweenEquations.easeNone).start(manager);
				Tween.to(parts.get(1), PartTween.POSITION_ROTATION,0.2f).target(getPosition().x-1.1f, getPosition().y-8.6f, 27.582f).ease(
						TweenEquations.easeNone).start(manager);
				Tween.to(parts.get(2), PartTween.POSITION_ROTATION,0.2f).target(getPosition().x+1.3f, getPosition().y+10.3f, 360f-349.33f).ease(
						TweenEquations.easeNone).start(manager);
				Tween.to(parts.get(3), PartTween.POSITION_ROTATION,0.2f).target(getPosition().x+5.0f/32f, getPosition().y-10.8f/32f, 360f-354.403f).ease(
						TweenEquations.easeNone).start(manager);
				Tween.to(parts.get(4), PartTween.POSITION_ROTATION,0.2f).target(getPosition().x+4.3f/32f, getPosition().y+16f/32f, 0f).ease(
						TweenEquations.easeNone).start(manager);
				Tween.to(parts.get(5), PartTween.POSITION_ROTATION,0.2f).target(getPosition().x+3.4f/32f, getPosition().y+13.4f/32f, 360f-309.806f).ease(
						TweenEquations.easeNone).start(manager);
				Tween.to(parts.get(6), PartTween.POSITION_ROTATION,0.2f).target(getPosition().x-3.4f/32f, getPosition().y+12.5f/32f, 0f).ease(
						TweenEquations.easeNone).start(manager);
				Tween.to(parts.get(7), PartTween.POSITION_ROTATION,0.2f).target(getPosition().x+3.7f/32f, getPosition().y+16f/32f, 48.814f).ease(
						TweenEquations.easeNone).start(manager);*/
			} else if (keyFrame == 2 || keyFrame == 4) {
				//System.out.println("Keyframe 2");
				Tween.to(parts.get(0), PartTween.POSITION_ROTATION,FRAME_LENGTH).target(getPosition().x+2.0f/64f, getPosition().y-25.8f/64f, 0f).ease(
						TweenEquations.easeNone).start(manager);
				Tween.to(parts.get(1), PartTween.POSITION_ROTATION,FRAME_LENGTH).target(getPosition().x+1.2f/64f, getPosition().y-17.1f/64f, 0f).ease(
						TweenEquations.easeNone).start(manager);
				Tween.to(parts.get(2), PartTween.POSITION_ROTATION,FRAME_LENGTH).target(getPosition().x-10.7f/64f, getPosition().y-25.5f/64f, 0).ease(
						TweenEquations.easeNone).start(manager);
				Tween.to(parts.get(3), PartTween.POSITION_ROTATION,FRAME_LENGTH).target(getPosition().x-8.1159f/64f, getPosition().y-16.876f/64f, 0).ease(
						TweenEquations.easeNone).start(manager);
				Tween.to(parts.get(4), PartTween.POSITION_ROTATION,FRAME_LENGTH).target(getPosition().x-11.890f/64f, getPosition().y+28.91f/64f, 0f).ease(
						TweenEquations.easeNone).start(manager);
				Tween.to(parts.get(5), PartTween.POSITION_ROTATION,FRAME_LENGTH).target(getPosition().x+4.06f/64f, getPosition().y-1.2f/64f, 0).ease(
						TweenEquations.easeNone).start(manager);
				Tween.to(parts.get(6), PartTween.POSITION_ROTATION,FRAME_LENGTH).target(getPosition().x-8.409f/64f, getPosition().y+3.8455f/64f, 0f).ease(
						TweenEquations.easeNone).start(manager);
				Tween.to(parts.get(7), PartTween.POSITION_ROTATION,FRAME_LENGTH).target(getPosition().x-12.125f/64f, getPosition().y-1.193f/64f, 0f).ease(
						TweenEquations.easeNone).start(manager);
				if (keyFrame == 4) keyFrame = 0;
			} else if (keyFrame == 3) {
				//System.out.println("Keyframe 3");
				Tween.to(parts.get(0), PartTween.POSITION_ROTATION,FRAME_LENGTH).target(getPosition().x-0.3f/64f, getPosition().y-23.29f/64f, 329.8f-360f).ease(
						TweenEquations.easeNone).start(manager);
				Tween.to(parts.get(1), PartTween.POSITION_ROTATION,FRAME_LENGTH).target(getPosition().x+2.3f/64f, getPosition().y-13.0f/64f, 330.1f-360f).ease(
						TweenEquations.easeNone).start(manager);
				Tween.to(parts.get(2), PartTween.POSITION_ROTATION,FRAME_LENGTH).target(getPosition().x-5.0f/64f, getPosition().y-25.2f/64f, 27.4f).ease(
						TweenEquations.easeNone).start(manager);
				Tween.to(parts.get(3), PartTween.POSITION_ROTATION,FRAME_LENGTH).target(getPosition().x-8.1159f/64f, getPosition().y-16.876f/64f, 30.5f).ease(
						TweenEquations.easeNone).start(manager);
				Tween.to(parts.get(4), PartTween.POSITION_ROTATION,FRAME_LENGTH).target(getPosition().x-11.540f/64f, getPosition().y+28.91f/64f, 0f).ease(
						TweenEquations.easeNone).start(manager);
				Tween.to(parts.get(5), PartTween.POSITION_ROTATION,FRAME_LENGTH).target(getPosition().x+3.48f/64f, getPosition().y-6.1f/64f, 47.1f).ease(
						TweenEquations.easeNone).start(manager);
				Tween.to(parts.get(6), PartTween.POSITION_ROTATION,FRAME_LENGTH).target(getPosition().x-8.909f/64f, getPosition().y-4.455f/64f, 0f).ease(
						TweenEquations.easeNone).start(manager);
				Tween.to(parts.get(7), PartTween.POSITION_ROTATION,FRAME_LENGTH).target(getPosition().x-12.08f/64f, getPosition().y-0.2f/64f, 330f-360f).ease(
						TweenEquations.easeNone).start(manager);
			}
		}
		manager.update(delta);
		for (WalkerPart wp: parts) {
			wp.update(ship);
		}
	}

	@Override
	public boolean isSolid() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void handleTopOfMovingPlatform(MovablePlatform movablePlatform) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void handleXCollision(Rectangle tile) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void handleYCollision(Rectangle tile, boolean onMovablePlatform,
			MovablePlatform movablePlatform) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void handleNoTileUnderneath() {
		// TODO Auto-generated method stub
		
	}

}
