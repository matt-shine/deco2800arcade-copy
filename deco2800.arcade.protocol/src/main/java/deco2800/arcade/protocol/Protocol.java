package deco2800.arcade.protocol;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.KeyGenerator;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.serializers.*;
import com.esotericsoftware.kryo.serializers.DefaultSerializers.*;

import deco2800.arcade.protocol.achievement.AchievementListRequest;
import deco2800.arcade.protocol.achievement.AddAchievementRequest;
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

	private static List<Class<?>> registeredClasses = new ArrayList<Class<?>>();
	/**
	 * Registers the classes that will be sent over the network. Classes 
	 * registered in this method will not be encrypted.
	 * @param kryo
	 */
	public static void register() {
		
		// Connection messages
		register(ConnectionResponse.class);

		// Credit messages
		register(CreditBalanceRequest.class);
		register(CreditBalanceResponse.class);

		// Achievement messages
		register(AchievementListRequest.class);
		register(AddAchievementRequest.class);

		// Game messages
		register(GameStatusUpdate.class);
		register(GameStatusUpdateResponse.class);
		register(NewGameRequest.class);
		register(GameRequestType.class);
		register(NewGameResponse.class);

		// Communication messages
		register(CommunicationRequest.class);
		register(ContactListUpdate.class);
		register(ChatRequest.class);
		register(TextMessage.class);
		register(VoiceMessage.class);
		
		// Register miscellaneous classes
		register(byte[].class);
	}
	
	public static Boolean contains(Class<?> type) {
		return registeredClasses.contains(type);
	}
	
	private static void register(Class<?> type) {
		if(!registeredClasses.contains(type))
		registeredClasses.add(type);
	}
	
//	/**
//	 * Sends ConnectionRequest over network using encryption rather than 
//	 * plaintext
//	 * @param connectionRequest
//	 */
//	public static void registerEncrypted(ConnectionRequest connectionRequest) {		
//		connectionRequest.generateKey();
//		
//		// Ensures any ConnectionRequests are sent over the network using the 
//		// Blowfish encryption algorithm
//		kryo.register(ConnectionRequest.class, new BlowfishSerializer(
//				new FieldSerializer(kryo, ConnectionRequest.class), connectionRequest.key));
//	}

}