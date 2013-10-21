package deco2800.server.listener;

import java.util.ArrayList;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
import deco2800.arcade.protocol.replay.types.Session;
import deco2800.server.ArcadeServer;
import deco2800.server.database.DatabaseException;

public class ReplayListener extends Listener {

	private Logger logger = LoggerFactory.getLogger( ReplayListener.class );
    
	public ReplayListener() {
		try {
			ArcadeServer.instance().getReplayStorage().initialise();
		} catch( DatabaseException e ) {
            logger.error( e.toString() );
		}
	}
	
    @Override
    public void received(Connection connection, Object object) {
        super.received(connection, object);
        
        if (object instanceof StartSessionRequest)
        {
            StartSessionRequest ssr = (StartSessionRequest) object;
            StartSessionResponse response = new StartSessionResponse();
            
            Date date = new Date();
            
            int sessionID = -1;
            
            try {
                sessionID = ArcadeServer.instance().getReplayStorage().insertSession( ssr.gameId, ssr.username, date.getTime(), "");
            } catch (DatabaseException e) {
                logger.error( e.toString() );
            }
            
            if ( sessionID >= 0 ) {
                logger.info("Got a start replay session request for game ID '" + ssr.gameId + "' with username '" + ssr.username + "'");
            }
            
            response.sessionId = sessionID;
            connection.sendTCP(response);
        } else if (object instanceof EndSessionRequest)
        {
            EndSessionRequest esr = (EndSessionRequest) object;
            EndSessionResponse response = new EndSessionResponse();
            try {
                ArcadeServer.instance().getReplayStorage().endRecording(esr.sessionId);
            } catch (DatabaseException e) {
                logger.error( e.toString() );
            }
            
            logger.info("Got an end replay session event for event ID " + esr.sessionId);
            
            response.success = true;
            connection.sendTCP(response);
            
        } else if (object instanceof ListSessionsRequest)
        {
            ListSessionsRequest lsr = (ListSessionsRequest) object;
            ListSessionsResponse response = new ListSessionsResponse();
            response.sessions = new ArrayList<Session>();
            
            logger.info("Got list replay session event.");

            ArrayList<String> sessionStrings = null;
            
            try {
                sessionStrings = ArcadeServer.instance().getReplayStorage().getSessionsForGame(lsr.gameId);
                logger.info( "Served replay session list as " + sessionStrings );
            } catch (DatabaseException e) {
                logger.error( e.toString() );
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
            
            logger.info( "Got replay push event for session " + per.sessionId + " at index " + per.eventIndex );
            
            try {
                ArcadeServer.instance().getReplayStorage().insertEvent( per.sessionId, per.eventIndex, per.nodeString); 
            } catch (DatabaseException e) {
                logger.error( e.toString() );
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
                logger.error( e.toString() );
            }

            logger.info("Served " + response.nodes.size() + " replay events for session " + ger.sessionId);
            
            response.serverOffset = 0;
            
            connection.sendTCP(response);
        }
    }

}
