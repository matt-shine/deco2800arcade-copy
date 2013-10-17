package deco2800.arcade.burningskies.entities.bullets;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

import deco2800.arcade.burningskies.entities.Ship;
import deco2800.arcade.burningskies.screen.PlayScreen;

public abstract class BulletPattern extends Actor {
	
	protected Ship emitter; // who is firing these things
	protected boolean firing;
	protected float interval; // how often bullets are fired
	protected PlayScreen screen; // so we can spawn things
	private float timer;
	private Vector2 lastEmit; // lag compensation on movement of emitter
	private Vector2 thisEmit = new Vector2();
	
	public BulletPattern(Ship emitter, PlayScreen screen) {
		this.emitter = emitter;
		this.screen = screen;
		firing = false;
	}
	
	@Override
    public void act(float delta) {
		onRender(delta);
        super.act(delta);
	}
	
	/**
	 * Start firing our pattern of bullets
	 */
	public void start() {
		timer = interval; //for instant start time, also rewards player for keyboard spam
		firing = true;
		lastEmit = null;
	}
	
	/**
	 * Stop our barrage, if an enemy/player dies or level complete etc
	 */
	public void stop() {
		firing = false;
	}
	
	/**
	 * Decides whether we need to fire a bullet or not
	 * @param delta time difference
	 */
	public void onRender(float delta) {
		if(!firing) return;
		timer += delta;
		if(timer < interval) return;
		float x = 0, y = 0, timeDiff;
		thisEmit.x = emitter.getCenterX();
		thisEmit.y = emitter.getCenterY();
		if(lastEmit == null) lastEmit = new Vector2(thisEmit);
		// Compensate for frame drops - it happens always, bloody GC
		int loop = (int) Math.floor(timer / interval) - 1;
		for(int i=0;i<=loop;i++) {
			timeDiff = timer-delta-i*interval;
			x = thisEmit.x - timeDiff/timer*((thisEmit.x - lastEmit.x));
			y = thisEmit.y - timeDiff/timer*((thisEmit.y - lastEmit.y));
			fire(timeDiff, x, y);
		}
		timer = timer % interval;
		lastEmit.set(thisEmit);
	}
	
	/**
	 * When our interval has been reached, fire our bullet
	 */
	public abstract void fire(float lag, float x, float y);
	
	/**
	 * 
	 * @return whether or not the barrage has ceased
	 */
	public boolean isFiring() {
		return firing;
	}
}
