package deco2800.server.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

/**
 * This models the server storage for a forum. It would be best to use
 * a transaction on every SQL-related function.
 * 
 * @author Forum
 * @see java.sql
 */
public class ForumStorage {
	/**
	 * TODO Create method to retrieve the DB data
	 * TODO Coordinate methods with forum classes.
	 * TODO Create test case for delete
	 */
	/* Fields */
	private boolean initialized = false;
	
	/**
	 * Return initialized flag
	 * 
	 * @return boolean, initialized flag
	 */
	public boolean getInitialized() {
		return this.initialized;
	}
	
	/**
	 * Initialise the DB with tables.
	 * 
	 * @throws	DatabaseException for any exceptions
	 */
	public void initialise() throws DatabaseException {
		Connection con;
		String dropParentThreadTable = "DROP TABLE parent_thread";
		String createParentThreadTable = "CREATE TABLE parent_thread"
				+ " (pid integer PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1)" 
				+ ", topic varchar(30) NOT NULL"
				+ ", content long varchar NOT NULL"
				+ ", created_by integer NOT NULL"
				+ ", timestamp timestamp"
				+ ", category varchar(30) DEFAULT 'General Admin'"
				+ ", tags long varchar)";
		String createChkCategory = "ALTER TABLE parent_thread ADD CHECK (category IN ('General Admin', 'Game Bug', 'Others'))";
		/* Create database connection */
		con = Database.getConnection();
		/* Create tables */
		try {
			Statement st = con.createStatement();
			con.setAutoCommit(false);
			ResultSet tables = con.getMetaData().getTables(null, null, "parent_thread", null);
			if (!tables.next()) {
				st.execute(dropParentThreadTable);
				st.execute(createParentThreadTable);
				st.execute(createChkCategory);
			}
			con.commit();
			st.close();
			initialized = true;
		} catch (SQLException e) {
			try {
				con.rollback();
			} catch (SQLException er) {
				throw new DatabaseException("Fail to rollback: ", er);
			}
			throw new DatabaseException("Fail to create table: " + e);
		} finally {
			try {
				con.setAutoCommit(true);
				con.close();
			} catch (SQLException e) {
				throw new DatabaseException("Fail to close DB connectoin: " + e);
			}
		}
	}
	
	/**
	 * Insert new parent thread.
	 * Return pid for success, otherwise -1.
	 * 
	 * 
	 */
	public int insertParentThread(String topic, String content
			, int createdBy, String category, String tags) throws DatabaseException {
		int result = -1;
		
		String insertParentThread = "INSERT INTO parent_thread"
				+ " (topic, content, created_by, timestamp, category, tags)"
				+ " VALUES(?, ?, ?, ?, ?, ?)";
		
		/* Check parameters */
		if (topic == "" || topic.length() > 30) {
			return result;
		} else if (content == "") {
			return result;
		} else if (createdBy < 0) {
			return result;
		} else if (category == "") {
			return result;
		}
		
		/* Create database connection */
		Connection con = Database.getConnection();
		
		/* Insert value */
		try {
			con.setAutoCommit(false);
			/* Prepare */
			PreparedStatement st = con.prepareStatement(insertParentThread);
			st.setString(1, topic);
			st.setString(2, category);
			st.setInt(3, createdBy);
			st.setTimestamp(4, this.getNow());
			st.setString(5, category);
			st.setString(6, tags);
			/* Execute */
			st.executeUpdate();
			con.commit();
		} catch (SQLException e) {
			try {
				con.rollback();
			} catch (SQLException er) {
				throw new DatabaseException("Fail to rollback: ", er);
			}
			throw new DatabaseException("InsertError: " + e);
		} finally {
			try {
				con.setAutoCommit(true);
				con.close();
			} catch (SQLException e) {
				throw new DatabaseException("ExitError: " + e);
			}
		}
		return result;
	}
	
