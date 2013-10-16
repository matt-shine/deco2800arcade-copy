package deco2800.server.webserver;

import java.io.PrintStream;
import java.nio.charset.Charset;
import java.util.Comparator;
import java.util.TreeSet;

import org.simpleframework.http.Response;

import deco2800.arcade.model.Game;
import deco2800.server.ArcadeServer;

public class GameResponder implements WebResponder {
	
	public GameResponder() {
		super();
	}
	
	public void respond( Response response, String param ) throws Exception {
		
		PrintStream body = response.getPrintStream();

		ArcadeWebserver.setResponseValues(response, "text/html");
		
		String bodyString = "";
		
		Comparator<Game> alphabetical = new Comparator<Game>() {  
            @Override  
            public int compare(Game o1, Game o2) {  
                int rval = Integer.valueOf( ( o1.name ).compareTo( o2.name) );  
                if (rval != 0) return rval;  
                return o1.compareTo(o2);  
            }  
        };
		
		TreeSet<Game> games = new TreeSet<Game>( alphabetical );
		games.addAll( ArcadeServer.instance().getGameStorageDatabase().getServerGames() );
		
		bodyString = FileReader.readFile( "webserver/html/template.html", Charset.forName("UTF-8" ) );
		String contentString = "<h1 class='text center'>Games</h1>";
		
		for ( Game game : games ) {
			contentString += FileReader.readFile( "webserver/html/_game.html", Charset.forName("UTF-8" ) );
			
			contentString = contentString.replace( "#{{gamename}}", game.name );
			contentString = contentString.replace( "#{{gamedescription}}", game.description );
			contentString = contentString.replace( "#{{gamelogo}}", "/logo/" + game.id + ".png");

			contentString = contentString.replace( "#{{price}}", String.valueOf( game.pricePerPlay ) );
			
		}
		bodyString = bodyString.replace( "#{{content}}", contentString );
	
		body.println( bodyString );
		body.close();
	}
}
