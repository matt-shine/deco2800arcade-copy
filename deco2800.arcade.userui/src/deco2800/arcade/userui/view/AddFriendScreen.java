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

public class AddFriendScreen extends JFrame{
	
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
	private JButton donebutton;
	private JButton addfriendbutton;
	private JButton findbutton;
	
	//Declare Text Field
	private JTextField usernamefield, realnamefield;
	private JLabel username, realname;
	
	//Declare Images 
	private ImageIcon addfriend;
	
	//Declare Fonts
	Font blackbold = new Font("Verdana", Font.BOLD, 16);

	public AddFriendScreen(Model model) throws HeadlessException{
		
		super("Add Friend");
		
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
		setSize(400,300);
		setLocationRelativeTo(null);
		setVisible(true);
		setResizable(false);
		setAlwaysOnTop(true);
		
	}
	
	/**
	 * 
	 */
	public void addtitlepanel(){

		JLabel title = new JLabel("Add a Friend");
		title.setFont(blackbold);
		
		titlepanel = new JPanel(new MigLayout());
		titlepanel.add(title);		
		
	}
	
	/**
	 * 
	 */
	public void addcontentpanel(){
		
		addfriend = new ImageIcon("assets/images/add_friend.png");

	    addfriendbutton = new JButton(addfriend);
	    addfriendbutton.setBorder(BorderFactory.createEmptyBorder());
	    addfriendbutton.setContentAreaFilled(false);
	    
	    usernamefield = new JTextField("", 20);
	    realnamefield = new JTextField("", 20);
	    
	    username = new JLabel("User Name");
	    realname = new JLabel("Real Name");
		
		contentpanel = new JPanel(new MigLayout());
		
		contentpanel.add(username);
		contentpanel.add(usernamefield,"wrap");
		contentpanel.add(realname);
		contentpanel.add(realnamefield,"wrap");
		
	}
	
	/**
	 * 
	 */
	public void addactionpanel(){
		
		findbutton = new JButton("Find");
		addfriendbutton = new JButton("Add");

		actionpanel = new JPanel(new MigLayout());
		actionpanel.add(findbutton);
	    actionpanel.add(addfriendbutton);
	     		
	}

	public void addFriendListener(ActionListener listenForAddFriendButton){
		
		addfriendbutton.addActionListener(listenForAddFriendButton);
	
	}
	
	public void addFindListener(ActionListener listenForFindButton){
		
		findbutton.addActionListener(listenForFindButton);
	
	}
	
}



