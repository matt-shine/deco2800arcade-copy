package deco2800.arcade.server.database;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.dbunit.IDatabaseTester;
import org.dbunit.JdbcDatabaseTester;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import deco2800.server.database.DatabaseException;
import deco2800.server.database.PlayerPrivacy;

public class TestPlayerPrivacy2 {
	private PlayerPrivacy playerPrivacy;
	
	//@Before
	/**
	 * Drop all tables from database before doing anything else.
	 */
	public void setUp() throws DatabaseException {
		playerPrivacy = new PlayerPrivacy();
		playerPrivacy.initialise();
		playerPrivacy.dropTables();
	}
	
	//@Test
	public void basicTest() throws DatabaseException {
		
	}
}
