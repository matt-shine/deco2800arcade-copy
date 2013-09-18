package deco2800.arcade.server.database;

import org.dbunit.IDatabaseTester;
import org.dbunit.JdbcDatabaseTester;

import org.junit.BeforeClass;
import static org.junit.Assert.assertEquals;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import deco2800.server.database.ForumStorage;
import deco2800.arcade.forum.ParentThread;

/**
 * Unit test for ForumStorage class
 * 
 * @author Junya
 * @see deco2800.arcade.server.database.ForumStorage
 */
public class TestForumStorage {
	private static IDatabaseTester tester;
	private ForumStorage forumStorage;
	
	/**
	 * This method is run once when this class is instantiated
	 * @throws Exception
	 */
	@BeforeClass
	public static void setUpClass() throws Exception {
		tester = new JdbcDatabaseTester(
                "org.apache.derby.jdbc.EmbeddedDriver",
                "jdbc:derby:Arcade;user=server;password=server;create=true");
	}
	
	@Before
	public void setUp() throws Exception {
		forumStorage = new ForumStorage();
		forumStorage.initialise();
		forumStorage.insertParentThread("Test topic 1", "Test content this is.", 1, "General Admin", "tag1;tag2");
		forumStorage.insertParentThread("Test topic 2", "Test content this is.", 1, "General Admin", "tag1;tag2");
		forumStorage.insertParentThread("Test topic 3", "Test content this is.", 1, "General Admin", "tag1;tag2");
		forumStorage.insertParentThread("Test topic 4", "Test content this is.", 1, "General Admin", "tag1;tag2");
	}
	
	@Test
	public void selectTest() throws Exception {
		assertEquals("Test topic 1", forumStorage.getParentThread(1).getTopic());
		assertEquals("Test topic 2", forumStorage.getParentThread(2).getTopic());
	}
	
	@Test
	public void selectAllTest() throws Exception {
		//assertEquals("Test topic 1", forumStorage.getAllParentThread()[0][1]);
	}
	
	@Test
	public void selectRangeTest() throws Exception {
		//assertEquals(4, forumStorage.getParentThreads(0, 0, 0).length);
		//assertEquals(2, forumStorage.getParentThreads(0, 3, 2).length);
		//assertEquals(3, forumStorage.getParentThreads(2, 4, 0).length);
		//assertEquals(2, forumStorage.getParentThreads(1, 4, 2).length);
	}
}
