package deco2800.arcade.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class EncodedImage {
    // data is assumed to be PNG encoded (this could change later but is fine
    // for now)
    private byte[] data;

    /**
     * Creates an EncodedImage using the provided PNG file.
     *
     * @param pngFile The png file to load.
     */
    public EncodedImage(File pngFile) throws FileNotFoundException, IOException {
        // defer to our stream constructor
        this(new FileInputStream(pngFile), pngFile.length());
    }

    /**
     * Creates an EncodedImage by sinking the PNG-encoded image data provided
     * by the given stream.
     *
     * @param pngDataStream The input stream providing PNG-encoded data
     * @param streamLength  The length of the stream.
     */
    public EncodedImage(InputStream pngDataStream, long streamLength) 
        throws IOException {
        // first sink the stream
        data = new byte[(int)streamLength];
        int imgSize = 0;
        int i;
        while ((i = pngDataStream.read()) != -1) {
            // resize if needed (won't have to if streamLength is correct)
            if (imgSize == data.length) {
                byte[] old = data;
                data = new byte[old.length * 2];
                System.arraycopy(old, 0, data, 0, old.length);
            }

            data[imgSize] = (byte)i;
            ++imgSize;
        }

        // and trim the sink buffer down if needed
        if (imgSize != data.length) {
            byte[] read = data;
            data = new byte[imgSize];
            System.arraycopy(read, 0, data, 0, imgSize);
        }
    }

    /**
     * Creates an EncodedImage by taking a copy of a PNG encoded image
     * stored in a byte[].
     *
     * @param pngData The png encoded data to copy
     */
    public EncodedImage(byte[] pngData) {
        data = new byte[pngData.length];
        System.arraycopy(pngData, 0, data, 0, pngData.length);
    }

    /**
     * Returns an InputStream which can be used to access the image's data
     * encoded in PNG format.
     *
     * @return An InputStream which provides this image's data in PNG format.
     */
    public ByteArrayInputStream getInputStream() {
        return new ByteArrayInputStream(data);
    }

    /**
     * Returns a copy of the image's PNG-encoded data.
     */
    public byte[] getData() {
        byte[] cpy = new byte[data.length];
        System.arraycopy(data, 0, cpy, 0, data.length);
        return cpy;
    }

    //public static EncodedImage encode() {
    //    return new EncodedImage();
    //}
}
