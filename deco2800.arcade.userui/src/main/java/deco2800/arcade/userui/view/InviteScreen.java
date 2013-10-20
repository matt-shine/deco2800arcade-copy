package deco2800.arcade.userui.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import deco2800.arcade.model.Player;
import deco2800.arcade.userui.Model;
import net.miginfocom.swing.MigLayout;

public class InviteScreen extends JFrame{
	
	/**
	 * The view class for Invites
	 */
	
	private Model model;
	
	//Declare JPanel and ImagePanels
	private JPanel parentContainer;	
	private JPanel titlepanel;
	private JPanel contentpanel;
	private JPanel actionpanel;
	private JPanel textpanel;
	
	//Declare Buttons
	private JButton acceptbutton, declinebutton;
	
	//Declare text
	private JTextArea invites;
	
	//Declare Text Field
	private JTextField usernamefield;
	private JLabel username;
	
	//Declare Images 
	private ImageIcon accept, accepthover, decline, declinehover;
	
	//Declare Fonts
	Font blackbold = new Font("Verdana", Font.BOLD, 16);

	public InviteScreen(Model model) throws HeadlessException{
		
		super("Invites");
		
		this.model = model;
		
		/*
		 * Create Image Icons
		 */		
		accept = new ImageIcon("assets/images/accept.png");
		accepthover = new ImageIcon("assets/images/accepthover.png");
		decline = new ImageIcon("assets/images/decline.png");
		declinehover = new ImageIcon("assets/images/declinehover.png");
	
	    /*Add panels to main panel	
	     *                
	     */
		addtitlepanel();
		addcontentpanel();
		addtextpanel();
		addactionpanel();
	    
	    parentContainer = new JPanel(new MigLayout());
	    parentContainer.setBackground(Color.orange);
	    parentContainer.add(titlepanel,"wrap, gap right 30px");
	    parentContainer.add(contentpanel,"wrap");
	    parentContainer.add(textpanel,"wrap");
	    parentContainer.add(actionpanel);	    
		add(parentContainer);
	
		/*Set the  view window constraints
		 * 
		 */
		setSize(400,500);
		setLocationRelativeTo(null);
		setVisible(true);
		setResizable(false);
		setAlwaysOnTop(true);
		
	}
	
	/**
	 * 	Adds title label
	 */
	public void addtitlepanel(){

		JLabel title = new JLabel("Invites");
		title.setFont(blackbold);
		
		titlepanel = new JPanel(new MigLayout());
		titlepanel.setOpaque(false);
		titlepanel.add(title);		
		
	}
	
	/**
	 * 	Adds the labels and textfields
	 */
	public void addcontentpanel(){
		
		invites = new JTextArea();
		invites.setEditable(false);
		invites.setBackground(Color.white);
		
		contentpanel = new JPanel(new MigLayout());
		contentpanel.setOpaque(false);

		contentpanel.add(invites, "width :400px, height :200px");
		
	}
	
	/**
	 *  Adds the username textfield and label
	 */
	public void addtextpanel(){
		
	    usernamefield = new JTextField("", 20);
	    username = new JLabel("User Name");
	
		textpanel = new JPanel(new MigLayout());
		textpanel.setOpaque(false);
		
		textpanel.add(username);
		textpanel.add(usernamefield);
		
	}
	
	/**
	 * 	Adds the accept and decline request buttons
	 */
	public void addactionpanel(){
		
		acceptbutton = new JButton();
		acceptbutton.setBorder(BorderFactory.createEmptyBorder());
	    acceptbutton.setContentAreaFilled(false);
	    acceptbutton.setIcon(accept);
	    acceptbutton.setRolloverIcon(accepthover);
	    declinebutton = new JButton();
		declinebutton.setBorder(BorderFactory.createEmptyBorder());
	    declinebutton.setContentAreaFilled(false);
	    declinebutton.setIcon(decline);
	    declinebutton.setRolloverIcon(declinehover);

		actionpanel = new JPanel(new MigLayout());
		actionpanel.setOpaque(false);
	    actionpanel.add(acceptbutton,"gap after 30px");
		actionpanel.add(declinebutton);
	     		
	}

	/**
	 * Listener for the acceptbutton
	 * @param listenForAcceptButton
	 */
	public void addAcceptListener(ActionListener listenForAcceptButton){
		
		acceptbutton.addActionListener(listenForAcceptButton);
	
	}
	
	/**
	 * Listener for the declinebutton
	 * @param listenForDeclineButton
	 */
	public void addDeclineListener(ActionListener listenForDeclineButton){
		
		declinebutton.addActionListener(listenForDeclineButton);
	
	}
	
	/**
	 * Display list of invites
	 */
	public void displayinvites(){
		
		invites.setText(model.player.getInvites().toString());
		
	}

	/**
	 * Accept the friend invite, player gets added to friends list
	 * @param friend
	 */
	public void acceptinvite(Player friend){
		
		model.player.acceptFriendInvite(friend);
		invites.setText(model.player.getInvites().toString());
		
	}
	
	/**
	 * Reject the friend invite, player is remove from the list
	 * @param friend
	 */
	public void declineinvite(Player friend){
		
		model.player.removeInvite(friend);
		invites.setText(model.player.getInvites().toString());
		
	}
	
	/**
	 * Get the name of the user
	 */
	public void getname(){
		
		usernamefield.getText();
		
	}
	
}



