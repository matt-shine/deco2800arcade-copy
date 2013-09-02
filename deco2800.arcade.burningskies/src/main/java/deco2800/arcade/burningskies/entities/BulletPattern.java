package deco2800.arcade.burningskies.entities;


public abstract class BulletPattern {
	
	private float interval; // how often bullets are fired
	private float timer;
	private Ship emitter; // who is firing these things
	private boolean firing;
	
	public BulletPattern(Ship emitter) {
		this.emitter = emitter;
		firing = false;
	}
	
	/**
	 * Start firing our pattern of bullets
	 */
	public void start() {
		timer = 0;
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
	public abstract void onRender(float delta);
	
	/**
	 * 
	 * @return whether or not the barrage has ceased
	 */
	public boolean isFiring() {
		return firing;
	}
}
