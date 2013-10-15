package deco2800.arcade.userui.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagLayout;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;

import javax.swing.JFrame;
import javax.swing.border.EmptyBorder;

import deco2800.arcade.userui.Model;

public class EditScreen extends JFrame{

	// Declare JPanels and ImagePanels for later use
	private JPanel parentContainer;
	private JPanel topContainer;
	private JPanel middleContainer;
	private JPanel bottomContainer;
	private JPanel titlepanel;
	private ImagePanel background;

	// Declare ImageIcons for later use
	private ImageIcon Pink_Box;
	private ImageIcon piccat;
	private ImageIcon picbrowsebutton;
	private ImageIcon picsavebutton;
	private ImageIcon piccancelbutton;
	private ImageIcon rolloverSave;
	private ImageIcon rolloverCancel;
	private ImageIcon rolloverBrowse;

	// Declare JButtons for later use
	private JButton browsebutton;
	private JButton cancelbutton;
	private JButton savebutton;

	// Declare JTextFields for later use
	private JTextField upload;
	private JTextField profile;
	private JTextField realname;
	private JTextArea aboutme;

	// Declare JLabels for later use
	private JLabel avatar;
	private JLabel editprofilebar;
	private JLabel profilenamebar;
	private JLabel realnamebar;
	private JLabel aboutmebar;

	// Declare model
	private Model m;

	Font big = new Font("Verdana", Font.BOLD, 26);
	Font normal = new Font("Verdana", Font.PLAIN, 15);

	/*
	 * The actual edit screen construction
	 */

	public EditScreen(Model m) throws HeadlessException {

		super("Edit Profile");

		// Call all the function that construct the edit screen
		addTitlePanel();
		addTopContainer();
		addMiddleContainer();
		addBottomContainer();
		addBackgroundContainer();
		addParentContainer();
		add(parentContainer);

		// Set the view window constraints
		setSize(400,480);
		setLocationRelativeTo(null);
		setVisible(true);
		setResizable(false);
		setAlwaysOnTop(true);

	}

	/*
	 * Add the menu panel
	 */
	public void addTitlePanel() {

		titlepanel = new JPanel(new MigLayout());
		editprofilebar = new JLabel("Edit Profile");
		editprofilebar.setForeground(Color.black);
		editprofilebar.setFont(big);
		titlepanel.add(editprofilebar, "dock north, wrap, align center, gapleft 110px");
		titlepanel.setBorder(BorderFactory.createEmptyBorder(0,0,30,0)); 
		titlepanel.setOpaque(false);

	}

	/*
	 * Add all the elements to the top container
	 */

	public void addTopContainer(){
		
		topContainer = new JPanel(new MigLayout());
		
		piccat = new ImageIcon("assets/images/profile_holder.png");
		picbrowsebutton = new ImageIcon("assets/images/browse.png");
		rolloverBrowse = new ImageIcon("assets/images/browsehover.png");
		
		avatar = new JLabel();
		avatar.setIcon(piccat);
				
		browsebutton = new JButton(picbrowsebutton);
	    browsebutton.setBorder(BorderFactory.createEmptyBorder());
	    browsebutton.setContentAreaFilled(false);
	    browsebutton.setRolloverIcon(rolloverBrowse);
		
		upload = new JTextField("Upload a new picture");
		
		topContainer.add(avatar, "dock west, gapright 10px");
		topContainer.add(upload, "dock north, gaptop 70px");
		topContainer.add(browsebutton, "dock north, wrap");
		topContainer.setOpaque(false);
		
	}

