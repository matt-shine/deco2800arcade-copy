package deco2800.arcade.client.image;

import deco2800.arcade.model.EncodedImage;
import deco2800.arcade.protocol.image.*;
import deco2800.arcade.protocol.NetworkObject;
import deco2800.arcade.client.network.NetworkClient;
import deco2800.arcade.utils.AsyncFuture;
import deco2800.arcade.utils.Handler;
import java.util.concurrent.ConcurrentHashMap;
import deco2800.arcade.client.image.EncodedImageProvider;

/**
 * ImageClient deals with sending and recieving encoded images over the network.
 * For actually getting at the image data and using it with libGDX, see ImageManager.
 */
public class ImageClient implements EncodedImageProvider {

    private NetworkClient networkClient;
    private static ConcurrentHashMap<String, EncodedImage> imageCache = null;

    public ImageClient(NetworkClient networkClient) {
	if (imageCache == null)
	    imageCache = new ConcurrentHashMap<String, EncodedImage>();

	setNetworkClient(networkClient);
    }

    public void setNetworkClient(NetworkClient networkClient) {
	this.networkClient = networkClient;
    }

    /** 
     * Returns a future that will be provided with the EncodedImage associated with
     * the given imageID. The future will be cancelled if the image could not
     * be retrieved over this client's connection.
     * 
     * If useCache is true, then a cached version of this image will be used
     * if available, saving a network request. Note that the cache is static
     * and so will persist across multiple ImageClient lifetimes.
     */
    public AsyncFuture<EncodedImage> get(final String imageID, boolean useCache) {
        final AsyncFuture<EncodedImage> future = new AsyncFuture<EncodedImage>();
	
	if (useCache && imageCache.containsKey(imageID)) {
	    future.provide(imageCache.get(imageID));
	} else {
	    GetImageRequest req = new GetImageRequest();
	    req.imageID = imageID;
	    
	    // make the request and set a handler for the response, which will
	    // then provide our future with the image
	    networkClient.request(req).setHandler(new Handler<NetworkObject>() {
		public void handle(NetworkObject obj) {
		    System.out.println("Handling response");
		    GetImageResponse resp = (GetImageResponse)obj;
		    
		    if (resp.data == null)
			future.cancel(false);
		    else {
			EncodedImage img = new EncodedImage(resp.data);
			imageCache.put(imageID, img);
			future.provide(img);
		    }
		}
	    });
	}

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

	networkClient.request(req).setHandler(new Handler<NetworkObject>() {
	    public void handle(NetworkObject obj) {
		SetImageResponse resp = (SetImageResponse)obj;
		future.provide(resp.success);		
	    }
	});

        return future;
    }
}
