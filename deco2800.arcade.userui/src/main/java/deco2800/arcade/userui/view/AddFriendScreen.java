package deco2800.arcade.userui.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import deco2800.arcade.userui.Model;
import net.miginfocom.swing.MigLayout;

/**
 * The View Class for Adding a Friend
 *
 */
public class AddFriendScreen extends JFrame{
	
	private Model model;
	
	//Declare JPanel and ImagePanels
	private JPanel parentContainer;	
	private JPanel titlepanel;
	private JPanel contentpanel;
	private JPanel actionpanel;
	
	//Declare Buttons
	private JButton addfriendbutton;
	private JButton backbutton;
	
	//Declare Text Field
	private JTextField usernamefield, realnamefield;
	private JLabel username, realname;
	
	//Declare Images 
	private ImageIcon addfriend, addfriendhover, back, backhover;
	
	//Declare Fonts
	Font blackbold = new Font("Verdana", Font.BOLD, 16);

	public AddFriendScreen(Model model) throws HeadlessException{
		
		super("Add Friend");
		
		this.model = model;
		
		/*
		 * Create Image Icons
		 */		
		addfriend = new ImageIcon("assets/images/add.png");
		addfriendhover = new ImageIcon("assets/images/addhover.png");
		back = new ImageIcon("assets/images/back.png");
		backhover = new ImageIcon("assets/images/backhover.png");
	
	    /*Add panels to main panel	
	     *                
	     */
		
		addtitlepanel();
		addcontentpanel();
		addactionpanel();
	    
	    parentContainer = new JPanel(new MigLayout());
	    parentContainer.setBackground(Color.orange);
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
	 * 	Adds title label
	 */
	public void addtitlepanel(){

		JLabel title = new JLabel("Add a Friend");
		title.setFont(blackbold);
		
		titlepanel = new JPanel(new MigLayout());
		titlepanel.setOpaque(false);
		titlepanel.add(title);		
		
	}
	
	/**
	 * 	Adds the labels and textfields
	 */
	public void addcontentpanel(){

	    usernamefield = new JTextField("", 20);
	    realnamefield = new JTextField("", 20);    
	    username = new JLabel("User Name");
	    realname = new JLabel("Real Name");
	
		contentpanel = new JPanel(new MigLayout());
		contentpanel.setOpaque(false);
		
		contentpanel.add(username);
		contentpanel.add(usernamefield,"wrap");
		contentpanel.add(realname);
		contentpanel.add(realnamefield,"wrap");
		
	}
	
	/**
	 * 	Adds the backbutton and addfriendbutton
	 */
	public void addactionpanel(){
		
		backbutton = new JButton();
		backbutton.setBorder(BorderFactory.createEmptyBorder());
	    backbutton.setContentAreaFilled(false);
	    backbutton.setIcon(back);
	    backbutton.setRolloverIcon(backhover);
	    
		addfriendbutton = new JButton();
	    addfriendbutton.setBorder(BorderFactory.createEmptyBorder());
	    addfriendbutton.setContentAreaFilled(false);
	    addfriendbutton.setIcon(addfriend);
	    addfriendbutton.setRolloverIcon(addfriendhover);

		actionpanel = new JPanel(new MigLayout());
		actionpanel.setOpaque(false);
	    actionpanel.add(addfriendbutton,"gap after 30px");
		actionpanel.add(backbutton);
	     		
	}

	/**
	 * Listener for the addfriendbutton 
	 * @param listenForAddFriendButton
	 */
	public void addFriendListener(ActionListener listenForAddFriendButton){
		
		addfriendbutton.addActionListener(listenForAddFriendButton);
	
	}
	
	/**
	 * Listener for the backbutton
	 * @param listenForCancelButton
	 */
	public void addCancelListener(ActionListener listenForCancelButton){
		
		backbutton.addActionListener(listenForCancelButton);
	
	}
	
}



