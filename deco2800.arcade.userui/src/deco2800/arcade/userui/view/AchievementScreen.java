package deco2800.arcade.userui.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import deco2800.arcade.model.Achievement;
import deco2800.arcade.model.Game;
import deco2800.arcade.client.AchievementClient;
import deco2800.arcade.userui.Model;
import net.miginfocom.swing.MigLayout;

public class AchievementScreen extends JFrame{

	/**
	 * 
	 * The view class for the achievement page 
	 * 
	 **/
	
	private Model model;
	
	//Declare JPanel and ImagePanels
	private JPanel parentContainer;	
	private ImagePanel menupanel;
	private ImagePanel contentpanel;
	private JPanel achievementpanel, achievementbarpanel, achievementlistpanel;
	private ImagePanel sidepanel;
	private JPanel playerpanel, playerinfopanel;
	private ImagePanel gamepanel;
	private JPanel gameavatarpanel, gameinfopanel;
	
	//Declare Buttons here
	private JButton addfriendbutton, editbutton, statusbutton;
	private JButton selectbutton;
	private JButton homelink, storelink, librarylink, forumlink, myprofilelink;
	
	//Declare ComboBox
	private JComboBox gameselect;
		
	//Declare Labels here
	private JLabel avatar, addfriend, playername, playerlevel;
	private JLabel achievementbar;
	private JLabel gamename, gameachievementcount, gameicon;
	private JTextArea gamedescription, achievementlist;
	
	//Declare Images here
	private ImageIcon picavatar, picaddfriend, picachievementbar, 
	piclocked, picunlocked, piceditbutton, picfriendoffline, piconline, picoffline;
			
	//Declare Fonts to use here
	Font blackbold = new Font("Verdana", Font.BOLD, 16);
	Font blacknormal = new Font("Verdana", Font.PLAIN, 14);
	Font blacksmall = new Font("Verdana", Font.PLAIN, 12);
	Font blacklink = new Font("Verdana", Font.PLAIN, 15);
	Font linkbold = new Font("Verdana", Font.BOLD, 14);
	Font sidebold = new Font("Verdana", Font.BOLD, 12);

	private Game game;
	
	public AchievementScreen(Model model) throws HeadlessException {
		
		super("Achievements");
		
		this.model = model;

		/* 
		 * Create Image Icons
		 * 
		 */	
		picavatar = new ImageIcon("assets/images/stark.png");
		picaddfriend = new ImageIcon("assets/images/add_friend.png");
		piclocked = new ImageIcon("assets/images/achievement_locked.png");
		picunlocked = new ImageIcon("assets/images/achievement_unlocked.png");
		piceditbutton = new ImageIcon("assets/images/edit_button.png");
		picfriendoffline = new ImageIcon("assets/images/addfriendoffline.png");
		piconline = new ImageIcon("assets/images/online.png");
		picoffline = new ImageIcon("assets/images/offline.png");
		
		addplayerinfopanel();
		addplayerpanel();
		addmenupanel();
		addachievementpanel();
		addgamepanel();
		
		addsidepanel();
		addcontentpanel();

		parentContainer = new JPanel(new MigLayout());
		parentContainer.add(menupanel, "dock north");
		parentContainer.add(sidepanel, "west, width :350, height :700");
		parentContainer.add(contentpanel, "east, width :950, height :700");		
		add(parentContainer);
	
		/*Set the  view window constraints
		 * 
		 */
		setSize(1280,800);
		pack();
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		setResizable(false);		
	}
	
	/**
	 *  Method creates a side panel to hold everything on the left of page.
	 */
	public void addsidepanel(){
		
		sidepanel = new ImagePanel(new ImageIcon("assets/images/sidebackground.png").getImage());  
	    sidepanel.setLayout(new MigLayout());
        sidepanel.add(playerpanel, "wrap, center, width :300, height :600");
        sidepanel.add(gamepanel, "wrap, gapbottom 30px, width :300, height :600");
        sidepanel.add(gameselect, "wrap");
        sidepanel.add(selectbutton,"gap bottom 170px");
        
	}
	
	/**
	 *  Method creates a content panel to hold everything on the right of page.
	 */
	public void addcontentpanel(){
		
		 contentpanel = new ImagePanel(new ImageIcon("assets/images/contentbackground.png").getImage());
		 contentpanel.setLayout(new MigLayout());
	     contentpanel.add(achievementpanel, "height :640, width :810, gapbefore 30px");
	     contentpanel.setBackground(Color.gray);
	     
	}
	
