package deco2800.arcade.userui.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.event.ActionListener;

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
	private ImageIcon piconline, picoffline, picaway, picbusy;
	
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
	    parentContainer.add(titlepanel,"wrap");
	    parentContainer.add(contentpanel,"gap left 30px, wrap");
	    parentContainer.add(actionpanel,"gap left 30px");	    
		add(parentContainer);
	
		/*Set the  view window constraints
		 * 
		 */
		setSize(300,350);
		setLocationRelativeTo(null);
		setVisible(true);
		setResizable(false);
		setAlwaysOnTop(true);
		
	}
	
	/**
	 * 
	 */
	public void addtitlepanel(){

		JLabel title = new JLabel("Player Status");
		title.setFont(blackbold);
		
		titlepanel = new JPanel(new MigLayout());
		titlepanel.add(title,"gap left 50px");		
		
	}
	
	/**
	 * 
	 */
	public void addcontentpanel(){
		
		piconline = new ImageIcon("assets/images/online.png");
		picoffline = new ImageIcon("assets/images/offline.png");
				
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
		
		contentpanel = new JPanel(new MigLayout());
		contentpanel.add(awaybutton);
		contentpanel.add(away,"wrap, gap left 15px");
		contentpanel.add(busybutton);
		contentpanel.add(busy,"wrap, gap left 15px");
		contentpanel.add(onlinebutton);
		contentpanel.add(online,"wrap, gap left 15px");
		contentpanel.add(offlinebutton);
	    contentpanel.add(offline,"wrap, gap left 15px");
	  
	    status = new ButtonGroup();
	    status.add(awaybutton);
	    status.add(busybutton);
	    status.add(offlinebutton);
	    status.add(onlinebutton);
		
	}
	
	/**
	 * 
	 */
	public void addactionpanel(){
		
		savebutton = new JButton("Save");
		cancelbutton = new JButton("Cancel");

		actionpanel = new JPanel(new MigLayout());
	    actionpanel.add(savebutton);
	    actionpanel.add(cancelbutton);
	     		
	}

	public void addSaveListener(ActionListener listenForSaveButton){
		
		savebutton.addActionListener(listenForSaveButton);
	
	}
	
	public void addCancelListener(ActionListener listenForCancelButton){
		
		cancelbutton.addActionListener(listenForCancelButton);
	
	}
	
	public void getStatusSelection(){
		
		System.out.println(status.getSelection().toString());
		
	}

}



