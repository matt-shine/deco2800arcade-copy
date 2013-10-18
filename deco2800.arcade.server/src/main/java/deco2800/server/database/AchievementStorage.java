package deco2800.server.database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.HashMap;
import java.util.regex.Pattern;
import java.io.File;
import java.io.IOException;
import java.io.FileNotFoundException;
import deco2800.arcade.model.Achievement;
import deco2800.arcade.model.AchievementProgress;
import deco2800.server.ResourceLoader;
import deco2800.server.ResourceHandler;
import deco2800.server.database.ImageStorage;
import org.w3c.dom.*;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;

import org.apache.log4j.PropertyConfigurator;
import org.slf4j.*;


/**
 * Implements Achievement storage on database.
 */
public class AchievementStorage { 
	
    private static Logger logger = LoggerFactory.getLogger(AchievementStorage.class);
	
    /**
     * Creates the Achievement tables and sets initialised to TRUE on completion
     * 
     * @throws	DatabaseException	If SQLException occurs. 
     */
    public  void initialise() throws DatabaseException{
	
		//Get a connection to the database
		Connection connection = Database.getConnection();
		//TODO This is a hack to ensure serverLog messages go where I want them rather
		//then getting caught by replay teams logger and printed to console, to be removed
		//when I hear back from them about merging our loggers into a serverLogger -Josh Team null
		PropertyConfigurator.configure("src/main/resources/log4j.properties");
			
		try {
		    ResultSet tableData = connection.getMetaData().getTables(null, null,
									     "ACHIEVEMENTS", null);
		    if (!tableData.next()){
			logger.info("No achievements table in AchievementStorage database, creating table now...");
			Statement statement = connection.createStatement();
			statement.execute("CREATE TABLE ACHIEVEMENTS(id VARCHAR(255) PRIMARY KEY," +
					  "NAME VARCHAR(30) NOT NULL," +
					  "DESCRIPTION VARCHAR(100) NOT NULL," +
					  "ICON VARCHAR(255) NOT NULL," +
					  "THRESHOLD INT NOT NULL)");
			logger.info("achievements table successfully created.");
		    }
				
		    ResultSet awardedAchievementData = connection.getMetaData().getTables(null, null,
											  "AWARDED_ACHIEVEMENT", null);
		    if (!awardedAchievementData.next()){
			logger.info("No awarded_achievement table in AchievementStorage database, creating table now...");
			Statement awardedAchievementStmt = connection.createStatement();
			awardedAchievementStmt.execute("CREATE TABLE AWARDED_ACHIEVEMENT(" + 
						       "id INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY(START WITH 1," +
						       "INCREMENT BY 1)," +
						       "playerID INT NOT NULL," +
						       "achievementID VARCHAR(255) NOT NULL)");
			logger.info("awarded_achievement table successfully created.");
		    }
				
		    ResultSet playerAchievementData = connection.getMetaData().getTables(null, null,
											 "PLAYER_ACHIEVEMENT", null);
		    if (!playerAchievementData.next()){
			logger.info("No playerAchievementData table in AchievementStorage database, creating table now...");
			Statement playerAchievementStatement = connection.createStatement();
			playerAchievementStatement.execute("CREATE TABLE PLAYER_ACHIEVEMENT(" +
							   "id INT PRIMARY KEY " +
							   "GENERATED ALWAYS AS IDENTITY(START WITH 1, INCREMENT BY 1)," +
							   "playerID INT NOT NULL," +
							   "achievementID VARCHAR(255) NOT NULL," +
							   "PROGRESS INT NOT NULL," +
							   "FOREIGN KEY (achievementID) REFERENCES ACHIEVEMENTS(id))");
			logger.info("playerAchievementData table successfully created.");
		    }
		    logger.info("Achivement Storage sucessfully initialised.");
		}
		catch (SQLException e) {
		    e.printStackTrace();
		    logger.error("Unable to create achievements tables in database");
		    throw new DatabaseException("Unable to create achievements tables", e);
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

	
    private void loadAchievement(Element achElem, String achFolder, String achXMLFilePath) throws DatabaseException {
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
		LinkedList<AchievementComponent> components = new LinkedList<AchievementComponent>();
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
			throw new RuntimeException("Unsupported tag: " + elemName + " in " + achXMLFilePath);
		    }
		}

		if(awardThreshold != -1 && components.size() > 0) {
		    throw new RuntimeException("Achievements can't have an award threshold as well as components");
		}

		if (components.size() > 0) {
                    awardThreshold = -1;
		}

		// actually see if we've got the icon - if not just log an error and set the Achievement
		// to have the unknown image so we don't throw and have bad data in the DB
		File icon = ResourceLoader.load(iconPath);
		if (icon.isFile()) {
		    imageStorage.set(iconPath, icon);
		} else {
		    logger.error("Achievement " + name + "'s icon doesn't exist at path: " + iconPath);
		    iconPath = ImageStorage.UNKNOWN_IMAGE_ID;
		}

		// load into DB
		statement.executeUpdate("INSERT INTO ACHIEVEMENTS " +
					"VALUES('" + id + "','" + name + "','" + description + "','" 
					+ iconPath + "'," + awardThreshold + ")");
                // also bring in the components
                for(AchievementComponent c : components) {
                    statement.executeUpdate("INSERT INTO ACHIEVEMENTS " +
					    "VALUES('" + c.id + 
					    "','__component','__component','__component'," + 
					    c.awardThreshold + ")");
                }
		
		logger.info("Achievement: {} added", name);
	    }
	} catch (SQLException e) {
	    e.printStackTrace();
	    logger.error("Unable to load in achievement from file");
	    throw new DatabaseException("Error in loading achievements.", e);
	} catch (FileNotFoundException e) {
            e.printStackTrace();
            logger.error("Could not find achievement image file");
            throw new DatabaseException("Couldn't find file", e);
        } catch (IOException e) {
	    logger.error("IO Exception thrown while reading image file");
	    throw new DatabaseException("IOException while reading image file", e);
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
	ResourceLoader.handleFilesMatchingPattern(
	    Pattern.compile("achievements\\.xml"), 1, 
	    new ResourceHandler() {
		public void handleFile(File f) {
		    try {
			String folder = f.getParentFile().getName();
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

			    loadAchievement((Element)achNode, folder, f.getPath());
			}
		    } catch(Exception e) {
			logger.error("Unable to load in data for achievements");
			e.printStackTrace();
		    }
		}
	    });
    }

    
    /**
     * Quries database for each of the specified achievement ID's and returns
     * each achievement in a list.
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
                if(Achievement.isComponentID(result.id)) continue;
                if(result.awardThreshold == -1) { // this achievement has components
                    result.awardThreshold = 0;
                    for(Achievement c : componentsForAchievement(result.id)) {
                        result.awardThreshold += c.awardThreshold;
                    }
                }
		achievements.add(result);

				
	    }
	    return achievements;
	} catch (SQLException e) {
	    e.printStackTrace();
	    logger.error("Unable to load in data for achievements");
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
    public ArrayList<Achievement> achievementsForGame(String gameId) throws DatabaseException {
        
        ArrayList<Achievement> achievements = new ArrayList<Achievement>();
    	
	//Get a connection to the database
	Connection connection = Database.getConnection();

	Statement statement = null;
	ResultSet results = null;
	try {
			
            statement = connection.createStatement();
            results = statement.executeQuery("SELECT * FROM ACHIEVEMENTS" +
                                             " WHERE ID LIKE '" + gameId + "%'" +
                                             " AND NAME != '__component'");
            while (results.next()) {
                Achievement ach = null;
                if(results.getString("threshold").equals("-1")) {
                    // it's a component achievement, we need to find its components
                    ArrayList<Achievement> components = componentsForAchievement(results.getString("id"));
                    int totalThreshold = 0;
                    for(Achievement c : components) {
                        totalThreshold += c.awardThreshold;
                    }
                    
                    ach = new Achievement(results.getString("id"),
                                          results.getString("name"),
                                          results.getString("description"),
                                          totalThreshold,
                                          results.getString("icon"));
                } else {
                    // regular achievement, we're solid
                    ach = new Achievement(results.getString("id"),
                                          results.getString("name"),
                                          results.getString("description"),
                                          results.getInt("threshold"),
                                          results.getString("icon"));
                }

                achievements.add(ach);
            }
			
	    return achievements;
	} catch (SQLException e) {
	    e.printStackTrace();
	    logger.error("Unable to retrieve achievement data for game: {}", gameId);
	    throw new DatabaseException("Unable to get achievements from database", e);
	} finally {
	    try {
		if (results != null){
		    results.close();
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
     * Returns an unsorted list of a game's achievements.
     *
     * @param gameId	The game to fetch achievements for.
     * @return			A list of Achievements for the supplied game.
     */
    public ArrayList<Achievement> componentsForAchievement(String achID) throws DatabaseException {
        
        ArrayList<Achievement> components = new ArrayList<Achievement>();
    	
	//Get a connection to the database
	Connection connection = Database.getConnection();

	Statement statement = null;
	ResultSet results = null;
	try {
			
            statement = connection.createStatement();
            results = statement.executeQuery("SELECT * FROM ACHIEVEMENTS" +
                                             " WHERE ID LIKE '" + achID + "%'" +
                                             " AND NAME = '__component'");
            while (results.next()) {
                Achievement ach = new Achievement(results.getString("id"),
                                                  results.getString("name"),
                                                  results.getString("description"),
                                                  results.getInt("threshold"),
                                                  results.getString("icon"));

                components.add(ach);
            }
			
	    return components;
	} catch (SQLException e) {
	    e.printStackTrace();
	    logger.error("Unable to retrieve components for achievement: {}", achID);
	    throw new DatabaseException("Unable to get components from database", e);
	} finally {
	    try {
		if (results != null){
		    results.close();
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
     * Returns an AchievementProgress instance representing the player's
     * progress in every achievement.
     *
     * @param player The player to fetch achievement progress for.
     * @return An AchievementProgress instance with the player's progress.
     */ 
    public AchievementProgress progressForPlayer(int playerID) throws DatabaseException {

        // just map achievement IDs to the player's progress if they have any
        // (if they have no progress leave it out)
        HashMap<String, Integer> progress = new HashMap<String, Integer>();

        // and we also need to tell the progress what's been awarded so that we
        // can implement the inProgressAchievementIDs and awardedAchievementIDs
        // without needing to hit the server again to ask for the awardThresholds
        // of the achievements to do comparisons
        HashMap<String, Boolean> awarded = new HashMap<String, Boolean>();
        
        // Get a connection to the database
    	Connection connection = Database.getConnection();
    	
    	Statement statement = null;
    	Statement achievementStmt = null;
    	ResultSet progressSet = null;
    	ResultSet achievementSet = null;
    	
    	try {
	    statement = connection.createStatement();
	    achievementStmt = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
	    progressSet = statement.executeQuery("SELECT * FROM PLAYER_ACHIEVEMENT" + 
						 " WHERE playerID = " + playerID );
	    achievementSet = achievementStmt.executeQuery("SELECT id, threshold FROM " +
							  "ACHIEVEMENTS");
	    while(progressSet.next()) {
		String achievementID = progressSet.getString("achievementID");
		int achievementProgress = progressSet.getInt("progress");
		// Check players progress against threshold.
		while(achievementSet.next()) {
		    // Find achievement to compare threshold.
		    if(achievementSet.getString("id").equals(achievementID)) {
			// If threshold is reached i.e. awarded..
			if(achievementSet.getInt("threshold") == achievementProgress) {
			    awarded.put(achievementID, true);
			    break;
			}
			// Else threshold has not reached i.e. progress..
			else {
			    progress.put(achievementID, achievementProgress);
			    break;
			}
		    }
		}
		achievementSet.beforeFirst();
	    }
    	} catch (SQLException e) {
	    e.printStackTrace();
	    logger.error("Unable to retrieve achievement progress for player with ID: {}", playerID);
	    throw new DatabaseException("Unable to get achievements from database", e);
	} finally {
	    try {
		if (progressSet != null){
		    progressSet.close();
		}
		if (achievementSet != null){
		    achievementSet.close();
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
        
        return new AchievementProgress(progress, awarded);
    }
    
    /**
     * Returns the achievement progress of a particular player.
     * 
     * @param playerID
     * @param achievementID
     * @return progress
     * @throws DatabaseException
     */
    public int progressForAchievement(int playerID, String achievementID) throws DatabaseException {
        // Get a connection to the database
    	Connection connection = Database.getConnection();
    	
    	Statement statement = null;
    	ResultSet resultSet = null;
    	
    	try {
	    statement = connection.createStatement();
	    resultSet = statement.executeQuery("SELECT * FROM PLAYER_ACHIEVEMENT" + 
                                               " WHERE achievementID = '" + achievementID + "'" + 
                                               " AND playerID = " + playerID);
	    int progress = 0;
	    if(resultSet.next()) {
                progress = resultSet.getInt("PROGRESS");
	    }
            return progress;
    	} catch (SQLException e) {
	    e.printStackTrace();
	    logger.error("Unable to retrieve progress for player with ID: {} in achievement: {}", playerID, achievementID);
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
  
    	} catch (SQLException e) {
	    e.printStackTrace();
	    logger.error("Failed to return players_achievement table from AchievementStorage database");
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
    public int incrementProgress(int playerID, String achievementID)
	throws DatabaseException {

        // note that AchievementProgress represents a player's progress in *every*
        // achievement and not just one so it's a bit inefficient for this method.
        // simpler to just return the new progress or -1 if nothing needs doing
    	int progress = 0;
    	progress = initialiseProgress(playerID, achievementID);
    	if(!checkThreshold(achievementID, progress)) {
	    //Get a connection to the database
	    Connection connection = Database.getConnection();
        	
	    Statement statement = null;
	    ResultSet resultSet = null;
	    //Connect to table and select Achievement and increment
	    try {
		statement = connection.createStatement();
		statement.executeUpdate("UPDATE PLAYER_ACHIEVEMENT " +
    					"SET PROGRESS = PROGRESS + 1 " +
    					"WHERE playerID=" + playerID + " " +
    					"AND achievementID='" + achievementID + "'");
	    } catch (SQLException e) {
		e.printStackTrace();
		logger.error("Unable to increment achievement: {} for player with ID: ", achievementID, playerID);
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

	    String overallID;
	    int overallProgress;
    		
            if(Achievement.isComponentID(achievementID)) {
                overallID = Achievement.idForComponentID(achievementID);
                // need to get the components and figure out the overall progress
                ArrayList<Achievement> components = componentsForAchievement(overallID);
                overallProgress = 0;
                for(Achievement c : components) {
                    overallProgress += progressForAchievement(playerID, c.id);
                }
            } else {
                // return new progress
                overallProgress = progressForAchievement(playerID, achievementID);
                overallID = achievementID;
            }
            
            if(checkThreshold(overallID, overallProgress)){
            	//Get a connection to the database
            	Connection connectionAwardCheck = Database.getConnection();
            	
		Statement statementAwardCheck = null;
		ResultSet resultSetAwardCheck = null;
		//Connect to table and select Achievement and increment
		try {
		    statementAwardCheck = connectionAwardCheck.createStatement();
		    statementAwardCheck.executeUpdate("INSERT INTO AWARDED_ACHIEVEMENT(" +
						      "playerID, achievementID) " +
						      "VALUES(" + playerID + ", '" + overallID + "')");
		} catch (SQLException e) {
		    e.printStackTrace();
		    logger.error("Unable to increment achievement: {} for player with ID: {}", achievementID, playerID);
		    throw new DatabaseException("Unable to get achievements from database", e);
		} finally {
		    try {
			if (resultSetAwardCheck != null){
			    resultSetAwardCheck.close();
			}
			if (statementAwardCheck != null){
			    statementAwardCheck.close();
			}
			if (connectionAwardCheck != null){
			    connectionAwardCheck.close();
			}
		    } catch (SQLException e) {
			e.printStackTrace();
		    }
		}
            }
            // get achievement corresponding to overallID
            //comparison between overallProgress and that achievement' threshold
            
            // TODO: Store into database if matches
            
            return overallProgress;
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
		//If no Progress is found, add an entry
		statement.executeUpdate("INSERT INTO PLAYER_ACHIEVEMENT(" +
					"playerID, achievementID, PROGRESS) " +
					"VALUES(" + playerID + ", '" + achievementID + "', 0)");
		progress = 0;
	    } else {
		progress = resultSet.getInt("PROGRESS");
	    }
	} catch (SQLException e) {
	    e.printStackTrace();
	    logger.error("Unable to initialise achievement: {} for player with ID: {}", achievementID, playerID);
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
		return true;
	    } else {
		return false;
	    }
			
	} catch (SQLException e) {
	    e.printStackTrace();
	    logger.error("Unable to retrieve achievements data from database");
	    throw new DatabaseException("Unable to get ACHIEVEMENTS from database", e);
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
	
    /**
     * Returns a integer of the number of players with a given achievement. 
     *
     * @param achievementID The ID of the achievement.
     * @throws DatabaseException
     */
    public int numPlayerWithAchievement(String achievementID)
	throws DatabaseException {

    	Connection connection = Database.getConnection();
    	
	Statement statement = null;
	ResultSet resultSet = null;
	//Connect to table and select Achievement and increment
	try {
	    statement = connection.createStatement();
	    resultSet = statement.executeQuery("SELECT COUNT(*) AS num FROM AWARDED_ACHIEVEMENT WHERE " +
					       "achievementID = '" + achievementID + "'");
	    resultSet.next();
	    return resultSet.getInt("num");
			
	} catch (SQLException e) {
	    e.printStackTrace();
	    logger.error("Unable to retrieve the number of players who have the achievement: {}", achievementID);
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
	
}


