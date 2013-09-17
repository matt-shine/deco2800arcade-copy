package deco2800.arcade.userui.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.HeadlessException;
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
import deco2800.arcade.userui.view.ImagePanel;

public class AchievementScreen extends JFrame implements ActionListener {

	/**
	 * 
	 * The view class for the main page of the user profile page
	 * 
	 **/
	
	private Model model;
	
	//Declare JPanel and ImagePanels
	private JPanel parentContainer;
	
	private ImagePanel menupanel;
	
	private JPanel contentpanel;
	private JPanel achievementpanel;
	
	private JPanel sidepanel;
	private JPanel playerpanel;
	private JPanel playerinfopanel;
	
	//Declare Buttons here
	private JButton addfriendbutton;
	private JButton editbutton;
	private JButton myprofilelink;
	private JButton homelink;
	private JButton storelink;
	private JButton gameslink;
	
	//Declare Labels here
	private JLabel avatar;
	private JLabel addfriend;
	private JLabel playername;
	private JLabel playerlevel;
	private JLabel achievementbar;
	
	//Declare Text Areas
	private JTextArea achievementarea;
	
	//Declare Images here
	private ImageIcon picavatar;
	private ImageIcon picachievementbar;
	private ImageIcon piclocked;
	private ImageIcon picunlocked;
	private ImageIcon piceditbutton;
	private ImageIcon picaddfriend;
	
