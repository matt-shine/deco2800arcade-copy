package deco2800.arcade.communication;

import java.util.ArrayList;

public class CommunicationModel {
	
	//FIXME ArrayList or List?
	private ArrayList<String> participants;
	
	public CommunicationModel(String username){
		participants = new ArrayList<String>();
		participants.add(username);
	}
	
	public void addParticipant(String username){
		participants.add(username);
	}
	
	public ArrayList<String> getParticipants(){
		return new ArrayList<String>(participants);
	}

}
