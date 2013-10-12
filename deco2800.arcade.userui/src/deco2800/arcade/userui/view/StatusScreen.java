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

import deco2800.arcade.userui.Model;
import net.miginfocom.swing.MigLayout;

public class StatusScreen extends JFrame{
	
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
	private JRadioButton awaybutton;
	private JRadioButton busybutton;
	private JRadioButton onlinebutton;
	private JRadioButton offlinebutton;
	private ButtonGroup status;
	private JButton savebutton;
	private JButton cancelbutton;
	
	//Declare Images 
	private ImageIcon piconline, picoffline, picaway, picbusy, 
	save, savehover, cancel, cancelhover;
	
	//Declare Fonts
	Font blackbold = new Font("Verdana", Font.BOLD, 16);

	public StatusScreen(Model model) throws HeadlessException{
		
		super("Player Status");
		
		this.model = model;
	
	    /*Add panels to main panel	
	     *                
	     */
		
		addtitlepanel();
		addcontentpanel();
		addactionpanel();
	    
	    parentContainer = new JPanel(new MigLayout());
	    parentContainer.setBackground(Color.PINK);
	    parentContainer.add(titlepanel,"wrap");
	    parentContainer.add(contentpanel,"gap left 30px, wrap");
	    parentContainer.add(actionpanel,"gap left 50px");	    
		add(parentContainer);
	
		/*Set the  view window constraints
		 * 
		 */
		setSize(300,380);
		setLocationRelativeTo(null);
		setVisible(true);
		setResizable(false);
		setAlwaysOnTop(true);
		
	}
	
	/**
	 *  Adds the title label
	 */
	public void addtitlepanel(){

		JLabel title = new JLabel("Player Status");
		title.setFont(blackbold);
		
		titlepanel = new JPanel(new MigLayout());
		titlepanel.setOpaque(false);
		titlepanel.add(title,"gap left 50px");		
		
	}
	
	/**
	 *  Adds all status buttons
	 */
	public void addcontentpanel(){
		
		piconline = new ImageIcon("assets/images/online.png");
		picoffline = new ImageIcon("assets/images/offline.png");
		picaway = new ImageIcon("assets/images/away.png");
		picbusy = new ImageIcon("assets/images/busy.png");
				
		JLabel away = new JLabel();
		JLabel busy = new JLabel();
		JLabel online = new JLabel();
		JLabel offline = new JLabel();
		away.setIcon(picaway);
		busy.setIcon(picbusy);
		online.setIcon(piconline);
		offline.setIcon(picoffline);	
		onlinebutton = new JRadioButton("", true);
		offlinebutton = new JRadioButton("", false);
		awaybutton = new JRadioButton("", false);
		busybutton = new JRadioButton("", false);
		
		contentpanel = new JPanel(new MigLayout());
		contentpanel.setOpaque(false);
		contentpanel.add(onlinebutton);
		contentpanel.add(online,"wrap, gap left 15px");
		contentpanel.add(offlinebutton);
	    contentpanel.add(offline,"wrap, gap left 15px");
		contentpanel.add(awaybutton);
		contentpanel.add(away,"wrap, gap left 15px");
		contentpanel.add(busybutton);
		contentpanel.add(busy,"wrap, gap left 15px");
	  
	    status = new ButtonGroup();
	    status.add(offlinebutton);
	    status.add(onlinebutton);
	    status.add(awaybutton);
	    status.add(busybutton);
		
	}
	
	/**
	 *  Adds the save and cancel buttons
	 */
	public void addactionpanel(){
		
		save = new ImageIcon("assets/images/save.png");
		savehover = new ImageIcon("assets/images/savehover.png");
		cancel = new ImageIcon("assets/images/cancel.png");
		cancelhover = new ImageIcon("assets/images/cancelhover.png");
		
		savebutton = new JButton();
		savebutton.setBorder(BorderFactory.createEmptyBorder());
	    savebutton.setContentAreaFilled(false);
	    savebutton.setIcon(save);
	    savebutton.setRolloverIcon(savehover);
		cancelbutton = new JButton();
		cancelbutton.setBorder(BorderFactory.createEmptyBorder());
	    cancelbutton.setContentAreaFilled(false);
	    cancelbutton.setIcon(cancel);
	    cancelbutton.setRolloverIcon(cancelhover);

		actionpanel = new JPanel(new MigLayout());
		actionpanel.setOpaque(false);
	    actionpanel.add(savebutton,"wrap, gap bottom 5px");
	    actionpanel.add(cancelbutton,"gap bottom 5px");
	     		
	}

	/**
	 * Listener for the save button
	 * @param listenForSaveButton
	 */
	public void addSaveListener(ActionListener listenForSaveButton){
		
		savebutton.addActionListener(listenForSaveButton);
	
	}
	
	/**
	 * Listener for the cancel button
	 * @param listenForCancelButton
	 */
	public void addCancelListener(ActionListener listenForCancelButton){
		
		cancelbutton.addActionListener(listenForCancelButton);
	
	}
	
	/**
	 * 	Updates the Model.status to reflect changes made in view
	 */
	public void getStatusSelection(){
		
		if (awaybutton.isSelected() == true){
			model.setStatus("away");
			model.setStatusIcon(picaway);
		}
		if (busybutton.isSelected() == true){
			model.setStatus("busy");
			model.setStatusIcon(picbusy);
		}
		if (onlinebutton.isSelected() == true){
			model.setStatus("online");
			model.setStatusIcon(piconline);
		}
		if (offlinebutton.isSelected() == true){
			model.setStatus("offline");
			model.setStatusIcon(picoffline);
		}
		
	}
	
	/*
	 *  Classes used for Testing 
	 */
	
	/**
	 *  Simulates onlinebutton click
	 */
	public void onlineclick(){
		
		onlinebutton.doClick();
		
	}
	
	/**
	 *  Simulates offlinebutton click
	 */
	public void offlineclick(){
		
		offlinebutton.doClick();
		
	}
	
	/**
	 *  Simulates awaybutton click
	 */
	public void awayclick(){
		
		awaybutton.doClick();
		
	}
	
	/**
	 *  Simulates busybutton click
	 */
	public void busyclick(){
		
		busybutton.doClick();
		
	}
	
	/**
	 *  Simulates savebutton click
	 */
	public void saveclick(){
		
		savebutton.doClick();
		
	}

}



