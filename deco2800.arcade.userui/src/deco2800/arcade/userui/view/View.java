package deco2800.arcade.userui.view;

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
import javax.swing.JTextArea;

import net.miginfocom.swing.MigLayout;
import deco2800.arcade.userui.model.Model;

public class View extends JFrame implements ActionListener {
	
	/**
	 * 
	 * The view class for the main page of the user profile page
	 * 
	 */
	
	/*
	 * Declaration of Elements 
	 */
	
	private Model model;
	
	//Declare JPanel and ImagePanels
	private JPanel parentContainer;
	
	private ImagePanel menupanel;
	private JPanel dropdownpanel;
	
	private JPanel contentpanel;
	private JPanel achievementpanel;
	private JPanel historypanel;
	
	private JPanel sidepanel;
	private JPanel playerpanel;
	private JPanel friendpanel;
	private JPanel aboutpanel;
	private JPanel playerinfopanel;
	
	//Declare Buttons here
	private JButton addfriendbutton;
	private JButton editbutton;
	
	//Declare Labels here
	private JLabel avatar;
	private JLabel addfriend;
	private JLabel playername;
	private JLabel playerlevel;
	private JLabel aboutbar;
	private JLabel friendbar;
	private JLabel historybar;
	private JLabel achievementbar;
	
	//Declare Text Areas
	private JTextArea achivementarea;
	private JTextArea aboutarea;
	private JTextArea friendarea;
	private JTextArea histroyarea;
	
	private JScrollPane aboutpane;
	private JScrollPane friendpane;
	private JScrollPane historypane;
	private JScrollPane achivementpane;
	
	//Declare Images here
	private ImageIcon picavatar;
	private ImageIcon picaboutbar;
	private ImageIcon picfriendbar;
	private ImageIcon picaddfriend;
	private ImageIcon pichistorybar;
	private ImageIcon picachievementbar;
	private ImageIcon piclocked;
	private ImageIcon picunlocked;
	private ImageIcon piceditbutton;
	
	//Declare Fonts to use here
	Font font = new Font("Verdana", Font.BOLD, 16);
	Font normal = new Font("Verdana", Font.PLAIN, 14);
		
