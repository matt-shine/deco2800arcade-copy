package deco2800.arcade.client.network.listener;

import java.security.Key;
import java.security.PublicKey;

import com.esotericsoftware.kryonet.Connection;

import deco2800.arcade.client.network.SessionModel;
import deco2800.arcade.protocol.connect.ClientKeyExchange;
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

			// Do nothing. Wait for ServerKeyExchange.
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
			
			if(serverKey != null) {
				// Store the server key
				session.setServerKey(serverKey);
				
				// We've received the server key so lets send our key back
				// FIXME should check that we actually have these keys
				PublicKey clientPublicKey = session.getClientPublicKey();
				PublicKey serverPublicKey = session.getServerKey();
				
				// Set up the message
				ClientKeyExchange clientKeyExchange = new ClientKeyExchange();
				try {
					clientKeyExchange.setClientKey(clientPublicKey, serverPublicKey);
				} catch (Exception e) {
					e.printStackTrace();
					// FIXME handle error
				}

				// Send the message
				connection.sendTCP(clientKeyExchange);
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
