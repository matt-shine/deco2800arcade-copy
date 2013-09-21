package deco2800.server.database;

import deco2800.arcade.model.Game;
import deco2800.arcade.model.Icon;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
		// Get a connection to the database
		Connection connection = Database.getConnection();
		
		try {
			ResultSet tableData = connection.getMetaData().getTables(null, null, "GAMES", null);
			if (!tableData.next()) {
				Statement statement = connection.createStatement();
				statement.execute("CREATE TABLE GAMES(gameID INT PRIMARY KEY," 
						+ "NAME VARCHAR(30)," + "ID VARCHAR(30)," + "PRICE INT,"
                        + "DESCRIPTION VARCHAR(200)," + "ICONPATH VARCHAR(40));");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DatabaseException("Unable to create games table", e);
		}
	initialised = true;
	}


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
                try {
                    game.icon = new Icon(resultSet.getString("ICONPATH"));
                } catch (IOException e) {
                    game.icon = null;
                }
                game.pricePerPlay = resultSet.getInt("PRICE");

                games.add(game);
            }

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