	//Declare Fonts to use here
	Font blackbold = new Font("Verdana", Font.BOLD, 16);
	Font blacknormal = new Font("Verdana", Font.PLAIN, 14);
	
		
	public AchievementScreen(Model model) throws HeadlessException {
		
		super("Achievements");
		
		this.model = model;
		
		/**
		 * Add elements here to window
		 **/
		
		/* 
		 * Create Image Icons
		 * 
		 */
		
		picavatar = new ImageIcon("assets/images/stark.png");
		picachievementbar = new ImageIcon("assets/images/achievement_bar.png");
		piclocked = new ImageIcon("assets/images/achievement_locked.png");
		picunlocked = new ImageIcon("assets/images/achievement_unlocked.png");
		piceditbutton = new ImageIcon("assets/images/edit_button.png");
		picaddfriend = new ImageIcon("assets/images/add_friend.png");
		
		/*
		 * Text Areas
		 * 
		 */
		
		achievementarea = new JTextArea();
		JPanel friendarea = new JPanel();
		
		/*
		 * Panels and ScrollPanes
		 * 
		 */
		parentContainer = new JPanel(new MigLayout());
		
	    menupanel = new ImagePanel(new ImageIcon("assets/images/menu_bar.png").getImage());
	    menupanel.setLayout(new MigLayout());
	    menupanel.setBackground(Color.red);
	    
	    sidepanel = new JPanel(new MigLayout());
	    playerpanel = new JPanel(new MigLayout());
	    playerinfopanel = new JPanel(new MigLayout());
	    playerpanel.setOpaque(false);
	    playerinfopanel.setOpaque(false);
	    
	    contentpanel = new JPanel(new MigLayout());
	    achievementpanel = new JPanel(new MigLayout());
	    //achievementpanel.setOpaque(false);
	               
        /*
         * Buttons and Image Buttons
         * 
         */
	    
	    addfriendbutton = new JButton(picaddfriend);
	    addfriendbutton.setBorder(BorderFactory.createEmptyBorder());
	    addfriendbutton.setContentAreaFilled(false);
	    editbutton = new JButton(piceditbutton);
	    editbutton.setBorder(BorderFactory.createEmptyBorder());
	    editbutton.setContentAreaFilled(false);
	    
	    myprofilelink = new JButton("My Profile");
	    //myprofilelink.setBorder(BorderFactory.createEmptyBorder());
	    //myprofilelink.setContentAreaFilled(false);
	    myprofilelink.setBackground(Color.CYAN);

	    homelink = new JButton("Home");
	    //homelink.setBorder(BorderFactory.createEmptyBorder());
	    //homelink.setContentAreaFilled(false);
	    gameslink = new JButton("MyGames");
	    //gameslink.setBorder(BorderFactory.createEmptyBorder());
	    //gameslink.setContentAreaFilled(false);
	    storelink = new JButton("Game-Store");
	    //storelink.setBorder(BorderFactory.createEmptyBorder());
	    //storelink.setContentAreaFilled(false);
	    
        
        /*Labels
         * 
         */
	    
        avatar = new JLabel();
        avatar.setIcon(picavatar);
        addfriend = new JLabel();
        addfriend.setIcon(picaddfriend);
        playername = new JLabel("Stark");
        playername.setForeground(Color.white);
        playername.setFont(blackbold);
        playerlevel = new JLabel("Level 20");
        playerlevel.setFont(blacknormal);
        playerlevel.setForeground(Color.white);

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
        JLabel achievement9 = new JLabel();
        JLabel achievement10 = new JLabel();
        JLabel achievement11 = new JLabel();
        JLabel achievement12 = new JLabel();
        achievement1.setIcon(piclocked);
        achievement2.setIcon(piclocked);
        achievement3.setIcon(piclocked);
        achievement4.setIcon(piclocked);
        achievement5.setIcon(piclocked);
        achievement6.setIcon(piclocked);
        achievement7.setIcon(piclocked);
        achievement8.setIcon(piclocked);
        achievement9.setIcon(piclocked);
        achievement10.setIcon(piclocked);
        achievement11.setIcon(piclocked);
        achievement12.setIcon(piclocked);
        JPanel achievementtext1 = new JPanel(new MigLayout());
        JPanel achievementtext2 = new JPanel(new MigLayout());
        JPanel achievementtext3 = new JPanel(new MigLayout());
        JPanel achievementtext4 = new JPanel(new MigLayout());
        JPanel achievementtext5 = new JPanel(new MigLayout());
        JPanel achievementtext6 = new JPanel(new MigLayout());
        JPanel achievementtext7 = new JPanel(new MigLayout());
        JPanel achievementtext8 = new JPanel(new MigLayout());
        JPanel achievementtext9 = new JPanel(new MigLayout());
        JPanel achievementtext10 = new JPanel(new MigLayout());
        JPanel achievementtext11 = new JPanel(new MigLayout());
        JPanel achievementtext12 = new JPanel(new MigLayout());
        achievementtext1.setBackground(Color.BLUE);
        achievementtext2.setBackground(Color.BLUE);
        achievementtext3.setBackground(Color.BLUE);
        achievementtext4.setBackground(Color.BLUE);
        achievementtext5.setBackground(Color.BLUE);
        achievementtext6.setBackground(Color.BLUE);
        achievementtext7.setBackground(Color.BLUE);
        achievementtext8.setBackground(Color.BLUE);
        achievementtext9.setBackground(Color.BLUE);
        achievementtext10.setBackground(Color.BLUE);
        achievementtext11.setBackground(Color.BLUE);
        achievementtext12.setBackground(Color.BLUE);

		/*Add objects and position panels
		 * 
		 */
        menupanel.add(homelink, "center, gapbefore 250px");
        menupanel.add(storelink, "center, gapbefore 60px");
        menupanel.add(gameslink, "center, gapbefore 60px");
        menupanel.add(myprofilelink,"center, gapbefore 440px");

        playerpanel.add(avatar);
        playerinfopanel.add(playername,"wrap, align 50% 50%");       
        playerinfopanel.add(playerlevel,"wrap, align 50% 50%"); 
        playerinfopanel.add(addfriendbutton, "wrap, align 50% 50%");
        playerinfopanel.add(editbutton,"align 50% 50%");
        playerpanel.add(playerinfopanel);
        
        sidepanel.add(playerpanel, "wrap, center, width :300, height :300");
        
        achievementpanel.add(achievementbar, "north");
        achievementpanel.add(achievement1,"gapbefore 5px, pad 10 10 10 10");
        achievementpanel.add(achievementtext1,"pad 10 10 10 10, growy, width :110");
        achievementpanel.add(achievement2,"pad 10 10 10 10");
        achievementpanel.add(achievementtext2,"pad 10 10 10 10, growy, width :110");
        achievementpanel.add(achievement3,"pad 10 10 10 10");
        achievementpanel.add(achievementtext3,"pad 10 10 10 10, growy, width :110");
        achievementpanel.add(achievement4,"pad 10 10 10 10");
        achievementpanel.add(achievementtext4,"wrap,pad 10 10 10 10, growy, width :110");
        achievementpanel.add(achievement5,"gapbefore 5px, pad 10 10 10 10");
        achievementpanel.add(achievementtext5,"pad 10 10 10 10, growy, width :110");
        achievementpanel.add(achievement6,"pad 10 10 10 10");
        achievementpanel.add(achievementtext6,"pad 10 10 10 10, growy, width :110");
        achievementpanel.add(achievement7,"pad 10 10 10 10");
        achievementpanel.add(achievementtext7,"pad 10 10 10 10, growy, width :110");
        achievementpanel.add(achievement8,"pad 10 10 10 10");
        achievementpanel.add(achievementtext8,"wrap, pad 10 10 10 10, growy, width :110");
        achievementpanel.add(achievement9,"gapbefore 5px, pad 10 10 10 10");
        achievementpanel.add(achievementtext9,"pad 10 10 10 10, growy, width :110");
        achievementpanel.add(achievement10,"pad 10 10 10 10");
        achievementpanel.add(achievementtext10,"pad 10 10 10 10, growy, width :110");
        achievementpanel.add(achievement11,"pad 10 10 10 10");
        achievementpanel.add(achievementtext11,"pad 10 10 10 10, growy, width :110");
        achievementpanel.add(achievement12,"pad 10 10 10 10");
        achievementpanel.add(achievementtext12,"pad 10 10 10 10, growy, width :110");

        contentpanel.add(achievementpanel, "height :320, width :810, gapbefore 30px");
            
	    /*Add panels to Main Panel	
	     *                
	     */
		parentContainer.add(menupanel, "dock north");
		sidepanel.setBackground(Color.darkGray);
		parentContainer.add(sidepanel, "west, width :350, height :700");
		contentpanel.setBackground(Color.gray);
		parentContainer.add(contentpanel, "east, width :950, height :700");
		
		/*Add the main panel to JFrame
		 * 
		 */
		add(parentContainer);
	
		/*Set the  view window constraints
		 * 
		 */
		setSize(1280,800);
		pack();
		setVisible(true);
		setResizable(false);		
	}

	@Override
	public void actionPerformed(ActionEvent a) {
		
	}

}
