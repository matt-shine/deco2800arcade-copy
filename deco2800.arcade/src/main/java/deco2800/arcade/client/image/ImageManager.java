package deco2800.arcade.client.image;

import java.util.HashMap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Application;
import deco2800.arcade.utils.AsyncFuture;
import deco2800.arcade.utils.Handler;
import deco2800.arcade.client.image.EncodedImageProvider;
import deco2800.arcade.model.EncodedImage;

/**
 * Manages images provided by an EncodedImageProvider (effectively ImageClient).
 * This class handles the memory management (i.e calling dispose() on Pixmaps and
 * Textures) involved with images and can request encoded versions of those images.
 * 
 * When used in conjunction with ImageClient, this allows images from the server to
 * be displayed.
 */
public class ImageManager {
    private HashMap<String, Pixmap> pixmapCache;
    private HashMap<String, Texture> textureCache;
    private EncodedImageProvider provider;

    public ImageManager(EncodedImageProvider provider) {
	this.provider = provider;
	this.textureCache = new HashMap<String, Texture>();       
	this.pixmapCache = new HashMap<String, Pixmap>();
    }

    /**
     * Invalidates all cached textures, forcing them to be recreated when they
     * are next needed. All existing Textures provided by any futures returned
     * by this instance are disposed of and should not be used.
     */
    public void invalidateCachedTextures() {
	for(Texture tex : textureCache.values()) {
	    tex.dispose();
	}

	textureCache.clear();
    }   

    /**
     * Returns a future to a Pixmap representing the image whose ID is imageID.
     * The returned Pixmap should not be disposed of - it is managed internally
     * by the ImageManager.
     *
     * Note that if you're wanting to draw an image, you should call getTexture()
     * with the same imageID instead, as it caches textures and so doesn't have
     * the overhead of needing to create a new Texture for display.
     */
    public AsyncFuture<Pixmap> getPixmap(final String imageID) {
	final AsyncFuture<Pixmap> future = new AsyncFuture<Pixmap>();
	
	// See if we've got the pixmap, otherwise we'll need to ask our provider for
	// the image's encoded version. We'll ask for the provider's cached version
	// to avoid network access where possible.
	if (pixmapCache.containsKey(imageID)) {
	    future.provide(pixmapCache.get(imageID)); 
	} else {
	    provider.get(imageID, true).setHandler(new Handler<EncodedImage>() {
		public void handle(EncodedImage img) {
		    byte[] data = img.getData();
		    Pixmap pixmap = new Pixmap(data, 0, data.length);
		    pixmapCache.put(imageID, pixmap);
		    future.provide(pixmap);
		}
	    });
	}

	return future;
    }

    /**
     * Returns a future to a GDX Texture based on the given imageID.
     * This future's handler is guaranteed to execute in the GDX render
     * thread, making it valid to use then and there.
     *
     * If you're calling .get() on the future then you'll need to make
     * sure that sure you're in the correct thread to use it.
     */
    public AsyncFuture<Texture> getTexture(final String imageID) {
	final AsyncFuture<Texture> future = new AsyncFuture<Texture>();
	
	// If we've already got that texture then we can just provide immediately,
	// otherwise we'll need to get its pixmap, build a texture and cache it.
	// Then we can provide the future.
	if (textureCache.containsKey(imageID)) {
	    Gdx.app.postRunnable(new Runnable() {
		@Override
		public void run() {	
		    future.provide(textureCache.get(imageID));
		}
	    });
	} else {
	    getPixmap(imageID).setHandler(new Handler<Pixmap>() {
		public void handle(final Pixmap pixmap) {
		    Gdx.app.postRunnable(new Runnable() {
			@Override
			public void run() {	
			    Texture tex = new Texture(pixmap);
			    textureCache.put(imageID, tex);		    		    
			    future.provide(tex);
			}
		    });
		    
		}
	    });
	}

	return future;
    }

}
