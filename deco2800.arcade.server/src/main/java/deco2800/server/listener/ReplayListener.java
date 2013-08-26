package deco2800.server.listener;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import deco2800.arcade.protocol.replay.demo.ReplayRequest;
import deco2800.arcade.protocol.replay.demo.ReplayResponse;

public class ReplayListener extends Listener {

    @Override
    public void received(Connection connection, Object object) {
        super.received(connection, object);

        //We got a request for the replay handler
        if (object instanceof ReplayRequest) {
            ReplayRequest replayRequest = (ReplayRequest) object;
            
            System.out.println("Replay Listener got something : " + replayRequest.random);
            
            ReplayResponse replayResponse = new ReplayResponse();
            replayResponse.test = "HELLO! " + replayRequest.random;
            
            connection.sendTCP(replayResponse);
        }
    }

}
