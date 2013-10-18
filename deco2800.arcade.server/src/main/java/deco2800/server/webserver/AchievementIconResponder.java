package deco2800.server.webserver;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;

import org.simpleframework.http.Response;

import deco2800.server.ArcadeServer;
import deco2800.server.database.DatabaseException;

public class AchievementIconResponder implements WebResponder {

    public AchievementIconResponder() {
		super();
    }

    @Override
    public void respond(Response response, String param) throws IOException, DatabaseException {
        
        ArcadeWebserver.setResponseValues(response, "image/png");
        
        /*
         * Attempt to load the requested image from the ImageStorage database, where '/' were 
         * modified to '-' for transmission as '/' has significance as a route in this context.
         * If the image is not found in the database, the default image will be loaded and returned.
         */
        try {
            byte[] data = ArcadeServer.instance().getImageStorage().get( param.replace( "-", "/" ) ).getData();
            
            OutputStream out = response.getOutputStream( data.length );
            
            out.write( data );
            out.close();
        
        } catch (Exception e) {
        	//TODO: Load the default image from ImageStorage.get() when they push to master
            ByteBuffer file = FileReader.readBinaryFile("../deco2800.arcade.ui/src/main/resources/logos/" + "default.png");

            OutputStream out = response.getOutputStream(file.capacity());
            
            out.write(file.array());
            out.close();
        }

    }

}
