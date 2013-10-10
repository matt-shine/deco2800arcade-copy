package deco2800.server.listener;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import deco2800.arcade.protocol.image.*;
import deco2800.server.ArcadeServer;
import deco2800.server.database.DatabaseException;
import deco2800.server.database.ImageStorage;
import deco2800.arcade.model.EncodedImage;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public class ImageListener extends Listener {

    @Override
    public void received(Connection conn, Object obj) {
        super.received(conn, obj);
        
        ImageStorage imgStorage = ArcadeServer.instance().getImageStorage();

        if (obj instanceof GetImageRequest) {
            GetImageRequest req = (GetImageRequest)obj;
            GetImageResponse resp = new GetImageResponse();
            resp.imageID = req.imageID;
            
            try {
                EncodedImage img = imgStorage.get(req.imageID);
                resp.data = img.getData();
            } catch (DatabaseException e) {
                resp.data = null;
            }
            
            conn.sendTCP(resp);
        } else if (obj instanceof SetImageRequest) {
            SetImageRequest req = (SetImageRequest)obj;
            SetImageResponse resp = new SetImageResponse();
            resp.imageID = req.imageID;
            
            try {
                imgStorage.set(req.imageID, new EncodedImage(req.data));
                resp.success = true;
            } catch (DatabaseException e) {
                resp.success = false;
            }
            
	    conn.sendTCP(resp);
        }

    }

}

