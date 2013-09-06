package deco2800.teamgameover.game;

import deco2800.arcade.client.GameClient;
import deco2800.arcade.client.network.NetworkClient;
import deco2800.arcade.model.Game.ArcadeGame;
import deco2800.arcade.model.Player;

/** The extension of Game class that is opened by the desktop application.
 * Will need to be altered to extend GameClient instead.
 * GameScreen.java might need to get merged with this one, so that this will
 * be the class in control of level progression 
 * @author Game Over
 *
 */
@ArcadeGame(id = "teamgameover")
public class TestGame2 extends GameClient {

      
    public TestGame2(Player player, NetworkClient networkClient) {
		super(player, networkClient);
		// TODO Auto-generated constructor stub
	}

	public SplashScreen getSplashScreen() {
    	return new SplashScreen(this);
    }
	
	@Override
	public void create() {		
	
		
		//Set to splash screen
		//setScreen(getSplashScreen());
		//OR go straight to the action
		setScreen(new GameScreen(this));
	}

	@Override
	public void dispose() {
		//batch.dispose();
		//texture.dispose();
	}

	@Override
	public void render() {		
		
		super.render();
		
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public deco2800.arcade.model.Game getGame() {
		// TODO Auto-generated method stub
		return null;
	}
}
