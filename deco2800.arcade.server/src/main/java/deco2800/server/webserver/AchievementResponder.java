package deco2800.server.webserver;

import java.io.PrintStream;
import java.nio.charset.Charset;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.Set;
import java.util.TreeSet;

import org.simpleframework.http.Response;

import deco2800.arcade.model.Achievement;
import deco2800.arcade.model.Game;
import deco2800.arcade.protocol.replay.types.Session;
import deco2800.server.ArcadeServer;

public class AchievementResponder {
	
	public static void respond( Response response ) throws Exception {
		
		PrintStream body = response.getPrintStream();
		long time = System.currentTimeMillis();

		response.setValue("Content-Type", "text/html");
		response.setValue("Server", "HelloWorld/1.0 (Simple 4.0)");
		response.setDate("Date", time);
		response.setDate("Last-Modified", time);
		
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
		String contentString = "<h1 class='text center'>Achievements</h1>";
		
		
		for ( Game game : games ) {
			
			ArrayList<Achievement> achievements = ArcadeServer.instance().getAchievementStorage().achievementsForGame( game.id );
			
			if ( achievements.size() == 0 ) {
				continue;
			}
			
			contentString += FileReader.readFile( "webserver/html/game_achievements.html", Charset.forName("UTF-8" ) );
			
			String gameAchievements = "";
			for ( Achievement achievement : achievements ) {
				gameAchievements += String.format( "<tr><td>%s</td><td class='text left'>%s</td></tr>", 
						achievement.name, 
						achievement.description );
			}
			
			contentString = contentString.replace( "#{{gamename}}", String.valueOf( game.name ) );
			contentString = contentString.replace( "#{{tablebody}}", String.valueOf( gameAchievements ) );
			
		}
		bodyString = bodyString.replace( "#{{content}}", contentString );
	
		body.println( bodyString );
		body.close();
	}
}
