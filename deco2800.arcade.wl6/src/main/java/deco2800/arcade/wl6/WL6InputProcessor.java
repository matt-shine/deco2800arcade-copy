package deco2800.arcade.wl6;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;

public class WL6InputProcessor implements InputProcessor {

	private WL6 game = null;
	private GameModel model = null;
	
	
	
	public WL6InputProcessor(WL6 game, GameModel model) {
		this.game = game;
		this.model = model;
	}
	
	
	@Override
	public boolean keyDown(int arg0) {
		if (arg0 == Keys.NUM_1) {
			game.toggleDebugMode();
		}
		if (arg0 == Keys.W) {
			model.getPlayer().setX(model.getPlayer().getPos().x + 1);
		}
		if (arg0 == Keys.S) {
			model.getPlayer().setX(model.getPlayer().getPos().x - 1);
		}
		if (arg0 == Keys.A) {
			model.getPlayer().setY(model.getPlayer().getPos().y - 1);
		}
		if (arg0 == Keys.D) {
			model.getPlayer().setY(model.getPlayer().getPos().y + 1);
		}
		return false;
	}

	@Override
	public boolean keyTyped(char arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyUp(int arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int arg0, int arg1) {
		model.getPlayer().setAngle(arg0);
		return false;
	}

	@Override
	public boolean scrolled(int arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchUp(int arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub
		return false;
	}

}
