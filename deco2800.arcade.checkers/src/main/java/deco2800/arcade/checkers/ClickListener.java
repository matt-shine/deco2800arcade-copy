package deco2800.arcade.checkers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ClickListener implements ActionListener{

	@Override
	public void actionPerformed(ActionEvent e) {
		Object square = e.getSource();
		
		System.out.println("a button has been pushed");
		
	}

}
