package deco2800.arcade.guesstheword.GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GuessTheWordController {
	
	private GuessTheWordGUI gui;
	
	public GuessTheWordController(GuessTheWordGUI g) {
		gui = g;
		
		g.achieveButtonListener(new AchieveListener());
		g.loginButtonListener(new LoginListener());
		g.helpButtonListener(new HelpListener());
	}
	
	private class AchieveListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	private class LoginListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	private class HelpListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			
		}
		
	}

}
