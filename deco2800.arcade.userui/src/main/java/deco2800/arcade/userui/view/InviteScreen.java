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
import javax.swing.JTextArea;
import javax.swing.JTextField;

import deco2800.arcade.userui.Model;
import net.miginfocom.swing.MigLayout;

public class InviteScreen extends JFrame{
	
	/**
	 * The view class for Adding a Friend
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
	private ImageIcon save, savehover;
	
	//Declare Fonts
	Font blackbold = new Font("Verdana", Font.BOLD, 16);

	public InviteScreen(Model model) throws HeadlessException{
		
		super("Invites");
		
		this.model = model;
		
		/*
		 * Create Image Icons
		 */		
		save = new ImageIcon("assets/images/save.png");
		savehover = new ImageIcon("assets/images/savehover.png");
	
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
		
		invites = new JTextArea();
		invites.setEditable(false);
		invites.setBackground(Color.white);
		
		contentpanel = new JPanel(new MigLayout());
		contentpanel.setOpaque(false);

		contentpanel.add(invites, "width :400px, height :200px");
		
	}
	
	public void addtextpanel(){
		
	    usernamefield = new JTextField("", 20);
	    username = new JLabel("User Name");
	
		textpanel = new JPanel(new MigLayout());
		textpanel.setOpaque(false);
		
		textpanel.add(username);
		textpanel.add(usernamefield);
		
	}
	
	/**
	 * 	Adds the backbutton and addfriendbutton
	 */
	public void addactionpanel(){
		
		acceptbutton = new JButton();
		acceptbutton.setBorder(BorderFactory.createEmptyBorder());
	    acceptbutton.setContentAreaFilled(false);
	    acceptbutton.setIcon(save);
	    acceptbutton.setRolloverIcon(savehover);

		actionpanel = new JPanel(new MigLayout());
		actionpanel.setOpaque(false);
	    actionpanel.add(acceptbutton,"gap after 30px");
		actionpanel.add(acceptbutton);
	     		
	}

	/**
	 * Listener for the backbutton
	 * @param listenForCancelButton
	 */
	public void addAcceptListener(ActionListener listenForAcceptButton){
		
		acceptbutton.addActionListener(listenForAcceptButton);
	
	}
	
}