	/**
	 *  Method creates a panel showing game information for each game in the arcade
	 */
	public void addgamepanel(){
		
		String[] gamelist = {"", "Pong", "Chess", "Burning Skies", "Checkers","Jungle Jump",
		"Snakes and Ladders","Raiden","Breakout"};
		gameselect = new JComboBox(gamelist);
		
		selectbutton = new JButton("Select");
		
		gamename = new JLabel("Game Name");
	    gamename.setFont(linkbold);
	    gamename.setForeground(Color.white);
	    gameachievementcount = new JLabel("15/100");
	    gameachievementcount.setFont(blackbold);
	    gameachievementcount.setForeground(Color.white);
	    gamedescription = new JTextArea("Some description about the game");
	    gamedescription.setFont(blacksmall);
	    gamedescription.setForeground(Color.white);
	    gamedescription.setLineWrap(true);
	    gamedescription.setOpaque(false);
	    gameicon = new JLabel();
	    gameicon.setIcon(piclocked);
	    
	    gameavatarpanel = new JPanel(new MigLayout());
	    gameavatarpanel.add(gameicon);
	    gameavatarpanel.setOpaque(false);
	    
	    gameinfopanel = new JPanel(new MigLayout());
	    gameinfopanel.add(gameachievementcount,"gap left 20px, wrap");
	    gameinfopanel.add(gamedescription,"width :180px, height :100px");
	    gameinfopanel.setOpaque(false);
		
		gamepanel = new ImagePanel(new ImageIcon("assets/images/Blue_Box.png").getImage());
		gamepanel.setLayout(new MigLayout());
		gamepanel.add(gamename,"gap left 10px, wrap");
		gamepanel.add(gameavatarpanel,"left, gap bottom 50px");
		gamepanel.add(gameinfopanel,"center, gap bottom 50px");
		
	}
	
	/**
	 *  Method creates a panel which displays
	 *  all achievements for a particular game 
	 *  as a list.
	 */
	public void addachievementpanel(){
				    
        achievementbar = new JLabel("Achievement List");
        achievementbar.setFont(blackbold);
        //achievementbar.setForeground(Color.white);
        
        achievementbarpanel = new JPanel(new MigLayout());
        
	    achievementlistpanel = new JPanel(new MigLayout());
	    achievementlist = new JTextArea();
	    achievementlist.setLineWrap(true);
	    
	    achievementbarpanel.add(achievementbar);
	    achievementlistpanel.add(achievementlist,"width :600px, height :500px");

	    achievementpanel = new JPanel(new MigLayout());       
		achievementpanel.add(achievementbarpanel,"gap left 10px, wrap");
		achievementpanel.add(achievementlistpanel, "width :700px, height :550px");
		
	}
	
	/**
	 *  Method creates a panel with player avatar &
	 *  playerinfopanel.
	 */
	public void addplayerpanel(){
		
	    playerpanel = new JPanel(new MigLayout());	    
	    playerpanel.setOpaque(false);
		playerpanel.add(avatar);
        playerpanel.add(playerinfopanel);
    
	}
	
	/**
	 *  Creates a panel containing player status options,
	 *  add friend button, edit profile option. 
	 */
public void addplayerinfopanel(){
		
		//Add Buttons
	    statusbutton = new JButton(piconline);
	    statusbutton.setBorder(BorderFactory.createEmptyBorder());
	    statusbutton.setContentAreaFilled(false);
	    editbutton = new JButton(piceditbutton);
	    editbutton.setBorder(BorderFactory.createEmptyBorder());
	    editbutton.setContentAreaFilled(false);
	    
	    //Add Labels	    
        avatar = new JLabel();
        avatar.setIcon(picavatar);
        playername = new JLabel("Player");
        playername.setForeground(Color.white);
        playername.setFont(blackbold);
        playerlevel = new JLabel("Last Login: 8/3/2013");
        playerlevel.setFont(blacksmall);
        playerlevel.setForeground(Color.white);
        
		//Add Elements to Panel
		playerinfopanel = new JPanel(new MigLayout());
		playerinfopanel.setOpaque(false);		   	
        playerinfopanel.add(playername,"wrap, align 50% 50%, gap top 30px");       
        playerinfopanel.add(playerlevel,"wrap, align 50% 50%, gap top 5px"); 
        playerinfopanel.add(statusbutton, "wrap, align 50% 50%, gap top 20px");
        playerinfopanel.add(editbutton,"align 50% 50%, gap top 5px");
        
	}
	
