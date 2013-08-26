package deco2800.arcade.userui;

/**
 * This class will handle the UI for the profile page.
 * 
 *
 */

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.FPSLogger;
//import com.riley.angrymasons.View.AngryAudio;

import deco2800.arcade.userui.Screens.MainMenu;

public class ProfileUI extends Game {
	
	public static final String VERSION = "0.0.0.02 Pre-Alpha";
	public static final String LOG = "user profile";
	public static final boolean DEBUG = false;
	FPSLogger log;
	
	@Override
	public void create() {
		log = new FPSLogger();
		setScreen(new MainMenu(this));
	}

	@Override
	public void dispose() {
		super.dispose();
	}

	@Override
	public void render() {		
		super.render();
		log.log();
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
	}

	@Override
	public void pause() {
		super.pause();
	}

	@Override
	public void resume() {
		super.resume();
	}
}
