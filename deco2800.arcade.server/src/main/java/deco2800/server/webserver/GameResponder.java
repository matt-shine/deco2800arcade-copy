package deco2800.server.webserver;

import java.io.IOException;
import java.io.PrintStream;
import java.util.TreeSet;

import org.simpleframework.http.Response;

import deco2800.arcade.model.Game;
import deco2800.server.ArcadeServer;
import deco2800.server.database.DatabaseException;

public class GameResponder implements WebResponder {
	
	public GameResponder() {
		super();
	}
	
	public void respond( Response response, String param ) throws IOException, DatabaseException {
		
		PrintStream body = response.getPrintStream();
		ArcadeWebserver.setResponseValues(response, "text/html");
		
		String bodyString = "";
		
		// Get all of the games from the database and store them alphabetically
		TreeSet<Game> games = new TreeSet<Game>( ArcadeWebserver.alphabeticalGameComparator() );
		games.addAll( ArcadeServer.instance().getGameStorageDatabase().getServerGames() );
		
		bodyString = FileReader.readFileUtf8( "webserver/html/template.html" );
		String contentString = "<h1 class='text center'>Games</h1>";
		
		for ( Game game : games ) {
			/*
			 * For each game on the server, create a partial game view and populate
			 * it with the data fetched from the database about that game
			 */
			contentString += FileReader.readFileUtf8( "webserver/html/_game.html" );
			
			contentString = contentString.replace( "#{{gamename}}", game.name );
			contentString = contentString.replace( "#{{gamedescription}}", game.description );
			/* The /logo/* route is defined to serve logo images from the logo storage, 
			 * where they are stored in the format 'id.png'
			 */
			contentString = contentString.replace( "#{{gamelogo}}", "/logo/" + game.id + ".png");
			contentString = contentString.replace( "#{{price}}", String.valueOf( game.pricePerPlay ) );
			
		}
		// Populate the template with the game data
		bodyString = bodyString.replace( "#{{content}}", contentString );
	
		body.println( bodyString );
		body.close();
	}
}
