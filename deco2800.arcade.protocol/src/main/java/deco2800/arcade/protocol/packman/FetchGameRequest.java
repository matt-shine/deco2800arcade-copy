package deco2800.arcade.protocol.packman;

import deco2800.arcade.protocol.BlockingMessage;

public class FetchGameRequest extends BlockingMessage {
    public String gameID;
    public String version;

    // Which byte in the file to start the read at
    public int byteOffset;

    // Block size in bytes
    public int blockSize;
}