	/*
	 * Add all elements to the middle container
	 */
	public void addMiddleContainer(){
		
		middleContainer = new JPanel(new MigLayout());
		
		profilenamebar = new JLabel("Profile Name:");
		profilenamebar.setForeground(Color.white);
		profilenamebar.setFont(normal);
		
		profile = new JTextField("What would you like profile name to be");
		profile.addMouseListener(new MouseListener() {

		    public void mouseClicked(MouseEvent e) {
		    	  profile.setText("");
		            repaint();

		    }

			@Override
			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mousePressed(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
		    	  profile.setText("");
		            repaint();
				
			}
		    });
			
		realnamebar = new JLabel("Real Name:");
		realnamebar.setForeground(Color.white);
		realnamebar.setFont(normal);
		
		realname = new JTextField("Whats your real name");
		
		realname.addMouseListener(new MouseListener() {

		    public void mouseClicked(MouseEvent e) {
		    	  realname.setText("");
		            repaint();

		    }

			@Override
			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mousePressed(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub
		    	  realname.setText("");
		            repaint();
			}
		    });
			
		
		aboutmebar = new JLabel("About Me:");
		aboutmebar.setForeground(Color.white);
		aboutmebar.setFont(normal);
				
		aboutme = new JTextArea("Give a short description");
		
		aboutme.addMouseListener(new MouseListener() {

		    public void mouseClicked(MouseEvent e) {
		    	  aboutme.setText("");
		            repaint();

		    }

			@Override
			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mousePressed(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
		    	  aboutme.setText("");
		            repaint();
				
			}
		    });
		
		middleContainer.add(profilenamebar, "dock north");
		middleContainer.add(profile, "dock north, wrap, align 50% 50%, span, grow");
		middleContainer.add(realnamebar, "dock north");
		middleContainer.add(realname, "dock north, wrap, align 50% 50%, span, grow");
		middleContainer.add(aboutmebar, "dock north");
		middleContainer.add(aboutme,"dock north, wrap, align 50% 50%, span, grow, width :230px, height :100px");
		
		
		middleContainer.setOpaque(false);
	}

	/*
	 * Add all elements to the bottom container
	 */
	public void addBottomContainer() {

		bottomContainer = new JPanel(new MigLayout());
		
		
		picsavebutton = new ImageIcon("assets/images/save.png");
		rolloverSave = new ImageIcon("assets/images/savehover.png");
		piccancelbutton = new ImageIcon("assets/images/cancel.png");
		rolloverCancel = new ImageIcon("assets/images/cancelhover.png");

		cancelbutton = new JButton(piccancelbutton);
	    cancelbutton.setBorder(BorderFactory.createEmptyBorder());
	    cancelbutton.setContentAreaFilled(false);
	    cancelbutton.setRolloverIcon(rolloverCancel);

		savebutton = new JButton(picsavebutton);
	    savebutton.setBorder(BorderFactory.createEmptyBorder());
	    savebutton.setContentAreaFilled(false);
	    savebutton.setRolloverIcon(rolloverSave);

		bottomContainer.add(savebutton, "dock west");
		bottomContainer.add(cancelbutton, "dock east");

		

		bottomContainer.setOpaque(false);

	}

	/*
	 * Adds all the containers to the background image container (minus
	 * 
	 * the menupanel)
	 */
	public void addBackgroundContainer() {

		background = new ImagePanel(new ImageIcon(
				"assets/images/background.png").getImage());
		background.setLayout(new MigLayout());
		background.add(titlepanel, "dock north, align center");
		background.add(topContainer, "dock north, align center, wrap, gapleft 70px");
		background.add(middleContainer, "dock north, align center, wrap, gapleft 70px");
		background.add(bottomContainer, "dock north, align center, wrap, gapleft 50px, gaptop 10px");

	}

	/*
	 * Adds the background container and menupanel to the parentContainer
	 */
	public void addParentContainer() {

		parentContainer = new JPanel(new MigLayout());
		
		parentContainer.add(background, "dock south");

	}

	/**
	 * Event Listeners for Buttons
	 */

	public void SaveListener(ActionListener listenForSaveButton){
		savebutton.addActionListener(listenForSaveButton);
	
	}
	
	public void CancelListener(ActionListener listenForCancelButton){
		cancelbutton.addActionListener(listenForCancelButton);
	
	}
	
	public void UploadListener(ActionListener listenForUploadButton){
		browsebutton.addActionListener(listenForUploadButton);
	
	}
}