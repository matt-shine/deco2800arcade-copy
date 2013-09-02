package deco2800.server.database;

import java.sql.*;
import java.util.*;

public class ReplayStorage {
	
	public void initialise() throws DatabaseException {
		
		Connection connection = Database.getConnection();
		Statement sessionsState = null;
		Statement eventsState = null;
		try{
			ResultSet sessionsTable = connection.getMetaData().getTables(null, null, "SESSIONS", null);
			if ( !sessionsTable.next() ){
				sessionsState = connection.createStatement();
				sessionsState.execute("CREATE TABLE SESSIONS(SessionID INT PRIMARY KEY AUTO_INCREMENT," +
						"GameID INT NOT NULL"+
						"Recording BOOLEAN NOT NULL" +
						"User VARCHAR(30) NOT NULL," +
						"DateTime LONG NOT NULL" +
						"Comments VARCHAR(255) NOT NULL)");
			}
			
			ResultSet eventsTable = connection.getMetaData().getTables(null,null,"EVENTS", null);
			if ( !eventsTable.next() ){
				eventsState = connection.createStatement();
				eventsState.execute("CREATE TABLE EVENTS(EventID INT PRIMARY KEY AUTO_INCREMENT," +
							"SessionID INT NOT NULL," +
							"EventIndex INT NOT NULL" + //Index of the most recent event
						    "Event STRING NOT NULL)");
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
			state.executeUpdate("UPDATE SESSIONS"
					  + "SET Recording = false"
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
	public ArrayList<String> getSessionsForGame( int gameID ) throws DatabaseException{
		//return list of everything from sessions table for that ID
		
		ArrayList<String> sessions = new ArrayList <String>();
		Connection connection = null;
		Statement state = null;
		String concat;

		try{
			connection = Database.getConnection();
			state = connection.createStatement();
			ResultSet results = state.executeQuery( "SELECT SessionID, "
					+ "Recording, User, DateTime, Comments FROM SESSIONS WHERE"
					+ "GameID =" + gameID );
			
			while ( results.next() ){
				int sessionID = results.getInt( "SessionID" );
				boolean recording = results.getBoolean( "Recording" );
				String user = results.getString( "User" );
				long dateTime = results.getLong( "DateTime" );
				String comments = results.getString( "Comments" );
				
				concat = sessionID + ", " + recording + ", " + user + ", " +
				dateTime + ", " + comments; 
				
				sessions.add( concat );//.add?
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
		return sessions;	
	}
	
	
	/**
	 * Currently returns an array of strings with events pertaining to sessionID 
	 * table SESSIONS with a SessionID of sessionID
	 * @param sessionID
	 * @return
	 */
	public  HashMap<Integer, String> getReplay( int sessionID ) throws DatabaseException{ //Want EventIDs too?
		
		Connection connection = null;
		Statement state = null;
		//String [] events;
		 HashMap <Integer, String> events =  new HashMap <Integer, String>();
	
		
		try{
			connection = Database.getConnection();
			state = connection.createStatement();
			
			ResultSet results = state.executeQuery
					("SELECT EventIndex, Event FROM EVENT WHERE SessionID =" + sessionID);
			//int rows = results.last() ? results.getRow() : 0; //get number of rows from output\
			//might have to set cursor to beforeFirst() row
			
		//	events = new String[rows];
			while( results.next() ){
				int eventIndex = results.getInt("EventIndex");
				String event = results.getString("Event");
				events.put(eventIndex, event);
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
	return events;	
}	
	
	/**
	 * Insert new entry into Sessions table.
	 * 
	 */
	public void insertSession( int sessionID, int gameID, boolean recording,
			String userName, long dateTime, String comment ) throws DatabaseException{
		
		Connection connection = null;
		Statement state = null;
		
		try{
			connection = Database.getConnection();
			state = connection.createStatement();
			String insert = "INSERT INTO SESSIONS " + "VALUES ("+ sessionID +", " + gameID + ", "
							+ recording + ", '" + userName + "', " + dateTime + ", '" + comment 
							+ "')";
			state.executeUpdate(insert);
			
			
		}catch( Exception e ){
			e.printStackTrace();
			throw new DatabaseException("Failed to insert into SESSIONS", e);
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
	
	/**
	 *  Insert new event into Events table.
	 *  
	 * @param eventID
	 * @param sessionID
	 * @param event
	 */
	public void insertEvent( int eventID, int sessionID, int eventIndex, String event ) throws DatabaseException{
		Statement state = null;
		Connection connection = null;
		
		try{
			connection = Database.getConnection();
			state = connection.createStatement();
			
			String insert = "INSERT INTO EVENTS " + "VALUES (" + eventID + ", " + sessionID + ", "
							+ eventIndex +", '" +event + "')";
					
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
	}
	
	
	public void remove( int sessionID ) throws DatabaseException{
		Statement state = null;
		Connection connection = null;
		
		try{
			connection = Database.getConnection();
			state = connection.createStatement();
			
			
			String insert = "DELETE FROM SESSIONS" 
					      + "WHERE SessionID =" + sessionID;
			state.executeUpdate(insert);
			
			//need to remove from both like so?	
				
			insert = "DELETE FROM EVENTS " 
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
					"SELECT EVENTS.EventIndex, SESSION.SessionID EVENTS.Event, SESSIONS.DateTime"
					+ "FROM EVENTS"
					+ "INNER JOIN SESSIONS" 
					+ "ON EVENTS.SessionID = SESSIONS.SessionID"
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
	
	
	

