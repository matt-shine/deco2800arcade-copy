package deco2800.arcade.burningskies.screen;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Input.Keys;

import deco2800.arcade.burningskies.entities.PlayerShip;

public class PlayerInputProcessor extends InputAdapter {
	
	private boolean checkL = false, checkR = false, checkU = false, checkD = false;
	private PlayerShip player;
	
	public PlayerInputProcessor(PlayerShip player) {
		this.player = player;
	}
	
	@Override
	public boolean keyDown(int keycode) {
		switch(keycode) {
		case Keys.W:
			checkU = true;
			player.setUp(true);
			break;
		case Keys.A:
			checkL = true;
			player.setLeft(true);
			break;
		case Keys.S:
			checkD = true;
			player.setDown(true);
			break;
		case Keys.D:
			checkR = true;
			player.setRight(true);
			break;
		case Keys.SPACE:
			player.setShooting(true);
			break;
		}
		return false;
	}
	
	@Override
	public boolean keyUp(int keycode) {
		switch(keycode) {
		case Keys.W:
			player.setUp(false);
			checkU = false;
			if(checkD) player.setDown(true);
			break;
		case Keys.A:
			player.setLeft(false);
			checkL = false;
			if(checkR) player.setRight(true);
			break;
		case Keys.S:
			player.setDown(false);
			checkD = false;
			if(checkU) player.setUp(true);
			break;
		case Keys.D:
			player.setRight(false);
			checkR = false;
			if(checkL) player.setLeft(true);
			break;
		case Keys.SPACE:
			player.setShooting(false);
			break;
		}
		return false;
	}
	
	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		if(button == Input.Buttons.LEFT) {
			player.setShooting(true);
		}
		return false;
	}
	
	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		if(button == Input.Buttons.LEFT) {
			player.setShooting(false);
		}
		return false;
	}

}
