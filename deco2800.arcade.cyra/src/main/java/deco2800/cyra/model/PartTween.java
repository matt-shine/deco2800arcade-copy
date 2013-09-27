package deco2800.cyra.model;

import aurelienribon.tweenengine.TweenAccessor;

public class PartTween implements TweenAccessor<MovableEntity> {

	public static final int POSITION = 1;
	public static final int POSITION_ROTATION = 2;
	/*@Override
	public int getValues(MovableEntity target, int tweenType, float[] returnValues) {
		switch(tweenType) {
		case POSITION_ROTATION:
			returnValues[2] = target.getRotation();
		case POSITION:
			returnValues[0] = target.getPosition().x;
			returnValues[1] = target.getPosition().y;
			return 1;
		default: return 0;
		}
		
	}

	@Override
	public void setValues(MovableEntity target, int tweenType, float[] newValues) {
		switch(tweenType) {
		case POSITION_ROTATION:
			target.setRotation(newValues[2]);
		case POSITION:
			target.getPosition().x = newValues[0];
			target.getPosition().y = newValues[1];
			break;
		default:
			assert false;
			break;
		}
		
	}*/
	
	@Override
	public int getValues(MovableEntity target, int tweenType, float[] returnValues) {
		switch(tweenType) {
		case POSITION:
			returnValues[0] = target.getPosition().x;
			returnValues[1] = target.getPosition().y;
			return 2;
		case POSITION_ROTATION:
			returnValues[0] = target.getPosition().x;
			returnValues[1] = target.getPosition().y;
			returnValues[2] = target.getRotation();
			return 3;
		default: return 0;
		}
		
	}

	@Override
	public void setValues(MovableEntity target, int tweenType, float[] newValues) {
		switch(tweenType) {
		case POSITION:
			target.getPosition().x = newValues[0];
			target.getPosition().y = newValues[1];
			break;
		case POSITION_ROTATION:
			target.getPosition().x = newValues[0];
			target.getPosition().y = newValues[1];
			target.setRotation(newValues[2]);
			break;
		default:
			assert false;
			break;
		}
		
	}

}
