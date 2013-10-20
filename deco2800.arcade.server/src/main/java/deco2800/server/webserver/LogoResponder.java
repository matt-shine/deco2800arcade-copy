package deco2800.server.webserver;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;

import org.simpleframework.http.Response;

public class LogoResponder implements WebResponder {

    public LogoResponder() {
		super();
    }

    @Override
    public void respond(Response response, String param) throws IOException {
        
    	// Specify that we will be returning a png image
        ArcadeWebserver.setResponseValues(response, "image/png");
        
        //Match the storage convention
        String logoName = param.toLowerCase().replace("\\s", "");
        
        /*
         * Attempt to serve the image file from the logo resources folder, 
         * serving the default image if this fails.
         */
        try
        {
            ByteBuffer file = FileReader.readBinaryFile("../deco2800.arcade.ui/src/main/resources/logos/" + logoName);

            OutputStream out = response.getOutputStream(file.capacity());
            
            out.write(file.array());
            out.close();
        
        } catch (Exception e)
        {
            ByteBuffer file = FileReader.readBinaryFile("../deco2800.arcade.ui/src/main/resources/logos/" + "default.png");

            OutputStream out = response.getOutputStream(file.capacity());
            
            out.write(file.array());
            out.close();
        }

    }

}
