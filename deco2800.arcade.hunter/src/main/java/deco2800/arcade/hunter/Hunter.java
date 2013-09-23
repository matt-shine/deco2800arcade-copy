package deco2800.arcade.hunter;

import com.badlogic.gdx.Screen;

import deco2800.arcade.client.network.NetworkClient;
import deco2800.arcade.hunter.screens.SplashScreen;
import deco2800.arcade.model.Game.ArcadeGame;
import deco2800.arcade.model.Player;
import deco2800.arcade.platformergame.PlatformerGame;

@ArcadeGame(id="hunter")
public class Hunter extends PlatformerGame {
	private PreferencesManager prefManage;
	
	public enum Config {
		INSTANCE;
		public static int screenWidth = 1280;
		public static int screenHeight = 720;
		public static int gameSpeed = 256;
		public final static int TILE_SIZE = 64;
		public final static int PANE_SIZE = 16;
		public final static int PANE_SIZE_PX = TILE_SIZE * PANE_SIZE;
		public final static int MAX_SPEED = 512;
	}

	public Hunter(Player player, NetworkClient networkClient) {
		super(player, networkClient);
		prefManage = new PreferencesManager();
	}
	
	public PreferencesManager getPreferencesManager(){
		return prefManage;
	}
	
	@Override
	public void create() {
		this.getOverlay().setListeners(new Screen() {
			@Override
			public void hide() {
				//Unpause your game here
			}
			
			@Override
			public void show() {
				//Pause your game here
			}
			
			@Override
			public void pause() {}
			@Override
			public void render(float arg0) {}
			@Override
			public void resize(int arg0, int arg1) {}
			@Override
			public void resume() {}
			@Override
			public void dispose() {}
		});
		setScreen(new SplashScreen(this));
	}
}
