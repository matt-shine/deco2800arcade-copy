package deco2800.arcade.userui;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import net.miginfocom.swing.MigLayout;

public class UserScreen extends JFrame{
	
	/**
	 * The view class for the main page of the user profile
	 */
	
	private Model model;
	
	//Declare JPanel and ImagePanels
	private JPanel parentContainer;	
	private ImagePanel menupanel;	
	private JPanel contentpanel;
	private ImagePanel achievementpanel;
	private ImagePanel historypanel;	
	private ImagePanel sidepanel;
	private JPanel playerinfopanel;
	private JPanel playerpanel;
	private ImagePanel friendpanel;
	private ImagePanel aboutpanel;
	private JPanel friendlist;
	private JScrollPane friendscroll;
	
	//Declare Buttons 
	private JButton addfriendbutton;
	private JButton editbutton;
	private JButton achievementbar;
	private JButton homelink, storelink, librarylink, forumlink, myprofilelink;
	
	//Declare Labels 
	private JLabel avatar;
	private JLabel playername, playerlastonline, realname, program, description;
	private JLabel addfriend;
	private JLabel aboutbar;
	private JLabel friendbar;
	private JLabel historybar;
	private JLabel history1, history2, history3, history4, history5, 
	history6;
	private JLabel achievement1, achievement2, achievement3, achievement4, achievement5, 
	achievement6;
	
	//Declare Text Areas
	private JTextArea achievementarea;
	private JTextArea historyarea;
	
	//Declare Images 
	private ImageIcon picavatar, picaddfriend, 
	pichistorybar, piclocked, picunlocked, piceditbutton,
	picfriendonline, picfriendoffline, piconline, picoffline;
	
	//Declare Fonts
	Font blackbold = new Font("Verdana", Font.BOLD, 16);
	Font blacknormal = new Font("Verdana", Font.PLAIN, 14);
	Font blacksmall = new Font("Verdana", Font.PLAIN, 12);
	Font blacklink = new Font("Verdana", Font.PLAIN, 15);
	Font linkbold = new Font("Verdana", Font.BOLD, 14);
	Font sidebold = new Font("Verdana", Font.BOLD, 12);

		
	public UserScreen(Model model) throws HeadlessException{
		
		super("Profile");
		
		this.model = model;

		/*  Create Image Icons
		 * 
		 */		
		picavatar = new ImageIcon("assets/images/stark.png");
		picaddfriend = new ImageIcon("assets/images/add_friend.png");
		pichistorybar = new ImageIcon("assets/images/history_bar.png");
		piclocked = new ImageIcon("assets/images/achievement_locked.png");
		picunlocked = new ImageIcon("assets/images/achievement_unlocked.png");
		piceditbutton = new ImageIcon("assets/images/edit_button.png");
		picfriendonline = new ImageIcon("assets/images/addfriendonline.png");
		picfriendoffline = new ImageIcon("assets/images/addfriendoffline.png");
		piconline = new ImageIcon("assets/images/online.png");
		picoffline = new ImageIcon("assets/images/offline.png");
				
		/*
		 *  Create all Panels 
		 *  (Page split into 3 main panels inside a parent container 
		 *  ie. sidepanel, contentpanel, menupanel )
		 */
			    
	    addmenupanel();
	    addplayerinfopanel();
	    addplayerpanel();
	    addaboutpanel();
	    addfriendpanel();
	    addhistorypanel();
	    addachievementpanel();
	    
	    addsidepanel();
	    addcontentpanel();
	    
	    /*Add panels to Main Panel	
	     *                
	     */
	    
	    parentContainer = new JPanel(new MigLayout());
		parentContainer.add(menupanel, "dock north");
		parentContainer.add(sidepanel, "west");
		parentContainer.add(contentpanel, "east");
		add(parentContainer);
	
		/*Set the  view window constraints
		 * 
		 */
		setSize(1280,800);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		pack();
		setVisible(true);
		setResizable(false);		
	}
	
	
	/**
	 *  Method creates a side panel to hold everything on the left of page.
	 */
	public void addsidepanel(){
		
	    sidepanel = new ImagePanel(new ImageIcon("assets/images/sidebackground.png").getImage());  
	    sidepanel.setLayout(new MigLayout());
        sidepanel.add(playerpanel, "wrap, center");
        sidepanel.add(aboutpanel, "wrap, center, gap top 5px");
        sidepanel.add(friendpanel, "center");

	}
	
