package deco2800.server.listener;

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
	
	public ReplayListener() {
		try {
			ArcadeServer.instance().getReplayStorage().initialise();
		} catch( DatabaseException e ) {
			// ouch
		}
	}
	
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
            
            Date date = new Date();
            
            int sessionID = -1;
            
            try {
                sessionID = ArcadeServer.instance().getReplayStorage().insertSession( ssr.gameId, ssr.username, date.getTime(), "");
            } catch (DatabaseException e) {
                e.printStackTrace();
            }
            
            if ( sessionID < 0 ) {
            	// something went wrong
            }
            
            log("Got a StartSessionRequest: " + ssr.gameId + ", " + ssr.username);
            
            response.sessionId = sessionID;
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
            response.sessions = new ArrayList<Session>();
            
            System.out.println("Got list sessions request");

            ArrayList<String> sessionStrings = null;
            
            try {
                sessionStrings = ArcadeServer.instance().getReplayStorage().getSessionsForGame(lsr.gameId);
                System.out.println(sessionStrings);
            } catch (DatabaseException e) {
                e.printStackTrace();
            }
            
            //Convert back to sessions
            if (sessionStrings != null)
            {
                for (String s : sessionStrings)
                {
                    String[] tokens = s.split(",");

                    int sessionId = Integer.parseInt(tokens[0].replaceAll("\\s+",""));
                    boolean recording = Boolean.parseBoolean(tokens[1]);
                    String user = tokens[2];
                    long dateTime = Long.parseLong(tokens[3].replaceAll("\\s+",""));
                    String comments = tokens[4];
                    
                    Session session = new Session();
                    session.gameId = lsr.gameId;
                    session.sessionId = sessionId;
                    session.time = dateTime;
                    session.username = user;
                    session.recording = recording;
                    session.comments = comments;
                    
                    response.sessions.add(session);
                }
            }
            
            connection.sendTCP(response);
            
        } else if (object instanceof PushEventRequest)
        {
            PushEventRequest per = (PushEventRequest) object;
            PushEventResponse response = new PushEventResponse();
            
            System.out.println( "Got push event: " + per.eventIndex );
            
            try {
                ArcadeServer.instance().getReplayStorage().insertEvent( per.sessionId, per.eventIndex, per.nodeString); 
            } catch (DatabaseException e) {
                e.printStackTrace();
            }
            
            response.success = true;
            connection.sendTCP(response);
            
            
        } else if (object instanceof GetEventsRequest)
        {
            GetEventsRequest ger = (GetEventsRequest) object;
            GetEventsResponse response = new GetEventsResponse();
            
            try {
                response.nodes = ArcadeServer.instance().getReplayStorage().getReplay(ger.sessionId);
            } catch (DatabaseException e) {
                e.printStackTrace();
            }

            System.out.println("Served " + response.nodes.size() + " events for session " + ger.sessionId);
            
            response.serverOffset = 0;
            
            connection.sendTCP(response);
        }
    }
    
    private void log(String s)
    {
        System.out.println(s);
    }

}
