package deco2800.server.webserver;

import java.io.PrintStream;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.simpleframework.http.Response;

import deco2800.arcade.protocol.replay.types.Session;
import deco2800.server.ArcadeServer;

public class ReplayResponder implements WebResponder {
	
	public ReplayResponder() {
		super();
	}
	
	/**
	 * Parses replay strings back into Session objects. Replay strings are stored as
	 * a comma-separated string of session id, recording state, user id, time and comment.
	 * @param replayStrings, the array of strings to parse
	 * @return a List of the Session objects parsed from the strings
	 */
	private List<Session> stringsToSessions( ArrayList<String> replayStrings ) {
		ArrayList<Session> replays = new ArrayList<Session>();
		
        if (replayStrings != null) {
            for (String s : replayStrings) {
            	try {
	                String[] tokens = s.split(",");
	                
	                /* Split the comma separated string into its components, then 
	                 * parse them accordingly.
	                 */
	
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
            	} catch ( Exception e ) {
            		/* Operation is not critical, so if the string is passed in the 
            		 * wrong format then ignore it
            		 */
            	}
            }
        }
        return replays;
	}
	
	public void respond( Response response, String param) throws Exception {
		
		PrintStream body = response.getPrintStream();

        ArcadeWebserver.setResponseValues(response, "text/html");
		
		String bodyString = "";
		
		bodyString = FileReader.readFileUtf8( "webserver/html/template.html" );
		
		String contentString = "<h1 class='text center'>Replays</h1>";
		
		List<String> gameIds = ArcadeServer.instance().getReplayStorage().getGameIds();
		
		for ( String gameId : gameIds ) {
			/* For each game ID, get the list of Sessions recorded against for that game
			 */
			ArrayList<String> replayStrings = new ArrayList<String>();
			replayStrings = ArcadeServer.instance().getReplayStorage().getSessionsForGame( gameId );
			List<Session> replays = stringsToSessions( replayStrings );
			
			contentString += FileReader.readFileUtf8( "webserver/html/_replay.html" );
			
			String tableString = "";
			for ( Session replay : replays ) {
				/* For each replay, create a table row containing the information
				 * about that replay fetched from the database
				 */
				Date replayDate = new Date( replay.time );
				String replayDateString = DateFormat.getDateTimeInstance().format( replayDate );
				
				Integer itemCount = ( ArcadeServer.instance().getReplayStorage().getReplay( replay.sessionId ) ).size();
				tableString += String.format( "<tr><td>%s</td><td>%d</td><td>%s</td></tr>", 
						replay.username, 
						itemCount, 
						replayDateString );
			}
			
			// Insert the game id and the table contents
			contentString = contentString.replace( "#{{gameid}}", gameId );
			contentString = contentString.replace( "#{{tablebody}}", tableString );
			
		}
		// Insert the contents into the template, and print it to the output stream
		bodyString = bodyString.replace( "#{{content}}", contentString );
	
		body.println( bodyString );
		body.close();
	}
}
