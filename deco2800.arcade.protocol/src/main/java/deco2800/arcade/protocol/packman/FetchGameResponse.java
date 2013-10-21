package deco2800.arcade.protocol.packman;

import deco2800.arcade.protocol.BlockingMessage;

public class FetchGameResponse extends BlockingMessage {

    public String gameID;
    public String version;

    // Game JAR data
	public byte[] data;

    // Total bytes in the entire file (not just this object)
    public int totalBytes;

    // Number of bytes returned this request
    public int byteCount;

    // Which byte in the file to start the read at
    public int byteOffset;

    // Block size in bytes
    public int blockSize;

    // Response status
    //  1   -> Read ok
    // -1   -> Invalid gameID
    // -2   -> Invalid version
    // -3   -> Error reading file
    public int status;

    @Override
    public String toString() {
        return "FetchGameResponse: <gameID=\"" + gameID + "\", version=\"" + version +
                "\", totalBytes=" + totalBytes + ", byteCount=" + byteCount +
                ", byteOffset=" + byteOffset + ", blockSize=" + blockSize +
                ", status=" + status;
    }
	
}
