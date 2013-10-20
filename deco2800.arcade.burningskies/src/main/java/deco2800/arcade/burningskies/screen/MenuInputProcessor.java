package deco2800.arcade.burningskies.screen;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Input.Keys;

import deco2800.arcade.burningskies.BurningSkies;
import deco2800.arcade.burningskies.Configuration;
import deco2800.arcade.client.ArcadeSystem;


public class MenuInputProcessor extends InputAdapter {
	
	private BurningSkies game;
	private static int buttonSelected = 0;
	private static Boolean keyboardSelection = false;
	
	private int[] secret = {0,0,0,0,0,0,0,0,0,0,0};
	private int sCount = 0;
	private int[] sol = {19,19,20,20,21,22,21,22,30,29,66};
	
	public MenuInputProcessor(BurningSkies game) {
		this.game = game;
	}
	
	@Override
	public boolean keyDown(int keycode) {
		secret[sCount++] = keycode;
		sCount = sCount % secret.length;
		test();
		switch(keycode) {
		case Keys.RIGHT:
			buttonSelected = ((buttonSelected + 1) % 5 + 5) % 5;
			keyboardSelection = true;
			break;
		case Keys.LEFT:
			buttonSelected = ((buttonSelected - 1) % 5 + 5) % 5;
			keyboardSelection = true;
			break;
		case Keys.ENTER:
			if (buttonSelected == 0) {
				keyboardSelection = false;
				game.setScreen(new PlayScreen(game));
			} else if (buttonSelected == 1) {
				keyboardSelection = false;
				game.setScreen(game.scoreScreen);
			} else if (buttonSelected == 2) {
				keyboardSelection = false;
				game.setScreen(game.optionsScreen);
			} else if (buttonSelected == 3) {
				keyboardSelection = false;
				game.setScreen(game.helpScreen);
			} else if (buttonSelected == 4) {
				keyboardSelection = false;
				ArcadeSystem.goToGame(ArcadeSystem.UI);
			}
			break;
		case Keys.DOWN:
			keyboardSelection = true;
			break;
		case Keys.UP:
			keyboardSelection = true;
			break;
		case Keys.BACKSPACE:
			if (keyboardSelection == true) {
				keyboardSelection = false;
			} else if (game.getScreen() == game.menuScreen) {
				ArcadeSystem.goToGame(ArcadeSystem.UI);
			} else {
				keyboardSelection = true;
				Configuration.writeConfig();
				game.setScreen(game.menuScreen);
			}
			break;
		}
		return false;
	}
	
	private void test() {
		int c2 = sCount;
		for(int i=0; i<secret.length; i++) {
			if(sol[i] != secret[c2]) {
				return;
			}
			c2 = ++c2 % secret.length;
		}
		game.zalgo = 1;
	}

	public static int getButtonSelection() {
		return buttonSelected;
	}

	public static  Boolean getKeyboardSelection() {
		return keyboardSelection;
	}
}
