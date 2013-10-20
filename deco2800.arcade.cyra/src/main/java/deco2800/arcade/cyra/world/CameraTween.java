package deco2800.arcade.cyra.world;

import aurelienribon.tweenengine.TweenAccessor;

import deco2800.arcade.cyra.model.MovableEntity;

public class CameraTween implements TweenAccessor<ParallaxCamera>{
		public static final int POSITION = 1;
		public static final int POSITION_ROTATION = 2;
		
		
		@Override
		public int getValues(ParallaxCamera target, int tweenType, float[] returnValues) {
			switch(tweenType) {
			case POSITION:
				returnValues[0] = target.position.x;
				returnValues[1] = target.position.y;
				return 2;
			/*case POSITION_ROTATION:
				returnValues[0] = target.position.x;
				returnValues[1] = target.position.y;
				returnValues[2] = target.getRotation();
				return 3;*/
			default: return 0;
			}
			
		}

		@Override
		public void setValues(ParallaxCamera target, int tweenType, float[] newValues) {
			switch(tweenType) {
			case POSITION:
				target.position.x = newValues[0];
				target.position.y = newValues[1];
				break;
			/*case POSITION_ROTATION:
				target.getPosition().x = newValues[0];
				target.getPosition().y = newValues[1];
				target.setRotation(newValues[2]);
				break;*/
			default:
				assert false;
				break;
			}
			
		}
}
