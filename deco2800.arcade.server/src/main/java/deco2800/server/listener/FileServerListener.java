package deco2800.server.listener;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import deco2800.arcade.protocol.packman.FetchGameRequest;
import deco2800.arcade.protocol.packman.FetchGameResponse;
import deco2800.arcade.protocol.BlockingMessage;
import deco2800.server.ArcadeServer;
import deco2800.arcade.packman.PackageServer;

import java.lang.System;

import java.io.FileInputStream;
import java.io.DataInputStream;
import java.io.IOException;

public class FileServerListener extends Listener {

    private static final String BASE_PATH = "Release";

    /**
     * Initializer
     */
    public FileServerListener(){}

	@Override
	public void received(Connection connection, Object object) {
		super.received(connection, object);

        if (object instanceof FetchGameRequest) {
            System.out.println("[Server]:  FetchGameRequest recieved");

            PackageServer packServ = ArcadeServer.instance().packServ();
            FetchGameRequest req = (FetchGameRequest) object;
            FetchGameResponse resp = new FetchGameResponse();

            // Build path
            String path = req.gameID + "-" + req.version + ".tar.gz";
            path = BASE_PATH + "/" + path;

            resp.gameID = req.gameID;
            resp.version = req.version;
            resp.byteOffset = req.byteOffset;
            resp.blockSize = req.blockSize;

            // Read the file data
            try {
                System.out.println(path);
                FileInputStream file = new FileInputStream(path);
                DataInputStream d = new DataInputStream(file);

                resp.totalBytes = d.available();

                file.skip(req.byteOffset);
                resp.data = new byte[req.blockSize];
                resp.byteCount = file.read(resp.data);

                file.close();
                resp.status = 1;
            } catch (IOException e) {
                // TODO actually work out what the error really was
                e.printStackTrace();
                resp.status = -3;
            }

            BlockingMessage.respond(connection, req, resp);
        }
	}


}
