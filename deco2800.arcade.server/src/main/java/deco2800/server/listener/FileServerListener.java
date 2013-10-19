package deco2800.server.listener;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import deco2800.server.FileServer;
import deco2800.arcade.protocol.packman.FetchGameRequest;

public class FileServerListener extends Listener {

    /**
     * Initializer
     */
    public FileServerListener(){}

	@Override
	public void received(Connection connection, Object object) {
		super.received(connection, object);

        if (object instanceof FetchGameRequest) {
            FileServer fs = new FileServer((FetchGameRequest) object, connection);
            Thread t = new Thread(fs);
            t.start();
        }
	}


}
