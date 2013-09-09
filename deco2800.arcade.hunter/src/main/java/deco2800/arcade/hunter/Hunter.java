package deco2800.arcade.hunter;

import com.badlogic.gdx.Screen;

import deco2800.arcade.client.GameClient;
import deco2800.arcade.client.UIOverlay;
import deco2800.arcade.client.network.NetworkClient;
import deco2800.arcade.hunter.screens.SplashScreen;
import deco2800.arcade.model.Game.ArcadeGame;
import deco2800.arcade.model.Player;
import deco2800.arcade.platformergame.PlatformerGame;

@ArcadeGame(id="hunter")
public class Hunter extends PlatformerGame {
	
	public int screenWidth = 1280;
	public int screenHeight = 720;

	public Hunter(Player player, NetworkClient networkClient) {
		super(player, networkClient);
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
