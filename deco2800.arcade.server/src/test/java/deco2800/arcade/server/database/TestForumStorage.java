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

import deco2800.server.database.ForumStorage;
import deco2800.arcade.model.forum.ChildThread;
import deco2800.arcade.model.forum.ParentThread;

/**
 * Unit test for ForumStorage class
 * 
 * @author Junya, Team Forum
 * @see deco2800.arcade.server.database.ForumStorage
 * @see deco2800.arcade.model.forum
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
		this.forumStorage.insertParentThread("Test topic 1", "Test content this is.", 1, "General_Discussion", "tag1#tag2");
		this.forumStorage.insertParentThread("Test topic 2", "Test content this is.", 1, "General_Discussion", "tag3#tag4");
		this.forumStorage.insertParentThread("Test topic 3", "Test content this is.", 2, "Tutorial", "tag3#tag5");
		this.forumStorage.insertParentThread("Test topic 4", "Test content this is.", 3, "Others", "tag6#tag7");
		this.forumStorage.insertChildThread("This is child thread 1.", 1, 1);
		this.forumStorage.insertChildThread("This is child thread 2.", 1, 1);
		this.forumStorage.insertChildThread("This is child thread 3.", 2, 1);
		this.forumStorage.insertChildThread("This is child thread 4.", 2, 1);
		this.forumStorage.insertChildThread("This is child thread 5.", 3, 2);
		this.forumStorage.insertChildThread("This is child thread 6.", 3, 2);
		this.forumStorage.setMaxPid();
	}
	
	@After
	public void  tearDown() throws Exception {
		tester.onTearDown();
	}
	
	/* Parent thread-related test cases */
	@Test
	public void getParentThreadsTest() throws Exception {
		ParentThread[] threads = this.forumStorage.getParentThreads(1000, 0, 0);
		for (ParentThread thread : threads) {
			System.out.println(thread.toString());
		}
	}
	
	@Test
	public void getParentThreadsTest2() throws Exception {
		ParentThread[] threads = this.forumStorage.getParentThreads(0, 0, 0);
		assertEquals("Test topic 1", forumStorage.getParentThread(1).getTopic());
		assertEquals("Test topic 2", this.forumStorage.getParentThread(2).getTopic());
		threads = this.forumStorage.getParentThreads(4, 2, 0);
		assertEquals(3, threads.length);
		threads = this.forumStorage.getParentThreads(5, 2, 1);
		assertEquals("Test topic 4", threads[0].getTopic());
		threads = this.forumStorage.getParentThreads(0, 0, 0, 1);
		for (ParentThread thread : threads) {
			assertEquals(1, thread.getCreatedBy().getId());
		}
		assertEquals(2, threads.length);
	}
	
	@Test
	public void getTaggedParentThreadsTest() throws Exception {
		ParentThread[] result = this.forumStorage.getTaggedParentThreads("tag3");
		assertEquals(3, result[0].getId());
		assertEquals(2, result[1].getId());
		result = this.forumStorage.getTaggedParentThreads("tag3", 1);
		for (ParentThread thread : result) {
			assertEquals(1, thread.getCreatedBy().getId());
		}
		assertEquals(2, result[0].getId());
	}
	
	@Test
	public void deleteParentThreadTest() throws Exception {
		this.forumStorage.deleteParentThread(5);
		assertEquals(null, this.forumStorage.getParentThread(5));
	}
	
	@Test 
	public void updateParentThreadTest() throws Exception {
		this.forumStorage.updateParentThread(1, "This is new topic", "", "Report_Bug", "");
		assertEquals("This is new topic", this.forumStorage.getParentThread(1).getTopic());
		assertEquals("Test content this is.", this.forumStorage.getParentThread(1).getMessage());
		assertEquals("Report_Bug", this.forumStorage.getParentThread(1).getCategory());
	}
	
	/* Child thread-related test cases */
	@Test
	public void getChildThreadTest() throws Exception {
		ChildThread[] threads = this.forumStorage.getChildThreads(1);
		for (ChildThread temp : threads) {
			System.out.println(temp.toString());
		}
	}
	
	@Test
	public void getChildThreadTest2() throws Exception {
		ChildThread[] threads = this.forumStorage.getChildThreads(1, 4, 1, 3);
		assertEquals(threads.length, 3);
		assertEquals(threads[0].getMessage(), "This is child thread 4.");
		threads = this.forumStorage.getChildThreads(1, 1);
		for (ChildThread thread : threads) {
			assertEquals(1, thread.getCreatedBy().getId());
		}
		assertEquals(2, threads.length);
	}
	
	@Test
	public void deleteChildThreadTest() throws Exception {
		this.forumStorage.deleteChildThread(1);
		this.forumStorage.deleteChildThread(2);
		assertEquals(this.forumStorage.getChildThreads(1).length, 2);
	}
	
	/* General test cases */
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
	
	@Test
	public void countVoteTest() throws Exception {
		this.forumStorage.addVote(1, "parent", 1);
		this.forumStorage.addVote(1, "parent", 1);
		this.forumStorage.addVote(1, "parent", 2);
		this.forumStorage.addVote(1, "child", 1);
		this.forumStorage.addVote(1, "child", 2);
		assertEquals(4, this.forumStorage.countParentThreadVotes(1));
		assertEquals(1, this.forumStorage.countParentThreadVotes(2));
	}
	
	private IDataSet getDataSet() throws DataSetException, IOException {
		URL url = TestCreditStorage.class.getClassLoader().getResource("TestCreditStorage.xml");
		FlatXmlDataSetBuilder builder = new FlatXmlDataSetBuilder();
		builder.setColumnSensing(true);
		return builder.build(url);
	}
}
