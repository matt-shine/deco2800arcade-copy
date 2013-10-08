package deco2800.arcade.burningskies.screen;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Input.Keys;

import deco2800.arcade.burningskies.BurningSkies;
import deco2800.arcade.client.ArcadeSystem;


public class MenuInputProcessor extends InputAdapter {
	
	private BurningSkies game;
	private static int buttonSelected = 0;
	private static Boolean keyboardSelection = false;
	
	public MenuInputProcessor(BurningSkies game) {
		this.game = game;
	}
	
	@Override
	public boolean keyDown(int keycode) {
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
				game.setScreen(game.menuScreen);
			}
			break;
		}
		return false;
	}
	
	public static int getButtonSelection() {
		return buttonSelected;
	}

	public static  Boolean getKeyboardSelection() {
		return keyboardSelection;
	}
}
