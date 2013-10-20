package deco2800.server.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import deco2800.arcade.packman.PackageUtils;

public class GamePath {

	private boolean initialised = false;

	public void initialise() throws DatabaseException {

		// Get a connection to the database
		Connection connection = Database.getConnection();

		try {
			ResultSet tableData = connection.getMetaData().getTables(null,
					null, "GAMEPATH", null);
			if (!tableData.next()) {
				Statement statement = connection.createStatement();
				statement.execute("CREATE TABLE GAMEPATH(gameID VARCHAR(40) PRIMARY KEY,"
								+ "path VARCHAR(200) NOT NULL,"
								+ "md5Hash LONG VARCHAR NOT NULL)");
				/* hard coded insert of the games into the table
				 * this should be done dynamically however due to other
				 * tables being hard coded I've had to as well
				 */
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DatabaseException("Unable to create game path table", e);
		}
		initialised = true;
	}
	
	public void addTheGames() throws DatabaseException {
		insertGame("pong", "pong-1.0.tar.gz");
		insertGame("deerforest", "deerforest-1.0.tar.gz");
		insertGame("Mixmaze", "Mixmaze-1.0.tar.gz");
		insertGame("Breakout", "Breakout-1.0.tar.gz");
		insertGame("burningskies", "burningskies-1.0.tar.gz");
		insertGame("Checkers", "Checkers-1.0.tar.gz");
		insertGame("chess", "chess-1.0.tar.gz");
		insertGame("Connect4", "Connect4-1.0.tar.gz");
		insertGame("LandInvaders", "LandInvaders-1.0.tar.gz");
		insertGame("Pacman", "Pacman-1.0.tar.gz");
		insertGame("Raiden", "Raiden-1.0.tar.gz");
		insertGame("towerdefence", "towerdefence-1.0.tar.gz");
		insertGame("Wolfenstein 3D", "Wolfenstein 3D-1.0.tar.gz");
		insertGame("snakeLadder", "snakeLadder-1.0.tar.gz");
		insertGame("tictactoe", "tictactoe-1.0.tar.gz");
		insertGame("junglejump", "junglejump-1.0.tar.gz");
		insertGame("soundboard", "soundboard-1.0.tar.gz");
		insertGame("GuessTheWord", "GuessTheWord-1.0.tar.gz");
		insertGame("MiniGolf", "MiniGolf-1.0.tar.gz");
		insertGame("lunarlander", "lunarlander-1.0.tar.gz");
	}
	/**
	 * Searches for the path of the game with a particular gameID
	 * @param gameID
	 *          ID for the requested game
	 * @return
	 *          Returns the path of the requested game
	 * @throws DatabaseException
	 *          If SQLException occurs
	 */
	public String getPath(String gameID) throws DatabaseException{
		if(!initialised){
			initialise();
		}
		Connection connection = Database.getConnection();

		Statement statement = null;
		ResultSet resultSet = null;
		try {
			statement = connection.createStatement();
			resultSet = statement.executeQuery("SELECT * FROM GAMEPATH WHERE"
					+ " gameID=" + gameID);
			String result = findPath(resultSet, gameID);

			return result;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DatabaseException("Unable to get game path from database",
					e);
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
	
	private String findPath(ResultSet results, String gameID) throws SQLException {
		String result = "";
		
		while (results.next()){
			String id = results.getString("gameID");
			if (id.equals(gameID)){
				result = results.getString("path");
				break;
			}
		}
		
		return result;
	}
	/**
	 * adds a game to the table with the specified ID and Path
	 * @param gameID
	 *          ID of the game to be added
	 * @param gamePath
	 *          Path of the game to be added
	 * @throws DatabaseException
	 *          If SQLException occurs
	 */
	public void insertGame(String gameID, String gamePath) throws DatabaseException{
		Connection connection = Database.getConnection();

		String gameMd5 = PackageUtils.genMD5(gamePath);
		Statement statement = null;
		String query = "INSERT INTO GAMEPATH VALUES ("+gameID+",'"+
		gamePath+"','"+gameMd5+"')";
		try {
			statement = connection.createStatement();
			statement.executeUpdate(query);
		} catch(SQLException e) {
			throw new DatabaseException("unable to add game to database",e);
		} finally {
			try {
				if (statement != null){
					statement.close();
				}
				if (connection != null){
					connection.close();
				}
			} catch (SQLException e){
				e.printStackTrace();
			}
		}
	}
	
	public String getMD5(String gameID) throws DatabaseException{
		if(!initialised){
			initialise();
		}
		Connection connection = Database.getConnection();

		Statement statement = null;
		ResultSet resultSet = null;
		try {
			statement = connection.createStatement();
			resultSet = statement.executeQuery("SELECT * FROM GAMEPATH WHERE"
					+ " gameID=" + gameID);
			String result = findMD5(resultSet, gameID);

			return result;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DatabaseException("Unable to get MD5 from database",
					e);
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
	
	private String findMD5(ResultSet results, String gameID) throws SQLException {
		String result = "";
		
		while (results.next()){
			String id = results.getString("gameID");
			if (id.equals(gameID)){
				result = results.getString("md5Hash");
				break;
			}
		}
		
		return result;
	}

}