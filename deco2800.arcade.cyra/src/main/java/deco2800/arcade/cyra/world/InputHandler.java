package deco2800.arcade.cyra.world;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import deco2800.arcade.cyra.model.Player.State;
import deco2800.arcade.cyra.model.Player;
import deco2800.arcade.cyra.model.Sword;

/** InputHandler class describes the Controller component in this game's MVC 
 * model. It handles user input and run the appropriate action associated with
 * that specific input.
 * 
 * @author Game Over
 */
public class InputHandler implements InputProcessor{
	private World world;
	private Player ship;
	private Sword sword;
	private boolean acceptInput;

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
					ship.moveLeft();
				}
				break;

			case Keys.RIGHT:
				if (acceptInput) {
					ship.moveRight();
				}
				break;

			case Keys.Z:
				if ( acceptInput && (ship.getState() == State.IDLE || ship.getState() == State.WALK || ship.getState() == State.WALL) ){
					world.incrementJumps();
					ship.jump();
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
				world.resetLevel();
				break;
			case Keys.W:
				ship.getPosition().x = 599f;
				break;
			case Keys.E:
				ship.getPosition().x = 220f;
				ship.getPosition().y = 60f;
				break;
			case Keys.P:
				world.togglePause();
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
				if (ship.getVelocity().y == 1 && acceptInput) {
					ship.getVelocity().y = 0;
				}
				break;

			case Keys.DOWN:
				if (ship.getVelocity().y == -1 && acceptInput) {
					ship.getVelocity().y = 0;
				}
				break;

			case Keys.LEFT:
				if (ship.getVelocity().x < -1 && acceptInput) {
					ship.getVelocity().x = 0;
				}
				break;

			case Keys.RIGHT:
				if (ship.getVelocity().x > 1 && acceptInput) {
					ship.getVelocity().x = 0;
				}
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
