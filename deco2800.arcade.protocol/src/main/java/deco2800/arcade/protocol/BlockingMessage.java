package deco2800.arcade.protocol;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Condition;

public class BlockingMessage {
    
    private int id;

    private static class BlockingSession extends Listener {
    
        private static int nextID = 0;
        private ReentrantLock lck;
        private Condition cond;
        private int targetID;
        private BlockingMessage resp;

        public BlockingSession() {
            this.lck = new ReentrantLock();
            this.cond = lck.newCondition();
            this.resp = null;
        }

        public BlockingMessage request(Connection connection, BlockingMessage req) {
            // give the request and our target a new ID
            this.targetID = req.id = nextID++;
            // let us be notified when the response arrives
            connection.addListener(this);
            // send our request
            connection.sendTCP(req);
            // wait for the response
            while(this.resp == null) {
                try {
                    lck.lock();
                    this.cond.await();
                } catch (InterruptedException e) {
                    return null;
                } finally {
                    lck.unlock();
                }
                
            }

            return this.resp;
        }

        public void received(Connection connection, Object object) {
            // make sure it's a response
            if(object instanceof BlockingMessage) {
                BlockingMessage resp = (BlockingMessage)object;
                // make sure it's responding to the id of our request
                if(resp.id != targetID) return;
                // set the response so the request function can see it               
                this.resp = resp;
                // clean up
                connection.removeListener(this);
                // and tell the request thread to wake up
                lck.lock();
                cond.signal();
                lck.unlock();
            }
        }
        
    }

    /**
     * Makes a blocking request over the nework. The response from the reciever
     * that's sent via BlockingMessage.respond is returned. Note that other
     * network objects will continue to be recieved over the connection.
     *
     * @param conn The connection to make the request over.
     * @param req  The request object to send. This object must have been registered
     *               for sending over Kryonet connections.
     */
    public static BlockingMessage request(Connection conn, BlockingMessage req) {
        BlockingSession session = new BlockingSession();
        return session.request(conn, req);
    }

    /**
     * Sends a response to a BlockingMessage.
     *
     */
    public static void respond(Connection conn,
                               BlockingMessage req,
                               BlockingMessage resp) {
        resp.id = req.id;
        conn.sendTCP(resp);
    }
}