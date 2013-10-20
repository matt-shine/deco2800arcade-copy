package deco2800.arcade.client;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import deco2800.arcade.client.network.NetworkClient;
import deco2800.arcade.packman.PackCompress;
import deco2800.arcade.packman.PackageUtils;
import deco2800.arcade.packman.PackageClient;
import deco2800.arcade.protocol.BlockingMessage;
import deco2800.arcade.protocol.packman.FetchGameRequest;
import deco2800.arcade.protocol.packman.FetchGameResponse;

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

    public FileClient(String gameID, NetworkClient client) {
        this.gameID = gameID;
        this.version = null;
        this.client = client;
    }

    @Override
    public void run() {
        String filebase = null;

        FetchGameResponse resp = null;

        FetchGameRequest fetchGameRequest = new
                FetchGameRequest();
        fetchGameRequest.gameID = gameID;
        fetchGameRequest.version = version;
        fetchGameRequest.byteOffset = 0;
        fetchGameRequest.blockSize = 512;

        int bytesReceived = 0;

        // Get the file
        try {

            BlockingMessage r = BlockingMessage.request(client.kryoClient(),
                    fetchGameRequest);
            resp = (FetchGameResponse) r;

            filebase = "../games/" + resp.gameID + "-" + resp.version;

            FileOutputStream fout = new FileOutputStream(filebase + ".tar.gz");

            // Fetch the file
            do {
                //stops the file server from crashing every two seconds.
                if (resp.data == null) return;
                
                fout.write(resp.data, 0, resp.byteCount);
                fout.flush();

                fetchGameRequest.byteOffset = fetchGameRequest.byteOffset + resp.byteCount;

                bytesReceived = bytesReceived + resp.byteCount;

                r = BlockingMessage.request(client.kryoClient(),
                        fetchGameRequest);
                resp = (FetchGameResponse) r;

            } while (resp.status == 1 && bytesReceived < resp.totalBytes);

            fout.close();
        } catch (IOException e) {
            System.out.println("Error saving downloaded game: ");
            e.printStackTrace();
        }

        try {
            // Verify MD5 signature
            String md5 = PackageUtils.genMD5(filebase + ".tar.gz");
            if (!md5.equals(resp.md5)) {
                System.out.println("[FILE CLIENT] Error transferring file. MD5 checksum failed.");
            } else {
                // Extract the JAR
                PackCompress unpack = new PackCompress();
                unpack.expand(filebase + ".tar.gz", filebase + ".jar");

                // Add the new JAR to the classpath
                if (!PackageClient.addJar(filebase + ".jar")) {
                    System.out.println("[FILE CLIENT] Error adding downloaded JAR to classpath.");
                }
            }

            // Delete the .tar.gz file regardless of whether it was ok or not
            File del = new File(filebase + ".tar.gz");
            del.delete();
        } catch (IOException e) {
            System.out.println("Error extracting downloaded game: ");
            e.printStackTrace();
        }

        if (resp == null || resp.status != 1) {
            System.out.println("Error fetching game JAR: " + resp);
        }
    }
}
