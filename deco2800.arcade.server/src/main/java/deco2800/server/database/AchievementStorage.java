package deco2800.server.database;

import java.sql.Connection;
//import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.HashMap;
import java.util.regex.Pattern;
import java.io.File;
import deco2800.arcade.model.Achievement;
import deco2800.arcade.model.AchievementProgress;
import deco2800.arcade.model.Player;
import deco2800.arcade.model.Game;
import deco2800.server.ResourceLoader;
import deco2800.server.ResourceHandler;
import deco2800.server.database.ImageStorage;
import org.w3c.dom.*;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

/**
 * Implements Achievement storage on database.
 */
public class AchievementStorage { 
	
	/**
	 * Creates the Achievement table and sets initialised to TRUE on completion
	 * 
	 * @throws	DatabaseException	If SQLException occurs. 
	 */
	public  void initialise() throws DatabaseException{

		//Get a connection to the database
		Connection connection = Database.getConnection();
		
		try {
			ResultSet tableData = connection.getMetaData().getTables(null, null,
					"ACHIEVEMENTS", null);
			if (!tableData.next()){
				Statement statement = connection.createStatement();
				statement.execute("CREATE TABLE ACHIEVEMENTS(id VARCHAR(255) PRIMARY KEY," +
						"NAME VARCHAR(30) NOT NULL," +
						"DESCRIPTION VARCHAR(100) NOT NULL," +
						"ICON VARCHAR(255) NOT NULL," +
						"THRESHOLD INT NOT NULL)");
			}
			ResultSet playerAchievementData = connection.getMetaData().getTables(null, null,
					"PLAYER_ACHIEVEMENT", null);
			if (!playerAchievementData.next()){
				Statement playerAchievementStatement = connection.createStatement();
				playerAchievementStatement.execute("CREATE TABLE PLAYER_ACHIEVEMENT(" +
						"id INT PRIMARY KEY " +
							"GENERATED ALWAYS AS IDENTITY(START WITH 1, INCREMENT BY 1)," +
						"playerID INT NOT NULL," +
						"achievementID VARCHAR(255) NOT NULL," +
						"PROGRESS INT NOT NULL," +
						"FOREIGN KEY (achievementID) REFERENCES ACHIEVEMENTS(id))");
			}
		}
		 catch (SQLException e) {
			e.printStackTrace();
			throw new DatabaseException("Unable to create achievements tableS", e);
		}
	}

    private ImageStorage imageStorage;

    public AchievementStorage(ImageStorage imageStorage) {
        this.imageStorage = imageStorage;
    }

	private class AchievementComponent {
		public String id;
		public int awardThreshold;
	}

