package deco2800.arcade.client.startup;

import java.awt.Component;

import javax.swing.JOptionPane;

public class UserNameDialog {

	/**
	 * Shows a textbox to get a username
	 * @param component
	 * @returns String username
	 */
	public static String getUsername(Component component){
		String result = null;
		
		while (result == null || result.trim().isEmpty()){
			result = JOptionPane.showInputDialog(component, "Enter Username");
		}
		
		return result;
	}
}
