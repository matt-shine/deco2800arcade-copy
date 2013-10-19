package deco2800.server.webserver;

import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.TreeSet;
import org.simpleframework.http.Response;
import deco2800.arcade.model.Achievement;
import deco2800.arcade.model.Game;
import deco2800.server.ArcadeServer;
import deco2800.server.database.DatabaseException;

public class AchievementResponder implements WebResponder {
	
	public AchievementResponder() {
		super();
	}
	
	public void respond( Response response, String param) throws IOException, DatabaseException {
		PrintStream body = response.getPrintStream();
		
        ArcadeWebserver.setResponseValues(response, "text/html");
		
		String bodyString = "";
		
		// Get all of the games from the database and store them alphabetically
		TreeSet<Game> games = new TreeSet<Game>( ArcadeWebserver.alphabeticalGameComparator() );
		games.addAll( ArcadeServer.instance().getGameStorageDatabase().getServerGames() );
		
		
		bodyString = FileReader.readFileUtf8( "webserver/html/template.html" );
		
		String contentString = "<h1 class='text center'>Achievements</h1>";
		
		for ( Game game : games ) {
			
			ArrayList<Achievement> achievements = ArcadeServer.instance().getAchievementStorage().achievementsForGame( game.id );
			if ( achievements.size() == 0 ) {
				continue;
			}
			
			/* For every game that has achievements listed in the database, add the 
			 * game_achievements partial to the content. This will be filled in with
			 * the relevant values.
			 */
			
			contentString += FileReader.readFileUtf8( "webserver/html/_game_achievements.html" );
			
			// Construct a new String representing one table row per achievement
			String gameAchievements = "";
			for ( Achievement achievement : achievements ) {
				gameAchievements += String.format( "<tr><td class='achievement-icon-holder'><img src='achievement_icon/%s' class='achievement-icon'></td><td>%s</td><td class='text left'>%s</td></tr>", 
				        achievement.icon.replace( "/", "-" ),
						achievement.name, 
						achievement.description );
			}
			
			/* Replace the name in the template, as well as the table body with the
			 * rows constructed above. Note that this is replacing over the whole content,
			 * but at any one time the #{{}} fields will only exist for the most recent,
			 * as they are replaced as each game's achievements are added.
			 */
			contentString = contentString.replace( "#{{gamename}}", String.valueOf( game.name ) );
			contentString = contentString.replace( "#{{tablebody}}", String.valueOf( gameAchievements ) );
			
		}

		// Put the constructed content into the template and close the stream
		bodyString = bodyString.replace( "#{{content}}", contentString );

		body.println( bodyString );
		body.close();
	}
}
