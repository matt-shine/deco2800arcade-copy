package deco2800.server.database;

import java.sql.*;
import java.util.*;

public class ReplayStorage {
	
    private final String sessions = "REPLAY_SESSIONS";
    private final String events = "REPLAY_EVENTS";
    
	public void initialise() throws DatabaseException {
		
		Connection connection = Database.getConnection();
		Statement sessionsState = null;
		Statement eventsState = null;
		try{
			
			sessionsState = connection.createStatement();
			try {
				sessionsState.execute( "DROP TABLE " + sessions );
			} catch ( Exception e ) {
				
			}
			try {
				sessionsState.execute( "DROP TABLE " + events );
			} catch ( Exception e ) {
				
			}
			
			ResultSet sessionsTable = connection.getMetaData().getTables(null, null, sessions, null);
			if ( !sessionsTable.next() ){
				
				sessionsState = connection.createStatement();
				sessionsState.execute( "CREATE TABLE " + sessions + "(SessionID INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),"
						+ "GameID VARCHAR(255) NOT NULL,"
						+ "Recording BOOLEAN NOT NULL, "
						+ "UserName VARCHAR(30) NOT NULL,"
						+ "DateTime BIGINT NOT NULL, "
						+ "Comments VARCHAR(255) NOT NULL)");
			    
			}
			
			ResultSet eventsTable = connection.getMetaData().getTables(null, null, events, null);
			if ( !eventsTable.next() ){
				eventsState = connection.createStatement();
				eventsState.execute("CREATE TABLE " + events + "(EventID INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),"
							+ "SessionID INT NOT NULL,"
							+ "EventIndex INT NOT NULL," 	//Index of the most recent event
						    + "Event VARCHAR(255) NOT NULL)");
			}
			
		}catch( Exception e ){
			e.printStackTrace();
			throw new DatabaseException("Unable to create table", e);
		}finally{
			//free resources
			try{
				if ( sessionsState != null ){
					sessionsState.close();
				}
				if ( eventsState != null ){
					eventsState.close();
				}
				if ( connection != null ){
					connection.close();
				}
				
			}catch ( Exception e ){
				//Shouldn't occur
				e.printStackTrace();
				throw new DatabaseException("Something went awry.", e);
			}
		}	
	}
	

	//create sessionID?
	//set recording = true
	
	/**
	 * Sets recording = FALSE for given session.
	 * 
	 * @param sessionID
	 * @throws DatabaseException
	 */
	public void endRecording( int sessionID ) throws DatabaseException{
		Connection connection = null;
		Statement state = null;

		try{
			connection = Database.getConnection();
			state = connection.createStatement();
			state.executeUpdate("UPDATE " + sessions +  " "
					  + "SET Recording = false "
					  + "WHERE SessionID = " + sessionID);
			
		}catch( Exception e ){
			e.printStackTrace();
			throw new DatabaseException("Unable to update.", e);
		}finally{
			//free resources
			try{
				if ( state != null ){
					state.close();
				}
				if ( connection != null ){
					connection.close();
				}
				
			}catch ( Exception e ){
				//Shouldn't occur
				e.printStackTrace();
				throw new DatabaseException("Something went awry.", e);
			}			
		}
	}
	
