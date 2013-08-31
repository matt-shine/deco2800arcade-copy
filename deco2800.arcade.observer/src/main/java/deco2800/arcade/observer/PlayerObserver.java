package deco2800.arcade.observer;

import java.util.Observable;
import java.util.Observer;

import deco2800.arcade.model.Player;
// import deco2800.arcade.server.*;

public class PlayerObserver implements Observer{

	@Override
	public void update(Observable observable, Object object) {
		
		/*
		 * Checking if observable is an instance of Player
		 */
		if (observable instanceof Player) {
			
		}
		
	}
	
	
	
	
	
	
	
	
}
