package deco2800.server.listener;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import deco2800.arcade.protocol.packman.GameUpdateCheckRequest;
import deco2800.arcade.protocol.packman.GameUpdateCheckResponse;
import deco2800.arcade.protocol.BlockingMessage;
import deco2800.server.ArcadeServer;
import deco2800.arcade.packman.PackageServer;

import java.lang.System;

public class PackmanListener extends Listener {

    /**
     * Initializer
     */
    public PackmanListener(){}

	@Override
	public void received(Connection connection, Object object) {
		super.received(connection, object);

		if (object instanceof GameUpdateCheckRequest) {
            System.out.println("[Server]: GameUpdateCheckRequest recieved");
            PackageServer packServ = ArcadeServer.instance().packServ();
            packServ.printSuccess();
		}

        if (object instanceof GameUpdateCheckRequest){
            System.out.println("[Server]:  GameUpdateCheckRequest recieved");

            PackageServer packServ = ArcadeServer.instance().packServ();
            GameUpdateCheckRequest req = (GameUpdateCheckRequest) object;
            GameUpdateCheckResponse resp = new GameUpdateCheckResponse();
            String gameID = req.gameID;

            // Get the MD5 for the game
            // TODO actually check whether the game version is the latest
            resp.md5 = packServ.getMD5ForGame(gameID);

            BlockingMessage.respond(connection, req, resp);
        }
	}


}
