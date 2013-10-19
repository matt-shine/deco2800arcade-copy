package deco2800.server.database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import deco2800.arcade.model.Accolade;
import deco2800.arcade.model.AccoladeContainer;

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
			 * GameID- the ID of the game for the accolade
			 * Value-The progress of the accolade.
			 * Name-The plain name identifier
			 * Description-The display string that will be used to make toString
			 * PopUp - When the raw value is a multiple of this a message appears on the game client
			 * PopUpMessage - This is the message that appears when the accolade popup value is reached
			 * Unit-The unit to be used as part of toString
			 * Modifier- This is multiplied against the value to produce the final value (hint:use fractions for division)
			 * Tag-Combined tag that is used as part of Global_Accolades.Table
			 * Imagepath-The location of the associated accolade image.
			 * 
			 */
			ResultSet tableData = connection.getMetaData().getTables(null, null,
					"ACCOLADES", null);
			if (!tableData.next()){
				Statement statement = connection.createStatement();
				statement.execute("CREATE TABLE ACCOLADES(" +
						"ID INT PRIMARY KEY, " +
						"GAMEID INT NOT NULL," +
						"VALUE INT NOT NULL, " +
						"NAME VARCHAR(30) NOT NULL, " +
						"DESCRIPTION VARCHAR(100) NOT NULL, " +
						"POPUP INT NOT NULL, " +
						"POPUPMESSAGE VARCHAR(100) NOT NULL, " +
						"UNIT VARCHAR(10) NOT NULL, " +
						"MODIFIER REAL NOT NULL, " +
						"TAG VARCHAR(20) NOT NULL, " + 
						"IMAGEPATH VARCHAR(255) NOT NULL)");
				
			}
			/**
			 * Create the Player_accolades table
			 * 
			 * ID - ID of the player
			 * AccoladeID - foreign key of the ID in ACCOLADES table
			 */
			tableData = connection.getMetaData().getTables(null, null, "PLAYER_ACCOLADES", null);
			if (!tableData.next()) {
				Statement statement = connection.createStatement();
				statement.execute("CREATE TABLE PLAYER_ACCOLADES("+
							"ID INTEGER NOT NULL," +
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
				Statement statement = connection.createStatement();
				statement.execute("CREATE TABLE GAME_ACCOLADES(" + 
							"ID INT NOT NULL," +
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
				//TODO FIX THIS 
				 
				result = new Accolade(resultSet.getString("name"),
									resultSet.getString("description"),
									resultSet.getInt("popup"), 
									resultSet.getString("popupMessage"),
									resultSet.getFloat("modifier"),
									resultSet.getString("unit"),
									resultSet.getString("tag"),
									resultSet.getString("imagepath")
									).setID(accoladeID
									).setValue(resultSet.getInt("value")
									).setGameID(resultSet.getInt("gameID")
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
					"WHERE ID = '" + playerID + "'");
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
	
	
