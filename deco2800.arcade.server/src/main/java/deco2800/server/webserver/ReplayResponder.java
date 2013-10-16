package deco2800.server.webserver;

import java.io.PrintStream;
import java.nio.charset.Charset;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.simpleframework.http.Response;

import deco2800.arcade.protocol.replay.types.Session;
import deco2800.server.ArcadeServer;

public class ReplayResponder implements WebResponder {
	private static ArrayList<Session> stringsToSessions( ArrayList<String> replayStrings ) {
		ArrayList<Session> replays = new ArrayList<Session>();
		//Convert back to sessions
        if (replayStrings != null)
        {
            for (String s : replayStrings)
            {
                String[] tokens = s.split(",");

                int sessionId = Integer.parseInt(tokens[0].replaceAll("\\s+",""));
                boolean recording = Boolean.parseBoolean(tokens[1]);
                String user = tokens[2];
                long dateTime = Long.parseLong(tokens[3].replaceAll("\\s+",""));
                String comments = tokens[4];
                
                Session session = new Session();
                session.sessionId = sessionId;
                session.time = dateTime;
                session.username = user;
                session.recording = recording;
                session.comments = comments;
                
                replays.add( session );
            }
        }
        return replays;
	}
	
	public void respond( Response response ) throws Exception {
		
		PrintStream body = response.getPrintStream();
		long time = System.currentTimeMillis();

		response.setValue("Content-Type", "text/html");
		response.setValue("Server", "HelloWorld/1.0 (Simple 4.0)");
		response.setDate("Date", time);
		response.setDate("Last-Modified", time);
		
		String bodyString = "";
		
		List<String> gameIds = ArcadeServer.instance().getReplayStorage().getGameIds();
		
		//gameIds.add( "test" );
		
		bodyString = FileReader.readFile( "webserver/html/template.html", Charset.forName("UTF-8" ) );
		
		String contentString = "<h1 class='text center'>Replays</h1>";
		
		for ( String gameId : gameIds ) {
			ArrayList<String> replayStrings = new ArrayList<String>();
			replayStrings = ArcadeServer.instance().getReplayStorage().getSessionsForGame( gameId );
			
			ArrayList<Session> replays = stringsToSessions( replayStrings );
			
			contentString += FileReader.readFile( "webserver/html/_replay.html", Charset.forName("UTF-8" ) );
			
			String tableString = "";
			for ( Session replay : replays ) {
				Date replayDate = new Date( replay.time );
				String replayDateString = DateFormat.getDateTimeInstance().format( replayDate );
				
				Integer itemCount = 0;
				if ( !replay.sessionId.equals( 100001 ) ) {
					 itemCount = ( ArcadeServer.instance().getReplayStorage().getReplay( replay.sessionId ) ).size();
				}
				tableString += String.format( "<tr><td>%s</td><td>%d</td><td>%s</td></tr>", 
						replay.username, 
						itemCount, 
						replayDateString );
			}
			
			contentString = contentString.replace( "#{{gameid}}", gameId );
			contentString = contentString.replace( "#{{tablebody}}", tableString );
			
		}
		bodyString = bodyString.replace( "#{{content}}", contentString );
	
		body.println( bodyString );
		body.close();
	}
}
