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
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;
import deco2800.arcade.userui.model.Model;

public class UserProfile extends JFrame implements ActionListener {
	
	/**
	 * The view class for the main page of the user profile page
	 */
	
	private Model model;
	
	//Declare JPanel and ImagePanels
	private JPanel parentContainer;	
	private ImagePanel menupanel;	
	private JPanel contentpanel;
	private JPanel achievementpanel;
	private JPanel historypanel;	
	private JPanel sidepanel;
	private JPanel playerinfopanel;
	private JPanel playerpanel;
	private JPanel friendpanel;
	private JPanel aboutpanel;
	
	//Declare Buttons 
	private JButton addfriendbutton;
	private JButton editbutton;
	private JButton myprofilelink;
	private JButton homelink;
	private JButton storelink;
	private JButton gameslink;
	
	//Declare Labels 
	private JLabel avatar;
	private JLabel addfriend;
	private JLabel playername;
	private JLabel playerlevel;
	private JLabel aboutbar;
	private JLabel friendbar;
	private JLabel historybar;
	private JLabel achievementbar;
	
	//Declare Text Areas
	private JTextArea achievementarea;
	private JTextArea aboutarea;
	private JTextArea historyarea;
	
	//Declare Images 
	private ImageIcon picavatar;
	private ImageIcon picaboutbar;
	private ImageIcon picfriendbar;
	private ImageIcon picaddfriend;
	private ImageIcon pichistorybar;
	private ImageIcon picachievementbar;
	private ImageIcon piclocked;
	private ImageIcon picunlocked;
	private ImageIcon piceditbutton;
	
