package deco2800.arcade.protocol;

import com.esotericsoftware.kryo.Kryo;

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

	public static void register(Kryo kryo) {
		//Connection messages
		kryo.register(ConnectionRequest.class);
		kryo.register(ConnectionResponse.class);

		//Credit messages
		kryo.register(CreditBalanceRequest.class);
		kryo.register(CreditBalanceResponse.class);
		
		//Achievement messages
		kryo.register(AchievementListRequest.class);
		kryo.register(AddAchievementRequest.class);
		
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
		
		//Game messages
		kryo.register(GameStatusUpdate.class);
		kryo.register(GameStatusUpdateResponse.class);
		kryo.register(NewGameRequest.class);
		kryo.register(GameRequestType.class);
		kryo.register(NewGameResponse.class);
		
		//Communication messages
		kryo.register(CommunicationRequest.class);
		kryo.register(ContactListUpdate.class);
		kryo.register(ChatRequest.class);
		kryo.register(TextMessage.class);
		kryo.register(VoiceMessage.class);
	}
	
}