	/**
	 *  Method creates a content panel to hold everything on the right of page.
	 */
	public void addcontentpanel(){
		
	    contentpanel = new ImagePanel(new ImageIcon("assets/images/contentbackground.png").getImage());
		contentpanel.setLayout(new MigLayout());
	    contentpanel.add(historypanel, "wrap, height :320, width :810, gap top 20px");
        contentpanel.add(achievementpanel, "height :320, width :810");
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
	    addfriendbutton = new JButton(picfriendoffline);
	    addfriendbutton.setBorder(BorderFactory.createEmptyBorder());
	    addfriendbutton.setContentAreaFilled(false);
	    editbutton = new JButton(piceditbutton);
	    editbutton.setBorder(BorderFactory.createEmptyBorder());
	    editbutton.setContentAreaFilled(false);
	    
	    //Add Labels	    
        avatar = new JLabel();
        avatar.setIcon(picavatar);
        addfriend = new JLabel();
        addfriend.setIcon(picaddfriend);
        playername = new JLabel("Player");
        playername.setForeground(Color.white);
        playername.setFont(blackbold);
        playerlastonline = new JLabel("Last Login: 8/3/2013");
        playerlastonline.setFont(blacksmall);
        playerlastonline.setForeground(Color.white);
        
		//Add Elements to Panel
		playerinfopanel = new JPanel(new MigLayout());
		playerinfopanel.setOpaque(false);		   	
        playerinfopanel.add(playername,"wrap, align 50% 50%, gap top 30px");       
        playerinfopanel.add(playerlastonline,"wrap, align 50% 50%, gap top 5px"); 
        playerinfopanel.add(addfriendbutton, "wrap, align 50% 50%, gap top 20px");
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
	
	/**
	 *   Creates a panel listing all friends of user
	 */
	public void addfriendpanel(){
		
		friendlist = new JPanel(new MigLayout());
		friendlist.setOpaque(false);
		friendlist.setBorder(BorderFactory.createEmptyBorder());
		//ScrollPane
		friendscroll = new JScrollPane(friendlist);
		friendscroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		friendscroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		friendscroll.setOpaque(false);
		friendscroll.setBorder(BorderFactory.createEmptyBorder());
		friendscroll.getViewport().setOpaque(false);
		//Label
		friendbar = new JLabel("FRIEND LIST");
	    friendbar.setFont(sidebold);
	    friendbar.setForeground(Color.white);	
		//Add Elements to Panel
	    friendpanel = new ImagePanel(new ImageIcon("assets/images/Blue_Box.png").getImage());
        friendpanel.setLayout(new MigLayout());
	    friendpanel.add(friendbar, "gap left 10px, gap bottom 110px");
        friendpanel.add(friendscroll, "width :500px, height :100px, gap top 30px");    
		
	}
	
	/**
	 *   Creates a panel displaying player information
	 *   ie. realname, program, description
	 */
	public void addaboutpanel(){

		realname = new JLabel("Name:");
		realname.setFont(blacknormal);
		realname.setForeground(Color.white);
		program = new JLabel("Program:");
		program.setFont(blacknormal);
		program.setForeground(Color.white);
		description = new JLabel("Description:");
		description.setFont(blacknormal);
		description.setForeground(Color.white);
		
		aboutbar = new JLabel("ABOUT ME");
	    aboutbar.setFont(sidebold);
	    aboutbar.setForeground(Color.white);
	    
	    JPanel aboutlist = new JPanel(new MigLayout());
	    aboutlist.setOpaque(false);
	    aboutlist.add(realname,"wrap");
	    aboutlist.add(program,"wrap");
	    aboutlist.add(description);
		
		//Add Elements to Panel
	    aboutpanel = new ImagePanel(new ImageIcon("assets/images/Blue_Box.png").getImage());
        aboutpanel.setLayout(new MigLayout());
        aboutpanel.add(aboutbar,"gap left 10px, wrap");
        aboutpanel.add(aboutlist,"width :10px, height :120px");
        
	}
	
	/**
	 *  Method creates a panel with up to 6 set achievements &
	 *  link button to the achievements page.
	 */
	public void addachievementpanel(){
				
	    //Displayed Achievements	    
	    achievement1 = new JLabel();
	    achievement2 = new JLabel();
	    achievement3 = new JLabel();
	    achievement4 = new JLabel();
	    achievement5 = new JLabel();
	    achievement6 = new JLabel();
	    
	    achievement1.setIcon(piclocked);
	    achievement2.setIcon(piclocked);
	    achievement3.setIcon(piclocked);
	    achievement4.setIcon(piclocked);
	    achievement5.setIcon(piclocked);
	    achievement6.setIcon(piclocked);
	    
	    JPanel achievementtext1 = new JPanel(new MigLayout());
	    JPanel achievementtext2 = new JPanel(new MigLayout());
	    JPanel achievementtext3 = new JPanel(new MigLayout());
	    JPanel achievementtext4 = new JPanel(new MigLayout());
	    JPanel achievementtext5 = new JPanel(new MigLayout());
	    JPanel achievementtext6 = new JPanel(new MigLayout());
	    
	    JPanel achievementbarpanel = new JPanel(new MigLayout());
	    JPanel achievementlistpanel = new JPanel(new MigLayout());
	    
	    achievementbar = new JButton("Achievements");
	    achievementbar.setFont(linkbold);
	    achievementbar.setForeground(Color.white);
	    achievementbar.setBorder(BorderFactory.createEmptyBorder());
	    achievementbar.setContentAreaFilled(false);
	    
	    achievementbarpanel.add(achievementbar);
	    achievementbarpanel.setOpaque(false);
	    
		//Add Elements to Panel
	    achievementpanel = new ImagePanel(new ImageIcon("assets/images/Green_Box.png").getImage());
		achievementpanel.setLayout(new MigLayout());
				
        achievementlistpanel.add(achievement1);
        achievementlistpanel.add(achievementtext1,"growy, width :110");
        achievementlistpanel.add(achievement2,"");
        achievementlistpanel.add(achievementtext2,"growy, width :110");
        achievementlistpanel.add(achievement3);
        achievementlistpanel.add(achievementtext3,"wrap, growy, width :110");
        achievementlistpanel.add(achievement4);
        achievementlistpanel.add(achievementtext4,"growy, width :110");
        achievementlistpanel.add(achievement5);
        achievementlistpanel.add(achievementtext5,"growy, width :110");
        achievementlistpanel.add(achievement6);
        achievementlistpanel.add(achievementtext6,"growy, width :110");
        
        achievementlistpanel.setOpaque(false);
		
		achievementpanel.add(achievementbarpanel,"wrap");
		achievementpanel.add(achievementlistpanel);

	}
	
	/**
	 *  Creates a panel displaying the most recently played games.
	 *  (Up to 6 can be showed at a time)
	 */
	public void addhistorypanel(){
				
		historyarea = new JTextArea();		
		JScrollPane historyscroll = new JScrollPane(historyarea);
		historyscroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		historyscroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		
	    historybar = new JLabel();
        historybar.setIcon(pichistorybar);
        
        //Show Recent history Labels and game icon
        history1 = new JLabel();
        history2 = new JLabel();
        history3 = new JLabel();
        history4 = new JLabel();
        history5 = new JLabel();
        history6 = new JLabel();

        history1.setIcon(piclocked);
        history2.setIcon(piclocked);
        history3.setIcon(piclocked);
        history4.setIcon(piclocked);
        history5.setIcon(piclocked);
        history6.setIcon(piclocked);

        JPanel historytext1 = new JPanel(new MigLayout());
        JPanel historytext2 = new JPanel(new MigLayout());
        JPanel historytext3 = new JPanel(new MigLayout());
        JPanel historytext4 = new JPanel(new MigLayout());
        JPanel historytext5 = new JPanel(new MigLayout());
        JPanel historytext6 = new JPanel(new MigLayout());
        
	    historybar = new JLabel("Game History");
	    historybar.setFont(linkbold);
	    historybar.setForeground(Color.white);
	    
	    JPanel historybarpanel = new JPanel(new MigLayout());
	    historybarpanel.setOpaque(false);
	    JPanel historylistpanel = new JPanel(new MigLayout());
	    historylistpanel.setOpaque(false);
	    
        //Add Elements to Panel	    
	    historybarpanel.add(historybar);	    
        historylistpanel.add(history1);
        historylistpanel.add(historytext1,"growy, width :110");
        historylistpanel.add(history2);
        historylistpanel.add(historytext2,"growy, width :110");
        historylistpanel.add(history3);
        historylistpanel.add(historytext3,"wrap, growy, width :110");
        historylistpanel.add(history4);
        historylistpanel.add(historytext4,"growy, width :110");
        historylistpanel.add(history5);
        historylistpanel.add(historytext5,"growy, width :110");
        historylistpanel.add(history6);
        historylistpanel.add(historytext6,"growy, width :110");
        
	    historypanel = new ImagePanel(new ImageIcon("assets/images/Green_Box.png").getImage());
	    historypanel.setLayout(new MigLayout());       
        historypanel.add(historybarpanel,"wrap");
        historypanel.add(historylistpanel);
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
	 * Button navigates to the Achievements page on click
	 * @param listenForAchievementButton
	 */
	public void addAchievementListener(ActionListener listenForAchievementButton){
		
		achievementbar.addActionListener(listenForAchievementButton);
		
	}
	
	/**
	 * Button opens a popup allowing user to add friend
	 * @param listenForAddFriendButton
	 */
	public void addFriendListener(ActionListener listenForAddFriendButton){
		
		addfriendbutton.addActionListener(listenForAddFriendButton);	
		
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
		
		// make button then add here
		
	}
	
	public void displayErrorMessage(String errorMessage){
		
		JOptionPane.showMessageDialog(this, errorMessage);
		
	}
	
}



