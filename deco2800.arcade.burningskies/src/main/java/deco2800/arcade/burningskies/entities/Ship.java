package deco2800.arcade.burningskies.entities;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.graphics.Texture;

public abstract class Ship extends Entity {

	protected float health;	
	protected Vector2 velocity;
	protected Vector2 position;
	protected float flash = 0f;
	private boolean godMode;
	private float gModeTimer;
	private final float gModeLimit = 5;
	
	/**
	 * Basic constructor for a ship.
	 * @ensure health && velocity > 0
	 */
	public Ship(int health, Texture image, Vector2 position) {
		super(image);
		this.health = health;
		this.position = position;
		velocity = new Vector2(0,0);
		godMode = false;
		gModeTimer = 0f;
	}
	/**
	 * Checks if the current ship is alive.
	 */
	public boolean isAlive() {
		if (health <= 0) {
			return false;
		} else return true;
	}
	
	public float getHealth() {
		return health;
	}
	
	@Override
    public void act(float delta) {
		onRender(delta);
        super.act(delta);
	}
	
	@Override
	public boolean remove() {
		return super.remove();
	}
	
	/**
	 * Damages the ship if not in the godMode state.
	 * @ensure healthchange > 0
	 */
	public void damage(float healthchange) {
		if(!godMode)
			this.health -= healthchange;
		flash = 1f;
	}

	/**
	 *  Heals the ship.
	 *  @ensure healthchange > 0
	 */
	public void heal(int healthchange) {
		this.health += healthchange;
	}
	
	/**
	 * Checks where the ship is still within the bounds of the playing screen.
	 */
	public boolean inBounds() {
		float left = getX() + getWidth();
		float right = getY() + getHeight();
		if(left < 0 || right < 0 || getX() > getStage().getWidth() || getY() > getStage().getHeight() ) {
			return true;
		} else return false;
	}
	
	/**
	 * What to do every frame.
	 * Make sure to super.onRender so you implement damage flashes
	 */
	public void onRender(float delta) {
		if(godMode) {
			setColor(0, 1, 0, 1);
			
			if(gModeTimer >= gModeLimit) {
				godMode = false;
				gModeTimer = 0;
			}

			gModeTimer += delta;
			return;
		}
		
		if(flash > 0) {
			setColor(1, 1-flash, 1-flash, 1);
			flash -= delta*25;
		} else {
			setColor(1, 1, 1, 1);
		}
		
		
	}
	
	/**
	 * Sets the ships state to be invulnerable to damage.
	 */
	public void setGodMode(boolean b) {
		godMode = b;
		gModeTimer = 0;
	}
	
}
