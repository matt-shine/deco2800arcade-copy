package deco2800.server.listener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import deco2800.arcade.protocol.replay.EndSessionRequest;
import deco2800.arcade.protocol.replay.EndSessionResponse;
import deco2800.arcade.protocol.replay.GetEventsRequest;
import deco2800.arcade.protocol.replay.GetEventsResponse;
import deco2800.arcade.protocol.replay.ListSessionsRequest;
import deco2800.arcade.protocol.replay.ListSessionsResponse;
import deco2800.arcade.protocol.replay.PushEventRequest;
import deco2800.arcade.protocol.replay.PushEventResponse;
import deco2800.arcade.protocol.replay.StartSessionRequest;
import deco2800.arcade.protocol.replay.StartSessionResponse;
import deco2800.arcade.protocol.replay.demo.ReplayRequest;
import deco2800.arcade.protocol.replay.demo.ReplayResponse;
import deco2800.arcade.protocol.replay.types.Session;
import deco2800.server.ArcadeServer;
import deco2800.server.database.DatabaseException;

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
            
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Date date = new Date();
            System.out.println(dateFormat.format(date));
            
            try {
                //TODO, DB should sent US the sessionId, also date format is wrong.
                ArcadeServer.instance().getReplayStorage().insertSession(1234, ssr.gameId.intValue(), true, ssr.username, 000, "");
            } catch (DatabaseException e) {
                e.printStackTrace();
            }
            
            log("Got a StartSessionRequest: " + ssr.gameId + ", " + ssr.username);
            
            //TODO Generate Session ID
            response.sessionId = 1234;
            connection.sendTCP(response);
        } else if (object instanceof EndSessionRequest)
        {
            EndSessionRequest esr = (EndSessionRequest) object;
            EndSessionResponse response = new EndSessionResponse();
            
            try {
                ArcadeServer.instance().getReplayStorage().endRecording(esr.sessionId);
            } catch (DatabaseException e) {
                e.printStackTrace();
            }
            
            log("Got an EndSessionRequest: " + esr.sessionId);
            
            //TODO check if session is not already terminated.
            response.success = true;
            connection.sendTCP(response);
            
        } else if (object instanceof ListSessionsRequest)
        {
            ListSessionsRequest lsr = (ListSessionsRequest) object;
            ListSessionsResponse response = new ListSessionsResponse();
            
            try {
                ArrayList<String> sessionStrings = ArcadeServer.instance().getReplayStorage().getSessionsForGame(lsr.gameId.intValue());
            } catch (DatabaseException e) {
                e.printStackTrace();
            }
            
            //TODO convert session strings into actual sessions?
            response.sessions = new ArrayList<Session>();
            connection.sendTCP(response);
            
        } else if (object instanceof PushEventRequest)
        {
            PushEventRequest per = (PushEventRequest) object;
            PushEventResponse response = new PushEventResponse();
            
            //TODO extract eventId from the per
            try {
                ArcadeServer.instance().getReplayStorage().insertEvent(1234, per.sessionId, 0, per.nodeString); //3rd arg is the eventindex
            } catch (DatabaseException e) {
                e.printStackTrace();
            }
            
            response.success = true;
            connection.sendTCP(response);
            
            
        } else if (object instanceof GetEventsRequest)
        {
            GetEventsRequest ger = (GetEventsRequest) object;
            GetEventsResponse response = new GetEventsResponse();
            
            //TODO wtf hashmap wtf? --- i so sorry
            try {
                ArcadeServer.instance().getReplayStorage().getReplay(ger.sessionId);
            } catch (DatabaseException e) {
                e.printStackTrace();
            }
            
            //TODO deal with the data that comes back.
        }
    }
    
    private void log(String s)
    {
        System.out.println(s);
    }

}
