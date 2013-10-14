package deco2800.arcade.protocol;

import com.esotericsoftware.kryonet.Connection;
import deco2800.arcade.protocol.NetworkObject;

@Deprecated
public class BlockingMessage extends NetworkObject {
    
    public static BlockingMessage request(Connection conn, BlockingMessage req) {
	return (BlockingMessage)NetworkObject.request(conn, req).get();
    }

    /**
     * Sends a response to a BlockingMessage.
     *
     */
    public static void respond(Connection conn,
                               BlockingMessage req,
                               BlockingMessage resp) {
        resp.makeResponse(req);
        conn.sendTCP(resp);
    }
}
