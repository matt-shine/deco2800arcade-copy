package deco2800.arcade.protocol;

import com.esotericsoftware.kryo.Kryo;

import deco2800.arcade.protocol.achievement.AchievementListRequest;
import deco2800.arcade.protocol.achievement.AddAchievementRequest;
import deco2800.arcade.protocol.communication.ContactListUpdate;
import deco2800.arcade.protocol.communication.RegisterContact;
import deco2800.arcade.protocol.communication.textMessage;
import deco2800.arcade.protocol.communication.voiceMessage;
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
		kryo.register(RegisterContact.class);
		kryo.register(ContactListUpdate.class);
		kryo.register(textMessage.class);
		kryo.register(voiceMessage.class);
	}
	
}
