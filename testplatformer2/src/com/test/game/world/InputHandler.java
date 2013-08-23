package com.test.game.world;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.test.game.model.Bullet;
import com.test.game.model.Ship.State;
import com.test.game.model.Ship;
import com.test.game.model.Sword;

public class InputHandler implements InputProcessor{

	World world;
	Ship ship;
	Sword sword;
	Vector2 vec2Touch = new Vector2();
	private boolean acceptInput;
	Vector3 touch = new Vector3();

	//private static final float WALL_ATTACH_LENGTH = 4f;
	//private float wallTime = 0;

	public InputHandler(World world) {
		this.world = world;
		this.ship = world.getShip();
		this.sword = world.getSword();
		acceptInput = true;
	}
	
	@Override
	public boolean keyDown(int keycode) {
		switch(keycode){
			case Keys.LEFT:
				if (acceptInput) {
					ship.getVelocity().x = -Ship.SPEED;
					if (ship.getState() != State.WALL) {
						ship.setState(State.WALK);
						ship.setFacingRight(false);
					}
				}
				break;

			case Keys.RIGHT:
				if (acceptInput) {
					ship.getVelocity().x = Ship.SPEED;
					if (ship.getState() != State.WALL) {
						ship.setState(State.WALK);
						ship.setFacingRight(true);
					}
				}
				break;

			case Keys.Z:
				if ( acceptInput && (ship.getState() == State.IDLE || ship.getState() == State.WALK || ship.getState() == State.WALL) ){
					if (ship.getState() == State.WALL) {
						if (ship.getVelocity().x > 0) {
							ship.setFacingRight(true);
						} else if (ship.getVelocity().x < 0) {
							ship.setFacingRight(false);
						} else {
							ship.setFacingRight(!ship.isFacingRight());
						}
					}
					ship.getVelocity().y = Ship.JUMP_VELOCITY;
					ship.setState(Ship.State.JUMP);
					ship.resetJumpTime();
				}
				break;

			case Keys.X:
				if (!sword.inProgress() && acceptInput) {
					if (ship.getState() != State.WALL) {
						sword.begin(ship.isFacingRight());
					} else {
						sword.begin(!ship.isFacingRight());
					}
				}
				break;

			case Keys.Q:
				ship.setPosition(new Vector2(4, 10));
				break;

			default:
				break;
		}
		return true;
	}

	@Override
	public boolean keyUp(int keycode) {
		switch(keycode){
			case Keys.UP:
				if (ship.getVelocity().y == 1 && acceptInput)
				ship.getVelocity().y = 0;
				break;

			case Keys.DOWN:
				if (ship.getVelocity().y == -1 && acceptInput)
				ship.getVelocity().y = 0;
				break;

			case Keys.LEFT:
				if (ship.getVelocity().x < -1 && acceptInput)
				ship.getVelocity().x = 0;
				break;

			case Keys.RIGHT:
				if (ship.getVelocity().x > 1 && acceptInput)
				ship.getVelocity().x = 0;
				break;

			case Keys.Z:
				ship.clearJumpTime();
				break;

			default:
				break;
		}
		return true;
	}

	public void acceptInput() {
		acceptInput = true;
	}
	
	public void cancelInput() {
		acceptInput = false;
	}
	
	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		//pointer is how many fingers on your touchpad
		touch.set(screenX, screenY, 0);
		world.getRenderer().getCamera().unproject(touch);
		vec2Touch.set(touch.x, touch.y);
		world.addBullet(new Bullet(Bullet.BULLET_SPEED, 0, new Vector2(ship.getPosition().x+ship.getWidth()/2-Bullet.BULLET_SIZE/2, ship.getPosition().y+ship.getHeight()/2),
				Bullet.BULLET_SIZE, Bullet.BULLET_SIZE, new Vector2(vec2Touch.sub(ship.getPosition()).nor())));
		return true;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}

}
