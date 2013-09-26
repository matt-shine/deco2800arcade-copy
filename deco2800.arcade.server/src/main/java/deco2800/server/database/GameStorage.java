package deco2800.server.database;

import deco2800.arcade.model.Game;
import deco2800.arcade.model.Icon;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;


public class GameStorage {
	private boolean initialised = false;
	
	/** 
	 * Creates the Games table and sets initialised to TRUE on completion
	 * 
	 * @throws DatabaseException
	 *				If SQLException occurs.
	 */
	public void initialise() throws DatabaseException {
		if (initialised) return;

		// Get a connection to the database
		Connection connection = Database.getConnection();

		
		try {
            //System.out.println("Loading Games Table");
			ResultSet tableData = connection.getMetaData().getTables(null, null, "GAMES", null);

			if (!tableData.next()) {
                //System.out.println("Creating Games Table");
				Statement statement = connection.createStatement();
				statement.execute("CREATE TABLE GAMES(gameID INT NOT NULL," +
                        "ID VARCHAR (30) NOT NULL," +
                        "NAME VARCHAR(30) NOT NULL," +
                        "PRICE INT NOT NULL," +
                        "DESCRIPTION VARCHAR (500) NOT NULL," +
                        "ICONPATH VARCHAR (100)," +
                        "PRIMARY KEY(gameID))");
            } else {
                //Statement statement = connection.createStatement();
                //statement.executeUpdate("ALTER TABLE GAMES ALTER COLUMN DESCRIPTION VARCHAR (255) NOT NULL, ICONPATH VARCHAR (100)");
                //System.out.println("Filling Games Table");
                runScript(connection);
                //System.out.println("Filled Games Table");
            }
        } catch (SQLException e) {
			e.printStackTrace();
			throw new DatabaseException("Unable to create games table", e);
		}
	    initialised = true;
	}

    private void runScript(Connection connection) throws SQLException {
        Statement statement = connection.createStatement();

        String inserts = "TRUNCATE TABLE GAMES;" +
                "INSERT INTO GAMES values (0, 'Pong', 'Pong', 0, 'Tennis, without that annoying 3rd dimension!', '');" +
                "INSERT INTO GAMES values (1, 'deerforest', 'deerforest', 0, 'N/A', '');" +
                "INSERT INTO GAMES values (2, 'mixmaze', 'Mix Maze', 0, 'N/A', '');" +
                "INSERT INTO GAMES values (3, 'Breakout', 'Breakout', 0, 'Bounce the ball off your paddle to keep it from falling off the bottom of the screen.', '');" +
                "INSERT INTO GAMES values (4, 'burningskies', 'Burning Skies', 0, 'N/A', '');" +
                "INSERT INTO GAMES values (5, 'Checkers', 'Checkers', 0, 'It is checkers', '');" +
                "INSERT INTO GAMES values (6, 'chess', 'Chess', 0, 'N/A', '');" +
                "INSERT INTO GAMES values (7, 'Connect4', 'Connect4', 0, 'Fun old connect 4!', '');" +
                "INSERT INTO GAMES values (8, 'LandInvaders', 'LandInvaders', 0, 'funny game!', '');" +
                "INSERT INTO GAMES values (9, 'pacman', 'Pac man', 0, 'An implementation of the classic arcade game Pac ', '');" +
                "INSERT INTO GAMES values (10, 'Raiden', 'Raiden', 0, 'Flight fighter', '');" +
                "INSERT INTO GAMES values (11, 'towerdefence', 'Tower Defence', 0, 'N/A', '');" +
                "INSERT INTO GAMES values (12, 'Wolfenstein 3D', 'Wolfenstein 3D', 0, 'Killin Natzis', '');" +
                "INSERT INTO GAMES values (13, 'snakeLadder', 'snakeLadder', 0, 'N/A', '');" +
                "INSERT INTO GAMES values (14, 'tictactoe', 'Tic Tac Toe', 0, 'N/A', '');" +
                "INSERT INTO GAMES values (15, 'junglejump', 'Jungle Jump', 0, 'N/A', '');";
        String[] cmds = inserts.split(";");

        for (String cmd : cmds) {
            try {
                statement.addBatch(cmd);
            } catch (SQLException e) {

            }
            System.out.println(cmd);

        }

        statement.executeBatch();
    }

