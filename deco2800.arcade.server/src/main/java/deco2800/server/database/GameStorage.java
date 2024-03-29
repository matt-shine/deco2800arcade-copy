package deco2800.server.database;

import deco2800.arcade.model.Game;
import deco2800.arcade.model.Icon;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


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
		ResultSet resultSet = null;
		Statement statement = null;
		try {
			resultSet = connection.getMetaData().getTables(null, null, "GAMES", null);

			if (!resultSet.next()) {
				statement = connection.createStatement();
				statement.execute("CREATE TABLE GAMES(gameID INT NOT NULL," +
                        "ID VARCHAR (30) NOT NULL," +
                        "NAME VARCHAR(30) NOT NULL," +
                        "PRICE INT NOT NULL," +
                        "DESCRIPTION VARCHAR (5000) NOT NULL," +
                        "ICONPATH VARCHAR (100)," +
                        "PRIMARY KEY(gameID))");
            } else {
                runScript(connection);
            }
        } catch (SQLException e) {
        	 Logger logger = LoggerFactory.getLogger(GameStorage.class);
			 logger.error(e.getStackTrace().toString());
			throw new DatabaseException("Unable to create Games table", e);
		} finally {
			try {
				if (resultSet != null) {
					resultSet.close();
				}
				if (statement != null) {
					statement.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				 Logger logger = LoggerFactory.getLogger(GameStorage.class);
				 logger.error(e.getStackTrace().toString());
			}
		}
	    initialised = true;
	}

    /**
     * Run scripts to populate games table
     * @param connection Database connection
     * @throws SQLException
     */
    private void runScript(Connection connection) throws SQLException {
        Statement statement = connection.createStatement();

        String inserts = "TRUNCATE TABLE GAMES;" +
                "INSERT INTO GAMES values (0, 'Pong', 'Pong', 0, 'Tennis, without that annoying 3rd dimension!', '');" +
                "INSERT INTO GAMES values (1, 'deerforest', 'deerforest', 0, 'A battle of minds, fought with pieces of paper with deer on them. Probably. More dramatic than it sounds.', '');" +
                "INSERT INTO GAMES values (2, 'Mixmaze', 'Mix Maze', 0, 'Build as much box as you can, stop your opponent from building theirs.', '');" +
                "INSERT INTO GAMES values (3, 'Breakout', 'Breakout', 0, 'Bounce the ball off your paddle to keep it from falling off the bottom of the screen.', '');" +
                "INSERT INTO GAMES values (4, 'burningskies', 'Burning Skies', 0, 'Engage in one of the most thrilling space combat battles this side of the galaxy in the critically acclaimed.', '');" +
                "INSERT INTO GAMES values (5, 'Checkers', 'Checkers', 0, 'It is checkers', '');" +
                "INSERT INTO GAMES values (6, 'chess', 'Chess', 0, 'A game of Chess, where 2 players, dubbed white or black, aim to CheckMate their opponent before being CheckMated themselves.', '');" +
                "INSERT INTO GAMES values (7, 'Connect4', 'Connect4', 0, 'Fun old connect 4!', '');" +
                "INSERT INTO GAMES values (8, 'LandInvaders', 'LandInvaders', 0, 'funny game!', '');" +
                "INSERT INTO GAMES values (9, 'Pacman', 'Pac man', 0, 'An implementation of the classic arcade game PacMan', '');" +
                "INSERT INTO GAMES values (10, 'Raiden', 'Raiden', 0, 'A Space Flight fighter', '');" +
                "INSERT INTO GAMES values (11, 'towerdefence', 'Tower Defence', 0, 'Defend your towers with wit and strategy. Endless, fun, fast paced gameplay!', '');" +
                "INSERT INTO GAMES values (12, 'Wolfenstein 3D', 'Wolfenstein Time', 0, 'Fight through a maze of classic wolfenstein levels against the iceking and his minions.', '');" +
                "INSERT INTO GAMES values (13, 'snakeLadder', 'snakeLadder', 0, 'Many snakes and ladders.', '');" +
                "INSERT INTO GAMES values (14, 'tictactoe', 'Tic Tac Toe', 0, 'The game nobody can lose, and nobody can win.', '');" +
                "INSERT INTO GAMES values (15, 'junglejump', 'Jungle Jump', 0, 'Help Jay find Jays Jane', '');" +
                "INSERT INTO GAMES values (16, 'soundboard', 'UQ Soundboard', 0, 'The epic DECO2800 Soundboard!! Enjoy the master sounds of UQ!', '');" +
                "INSERT INTO GAMES values (17, 'GuessTheWord', 'GuessTheWord', 0, 'This word guessing is popular with people of all ages. Its challenging and exciting at the same time!!', '');" +
                "INSERT INTO GAMES values (18, 'MiniGolf', 'MiniGolf', 0, 'Search for buried treasure', '');" +
                "INSERT INTO GAMES values (19, 'lunarlander', 'LunarLander', 0, 'Can your Lunar Lander make it to the surface safely?', '');" +
                "INSERT INTO GAMES values (20, 'Cyra', 'Cyra', 0, 'Run, jump, swing sword.', '');" +
                "INSERT INTO GAMES values (21, 'hunter', 'Hunter Game', 0, 'A 2D platformer running game where you hunt animals before they eat you!', '');" +
        		"INSERT INTO GAMES values (22, 'blackjack', 'Blackjack', 0, 'Try your hand at blackjack!', '');";

        String[] cmds = inserts.split(";");

        for (String cmd : cmds) {
            try {
                statement.addBatch(cmd);
            } catch (SQLException e) {
            	 Logger logger = LoggerFactory.getLogger(GameStorage.class);
    			 logger.error(e.getStackTrace().toString());
            }
            //System.out.println(cmd);
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
        	 Logger logger = LoggerFactory.getLogger(GameStorage.class);
			 logger.error(e.getStackTrace().toString());
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
            	 Logger logger = LoggerFactory.getLogger(GameStorage.class);
    			 logger.error(e.getStackTrace().toString());
            }
        }
        return games;
    }

	/**
	 * Gets a games name from the table
	 * @param gameID game id
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
			return findGameName(gameID, resultSet);
		} catch (SQLException e) {
			 Logger logger = LoggerFactory.getLogger(GameStorage.class);
			 logger.error(e.getStackTrace().toString());
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
				 Logger logger = LoggerFactory.getLogger(GameStorage.class);
				 logger.error(e.getStackTrace().toString());
			}
		}
	}

    /**
     * Get game ID
     * @param gameID integer game id
     * @return GameID arcade string game id
     * @throws DatabaseException
     */
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
            return findGameID(gameID, resultSet);

        } catch (SQLException e) {
        	 Logger logger = LoggerFactory.getLogger(GameStorage.class);
			 logger.error(e.getStackTrace().toString());
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
            	 Logger logger = LoggerFactory.getLogger(GameStorage.class);
    			 logger.error(e.getStackTrace().toString());
            }
        }
    }

    /**
     * Get Game Price
     * @param gameID game ID
     * @return game price
     * @throws DatabaseException
     */
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
            return findGamePrice(gameID, resultSet);
        } catch (SQLException e) {
        	 Logger logger = LoggerFactory.getLogger(GameStorage.class);
			 logger.error(e.getStackTrace().toString());
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
            	 Logger logger = LoggerFactory.getLogger(GameStorage.class);
    			 logger.error(e.getStackTrace().toString());
            }
        }
    }

    /**
     * Get game description
     * @param gameID game id
     * @return game description
     * @throws DatabaseException
     */
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
			return findGameDescription(gameID, resultSet);
		} catch (SQLException e) {
			 Logger logger = LoggerFactory.getLogger(GameStorage.class);
			 logger.error(e.getStackTrace().toString());
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
				 Logger logger = LoggerFactory.getLogger(GameStorage.class);
				 logger.error(e.getStackTrace().toString());
			}
		}
	}

    /**
     * Get Icon Path
     * @param gameID game ID
     * @return Icon Path
     * @throws DatabaseException
     */
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
			return findIconPath(gameID, resultSet);
		} catch (SQLException e) {
			 Logger logger = LoggerFactory.getLogger(GameStorage.class);
			 logger.error(e.getStackTrace().toString());
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
				 Logger logger = LoggerFactory.getLogger(GameStorage.class);
				 logger.error(e.getStackTrace().toString());
			}
		}
	}
	
	/**
	 * Returns the game name when gameID matches the ID given
	 * @param	gameID game ID
	 * @param 	results Result Set
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

    /**
     * Find Game (Arcade - string) ID
     * @param gameID game id
     * @param results Result Set
     * @return Game (Arcade) ID
     * @throws SQLException
     */
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

    /**
     * Find Game Price for a game
     * @param gameID game id
     * @param results Result Set
     * @return price of game
     * @throws SQLException
     */
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

    /**
     * Find game description of a game
     * @param gameID Game ID
     * @param results Result Set
     * @return game description
     * @throws SQLException
     */
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

    /**
     * Find Icon path of a game
     * @param gameID Game ID
     * @param results Result Set
     * @return Icon Path
     * @throws SQLException
     */
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

