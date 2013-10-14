package deco2800.arcade.client;

import com.esotericsoftware.kryonet.Connection;
import deco2800.arcade.protocol.packman.FetchGameRequest;
import deco2800.arcade.protocol.packman.FetchGameResponse;
import deco2800.arcade.protocol.BlockingMessage;
import deco2800.arcade.client.network.NetworkClient;

import java.lang.*;

/**
 * This class spawns a thread and sends a request to the server for a given file
 *
 */
public class FileClient implements Runnable {

    private String gameID;
    private String version;
    private NetworkClient client;

    public FileClient(String gameID, String version, NetworkClient client) {
        this.gameID = gameID;
        this.version = version;
        this.client = client;
    }

    @Override
    public void run() {
        FetchGameResponse resp;

        FetchGameRequest fetchGameRequest = new
                FetchGameRequest();
        fetchGameRequest.gameID = gameID;
        fetchGameRequest.version = version;
        fetchGameRequest.byteOffset = 0;
        fetchGameRequest.blockSize = 512;

        int bytesReceived = 0;

        // Fetch the file
        do {
            BlockingMessage r = BlockingMessage.request(client.kryoClient(),
                    fetchGameRequest);

            resp = (FetchGameResponse) r;

            fetchGameRequest.byteOffset = fetchGameRequest.byteOffset + resp.byteCount;

            bytesReceived = bytesReceived + resp.byteCount;

        } while (resp.status == 1 && bytesReceived < resp.totalBytes);

        if (resp.status != 1) {
            System.out.println("Error fetching game JAR: " + resp);
        }
    }
}
