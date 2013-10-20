package deco2800.server.listener;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

import deco2800.arcade.model.ChatNode;
import deco2800.arcade.model.Player;
import deco2800.arcade.protocol.communication.ChatHistory;
import deco2800.arcade.protocol.communication.CommunicationRequest;
import deco2800.arcade.protocol.communication.TextMessage;
import deco2800.server.ArcadeServer;
import deco2800.server.database.Database;
import deco2800.server.database.DatabaseException;

public class CommunicationListener extends Listener {
	
	private Server server;
	private HashMap<Integer, Integer> connectedUsers;
	private HashMap<Integer, String> userAliases;
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
			
			//A user has just logged in, get their chat history and send it to them
			HashMap<Integer, ChatNode> personalChatHistory = ArcadeServer.instance().getChatStorage().getChatHistory(contact.playerID);
			
			if (personalChatHistory != null){
				ChatHistory chatHistory = new ChatHistory();
				chatHistory.setChatHistory(personalChatHistory);
				server.sendToTCP(connectedUsers.get(contact.playerID), chatHistory);
			}
		}
							
		if(object instanceof TextMessage){
			textMessage = (TextMessage) object;

			/*
			//In the mean time, forward the message without checking if blocked:
			for (int recipientID : textMessage.getRecipients()){
				ArcadeServer.instance().getChatStorage().addChatHistory(textMessage, recipientID);				
				if (connectedUsers.containsKey(recipientID)){
					server.sendToTCP(connectedUsers.get(recipientID), textMessage);
				}
			}
			*/

			java.sql.Connection dbconn;
			Statement statement = null;
			ResultSet resultSet = null;
			List<Integer> blockedIDs = new ArrayList<Integer>();
			
			for (int recipientID : textMessage.getRecipients()){
				//For each recipient, get their blocked list and check that the sender is not in it
				try {
					dbconn = Database.getConnection();
					statement = dbconn.createStatement();
					resultSet = statement.executeQuery("SELECT * FROM FRIENDS" 
													+" WHERE U1=" + recipientID 
													+ " AND BLOCKED=1");
					while (resultSet.next()){
						blockedIDs.add(resultSet.getInt("U2"));
					}
				} catch (DatabaseException e) {
					//Failed
				} catch (SQLException e) {
					//Failed
				}
				
				//If the sender is not in the recipient's blocked list, then proceed
				if (!blockedIDs.contains(textMessage.getSenderID())){
					ArcadeServer.instance().getChatStorage().addChatHistory(textMessage, recipientID);				
					if (connectedUsers.containsKey(recipientID)){
						server.sendToTCP(connectedUsers.get(recipientID), textMessage);
					}
				}
			}
			
		}
	}
}
