package deco2800.arcade.protocol.image;
import deco2800.arcade.protocol.NetworkObject;

public class SetImageRequest extends NetworkObject {
    public String imageID;
    public byte[] data;
}
