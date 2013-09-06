package deco2800.arcade.burningskies.entities;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;

public abstract class BulletPattern extends Actor {
	
	protected float interval; // how often bullets are fired
	protected float timer;
	protected Ship emitter; // who is firing these things
	protected Stage stage; // so we can spawn things
	protected boolean firing;
	
	public BulletPattern(Stage stage, Ship emitter) {
		this.emitter = emitter;
		this.stage = stage;
		stage.addActor(this);
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
		timer = 0;
		firing = true;
	}
	
	/**
	 * Stop our barrage, if an enemy/player dies or level complete etc
	 */
	public void stop() {
		timer = 0;
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
		// Compensate for frame drops - it happens always, bloody java
		int loop = (int) Math.floor(timer / interval) - 1;
		for(int i=0;i<=loop;i++) {
			fire((float) (timer-delta-i*interval));
		}
		timer = timer % interval;
	}
	
	/**
	 * When our interval has been reached, fire our bullet
	 */
	public abstract void fire(float lag);
	
	/**
	 * 
	 * @return whether or not the barrage has ceased
	 */
	public boolean isFiring() {
		return firing;
	}
}
