package deco2800.arcade.client.image;

import deco2800.arcade.model.EncodedImage;
import deco2800.arcade.utils.AsyncFuture;

/**
 * Defines an interface for getting EncodedImages via futures based on their
 * ID.
 */
public interface EncodedImageProvider {
    /**
     * Returns a future to an EncodedImage represented by the given imageID.
     *
     * @param imageID The ID of the image to get.
     * @param useCache Whether a cached version of the image should be provided
     *                 or whether the latest version should be retrieved.
     * @return A future to the encoded image.
     */
    AsyncFuture<EncodedImage> get(String imageID, boolean useCache);
}
