package deco2800.arcade.protocol;

import java.util.ArrayList;
import java.util.List;

import javax.crypto.SealedObject;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

/**
 * Proxy listener that unseals secure messages. Listeners register with the
 * class receive the plain text version of the incoming message.
 * 
 * @author Team Mashup
 * 
 */
public class SealedListenerProxy extends Listener {
	private List<Listener> listeners;
	private SymmetricSealer sealer;

	// FIXME: the send method of Connection objects is not secure
	// and we should not allow listeners to have inadvertent access to it.

	/**
	 * Supply the sealer that will be used to unseal the incoming messages.
	 * 
	 * @param sealer
	 */
	public SealedListenerProxy(SymmetricSealer sealer) {
		listeners = new ArrayList<Listener>();
		this.sealer = sealer;
	}

	/**
	 * Adds a listener to the proxy. The listener can expect to receive unsealed
	 * versions of the incoming messages.
	 * 
	 * @param listener
	 */
	public void addListener(Listener listener) {
		if (!listeners.contains(listener)) {
			listeners.add(listener);
		}
	}

	/**
	 * Remove listener from the proxy.
	 * 
	 * @param listener
	 */
	public void remove(Listener listener) {
		listeners.remove(listener);
	}

	@Override
	public void connected(Connection connection) {
		super.connected(connection);
		for (Listener listener : listeners) {
			listener.connected(connection);
		}
	}

	@Override
	public void disconnected(Connection connection) {
		super.disconnected(connection);
		for (Listener listener : listeners) {
			listener.disconnected(connection);
		}
	}

	@Override
	public void idle(Connection connection) {
		super.idle(connection);
		for (Listener listener : listeners) {
			listener.idle(connection);
		}
	}

	@Override
	public void received(Connection connection, Object object) {
		super.received(connection, object);

		if (object instanceof SealedObject) {
			SealedObject sealedObject = (SealedObject) object;

			Object unsealedObject = null;
			try {
				unsealedObject = sealer.unSeal(sealedObject);
			} catch (Exception e) {
				e.printStackTrace();
			}

			for (Listener listener : listeners) {
				listener.received(connection, unsealedObject);
			}
		}
	}
}