package deco2800.arcade.client.startup;

import java.awt.Component;

import javax.swing.JOptionPane;

public class GameSelector {

	public static Object selectGame(Component component, Object[] options){
		return selectGame(component, options, 0);
	}
	
	public static Object selectGame(Component component, Object[] options, int selected){
		 return JOptionPane.showInputDialog(component, "Select a game", "Cancel", JOptionPane.PLAIN_MESSAGE, null, options, options.length > selected ? options[selected] : null);
	}
}
