package deco2800.arcade.observer;

import java.util.Observable;
import java.util.Observer;


import deco2800.arcade.model.Blocked;
import deco2800.arcade.model.FriendInvites;
import deco2800.arcade.model.Friends;
import deco2800.arcade.model.Games;
import deco2800.arcade.model.Player;


import deco2800.arcade.model.*;

// import deco2800.arcade.server.*;


public class PlayerObserver implements Observer{

	@Override
	public void update(Observable observable, Object object) {
		
		
		/*
		 * Checking if observable is an instance of Player
		 */
		if (observable instanceof Player) {
			
			if (object instanceof Friends) {
				// send object to database
				
			} else if (object instanceof FriendInvites) {
				// send object to database
				
			} else if (object instanceof Games) {
				// send object to database
				
			} else if (object instanceof Blocked) {
				// send object to database
				
			} else if (object instanceof Field){
				Field field = (Field)object;
				String value = field.getValue();
				switch(field.getID()){
				case Player.USERNAME_ID:
					//stuff
				case Player.NAME_ID:
					//stuff
				case Player.EMAIL_ID:
					//stuff
				case Player.PROGRAM_ID:
					//stuf
				case Player.BIO_ID:
					//stuff
	
				}
				
			}
		}
		
	}
	
	
	
	
	
	
	
	
}
