package deco2800.server.listener;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import deco2800.arcade.protocol.communication.ChatHistory;
import deco2800.arcade.protocol.communication.CommunicationRequest;
import deco2800.arcade.protocol.communication.TextMessage;
import deco2800.server.ArcadeServer;
import deco2800.server.database.ChatStorage;

public class CommunicationListener extends Listener {
	
	private Server server;
	private Map<Integer, Integer> connectedUsers;
	private Map<Integer, String> userAliases;
	private TextMessage textMessage;
	
	
	public CommunicationListener(Server server) {
		this.server = server;
		this.textMessage = new TextMessage();
		this.connectedUsers = new HashMap<Integer, Integer>();
		this.userAliases = new HashMap<Integer, String>();
	}
	
	@Override
	public void received(Connection connection, Object object) {
		super.received(connection, object);

		if (object instanceof CommunicationRequest){
			CommunicationRequest contact = (CommunicationRequest) object;
			connectedUsers.put(contact.playerID, connection.getID());
			userAliases.put(contact.playerID, contact.username);
			
			//A user has just logged in, get there chat history and send it to them
			Map<Integer, List<String>> personalChatHistory = ArcadeServer.instance().getChatStorage().getChatHistory(contact.playerID);
			if (personalChatHistory != null){
				ChatHistory chatHistory = new ChatHistory();
				chatHistory.updateChatHistory(personalChatHistory);
				server.sendToTCP(connectedUsers.get(contact.playerID), chatHistory);
			}
		}
							
		if(object instanceof TextMessage){
			textMessage = (TextMessage) object;
			textMessage.setSenderUsername(userAliases.get(textMessage.getSenderID()));
			String chatLine = textMessage.getSenderUsername() + ": " + textMessage.getText();

			//Need a way to get a Player object from somewhere... Or at least the ability to check stuff like IsBlocked from just two playerIDs...
			//In the mean time, forward the message without checking if blocked:
			for (int recipientID : textMessage.getRecipients()){
				if (connectedUsers.get(recipientID) != null){ //Recipient is online
					if (recipientID != textMessage.getSenderID()){
						ArcadeServer.instance().getChatStorage().addChatHistory(recipientID, textMessage.getSenderID(), chatLine);
						ArcadeServer.instance().getChatStorage().addChatHistory(textMessage.getSenderID(), recipientID, chatLine);
					}
					server.sendToTCP(connectedUsers.get(recipientID), textMessage);
				} else { //Recipient is offline
					ArcadeServer.instance().getChatStorage().addChatHistory(recipientID, textMessage.getSenderID(), chatLine);
					ArcadeServer.instance().getChatStorage().addChatHistory(textMessage.getSenderID(), recipientID, chatLine);
				}
			}
			
			//What about something like this?
			//ArcadeServer.instance().getplayers?
			
			/*
			//This stuff was for the case where userAliases was <playerID, Player> but that broke the network passing around Players for some reason
			
			for (int recipientID : textMessage.recipients){
				//Check if the recipient has the sender blocked before forwarding the message
				//This should come from a persistent database, not just a list that gets populated as the users login!
				if (userAliases.get(recipientID) != null){ //This shouldn't happen with a database, but can as per the comment above
					if (userAliases.get(recipientID).isBlocked(userAliases.get(textMessage.senderID))){ //Blocked
						//Don't need to do anything here, because the sender won't have themselves blocked, so the message will appear as sent anyway below:
					} else { //Not blocked
						if (connectedUsers.get(recipientID) == null){ //Need a better check than this, unless connectedUsers removes a user when they go offline?
							//that player is offline, store the message in their chat history (database)
						} else {
							//that player is online, send the message directly
							textMessage.senderName = userAliases.get(textMessage.senderID).getUsername();
							server.sendToTCP(connectedUsers.get(recipientID), textMessage);
						}
					}					
				}	
			}
			*/
		}
	}
}