	/**
	 * Returns an ArrayList of all information pertaining to specific gameID
	 * 
	 * @param gameID
	 * @return
	 */
	public ArrayList<String> getSessionsForGame( String gameID ) throws DatabaseException{
		//return list of everything from sessions table for that ID
		
		ArrayList<String> sessionsList = new ArrayList <String>();
		Connection connection = null;
		Statement state = null;
		String concat;

		try{
			connection = Database.getConnection();
			state = connection.createStatement();

			ResultSet results = state.executeQuery( "SELECT SessionID, "
					+ "Recording, UserName, DateTime, Comments FROM " + sessions + " WHERE"
					+ " GameID = '" + gameID + "'");
			

			
			while ( results.next() ){
				int sessionID = results.getInt( "SessionID" );
				boolean recording = results.getBoolean( "Recording" );
				String user = results.getString( "UserName" );
				long dateTime = results.getLong( "DateTime" );
				String comments = results.getString( "Comments" );
				
				concat = sessionID + ", " + recording + ", " + user + ", " +
				dateTime + ", " + comments; 
				
				sessionsList.add( concat );//.add?
				concat = "";
			}
			
		}catch( Exception e ){
			e.printStackTrace();
			throw new DatabaseException("Failed to retrieve sessions.", e);
		}finally{
			//free resources
			try{
				if ( state != null ){
					state.close();
				}
				if ( connection != null ){
					connection.close();
				}
				
			}catch ( Exception e ){
				//Shouldn't occur
				e.printStackTrace();
				throw new DatabaseException("Something went awry.", e);
			}			
		}
		return sessionsList;	
	}
	
	
	/**
	 * Currently returns an array of strings with events pertaining to sessionID 
	 * table SESSIONS with a SessionID of sessionID
	 * @param sessionID
	 * @return
	 */
	public List<String> getReplay( int sessionID ) throws DatabaseException{ //Want EventIDs too?
		
		Connection connection = null;
		Statement state = null;
		//String [] events;
		// HashMap <Integer, String> events =  new HashMap <Integer, String>();
		 
		List<String> eventList = new ArrayList<String>();
		
		try{
			connection = Database.getConnection();
			state = connection.createStatement();

			
			ResultSet results = state.executeQuery
					("SELECT Event FROM " + events + " WHERE SessionID =" + sessionID + " ORDER BY EventIndex");
			
			//int rows = results.last() ? results.getRow() : 0; //get number of rows from output\
			//might have to set cursor to beforeFirst() row
			
		//	events = new String[rows];
			while( results.next() ){
				String event = results.getString("Event");
				eventList.add( event);
			}
		}catch( Exception e ){
			e.printStackTrace();
			throw new DatabaseException("Failed to retrieve events.", e);
		}finally{
			//free resources
			try{
				if ( state != null ){
					state.close();
				}
				if ( connection != null ){
					connection.close();
				}
				
			}catch ( Exception e ){
				//Shouldn't occur
				e.printStackTrace();
				throw new DatabaseException("Something went awry.", e);
			}
		}
		
		return eventList;	
	}	
	
	/**
	 * Insert new entry into Sessions table.
	 * 
	 */
	public int insertSession( String gameID, 
			String userName, long dateTime, String comment ) throws DatabaseException{
		
		Connection connection = null;
		Statement state = null;
		
		int sessionID = -1;
		
		try{
			connection = Database.getConnection();
			
			state = connection.createStatement();
			String insert = "INSERT INTO " + sessions + " (GameID, Recording, UserName, DateTime, Comments) " 
							+ "VALUES ('" + gameID + "', true, '" 
							+ userName + "', " + dateTime + ", '" + comment 
							+ "')";
			state.executeUpdate(insert);
			
			state = connection.createStatement();
			String getSession = "SELECT MAX(SessionID) AS SID FROM " + sessions;
			ResultSet results = state.executeQuery(getSession);
			
			if( results.next() ){
				sessionID = results.getInt("SID");
			} else {
				// something's pretty wrong here, couldn't get the new session ID
			}
			
		}catch( Exception e ){
			e.printStackTrace();
			throw new DatabaseException("Failed to insert into " + sessions, e);
		}finally{
			//free resources
			
			try{
				if ( state != null ){
					state.close();
				}
				if ( connection != null ){
					connection.close();
				}
				
			}catch ( Exception e ){
				//Shouldn't occur
				e.printStackTrace();
				throw new DatabaseException("Insertion went awry.", e);
				
			}			
		}
		
		return sessionID;
	}
	
