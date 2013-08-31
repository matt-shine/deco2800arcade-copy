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
import deco2800.arcade.protocol.multiplayerGame.NewMultiGameRequest;
import deco2800.arcade.protocol.multiplayerGame.NewMultiResponse;


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
		
		//Multiplayer Messages
		kryo.register(NewMultiGameRequest.class);
		kryo.register(NewMultiResponse.class);
	}
	
}
