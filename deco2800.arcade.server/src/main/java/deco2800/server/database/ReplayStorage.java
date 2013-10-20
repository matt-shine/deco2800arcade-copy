package deco2800.server.database;

import java.sql.*;
import java.util.*;

public class ReplayStorage {
	
    private static final String SESSIONS_TABLE = "REPLAY_SESSIONS";
    private static final String EVENTS_TABLE = "REPLAY_EVENTS";
    
    public static final int SESSION_ID_INDEX = 0;
    public static final int RECORDING_INDEX = 1;
    public static final int USER_INDEX = 2;
    public static final int DATE_INDEX = 3;
    public static final int COMMENT_INDEX = 4;
    
	public void initialise() throws DatabaseException {
		
		Connection connection = Database.getConnection();
		Statement sessionsState = null;
		Statement eventsState = null;
		ResultSet sessionsTable = null;
		ResultSet eventsTable = null;
		try{
			
			sessionsState = connection.createStatement();
			try {
				sessionsState.execute( "DROP TABLE " + SESSIONS_TABLE );
			} catch ( Exception e ) { }
			
			try {
				sessionsState.execute( "DROP TABLE " + EVENTS_TABLE );
			} catch ( Exception e ) { }
			
			sessionsTable = connection.getMetaData().getTables(null, null, SESSIONS_TABLE, null);
			if ( !sessionsTable.next() ){
				sessionsState = connection.createStatement();
				sessionsState.execute( "CREATE TABLE " + SESSIONS_TABLE + "(SessionID INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),"
						+ "GameID VARCHAR(255) NOT NULL,"
						+ "Recording BOOLEAN NOT NULL, "
						+ "UserName VARCHAR(30) NOT NULL,"
						+ "DateTime BIGINT NOT NULL, "
						+ "Comments VARCHAR(255) NOT NULL)");
			}
			
			eventsTable = connection.getMetaData().getTables(null, null, EVENTS_TABLE, null);
			if ( !eventsTable.next() ){
				eventsState = connection.createStatement();
				eventsState.execute("CREATE TABLE " + EVENTS_TABLE + "(EventID INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),"
							+ "SessionID INT NOT NULL,"
							+ "EventIndex INT NOT NULL," 	//Index of the most recent event
						    + "Event VARCHAR(255) NOT NULL)");
			}
			
		} catch( Exception e ) {
			throw new DatabaseException( "Unable to create table", e );
		} finally {
		    close( connection );
            close( sessionsState );     
            close( eventsState );     
            close( sessionsTable );
            close( eventsTable );
		}
	}
	
	/**
	 * Sets recording = FALSE for given session.
	 * 
	 * @param sessionID, the ID of the session to close
	 * @throws DatabaseException
	 */
	public void endRecording( int sessionID ) throws DatabaseException{
		Connection connection = null;
		Statement state = null;

		try {
			connection = Database.getConnection();
			state = connection.createStatement();
			state.executeUpdate("UPDATE " + SESSIONS_TABLE +  " "
					  + "SET Recording = false "
					  + "WHERE SessionID = " + sessionID);
			
		} catch( Exception e ) {
			throw new DatabaseException( "Unable to update.", e );
		} finally {
		    close( connection );
            close( state );     	
		}	
	}
	
	/** 
	 * Returns a list of all GameIDs with replay data.
	 * 
	 * @return a List of Strings, where each string is the id of a game with replays
	 * @throws DatabaseException
	 */
	public List<String> getGameIds() throws DatabaseException {
		//return list of everything from sessions table for that ID
		
		List<String> gamesList = new ArrayList <String>();
		Connection connection = null;
		Statement state = null;
		ResultSet results = null;

		try {
			connection = Database.getConnection();
			state = connection.createStatement();

			results = state.executeQuery( "SELECT DISTINCT GameId FROM " + SESSIONS_TABLE );
			
			while ( results.next() ) {
				gamesList.add( results.getString( "GameId" ) );
			}
			
		} catch( Exception e ) {
			throw new DatabaseException( "Failed to retrieve sessions.", e );
		} finally {
		    close( connection );
            close( state ); 	
            close( results ); 	
		}	
		
		return gamesList;	
	}
	
	/**
	 * Gets all of the replay sessions corresponding to a particular game, stored
	 * as a List of Strings. Each String represents a single replay session.
	 * 
	 * @param gameID, the string ID of the game to get the sessions for
	 * @return a List of all information pertaining to specific gameID
	 */
	public List<String> getSessionsForGame( String gameID ) throws DatabaseException{
		
		ArrayList<String> sessionsList = new ArrayList <String>();
		Connection connection = null;
		Statement state = null;
		ResultSet results = null;
		String concat;

		try{
			connection = Database.getConnection();
			state = connection.createStatement();

			results = state.executeQuery( "SELECT SessionID, "
					+ "Recording, UserName, DateTime, Comments FROM " + SESSIONS_TABLE + " WHERE"
					+ " GameID = '" + gameID + "'");
			
			while ( results.next() ){
				int sessionID = results.getInt( "SessionID" );
				boolean recording = results.getBoolean( "Recording" );
				String user = results.getString( "UserName" );
				long dateTime = results.getLong( "DateTime" );
				String comments = results.getString( "Comments" );
				
				concat = sessionID + ", " + recording + ", " + user + ", " +
				dateTime + ", " + comments; 
				
				sessionsList.add( concat );
				concat = "";
			}
			
		} catch( Exception e ) {
			throw new DatabaseException( "Failed to retrieve sessions.", e );
		} finally {
		    close( connection );
            close( state ); 
			close( results );
		}	
		
		return sessionsList;	
	}
	
	
	/**
	 * Returns a List of String, with each String being a JSON serialised representation
	 * of a replay event. The elements of the list are ordered by their event index.
	 * 
	 * @param sessionID, the integer ID of the session to get replay data for
	 * @return a List of Strings, with each item being an Event in the given Session
	 */
	public List<String> getReplay( int sessionID ) throws DatabaseException { 
		
		Connection connection = null;
		Statement state = null;
		ResultSet results = null;

		List<String> eventList = new ArrayList<String>();
		
		try{
			connection = Database.getConnection();
			state = connection.createStatement();
			
			results = state.executeQuery( "SELECT Event FROM " +
			                              EVENTS_TABLE +
			                              " WHERE SessionID =" +
			                              sessionID +
			                              " ORDER BY EventIndex" );
			
			while( results.next() ) {
				String event = results.getString( "Event" );
				eventList.add( event);
			}
		} catch( Exception e ) {
			throw new DatabaseException( "Failed to retrieve events.", e );
		} finally {
		    close( connection );
            close( state ); 	
            close( results );
		}	
		
		return eventList;	
	}	
	
