package deco2800.arcade.userui.view;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import deco2800.arcade.userui.model.Model;

public class View extends JFrame implements ActionListener {
	
	/*
	 * Declare elements here
	 */
	private Model model;
	
	private JPanel mainpanel;
	private ImagePanel menupanel;
	private JPanel sidepanel;
	private JPanel contentpanel;
	private JScrollPane sidescroll;
	private JScrollPane contentscroll;
	
	//Declare buttons here
	
	//Declare Labels here
	private JLabel avatar;
	private JLabel addfriend;
	private JLabel playername;
	private JLabel playerlevel;
	private JLabel aboutbar;
	private JLabel friendbar;
	
	//Declare Text Areas
	
	
	//Declare Images here
	private ImageIcon picavatar;
	private ImageIcon picaboutbar;
	private ImageIcon picfriendbar;
	private ImageIcon picaddfriend;

	//User a GridBagLayout to these sections
	private GridBagLayout pagelayout = new GridBagLayout();
	private GridBagLayout menulayout = new GridBagLayout();
	private GridBagLayout sidelayout = new GridBagLayout();
	private GridBagLayout mainlayout = new GridBagLayout();
	
	public View(Model model) throws HeadlessException {
		super("User Profile");
		
		this.model = model;
		
		/*
		 * Add elements here to window
		 */
		
		//Image Icons
		picavatar = new ImageIcon("assets/images/avatar_mockup.png");
		picaboutbar = new ImageIcon("assets/images/about_bar.png");
		picfriendbar = new ImageIcon("assets/images/friend_bar.png");
		picaddfriend = new ImageIcon("assets/images/add_friend.png");

		
		//Panels and ScrollPanes
		mainpanel = new JPanel(new GridLayout());
	    menupanel = new ImagePanel(new ImageIcon("assets/images/menu_bar.png").getImage());
	    menupanel.setLayout(new GridBagLayout());
	    sidepanel = new JPanel(new GridBagLayout());
	    contentpanel = new JPanel(new GridBagLayout());
	    
        sidescroll = new JScrollPane(sidepanel);
        
        //Buttons

        
        //Labels
        avatar = new JLabel();
        avatar.setIcon(picavatar);
        addfriend = new JLabel();
        addfriend.setIcon(picaddfriend);
        playername = new JLabel("Stark");
        aboutbar = new JLabel();
        aboutbar.setIcon(picaboutbar);
        friendbar = new JLabel();
        friendbar.setIcon(picfriendbar);
        playerlevel = new JLabel("Level 20");

		//Add objects to panels     
        sidepanel.add(avatar);
        sidepanel.add(playername);       
        sidepanel.add(playerlevel);        
        sidepanel.add(addfriend);        
        sidepanel.add(aboutbar);       
        sidepanel.add(friendbar);
            
	    //Add panels to Main Panel	               
		add(menupanel, BorderLayout.NORTH);
				
		mainpanel.add(sidepanel);

		mainpanel.add(contentpanel);
		
		//Add the main panel to JFrame		
		add(mainpanel);
		
		this.pack();
		
		//Note: need to fix problem 2 JPanels are overlapping
		sidepanel.setBackground(Color.darkGray);
        
		contentpanel.setBackground(Color.gray);
	
		// Set the  view window constraints
		setSize(1280,800);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		
		/* Example code to call a button event
		 * JButton source = (JButton)e.getSource();
		
		if(source == editbutton) {
			System.out.println("Edit profile");
		} else {
			System.out.println("Some other button");		
		}*/
		
	}
	
	
}

/*
 * Class for adding background panel to JPanels
 */

class ImagePanel extends JPanel {

	  private Image img;

	  public ImagePanel(String img) {
	    this(new ImageIcon(img).getImage());
	  }

	  public ImagePanel(Image img) {
	    this.img = img;
	    Dimension size = new Dimension(img.getWidth(null), img.getHeight(null));
	    setPreferredSize(size);
	    setMinimumSize(size);
	    setMaximumSize(size);
	    setSize(size);
	    setLayout(null);
	  }

	  public void paintComponent(Graphics g) {
	    g.drawImage(img, 0, 0, null);
	  }

	}
