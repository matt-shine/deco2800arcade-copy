package deco2800.arcade.burningskies.screen;

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
		case Keys.LEFT:
			checkL = true;
			player.setLeft(true);
			break;
		case Keys.RIGHT:
			checkR = true;
			player.setRight(true);
			break;
		case Keys.UP:
			checkU = true;
			player.setUp(true);
			break;
		case Keys.DOWN:
			checkD = true;
			player.setDown(true);
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
		case Keys.LEFT:
			player.setLeft(false);
			checkL = false;
			if(checkR) player.setRight(true);
			break;
		case Keys.RIGHT:
			player.setRight(false);
			checkR = false;
			if(checkL) player.setLeft(true);
			break;
		case Keys.DOWN:
			player.setDown(false);
			checkD = false;
			if(checkU) player.setUp(true);
			break;
		case Keys.UP:
			player.setUp(false);
			checkU = false;
			if(checkD) player.setDown(true);
			break;
		case Keys.SPACE:
			player.setShooting(false);
			break;
		}
		return false;
	}

}
