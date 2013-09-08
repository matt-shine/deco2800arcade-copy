package com.test.game.world;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;

/** Reference:
 * http://libgdx.googlecode.com/svn/trunk/tests/gdx-tests/src/com/badlogic/gdx/tests/ParallaxTest.java
 * 
 *
 */
public class ParallaxCamera extends OrthographicCamera{
	
		Matrix4 parallaxView = new Matrix4();
		Matrix4 parallaxCombined = new Matrix4();
		Vector3 tmp = new Vector3();
		Vector3 tmp2 = new Vector3();
		
		private boolean followShip;

		public ParallaxCamera (float viewportWidth, float viewportHeight) {
			super(viewportWidth, viewportHeight);
			followShip = true;
		}

		public Matrix4 calculateParallaxMatrix (float parallaxX, float parallaxY) {
			update();
			tmp.set(position);
			tmp.x *= parallaxX;
			tmp.y *= parallaxY;

			parallaxView.setToLookAt(tmp, tmp2.set(tmp).add(direction), up);
			parallaxCombined.set(projection);
			Matrix4.mul(parallaxCombined.val, parallaxView.val);
			return parallaxCombined;
		}
	
		public void setFollowShip(boolean followShip) {
			this.followShip = followShip;
		}
		
		public boolean isFollowingShip() {
			return followShip;
		}
}