	private void loadAchievement(Element achElem, String achFolder) throws DatabaseException {
		String id = achElem.getAttribute("id");
		Statement statement = null;
		ResultSet resultSet = null;
		String name = null;
		String description = null;
		String iconPath = null;
		int awardThreshold = -1;
		
		//Get a connection to the database
		Connection connection = Database.getConnection();
		
		try {
			// query DB to see if we already have this id?
			statement = connection.createStatement();
			resultSet = statement.executeQuery("SELECT * FROM ACHIEVEMENTS " +
					"WHERE ID = '" + id + "'");
			if(!resultSet.next()) {
				// still need to bring in icon
				
				LinkedList<AchievementComponent> components = new LinkedList<AchievementComponent>();
				System.out.println("Loading " + id + "...");
				NodeList achInfo = achElem.getChildNodes();
				int n = achInfo.getLength();
				for(int j = 0; j < n; ++j) {
					Node infNode = achInfo.item(j);
					if(infNode.getNodeType() != Node.ELEMENT_NODE) {
						continue;
					}
					Element infElem = (Element)infNode;
					String elemName = infElem.getTagName();
					
					if (elemName.equals("name")) {
						name = infElem.getTextContent();
					} else if (elemName.equals("description")) {
						description = infElem.getTextContent();
					} else if (elemName.equals("awardThreshold")) {
						awardThreshold = Integer.decode(infElem.getTextContent());
					} else if (elemName.equals("icon")) {
						iconPath = achFolder + File.separator + infElem.getTextContent();
					} else if (elemName.equals("components")) {
						NodeList componentNodes = infElem.getChildNodes();
						for(int k = 0; k < componentNodes.getLength(); ++k) {
							Node cmpNode = componentNodes.item(k);
							if(cmpNode.getNodeType() != Node.ELEMENT_NODE) {
								continue;
							}
							Element cmpElem = (Element)cmpNode;
							AchievementComponent component = new AchievementComponent();
							component.id = cmpElem.getAttribute("id");
							component.awardThreshold = Integer.decode(cmpElem.getAttribute("awardThreshold"));
							components.addLast(component);
						}
					} else {
						throw new RuntimeException("Unsupported tag: " + elemName);
					}
				}

				if(awardThreshold != -1 && components.size() > 0) {
					throw new RuntimeException("Achievements can't have an award threshold as well as components");
				}

				System.out.println("... loaded. {");
				System.out.println("  name: " + name);
				System.out.println("  description: " + description);
				if (awardThreshold != -1) System.out.println("  award threshold: " + awardThreshold);
				System.out.println("  icon path: " + iconPath);
				if (components.size() > 0) {
					System.out.println("  components : {");
					for(AchievementComponent c : components) {
						System.out.println("    " + c.id + ", " + c.awardThreshold);
					}
					System.out.println("  }");
				}
				System.out.println("}");

				// load into DB
				statement.executeUpdate("INSERT INTO ACHIEVEMENTS " +
						"VALUES('" + id + "','" + name + "','" + description + "','" 
						+ iconPath + "'," + awardThreshold + ")");
		        File icon = ResourceLoader.load(iconPath);
		        imageStorage.set(iconPath, icon);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DatabaseException("Error in loading achievements.", e);
		} finally {
			try {
				if (resultSet != null){
					resultSet.close();
				}
				if (statement != null){
					statement.close();
				}
				if (connection != null){
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		
	}

	public void loadAchievementData() {
		System.out.println("Loading Achievements into DB...");
		ResourceLoader.handleFilesMatchingPattern(Pattern.compile("achievements\\.xml"),
				1, new ResourceHandler() {
			public void handleFile(File f) {
				try {
					String folder = f.getParentFile().getName();
					System.out.println(f.getPath());
					DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
					DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
					Document doc = docBuilder.parse(f);
					
					NodeList achievementNodes = doc.getElementsByTagName("achievement");
					int numAchievements = achievementNodes.getLength();
					for (int i = 0; i < numAchievements; ++i) {
						Node achNode = achievementNodes.item(i);
						if (achNode.getNodeType() != Node.ELEMENT_NODE) {
							continue;
						}

						loadAchievement((Element)achNode, folder);
					}
					System.out.println("Loading Achievements completed.");
				} catch(Exception e) {
					System.out.println("Couldn't parse achievements file at " + f.getPath());
					e.printStackTrace();
				}
			}
		});
	}

	
	
    /**
     * Utility method for fetching a single achievement. This is just a wrapper
     * around achievementsForIDs, which is a more efficient method for
     * fetching many achievements.
     * 
     * @param  achievementID            The unique ID for the achievement.
     * @throws IllegalArgumentException If an achievement with the provided ID
     *                                  doesn't exist.
     * @return The Achievement matching the provided ID.
     * @throws DatabaseException 
     */
    public Achievement achievementForID(String achievementID) throws DatabaseException {
        ArrayList<String> achievementIDs = new ArrayList<String>();
        achievementIDs.add(achievementID);
        return achievementsForIDs(achievementIDs).get(0);
    }
    
    /**
     * Returns a list of Achievements corresponding to the supplied list of
     * IDs. The ordering of the returned Achievements matches that of the
     * supplied list of IDs.
     *
     * @param achievementIDs            The list of unique achievement IDs to
     *                                  fetch data for from the server.
     * @throws IllegalArgumentException If any of the provided IDs don't have
     *                                  corresponding achievements.
     * @return A list of Achievements corresponding to the supplied IDs.
     * @throws DatabaseException 
     */
    public ArrayList<Achievement> achievementsForIDs(
            ArrayList<String> achievementIDs) throws DatabaseException {
    	
    	ArrayList<Achievement> achievements = new ArrayList<Achievement>();
    	
		//Get a connection to the database
		Connection connection = Database.getConnection();

		Statement statement = null;
		ResultSet resultSet = null;
		try {
			for ( String id : achievementIDs) {
				statement = connection.createStatement();
				resultSet = statement.executeQuery("SELECT * FROM ACHIEVEMENTS" +
						" WHERE ID='" + id + "'");
				Achievement result = findAchievementFromId(id, resultSet);
				achievements.add(result);

				
			}
			return achievements;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DatabaseException("Unable to get achievements from database", e);
		} finally {
			try {
				if (resultSet != null){
					resultSet.close();
				}
				if (statement != null){
					statement.close();
				}
				if (connection != null){
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
    }
    
    /**
	 * Returns achievements where id matches String achievementID given
	 * 
	 * @param	String achievementID, ResultSet results
	 * @throws	SQLException
	 * @return	Integer result
	 */
	private Achievement findAchievementFromId(String achievementID, ResultSet results) 
			throws SQLException{
		Achievement result = null;
		while (results.next()){
			String achievementId = results.getString("id");
			
			if (achievementId.equals(achievementId)){
				result = new Achievement(
						achievementId,
						results.getString("name"),
						results.getString("description"),
						results.getInt("threshold"),
						results.getString("icon")
						);
				break;
			}
		}
		return result;
	}
    
    /**
     * Returns an unsorted list of a game's achievements.
     *
     * @param gameId	The game to fetch achievements for.
     * @return			A list of Achievements for the supplied game.
     */
    public ArrayList<Achievement> achievementsForGame(Game gameId) throws DatabaseException {
        ArrayList<Achievement> achievements = new ArrayList<Achievement>();
        // TODO Need to sort out where gameID will be defined and stored 

        return achievements;
    }

    
    /**
     * Returns an AchievementProgress instance representing the player's
     * progress in every achievement.
     *
     * @param player The player to fetch achievement progress for.
     * @return An AchievementProgress instance with the player's progress.
     */ 
    public AchievementProgress progressForPlayer(Player player) throws DatabaseException {
        // TODO: implement me!

        // just map achievement IDs to the player's progress if they have any
        // (if they have no progress leave it out)
        HashMap<String, Integer> progress = new HashMap<String, Integer>();

        // and we also need to tell the progress what's been awarded so that we
        // can implement the inProgressAchievementIDs and awardedAchievementIDs
        // without needing to hit the server again to ask for the awardThresholds
        // of the achievements to do comparisons
        HashMap<String, Boolean> awarded = new HashMap<String, Boolean>();
        
        return new AchievementProgress(progress, awarded);
    }
    
    
    /**
     * Return the PLAYERS_ACHIEVEMENT table.
     * 
     * @throws DatabaseException
     */
    public void returnPlayersAchievement() throws DatabaseException {
    	// Get a connection to the database
    	Connection connection = Database.getConnection();
    	
    	Statement statement = null;
    	ResultSet resultSet = null;
    	
    	try {
    		statement = connection.createStatement();
    		resultSet = statement.executeQuery("SELECT * FROM PLAYER_ACHIEVEMENT");
    		
    		while(resultSet.next()) {
    			System.out.print("PlayerID: "+ resultSet.getString("playerID") 
    					+ " Achievement: " + resultSet.getString("achievementID") 
    					+ " Progress: " + resultSet.getString("progress") + "\n");
    		}
  
    	} catch (SQLException e) {
			e.printStackTrace();
			throw new DatabaseException("Unable to get achievements from database", e);
		} finally {
			try {
				if (resultSet != null){
					resultSet.close();
				}
				if (statement != null){
					statement.close();
				}
				if (connection != null){
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
    	
    }
    
    
    /**
     * Increments the player's progress for the achievement with ID
     * `achievementID`. 
     *
     * @param achievementID The ID of the achievement.
     * @param playerID The player whose progress should be incremented.
     * @return The new progress for the player, or -1 if they already have been awarded
     *           the achievement.
     * @throws DatabaseException
     */
    public int incrementProgress(Player player, String achievementID)
    		throws DatabaseException {
        // note that AchievementProgress represents a player's progress in *every*
        // achievement and not just one so it's a bit inefficient for this method.
        // simpler to just return the new progress or -1 if nothing needs doing
    	int progress = 0;
    	progress = initialiseProgress(player.getID(), achievementID);
    	if(!checkThreshold(achievementID, progress)) {
    		//Get a connection to the database
        	Connection connection = Database.getConnection();
        	
    		Statement statement = null;
    		ResultSet resultSet = null;
    		//Connect to table and select Achievement and increment
    		try {
    			int playerID = player.getID();
    			statement = connection.createStatement();
    			statement.executeUpdate("UPDATE PLAYER_ACHIEVEMENT " +
    					"SET PROGRESS = PROGRESS + 1 " +
    					"WHERE playerID=" + playerID + " " +
    					"AND achievementID='" + achievementID + "'");
    		} catch (SQLException e) {
    			e.printStackTrace();
    			throw new DatabaseException("Unable to get achievements from database", e);
    		} finally {
    			try {
    				if (resultSet != null){
    					resultSet.close();
    				}
    				if (statement != null){
    					statement.close();
    				}
    				if (connection != null){
    					connection.close();
    				}

                    // return the new progress
    				return progress + 1;
    			} catch (SQLException e) {
    				e.printStackTrace();
    			}
    		}
    	}
        // already have this achievement
        return -1;
    }
    
    /**
     * Checks if player's achievement exists in the database. If not, achievement is
     * initialised and created.
     * 
     * @param playerID The player's ID to check against DB
     * @param achievementID To check which achievement is being initialised
     */
	private int initialiseProgress(int playerID, String achievementID)
			throws DatabaseException {
		
		//Get a connection to the database
    	Connection connection = Database.getConnection();
    	
		int progress = 0;
		Statement statement = null;
		ResultSet resultSet = null;
		
		try {
			statement = connection.createStatement();
			//Check for any existing Achievement Progress
			resultSet = statement.executeQuery("SELECT * FROM PLAYER_ACHIEVEMENT " +
					"WHERE playerID = " + playerID + 
					" AND achievementID = '"+ achievementID + "'");
			if(!resultSet.next()) {
				//If no Progress is found,  the player initial achievement
				System.out.print("DB: Insert new player achievement record.\n");
				statement.executeUpdate("INSERT INTO PLAYER_ACHIEVEMENT(" +
						"playerID, achievementID, PROGRESS) " +
						"VALUES(" + playerID + ", '" + achievementID + "', 0)");
				progress = 1;
			} else {
				System.out.print("DB: Existing progress found. Increment progress continue.\n");
				progress = resultSet.getInt("progress");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DatabaseException("Unable to get PLAYER_ACHIEVEMENT from database", e);
		} finally {
			try {
				if (resultSet != null){
					resultSet.close();
				}
				if (statement != null){
					statement.close();
				}
				if (connection != null){
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return progress;
	}
	
	/**
	 * Check progress with the achievement threshold.
	 * 
	 * @param achievementID
	 * @param progress
	 * @return
	 * @throws DatabaseException
	 */
	private boolean checkThreshold(String achievementID, int progress) 
			throws DatabaseException{
		//Get a connection to the database
    	Connection connection = Database.getConnection();
		
		Statement stmt = null;
		ResultSet data = null;
		
		try {
			stmt = connection.createStatement();
			data = stmt.executeQuery("SELECT * FROM ACHIEVEMENTS " +
					"WHERE id = '" + achievementID + "' AND " +
							"THRESHOLD = " + progress);
			if(data.next()) {
				System.out.print("DB: Threshold has been reached.\n");
				return true;
			} else {
				return false;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DatabaseException("Unable to get PLAYER_ACHIEVEMENT from database", e);
		} finally {
			try {
				if (data != null){
					data.close();
				}
				if (stmt != null){
					stmt.close();
				}
				if (connection != null){
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}


