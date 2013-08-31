package deco2800.arcade.communication;

import java.util.ArrayList;


public class CommunicationModel {
	
	private ArrayList<String> participants;
	
	public CommunicationModel(ArrayList<String> requestParticipants){
		participants = new ArrayList<String>();
		for (String participant : requestParticipants){
			participants.add(participant);
		}
	}
	
	public void addParticipant(String username){
		participants.add(username);
	}
	
	public void removeParticipant(String username){
		participants.remove(username);
	}
		
	public String getParticipant(String username){
		return participants.get(participants.indexOf(username));
	}
	
	public ArrayList<String> getParticipants(){
		return new ArrayList<String>(participants);
	}

}
