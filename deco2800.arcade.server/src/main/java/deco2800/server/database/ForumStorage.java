package deco2800.server.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

import deco2800.arcade.model.Player;
import deco2800.arcade.model.User;
import deco2800.arcade.model.forum.ChildThread;
import deco2800.arcade.model.forum.ParentThread;

/**
 * This models the server storage for a forum into Java derby's DB 
 * with JDBC driver.
 * Forum consists of parent_thread and child_thread tables, and 
 * child_thread's p_thread attribute references parent_thread's pid.
 * <p>
 * Features:
 * <ul>
 * <li>PreparedStatement for all query and update command.</li>
 * <li>One commit algorithm for every functions (methods). I.e. if a function
 * has multiple update commands (i.e. change DB states), it commits the change 
 * manually.</li>
 * </ul>
 * 
 * @author Forum
 * @see java.sql
 */
public class ForumStorage {
	/* Fields */
	private boolean initialized = false;
	private String[] category = {"General Admin", "Game Bug", "New Game Idea", "News", "Others"};

	/**
	 * Return initialized flag
	 * 
	 * @return boolean, initialized flag
	 */
	public boolean getInitialized() {
		return this.initialized;
	}
	
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
		String createParentThreadTable = "CREATE TABLE parent_thread"
				+ " (pid int PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1)" 
				+ ", topic varchar(30) NOT NULL"
				+ ", message long varchar NOT NULL"
				+ ", created_by int NOT NULL"
				+ ", timestamp timestamp"
				+ ", category varchar(30) DEFAULT 'General Admin'"
				+ ", tags long varchar"
				+ ", vote int DEFAULT 0"
				+ ")";
		String createChildThreadTable = "CREATE TABLE child_thread"
				+ " (cid int PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1)"
				+ ", message long varchar NOT NULL"
				+ ", created_by int NOT NULL"
				+ ", timestamp timestamp"
				+ ", p_thread int NOT NULL"
				+ ", vote int DEFAULT 0"
				+ ")";
		String addFkPThread = "ALTER TABLE child_thread"
				+ " ADD CONSTRAINT fk_p_thread FOREIGN KEY (p_thread) REFERENCES parent_thread (pid) ON DELETE CASCADE";
		/* Create database connection */
		con = Database.getConnection();
		/* Create tables */
		try {
			Statement st = con.createStatement();
			con.setAutoCommit(false);
			ResultSet rs = con.getMetaData().getTables(null, null, "PARENT_THREAD", null);
			if (!rs.next()) {
				st.execute(createParentThreadTable);
				st.execute(this.getCategoryConstraint());
				this.insertParentThread("Test parent thread", "Very fist parent thread", 1, "General Admin", "Test#Parent thread");
			}
			rs.close();
			rs = con.getMetaData().getTables(null, null, "CHILD_THREAD", null);
			if (!rs.next()) {
				st.execute(createChildThreadTable);
				st.execute(addFkPThread);
				this.insertChildThread("Very first child thread.", 1, 1);
			}
			rs.close();
			con.commit();
			st.close();
			this.initialized = true;
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			} catch (SQLException er) {
				er.printStackTrace();
				throw new DatabaseException("Fail to rollback: ", er);
			} 
			this.initialized = false;
			throw new DatabaseException("Fail to create table: " + e);
		} finally {
			try {
				con.setAutoCommit(true);
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
				throw new DatabaseException("Fail to close DB connectoin: " + e);
			}
		}
		
	}
	
	/* Utilities */
	/* Utilities */
	/**
	 * Reset table i.e. delete all rows in all tables in ForumStorage
	 */
	public void resetTables() throws DatabaseException {
		String update = "DELETE FROM parent_thread";
		String update2 = "DELETE FROM child_thread";
		String alter = "ALTER TABLE parent_thread ALTER COLUMN pid RESTART WITH 1";
		String alter2 = "ALTER TABLE child_thread ALTER COLUMN cid RESTART WITH 1";
		Connection con = Database.getConnection();
		try {
			con.setAutoCommit(false);
			Statement st = con.createStatement();
			st.execute(update);
			st.execute(update2);
			st.execute(alter);
			st.execute(alter2);
			st.close();
			con.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			} catch (SQLException er) {
				e.printStackTrace();
				throw new DatabaseException("Fail to rollback, " + er.getMessage());
			}
			throw new DatabaseException("Fail to reset tables, " + e.getMessage());
		} finally {
			try {
				con.setAutoCommit(true);
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
				throw new DatabaseException("Fail to close connection, " + e.getMessage());
			}
		}
	}
	
	/** 
	 * Do not call this function unless special reasons. You may want to use resetTables() instead.
	 * Drop all tables of Forum Storage.
	 */
	public void dropAllTables() throws DatabaseException {
		String update1 = "DROP TABLE parent_thread";
		String update2 = "DROP TABLE child_thread";
		Connection con = Database.getConnection();
		try {
			Statement st = con.createStatement();
			con.setAutoCommit(false);
			ResultSet rs = con.getMetaData().getTables(null, null, "CHILD_THREAD", null);
			if (rs.next()) {
				st.execute(update2);
			}
			rs.close();
			rs = con.getMetaData().getTables(null, null, "PARENT_THREAD", null);
			if (rs.next()) {
				st.execute(update1);
			}
			rs.close();
			con.commit();
			st.close();
			this.initialized = false;
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			} catch (SQLException er) {
				throw new DatabaseException("Fail to rollback: ", er);
			} 
			this.initialized = false;
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
	
	/* Query */
	/**
	 * Get tags (non duplication) attached onto parent threads in DB as string array.
	 * Set limits to set the number of tags to be retrieved.
	 * Notes; the order of tags is always same since the order of query is organized by pid.
	 * 
	 * @param	limit, non-negative int, the number of tags to be retrieved. If limits == 0, all tags.
	 * @return	String array. If no tags, return empty string array (i.e. String[0]).
	 * @throws DatabaseException	if SQLException
	 */
	public String[] getAllTags(int limit) throws DatabaseException {
		String query = "SELECT tags FROM parent_thread";
		ArrayList<String> result = new ArrayList<String>();
		
		if (limit < 0) {
			return new String[0];
		}
		Connection con = Database.getConnection();
		boolean isLimit = false;
		try {
			PreparedStatement st = con.prepareStatement(query);
			ResultSet rs = st.executeQuery();
			while (rs.next()) {
				String tags = rs.getString("tags");
				if (tags != "" || tags != null) {
					String[] array = tags.split(TAG_SPLITTER);
					for (String tag : array) {
						if (!result.contains(tag)) {
							result.add(tag);
							if (limit != 0 && result.size() == limit) {
								isLimit = true;
							}
						}
					}
				}
				if (isLimit == true) {
					break;
				}
			}
			rs.close();
			st.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DatabaseException(e);
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
				throw new DatabaseException(e);
			}
		}
		if (result.size() == 0) {
			return new String[0];
		} else {
			return result.toArray(new String[0]);
		}
	}
		/* Queries */

	
	/**
	 * Retrieve parent thread from DB by given pid
	 * 
	 * @param pid, int parent thread id
	 * @return ParentThread instance
	 * @throws DatabaseException	Occurs if query error.
	 */
	public ParentThread getParentThread(int pid) throws DatabaseException {
		String query = "SELECT * FROM parent_thread WHERE pid = ?";
		ParentThread result;
		
		if (pid < 0) {
			return null;
		}
		
		Connection con = Database.getConnection();
		
		try {
			con.setAutoCommit(true);
			PreparedStatement st = con.prepareStatement(query);
			st.setInt(1, pid);
			ResultSet rs = st.executeQuery();
			if (rs.next()) {
				result = new ParentThread(rs.getInt("pid"), rs.getString("topic"), rs.getString("message")
						, new User(rs.getInt("created_by")), rs.getTimestamp("timestamp"), rs.getString("category")
						, rs.getString("tags"), rs.getInt("vote"));
			} else {
				result = null;
			}
			rs.close();
			st.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DatabaseException("Fail to retrieve parent thread: " + e);
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
				throw new DatabaseException("Fail to close DB connection: " + e);
			}
		}
		return result;
	}
	
	/**
	 * Get parent threads which has given tag.
	 * 
	 * @param tag, String, tag. If empty, returns empty array.
	 * @return	ParentThread[], array of parent threads which has the tag.
	 * 			Return empty ParentThread[] if no results.
	 * @throws DatabaseException	if SQLException.
	 */
	public ParentThread[] getTaggedParentThreads(String tag) throws DatabaseException {
		String query = "SELECT * FROM parent_thread";
		ArrayList<ParentThread> result = new ArrayList<ParentThread>();
		Connection con = Database.getConnection();
		if (tag == "") {
			return new ParentThread[0];
		}
		try {
			PreparedStatement st = con.prepareStatement(query);
			ResultSet rs = st.executeQuery();
			while (rs.next()) {
				String tags = rs.getString("tags");
				String[] tag_array = tags.split(TAG_SPLITTER);
				for (String temp : tag_array) {
					if (temp.equals(tag)) {
						result.add(new ParentThread(rs.getInt("pid"), rs.getString("topic"), rs.getString("message")
								, new User(rs.getInt("created_by")), rs.getTimestamp("timestamp"), rs.getString("category")
								, rs.getString("tags"), rs.getInt("vote")));
					}
				}
			}
			rs.close();
			st.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DatabaseException(e);
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
				throw new DatabaseException(e);
			}
		}
		return result.toArray(new ParentThread[0]);
	}
	
	/**
	 * Return array of ParentThreads with specifying id range. See param specification for 
	 * how to set the id range. All params must be non-negative.
	 * Query to be executed =	SELECT * FROM parent_thread
	 * 							WHERE [pid <= start [AND end <= pid]]
	 * 							[FETCH FIRST limit ROWS ONLY]
	 * 
	 * @param start	int, starting pid of parent thread to be retrieved, 
	 * 				if start = 0, it starts from first parent thread in table.
	 * 				(inclusive)
	 * @param end	int, ending pid of parent thread to be retrieved, 
	 * 				if end = 0, it does not specify the ending parent thread
	 * 				in table.
	 * 				(inclusive)
	 * @param limit	int, limit to number of parent threads to be returned, 
	 * 				if limit = 0 it does not specify the limit. 
	 * @return	String[][], 3D String array {{pid, topic, content, createdBy, 
	 * 			category, tags}}, return null if no result found.
	 * @throws	DatabaseException	if SQLException or invalid parameter.
	 */
	public ParentThread[] getParentThreads(int start, int end, int limit) throws DatabaseException {
		String query1 = "SELECT * FROM parent_thread WHERE ? <= pid AND pid <= ?";
		String query2 = "SELECT * FROM parent_thread WHERE ? <= pid";
		String query;
		ArrayList<ParentThread> result = new ArrayList<ParentThread>();
		
		/* Check params */
		if (start < 0 || end < 0 || limit < 0 || start < end) {
			throw new DatabaseException("Invalid parameter.");
		}
		
		/* Get DB connection */
		Connection con = Database.getConnection();
		
		/* Begin query */
		if (end == 0) {
			query = query2;
		} else {
			query = query1;
		}
		if (limit != 0) {
			query += " FETCH FIRST " + String.valueOf(limit) + " ROWS ONLY";
		}
		try {
			/* Prepare st */
			PreparedStatement st = con.prepareStatement(query);
			if (end == 0) {
				st.setInt(1, start);
			} else {
				st.setInt(1, start);
				st.setInt(2, end);
			}
			ResultSet rs = st.executeQuery();
			while (rs.next()) {
				ParentThread pThread = new ParentThread(rs.getInt("pid"), rs.getString("topic")
						, rs.getString("message"), new User(rs.getInt("created_by")), rs.getTimestamp("timestamp")
						, rs.getString("category"), rs.getString("tags"), rs.getInt("vote"));
				result.add(pThread);
			}
			rs.close();
			st.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DatabaseException("Fail to retrieve parent threads: " + e);
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
				throw new DatabaseException("Fail to close DB connection: " + e);
			}
		}
		if (result.size() == 0) {
			return null;
		} else {
			/* Convert ArrayList to String[][] */
			return result.toArray(new ParentThread[0]);
		}
	}
	
	/**
	 * Retrieve child thread having a specific cid.
	 * 
	 * @param cid	int, unique id of child thread
	 * @return ChildThread	null if no result.
	 * @throws DatabaseException	if SQLException or invalid parameter.
	 */
	public ChildThread getChildThread(int cid) throws DatabaseException {
		String query = "SELECT * FROM child_thread WHERE cid = ?";
		ChildThread result;
		if (cid < 0) {
			throw new DatabaseException("cid must be non-negative value.");
		}
		Connection con = Database.getConnection();
		try {
			PreparedStatement st = con.prepareStatement(query);
			st.setInt(1, cid);
			ResultSet rs = st.executeQuery();
			if (rs.next()) {
				result = new ChildThread(rs.getInt("cid"), rs.getString("message")
						, new User(rs.getInt("created_by")), rs.getTimestamp("timestamp") 
						, rs.getInt("vote"));
			} else {
				result = null;
			}
			rs.close();
			st.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DatabaseException("Fail to get child thread, " + e.getMessage());
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
				throw new DatabaseException("Fail to close DB connection: " + e);
			}
		}
		return result;
	}
	
	/**
	 * Return array of ParentThreads with specifying id range and having a specific parent thread. 
	 * See param specification for how to set the id range. All params must be non-negative.
	 * Overload method exists.
	 * Query to be executed =	SELECT * FROM parent_thread
	 * 							WHERE [pid <= start [AND end <= pid]]
	 * 							[FETCH FIRST limit ROWS ONLY]
	 * 
	 * @param pid	int, id of referencing parent thread
	 * @param start	int, starting cid to be retrieved. If 0, from first record.
	 * @param end	int, ending cid to be retrieved. If 0, no end.
	 * @param limit	int, number of records (child threads) to retrieve.
	 * @return	Array of ChildThread instances.
	 * @throws DatabaseException	if SQLException or invalid parameter.
	 */
	public ChildThread[] getChildThreads(int pid, int start, int end, int limit) throws DatabaseException {
		String query1 = "SELECT * FROM child_thread WHERE p_thread = ? AND ? <= cid AND cid <= ?";
		String query2 = "SELECT * FROM child_thread WHERE p_thread = ? AND ? <= cid";
		String query;
		ArrayList<ChildThread> result = new ArrayList<ChildThread>();
		
		if (pid < 0 || start < 0 || end < 0 || start > end || limit < 0) {
			throw new DatabaseException("Invalid parameters.");
		}
		
		Connection con = Database.getConnection();
		if (end == 0) {
			query = query2;
		} else {
			query = query1;
		}
		if (limit != 0) {
			query += " FETCH FIRST " + String.valueOf(limit) + " ROWS ONLY";
		}
		try {
			PreparedStatement st = con.prepareStatement(query);
			st.setInt(1, pid);
			if (end == 0) {
				st.setInt(2, start);
			} else {
				st.setInt(2, start);
				st.setInt(3, end);
			}
			ResultSet rs = st.executeQuery();
			while (rs.next()) {
				ChildThread temp = new ChildThread(rs.getInt("cid"), rs.getString("message")
						, new User(rs.getInt("created_by")), rs.getTimestamp("timestamp") 
						, rs.getInt("vote"));
				result.add(temp);
			}
			rs.close();
			st.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DatabaseException("Fail to get child thread, " + e.getMessage());
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
				throw new DatabaseException("Fail to close DB connection: " + e);
			}
		}
		if (result.size() == 0) {
			return null;
		} else {
			return result.toArray(new ChildThread[0]);
		}
	}
	
	/**
	 * Retrieve child threads having a specific parent thread.
	 * Overload method exists.
	 * 
	 * @param pid	int, id of a parent thread.
	 * @return	Array of ChildThread instances.
	 * @throws DatabaseException	if SQLException or invalid parameters.
	 */
	public ChildThread[] getChildThreads(int pid) throws DatabaseException {
		return this.getChildThreads(pid, 0, 0, 0);
	}
	
	/* Updates */
	/**
	 * Insert new parent thread
	 * 
	 * @param topic	String, topic
	 * @param message	String, message
	 * @param createdBy	int, user or player id who creates it
	 * @param category	String, category which thread is in that is specified in this.category
	 * @param tags	tags, tags that are attached to this thread
	 * @return	int, pid of new parent thread, -1 if failed.
	 * @throws DatabaseException
	 */
	public int insertParentThread(String topic, String message
			, int createdBy, String category, String tags) throws DatabaseException {
		int result = -1;
		
		String insertParentThread = "INSERT INTO parent_thread"
				+ " (topic, message, created_by, timestamp, category, tags)"
				+ " VALUES(?, ?, ?, ?, ?, ?)";
		
		/* Check parameters */
		if (topic == "" || topic.length() > 30) {
			return result;
		} else if (message == "") {
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
			st.setString(2, message);
			st.setInt(3, createdBy);
			st.setTimestamp(4, this.getNow());
			st.setString(5, category);
			st.setString(6, tags);
			/* Execute */
			st.executeUpdate();
			con.commit();
			st.close();
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			} catch (SQLException er) {
				er.printStackTrace();
				throw new DatabaseException("Fail to rollback: ", er);
			}
			throw new DatabaseException("InsertError: " + e);
		} finally {
			try {
				con.setAutoCommit(true);
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
				throw new DatabaseException("ExitError: " + e);
			}
		}
		return result;
	}
	
	/**
	 * Update (modify) parent thread which has the given pid. 
	 * It is noted that pid is immutable.
	 * 
	 * @param pid	int, parent thread id which is subject to change.
	 * @param newTopic	String, new topic. If empty, no change.
	 * @param newMessage	String, new message. If empty, no change.
	 * @param newCategory	String, new category. If empty, no change.
	 * @param newTags	String, new tags. If empty, no change.
	 * @throws DatabaseException	If SQLException or parameter error.
	 */
	public void updateParentThread(int pid, String newTopic, String newMessage, String newCategory, String newTags)
			throws DatabaseException {
		String query = "SELECT topic, message, category, tags FROM parent_thread WHERE pid = ?";
		String update = "UPDATE parent_thread SET topic = ?, message = ?, category = ?, tags = ? WHERE pid = ? ";
		/* Check parameters */
		if (pid < 0) {
			throw new DatabaseException("Parent thread id must be non-negative int");
		}
		if (newTopic == "" && newMessage == "" && newCategory == "" && newTags == "") {
			throw new DatabaseException("Invalid parameter set.");
		}
		/* Retrieve data and update */
		Connection con = Database.getConnection();
		try {
			PreparedStatement st = con.prepareStatement(query);
			st.setInt(1, pid);
			ResultSet rs = st.executeQuery();
			if (rs.next()) {
				PreparedStatement st2 = con.prepareStatement(update);
				/* Set new value if not null */
				if (newTopic != "") {
					st2.setString(1, newTopic);
				} else {
					/* Otherwise override with the same value */
					st2.setString(1, rs.getString("topic"));
				}
				if (newMessage != "") {
					st2.setString(2, newMessage);
				} else {
					st2.setString(2, rs.getString("message"));
				}
				if (newCategory != "" && this.inCategoryList(newCategory)) {
					st2.setString(3, newCategory);
				} else {
					st2.setString(3, rs.getString("category"));
				}
				if (newTags != "") {
					st2.setString(4, newTags);
				} else {
					st2.setString(4, rs.getString("tags"));
				}
				st2.setInt(5, pid);
				st2.executeUpdate();
				st.close();
			} else {
				throw new DatabaseException("Parent thread is not found");
			} 
			rs.close();
			st.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DatabaseException(e);
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
				throw new DatabaseException("Failed to close connection");
			}
		}
		
	}
	
	/**
	 * Update child thread record having a specific cid.
	 * 
	 * @param cid	id of child thread to be modified
	 * @param newMessage	new message of child thread.
	 * @throws DatabaseException
	 */
	public void updateChildThread(int cid, String newMessage) throws DatabaseException {
		String update = "UPDATE parent_thread SET message = ? WHERE cid = ?";
		if (newMessage == "" || cid < 0) {
			throw new DatabaseException("Invalid parameter.");
		}
		Connection con = Database.getConnection();
		try {
			PreparedStatement st = con.prepareStatement(update);
			st.setString(1, newMessage);
			st.setInt(2, cid);
			st.executeUpdate();
			st.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DatabaseException("Fail to update due to: " + e.getMessage());
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
				throw new DatabaseException("Fail to close connection due to: " + e.getMessage()); 
			}
		}
		return;
	}
	
	/**
	 * Delete parent thread of given pid.
	 * DELETE ON CASCADE is applied for referencing child_thread.
	 * @param pid	integer, parent thread id to be deleted.
	 * @throws DatabaseException	if SQLException.
	 */
	public void deleteParentThread(int pid) throws DatabaseException {
		String deleteParentThread = "DELETE FROM parent_thread WHERE pid=?";
		
		/* Check parameter */
		if (pid < 0) {
			return;
		}

		Connection con = Database.getConnection();
		/* Delete parent thread having pid */
		try {
			PreparedStatement st = con.prepareStatement(deleteParentThread);
			st.setInt(1, pid);
			st.executeUpdate();
			st.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DatabaseException("Fail to delete parent thread: " + e);
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
				throw new DatabaseException("Fail to close DB connection: " + e);
			}
		}
	}
	
	/**
	 * Insert a new child thread. pThread(id of parent thread) must be one of existing parent threads.
	 * 
	 * @param message	string, message of child thread. It must not be empty.
	 * @param createdBy	int, user's id of thread. It must be non-negative.
	 * @param pThread	int, referencing id for parent thread. 
	 * @throws DatabaseException	if SQLException or invalid parameter.
	 */
	public void insertChildThread(String message, int createdBy, int pThread) throws DatabaseException {
		String update = "INSERT INTO child_thread (message, created_by, timestamp, p_thread)"
				+ " VALUES(?, ?, ?, ?)";
		if (message == "" || createdBy < 0 || pThread < 0) {
			throw new DatabaseException("Message should not be empty or createdBy and pThread should not be negative integer.");
		}
		Connection con = Database.getConnection();
		try {
			PreparedStatement st = con.prepareStatement(update);
			st.setString(1, message);
			st.setInt(2, createdBy);
			st.setTimestamp(3, this.getNow());
			st.setInt(4, pThread);
			st.executeUpdate();
			st.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DatabaseException("Fail to insert new child_thread, " + e.getMessage());
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
				throw new DatabaseException("Fail to close connection, " + e.getMessage());
			}
		}
	}
	
	/**
	 * Delete child thread having a specific id.
	 * 
	 * @param cid	int, id of child thread to be deleted.
	 * @throws DatabaseException	if SQLException or invalid parameter
	 */
	public void deleteChildThread(int cid) throws DatabaseException {
		String update = "DELETE FROM child_thread WHERE cid = ?";
		Connection con = Database.getConnection();
		try {
			PreparedStatement st = con.prepareStatement(update);
			st.setInt(1, cid);
			st.executeUpdate();
			st.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DatabaseException("Fail to delete child_thread, " + e.getMessage());
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
				throw new DatabaseException("Fail to close connection, " + e.getMessage());
			}
		}
	}
	
	/**
	 * Increase/decrease like value of threads. 
	 * 
	 * @param value	value to be changed on like (negative is allowed)
	 * @param threadType	If "parent", change parent_thread's like. If "child"
	 * 						, change the child_thread's like.
	 * @param id	id of threads (non-negative)
	 * @exception DatabaseException
	 */
	public void addVote(int value, String threadType, int id) throws DatabaseException {
		String query;
		String update;
		int vote;
		if (id < 0) {
			throw new DatabaseException("Invalid id for addVote().");
		}
		if (threadType == "parent") {
			query = "SELECT vote FROM parent_thread WHERE pid = ?";
			update = "UPDATE parent_thread SET vote = ? WHERE pid = ?";
		} else if (threadType == "child") {
			query = "SELECT vote FROM child_thread WHERE cid = ?";
			update = "UPDATE child_thread SET vote = ? WHERE cid = ?";
		} else {
			throw new DatabaseException("Invalid threadType for addVote().");
		}
		Connection con = Database.getConnection();
		try {
			PreparedStatement st = con.prepareStatement(query);
			st.setInt(1, id);
			ResultSet rs = st.executeQuery();
			if (rs.next()) {
				vote = rs.getInt("vote");
				rs.close();
				st.close();
				st = con.prepareStatement(update);
				vote += value;
				st.setInt(1, vote);
				st.setInt(2, id);
				st.execute();
			} else {
				rs.close();
				st.close();
				throw new DatabaseException("Thread does not exist");
			}
			rs.close();
			st.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DatabaseException(e.getMessage());
		}
	}
	
		/* Private methods */
/* Private methods */
	private String getCategoryConstraint() {
		String result = "ALTER TABLE parent_thread ADD CHECK (category IN (";
		for (int i = 0; i < this.category.length; i++) {
			result += "'" + this.category[i] + "'";
			if (i != this.category.length-1) {
				result += ", ";
			}
		}
		result += "))";
		return result;
	}
	
	private String getTagsString(String[] tags) {
		StringBuilder result = new StringBuilder();
		for (int i = 0; i < tags.length; i++) {
			result += tags[i];
			result += "#";
		return result;
	}
	
	private String[] getTagsArray(String tags) {
		return tags.split(TAG_SPLITTER);
	}
	
	private Timestamp getNow() {
		Date d = new Date();
		return new Timestamp(d.getTime());
	}
	
	private boolean inCategoryList(String category) {
		for (int i = 0; i < this.category.length; i++) {
			if (category.equals(this.category[i])) {
				return true;
			}
		}
		return false;
	}
}