	/**
	 * Retrieve parent thread data from given pid from DB.
	 * Return String[] for success and null for failure
	 * 
	 * @param pid, int, unique id of parent thread
	 * @return String[], contains the result set {pid, topic, content
	 * 					, createdBy, timestamp, category, tags}
	 */
	public String[] getParentThread(int pid) throws DatabaseException {
		String selectPid = "SELECT * FROM parent_thread WHERE pid=?";
		String[] result = new String[7];
		
		/* Create database connection */
		Connection con = Database.getConnection();
		
		/* Check parameter */
		if (pid < 0) {
			return null;
		}
		/* SELECT query */
		try {
			con.setAutoCommit(true);
			PreparedStatement st = con.prepareStatement(selectPid);
			st.setInt(1, pid);
			ResultSet rs = st.executeQuery();
			if (rs.next()) {
				result[0] = rs.getString("pid");
				result[1] = rs.getString("topic");
				result[2] = rs.getString("content");
				result[3] = rs.getString("created_by");
				result[4] = rs.getString("timestamp");
				result[5] = rs.getString("category");
				result[6] = rs.getString("tags");
			}
		} catch (SQLException e) {
			throw new DatabaseException("Fail to retrieve parent thread: " + e);
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				throw new DatabaseException("Fail to close DB connection: " + e);
			}
		}
		
		return result;
	}
	
	/**
	 * Retrieve all parent threads data from DB. Return String[][] for result
	 * , and return null for failure. 
	 * 
	 * @return	String[][],	{pthread = {pid, topic, content, createdBy
	 * 						, timestamp, category, tags}}
	 */
	public String[][] getAllParentThread() throws DatabaseException {
		String selectAll = "SELECT * FROM parent_thread";
		ArrayList<String[]> result = new ArrayList<String[]>();
		String[] strArray;
		Connection con = null;
		/* Get DB connection */
		try {
			con = Database.getConnection();
		} catch (Exception e) {
			return null;
		}
		/* Retrieve all parent threads from DB */
		try {
			PreparedStatement st = con.prepareStatement(selectAll);
			ResultSet rs = st.executeQuery();
			while (rs.next()) {
				strArray = new String[7];
				strArray[0] = rs.getString("pid");
				strArray[1] = rs.getString("topic");
				strArray[2] = rs.getString("content");
				strArray[3] = rs.getString("created_by");
				strArray[4] = rs.getString("timestamp");
				strArray[5] = rs.getString("category");
				strArray[6] = rs.getString("tags");
				result.add(strArray);
			}
		} catch (SQLException e) {
			throw new DatabaseException("Fail to retrieve parent threads: " + e);
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				throw new DatabaseException("Fail to close DB connection: " + e);
			}
		}
		/* Convert ArrayList to String[][] */
		String[][] result2 = new String[result.size()][7];
		result2 = result.toArray(result2);
		return result2;
	}
	
	public void deleteParentThread(int pid) throws DatabaseException {
		String deleteParentThread = "DELETE FROM parent_thread WHERE pid=?";
		Connection con;
		
		/* Check parameter */
		if (pid < 0) {
			return;
		}
		/* Get DB connection */
		try {
			con = Database.getConnection();
		} catch (Exception e) {
			return;
		}
		/* Delete parent thread having pid */
		try {
			PreparedStatement st = con.prepareStatement(deleteParentThread);
			st.setInt(1, pid);
			st.executeUpdate();
		} catch (SQLException e) {
			throw new DatabaseException("Fail to delete parent thread: " + e);
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				throw new DatabaseException("Fail to close DB connection: " + e);
			}
		}
	}
	
	private String getTagsString(String[] tags) {
		String result = "";
		for (int i = 0; i < tags.length; i++) {
			result += tags[i];
			result += ";";
		}
		return result;
	}
	
	private Timestamp getNow() {
		Date d = new Date();
		return new Timestamp(d.getTime());
	}
}
