package deco2800.arcade.client;

import deco2800.arcade.model.EncodedImage;
import deco2800.arcade.protocol.image.*;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import deco2800.arcade.util.AsyncFuture;

public class ImageClient {

    private Connection connection;

    public ImageClient(Connection connection) {
        this.connection = connection;
    }

    /** 
     * Returns a future that will be provided with the EncodedImage associated with
     * the given imageID. The future will be cancelled if the image could not
     * be retrieved over this client's connection.
     */
    public AsyncFuture<EncodedImage> get(final String imageID) {
        final AsyncFuture<EncodedImage> future = new AsyncFuture<EncodedImage>();
        
        // set up a listener for the response so we can provide the future
        // with the data once it comes in over the network
        connection.addListener(new Listener() {
                public void received(Connection conn, Object obj) {
                    if (obj instanceof GetImageResponse) {
                        GetImageResponse resp = (GetImageResponse)obj;
                        // not the right image, we don't care about it
                        if (!resp.imageID.equals(imageID))
                            return;

                        if (resp.data == null)
                            future.cancel(false);
                        else
                            future.provide(new EncodedImage(resp.data));

                        // don't need to listen for anything any more
                        connection.removeListener(this);
                    }
                }
        });

        // make the request
        GetImageRequest req = new GetImageRequest();
        req.imageID = imageID;
	connection.sendTCP(req);

        return future;
    }
    
    /**
     * Tells the server to associate the provided id with the given image.
     * The returned future can be used to track the success of this operation.
     * It's not safe to do a get() for the given imageID and have the newly
     * set image be returned until the future's get() method returns true.
     */
    public AsyncFuture<Boolean> set(final String imageID, EncodedImage img) {
        final AsyncFuture<Boolean> future = new AsyncFuture<Boolean>();

        // set up a listener for the response so we can provide the future
        // with the data once it comes in over the network
        connection.addListener(new Listener() {
                public void received(Connection conn, Object obj) {
                    if (obj instanceof SetImageResponse) {
                        SetImageResponse resp = (SetImageResponse)obj;
                        // not the right image, we don't care about it
                        if (!resp.imageID.equals(imageID))
                            return;

                        future.provide(new Boolean(resp.success));

                        // don't need to listen for anything any more
                        connection.removeListener(this);
                    }
                }
        });

        // make the request
        SetImageRequest req = new SetImageRequest();
        req.imageID = imageID;
        req.data = img.getData();
	connection.sendTCP(req);

        return future;
    }
}
