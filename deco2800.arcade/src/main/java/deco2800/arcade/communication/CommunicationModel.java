package deco2800.arcade.communication;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class CommunicationModel {
	
	private ArrayList<String> participants;
	
	public CommunicationModel(String chatID){
		participants = new ArrayList<String>();
		
		List<String> participantString = Arrays.asList(chatID.split(","));
		
		for (String participant : participantString){
			participants.add(participant);
		}
		
	}
	
	public void addParticipant(String username){
		participants.add(username);
	}
	
	public void removeParticipant(String username){
		participants.remove(username);
	}
	
	public ArrayList<String> getParticipants(){
		return new ArrayList<String>(participants);
	}

}
