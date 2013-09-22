package deco2800.arcade.userui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

public class EditScreen extends JFrame {
	
	private JPanel parentContainer;
	private JPanel topContainer;
	private JPanel middleContainer;
	private ImagePanel menupanel;
	private JPanel bottomContainer;
	
	
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
		
		piccat = new ImageIcon("assets/images/avatar_mockup.png");
		picbrowsebutton = new ImageIcon("assets/images/browse_button.png");
		picsavebutton = new ImageIcon("assets/images/save_button.png");
		piccancelbutton = new ImageIcon("assets/images/cancel_button.png");
		
		
		parentContainer = new JPanel(new MigLayout());
		
		menupanel = new ImagePanel(new ImageIcon("assets/images/menu_bar.png").getImage());
	    menupanel.setLayout(new MigLayout());
	    
	    topContainer = new JPanel(new MigLayout());
	    middleContainer = new JPanel(new MigLayout());
	    bottomContainer = new JPanel(new MigLayout());
	    
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
        topContainer.add(browsebutton, "dock north, wrap, align 50% 50%");
         
        
        middleContainer.add(profilenamebar, "dock north");
        middleContainer.add(profile, "dock north, wrap, align 50% 50%");
        middleContainer.add(realnamebar, "dock north");
        middleContainer.add(realname, "dock north, wrap, align 50% 50%");
        middleContainer.add(aboutmebar, "dock north");
        middleContainer.add(aboutme, "dock north, wrap, align 50% 50%");
        
        bottomContainer.add(savebutton, "dock north");
        bottomContainer.add(cancelbutton, "dock north");
        
                 
        parentContainer.add(menupanel, "dock north");
        parentContainer.add(topContainer, "dock north");
        parentContainer.add(middleContainer, "dock north");
        parentContainer.add(bottomContainer, "dock north");
        add(parentContainer);
		
        
		
	// Set the  view window constraints
	setSize(1280,800);
	setDefaultCloseOperation(EXIT_ON_CLOSE);
	setVisible(true);
	setResizable(false);
	
	}

}
