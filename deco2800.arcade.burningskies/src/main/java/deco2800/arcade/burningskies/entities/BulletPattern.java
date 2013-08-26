package deco2800.arcade.burningskies.entities;


public class BulletPattern {
	
	private float interval; // how often bullets are fired
	private Ship emitter; // who is firing these things
	
	public BulletPattern(Ship emitter) {
		this.emitter = emitter;
	}
	
	/**
	 * Start firing our pattern of bullets
	 */
	public void start() {
	}
	
	/**
	 * Stop our barrage, if an enemy/player dies or level complete etc
	 */
	public void stop() {
		
	}
	
	/**
	 * Decides whether we need to fire a bullet or not
	 * @param delta time difference
	 */
	public void onRender(float delta) {
		
	}
	
	/**
	 * 
	 * @return whether or not the barrage has ceased
	 */
	public boolean isFiring() {
		return false;
	}
}
