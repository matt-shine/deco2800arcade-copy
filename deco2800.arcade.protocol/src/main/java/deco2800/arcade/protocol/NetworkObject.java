package deco2800.arcade.protocol;

import java.io.Serializable;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import deco2800.arcade.utils.AsyncFuture;

/**
 * A network object
 */

public abstract class NetworkObject implements Serializable {
	// FIXME should subclasses have a distinct UID? 
	private static final long serialVersionUID = -8931542268092663766L;
    private static int nextUniqueID = 0;
    private int id = 0;

    /**
     * Increments id variable to keep it unique
     */
    public void makeUnique() {
    	id = nextUniqueID++;
    }

    /**
     * Creates a response with the same id as the given request
     * @param request
     */
    public void makeResponse(NetworkObject request) {
    	id = request.id;
    }

    /**
     * Returns true if the given request is a response to the calling request.
     * @param request
     * @return true if id == request.id
     */
    public boolean isResponse(NetworkObject request) {
    	return id == request.id;
    }

    /**
     * @param conn
     * @param req
     * @return AsyncFuture NetworkObject
     */
    public static AsyncFuture<NetworkObject> 
    request(final Connection conn, final NetworkObject req) {
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
    
    /**
     * @param conn
     * @param request
     * @param response
     */
    public static void respond(
    		Connection conn, NetworkObject request, NetworkObject response) {
	response.makeResponse(request);
	conn.sendTCP(response);
    }
}
