package deco2800.arcade.communication;


import java.util.ArrayList;
import java.util.List;

public class ChatNode {

	private List<String> participants;

	
	public ChatNode(List<String> chatParticipants){
		participants = new ArrayList<String>(chatParticipants);
	}
	
	public ChatNode(String participant){
		participants = new ArrayList<String>();
		addParticipant(participant);
	}
	
	public void removeParticipant(String playerId){
		participants.remove(playerId);
	}
	
	public void addParticipant(String playerId){
		participants.add(playerId);
	}
	
	public List<String> getParticipants(){
		return participants;
	}
	
}

	
//	private void parseInput(TextMessage textMessage){
//		String text = textMessage.text;
//		
//		if(textMessage.username.equals(username)){
//			if (text.startsWith("/?") || text.startsWith("/help")){
//				controller.updateChat("To add a user to the conversation, type /invite username\n");
//				controller.updateChat("To remove a user from the conversation, type /kick username\n");
//			} else if (textMessage.text.startsWith("/invite ")){
//				invite = textMessage.text.substring(8);
//				
//				if (!model.getParticipants().contains(this.invite)){
//					ChatRequest chatRequest = new ChatRequest();
//					chatRequest.invite = invite;
//					chatRequest.chatID = textMessage.chatID;
//					chatRequest.sender = username;
//					chatRequest.participants.addAll(model.getParticipants());
//					chatRequest.participants.add(invite);
//					networkClient.sendNetworkObject(chatRequest);
//				} else {
//					controller.updateChat(dateFormat.format(date) + " - " + this.invite + " is already in the conversation.\n");
//				}
//			} else if(textMessage.text.startsWith("/kick ")){
//				String kick = textMessage.text.substring(6);
//				kickUser(textMessage, kick);
//			}else{
//				controller.updateChat(dateFormat.format(date) + " - " + textMessage.username + ": " + textMessage.text + "\n");
//			}
//		}
//	}
	
//	public void inviteUser(ChatResponse chatResponse){
//		date = new Date();
//		if (chatResponse.response.equals("available")){
//			model.addParticipant(chatResponse.invite);
//			//controller.updateChat(dateFormat.format(date) + " - " + chatResponse.invite + " was added to the conversation by " + chatResponse.sender + "\n");
//			controller.systemChat(dateFormat.format(date) + " - " + chatResponse.invite + " was added to the conversation by " + chatResponse.sender + "\n");
//			
//			this.chatTitle = model.getParticipants().toString();
//			ArrayList<String> otherParticipants = new ArrayList<String>();
//			otherParticipants.addAll(model.getParticipants());
//			otherParticipants.remove(player.getUsername());
//			if (otherParticipants.isEmpty()){
//				this.chatTitle = "Yourself";
//			} else {
//				this.chatTitle = otherParticipants.toString();
//			}			
//			window.setWindowTitle("Chatting with: " + this.chatTitle);
//			
//			int newChatID = model.getParticipants().hashCode();
//			currentChats.put(newChatID, currentChats.get(chatResponse.chatID));
//		} else {
//			if (chatResponse.sender.equals(player.getUsername())){
//				controller.updateChat(dateFormat.format(date) + " - " + "Unable to add " + chatResponse.invite + ", user not found.\n");
//			}
//		}
//	}
//	
//	public void kickUser(TextMessage textMessage, String kick){
//		if (player.getUsername().equals(kick)){
//			controller.updateChat(dateFormat.format(date) + " - " + " You were kicked from the conversation.\n");
//			currentChats.remove(textMessage.chatID);				
//			ArrayList<String> participants = model.getParticipants();
//			for (String participant : participants){
//				model.removeParticipant(participant);
//			}
//			window.sendButton.setEnabled(false);
//		} else {
//			if (model.getParticipants().contains(kick)){
//				model.removeParticipant(kick);		
//				controller.updateChat(dateFormat.format(date) + " - " + kick + " was kicked from the conversation by " + textMessage.username + "\n");
//				currentChats.put(model.getParticipants().hashCode(), currentChats.get(textMessage.chatID));
//				
//				//currentChats.remove()
//				
//				this.chatTitle = model.getParticipants().toString();
//				ArrayList<String> otherParticipants = new ArrayList<String>();
//				otherParticipants.addAll(model.getParticipants());
//				otherParticipants.remove(player.getUsername());
//				if (otherParticipants.isEmpty()){
//					this.chatTitle = "Yourself";
//				} else {
//					this.chatTitle = otherParticipants.toString();
//				}
//				window.setWindowTitle("Chatting with: " + this.chatTitle);
//			} else {
//				if (textMessage.username.equals(player.getUsername())){
//					controller.updateChat(dateFormat.format(date) + " - " + "Unable to kick " + kick + ", user not found.\n");
//				}
//			}
//		}
//	}