    /**
     * Return set of Games on the Server
     * @return Set of Games on the server
     * @throws DatabaseException
     */
    public Set<Game> getServerGames() throws DatabaseException {
        Set<Game> games = new HashSet<Game>();
        if (!initialised) {
            initialise();
        }

        Connection connection = Database.getConnection();

        Statement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * from GAMES");
            while (resultSet.next()) {
                Game game = new Game();
                game.id = resultSet.getString("ID");
                game.name = resultSet.getString("NAME");
                game.description = resultSet.getString("DESCRIPTION");
                game.icon = new Icon();
                game.icon.setPath(resultSet.getString("ICONPATH"));
                game.pricePerPlay = resultSet.getInt("PRICE");
                games.add(game);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException("Unable to get game set from database", e);
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
        return games;
    }

	/**
	 * Gets a games name from the table
	 * @param gameID
	 * @return string name of game
	 * @throws DatabaseException 	If SQLException occurs
	 */
	public String getGameName(int gameID) throws DatabaseException {
		if (!initialised) {
			initialise();
		}
		
		Connection connection = Database.getConnection();
		
		Statement statement = null;
		ResultSet resultSet = null;
		try {
			statement = connection.createStatement();
			resultSet = statement.executeQuery("SELECT * from GAMES");
			String result = findGameName(gameID, resultSet);
			
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DatabaseException("Unable to get game name from database", e);
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

    public String getGameID(int gameID) throws DatabaseException {
        if (!initialised) {
            initialise();
        }

        Connection connection = Database.getConnection();

        Statement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * from GAMES");
            String result = findGameID(gameID, resultSet);

            return result;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException("Unable to get game id from database", e);
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

    public int getGamePrice(int gameID) throws DatabaseException {
        if (!initialised) {
            initialise();
        }

        Connection connection = Database.getConnection();

        Statement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * from GAMES");
            int result = findGamePrice(gameID, resultSet);

            return result;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException("Unable to get game price from database", e);
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
	
	public String getGameDescription(int gameID) throws DatabaseException {
		if (!initialised) {
			initialise();
		}
		
		Connection connection = Database.getConnection();
		
		Statement statement = null;
		ResultSet resultSet = null;
		try {
			statement = connection.createStatement();
			resultSet = statement.executeQuery("SELECT * from GAMES");
			String result = findGameDescription(gameID, resultSet);
			
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DatabaseException("Unable to get game description from database", e);
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
	
	public String getIconPath(int gameID) throws DatabaseException {
		if (!initialised) {
			initialise();
		}
		
		Connection connection = Database.getConnection();
		
		Statement statement = null;
		ResultSet resultSet = null;
		try {
			statement = connection.createStatement();
			resultSet = statement.executeQuery("SELECT * from GAMES");
			String result = findIconPath(gameID, resultSet);
			
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DatabaseException("Unable to get game iconpath from database", e);
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
	
	/**
	 * Returns the game name when gameID matches the ID given
	 * @param	gameID
	 * @param 	results
	 * @return String game name
	 * @throws SQLException
	 */
	private String findGameName(int gameID, ResultSet results) throws SQLException {
		String result = null;
		while (results.next()) {
            int id = results.getInt("gameID");
            if (id == gameID) {
				result = results.getString("NAME");
				break;
			}
		}
		return result;
	}

    private String findGameID(int gameID, ResultSet results) throws SQLException {
        String result = null;
        while (results.next()) {
            int id = results.getInt("gameID");
            if (id == gameID) {
                result = results.getString("ID");
                break;
            }
        }
        return result;
    }

    private int findGamePrice(int gameID, ResultSet results) throws SQLException {
        int result = 0;
        while (results.next()) {
            int id = results.getInt("gameID");
            if (id == gameID) {
                result = results.getInt("PRICE");
                break;
            }
        }
        return result;
    }
	
	private String findGameDescription(int gameID, ResultSet results) throws SQLException {
		String result = null;
		while (results.next()) {
            int id = results.getInt("gameID");
            if (id == gameID) {
				result = results.getString("DESCRIPTION");
				break;
			}
		}
		return result;
	}
	
	private String findIconPath(int gameID, ResultSet results) throws SQLException {
		String result = null;
		while (results.next()) {
			int id = results.getInt("gameID");
			if (id == gameID) {
				result = results.getString("ICONPATH");
				break;
			}
		}
		return result;
	}
}
//look up row mapper - to create instances of the game

