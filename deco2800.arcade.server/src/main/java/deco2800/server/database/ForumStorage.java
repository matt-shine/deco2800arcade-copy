package deco2800.server.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
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
	 * TODO 1. Create Initialization with test
	 * Note; only initialization with parent_thread is implemented.
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
				+ ", category varchar(10) DEFAULT 'General Admin'"
				+ ", tags long varchar)";
		String createChkCategory = "ALTER TABLE parent_thread ADD CHECK (category IN ('General Admin', 'Game Bug', 'Others'))";
		/* Create database connection */
		try {
			con = Database.getConnection();
		} catch (Exception e) {
			throw new DatabaseException(e);
		}
		
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
			throw new DatabaseException("Fail to create table: " + e);
		} catch (Exception e) {
			throw new DatabaseException("Unhandled error: " + e);
		} finally {
			try {
				con.setAutoCommit(true);
				con.close();
			} catch (Exception e) {
				throw new DatabaseException("Failt to set autocommit");
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
			, int createdBy, String category, String[] tags) throws DatabaseException {
		int result = -1;
		
		String insertParentThread = "INSERT INTO parent_thread"
				+ " (topic, content, created_by, timestamp, category, tags)"
				+ " VALUES(?, ?, ?, ?, ?, ?)";
		
		/* Create database connection */
		Connection con = null;
		try {
			con = Database.getConnection();
		} catch (Exception e) {
			throw new DatabaseException(e);
		} 
		
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
			st.setString(6, this.getTagsString(tags));
			/* Execute */
			st.executeUpdate();
			con.commit();
		} catch (SQLException e) {
			throw new DatabaseException("InsertError: " + e);
		} catch (Exception e) {
			throw new DatabaseException(e);
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
	
	private String getTagsString(String[] tags) {
		String result = "";
		for (int i = 0; i < tags.length; i++) {
			result += tags[i];
			result += ":";
		}
		return result;
	}
	
	private Timestamp getNow() {
		Date d = new Date();
		return new Timestamp(d.getTime());
	}
}
