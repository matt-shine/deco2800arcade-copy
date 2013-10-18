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
import deco2800.arcade.model.forum.ForumUser;
import deco2800.arcade.model.forum.ChildThread;
import deco2800.arcade.model.forum.ParentThread;

/**
 * This models the server storage for a forum into Java derby's DB 
 * with JDBC driver. <br>
 * Forum consists of parent_thread and child_thread tables, and 
 * child_thread's p_thread attribute references parent_thread's pid.
 * <p>
 * Features:
 * <ul>
 * 	<li>PreparedStatement for all query and update command.</li>
 * 	<li>One commit algorithm for every functions (methods). I.e. if a function
 * 	has multiple update commands (i.e. change DB states), it commits the change 
 * 	manually.</li>
 * 	<li>Forum uses the user information from PlayerStorage.</li>
 * 	<li>Most query results are ordered by PK on descending. So, threads 
 * 	are retrieved from latest to oldest (i.e. high id to low id).</li>
 * </ul>
 * 
 * @author Junya, Team Forum
 * @see java.sql
 * @see deco2800.arcade.model.forum.*
 * @see deco2800.arcade.protocol.forum.*
 */
public class ForumStorage {
	/* Fields */
	private boolean initialized = false;
	private static final String[] CATEGORY = {"General_Discussion", "Report_Bug", "Tutorial", "Others"};
	private static final String TAG_SPLITTER = "#";
	private int uid;
	/* maxPid is a highest pid that parent_thread table has.
	 * Increment this only if parent_thread SQL insert (Not for delete). And call
	 * setMaxPid() in initialise().
	 */
 	private int maxPid;
 	/* Similar to maxPid */
 	private int maxCid;
	
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
				+ ", category varchar(30) DEFAULT 'General_Discussion'"
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
				st.execute("ALTER TABLE parent_thread DROP CONSTRAINT CHK_CATEGORY");
				st.execute(this.getCategoryConstraint());
			}
			rs.close();
			rs = con.getMetaData().getTables(null, null, "CHILD_THREAD", null);
			if (!rs.next()) {
				st.execute(createChildThreadTable);
				st.execute(addFkPThread);
			}
			this.resetTables();
			rs.close();
			con.commit();
			st.close();
			this.insertParentThread("Test parent thread", "Very fist parent thread", 1, "Others", "Test#Parent thread");
			this.insertChildThread("Very first child thread.", 1, 1);
			this.insertThreadExamples();
			//this.printAllThreads();
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
		this.uid = 0;
		this.setMaxPid();
		this.setMaxCid();
	}
	
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
	public static void dropAllTables() throws DatabaseException {
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
		} catch (SQLException e) {
			e.printStackTrace();
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
				e.printStackTrace();
				throw new DatabaseException("Fail to close DB connectoin: " + e);
			}
		}
	}
	
	/**
	 * Set this.maxPid which will be used for SQL queries. It is useful for when 
	 * retrieving threads from highest to lowest (ORDER BY pid DESC).
	 * This should be called in initialize().
	 * 
	 * @throws DatabaseException	if SQLException or fail to set maxPid.
	 */
	public void setMaxPid() throws DatabaseException {
		Connection con = Database.getConnection();
		this.maxPid = 0;
		try {
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("SELECT MAX(pid) AS maxpid FROM parent_thread");
			if (rs.next()) {
				this.maxPid = rs.getInt("maxpid");
				rs.close();
				st.close();
				//System.out.println("MaxPid: " + this.maxPid);
			} else {
				throw new DatabaseException("Fail to set this.maxPid");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DatabaseException("Fail to retrieve maxPid, " + e.getMessage());
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
	 * Set this.maxCid based on child_thread table. Similar to setMaxPid().
	 * 
	 * @throws DatabaseException	if SQLException
	 */
	public void setMaxCid() throws DatabaseException {
		Connection con = Database.getConnection();
		this.maxCid = 0;
		try {
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("SELECT MAX(cid) AS maxcid FROM child_thread");
			if (rs.next()) {
				this.maxCid = rs.getInt("maxcid");
				rs.close();
				st.close();
				//System.out.println("MaxCid: " + this.maxCid);
			} else {
				throw new DatabaseException("Fail to set this.maxCid");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DatabaseException("Fail to retrieve maxCid, " + e.getMessage());
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
				throw new DatabaseException("Fail to close connection, " + e.getMessage());
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
			throw new DatabaseException("Invalid Parameter: limit must be non-negative int.");
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
			throw new DatabaseException("Invalid parameter: pid must be non-negative int");
		}
		
		Connection con = Database.getConnection();
		
		try {
			con.setAutoCommit(true);
			PreparedStatement st = con.prepareStatement(query);
			st.setInt(1, pid);
			ResultSet rs = st.executeQuery();
			if (rs.next()) {
				result = new ParentThread(rs.getInt("pid"), rs.getString("topic"), rs.getString("message")
						, new ForumUser(rs.getInt("created_by"), "no name"), rs.getTimestamp("timestamp"), rs.getString("category")
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
		String query = "SELECT * FROM parent_thread ORDER BY pid DESC";
		ArrayList<ParentThread> result = new ArrayList<ParentThread>();
		Connection con = Database.getConnection();
		if (tag == "") {
			throw new DatabaseException("Invalid parameter: tag must not be empty string");
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
								, new ForumUser(rs.getInt("created_by"), "no name"), rs.getTimestamp("timestamp"), rs.getString("category")
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
	 * Get parent threads having a specific tag and user id (created_by).
	 * Overload exists.
	 * 
	 * @param tag	String, tag attached to threads
	 * @param userId	int, user id who creates threads
	 * @return	ParentThread array.
	 * @throws DatabaseException	if invalid parameter and SQLException.
	 */
	public ParentThread[] getTaggedParentThreads(String tag, int userId) throws DatabaseException {
		String query = "SELECT * FROM parent_thread WHERE created_by = ? ORDER BY pid DESC";
		ArrayList<ParentThread> result = new ArrayList<ParentThread>();
		Connection con = Database.getConnection();
		if (tag == "") {
			return new ParentThread[0];
		}
		if (userId < 0) {
			throw new DatabaseException("Invalid parameter (negative int for id is given).");
		}
		try {
			PreparedStatement st = con.prepareStatement(query);
			st.setInt(1, userId);
			ResultSet rs = st.executeQuery();
			while (rs.next()) {
				String tags = rs.getString("tags");
				String[] tag_array = tags.split(TAG_SPLITTER);
				for (String temp : tag_array) {
					if (temp.equals(tag)) {
						result.add(new ParentThread(rs.getInt("pid"), rs.getString("topic"), rs.getString("message")
								, new ForumUser(rs.getInt("created_by"), "no name"), rs.getTimestamp("timestamp"), rs.getString("category")
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
	 * Return parent threads that have a specific tag, user id
	 * , id range and limit of records to be retrieved. <br>
	 * It is similar to getParentThreads(), but this specifies tag 
	 * and this is more costly.
	 * 
	 * @param tag	String, tag to be searched.
	 * @param start	int, starting id to be searched (i.e. highest id). 
	 * 				If 0, it will search from the highest id.
	 * @param end	int, ending id to be searched. If 0, no ending id.
	 * @param limit	int, limit to numbers of records to be retrieved. If 0,
	 * 				no limit. 
	 * @return	Array of ParentThread. Null if no results.
	 * @throws DatabaseException	if invalid parameter or SQLException. 
	 */
	public ParentThread[] getTaggedParentThreads(String tag
			, int start, int end, int limit) throws DatabaseException {
		String query1 = "SELECT * FROM parent_thread WHERE ? >= pid AND pid >= ?";
		String query2 = "SELECT * FROM parent_thread WHERE ? >= pid";
		String queryAddOn = " ORDER BY pid DESC";
		String query;
		ArrayList<ParentThread> result = new ArrayList<ParentThread>();
		
		/* Check params */
		if (start < 0 || end < 0 || limit < 0) {
			throw new DatabaseException("Invalid parameter.");
		}
		tag.trim();
		/* Get DB connection */
		Connection con = Database.getConnection();
		
		/* Begin query */
		if (start == 0) {
			/* Since result will be descending, set the highest pid for start */
			start = this.maxPid;
		}
		if (end == 0) {
			query = query2;
		} else {
			query = query1;
		}
		query += queryAddOn;
		int numRecords = 0;
		
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
				if (limit != 0 && numRecords == limit) {
					break;
				}
				/* Since tags contains multiple tag, every record is needed to check */
				String tags = rs.getString("tags");
				String[] tag_array = tags.split(TAG_SPLITTER);
				for (String temp : tag_array) {
					temp.trim();
					if (temp.equals(tag)) {
						result.add(new ParentThread(rs.getInt("pid"), rs.getString("topic"), rs.getString("message")
								, new ForumUser(rs.getInt("created_by"), "no name"), rs.getTimestamp("timestamp"), rs.getString("category")
								, rs.getString("tags"), rs.getInt("vote")));
						numRecords++;
					}
				}
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
			/* Convert ArrayList to ParentThread[] */
			return result.toArray(new ParentThread[0]);
		}
	}
	
	/**
	 * Return array of ParentThreads with specifying id range. See param specification for 
	 * how to set the id range. All params must be non-negative. It is noted that the retrieved result 
	 * will be ordered by descending (i.e. High id to low id).
	 * <p>
	 * Query to be executed =	SELECT * FROM parent_thread
	 * 							WHERE pid >= start [AND end >= pid]
	 * 							ORDER BY pid DESC
	 * 							[FETCH FIRST limit ROWS ONLY]
	 * 
	 * @param start	int, starting pid of parent thread to be retrieved, 
	 * 				if start = 0, it starts from last parent thread specified 
	 * 				by this.maxPid in table.
	 * 				(inclusive)
	 * @param end	int, ending pid of parent thread to be retrieved, 
	 * 				if end = 0, it does not specify the ending parent thread
	 * 				in table.
	 * 				(inclusive)
	 * @param limit	int, limit to number of parent threads to be returned, 
	 * 				if limit = 0 it does not specify the limit. 
	 * @return	ParentThread array, null if no result
	 * @throws	DatabaseException	if SQLException or invalid parameter.
	 */
	public ParentThread[] getParentThreads(int start, int end, int limit) throws DatabaseException {
		String query1 = "SELECT * FROM parent_thread WHERE ? >= pid AND pid >= ?";
		String query2 = "SELECT * FROM parent_thread WHERE ? >= pid";
		String queryAddOn = " ORDER BY pid DESC";
		String query;
		ArrayList<ParentThread> result = new ArrayList<ParentThread>();
		
		/* Check params */
		if (start < 0 || end < 0 || limit < 0) {
			throw new DatabaseException("Invalid parameter.");
		}
		
		/* Get DB connection */
		Connection con = Database.getConnection();
		
		/* Begin query */
		if (start == 0) {
			/* Since result will be descending, set the highest pid for start */
			start = this.maxPid;
		}
		if (end == 0) {
			query = query2;
		} else {
			query = query1;
		}
		query += queryAddOn;
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
						, rs.getString("message"), new ForumUser(rs.getInt("created_by"), "no name"), rs.getTimestamp("timestamp")
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
			/* Convert ArrayList to ParentThread[] */
			return result.toArray(new ParentThread[0]);
		}
	}
	
	/**
	 * Return array of ParentThreads with specifying thread's id range and user id who 
	 * created threads. This is extension of getParentThreads(int, int, int).
	 * <p>
	 * Query to be executed =	SELECT * FROM parent_thread
	 * 							WHERE created_by = userId AND pid >= start [AND end >= pid]
	 * 							ORDER BY pid DESC
	 * 							[FETCH FIRST limit ROWS ONLY]
	 * 
	 * @param start	int, starting pid of parent thread to be retrieved, 
	 * 				if start = 0, it starts from last parent thread in table.
	 * 				(inclusive)
	 * @param end	int, ending pid of parent thread to be retrieved, 
	 * 				if end = 0, it does not specify the ending parent thread
	 * 				in table.
	 * 				(inclusive)
	 * @param limit	int, limit to number of parent threads to be returned, 
	 * 				if limit = 0 it does not specify the limit. 
	 * @param userId	int, user id who creates threads.
	 * @return	ParentThread array, null if no result
	 * @throws	DatabaseException	if SQLException or invalid parameter.
	 */
	public ParentThread[] getParentThreads(int start, int end, int limit, int userId) throws DatabaseException {
		String query1 = "SELECT * FROM parent_thread WHERE created_by = ? AND ? >= pid AND pid >= ?";
		String query2 = "SELECT * FROM parent_thread WHERE created_by = ? AND ? >= pid";
		String queryAddOn = " ORDER BY pid DESC";
		String query;
		ArrayList<ParentThread> result = new ArrayList<ParentThread>();
		
		/* Check params */
		if (start < 0 || end < 0 || limit < 0 || start < end || userId < 0) {
			throw new DatabaseException("Invalid parameter.");
		}
		
		/* Get DB connection */
		Connection con = Database.getConnection();
		
		/* Begin query */
		if (start == 0) {
			/* Since result will be descending, set the highest pid for start */
			start = this.maxPid;
		}
		if (end == 0) {
			query = query2;
		} else {
			query = query1;
		}
		query += queryAddOn;
		if (limit != 0) {
			query += " FETCH FIRST " + String.valueOf(limit) + " ROWS ONLY";
		}
		try {
			/* Prepare st */
			PreparedStatement st = con.prepareStatement(query);
			st.setInt(1, userId);
			if (end == 0) {
				st.setInt(2, start);
			} else {
				st.setInt(2, start);
				st.setInt(3, end);
			}
			ResultSet rs = st.executeQuery();
			while (rs.next()) {
				ParentThread pThread = new ParentThread(rs.getInt("pid"), rs.getString("topic")
						, rs.getString("message"), new ForumUser(rs.getInt("created_by"), "no name"), rs.getTimestamp("timestamp")
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
	 * Return array of ParentThreads with specifying thread's id range and user id who 
	 * created threads. This is extension of getParentThreads(int, int, int).
	 * <p>
	 * Query to be executed =	SELECT * FROM parent_thread
	 * 							WHERE category = category AND pid >= start [AND end >= pid]
	 * 							ORDER BY pid DESC
	 * 							[FETCH FIRST limit ROWS ONLY]
	 * 
	 * @param start	int, starting pid of parent thread to be retrieved, 
	 * 				if start = 0, it starts from last parent thread in table.
	 * 				(inclusive)
	 * @param end	int, ending pid of parent thread to be retrieved, 
	 * 				if end = 0, it does not specify the ending parent thread
	 * 				in table.
	 * 				(inclusive)
	 * @param limit	int, limit to number of parent threads to be returned, 
	 * 				if limit = 0 it does not specify the limit. 
	 * @param category	string, category specified in parent thread 
	 * @return	ParentThread array, null if no result
	 * @throws	DatabaseException	if SQLException or invalid parameter.
	 */
	public ParentThread[] getParentThreads(int start, int end, int limit, String category) throws DatabaseException {
		String query1 = "SELECT * FROM parent_thread WHERE category = ? AND ? >= pid AND pid >= ?";
		String query2 = "SELECT * FROM parent_thread WHERE category = ? AND ? >= pid";
		String queryAddOn = " ORDER BY pid DESC";
		String query;
		ArrayList<ParentThread> result = new ArrayList<ParentThread>();
		
		/* Check params */
		if (start < 0 || end < 0 || limit < 0 || start < end || (!this.inCategoryList(category))) {
			throw new DatabaseException("Invalid parameter.");
		}
		
		/* Get DB connection */
		Connection con = Database.getConnection();
		
		/* Begin query */
		if (start == 0) {
			/* Since result will be descending, set the highest pid for start */
			start = this.maxPid;
		}
		if (end == 0) {
			query = query2;
		} else {
			query = query1;
		}
		query += queryAddOn;
		if (limit != 0) {
			query += " FETCH FIRST " + String.valueOf(limit) + " ROWS ONLY";
		}
		try {
			/* Prepare st */
			PreparedStatement st = con.prepareStatement(query);
			st.setString(1, category);
			if (end == 0) {
				st.setInt(2, start);
			} else {
				st.setInt(2, start);
				st.setInt(3, end);
			}
			ResultSet rs = st.executeQuery();
			while (rs.next()) {
				ParentThread pThread = new ParentThread(rs.getInt("pid"), rs.getString("topic")
						, rs.getString("message"), new ForumUser(rs.getInt("created_by"), "no name"), rs.getTimestamp("timestamp")
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
						, new ForumUser(rs.getInt("created_by"), "no name"), rs.getTimestamp("timestamp") 
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
	 * Return array of ChildThreads with specifying id range and having a specific parent thread. 
	 * See param specification for how to set the id range. All params must be non-negative.<br>
	 * The results will be ordered by cid descending.
	 * Overload method exists.
	 * <p>
	 * Query to be executed =	SELECT * FROM child_thread
	 * 							WHERE p_thread = pid AND cid >= start [AND end >= cid]
	 * 							ORDER BY cid DESC
	 * 							[FETCH FIRST limit ROWS ONLY]
	 * 
	 * @param pid	int, id of referencing parent thread
	 * @param start	int, starting cid to be retrieved. If 0, from last record.
	 * @param end	int, ending cid to be retrieved. If 0, no end.
	 * @param limit	int, number of records (child threads) to retrieve.
	 * @return	Array of ChildThread instances.
	 * @throws DatabaseException	if SQLException or invalid parameter.
	 */
	public ChildThread[] getChildThreads(int pid, int start, int end, int limit) throws DatabaseException {
		String query1 = "SELECT * FROM child_thread WHERE p_thread = ? AND ? >= cid AND cid >= ?";
		String query2 = "SELECT * FROM child_thread WHERE p_thread = ? AND ? >= cid";
		String queryAddOn = " ORDER BY cid DESC";
		String query;
		ArrayList<ChildThread> result = new ArrayList<ChildThread>();
		
		if (pid < 0 || start < 0 || end < 0 || limit < 0) {
			throw new DatabaseException("Invalid parameters.");
		}
		
		Connection con = Database.getConnection();
		if (start == 0) {
			start = this.maxCid;
		}
		if (end == 0) {
			query = query2;
		} else {
			query = query1;
		}
		query += queryAddOn;
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
						, new ForumUser(rs.getInt("created_by"), "no name"), rs.getTimestamp("timestamp") 
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
	 * Return array of ChildThreads with specifying id range and having a specific parent thread 
	 * that are made by a specific user. 
	 * See param specification for how to set the id range. All params must be non-negative.<br>
	 * The results will be ordered by cid descending.
	 * Overload method exists.
	 * <p>
	 * Query to be executed =	SELECT * FROM child_thread
	 * 							WHERE p_thread = pid AND cid >= start [AND end >= cid] AND created_by = ?
	 * 							ORDER BY cid DESC
	 * 							[FETCH FIRST limit ROWS ONLY]
	 * 
	 * @param pid	int, id of referencing parent thread
	 * @param start	int, starting cid to be retrieved. If 0, from last record.
	 * @param end	int, ending cid to be retrieved. If 0, no end.
	 * @param limit	int, number of records (child threads) to retrieve.
	 * @param usderId	int, id of a user who created threads.
	 * @return	Array of ChildThread instances.
	 * @throws DatabaseException	if SQLException or invalid parameter.
	 */
	public ChildThread[] getChildThreads(int pid, int start, int end, int limit, int userId) throws DatabaseException {
		String query1 = "SELECT * FROM child_thread WHERE p_thread = ? AND ? >= cid AND cid >= ? AND created_by = ?";
		String query2 = "SELECT * FROM child_thread WHERE p_thread = ? AND ? >= cid AND created_by = ?";
		String queryAddOn = " ORDER BY cid DESC";
		String query;
		ArrayList<ChildThread> result = new ArrayList<ChildThread>();
		
		if (pid < 0 || start < 0 || end < 0 || limit < 0 || userId < 0) {
			throw new DatabaseException("Invalid parameters.");
		}
		
		Connection con = Database.getConnection();
		if (start == 0) {
			start = this.maxCid;
		}
		if (end == 0) {
			query = query2;
		} else {
			query = query1;
		}
		query += queryAddOn;
		if (limit != 0) {
			query += " FETCH FIRST " + String.valueOf(limit) + " ROWS ONLY";
		}
		try {
			PreparedStatement st = con.prepareStatement(query);
			st.setInt(1, pid);
			if (end == 0) {
				st.setInt(2, start);
				st.setInt(3, userId);
			} else {
				st.setInt(2, start);
				st.setInt(3, end);
				st.setInt(4, userId);
			}
			ResultSet rs = st.executeQuery();
			while (rs.next()) {
				ChildThread temp = new ChildThread(rs.getInt("cid"), rs.getString("message")
						, new ForumUser(rs.getInt("created_by"), "no name"), rs.getTimestamp("timestamp") 
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
	
	/**
	 * Retrieve child threads having a specific parent thread.
	 * Overload method exists.
	 * 
	 * @param pid	int, id of a parent thread.
	 * @param userId	int, user id who creates threads.
	 * @return	Array of ChildThread instances.
	 * @throws DatabaseException	if SQLException or invalid parameters.
	 */
	public ChildThread[] getChildThreads(int pid, int userId) throws DatabaseException {
		return this.getChildThreads(pid, 0, 0, 0, userId);
	}
	
	/**
	 * Retrieve user information (extracted) from given id and return it as ForumUser
	 * 
	 * @param id	non-negative int, user's id.
	 * @return ForumUser, containing id and name. If no result, return null.
	 * @throws DatabaseException	if invalid parameter and SQLException.
	 */
	public ForumUser getForumUser(int id) throws DatabaseException {
		String query = "SELECT playerid, username FROM Player WHERE playerid = ?";
		ForumUser result;
		if (id < 0) {
			throw new DatabaseException("Invalid parameter (id was a negative integer).");
		}
		Connection con = Database.getConnection();
		try {
			PreparedStatement st = con.prepareStatement(query);
			st.setInt(1, id);
			ResultSet rs = st.executeQuery();
			if (rs.next()) {
				result = new ForumUser(id, rs.getString("username"));
			} else {
				result = null;
			}
			rs.close();
			st.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DatabaseException("Failed to get user information: " + e.getMessage());
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
				throw new DatabaseException("Fail to close the connection: " + e.getMessage());
			}
		}
		return result;
	}
	
	/**
	 * Partially implemented. getForumUser based on username.
	 * 
	 * @param username	String, username
	 * @return	ForumUser containing id and name of forum user.
	 */
	public ForumUser getForumUser(String username) {
		ForumUser result;
		result = new ForumUser(uid, username);
		uid++;
		return result;
	}
	
	/**
	 * Return total vote having a specific user id
	 * 
	 * @param userId	int, user's id
	 * @return int, total votes held by a specific user.
	 * @throws DatabaseException
	 */
	public int countUserVote(int userId) throws DatabaseException {
		String query = "SELECT SUM(vote) AS total FROM parent_thread WHERE created_by = ?";
		String query2 = "SELECT SUM(vote) AS total FROM child_thread WHERE created_by = ?";
		int result = 0;
		if (userId < 0) {
			throw new DatabaseException("Invalid parameter.");
		}
		Connection con = Database.getConnection();
		try {
			PreparedStatement st = con.prepareStatement(query);
			st.setInt(1, userId);
			ResultSet rs = st.executeQuery();
			while (rs.next()) {
				result += rs.getInt("total");
			}
			rs.close();
			st.close();
			st = con.prepareStatement(query2);
			st.setInt(1, userId);
			rs = st.executeQuery();
			while (rs.next()) {
				result += rs.getInt("total");
			}
			rs.close();
			st.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DatabaseException("Fail to get total votes: " + e.getMessage());
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
				throw new DatabaseException("Fail to close the connection: " + e.getMessage());
			}
		}
		return result;
	}
	
	/**
	 * Return total votes of a parent thread and its underlying child threads.
	 * 
	 * @param pid	int, parent thread id
	 * @return	int, total votes
	 * @throws DatabaseException	if invalid parameter or SQLException.
	 */
	public int countParentThreadVotes(int pid) throws DatabaseException {
		String query = "SELECT vote FROM parent_thread WHERE pid = ?";
		String query2 = "SELECT SUM(vote) AS total FROM child_thread WHERE p_thread = ?";
		int result = 0;
		if (pid < 0) {
			throw new DatabaseException("Invalid parameter.");
		}
		Connection con = Database.getConnection();
		try {
			PreparedStatement st = con.prepareStatement(query);
			st.setInt(1, pid);
			ResultSet rs = st.executeQuery();
			while (rs.next()) {
				result += rs.getInt("vote");
			}
			rs.close();
			st.close();
			st = con.prepareStatement(query2);
			st.setInt(1, pid);
			rs = st.executeQuery();
			while (rs.next()) {
				result += rs.getInt("total");
			}
			rs.close();
			st.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DatabaseException("Fail to get total votes: " + e.getMessage());
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
				throw new DatabaseException("Fail to close the connection: " + e.getMessage());
			}
		}
		return result;
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
		} else if (!this.inCategoryList(category)) {
			return result;
		}
		
		/* Create database connection */
		Connection con = Database.getConnection();
		
		/* Insert value */
		try {
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
			st.close();
			this.maxPid++;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DatabaseException("InsertError: " + e);
		} finally {
			try {
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
			this.maxCid++;
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
	 * Delete all threads having a specific user id.
	 * 
	 * @param userId	int, user id
	 * @throws DatabaseException	if SQLException.
	 */
	public void deleteThreads(int userId) throws DatabaseException {
		String update = "DELETE FROM parent_thread WHERE userId = ?";
		Connection con = Database.getConnection();
		try {
			PreparedStatement st = con.prepareStatement(update);
			st.setInt(1, userId);
			st.executeUpdate();
			st.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DatabaseException("Fail to delete threads created by " + Integer.toString(userId) + ", " + e.getMessage());
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
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
				throw new DatabaseException("Fail to close connection, " + e.getMessage());
			}
		}
	}
	
	/* Private methods */
	private String getCategoryConstraint() {
		String result = "ALTER TABLE parent_thread ADD CONSTRAINT chk_category CHECK (category IN (";
		for (int i = 0; i < CATEGORY.length; i++) {
			result += "'" + CATEGORY[i] + "'";
			if (i != CATEGORY.length-1) {
				result += ", ";
			}
		}
		result += "))";
		return result;
	}
	
	private String getTagsString(String[] tags) {
		StringBuilder result = new StringBuilder();
		for (int i = 0; i < tags.length; i++) {
			result.append(tags[i]);
			if (i != (tags.length - 1)) {
				result.append("#");
			}
		}
		return new String(result);
	}
	
	private String[] getTagsArray(String tags) {
		return tags.split(TAG_SPLITTER);
	}
	
	private Timestamp getNow() {
		Date d = new Date();
		return new Timestamp(d.getTime());
	}
	
	/**
	 * Check if category is in category list (this.category).
	 * @param category
	 * @return
	 */
	private boolean inCategoryList(String category) {
		for (int i = 0; i < CATEGORY.length; i++) {
			if (category.equals(CATEGORY[i])) {
				return true;
			}
		}
		return false;
	}
	
	private void insertThreadExamples() throws DatabaseException {
		int num = 0;
		for (int i = 0; i < 10; i++) {
			this.insertParentThread("Parent Thread test " + i, "Test message", 1, CATEGORY[num], "test");
			if (num != 3) {
				num++;
			} else {
				num = 0;
			}
		}
		ParentThread[] threads = this.getParentThreads(1000, 0, 0);
		if (threads != null) {
			for (ParentThread thread : threads) {
				this.insertChildThread("Child Thread test", 1, thread.getId());
			}
		}
	}
	
	/**
	 * Print all threads into standard output. Useful for debug.
	 * 
	 * @throws DatabaseException
	 */
	
	public void printAllThreads() throws DatabaseException {
		System.out.println("Print all threads: ");
		ParentThread[] threads = this.getParentThreads(1000, 0, 0);
		if (threads != null) {
			for (ParentThread thread : threads) {
				System.out.println("    " + thread.toString());
				System.out.println("    Print child threads: ");
				ChildThread[] threads2 = this.getChildThreads(thread.getId());
				if (threads2 != null) {
					for (ChildThread thread2 : threads2) {
						System.out.println("        " + thread2.toString());
					}
				}
			}
		}
		return;
	}
}