	/**
	 *  Insert new event into Events table.
	 *  
	 * @param eventID
	 * @param sessionID
	 * @param event
	 */
	public void insertEvent( int sessionID, int eventIndex, String event ) throws DatabaseException{
		Statement state = null;
		Connection connection = null;
		
		try{
			connection = Database.getConnection();
			state = connection.createStatement();
			
			String insert = "INSERT INTO " + events + " (SessionID, EventIndex, Event) " 
						+ "VALUES (" + sessionID 
							+ ", " + eventIndex 
							+ ", '" +event + "')";
					
			state.executeUpdate(insert);
		
		}catch( Exception e ){
			e.printStackTrace();
			throw new DatabaseException("Failed to insert into " + events, e);
		}finally{
			//free resources
			try{
				if ( state != null ){
					state.close();
				}
				if ( connection != null ){
					connection.close();
				}
				
			}catch ( Exception e ){
				//Shouldn't occur
				e.printStackTrace();
				throw new DatabaseException("Insertion went awry.", e);
			}			
		}
	}
	
	
	public void remove( int sessionID ) throws DatabaseException{
		Statement state = null;
		Connection connection = null;
		
		try{
			connection = Database.getConnection();
			state = connection.createStatement();
			
			
			String insert = "DELETE FROM " + sessions + " "
					      + "WHERE SessionID =" + sessionID;
			state.executeUpdate(insert);
			
			//need to remove from both like so?	
				
			insert = "DELETE FROM " + events + " " 
				   + "WHERE SessionID =" + sessionID;
			state.executeUpdate(insert);
			
		}catch( Exception e ){
			e.printStackTrace();
			throw new DatabaseException("Failed to remove.", e);
		}finally{
			//free resources
			try{
				if ( state != null ){
					state.close();
				}
				if ( connection != null ){
					connection.close();
				}
				
			}catch ( Exception e ){
				//Shouldn't occur
				e.printStackTrace();
				throw new DatabaseException("Removal went awry.", e);
			}			
		}
	}
	
	/**
	 * Retrieves all the events that have been missed between time of game start &
	 *  the time at which the spectator began viewing.
	 * @param sessionID - SessionID of game being viewed.
	 * @param dateTime - Time when spectator initiated viewing.
	 * @return
	 * @throws DatabaseException 
	 */
	//for spectator mode
	public HashMap<Integer, String> getMissedEvents( int sessionID, long dateTime ) throws DatabaseException{ 
		Statement state = null;
		Connection connection = null;
		HashMap <Integer, String> events = new HashMap<Integer, String>();
		
		try{
			connection = Database.getConnection();
			state = connection.createStatement();
			
			String insert = 
					"SELECT " + events + ".EventIndex, " + sessions + ".SessionID " + events + ".Event, " + sessions + ".DateTime"
					+ "FROM " + events
					+ "INNER JOIN " + sessions 
					+ "ON " + events + ".SessionID = " + sessions + ".SessionID"
					+ "WHERE SessionID = "+ sessionID
					+ "AND DateTime <=" + dateTime
					+ ")";

			ResultSet results = state.executeQuery(insert);
			
		//	int rows = results.last() ? results.getRow() : 0; //get number of rows from output\
			//might have to set cursor to beforeFirst() row
			
		//	events = new String[rows];
			
			while( results.next() ){
				String event = results.getString("Event");
				int eventIndex = results.getInt("EventIndex");
				//events.add(event);
				events.put(eventIndex, event);
			}
			
		}catch( Exception e ){
			e.printStackTrace();
			throw new DatabaseException("Failed to retrieve.", e);
		}finally{
			//free resources
			try{
				if ( state != null ){
					state.close();
				}
				if ( connection != null ){
					connection.close();
				}
				
			}catch ( Exception e ){
				//Shouldn't occur
				e.printStackTrace();
				throw new DatabaseException("Insertion went awry.", e);
			}			
		}	
		return events;
	}
	
	
/*	
   //spectator mode
	public Boolean missedEvents( int sessionID ){
		Statement state;
		Connection connection;
		
		try{
			connection = Database.getConnection();
			state = connection.createStatement();
			
			String insert = "INSERT INTO EVENTS " + "VALUES (" + eventID + ", " + sessionID + ", '"
							+ event + "')";
					
			state.executeUpdate(insert);
		
		}catch( Exception e ){
			e.printStackTrace();
			throw new DatabaseException("Failed to insert into EVENTS", e);
		}finally{
			//free resources
			try{
				if ( state != null ){
					state.close();
				}
				if ( connection != null ){
					connection.close();
				}
				
			}catch ( Exception e ){
				//Shouldn't occur
				e.printStackTrace();
				throw new DatabaseException("Insertion went awry.", e);
			}			
		}
		
	}*/
	
	
}
	
	
	

