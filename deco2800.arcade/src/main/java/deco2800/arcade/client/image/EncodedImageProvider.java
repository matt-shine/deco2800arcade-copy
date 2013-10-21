package deco2800.arcade.client.image;

import deco2800.arcade.model.EncodedImage;
import deco2800.arcade.utils.AsyncFuture;

public interface EncodedImageProvider {
    AsyncFuture<EncodedImage> get(String imageID, boolean useCache);
}
