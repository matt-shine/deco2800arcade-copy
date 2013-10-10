package deco2800.arcade.protocol;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import deco2800.arcade.utils.AsyncFuture;

/**
 * A network object
 */
public class NetworkObject {
    private static int nextUniqueID = 0;
    private int id = 0;

    public void makeUnique() {
	id = nextUniqueID++;
    }

    public void makeResponse(NetworkObject request) {
	id = request.id;
    }

    public boolean isResponse(NetworkObject request) {
	return id == request.id;
    }

    public static AsyncFuture<NetworkObject> request(final Connection conn, final NetworkObject req) {
		req.makeUnique();
	final AsyncFuture<NetworkObject> future = new AsyncFuture<NetworkObject>();

	conn.addListener(new Listener() {
             public void received(Connection conn, Object obj) {
		 if (obj instanceof NetworkObject) {
		     NetworkObject resp = (NetworkObject)obj;
		     if (resp.isResponse(req)) {
			 future.provide(resp);
			 conn.removeListener(this);
		     }
		 }
	     }
	});

	conn.sendTCP(req);
	return future;
    }
}
