package deco2800.arcade.burningskies;

public class BulletPattern {
	
	private int interval; // how often bullets are fired
	private Ship emitter; // who is firing these things
	
	public BulletPattern(Ship emitter) {
		this.emitter = emitter;
	}
	
	/**
	 * Start firing our pattern of bullets
	 */
	public void fire() {
	}
	
	/**
	 * Stop our barrage, if an enemy/player dies or level complete etc
	 */
	public void stop() {
		
	}
	
	/**
	 * 
	 * @return whether or not the barrage has ceased
	 */
	public boolean isFiring() {
		return false;
	}
}
