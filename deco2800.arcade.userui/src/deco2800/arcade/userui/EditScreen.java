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

public class EditScreen extends JFrame {
	
	private JPanel parentContainer;
	private JPanel topContainer;
	private JPanel middleContainer;
	private JPanel bottomContainer;
	private ImagePanel menupanel;
	private ImagePanel background;

	
	
	
	private ImageIcon Pink_Box;
	private ImageIcon piccat;
	private ImageIcon picbrowsebutton;
	private ImageIcon picsavebutton;
	private ImageIcon piccancelbutton;
	
	private JButton browsebutton;
	private JButton cancelbutton;
	private JButton savebutton;
	
	private JTextField upload;
	private JTextField profile;
	private JTextField realname;
	private JTextField aboutme;
	
	private JLabel avatar;
	private JLabel editprofilebar;
	private JLabel profilenamebar;
	private JLabel realnamebar;
	private JLabel aboutmebar;
	
	
	
	private Model m;
	
	Font font = new Font("Verdana", Font.BOLD, 26);
	Font normal = new Font("Verdana", Font.PLAIN, 14);
	
	public EditScreen(Model m) throws HeadlessException {
	super("Edit Profile");
		
		piccat = new ImageIcon("assets/images/Profile_Picture.png");
		picbrowsebutton = new ImageIcon("assets/images/browse.png");
		picsavebutton = new ImageIcon("assets/images/save.png");
		piccancelbutton = new ImageIcon("assets/images/cancel.png");
		
		
		parentContainer = new JPanel(new MigLayout());
		
		menupanel = new ImagePanel(new ImageIcon("assets/images/Link_Bar.png").getImage());
	    menupanel.setLayout(new MigLayout());
	    
	    topContainer = new JPanel(new MigLayout());
	    middleContainer = new JPanel(new MigLayout());
	    bottomContainer = new JPanel(new MigLayout());
	    background = new ImagePanel(new ImageIcon("assets/images/background.png").getImage());
	    background.setLayout(new MigLayout());
	    
        
        //set sizes
        

	    
	    
	    
	    
	    
	    browsebutton = new JButton(picbrowsebutton);
	    browsebutton.setBorder(BorderFactory.createEmptyBorder());
	    browsebutton.setContentAreaFilled(false);
	    cancelbutton = new JButton(piccancelbutton);
	    cancelbutton.setBorder(BorderFactory.createEmptyBorder());
	    cancelbutton.setContentAreaFilled(false);
	    savebutton = new JButton(picsavebutton);
	    savebutton.setBorder(BorderFactory.createEmptyBorder());
	    savebutton.setContentAreaFilled(false);
	    
	    avatar = new JLabel();
        avatar.setIcon(piccat);
        
      
        editprofilebar = new JLabel("Edit Profile");
        editprofilebar.setForeground(Color.black);
        editprofilebar.setFont(font);
        profilenamebar = new JLabel("Profile Name:");
        profilenamebar.setForeground(Color.black);
        profilenamebar.setFont(normal);
        realnamebar = new JLabel("Real Name:");
        realnamebar.setForeground(Color.black);
        realnamebar.setFont(normal);
        aboutmebar = new JLabel("About Me:");
        aboutmebar.setForeground(Color.black);
        aboutmebar.setFont(normal);
        
        upload = new JTextField("Upload a new picture");
        profile = new JTextField("What would you like profile name to be");
        realname = new JTextField("Whats your real name");
        aboutme = new JTextField("Give a short description");
        
        
      
     
        
        topContainer.add(avatar, "dock west");
        topContainer.add(editprofilebar, "dock north, wrap, align 50% 50%");
        topContainer.add(upload, "dock north");
        topContainer.add(browsebutton, "dock north, wrap");
        
        
         
        
        middleContainer.add(profilenamebar, "dock north");
        middleContainer.add(profile, "dock north, wrap, align 50% 50%, span, grow");
        middleContainer.add(realnamebar, "dock north");
        middleContainer.add(realname, "dock north, wrap, align 50% 50%, span, grow");
        middleContainer.add(aboutmebar, "dock north");
        middleContainer.add(aboutme, "dock north, wrap, align 50% 50%, span, grow");
        
        bottomContainer.add(savebutton, "dock north");
        bottomContainer.add(cancelbutton, "dock north");
        
        topContainer.setBorder(new EmptyBorder(30, 450, 0, 0));
        middleContainer.setBorder(new EmptyBorder(0, 450, 0, 0));
        bottomContainer.setBorder(new EmptyBorder(0, 450, 0, 0));
        topContainer.setOpaque(false);
        middleContainer.setOpaque(false);
        bottomContainer.setOpaque(false);
        
        background.add(topContainer, "dock north, align center");
        background.add(middleContainer, "dock north, align center");
        background.add(bottomContainer, "dock north, align center");
       
        parentContainer.add(menupanel, "dock north, align center");
        parentContainer.add(background, "dock south");
       // parentContainer.add(topContainer, "dock north, align center");
       // parentContainer.add(middleContainer, "dock north, align center");
       // parentContainer.add(bottomContainer, "dock north, align center");
        add(parentContainer);
		
        
		
	// Set the  view window constraints
	setSize(1280,800);
	setDefaultCloseOperation(EXIT_ON_CLOSE);
	setVisible(true);
	setResizable(false);
	
	}
	

}
	
