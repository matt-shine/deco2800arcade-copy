package deco2800.arcade.landInvaders;


import com.badlogic.gdx.Screen;

import deco2800.arcade.client.GameClient;
import deco2800.arcade.client.network.NetworkClient;
import deco2800.arcade.model.Game;
import deco2800.arcade.model.Game.ArcadeGame;
import deco2800.arcade.model.Player;

//Main Class
@ArcadeGame(id = "LandInvaders")
public class LandInvaders extends GameClient  {

	private static final Game GAME;
	private boolean isPaused = false;

	public LandInvaders(Player player, NetworkClient networkClient) {
		super(player, networkClient);
		Invaders invader = new Invaders();
	}
	
	public void create() {
		super.create();
	//sets overlay listeners
	this.getOverlay().setListeners(new Screen() {

		@Override
		public void dispose() {
		}

		@Override
		public void hide() {
			isPaused = false;
		}

		@Override
		public void pause() {
		}

		@Override
		public void render(float arg0) {
		}

		@Override
		public void resize(int arg0, int arg1) {
		}

		@Override
		public void resume() {
//			super.resume();
		}

		@Override
		public void show() {
			isPaused = true;
		}

	});
}

	public boolean isPaused() {
		return isPaused();
	}
	public void setPause(boolean pause) {
		isPaused = pause;
	}
	@Override
	public void dispose() {
		super.dispose();
	}

	@Override
	public void pause() {
		super.pause();
	}

	
//	public void resume() {
//		super.resume();
//	}

	static {
		GAME = new Game();
		GAME.id = "LandInvaders";
		GAME.name = "LandInvaders";
		GAME.description = "funny game!";
	}
	@Override
	public Game getGame() {
		
		return GAME;
	}

}
