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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import deco2800.server.ResourceLoader;

public class ImageStorage {
    
    // max image size of 1M

    // reserved image ids that are guaranteed to be accessible
    /**
     * An identifier used to represent the "unknown image". This is a reserved identifier
     * that always exists, and whose image cannot be modified.
     */
    public static final String UNKNOWN_IMAGE_ID = "ImageStorage.reserved.UNKNOWN_IMAGE";

    /**
     * An identifier used to represent a generic avatar. This is a reserved identifier
     * that always exists, and whose image cannot be modified.
     */
    public static final String GENERIC_AVATAR_ID = "ImageStorage.reserved.GENERIC_AVATER";
    
    /**
     * Get a logger for this class
     */
    private static Logger logger = LoggerFactory.getLogger(ImageStorage.class);

    // private toggle to allow us to initially load in reserved IDs
    private boolean allowSettingReservedIDs = false;

    public void initialise() throws DatabaseException {
    	logger.debug("ImageStorage about to be initialised");
        Connection connection = Database.getConnection();
        Statement statement = null;
        ResultSet tableData = null;
        try {
            tableData = connection.getMetaData().getTables(null, null,
					"IMAGES", null);
	    if (!tableData.next()){
	    logger.info("Images table not in database, creating now");
		statement = connection.createStatement();
		
		statement.execute("CREATE TABLE IMAGES(id VARCHAR(255) PRIMARY KEY," +
				  "data BLOB(1M) NOT NULL)");
		logger.info("Images table created.");
	    }
	    
	    // load in our reserved ids
	    try {
		allowSettingReservedIDs = true;
		set(UNKNOWN_IMAGE_ID, ResourceLoader.load("reservedImages/unknown.png"));
		set(GENERIC_AVATAR_ID, ResourceLoader.load("reservedImages/avatar.png"));		
	    } catch (IOException e) {
	    	logger.error("Could not load in reserved images.");
	    	throw new DatabaseException("Couldn't load in reserved image", e);
	    } finally {
		allowSettingReservedIDs = false;
	    }
		
	} catch(SQLException e) {
            e.printStackTrace();
            logger.error("Could not create images table.");
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
     * ID is replaced. If the image's id starts with "ImageStorage.reserved.", a
     * DatabaseException will be thrown.
     * 
     * @param id    The id of the image
     * @param image The image to store
     */
    public void set(String id, EncodedImage image) throws DatabaseException {
    logger.debug("Attempting to associate id: {} with an image", id);
	if (!allowSettingReservedIDs && id.startsWith("ImageStorage.reserved."))
	    throw new DatabaseException("Attempted to set an image with a reserved ID");

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
     *
     *
     */
    public void set(String imageID, File imageFile) 
	throws DatabaseException, FileNotFoundException, IOException {
	if (!imageFile.isFile()){
		logger.error("Attempted to set image ID: {} but could not load file", imageID);
	    throw new FileNotFoundException();
	}
	set(imageID, new EncodedImage(imageFile));
    }    

    /**
     * Returns the image associated with the given id, or the image associated with
     * UNKNOWN_IMAGE_ID as a fallback.
     *
     *
     */
    public EncodedImage get(String id) throws DatabaseException {
    	logger.debug("Associating image ID: {} with default unknown image file", id);
        Connection conn = Database.getConnection();
        PreparedStatement ps = null;        

        try {
            ps = conn.prepareStatement(
                "SELECT data FROM IMAGES WHERE id = ?");
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();

            if (!rs.next()) {
		if (!id.equals(UNKNOWN_IMAGE_ID))
		    return get(UNKNOWN_IMAGE_ID);
		else
		    return null;
            }

            // there was something in the set, read it
            Blob dataBlob = rs.getBlob(1);
            return new EncodedImage(dataBlob.getBinaryStream(), dataBlob.length());
        } catch(SQLException e) {
        	logger.error("Attempting to associate image ID: {} with default image failed");
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

    /**
     * Attempts to get the image with a given id. If there's no such image in the
     * storage, the image with a fallback id is retrieved instead. If that image
     * couldn't be retrieved either, the image with id UNKNOWN_IMAGE_ID is returned.
     *
     * @param id       The id of the image to get.
     * @param fallback The id of the image to get as a fallback.
     * @return Either the image associated with id, fallback, or as a last resort
     *         UNKNOWN_IMAGE_ID.
     */
    public EncodedImage get(String id, String fallback) throws DatabaseException {
	EncodedImage img = null;

	if (contains(id))
	    img = get(id);
	if (img == null)
		logger.info("Failed to get requested image (with ID: {}), attempting to get fallback");
	    img = get(fallback);
	if (img == null)
		logger.info("Failed to get fallback image (with ID: {}), returning default unknown image.");
	    img = get(UNKNOWN_IMAGE_ID);

	return img;
    }

    /**
     * Returns whether the database contains an image with a given id.
     *
     * @param id The id to test.
     * @return Whether the database contains an image with that id.
     */
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
        	logger.error("Attempted to check if database contains an image but failed");
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
