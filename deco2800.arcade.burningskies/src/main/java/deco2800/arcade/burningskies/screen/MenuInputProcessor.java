package deco2800.arcade.burningskies.screen;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Input.Keys;

import deco2800.arcade.burningskies.BurningSkies;
import deco2800.arcade.client.ArcadeSystem;


public class MenuInputProcessor extends InputAdapter {
	
	private BurningSkies game;
	private int buttonSelected = MenuScreen.getSelected();
	private Boolean keyboardSelection = MenuScreen.getKeyboardSelection();
	
	public MenuInputProcessor(BurningSkies game) {
		this.game = game;
	}
	
	@Override
	public boolean keyDown(int keycode) {
		switch(keycode) {
		case Keys.UP:
        	buttonSelected = ((buttonSelected - 1) % 5 + 5) % 5;
        	MenuScreen.setSelected(buttonSelected);
			break;
		case Keys.DOWN:
			buttonSelected = ((buttonSelected + 1) % 5 + 5) % 5;
        	MenuScreen.setSelected(buttonSelected);
			break;
		case Keys.ENTER:
			if (buttonSelected == 0) {
				game.setScreen(new PlayScreen(game));
			} else if (buttonSelected == 1) {
				game.setScreen(new ScoreScreen(game));
			} else if (buttonSelected == 2) {
				game.setScreen(new OptionsScreen(game));;
			} else if (buttonSelected == 3) {
				game.setScreen(new HelpScreen(game));
			} else if (buttonSelected == 4) {
				ArcadeSystem.goToGame(ArcadeSystem.UI);
			}
		case Keys.LEFT:
			keyboardSelection = true;
			MenuScreen.setKeyboardSelected(keyboardSelection);
		case Keys.RIGHT:
			keyboardSelection = true;
			MenuScreen.setKeyboardSelected(keyboardSelection);
		case Keys.ESCAPE:
			keyboardSelection = false;
			MenuScreen.setKeyboardSelected(keyboardSelection);
		}
		return false;
	}
}
