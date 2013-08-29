package deco2800.arcade.guesstheword.GUI;

import java.awt.Container;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.*;
import java.awt.event.ActionListener;


public class GuessTheWordGUI {
	
	private JFrame mainFrame;
	private JPanel mainPanel;
	
	private JTextArea gameTitle; 
	public  JTextArea gameInfoTextArea;
	
	/**
	 * Button for User Login
	 * */
	public JButton userButton;

	/**
	 * Button for Achievement
	 * */
	public JButton achieveButton;
	/**
	 * Button for Help
	 * */
	public JButton helpButton;
	
	private Font titleFont = new Font("SanSerif", Font.BOLD, 48);
	
	public GuessTheWordGUI(){
		Container c = new Container();
		createMainGUI(c);
	}
	
	public void createMainGUI(Container c){
		
		GridBagConstraints gbc = new GridBagConstraints();
		
		mainFrame = new JFrame();
		mainFrame.setLayout(new GridBagLayout());
		mainFrame.setTitle("Guess the Word"); 
		mainFrame.setResizable(false);
		mainFrame.setSize(800, 600);
		
		gameTitle = new JTextArea("GUESS THE WORD");
		gameTitle.setFont(new Font("SanSerif", Font.BOLD, 48));
		gameTitle.setEditable(false);
		
		gameInfoTextArea = new JTextArea(getGameInfo());
		gameInfoTextArea.setFont(new Font("SanSerif", Font.BOLD, 20));
		gameInfoTextArea.setEditable(false);
		
		gbc.anchor = GridBagConstraints.NORTH;
		gbc.gridx = 0; 
		gbc.gridy = 0;
		mainFrame.add(gameTitle, gbc);
		gbc.gridx = 0; 
		gbc.gridy = 1;
		mainFrame.add(gameInfoTextArea, gbc);
		
		mainFrame.setVisible(true);
		// The program will stop when the window is close.
		mainFrame.setDefaultCloseOperation(mainFrame.EXIT_ON_CLOSE);
	}
	
	public void createUserLogin(Container c){
		
	}
	
	
	private String getGameInfo(){
		
		String info = "Guess the word! Is an interactive game for all. \n" +
				"The idea is to guess the word from a set of pictures. \n" +
				"Points are allocated based on the number of picture aid used. " +
				"\nIn order to play, please register an account or " +
				"login if you already have a game arcade account.";
		
		return info;
	}
	public static void main(String[] args){
		new GuessTheWordGUI();
	}

}