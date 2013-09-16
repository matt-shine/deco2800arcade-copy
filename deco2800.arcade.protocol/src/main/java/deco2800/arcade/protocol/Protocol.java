package deco2800.arcade.protocol;

import javax.crypto.SealedObject;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.serializers.*;

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
import deco2800.arcade.protocol.highscore.AddScoreRequest;
import deco2800.arcade.protocol.replay.EndSessionRequest;
import deco2800.arcade.protocol.replay.EndSessionResponse;
import deco2800.arcade.protocol.replay.GetEventsRequest;
import deco2800.arcade.protocol.replay.GetEventsResponse;
import deco2800.arcade.protocol.replay.ListSessionsRequest;
import deco2800.arcade.protocol.replay.ListSessionsResponse;
import deco2800.arcade.protocol.replay.PushEventRequest;
import deco2800.arcade.protocol.replay.PushEventResponse;
import deco2800.arcade.protocol.replay.StartSessionRequest;
import deco2800.arcade.protocol.replay.StartSessionResponse;
import deco2800.arcade.protocol.replay.demo.ReplayRequest;
import deco2800.arcade.protocol.replay.demo.ReplayResponse;

public class Protocol {

//	private static List<Class<?>> registeredClasses = new ArrayList<Class<?>>();
	
	/**
	 * Registers the classes that will be sent over the network. Classes 
	 * registered in this method will not be encrypted.
	 * @param kryo
	 */
	public static void register(Kryo kryo) {
		kryo.register(SealedObject.class);
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
//				new FieldSerializer(kryo, ConnectionRequest.class), connectionRequest.getKey()));
//	}

}