	/**
	 *   Creates a panel containing button links to other pages in game arcade.
	 */
	public void addmenupanel(){
		
		 //Page Button Links
	    myprofilelink = new JButton("MY PROFILE");
	    myprofilelink.setFont(linkbold);
	    myprofilelink.setBorder(BorderFactory.createEmptyBorder());
	    myprofilelink.setContentAreaFilled(false);
	    myprofilelink.setForeground(Color.WHITE);
	    homelink = new JButton("HOME");
	    homelink.setFont(blacklink);
	    homelink.setForeground(Color.white);
	    homelink.setBorder(BorderFactory.createEmptyBorder());
	    homelink.setContentAreaFilled(false);
	    librarylink = new JButton("LIBRARY");
	    librarylink.setFont(blacklink);
	    librarylink.setForeground(Color.white);
	    librarylink.setBorder(BorderFactory.createEmptyBorder());
	    librarylink.setContentAreaFilled(false);
	    storelink = new JButton("STORE");
	    storelink.setFont(blacklink);
	    storelink.setForeground(Color.white);
	    storelink.setBorder(BorderFactory.createEmptyBorder());
	    storelink.setContentAreaFilled(false);
	    forumlink = new JButton("FORUM");
	    forumlink.setFont(blacklink);
	    forumlink.setForeground(Color.white);
	    forumlink.setBorder(BorderFactory.createEmptyBorder());
	    forumlink.setContentAreaFilled(false);
	    
		//Add Elements to Panel
	    menupanel = new ImagePanel(new ImageIcon("assets/images/menu_bar.png").getImage());
	    menupanel.setLayout(new MigLayout());	    
        menupanel.add(homelink, "center, gapbefore 250px");
        menupanel.add(storelink, "center, gapbefore 100px");
        menupanel.add(librarylink, "center, gapbefore 100px");
        menupanel.add(forumlink, "center, gapbefore 100px");
        menupanel.add(myprofilelink,"center, gapbefore 350px");	   		
	}

	/*
	 *  Event Listeners for Buttons
	 */
	
	/**
	 * Button navigates to the home page on click
	 * @param listenForHomeLink
	 */
	public void addHomeListener(ActionListener listenForHomeLink){
		
		homelink.addActionListener(listenForHomeLink);	
		
	}
	
	/**
	 * Button navigates to the GameStore page on click
	 * @param listenForStoreLink
	 */
	public void addStoreListener(ActionListener listenForStoreLink){
		
		storelink.addActionListener(listenForStoreLink);
		
	}
	
	/**
	 * Button navigates to the Forum page on click
	 * @param listenForForumLink
	 */
	public void addForumListener(ActionListener listenForForumLink){
		
		forumlink.addActionListener(listenForForumLink);
		
	}
	
	/**
	 * Button navigates to the Library page on click
	 * @param listenForLibraryLink
	 */
	public void addLibraryListener(ActionListener listenForLibraryLink){
		
		librarylink.addActionListener(listenForLibraryLink);
		
	}
	
	/**
	 * Button navigates back to user's profile page
	 * @param listenForProfileLink
	 */
	public void addProfileListener(ActionListener listenForProfileLink){
		
		myprofilelink.addActionListener(listenForProfileLink);	
		
	}
	
	/**
 	* Button opens a popup allowing user to add friend
 	* @param listenForAddFriendButton
 	*/
	public void addFriendListener(ActionListener listenForAddFriendButton){
	

		
	}

	/**
 	* Button opens a popup allowing editing of profile details
 	* @param listenForEditButton
 	*/
	public void addEditListener(ActionListener listenForEditButton){
	
		editbutton.addActionListener(listenForEditButton);	
	
	}

	/**
 	* Button opens a popup allowing user to change their visibility status
 	* @param listenForStatusButton
 	*/
	public void addStatusListener(ActionListener listenForStatusButton){
	
		statusbutton.addActionListener(listenForStatusButton);
	
	}
	/**
 	* Button when clicked will display achievements for the particular game 
 	* selected in combo-box
 	* @param listenForSelectButton
 	*/
	public void addSelectListener(ActionListener listenForSelectButton){
	
		selectbutton.addActionListener(listenForSelectButton);
	
	}

	public void displayErrorMessage(String errorMessage){
	
		JOptionPane.showMessageDialog(this, errorMessage);
	
	}
	
	/*
	 * Get and Set method for objects
	 */
	
	public void getGameSelection(){
		
		//Set selected game identifier
		System.out.println(gameselect.getSelectedItem().toString());
		
	}
	
	public void setAchievementList(Game game){
		
		this.game = game;			
		ArrayList<Achievement> list;
		AchievementClient gameAchievement = null;
		
		gameAchievement.achievementsForGame(game);
		achievementlist.setText(gameAchievement.toString());
				
	}

}
