package deco2800.arcade.client;

import com.esotericsoftware.kryonet.Connection;
import deco2800.arcade.protocol.packman.FetchGameRequest;
import deco2800.arcade.protocol.packman.FetchGameResponse;
import deco2800.arcade.protocol.BlockingMessage;
import deco2800.arcade.client.network.NetworkClient;

import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.*;
import java.lang.System;

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
        FetchGameResponse resp = null;

        FetchGameRequest fetchGameRequest = new
                FetchGameRequest();
        fetchGameRequest.gameID = gameID;
        fetchGameRequest.version = version;
        fetchGameRequest.byteOffset = 0;
        fetchGameRequest.blockSize = 512;

        int bytesReceived = 0;

        try {
            FileOutputStream fout = new FileOutputStream("../games/" + gameID +
                    "-" + version + ".tar.gz");

            // Fetch the file
            do {
                BlockingMessage r = BlockingMessage.request(client.kryoClient(),
                        fetchGameRequest);

                resp = (FetchGameResponse) r;

                fout.write(resp.data, 0, resp.byteCount);
                fout.flush();

                fetchGameRequest.byteOffset = fetchGameRequest.byteOffset + resp.byteCount;

                bytesReceived = bytesReceived + resp.byteCount;

            } while (resp.status == 1 && bytesReceived < resp.totalBytes);

            fout.close();
        } catch (IOException e) {
            System.out.println("Error saving downloaded game: ");
            e.printStackTrace();
        }

        if (resp == null || resp.status != 1) {
            System.out.println("Error fetching game JAR: " + resp);
        }
    }
}
