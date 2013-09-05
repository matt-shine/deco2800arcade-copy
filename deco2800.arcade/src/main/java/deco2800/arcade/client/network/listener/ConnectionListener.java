package deco2800.arcade.client.network.listener;

import java.security.Key;
import java.security.PublicKey;

import com.esotericsoftware.kryonet.Connection;

import deco2800.arcade.client.network.SessionModel;
import deco2800.arcade.protocol.connect.ConnectionResponse;
import deco2800.arcade.protocol.connect.ServerKeyExchange;
import deco2800.arcade.protocol.connect.SessionKeyExchange;

public class ConnectionListener extends NetworkListener {
	private SessionModel session;

	public ConnectionListener(SessionModel session) {
		this.session = session;
	}

	@Override
	public void connected(Connection connection) {
		super.connected(connection);
	}

	@Override
	public void disconnected(Connection connection) {
		super.disconnected(connection);
	}

	@Override
	public void idle(Connection connection) {
		super.idle(connection);
	}

	@Override
	public void received(Connection connection, Object object) {
		super.received(connection, object);

		if (object instanceof ConnectionResponse) {

			@SuppressWarnings("unused")
			ConnectionResponse connectionResponse = (ConnectionResponse) object;

			// TODO something
		}

		if (object instanceof ServerKeyExchange) {
			ServerKeyExchange keyExchange = (ServerKeyExchange) object;
			
			// Check certificate / unseal
			PublicKey serverKey = null;
			try {
				serverKey = keyExchange.getServerKey(session.getServerCert());
			} catch (Exception e) {
				e.printStackTrace();
				// TODO: handle this. E.g resend our connection request.
			}
			
			// Store the server key
			if(serverKey != null) {
				session.setServerKey(serverKey);
				// TODO: Send the client's public key to the server
				// (1) first we need a (plain text right now) send method
				// (2) we need to prepare the Exchange
				// (3) send the exchange
			}
		}

		if (object instanceof SessionKeyExchange) {
			SessionKeyExchange keyExchange = (SessionKeyExchange) object;
			
			Key secret = null;
			try {
				secret = keyExchange.getSessionKey(session
						.getClientPrivateKey());
			} catch (Exception e) {
				e.printStackTrace();
				// TODO: handle this. E.g resend our connection request.
			}
			
			// Store the server key
			if(secret != null) {
				session.setSessionSecret(secret);
			}
		}
	}
}