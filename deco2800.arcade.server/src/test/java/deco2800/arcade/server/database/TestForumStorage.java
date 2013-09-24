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
import deco2800.arcade.model.forum.ParentThread;

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
		System.out.println("TestForumStorage starts");
		this.forumStorage = new ForumStorage();
		this.forumStorage.initialise();
		this.forumStorage.insertParentThread("Test topic 1", "Test content this is.", 1, "General Admin", "tag1#tag2");
		this.forumStorage.insertParentThread("Test topic 2", "Test content this is.", 1, "General Admin", "tag3#tag4");
		this.forumStorage.insertParentThread("Test topic 3", "Test content this is.", 1, "General Admin", "tag3#tag5");
		this.forumStorage.insertParentThread("Test topic 4", "Test content this is.", 1, "General Admin", "tag6#tag7");
		String tags = "";
		for (String tag : this.forumStorage.getAllTags(0)) {
			tags += tag;
			tags += "#";
		}
		System.out.println("Tags: " + tags);
	}
	
	@Test
	public void selectTest() throws Exception {
		assertEquals("Test parent thread", forumStorage.getParentThread(1).getTopic());
		assertEquals("Test topic 1", forumStorage.getParentThread(2).getTopic());
		assertEquals("Test topic 2", this.forumStorage.getParentThread(3).getTopic());
	}
	
	@Test
	public void deleteTest() throws Exception {
		this.forumStorage.deleteParentThread(5);
		assertEquals(null, this.forumStorage.getParentThread(5));
	}
	
	@Test 
	public void updateTest() throws Exception {
		this.forumStorage.updateParentThread(2, "This is new topic", "", "Game Bug", "");
		assertEquals("This is new topic", this.forumStorage.getParentThread(2).getTopic());
		assertEquals("Test content this is.", this.forumStorage.getParentThread(2).getMessage());
		assertEquals("Game Bug", this.forumStorage.getParentThread(2).getCategory());
	}
	
	@Test
	public void getTaggedParentThreadsTest() throws Exception {
		ParentThread[] result = this.forumStorage.getTaggedParentThreads("tag3");
		assertEquals(3, result[0].getId());
		assertEquals(4, result[1].getId());
	}
}