	//Declare Fonts
	Font blackbold = new Font("Verdana", Font.BOLD, 16);
	Font blacknormal = new Font("Verdana", Font.PLAIN, 14);
	Font blacklink = new Font("Verdana", Font.PLAIN, 15);
	
		
	public UserProfile(Model model) throws HeadlessException {
		
		super("Profile");
		
		this.model = model;

		/*  Create Image Icons
		 * 
		 */		
		picavatar = new ImageIcon("assets/images/stark.png");
		picaboutbar = new ImageIcon("assets/images/about_bar.png");
		picfriendbar = new ImageIcon("assets/images/friendbar.png");
		picaddfriend = new ImageIcon("assets/images/add_friend.png");
		pichistorybar = new ImageIcon("assets/images/history_bar.png");
		picachievementbar = new ImageIcon("assets/images/achievement_bar.png");
		piclocked = new ImageIcon("assets/images/achievement_locked.png");
		picunlocked = new ImageIcon("assets/images/achievement_unlocked.png");
		piceditbutton = new ImageIcon("assets/images/edit_button.png");
				
		/*
		 *  Create all Panels 
		 *  (Page split into 3 main panels inside a parent container 
		 *  ie. sidepanel, contentpanel, menupanel )
		 */
		parentContainer = new JPanel(new MigLayout());
			    
	    addmenupanel();
	    addplayerinfopanel();
	    addplayerpanel();
	    addaboutpanel();
	    addfriendpanel();
	    addhistorypanel();
	    addachievementpanel();
	    
	    sidepanel = new JPanel(new MigLayout());        
        sidepanel.add(playerpanel, "wrap, center, width :300, height :300");
        sidepanel.add(aboutpanel, "wrap, center, width :300, height :300");
        sidepanel.add(friendpanel, "center, width :300, height :300");
             
	    contentpanel = new JPanel(new MigLayout());
        contentpanel.add(historypanel, "wrap, height :320, width :810, gapbefore 30px");
        contentpanel.add(achievementpanel, "height :320, width :810, gapbefore 30px");
            
	    /*Add panels to Main Panel	
	     *                
	     */
		parentContainer.add(menupanel, "dock north");
		sidepanel.setBackground(Color.darkGray);
		parentContainer.add(sidepanel, "west, width :350, height :700");
		contentpanel.setBackground(Color.gray);
		parentContainer.add(contentpanel, "east, width :950, height :700");

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
	
	
	/*
	 *  Holds the player info and avatar
	 */
	public void addplayerpanel(){
		
	    playerpanel = new JPanel(new MigLayout());	    
	    playerpanel.setOpaque(false);
		playerpanel.add(avatar);
        playerpanel.add(playerinfopanel);
    
	}
	
	/*
	 * Displays all the player information
	 */
	public void addplayerinfopanel(){
		
		//Add Buttons
	    addfriendbutton = new JButton(picaddfriend);
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
        playername = new JLabel("Stark");
        playername.setForeground(Color.white);
        playername.setFont(blackbold);
        playerlevel = new JLabel("Level 20");
        playerlevel.setFont(blacknormal);
        playerlevel.setForeground(Color.white);
        
		//Add Elements to Panel
		playerinfopanel = new JPanel(new MigLayout());
		playerinfopanel.setOpaque(false);		   	
        playerinfopanel.add(playername,"wrap, align 50% 50%");       
        playerinfopanel.add(playerlevel,"wrap, align 50% 50%"); 
        playerinfopanel.add(addfriendbutton, "wrap, align 50% 50%");
        playerinfopanel.add(editbutton,"align 50% 50%");
        
	}
	
	/*
	 *  The top navigation bar
	 */
	public void addmenupanel(){
		
	    //Page Button Links
	    myprofilelink = new JButton("My Profile");
	    myprofilelink.setFont(blacklink);
	    myprofilelink.setBorder(BorderFactory.createEmptyBorder());
	    myprofilelink.setContentAreaFilled(false);
	    myprofilelink.setForeground(Color.WHITE);
	    homelink = new JButton("Home");
	    homelink.setFont(blacklink);
	    homelink.setForeground(Color.white);
	    homelink.setBorder(BorderFactory.createEmptyBorder());
	    homelink.setContentAreaFilled(false);
	    gameslink = new JButton("MyGames");
	    gameslink.setFont(blacklink);
	    gameslink.setForeground(Color.white);
	    gameslink.setBorder(BorderFactory.createEmptyBorder());
	    gameslink.setContentAreaFilled(false);
	    storelink = new JButton("Game-Store");
	    storelink.setFont(blacklink);
	    storelink.setForeground(Color.white);
	    storelink.setBorder(BorderFactory.createEmptyBorder());
	    storelink.setContentAreaFilled(false);
	    
		//Add Elements to Panel
	    menupanel = new ImagePanel(new ImageIcon("assets/images/menu_bar.png").getImage());
	    menupanel.setLayout(new MigLayout());	    
        menupanel.add(homelink, "center, gapbefore 250px");
        menupanel.add(storelink, "center, gapbefore 100px");
        menupanel.add(gameslink, "center, gapbefore 100px");
        menupanel.add(myprofilelink,"center, gapbefore 470px");	    		
	}
	
	/*
	 *  Friends of this particular user, which are being stored
	 */
	public void addfriendpanel(){
		
		//ScrollPane
		JPanel friendarea = new JPanel();
		JScrollPane friendscroll = new JScrollPane(friendarea);
		friendscroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		friendscroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		
		//Label
		friendbar = new JLabel();
	    friendbar.setIcon(picfriendbar);
		
		//Add Elements to Panel
	    friendpanel = new JPanel(new MigLayout());
        friendpanel.add(friendbar, "north");
        friendpanel.add(friendscroll,"width :100%, height :100%");    
		
	}
	
	/*
	 *  Displays information about the user
	 */
	public void addaboutpanel(){
		
		//Textarea
		aboutarea = new JTextArea();
		aboutarea.setLineWrap(true);
		aboutarea.setFont(blacknormal);
		aboutarea.setLineWrap(true);
		
		//ScrollPane
		JScrollPane aboutscroll = new JScrollPane(aboutarea);
		aboutscroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		aboutscroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		
		//Label
		aboutbar = new JLabel();
	    aboutbar.setIcon(picaboutbar);
		
		//Add Elements to Panel
	    aboutpanel = new JPanel(new MigLayout());
        aboutpanel.add(aboutbar,"north");
        aboutpanel.add(aboutscroll, "width :100%, height :100%");
        
	}
	
	/*
	 * Shows chosen achievements listed by user
	 */
	public void addachievementpanel(){
				
		achievementarea = new JTextArea();		
	    achievementbar = new JLabel();
	    achievementbar.setIcon(picachievementbar);
	    
	    //Displayed Achievements
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
	    
		//Add Elements to Panel
	    achievementpanel = new JPanel(new MigLayout());
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
		
	}
	
	/*
	 * Shows the most recent games played by the user
	 */
	public void addhistorypanel(){
				
		historyarea = new JTextArea();		
		JScrollPane historyscroll = new JScrollPane(historyarea);
		historyscroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		historyscroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		
	    historybar = new JLabel();
        historybar.setIcon(pichistorybar);
        
        //Show Recent history Labels and game icon
        JLabel history1 = new JLabel();
        JLabel history2 = new JLabel();
        JLabel history3 = new JLabel();
        JLabel history4 = new JLabel();
        JLabel history5 = new JLabel();
        JLabel history6 = new JLabel();

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

        historytext1.setBackground(Color.BLUE);
        historytext2.setBackground(Color.BLUE);
        historytext3.setBackground(Color.BLUE);
        historytext4.setBackground(Color.BLUE);
        historytext5.setBackground(Color.BLUE);
        historytext6.setBackground(Color.BLUE);
        
        //Add Elements to Panel
	    historypanel = new JPanel(new MigLayout());
        historypanel.add(historybar, "north");
        historypanel.add(history1,"gapbefore 5px, pad 10 10 10 10");
        historypanel.add(historytext1,"pad 10 10 10 10, growy, width :110");
        historypanel.add(history2,"pad 10 10 10 10");
        historypanel.add(historytext2,"pad 10 10 10 10, growy, width :110");
        historypanel.add(history3,"pad 10 10 10 10");
        historypanel.add(historytext3,"pad 10 10 10 10, growy, width :110");
        historypanel.add(history4,"pad 10 10 10 10");
        historypanel.add(historytext4,"wrap,pad 10 10 10 10, growy, width :110");
        historypanel.add(history5,"gapbefore 5px, pad 10 10 10 10");
        historypanel.add(historytext5,"pad 10 10 10 10, growy, width :110");
        historypanel.add(history6,"pad 10 10 10 10");
        historypanel.add(historytext6,"pad 10 10 10 10, growy, width :110");		
	}
	

	/**
	 *  Event Listeners for Buttons
	 */
	
	@Override
	public void actionPerformed(ActionEvent e) {

		
	}
	
	
}


/*
 * Class for adding background Image to JPanels
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
