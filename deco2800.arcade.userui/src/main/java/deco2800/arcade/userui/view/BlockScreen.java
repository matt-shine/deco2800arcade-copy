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
import javax.swing.JTextArea;
import javax.swing.JTextField;

import deco2800.arcade.userui.Model;
import net.miginfocom.swing.MigLayout;

public class BlockScreen extends JFrame{
	
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
	private JButton blockbutton, unblockbutton;
	
	//Declare text
	private JTextArea invites;
	
	//Declare Text Field
	private JTextField usernamefield;
	private JLabel username;
	
	//Declare Images 
	private ImageIcon add, addhover, remove, removehover;
	
	//Declare Fonts
	Font blackbold = new Font("Verdana", Font.BOLD, 16);

	public BlockScreen(Model model) throws HeadlessException{
		
		super("Blocked List");
		
		this.model = model;
		
		/*
		 * Create Image Icons
		 */		
		add = new ImageIcon("assets/images/add.png");
		addhover = new ImageIcon("assets/images/addhover.png");
		remove = new ImageIcon("assets/images/remove.png");
		removehover = new ImageIcon("assets/images/removehover.png");
	
	    /*Add panels to main panel	
	     *                
	     */
		addtitlepanel();
		addcontentpanel();
		addtextpanel();
		addactionpanel();
	    
	    parentContainer = new JPanel(new MigLayout());
	    parentContainer.setBackground(Color.pink);
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

		JLabel title = new JLabel("Block List");
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
		
		blockbutton = new JButton();
		blockbutton.setBorder(BorderFactory.createEmptyBorder());
	    blockbutton.setContentAreaFilled(false);
	    blockbutton.setIcon(add);
	    blockbutton.setRolloverIcon(addhover);
	    unblockbutton = new JButton();
		unblockbutton.setBorder(BorderFactory.createEmptyBorder());
	    unblockbutton.setContentAreaFilled(false);
	    unblockbutton.setIcon(remove);
	    unblockbutton.setRolloverIcon(removehover);

		actionpanel = new JPanel(new MigLayout());
		actionpanel.setOpaque(false);
	    actionpanel.add(blockbutton,"gap after 30px");
		actionpanel.add(unblockbutton);
	     		
	}

	/**
	 * Listener for the blockbutton
	 * @param listenForBlockButton
	 */
	public void addBlockListener(ActionListener listenForBlockButton){
		
		blockbutton.addActionListener(listenForBlockButton);
	
	}
	
	/**
	 * Listener for the declinebutton
	 * @param listenForDeclineButton
	 */
	public void addUnblockListener(ActionListener listenForUnblockButton){
		
		unblockbutton.addActionListener(listenForUnblockButton);
	
	}
	
}



