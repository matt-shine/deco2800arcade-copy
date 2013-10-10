package deco2800.server.database;

import deco2800.arcade.model.EncodedImage;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.Blob;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.File;
import java.io.FileNotFoundException;

public class ImageStorage {
    
    // max image size of 1M

    public void initialise() throws DatabaseException {
        Connection connection = Database.getConnection();
        Statement statement = null;
        ResultSet tableData = null;
        try {
            tableData = connection.getMetaData().getTables(null, null,
					"IMAGES", null);
			if (!tableData.next()){
				statement = connection.createStatement();
				statement.execute("CREATE TABLE IMAGES(id VARCHAR(255) PRIMARY KEY," +
						"data BLOB(1M) NOT NULL)");
			}
        } catch(SQLException e) {
            e.printStackTrace();
            throw new DatabaseException("Couldn't create images table", e);
        } finally {           
            try {
                if (statement != null) statement.close();
                tableData.close();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    /**
     * Associates an image with an id. The image can be retrieved from the database
     * with a call to get using the same id. Any previous image associated with this
     * ID is replaced.
     * 
     * @param id    The id of the image
     * @param image The image to store
     */
    public void set(String id, EncodedImage image) throws DatabaseException {      
        Connection conn = Database.getConnection();
        ByteArrayInputStream bis = image.getInputStream();
        PreparedStatement ps = null;

        try {
	    if (contains(id)) {
		ps = conn.prepareStatement("UPDATE IMAGES SET data = ? WHERE id = ?");
		ps.setString(2, id);
		ps.setBinaryStream(1, bis);
		ps.execute();

	    } else {
		ps = conn.prepareStatement("INSERT INTO IMAGES VALUES (?, ?)");		
		ps.setString(1, id);
		ps.setBinaryStream(2, bis);
		ps.execute();
	    }
	} catch(SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                ps.close();
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }         
    }

    /**
     * Helper method for taking an image from a file and associating it with an id
     * in the storage. This calls through to set(imageID, *the image in the file*).
     */
    public void set(String imageID, File imageFile) 
	throws DatabaseException, FileNotFoundException, IOException {
	if (!imageFile.isFile())
	    throw new FileNotFoundException();

	set(imageID, new EncodedImage(imageFile));
    }

    /**
     * Returns the image associated with the given id.
     */
    public EncodedImage get(String id) throws DatabaseException {
        Connection conn = Database.getConnection();
        PreparedStatement ps = null;        

        try {
            ps = conn.prepareStatement(
                "SELECT data FROM IMAGES WHERE id = ?");
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();

            if (!rs.next()) {
                return null; // nothing in the DB
            }

            // there was something in the set, read it
            Blob dataBlob = rs.getBlob(1);
            return new EncodedImage(dataBlob.getBinaryStream(), dataBlob.length());
        } catch(SQLException e) {
            e.printStackTrace();
            return null;
        } catch(IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                ps.close(); // closes rs as well
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean contains(String id) throws DatabaseException {
	Connection conn = Database.getConnection();
        PreparedStatement ps = null;        

        try {
            ps = conn.prepareStatement(
                "SELECT id FROM IMAGES WHERE id = ?");
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();

            if (!rs.next())
		return false;
	    else
		return true;

        } catch(SQLException e) {
            throw new DatabaseException("SQLException caught", e);            
        } finally {
            try {
                ps.close(); // closes rs as well
                conn.close();
            } catch (SQLException e) {
		throw new DatabaseException("Couldn't close connections", e);
            }
        }
    }
}
