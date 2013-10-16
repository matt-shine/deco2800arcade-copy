package deco2800.server.webserver;

import java.io.OutputStream;
import java.nio.ByteBuffer;

import org.simpleframework.http.Response;

public class LogoResponder implements WebResponder {

    public LogoResponder() {
        // TODO Auto-generated constructor stub
    }

    @Override
    public void respond(Response response, String param) throws Exception {
        
        ArcadeWebserver.setResponseValues(response, "image/png");
        
        try
        {
            ByteBuffer file = FileReader.readBinaryFile("../deco2800.arcade.ui/src/main/resources/logos/" + param);

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
