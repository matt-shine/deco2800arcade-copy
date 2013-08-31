package deco2800.arcade.arcadeui;

import com.badlogic.gdx.Screen;
import deco2800.arcade.client.GameClient;
import deco2800.arcade.client.UIOverlay;
import deco2800.arcade.client.network.NetworkClient;
import deco2800.arcade.model.Game;
import deco2800.arcade.model.Game.ArcadeGame;
import deco2800.arcade.model.Game.InternalGame;
import deco2800.arcade.model.Player;

@InternalGame
@ArcadeGame(id="arcadeoverlay")
public class Overlay extends GameClient implements UIOverlay {
	
	private OverlayScreen screen = new OverlayScreen();
	
	public Overlay(Player player, NetworkClient networkClient) {
		super(player, networkClient);

		this.setScreen(screen);
		
	}
	

	@Override
	public void setListeners(Screen l) {
		screen.setListeners(l);
	}

	@Override
	public void addPopup(String s) {
		//TODO: popups
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resize(int arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Game getGame() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void create() {
		this.setScreen(screen);
	}


}
