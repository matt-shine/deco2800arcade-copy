package deco2800.arcade.pong;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;

import deco2800.arcade.client.ArcadeSystem;
import deco2800.arcade.client.GameClient;
import deco2800.arcade.client.network.NetworkClient;

/**
 * The network paddle receives events from the server about the position of the
 * network player's paddle, and whether or not they have successfully bounced
 * the ball. TODO needs to be fully implemented
 * 
 * @author uqjstee8
 * 
 */
public class NetworkPaddle extends Paddle {

	private NetworkClient client;
	private GameClient pong;

	/**
	 * Instantiates a new NetworkPaddle
	 * 
	 * @param position
	 * @param client
	 * @throws UnsupportedOperationException
	 */
	public NetworkPaddle(Vector2 position, NetworkClient client) {
		super(position);
		throw new UnsupportedOperationException();

		// client.addListener(new NetworkPaddleListener());
	}

	public static final int KBPADDLESPEED = 200;

	/**
	 * Sets the user's paddle position
	 * 
	 * @param position
	 */
	public NetworkPaddle(Vector2 position, GameClient pong) {
		super(position);
		this.pong = pong;

	}

	/**
	 * Updates direction of ball based on the direction the paddle was moving
	 */

	@Override
	public void update(Ball ball) {
		super.update(ball);
	}

	/**
	 * Moves the paddle up distance y
	 * 
	 * @param y: the distance up to move the paddle
	 */
	public void move(float y) {
		super.move(y);
	}

}
