package deco2800.arcade.client;

/**
 * Receive notifications when a game client has finished and can be safely closed
 * @author uqjstee8
 *
 */
public interface GameOverListener {
	public void notify(GameClient client);
}
