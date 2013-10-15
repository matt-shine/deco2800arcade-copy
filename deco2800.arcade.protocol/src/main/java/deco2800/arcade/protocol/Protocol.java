package deco2800.arcade.protocol;

import java.util.ArrayList;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.serializers.BlowfishSerializer;
import com.esotericsoftware.kryo.serializers.FieldSerializer;

import deco2800.arcade.model.Game;
import deco2800.arcade.model.Icon;
import deco2800.arcade.protocol.achievement.AchievementsForGameRequest;
import deco2800.arcade.protocol.achievement.AchievementsForGameResponse;
import deco2800.arcade.protocol.achievement.AchievementsForIDsRequest;
import deco2800.arcade.protocol.achievement.AchievementsForIDsResponse;
import deco2800.arcade.protocol.achievement.IncrementProgressRequest;
import deco2800.arcade.protocol.achievement.IncrementProgressResponse;
import deco2800.arcade.protocol.achievement.ProgressForPlayerRequest;
import deco2800.arcade.protocol.achievement.ProgressForPlayerResponse;
import deco2800.arcade.protocol.communication.ChatHistory;
import deco2800.arcade.protocol.communication.CommunicationRequest;
import deco2800.arcade.protocol.communication.ContactListUpdate;
import deco2800.arcade.protocol.communication.TextMessage;
import deco2800.arcade.protocol.communication.VoiceMessage;
import deco2800.arcade.protocol.connect.ConnectionRequest;
import deco2800.arcade.protocol.connect.ConnectionResponse;
import deco2800.arcade.protocol.credit.CreditBalanceRequest;
import deco2800.arcade.protocol.credit.CreditBalanceResponse;
import deco2800.arcade.protocol.game.GameLibraryRequest;
import deco2800.arcade.protocol.game.GameLibraryResponse;
import deco2800.arcade.protocol.game.GameRequestType;
import deco2800.arcade.protocol.game.GameStatusUpdate;
import deco2800.arcade.protocol.game.GameStatusUpdateResponse;
import deco2800.arcade.protocol.game.NewGameRequest;
import deco2800.arcade.protocol.game.NewGameResponse;
import deco2800.arcade.protocol.highscore.AddScoreRequest;
import deco2800.arcade.protocol.highscore.GetScoreRequest;
import deco2800.arcade.protocol.highscore.GetScoreResponse;
import deco2800.arcade.protocol.image.GetImageRequest;
import deco2800.arcade.protocol.image.GetImageResponse;
import deco2800.arcade.protocol.image.SetImageRequest;
import deco2800.arcade.protocol.image.SetImageResponse;
import deco2800.arcade.protocol.lobby.ActiveMatchDetails;
import deco2800.arcade.protocol.lobby.ClearListRequest;
import deco2800.arcade.protocol.lobby.CreateMatchRequest;
import deco2800.arcade.protocol.lobby.CreateMatchResponse;
import deco2800.arcade.protocol.lobby.JoinLobbyMatchRequest;
import deco2800.arcade.protocol.lobby.JoinLobbyMatchResponse;
import deco2800.arcade.protocol.lobby.JoinLobbyMatchResponseType;
import deco2800.arcade.protocol.lobby.JoinLobbyResponseType;
import deco2800.arcade.protocol.lobby.LobbyRequestType;
import deco2800.arcade.protocol.lobby.NewLobbyRequest;
import deco2800.arcade.protocol.multiplayerGame.GameStateUpdateRequest;
import deco2800.arcade.protocol.multiplayerGame.MultiGameRequestType;
import deco2800.arcade.protocol.multiplayerGame.NewMatchmakingRequest;
import deco2800.arcade.protocol.multiplayerGame.NewMultiGameRequest;
import deco2800.arcade.protocol.multiplayerGame.NewMultiResponse;
import deco2800.arcade.protocol.multiplayerGame.NewMultiSessionResponse;
import deco2800.arcade.protocol.packman.FetchGameRequest;
import deco2800.arcade.protocol.packman.FetchGameResponse;
import deco2800.arcade.protocol.packman.GameUpdateCheckRequest;
import deco2800.arcade.protocol.packman.GameUpdateCheckResponse;
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
//import deco2800.arcade.model.Achievement;

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
		//kryo.register(Achievement.class);
		kryo.register(AchievementsForIDsRequest.class);
		kryo.register(AchievementsForIDsResponse.class);
		kryo.register(AchievementsForGameRequest.class);
		kryo.register(AchievementsForGameResponse.class);
		kryo.register(IncrementProgressRequest.class);
		kryo.register(IncrementProgressResponse.class);
		kryo.register(ProgressForPlayerRequest.class);
		kryo.register(ProgressForPlayerResponse.class);
		kryo.register(java.util.ArrayList.class);

		// Image messages
		Class<?>[] imageClasses = { GetImageRequest.class, GetImageResponse.class,
					    SetImageRequest.class, SetImageResponse.class };
		for (Class<?> c : imageClasses) {
		    kryo.register(c);
		}
		
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
		kryo.register(ChatHistory.class);
		kryo.register(TextMessage.class);
		kryo.register(VoiceMessage.class);
		
		//Multiplayer Messages
		kryo.register(NewMultiGameRequest.class);
		kryo.register(NewMultiResponse.class);
		kryo.register(MultiGameRequestType.class);
		kryo.register(GameStateUpdateRequest.class);
		kryo.register(NewMultiSessionResponse.class);
		kryo.register(NewMatchmakingRequest.class);

		// Package Manager
		kryo.register(GameUpdateCheckRequest.class);
    kryo.register(FetchGameRequest.class);
    kryo.register(FetchGameResponse.class);
		
		//Lobby classes
		kryo.register(NewLobbyRequest.class);
		kryo.register(LobbyRequestType.class);
		kryo.register(JoinLobbyResponseType.class);
		kryo.register(CreateMatchRequest.class);
		kryo.register(CreateMatchResponse.class);
		kryo.register(ActiveMatchDetails.class);
		kryo.register(ClearListRequest.class);
		kryo.register(JoinLobbyMatchRequest.class);
        kryo.register(GameUpdateCheckRequest.class);
        kryo.register(GameUpdateCheckResponse.class);
        kryo.register(JoinLobbyMatchResponse.class);
        kryo.register(JoinLobbyMatchResponseType.class);

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
