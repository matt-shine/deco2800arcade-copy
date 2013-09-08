package deco2800.arcade.guesstheword.GUI;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;

import javax.imageio.ImageIO;
import javax.swing.*;

import deco2800.arcade.guesstheword.gameplay.WordShuffler;

import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;


public class GuessTheWordGUI extends JFrame {

	private JLabel gameTitle; 

	public  JTextArea gameInfoTextArea;
	
	private JScrollPane gameInfoScp;

	private static JPanel gamepanel;

	private JPanel mainPanel;
	/**
	 * Button for User Login
	 * */
	public JButton loginButton;

	/**
	 * Button for Achievement
	 * */
	public JButton achieveButton;
	/**
	 * Button for Help
	 * */
	public JButton helpButton;
	private Graphics g;
	private Image backgroundImage = null;
	
	  public void paint( Graphics g ) { 
	    super.paint(g);
	    g.drawImage(backgroundImage, 0, 0, null);
	  }
	
	public GuessTheWordGUI(){
		
		createMainGUI();
		mainPanel = new JPanel();
		mainPanel.setLayout(new GridBagLayout());
		
		Container c = getContentPane();
		
		createGameTitleLabel(c);
		createGameDetailArea(c);
		createUserLoginButton(c);
		createAchieveMentButton(c);
		createHelpButton(c);
		c.add(mainPanel);
		addGamePanel(c);
		//pack();
	}
	
	private void createMainGUI(){
//		setLayout(new BorderLayout());
		setLayout(new GridBagLayout());
		setTitle("Guess the Word"); 
		setResizable(false);
		setBounds(300,100,700,600);
		
		//setSize(800, 600);
		//setSize(screenSize.width, screenSize.height);
		setVisible(true);
		// The program will stop when the window is close.
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
	}
	
	private void addGamePanel(Container c){
		gamepanel = new GamePage();
		gamepanel.setVisible(false);
		c.add(gamepanel);
	}
	
	public void openGamePanel(){
		mainPanel.setVisible(false);
		gamepanel.setPreferredSize(new java.awt.Dimension(700, 500)); 
		gamepanel.setVisible(true);
	}
	private void createGameTitleLabel(Container c){

		JPanel titlePanel = new JPanel();
		gameTitle = new JLabel("GUESS THE WORD");
		gameTitle.setFont(new Font("SanSerif", Font.BOLD, 48));
		titlePanel.add(gameTitle);
		
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		
		mainPanel.add(titlePanel,gbc);
	}
	
	private void createGameDetailArea(Container c){
		JPanel gameDetailPanel = new JPanel();
	
		gameInfoTextArea = new JTextArea(getGameInfo());
		gameInfoTextArea.setFont(new Font("SanSerif", Font.BOLD, 18));
		gameInfoTextArea.setBackground(Color.WHITE);
//		gameInfoTextArea.setLineWrap(true);
//		gameInfoTextArea.setWrapStyleWord(true);
		gameInfoTextArea.setEditable(false);
		
		gameInfoScp = new JScrollPane(gameInfoTextArea);
		gameInfoScp.setSize(100, 150);
		
		gameDetailPanel.add(gameInfoScp);
		
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 1;
		
		mainPanel.add(gameDetailPanel,gbc);
	}
	
	private void createUserLoginButton(Container c){
		JPanel loginPanel = new JPanel();
		loginButton = new JButton("Play Game");
		loginButton.setPreferredSize(new Dimension(200 , 50));
		
		loginPanel.add(loginButton);
		
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 2;
		
		mainPanel.add(loginPanel,gbc);
	}
	
	private void createAchieveMentButton(Container c){
		JPanel achievePanel = new JPanel();
		
		achieveButton = new JButton("Achievement");
		achieveButton.setPreferredSize(new Dimension(200 , 50));
		
		achievePanel.add(achieveButton);

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 3;
		
		mainPanel.add(achievePanel,gbc);
	}
	
	private void createHelpButton(Container c){
		JPanel helpPanel = new JPanel();
		
		helpButton = new JButton("Help");
		helpButton.setPreferredSize(new Dimension(200 , 50));
		
		helpPanel.add(helpButton);
		
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 4;
		
		mainPanel.add(helpPanel,gbc);
	}
	
	private String getGameInfo(){
		
		String info = "Guess the word! Is an interactive game for all.\n" +
				"The idea is to guess the word from a set of pictures.\n" +
				"Points are allocated based on the number of picture aid used. \n" +
				"In order to play, please register an account or \n" +
				"login if you already have a game arcade account.\n";
		
		return info;
	}
	
	/**
	 * Methods to add the ActionListner for loginButton button.
	 * 
	 * @param 
	 * 			pl is the Action Listener created
	 */
	public void loginButtonListener(ActionListener pl) {
		loginButton.addActionListener(pl);
	}
	/**
	 * Methods to add the ActionListner for achieveButton button.
	 * 
	 * @param 
	 * 			pl is the Action Listener created
	 */
	public void achieveButtonListener(ActionListener pl) {
		achieveButton.addActionListener(pl);
	}
	/**
	 * Methods to add the ActionListner for helpButton button.
	 * 
	 * @param 
	 * 			pl is the Action Listener created
	 */
	public void helpButtonListener(ActionListener pl) {
		helpButton.addActionListener(pl);
	}
	
	
	public static void main(String[] args){

	     try {
	            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
	                if ("Nimbus".equals(info.getName())) {
	                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
	                    break;
	                }
	            }
	        } catch (ClassNotFoundException ex) {
	            java.util.logging.Logger.getLogger(GuessTheWordGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
	        } catch (InstantiationException ex) {
	            java.util.logging.Logger.getLogger(GuessTheWordGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
	        } catch (IllegalAccessException ex) {
	            java.util.logging.Logger.getLogger(GuessTheWordGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
	        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
	            java.util.logging.Logger.getLogger(GuessTheWordGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
	        }
		
		new GuessTheWordController(new GuessTheWordGUI());
		
	}

}