package deco2800.arcade.protocol;

import java.security.NoSuchAlgorithmException;

import javax.crypto.KeyGenerator;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.serializers.*;
import com.esotericsoftware.kryo.serializers.DefaultSerializers.*;

import deco2800.arcade.protocol.achievement.*;
import java.util.ArrayList;
import deco2800.arcade.protocol.communication.ChatRequest;
import deco2800.arcade.protocol.communication.ContactListUpdate;
import deco2800.arcade.protocol.communication.CommunicationRequest;
import deco2800.arcade.protocol.communication.TextMessage;
import deco2800.arcade.protocol.communication.VoiceMessage;
import deco2800.arcade.protocol.connect.ConnectionRequest;
import deco2800.arcade.protocol.connect.ConnectionResponse;
import deco2800.arcade.protocol.credit.CreditBalanceRequest;
import deco2800.arcade.protocol.credit.CreditBalanceResponse;
import deco2800.arcade.protocol.game.GameRequestType;
import deco2800.arcade.protocol.game.GameStatusUpdate;
import deco2800.arcade.protocol.game.GameStatusUpdateResponse;
import deco2800.arcade.protocol.game.NewGameRequest;
import deco2800.arcade.protocol.game.NewGameResponse;

public class Protocol {
	
	private static Kryo kryo;
	
	public static void setKryo(Kryo kryo) {
		Protocol.kryo = kryo;
	}

	/**
	 * Registers the classes that will be sent over the network. Classes 
	 * registered in this method will not be encrypted.
	 * @param kryo
	 */
	public static void register(Kryo kryo) {
		Protocol.setKryo(kryo);
		
		// Connection messages
		kryo.register(ConnectionResponse.class);

		// Credit messages
		kryo.register(CreditBalanceRequest.class);
		kryo.register(CreditBalanceResponse.class);
<<<<<<< HEAD
		
		//Achievement messages

		kryo.register(AchievementsForIDsRequest.class);
		kryo.register(AchievementsForIDsResponse.class);
		kryo.register(AchievementsForGameRequest.class);
		kryo.register(AchievementsForGameResponse.class);
		kryo.register(IncrementProgressRequest.class);
		kryo.register(IncrementProgressResponse.class);
		kryo.register(ProgressForPlayerRequest.class);
		kryo.register(ProgressForPlayerResponse.class);
		kryo.register(java.util.ArrayList.class);
		
		//Game messages
=======

		// Achievement messages
		kryo.register(AchievementListRequest.class);
		kryo.register(AddAchievementRequest.class);

		// Game messages
>>>>>>> master
		kryo.register(GameStatusUpdate.class);
		kryo.register(GameStatusUpdateResponse.class);
		kryo.register(NewGameRequest.class);
		kryo.register(GameRequestType.class);
		kryo.register(NewGameResponse.class);

		// Communication messages
		kryo.register(CommunicationRequest.class);
		kryo.register(ContactListUpdate.class);
		kryo.register(ChatRequest.class);
		kryo.register(TextMessage.class);
		kryo.register(VoiceMessage.class);
		
		// Register miscellaneous classes
		kryo.register(byte[].class);
	}
	
	/**
	 * Sends ConnectionRequest over network using encryption rather than 
	 * plaintext
	 * @param connectionRequest
	 */
	public static void registerEncrypted(ConnectionRequest connectionRequest) {		
		connectionRequest.generateKey();
		
		// Ensures any ConnectionRequests are sent over the network using the 
		// Blowfish encryption algorithm
		kryo.register(ConnectionRequest.class, new BlowfishSerializer(
				new FieldSerializer(kryo, ConnectionRequest.class), connectionRequest.key));
	}

}
