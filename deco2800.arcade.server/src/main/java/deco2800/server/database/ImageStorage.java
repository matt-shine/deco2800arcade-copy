package deco2800.server.database;

import deco2800.arcade.model.EncodedImage;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.io.File;

public class ImageStorage {
    
    // max image size of 1M

    public void initialise() throws DatabaseException {
        Connection conn = Database.getConnection();
        try {
            ResultSet tableData = conn.getMetaData().getTables(null, null,
					"IMAGES", null);
			if (!tableData.next()){
				Statement statement = conn.createStatement();
				statement.execute("CREATE TABLE IMAGES(id VARCHAR(255) PRIMARY KEY," +
						"data BLOB(1M) NOT NULL)");
			}
        } catch(SQLException e) {
            e.printStackTrace();
            throw new DatabaseException("Couldn't create images table", e);
        }
    }

    /**
     * Associates the image stored in `imgFile` with `id`.
     * 
     * @param id The id to associate the image with
     * @param imgFile The file containing the image to store
     */
    public void set(String id, File imgFile) {
        // Read in the data and create an EncodedImage

        // use our set(String,EncodedImage) overload to do the DB work
    }
    
    /**
     * Associates an image with an id. The image can be retrieved from the database
     * with a call to get
     */
    public void set(String id, EncodedImage image) {

    }

    public EncodedImage get(String id) {
        return new EncodedImage();
    }

    public boolean contains(String id) {
        return false;
    }
}