package deco2800.arcade.server.database;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.InputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import org.dbunit.IDatabaseTester;
import org.dbunit.JdbcDatabaseTester;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.DefaultTable;
import org.dbunit.dataset.DefaultDataSet;
import org.dbunit.dataset.datatype.DataType;
import org.dbunit.dataset.Column;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import deco2800.arcade.model.EncodedImage;
import deco2800.server.database.DatabaseException;
import deco2800.server.database.ImageStorage;

public class TestImageStorage {

    private static IDatabaseTester databaseTester; //manage connections to the database
    private ImageStorage imageStorage; //storage object to test
	
    @BeforeClass
    public static void setUpClass() throws Exception {
	databaseTester = new JdbcDatabaseTester(
		"org.apache.derby.jdbc.EmbeddedDriver",
		"jdbc:derby:Arcade;user=server;password=server;create=true");
    }
	
    @Before
    public void setUp() throws Exception {        
        databaseTester.setDataSet(new DefaultDataSet());
	databaseTester.onSetup();
	imageStorage = new ImageStorage();       
	imageStorage.initialise();
    }
	
    @After
    public void  tearDown() throws Exception {
	databaseTester.onTearDown();
    }

    @Test
    public void testInsertRetrieve() throws Exception {
        URL url = TestImageStorage.class.getClassLoader().getResource("image.png");
        File f = new File(url.toURI());
        EncodedImage image = new EncodedImage(f);
        imageStorage.set("foo", image);
        
        EncodedImage retrievedImage = imageStorage.get("foo");
        int src = 0;
        int ret = 0;
        InputStream srcStream = image.getInputStream();
        InputStream retStream = retrievedImage.getInputStream();

        while (src != -1 && ret != -1) {
            src = srcStream.read();
            ret = retStream.read();

            assertEquals(src, ret);
        }

        assertEquals(src, ret);
    }

    @Test
    public void testGetFallback() throws Exception {
	EncodedImage unknownImage = imageStorage.get(ImageStorage.UNKNOWN_IMAGE_ID);
	EncodedImage retrievedImage = imageStorage.get("asdfghjkl;");
        int src = 0;
        int ret = 0;
        InputStream srcStream = unknownImage.getInputStream();
        InputStream retStream = retrievedImage.getInputStream();

        while (src != -1 && ret != -1) {
            src = srcStream.read();
            ret = retStream.read();

            assertEquals(src, ret);
        }

        assertEquals(src, ret);	
    }
}

