package deco2800.arcade.server.database;

import static org.junit.Assert.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;

import org.dbunit.DBTestCase;
import org.dbunit.IDatabaseTester;
import org.dbunit.JdbcBasedDBTestCase;
import org.dbunit.JdbcDatabaseTester;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.DefaultTable;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.dataset.xml.XmlDataSet;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import deco2800.server.database.CreditStorage;
import deco2800.server.database.DatabaseException;

public class TestCreditStorage {

	private static IDatabaseTester databaseTester;
	private CreditStorage creditStorage;

	@BeforeClass
	public static void setUpClass() throws Exception {
		databaseTester = new JdbcDatabaseTester(
                "org.apache.derby.jdbc.EmbeddedDriver",
                "jdbc:derby:Arcade;user=server;password=server;create=true");
	}

	private IDataSet getDataSet() throws DataSetException, IOException {
		URL url = TestCreditStorage.class.getClassLoader().getResource("TestCreditStorage.xml");
		FlatXmlDataSetBuilder builder = new FlatXmlDataSetBuilder();
		builder.setColumnSensing(true);
		return builder.build(url);
	}
	
	@Before
	public void setUp() throws Exception {
		creditStorage = new CreditStorage();
		IDataSet ds = getDataSet();
        databaseTester.setDataSet(ds);
		databaseTester.onSetup();
	}
	
	@After
	public void  tearDown() throws Exception {
		databaseTester.onTearDown();
	}
	
	@Test
	public void initialTotal() throws DatabaseException {
		assertEquals(0, (int) creditStorage.getUserCredits("Bob"));
	}
	
	@Test
	public void basecase() throws DatabaseException {
		creditStorage.addUserCredits("Bob", 5);
		assertEquals(new Integer(5), creditStorage.getUserCredits("Bob"));
	}
	
}
