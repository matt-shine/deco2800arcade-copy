package deco2800.server.database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DeerForestStorage {
	
	private boolean initialised = false;

	public void initialise() throws DatabaseException{
		
		Connection connection = Database.getConnection();
		
		try {
			ResultSet tableData = connection.getMetaData().getTables(null, null, "DEER_PLAYER", null);
			if (!tableData.next()){
				Statement statement = connection.createStatement();
				statement.execute("CREATE TABLE PLAYER(PlayerID INT PRIMARY KEY,"
						+ "USERNAME VARCHAR(30) NOT NULL,"
						+ "Wins INT," 
						+ "Losses INT)");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DatabaseException("Unable to create player table", e);
		}
		
		try {
			ResultSet tableData = connection.getMetaData().getTables(null, null, "DECK", null);
			if (!tableData.next()){
				Statement statement = connection.createStatement();
				statement.execute("CREATE TABLE DECK("
						+ "DeckID INT NOT NULL,"
						+ "PlayerID INT NOT NULL,"
						+ "DeckName varchar(30),"
						+ "PRIMARY KEY(DeckID),"
						+ "FOREIGN KEY PlayerID REFERENCES DEER_PLAYER(playerID)");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DatabaseException("Unable to create deck table", e);
		}
		
		try {
			ResultSet tableData = connection.getMetaData().getTables(null, null, "CARD", null);
			if (!tableData.next()){
				Statement statement = connection.createStatement();
				statement.execute("CREATE TABLE CARD(CardID INT NOT NULL,"
						+ "PlayerID INT NOT NULL,"
						+ "DeckID INT NOT NULL,"
						+ "Type varchar(30),"
						+ "Name varchar(30),"
						+ "Attack INT,"
						+ "Health INT,"
						+ "PRIMARY KEY(CardID),"
						+ "FOREIGN KEY DeckID REFERENCES DECK(DeckID),"
						+ "FOREIGN KEY PlayerID REFERENCES DEER_PLAYER(PlayerID)");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DatabaseException("Unable to create card table", e);
		}
		initialised = true;
	}
	
	public String getGameName(int deckID) throws DatabaseException {
		if (!initialised) {
			initialise();
		}
		
		Connection connection = Database.getConnection();
		
		Statement statement = null;
		ResultSet resultSet = null;
		try {
			statement = connection.createStatement();
			resultSet = statement.executeQuery("SELECT * from DECK");
			String result = findDeckName(deckID, resultSet);
			
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DatabaseException("Unable to get deck name from database", e);
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
	}
	
	private String findDeckName(int deckID, ResultSet results) throws SQLException {
		String result = null;
		while (results.next()) {
			String user = results.getString("deckID");
			if (user.equals(deckID)) {
				result = results.getString("DeckName");
				break;
			}
		}
		return result;
	}	
}
