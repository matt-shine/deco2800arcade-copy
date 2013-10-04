package deco2800.arcade.userui;

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
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;

import javax.swing.JFrame;
import javax.swing.border.EmptyBorder;

public class EditScreen extends JFrame implements ActionListener {

	// Declare JPanels and ImagePanels for later use
	private JPanel parentContainer;
	private JPanel topContainer;
	private JPanel middleContainer;
	private JPanel bottomContainer;
	private ImagePanel menupanel;
	private ImagePanel background;

	// Declare ImageIcons for later use
	private ImageIcon Pink_Box;
	private ImageIcon piccat;
	private ImageIcon picbrowsebutton;
	private ImageIcon picsavebutton;
	private ImageIcon piccancelbutton;

	// Declare JButtons for later use
	private JButton browsebutton;
	private JButton cancelbutton;
	private JButton savebutton;

	// Declare JTextFields for later use
	private JTextField upload;
	private JTextField profile;
	private JTextField realname;
	private JTextField aboutme;

	// Declare JLabels for later use
	private JLabel avatar;
	private JLabel editprofilebar;
	private JLabel profilenamebar;
	private JLabel realnamebar;
	private JLabel aboutmebar;

	// Declare model
	private Model m;

	Font big = new Font("Verdana", Font.BOLD, 26);
	Font normal = new Font("Verdana", Font.PLAIN, 14);

	/*
	 * The actual edit screen construction
	 */

	public EditScreen(Model m) throws HeadlessException {

		super("Edit Profile");

		// Call all the function that construct the edit screen
		addMenuPanel();
		addTopContainer();
		addMiddleContainer();
		addBottomContainer();
		addBackgroundContainer();
		addParentContainer();
		add(parentContainer);

		// Set the view window constraints
		setSize(1280, 800);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		setResizable(false);

	}

	/*
	 * Add the menu panel
	 */
	public void addMenuPanel() {

		menupanel = new ImagePanel(new ImageIcon

		("assets/images/Link_Bar.png").getImage());
		menupanel.setLayout(new MigLayout());

	}

	/*
	 * Add all the elements to the top container
	 */

	public void addTopContainer(){
		
		topContainer = new JPanel(new MigLayout());
		
		piccat = new ImageIcon("assets/images/Profile_Picture.png");
		picbrowsebutton = new ImageIcon("assets/images/browse.png");
		
		avatar = new JLabel();
		avatar.setIcon(piccat);
		
		editprofilebar = new JLabel("Edit Profile");
		editprofilebar.setForeground(Color.black);
		editprofilebar.setFont(big);
		

		
		browsebutton = new JButton(picbrowsebutton);
		browsebutton.setBorder(BorderFactory.createEmptyBorder());
		browsebutton.setContentAreaFilled(false);
		
		upload = new JTextField("Upload a new picture");
		

		topContainer.add(avatar, "dock west");
		topContainer.add(editprofilebar, "dock north, wrap, align 50% 50%");
		topContainer.add(upload, "dock north");
		topContainer.add(browsebutton, "dock north, wrap");
		topContainer.setBorder(new EmptyBorder(30, 450, 0, 0));
		topContainer.setOpaque(false);
		
	}

	/*
	 * Add all elements to the middle container
	 */
	public void addMiddleContainer(){
		
		middleContainer = new JPanel(new MigLayout());
		
		profilenamebar = new JLabel("Profile Name:");
		profilenamebar.setForeground(Color.black);
		profilenamebar.setFont(normal);
		
		profile = new JTextField("What would you like profile name to be");
		
		realnamebar = new JLabel("Real Name:");
		realnamebar.setForeground(Color.black);
		realnamebar.setFont(normal);
		
		realname = new JTextField("Whats your real name");
		
		aboutmebar = new JLabel("About Me:");
		aboutmebar.setForeground(Color.black);
		aboutmebar.setFont(normal);
				
		aboutme = new JTextField("Give a short description");
		
		middleContainer.add(profilenamebar, "dock north");
		middleContainer.add(profile, "dock north, wrap, align 50% 50%, span, grow");
		middleContainer.add(realnamebar, "dock north");
		middleContainer.add(realname, "dock north, wrap, align 50% 50%, span, grow");
		middleContainer.add(aboutmebar, "dock north");
		middleContainer.add(aboutme,"dock north, wrap, align 50% 50%, span, grow");
		
		middleContainer.setBorder(new EmptyBorder(0, 450, 0, 0));
		middleContainer.setOpaque(false);
	}

	/*
	 * Add all elements to the bottom container
	 */
	public void addBottomContainer() {

		bottomContainer = new JPanel(new MigLayout());

		picsavebutton = new ImageIcon("assets/images/save.png");
		piccancelbutton = new ImageIcon("assets/images/cancel.png");

		cancelbutton = new JButton(piccancelbutton);
		cancelbutton.setBorder(BorderFactory.createEmptyBorder());
		cancelbutton.setContentAreaFilled(false);
		savebutton = new JButton(picsavebutton);
		savebutton.setBorder(BorderFactory.createEmptyBorder());
		savebutton.setContentAreaFilled(false);

		bottomContainer.add(savebutton, "dock north");
		bottomContainer.add(cancelbutton, "dock north");

		bottomContainer.setBorder(new EmptyBorder(0, 450, 0, 0));

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

		background.add(topContainer, "dock north, align center");
		background.add(middleContainer, "dock north, align center");
		background.add(bottomContainer, "dock north, align center");

	}

	/*
	 * Adds the background container and menupanel to the parentContainer
	 */
	public void addParentContainer() {

		parentContainer = new JPanel(new MigLayout());
		parentContainer.add(menupanel, "dock north, align center");
		parentContainer.add(background, "dock south");

	}

	/**
	 * Event Listeners for Buttons
	 */

	@Override
	public void actionPerformed(ActionEvent e) {

	}

}