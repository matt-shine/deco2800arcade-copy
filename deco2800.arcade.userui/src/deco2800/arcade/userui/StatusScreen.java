package deco2800.arcade.userui;

import java.awt.Color;
import java.awt.Font;
import java.awt.HeadlessException;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import net.miginfocom.swing.MigLayout;

public class StatusScreen extends JFrame{
	
	/**
	 * The view class for the status
	 */
	
	private Model model;
	
	//Declare JPanel and ImagePanels
	private JPanel parentContainer;	
	
	//Declare Buttons
	private JRadioButton awaybutton;
	private JRadioButton busybutton;
	private JRadioButton onlinebutton;
	private JRadioButton offlinebutton;
	private ButtonGroup status;
	private JButton savebutton;
	private JButton cancelbutton;
	
	//Declare Images 
	private ImageIcon piconline, picoffline, picaway, picbusy;
	
	//Declare Fonts
	Font blackbold = new Font("Verdana", Font.BOLD, 16);
	Font blacknormal = new Font("Verdana", Font.PLAIN, 14);
	Font blacksmall = new Font("Verdana", Font.PLAIN, 12);
	Font blacklink = new Font("Verdana", Font.PLAIN, 15);
	Font linkbold = new Font("Verdana", Font.BOLD, 14);
	Font sidebold = new Font("Verdana", Font.BOLD, 12);

		
	public StatusScreen(Model model) throws HeadlessException{
		
		super("Player Status");
		
		this.model = model;

		/*  Create Image Icons
		 * 
		 */		
		piconline = new ImageIcon("assets/images/online.png");
		picoffline = new ImageIcon("assets/images/offline.png");
				
		/*
		 *  Create all Panels 
		 */
		JPanel titlepanel = new JPanel(new MigLayout());
		JPanel contentpanel = new JPanel(new MigLayout());
		JPanel savepanel = new JPanel(new MigLayout());
		
		JLabel title = new JLabel("Player Status");
		title.setFont(blackbold);
		
		JLabel away = new JLabel();
		JLabel busy = new JLabel();
		JLabel online = new JLabel();
		JLabel offline = new JLabel();
		away.setIcon(picoffline);
		busy.setIcon(picoffline);
		online.setIcon(piconline);
		offline.setIcon(picoffline);
		
		awaybutton = new JRadioButton("", true);
		busybutton = new JRadioButton("", false);
		onlinebutton = new JRadioButton("", false);
		offlinebutton = new JRadioButton("", false);
		savebutton = new JButton("Save");
		cancelbutton = new JButton("Cancel");
		
		titlepanel.add(title,"gap left 50px");		
		contentpanel.add(awaybutton);
		contentpanel.add(away,"wrap, gap left 15px");
		contentpanel.add(busybutton);
		contentpanel.add(busy,"wrap, gap left 15px");
		contentpanel.add(onlinebutton);
		contentpanel.add(online,"wrap, gap left 15px");
		contentpanel.add(offlinebutton);
	    contentpanel.add(offline,"wrap, gap left 15px");
	    
	    savepanel.add(savebutton);
	    savepanel.add(cancelbutton);
	    
	    status = new ButtonGroup();
	    status.add(awaybutton);
	    status.add(busybutton);
	    status.add(offlinebutton);
	    status.add(onlinebutton);
	        
	    /*Add panels to Main Panel	
	     *                
	     */
	    
	    parentContainer = new JPanel(new MigLayout());
	    parentContainer.add(titlepanel,"wrap");
	    parentContainer.add(contentpanel,"gap left 30px, wrap");
	    parentContainer.add(savepanel,"gap left 30px");
	    
		add(parentContainer);
	
		/*Set the  view window constraints
		 * 
		 */
		setSize(300,350);
		setLocationRelativeTo(null);
		setVisible(true);
		setResizable(false);		
	}
	


}



