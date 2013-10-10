package deco2800.arcade.client;

import deco2800.arcade.model.EncodedImage;
import deco2800.arcade.protocol.image.*;
import deco2800.arcade.protocol.NetworkObject;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import deco2800.arcade.utils.AsyncFuture;
import deco2800.arcade.utils.Handler;

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
	GetImageRequest req = new GetImageRequest();
        req.imageID = imageID;

	// make the request and set a handler for the response, which will
	// then provide our future with the image
	NetworkObject.request(connection, req).setHandler(new Handler<NetworkObject>() {
	    public void handle(NetworkObject obj) {
		GetImageResponse resp = (GetImageResponse)obj;
		
		if (resp.data == null)
		    future.cancel(false);
		else
		    future.provide(new EncodedImage(resp.data));
	    }
	});

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
	SetImageRequest req = new SetImageRequest();
        req.imageID = imageID;
        req.data = img.getData();

	NetworkObject.request(connection, req).setHandler(new Handler<NetworkObject>() {
	    public void handle(NetworkObject obj) {
		SetImageResponse resp = (SetImageResponse)obj;
		future.provide(resp.success);		
	    }
	});

        return future;
    }
}