	public View(Model model) throws HeadlessException {
		
		super("User Profile");
		
		this.model = model;
		
		/*
		 * Add elements here to window
		 */
		
		// Create Image Icons
		picavatar = new ImageIcon("assets/images/stark.png");
		picaboutbar = new ImageIcon("assets/images/about_bar.png");
		picfriendbar = new ImageIcon("assets/images/friendbar.png");
		picaddfriend = new ImageIcon("assets/images/add_friend.png");
		pichistorybar = new ImageIcon("assets/images/history_bar.png");
		picachievementbar = new ImageIcon("assets/images/achievement_bar.png");
		piclocked = new ImageIcon("assets/images/achievement_locked.png");
		picunlocked = new ImageIcon("assets/images/achievement_unlocked.png");
		piceditbutton = new ImageIcon("assets/images/edit_button.png");
		
		//Panels and ScrollPanes
		parentContainer = new JPanel(new MigLayout());
		
	    menupanel = new ImagePanel(new ImageIcon("assets/images/menu_bar.png").getImage());
	    menupanel.setLayout(new MigLayout());
	    dropdownpanel = new JPanel(new MigLayout());
	    //dropdownpanel.setOpaque(false);
	    
	    sidepanel = new JPanel(new MigLayout());
	    playerpanel = new JPanel(new MigLayout());
	    friendpanel = new JPanel(new MigLayout());
	    aboutpanel = new JPanel(new MigLayout());
	    playerinfopanel = new JPanel(new MigLayout());
	    playerpanel.setOpaque(false);
	    playerinfopanel.setOpaque(false);
	    //friendpanel.setOpaque(false);
	    //aboutpanel.setOpaque(false);
	    
	    contentpanel = new JPanel(new MigLayout());
	    historypanel = new JPanel(new MigLayout());
	    achievementpanel = new JPanel(new MigLayout());
	    //historypanel.setOpaque(false);
	    //achievementpanel.setOpaque(false);
	               
        //Buttons and Image Buttons
	    addfriendbutton = new JButton(picaddfriend);
	    addfriendbutton.setBorder(BorderFactory.createEmptyBorder());
	    addfriendbutton.setContentAreaFilled(false);
	    editbutton = new JButton(piceditbutton);
	    editbutton.setBorder(BorderFactory.createEmptyBorder());
	    editbutton.setContentAreaFilled(false);
        
        //Labels
        avatar = new JLabel();
        avatar.setIcon(picavatar);
        addfriend = new JLabel();
        addfriend.setIcon(picaddfriend);
        playername = new JLabel("Stark");
        playername.setForeground(Color.white);
        playername.setFont(font);
        aboutbar = new JLabel();
        aboutbar.setIcon(picaboutbar);
        friendbar = new JLabel();
        friendbar.setIcon(picfriendbar);
        playerlevel = new JLabel("Level 20");
        playerlevel.setFont(normal);
        playerlevel.setForeground(Color.white);
        historybar = new JLabel();
        historybar.setIcon(pichistorybar);
        achievementbar = new JLabel();
        achievementbar.setIcon(picachievementbar);
        JLabel achievement1 = new JLabel();
        JLabel achievement2 = new JLabel();
        JLabel achievement3 = new JLabel();
        JLabel achievement4 = new JLabel();
        JLabel achievement5 = new JLabel();
        JLabel achievement6 = new JLabel();
        JLabel achievement7 = new JLabel();
        JLabel achievement8 = new JLabel();
        achievement1.setIcon(piclocked);
        achievement2.setIcon(piclocked);
        achievement3.setIcon(piclocked);
        achievement4.setIcon(piclocked);
        achievement5.setIcon(piclocked);
        achievement6.setIcon(piclocked);
        achievement7.setIcon(piclocked);
        achievement8.setIcon(piclocked);
        JTextArea achivementtext1 = new JTextArea();
        JTextArea achivementtext2 = new JTextArea();
        JTextArea achivementtext3 = new JTextArea();
        JTextArea achivementtext4 = new JTextArea();
        JTextArea achivementtext5 = new JTextArea();
        JTextArea achivementtext6 = new JTextArea();
        JTextArea achivementtext7 = new JTextArea();
        JTextArea achivementtext8 = new JTextArea();

		//Add objects and position panels
        menupanel.add(dropdownpanel,"east");
        
        playerpanel.add(avatar);
        playerinfopanel.add(playername,"wrap, align 50% 50%");       
        playerinfopanel.add(playerlevel,"wrap, align 50% 50%"); 
        playerinfopanel.add(addfriendbutton, "wrap, align 50% 50%");
        playerinfopanel.add(editbutton,"align 50% 50%");
        playerpanel.add(playerinfopanel);
        friendpanel.add(friendbar, "north");
        aboutpanel.add(aboutbar,"north");
        
        sidepanel.add(playerpanel, "wrap, center, width :300, height :300");
        sidepanel.add(aboutpanel, "wrap, center, width :300, height :300");
        sidepanel.add(friendpanel, "center, width :300, height :300");
        
        historypanel.add(historybar, "north");
        achievementpanel.add(achievementbar, "north");
        achievementpanel.add(achievement1);
        achievementpanel.add(achievement2);
        achievementpanel.add(achievement3);
        achievementpanel.add(achievement4, "wrap");
        achievementpanel.add(achievement5);
        achievementpanel.add(achievement6);
        achievementpanel.add(achievement7);
        achievementpanel.add(achievement8);
    
        contentpanel.add(historypanel, "wrap, height :320, width :810, gapbefore 30px");
        contentpanel.add(achievementpanel, "height :320, width :810, gapbefore 30px");
            
	    //Add panels to Main Panel	               
		parentContainer.add(menupanel, "dock north");
		sidepanel.setBackground(Color.darkGray);
		parentContainer.add(sidepanel, "west, width :350, height :700");
		contentpanel.setBackground(Color.gray);
		parentContainer.add(contentpanel, "east, width :950, height :700");
		
		//Add the main panel to JFrame
		add(parentContainer);
	
		// Set the  view window constraints
		setSize(1280,800);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		pack();
		setVisible(true);
		setResizable(false);		
	}


	@Override
	public void actionPerformed(ActionEvent e) {

		
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
