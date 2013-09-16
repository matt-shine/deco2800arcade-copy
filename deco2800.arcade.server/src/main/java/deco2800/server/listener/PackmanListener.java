package deco2800.server.listener;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import deco2800.arcade.protocol.packman.GameUpdateCheckRequest;
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

        System.out.println("Here is a test");
        System.out.println(object);
		if (object instanceof GameUpdateCheckRequest) {
            System.out.println("Inside instanceof");
            PackageServer packServ = ArcadeServer.instance().packServ();
            packServ.printSuccess();
		}
	}


}
