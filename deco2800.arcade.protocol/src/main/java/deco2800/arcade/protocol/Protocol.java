package deco2800.arcade.protocol;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.serializers.*;

import java.util.ArrayList;

import deco2800.arcade.model.Achievement;
import deco2800.arcade.model.Game;
import deco2800.arcade.model.Icon;
import deco2800.arcade.protocol.achievement.*;
import deco2800.arcade.protocol.communication.ChatRequest;
import deco2800.arcade.protocol.communication.ContactListUpdate;
import deco2800.arcade.protocol.communication.CommunicationRequest;
import deco2800.arcade.protocol.communication.TextMessage;
import deco2800.arcade.protocol.communication.VoiceMessage;
import deco2800.arcade.protocol.connect.ConnectionRequest;
import deco2800.arcade.protocol.connect.ConnectionResponse;
import deco2800.arcade.protocol.credit.CreditBalanceRequest;
import deco2800.arcade.protocol.credit.CreditBalanceResponse;
import deco2800.arcade.protocol.packman.GameUpdateCheckRequest;
import deco2800.arcade.protocol.packman.GameUpdateCheckResponse;
import deco2800.arcade.protocol.game.*;
import deco2800.arcade.protocol.highscore.AddScoreRequest;
import deco2800.arcade.protocol.packman.GameUpdateCheckRequest;
import deco2800.arcade.protocol.highscore.GetScoreRequest;
import deco2800.arcade.protocol.highscore.GetScoreResponse;
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
import deco2800.arcade.protocol.replay.types.Session;

public class Protocol {
	
	private static Kryo kryo;
	
	/**
	 * Keeps the kryo instance for use with registerEncrypted()
	 * @param kryo
	 */
	public static void setKryo(Kryo kryo) {
		Protocol.kryo = kryo;
	}

	/**
	 * Registers the classes that will be sent over the network. Classes 
	 * registered in this method will not be encrypted.
	 * @param kryo
	 */
	public static void register(Kryo kryo) {
		//Connection messages
		kryo.register(ConnectionRequest.class);
		Protocol.setKryo(kryo);
		
		// Connection messages
		kryo.register(ConnectionResponse.class);

		// Credit messages
		kryo.register(CreditBalanceRequest.class);
		kryo.register(CreditBalanceResponse.class);

		// Achievement messages
		kryo.register(Achievement.class);
		kryo.register(AchievementsForIDsRequest.class);
		kryo.register(AchievementsForIDsResponse.class);
		kryo.register(AchievementsForGameRequest.class);
		kryo.register(AchievementsForGameResponse.class);
		kryo.register(IncrementProgressRequest.class);
		kryo.register(IncrementProgressResponse.class);
		kryo.register(ProgressForPlayerRequest.class);
		kryo.register(ProgressForPlayerResponse.class);
		kryo.register(java.util.ArrayList.class);
		kryo.register(AchievementListRequest.class);
		kryo.register(AddAchievementRequest.class);
		
		// High Score Messages
		kryo.register(AddScoreRequest.class);
		kryo.register(GetScoreRequest.class);
		kryo.register(GetScoreResponse.class);
		
		//Replay messages
		kryo.register(ReplayRequest.class);
		kryo.register(ReplayResponse.class);
		kryo.register(StartSessionRequest.class);
		kryo.register(StartSessionResponse.class);
		kryo.register(EndSessionRequest.class);
		kryo.register(EndSessionResponse.class);
		kryo.register(ListSessionsRequest.class);
		kryo.register(ListSessionsResponse.class);
		kryo.register(PushEventRequest.class);
		kryo.register(PushEventResponse.class);
		kryo.register(GetEventsRequest.class);
		kryo.register(GetEventsResponse.class);
		kryo.register(Session.class);
		
		//Game messages
		kryo.register(GameStatusUpdate.class);
		kryo.register(GameStatusUpdateResponse.class);
		kryo.register(NewGameRequest.class);
		kryo.register(GameRequestType.class);
		kryo.register(NewGameResponse.class);
        kryo.register(GameLibraryRequest.class);
        kryo.register(GameLibraryResponse.class);
        kryo.register(Game.class);
        kryo.register(Icon.class);

		// Communication messages
		kryo.register(CommunicationRequest.class);
		kryo.register(ContactListUpdate.class);
		kryo.register(ChatRequest.class);
		kryo.register(TextMessage.class);
		kryo.register(VoiceMessage.class);

		// Package Manager
        kryo.register(GameUpdateCheckRequest.class);
        kryo.register(GameUpdateCheckResponse.class);

		// Register miscellaneous classes
		kryo.register(byte[].class);
		kryo.register(ArrayList.class);
        kryo.register(java.util.Set.class);
        kryo.register(java.util.HashSet.class);
        kryo.register(java.awt.image.BufferedImage.class);
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
				new FieldSerializer<Object>(kryo, ConnectionRequest.class), connectionRequest.key));
	}

}
