package deco2800.arcade.model;

public class EncodedImage {
    // data is assumed to be PNG encoded (this could change later but is fine
    // for now)
    private byte[] data;

    
    public static EncodedImage encode() {
        return new EncodedImage();
    }
}