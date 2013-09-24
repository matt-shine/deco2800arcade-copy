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
 * This models the server storage for a forum. It would be best to use
 * a transaction on every SQL-related function.
 * This implements the transaction based database operation (i.e. every
 * SQL execution is committed in the end of function.)
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
		String dropParentThreadTable = "DROP TABLE parent_thread";
		String createParentThreadTable = "CREATE TABLE parent_thread"
				+ " (pid integer PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1)" 
				+ ", topic varchar(30) NOT NULL"
				+ ", message long varchar NOT NULL"
				+ ", created_by integer NOT NULL"
				+ ", timestamp timestamp"
				+ ", category varchar(30) DEFAULT 'General Admin'"
				+ ", tags long varchar)";
		/* Create database connection */
		con = Database.getConnection();
		/* Create tables */
		try {
			Statement st = con.createStatement();
			con.setAutoCommit(false);
			st.execute(dropParentThreadTable);
			st.execute(createParentThreadTable);
			st.execute(this.getCategoryConstraint());
			con.commit();
			st.close();
			this.initialized = true;
		} catch (SQLException e) {
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
		this.insertParentThread("Test parent thread", "Very fist parent thread", 1, "General Admin", "Test#Parent thread");
	}
	
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
						, rs.getString("tags"));
			} else {
				result = null;
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
								, rs.getString("tags")));
					}
				}
			}
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
	 * Return array of strings that contains parent threads data retrieved
	 * from DB with specifying id range. See param specification for 
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
	 * 			category, tags}}, return null if no result or failed
	 * @throws	DatabaseException
	 */
	public String[][] getParentThreads(int start, int end, int limit) throws DatabaseException {
		String query1 = "SELECT * FROM parent_thread WHERE ? <= pid AND pid <= ?";
		String query2 = "SELECT * FROM parent_thread WHERE ? <= pid";
		String query;
		ArrayList<String[]> result = new ArrayList<String[]>();
		String[] strArray;
		
		/* Check params */
		if (start < 0 || end < 0 || limit < 0) {
			return null;
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
			query += "FETCH FIRST " + String.valueOf(limit) + " ROWS ONLY";
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
			String[][] result2 = new String[result.size()][7];
			result2 = result.toArray(result2);
			return result2;
		}
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
	
	/**
	 * Update (modify) parent thread which has the given pid. 
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
			} else {
				throw new DatabaseException("Parent thread is not found");
			} 
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
	 * Delete parent thread of given pid
	 * @param pid	integer, parent thread id to be deleted.
	 * @throws DatabaseException	if SQLException.
	 */
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
