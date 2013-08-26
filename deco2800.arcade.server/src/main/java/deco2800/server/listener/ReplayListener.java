package deco2800.server.listener;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import deco2800.arcade.protocol.replay.EndSessionRequest;
import deco2800.arcade.protocol.replay.EndSessionResponse;
import deco2800.arcade.protocol.replay.GetEventsResponse;
import deco2800.arcade.protocol.replay.ListSessionsResponse;
import deco2800.arcade.protocol.replay.PushEventResponse;
import deco2800.arcade.protocol.replay.StartSessionRequest;
import deco2800.arcade.protocol.replay.StartSessionResponse;
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
        } else if (object instanceof StartSessionRequest)
        {
            StartSessionRequest ssr = (StartSessionRequest) object;
            StartSessionResponse response = new StartSessionResponse();
            
            log("Got a StartSessionRequest: " + ssr.gameId + ", " + ssr.username);
            
            //TODO Generate Session ID
            response.sessionId = 1234;
            connection.sendTCP(response);
        } else if (object instanceof EndSessionRequest)
        {
            EndSessionRequest esr = (EndSessionRequest) object;
            EndSessionResponse response = new EndSessionResponse();
            
            log("Got an EndSessionRequest: " + esr.sessionId);
            
            //TODO check if session is not already terminated.
            response.success = true;
            connection.sendTCP(response);
            
        } else if (object instanceof ListSessionsResponse)
        {
            ListSessionsResponse lsr = (ListSessionsResponse) object;
        } else if (object instanceof PushEventResponse)
        {
            PushEventResponse per = (PushEventResponse) object;
        } else if (object instanceof GetEventsResponse)
        {
            GetEventsResponse ger = (GetEventsResponse) object;
        }
    }
    
    private void log(String s)
    {
        System.out.println(s);
    }

}
