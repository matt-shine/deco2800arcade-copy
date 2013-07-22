package deco2800.arcade.client.login;

import java.awt.Component;

import javax.swing.JOptionPane;

public class UserNameDialog {

	public static String getUsername(Component component){
		String result = null;
		
		while (result == null || result.trim().isEmpty()){
			result = JOptionPane.showInputDialog(component, "Enter Username");
		}
		
		return result;
	}
}