	/**
	 * Inserts a new session into the database, given a gameID, username, date/time and
	 * a comment.
	 * 
	 * @param gameID, the string ID of the game to start a session for
	 * @param userName, the string username of the user who started the session
	 * @param dateTime, the date/time, given as time since epoch (long)
	 * @param comment, the comment to attach to the replay
	 * @return the ID of the session that was just created
	 * @throws DatabaseException
	 */
	public int insertSession( String gameID, String userName, long dateTime,
	                          String comment ) throws DatabaseException {
		
		Connection connection = null;
		Statement state = null;
		ResultSet results = null;
		int sessionID = -1;
		
		try {
			connection = Database.getConnection();
			
			state = connection.createStatement();
			String insert = "INSERT INTO " + SESSIONS_TABLE + " (GameID, Recording, UserName, DateTime, Comments) " 
							+ "VALUES ('" + gameID + "', true, '" 
							+ userName + "', " + dateTime + ", '" + comment 
							+ "')";
			state.executeUpdate(insert);
			
			state = connection.createStatement();
			String getSession = "SELECT MAX(SessionID) AS SID FROM " + SESSIONS_TABLE;
			results = state.executeQuery(getSession);
			
			if( results.next() ){
				sessionID = results.getInt("SID");
			} else {
			    throw new DatabaseException("Failed to get new Session ID");
			}
			
		} catch ( Exception e ) {
			throw new DatabaseException("Failed to insert into " + SESSIONS_TABLE, e);
		} finally {
		    close( connection );
            close( state ); 
            close( results );
		}	
		
		return sessionID;
	}
	
	/**
	 * Inserts a new event into the database, given a session ID, event index (relative
	 * to the start of the session) and the flattened event string.
	 * 
	 * @param sessionID, the integer ID of the session to add the event for
	 * @param eventIndex, the index of the event within the replay
	 * @param event, the flattened string representing the event
	 * @throws DatabaseException
	 */
	public void insertEvent( int sessionID, int eventIndex, String event )
	                         throws DatabaseException {
		Statement state = null;
		Connection connection = null;
		
		try{
			connection = Database.getConnection();
			state = connection.createStatement();
			
			String insert = "INSERT INTO " + EVENTS_TABLE + " (SessionID, EventIndex, Event) " 
						+ "VALUES (" + sessionID 
							+ ", " + eventIndex 
							+ ", '" + event + "')";
					
			state.executeUpdate( insert );
		
		} catch( Exception e ) {
			throw new DatabaseException( "Failed to insert into " + EVENTS_TABLE, e );
		} finally {
			close( connection );
			close( state );		
		}	
	}
	
	/**
	 * Removes a given sessionID from the database.
	 * 
	 * @param sessionID, the ID of the session to remove
	 * @throws DatabaseException
	 */
	public void remove( int sessionID ) throws DatabaseException {
		Statement state = null;
		Connection connection = null;
		
		try {
			connection = Database.getConnection();
			state = connection.createStatement();
			
			
			String insert = "DELETE FROM " + SESSIONS_TABLE + " "
					      + "WHERE SessionID =" + sessionID;
			state.executeUpdate( insert );
			
			insert = "DELETE FROM " + EVENTS_TABLE + " " 
				   + "WHERE SessionID =" + sessionID;
			state.executeUpdate( insert );
			
		} catch( Exception e ) {
			throw new DatabaseException( "Failed to remove.", e );
		} finally {
		    close( connection );
            close( state ); 	
		}	
	}
	
	
	/**
	 * Handles the closing of a database connection.
	 * 
	 * @param connection the database connection to close
	 * @throws DatabaseException
	 */
	private void close(Connection connection) throws DatabaseException {
		
		try {
			
			if ( connection != null ) {
				connection.close();
			}
			
		} catch ( SQLException e ) {
			throw new DatabaseException("Could not close the resources.", e);
		}	
	}
	
	   /**
     * Handles the closing of a database statement.
     * 
     * @param statement the database statement to close
     * @throws DatabaseException
     */
    private void close(Statement statement) throws DatabaseException {
        
        try {
            
            if ( statement != null ) {
                statement.close();
            }
            
        } catch ( SQLException e ) {
            throw new DatabaseException("Could not close the resources.", e);
        }   
    }
    
    /**
     * Handles the closing of a database result set.
     * 
     * @param results the database result set to close
     * @throws DatabaseException
     */
    private void close(ResultSet results) throws DatabaseException {
        
        try {
            
            if ( results != null ) {
                results.close();
            }
            
        } catch ( SQLException e ) {
            throw new DatabaseException("Could not close the resources.", e);
        }   
    }
}
	
	
	

