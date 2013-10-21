package deco2800.arcade.userui.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.Insets;
import java.awt.event.ActionListener;

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

import org.apache.log4j.Logger;

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
	private ImagePanel achievementpanel;
	private JPanel achievementbarpanel, achievementlistpanel;
	private ImagePanel sidepanel;
	private JPanel playerpanel, playerinfopanel;
	private ImagePanel gamepanel;
	private JPanel gameavatarpanel, gameinfopanel;
	
	//Declare Buttons here
	private JButton editbutton, statusbutton;
	private JButton selectbutton;
	private JButton homelink, storelink, librarylink, forumlink, myprofilelink;
	
	//Declare ComboBox
	private JComboBox gameselect;
		
	//Declare Labels here
	private JLabel avatar, playername, playerlevel;
	private JLabel achievementbar;
	private JLabel gamename, gameachievementcount, gameicon;
	private JTextArea gamedescription, achievementlist;
	
	//Declare Images here
	private ImageIcon picavatar,piclocked, picunlocked, piceditbutton, 
	piconline, select, selecthover, logo;
	private ImageIcon home, homehover, forum, forumhover, store, storehover,
	library, libraryhover, profile, profilehover;
			
	//Declare Fonts
	Font blackbold = new Font("Century Gothic", Font.BOLD, 20);
	Font blacknormal = new Font("Century Gothic", Font.PLAIN, 16);
	Font blacksmall = new Font("Century Gothic", Font.PLAIN, 13);
	Font blacklink = new Font("Century Gothic", Font.PLAIN, 16);
	Font linkbold = new Font("Century Gothic", Font.BOLD, 16);
	Font sidebold = new Font("Century Gothic", Font.BOLD, 14);

	private String name, description, count;
	
	//Logger
	static Logger log = Logger.getLogger(AchievementScreen.class);
	
	public AchievementScreen(Model model) throws HeadlessException {
		
		super("Achievements");
		
		this.model = model;

		/* 
		 * Create Image Icons
		 * 
		 */	
		picavatar = new ImageIcon("assets/images/stark.png");
		piclocked = new ImageIcon("assets/images/achievement_locked.png");
		picunlocked = new ImageIcon("assets/images/achievement_unlocked.png");
		piceditbutton = new ImageIcon("assets/images/edit_button.png");
		homehover = new ImageIcon("assets/images/homehover.png");
		home = new ImageIcon("assets/images/home.png");
		storehover = new ImageIcon("assets/images/storehover.png");
		store = new ImageIcon("assets/images/store.png");
		libraryhover = new ImageIcon("assets/images/libraryhover.png");
		library = new ImageIcon("assets/images/library.png");
		forumhover = new ImageIcon("assets/images/forumhover.png");
		forum = new ImageIcon("assets/images/forum.png");
		profilehover = new ImageIcon("assets/images/profilehover.png");
		profile = new ImageIcon("assets/images/profile.png");
		select = new ImageIcon("assets/images/select.png");
		selecthover = new ImageIcon("assets/images/selecthover.png");
		
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
        sidepanel.add(gameselect, "wrap, gap bottom 10px");
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
		
		String[] gamelist = {"", "Pong", "Chess", "Burning Skies","Jungle Jump",
				"Breakout","Mix Maze","Pacman", "Land Invaders","Snakes and Ladders",
				"Raiden"};
		
		gameselect = new JComboBox(gamelist);
		
		selectbutton = new JButton();
	    selectbutton.setBorder(BorderFactory.createEmptyBorder());
	    selectbutton.setContentAreaFilled(false);
		selectbutton.setIcon(select);
		selectbutton.setRolloverIcon(selecthover);
		
		gamename = new JLabel();
	    gamename.setFont(linkbold);
	    gamename.setForeground(Color.white);
	    gameachievementcount = new JLabel();
	    gameachievementcount.setFont(sidebold);
	    gameachievementcount.setForeground(Color.white);
	    gamedescription = new JTextArea();
	    gamedescription.setFont(blacksmall);
	    gamedescription.setForeground(Color.white);
	    gamedescription.setLineWrap(true);
	    gamedescription.setOpaque(false);
	    gameicon = new JLabel();
	    
	    gameavatarpanel = new JPanel(new MigLayout());
	    gameavatarpanel.add(gameicon);
	    gameavatarpanel.setOpaque(false);
	    
	    gameinfopanel = new JPanel(new MigLayout());
	    gameinfopanel.add(gamedescription,"width :180px, height :100px");
	    gameinfopanel.setOpaque(false);
		
		gamepanel = new ImagePanel(new ImageIcon("assets/images/Blue_Box.png").getImage());
		gamepanel.setLayout(new MigLayout());
		gamepanel.add(gamename,"gap left 15px");
	    gamepanel.add(gameachievementcount,"gap left 60px, wrap");
		gamepanel.add(gameavatarpanel,"gap bottom 10px");
		gamepanel.add(gameinfopanel,"gap bottom 10px");
		gamepanel.setOpaque(false);
		
	}
	
	/**
	 *  Method creates a panel which displays
	 *  all achievements for a particular game 
	 *  as a list.
	 */
	public void addachievementpanel(){
				    
        achievementbar = new JLabel("Achievement List");
        achievementbar.setFont(blackbold);
        achievementbar.setForeground(Color.white);
        
        achievementbarpanel = new JPanel(new MigLayout());
        
	    achievementlistpanel = new JPanel(new MigLayout());
	    achievementlistpanel.setOpaque(false);
	    achievementlist = new JTextArea();
	    achievementlist.setLineWrap(true);
	    achievementlist.setFont(blacknormal);
	    achievementlist.setForeground(Color.white);
	    achievementlist.setEditable(false);
	    achievementlist.setMargin(new Insets(10,10,10,10));
	    achievementlist.setOpaque(false);
	    
	    achievementbarpanel.add(achievementbar);
        achievementbarpanel.setOpaque(false);
	    achievementlistpanel.add(achievementlist,"width :600px, height :500px");

	    achievementpanel = new ImagePanel(new ImageIcon("assets/images/blue_achievement.png").getImage());
	    achievementpanel.setLayout(new MigLayout());			
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
	    myprofilelink = new JButton();
	    myprofilelink.setBorder(BorderFactory.createEmptyBorder());
	    myprofilelink.setContentAreaFilled(false);
	    myprofilelink.setIcon(profile);
	    myprofilelink.setRolloverIcon(profilehover);
	    homelink = new JButton();
	    homelink.setBorder(BorderFactory.createEmptyBorder());
	    homelink.setContentAreaFilled(false);
	    homelink.setIcon(home);
	    homelink.setRolloverIcon(homehover);
	    librarylink = new JButton();
	    librarylink.setBorder(BorderFactory.createEmptyBorder());
	    librarylink.setContentAreaFilled(false);
	    librarylink.setIcon(library);
	    librarylink.setRolloverIcon(libraryhover);
	    storelink = new JButton();
	    storelink.setBorder(BorderFactory.createEmptyBorder());
	    storelink.setContentAreaFilled(false);
	    storelink.setIcon(store);
	    storelink.setRolloverIcon(storehover);
	    forumlink = new JButton();
	    forumlink.setBorder(BorderFactory.createEmptyBorder());
	    forumlink.setContentAreaFilled(false);
	    forumlink.setIcon(forum);
	    forumlink.setRolloverIcon(forumhover);
	    
		//Add Elements to Panel
	    menupanel = new ImagePanel(new ImageIcon("assets/images/menu_bar.png").getImage());
	    menupanel.setLayout(new MigLayout());	    
        menupanel.add(homelink, "center, gapbefore 200px");
        menupanel.add(storelink, "center, gapbefore 50px");
        menupanel.add(librarylink, "center, gapbefore 50px");
        menupanel.add(forumlink, "center, gapbefore 50px");
        menupanel.add(myprofilelink,"center, gapbefore 370px");	 
        
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
	 * 
	 * @param icon
	 */
	public void setStatus(ImageIcon icon){
		
		statusbutton.setIcon(icon);
		
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
	
	/**
	 * Get the game selection in the combo-box
	 * @return String of the selected game
	 */
	public String getGameSelection(){
		
		log.info(gameselect.getSelectedItem().toString());
		return gameselect.getSelectedItem().toString();
		
	}
	
	/**
	 *  Display achievements in panel
	 */
	public void setAchievementList(){
		
		//achievementlist.setText(model.achievements.achievementsForGame(getGameSelection()).toString());
		achievementlist.setText(getGameSelection());	
		if (getGameSelection() == "Pong"){
			
			//achievementlist.setText(model.achievements.achievementsForGame(game));
		}
		//Need to change the gamelist (combobox) to take in Game element values
		//Note: since the box is a string may need to do a check for each game seperately
		
	}
	
	/**
	 *  Set the game's name field
	 * @param string 
	 */
	public void setGameName(String name){
		
		this.name = name;
		gamename.setText(name);
		
	}
	
	/**
	 *  Set the game's description field
	 */
	public void setGameDescription(String description){
		
		this.description = description;
		gamedescription.setText(description);
		
	}
	
	/**
	 *  Set the game's Logo
	 * @param logo ImageIcon of the game logo
	 */
	public void setGameLogo(ImageIcon logo){
		
		this.logo = logo;
		gameicon.setIcon(logo);
		
	}
	
	/**
	 *  Set the game's achievement count
	 * @param count String representation of the achievements
	 */
	public void setAchievementCount(String count){
		
		this.count = count;
		gameachievementcount.setText(count);
		
	}

}
