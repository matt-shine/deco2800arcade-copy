package deco2800.arcade.guesstheword.GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.GroupLayout.ParallelGroup;
import javax.swing.GroupLayout.SequentialGroup;

import java.awt.event.ActionListener;


public class GuessTheWordGUI extends JFrame {

	
	private JLabel gameTitle; 

	public  JTextArea gameInfoTextArea;
	
	private JScrollPane gameInfoScp;

	
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
	
	public GuessTheWordGUI(){
		
		createMainGUI();
		
		Container c = getContentPane();
		createGameTitleLabel(c);
		createGameDetailArea(c);
		createUserLoginButton(c);
		createAchieveMentButton(c);
		createHelpButton(c);

		pack();
	}
	
	private void createMainGUI(){
//		setLayout(new BorderLayout());
		setLayout(new GridBagLayout());
		setTitle("Guess the Word"); 
		setResizable(false);
		setBounds(300,100,800,600);
		
		//setSize(800, 600);
		//setSize(screenSize.width, screenSize.height);
		setVisible(true);
		// The program will stop when the window is close.
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
	}
	
	private void createGameTitleLabel(Container c){

		JPanel titlePanel = new JPanel();
		gameTitle = new JLabel("GUESS THE WORD");
		gameTitle.setFont(new Font("SanSerif", Font.BOLD, 48));
		titlePanel.add(gameTitle);
		
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		
		c.add(titlePanel,gbc);
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
		
		c.add(gameDetailPanel,gbc);
	}
	
	private void createUserLoginButton(Container c){
		JPanel loginPanel = new JPanel();
		loginButton = new JButton("Login");
		loginButton.setPreferredSize(new Dimension(200 , 50));
		
		loginPanel.add(loginButton);
		
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 2;
		
		c.add(loginPanel,gbc);
	}
	
	private void createAchieveMentButton(Container c){
		JPanel achievePanel = new JPanel();
		
		achieveButton = new JButton("Achievement");
		achieveButton.setPreferredSize(new Dimension(200 , 50));
		
		achievePanel.add(achieveButton);

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 3;
		
		c.add(achievePanel,gbc);
	}
	
	private void createHelpButton(Container c){
		JPanel helpPanel = new JPanel();
		
		helpButton = new JButton("Help");
		helpButton.setPreferredSize(new Dimension(200 , 50));
		
		helpPanel.add(helpButton);
		
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 4;
		
		c.add(helpPanel,gbc);
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
		new GuessTheWordGUI();
	}

}