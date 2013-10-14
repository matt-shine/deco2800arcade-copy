package deco2800.arcade.userui.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ButtonModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import deco2800.arcade.userui.Model;
import net.miginfocom.swing.MigLayout;

public class RemoveFriendScreen extends JFrame{
	
	/**
	 * The view class for the status page
	 */
	
	private Model model;
	
	//Declare JPanel and ImagePanels
	private JPanel parentContainer;	
	private JPanel titlepanel;
	private JPanel contentpanel;
	private JPanel actionpanel;
	
	//Declare Buttons
	private JButton removefriendbutton;
	
	//Declare Text Field
	private JTextField usernamefield;
	private JLabel username;
	
	//Declare Fonts
	Font blackbold = new Font("Verdana", Font.BOLD, 16);

	public RemoveFriendScreen(Model model) throws HeadlessException{
		
		super("Remove Friend");
		
		this.model = model;
	
	    /*Add panels to main panel	
	     *                
	     */
		
		addtitlepanel();
		addcontentpanel();
		addactionpanel();
	    
	    parentContainer = new JPanel(new MigLayout());
	    parentContainer.add(titlepanel,"wrap, gap right 30px");
	    parentContainer.add(contentpanel,"wrap");
	    parentContainer.add(actionpanel);	    
		add(parentContainer);
	
		/*Set the  view window constraints
		 * 
		 */
		setSize(400,250);
		setLocationRelativeTo(null);
		setVisible(true);
		setResizable(false);
		setAlwaysOnTop(true);
		
	}
	
	/**
	 * 
	 */
	public void addtitlepanel(){

		JLabel title = new JLabel("Remove a Friend");
		title.setFont(blackbold);
		
		titlepanel = new JPanel(new MigLayout());
		titlepanel.add(title);		
		
	}
	
	/**
	 * 
	 */
	public void addcontentpanel(){

	    usernamefield = new JTextField("", 20);	    
	    username = new JLabel("User Name");
		
		contentpanel = new JPanel(new MigLayout());
		contentpanel.add(username);
		contentpanel.add(usernamefield,"wrap");
		
	}
	
	/**
	 * 
	 */
	public void addactionpanel(){
		
		removefriendbutton = new JButton("Remove");

		actionpanel = new JPanel(new MigLayout());
		actionpanel.add(removefriendbutton);
	     		
	}

	public void addRemoveFriendListener(ActionListener listenForRemoveFriendButton){
		
		removefriendbutton.addActionListener(listenForRemoveFriendButton);
	
	}

	
}



