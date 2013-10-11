package deco2800.arcade.hunter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;

import deco2800.arcade.client.GameClient;
import deco2800.arcade.client.network.NetworkClient;
import deco2800.arcade.model.Game;
import deco2800.arcade.model.Player;

public abstract class PlatformerGame extends GameClient {

	private Screen screen;
	
	public PlatformerGame(Player player, NetworkClient networkClient) {
		super(player, networkClient);
	}

	@Override
	public Game getGame() {
		return null;
	}
	
	@Override
	public void create() {
		super.create();
	}

	@Override
	public void dispose() {
		super.dispose();
		if (screen != null) screen.hide();
	}

	@Override
	public void pause() {
		super.pause();
		if (screen != null) screen.pause();
	}

	@Override
	public void render() {
		super.render();
		if (screen != null) screen.render(Gdx.graphics.getDeltaTime());
		super.render();
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
		if (screen != null) screen.resize(width, height);
	}

	@Override
	public void resume() {
		super.resume();
		if (screen != null) screen.resume();
	}
	
	//Set the game screen
	public void setScreen (Screen screen) {
		if (this.screen != null) this.screen.hide();
		this.screen = screen;
		if (this.screen != null) {
			this.screen.show();
			this.screen.resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		}
	} 
	
	public Screen getScreen () {
		return screen;
	}

}
