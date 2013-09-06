package deco2800.arcade.burningskies.entities;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.graphics.Texture;

public abstract class Ship  extends Image {

	protected int health;	
	protected Vector2 velocity;
	protected Vector2 position;
	
	/**
	 * Basic constructor for a ship.
	 * @ensure health && velocity > 0
	 */
	public Ship(int health, Texture image, Vector2 position) {
		super(image);
		this.health = health;
		this.position = position;
		velocity = new Vector2(0,0);
	}
	/**
	 * Checks if the current ship is alive.
	 */
	public boolean isAlive() {
		if (health <= 0) {
			return false;
		} else return true;
	}
	
	@Override
    public void act(float delta) {
		onRender(delta);
        super.act(delta);
	}
	
	/**
	 * Modifies the current health of the ship accordingly.
	 * May be negative or positive value.
	 */
	public void setHealth(int healthchange) {
		this.health += healthchange;
	}
	
	public boolean inBounds() {
		float left = getX() + getWidth();
		float right = getY() + getHeight();
		if(left < 0 || right < 0 || getX() > getStage().getWidth() || getY() > getStage().getHeight() ) {
			return true;
		} else return false;
	}
	
	/**
	 * What do do every frame. Perhaps bounds checking etc.
	 * You really want to override this.
	 */
	void onRender(float delta) {
		position.add( velocity.x * delta, velocity.y * delta );
		setX(position.x);
		setY(position.y);
	}
}
