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
import deco2800.arcade.accolades.Accolade;
import deco2800.arcade.accolades.AccoladeContainer;

/**
 * Implements Accolades storage on database.
 */
public class AccoladeStorage{ 
	private boolean initialised = false;

	
	/**
	 * Creates the Accolades table and sets initialised to TRUE on completion
	 * 
	 * @throws	DatabaseException	If SQLException occurs. 
	 */
	public void initialise() throws DatabaseException{
		if (initialised) return;

		//Get a connection to the database
		Connection connection = Database.getConnection();
		
		try {
			/**
			 * Create the accolades table
			 * 
			 * ID-The accolade ID.
			 * Value-The progress of the accolade.
			 * Name-The plain name identifier
			 * Description-The display string that will be used to make toString
			 * Unit-The unit to be used as part of toString
			 * Modifier-This is to modify the accolade into something interesting,
			 * 			say grenades as tonnes of TNT or something similar
			 * Tag-Combined tag that is used as part of Global_Accolades.Table
			 * Imagepath-The location of the associated accolade image.
			 * 
			 */
			ResultSet tableData = connection.getMetaData().getTables(null, null,
					"ACCOLADES", null);
			if (!tableData.next()){
				Statement statement = connection.createStatement();
				
				String String, String Unit, int modifier, String Tag,
				String Image
				statement.execute("CREATE TABLE ACCOLADES(ID INT RIMARY KEY," +
						"VALUE INT NOT NULL" +
						"NAME VARCHAR(30) NOT NULL," +
						"DESCRIPTION VARCHAR(100) NOT NULL," +
						"UNIT VARCHAR(10) NOT NULL" +
						"MODIFIER INT NOT NULL" +
						"TAG VARCHAR(20) NOT NULL" + 
						"IMAGEPATH VARCHAR(255) NOT NULL)";
			}
			/**
			 * Create the Player_accolades table
			 * 
			 * ID - ID of the player
			 * AccoladeID - foreign key of the ID in ACCOLADES table
			 */
			tableData = connection.getMetaData().getTables(null, null, "PLAYER_ACCOLADES", null);
			if (!tableData.next()) {
				statement.execute("CREATE TABLE PLAYER_ACCOLADES(ID INTEGER NOT NULL," +
							"ACCOLADEID INT," +
							"FOREIGN KEY(ACCOLADEID) REFERENCES ACCOLADES(ID))");
			}
			/**
			 * Create the Game_accolades table
			 * 
			 * ID - ID of the game
			 * AccoladeID - foreign key of the ID in ACCOLADES table
			 */
			tableData = connection.getMetaData().getTables(null, null, "GAME_ACCOLADES", null);
			if (!tableData.next()) {
				statement.execute("CREATE TABLE GAME_ACCOLADES(ID INTEGER NOT NULL," +
							"ACCOLADEID INT," +
							"FOREIGN KEY(ACCOLADEID) REFERENCES ACCOLADES(ID))");
			}
			
		}
		 catch (SQLException e) {
			e.printStackTrace();
			throw new DatabaseException("Unable to create accolades table", e);
		}
		
	}

	/**
	 * 
	 * Get the accolade by accoladeID and return as an accolade object
	 * @param accoladeID
	 * @return Accolades
	 * @throws SQLException
	 * 
	 */
	public Accolade getAccolade(int accoladeID) throws DatabaseException {
		if (!initialised) {
			initialise();
		}
		Accolade result = null;
		Connection connection = Database.getConnection();
		
		Statement statement = null;
		ResultSet resultSet = null;
		try {
			statement = connection.createStatement();
			resultSet = statement.executeQuery("SELECT * from ACCOLADES " +
					"WHERE ID = '" + accoladeID + "'");

			if(resultSet == null){
				System.out.println("No such a accolade exist!");
			}else{
				result = new Accolade(accoladeID,
									resultSet.getInt("value"),
									resultSet.getString("name"),
									resultSet.getString("description"),
									resultSet.getString("unit"),
									resultSet.getInt("modifier"),
									resultSet.getString("tag"),
									resultSet.getString("imagepath")
							);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DatabaseException("Unable to get accolade from database", e);
		} finally {
			try {
				if (resultSet !=null) {
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
		return result;
	}
	/**
	 * 
	 * Get the accolades by playerID and return as an AccoladeContainer object
	 * @param playerID
	 * @return AccoladeContainer
	 * @throws SQLException
	 * 
	 */
	public AccoladeContainer getAccoladesByPlayerID(int playerID) throws DatabaseException{
		if (!initialised) {
			initialise();
		}
		AccoladeContainer result = null;
		Connection connection = Database.getConnection();
		
		Statement statement = null;
		ResultSet resultSet = null;
		try {
			statement = connection.createStatement();
			resultSet = statement.executeQuery("SELECT * from PLAYER_ACCOLADES " +
					"WHERE ID = '" + plaerID + "'");
			while(resultSet.next()){
				Accolade a = getAccolade(resultSet.getInt("accoladeid"));
				result.add(a);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DatabaseException("Unable to get accolade from database", e);
		} finally {
			try {
				if (resultSet !=null) {
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
		return result;
	}
	/**
	 * 
	 * Get the accolades by gameID and return as an AccoladeContainer object
	 * @param gameID
	 * @return AccoladeContainer
	 * @throws SQLException
	 * 
	 */
	public AccoladeContainer getAccoladesByGameID(int gameID) throws DatabaseException{
		if (!initialised) {
			initialise();
		}
		AccoladeContainer result = null;
		Connection connection = Database.getConnection();
		
		Statement statement = null;
		ResultSet resultSet = null;
		try {
			statement = connection.createStatement();
			resultSet = statement.executeQuery("SELECT * from GAME_ACCOLADES " +
					"WHERE ID = '" + gameID + "'");
			while(resultSet.next()){
				Accolade a = getAccolade(resultSet.getInt("accoladeid"));
				result.add(a);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DatabaseException("Unable to get accolade from database", e);
		} finally {
			try {
				if (resultSet !=null) {
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
		return result;
	}

}
	
	
