package deco2800.arcade.protocol;

import com.esotericsoftware.kryo.Kryo;

public class Protocol {

	public static void register(Kryo kryo) {
		kryo.register(ConnectRequest.class);
		kryo.register(ConnectionOK.class);
		kryo.register(NewGameRequest.class);
		kryo.register(OKMessage.class);
	}
	
}
