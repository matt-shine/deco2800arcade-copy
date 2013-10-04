package deco2800.arcade.server.database;

import java.io.IOException;
import java.net.URL;

import org.dbunit.IDatabaseTester;
import org.dbunit.JdbcDatabaseTester;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;

import org.junit.BeforeClass;
import static org.junit.Assert.assertEquals;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import deco2800.server.database.CreditStorage;
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
		System.out.println("TestForumStorage starts");
		tester = new JdbcDatabaseTester(
                "org.apache.derby.jdbc.EmbeddedDriver",
                "jdbc:derby:Arcade;user=server;password=server;create=true");
	}
	
	@Before
	public void setUp() throws Exception {
		this.forumStorage = new ForumStorage();
		this.forumStorage.initialise();
		IDataSet ds = this.getDataSet();
		tester.setDataSet(ds);
		tester.onSetup();
		this.forumStorage.resetTables();
		this.forumStorage.insertParentThread("Test topic 1", "Test content this is.", 1, "General Admin", "tag1#tag2");
		this.forumStorage.insertParentThread("Test topic 2", "Test content this is.", 1, "General Admin", "tag3#tag4");
		this.forumStorage.insertParentThread("Test topic 3", "Test content this is.", 1, "General Admin", "tag3#tag5");
		this.forumStorage.insertParentThread("Test topic 4", "Test content this is.", 1, "General Admin", "tag6#tag7");
	}
	
	@After
	public void  tearDown() throws Exception {
		tester.onTearDown();
	}
	
	@Test
	public void getParentThreadsTest() throws Exception {
		ParentThread[] threads = this.forumStorage.getParentThreads(0, 0, 0);
		for (ParentThread thread : threads) {
			System.out.println(thread.toString());
		}
		assertEquals("Test topic 1", forumStorage.getParentThread(1).getTopic());
		assertEquals("Test topic 2", this.forumStorage.getParentThread(2).getTopic());
	}
	
	@Test
	public void getTaggedParentThreadsTest() throws Exception {
		ParentThread[] result = this.forumStorage.getTaggedParentThreads("tag3");
		assertEquals(2, result[0].getId());
		assertEquals(3, result[1].getId());
	}
	
	@Test
	public void deleteParentThreadTest() throws Exception {
		this.forumStorage.deleteParentThread(5);
		assertEquals(null, this.forumStorage.getParentThread(5));
	}
	
	@Test 
	public void updateParentThreadTest() throws Exception {
		this.forumStorage.updateParentThread(1, "This is new topic", "", "Game Bug", "");
		assertEquals("This is new topic", this.forumStorage.getParentThread(1).getTopic());
		assertEquals("Test content this is.", this.forumStorage.getParentThread(1).getMessage());
		assertEquals("Game Bug", this.forumStorage.getParentThread(1).getCategory());
	}
	
	@Test
	public void getAllTagsTest() throws Exception {
		System.out.println("List of tags: ");
		String tags = "";
		for (String tag : this.forumStorage.getAllTags(0)) {
			tags += tag;
			tags += "#";
		}
		System.out.println("Tags: " + tags);
	}
	
	@Test
	public void addVoteTest() throws Exception {
		this.forumStorage.addVote(1, "parent", 1);
		assertEquals(1, this.forumStorage.getParentThread(1).getVote());
		this.forumStorage.addVote(-1, "parent", 1);
		assertEquals(0, this.forumStorage.getParentThread(1).getVote());
	}
	
	private IDataSet getDataSet() throws DataSetException, IOException {
		URL url = TestCreditStorage.class.getClassLoader().getResource("TestCreditStorage.xml");
		FlatXmlDataSetBuilder builder = new FlatXmlDataSetBuilder();
		builder.setColumnSensing(true);
		return builder.build(url);
	}
}
