package deco2800.arcade.pong;

import com.badlogic.gdx.math.Vector2;

import deco2800.arcade.client.network.NetworkClient;

/**
 * The network paddle receives events from the server about the position of the network player's paddle,
 * and whether or not they have successfully bounced the ball.
 * TODO needs to be fully implemented
 * @author uqjstee8
 *
 */
public class NetworkPaddle extends Paddle {

	//private NetworkClient client;
	
	public NetworkPaddle(Vector2 position, NetworkClient client) {
		super(position);
		throw new UnsupportedOperationException();
		//client.addListener(new NetworkPaddleListener());
	}

	@Override
	public void update(Ball ball) {
		// TODO Auto-generated method stub

	}
	
	/*
	private class NetworkPaddleListener extends NetworkListener {
		
	}
	*/

}
