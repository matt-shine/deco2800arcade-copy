package deco2800.arcade.deerforest.GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import deco2800.arcade.deerforest.models.gameControl.GameSystem;

public class MainGameController {

	private GameSystem model;
	private MainGameGUI view;
	
	public MainGameController(GameSystem model, MainGameGUI view) {
		this.model = model;
		this.view = view;
		
	}
	
	/**
	 * Action listener for each card in hand / field
	 * Upon calling checks where the card is and gives the player a list
	 * of options that the card can do (ie activate / summon for main phase, 
	 * attack for battle)
	 * 
	 * @author Segmelsian
	 *
	 */
	private class CardActionListner implements ActionListener {
		public void actionPerformed(ActionEvent e){
			
		}
	}
	
	
}
