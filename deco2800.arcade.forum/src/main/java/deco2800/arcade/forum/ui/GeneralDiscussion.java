package deco2800.arcade.forum.ui;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import java.awt.TextField;
import javax.swing.JButton;
import javax.swing.JToolBar;
import java.awt.Label;
import java.awt.Button;
import java.awt.Font;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.Color;
import javax.swing.JTextPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class GeneralDiscussion {
	private static JTextField textField;

	   public static void main(String[] args) {
	      
	      JFrame f = new JFrame("Arcade Forum");
	      f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	      f.setSize(1024, 768);
	      f.setLocation(300,200);
	      f.getContentPane().setLayout(null);
	      
	      JButton btnLogIn = new JButton("LOG IN");
	   	      
	      btnLogIn.setBounds(897, 16, 97, 25);
	      f.getContentPane().add(btnLogIn);
	      
	      JButton btnSearch = new JButton("Search");
	      btnSearch.setBounds(897, 73, 97, 25);
	      f.getContentPane().add(btnSearch);
	      
	      JButton btnNewButton = new JButton("New Thread");
	      btnNewButton.setBounds(7, 194, 116, 25);
	      f.getContentPane().add(btnNewButton);
	      
	      JLabel lblArcadeForum = new JLabel("ARCADE FORUM");
	      lblArcadeForum.setForeground(Color.WHITE);
	      lblArcadeForum.setFont(new Font("Tahoma", Font.BOLD, 16));
	      lblArcadeForum.setBounds(12, 13, 141, 28);
	      f.getContentPane().add(lblArcadeForum);
	      
	      JLabel lblHome = new JLabel("HOME");
	      lblHome.setFont(new Font("Cambria", Font.BOLD, 15));
	      lblHome.setBounds(26, 76, 56, 16);
	      f.getContentPane().add(lblHome);
	      
	      JLabel lblFaq = new JLabel("FAQ");
	      lblFaq.setFont(new Font("Cambria", Font.BOLD, 15));
	      lblFaq.setBounds(147, 76, 74, 16);
	      f.getContentPane().add(lblFaq);
	      
	      JLabel lblAnn = new JLabel("Announcement");
	      lblAnn.setFont(new Font("Tahoma", Font.BOLD, 15));
	      lblAnn.setBounds(776, 129, 141, 16);
	      f.getContentPane().add(lblAnn);
	      
	      JLabel lblTag = new JLabel("Tag");
	      lblTag.setFont(new Font("Tahoma", Font.BOLD, 15));
	      lblTag.setBounds(546, 129, 97, 16);
	      f.getContentPane().add(lblTag);
	      
	      JLabel lblNewLabel = new JLabel("General Discussion");
	      lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
	      lblNewLabel.setBounds(12, 155, 163, 26);
	      f.getContentPane().add(lblNewLabel);
	      
	      textField = new JTextField();
	      textField.setBounds(769, 74, 116, 22);
	      f.getContentPane().add(textField);
	      textField.setColumns(10);
	      
	      JTextArea textArea = new JTextArea();
	      textArea.setBackground(new Color(192, 192, 192));
	      textArea.setBounds(776, 152, 218, 220);
	      f.getContentPane().add(textArea);
	      
	      JTextArea textArea_1 = new JTextArea();
	      textArea_1.setBackground(new Color(30, 144, 255));
	      textArea_1.setBounds(546, 152, 218, 496);
	      f.getContentPane().add(textArea_1);
	      
	      JPanel panel = new JPanel();
	      panel.setBackground(Color.ORANGE);
	      panel.setBounds(0, 59, 1006, 57);
	      f.getContentPane().add(panel);
	      
	      JPanel panel_1 = new JPanel();
	      panel_1.setBackground(Color.DARK_GRAY);
	      panel_1.setBounds(0, 0, 1006, 57);
	      f.getContentPane().add(panel_1);
	      f.setVisible(true);
	      
	    }

	public static void setContentPanel(JPanel j) {
		// TODO Auto-generated method stub
		
	}

	public void setVisible(boolean b) {
		// TODO Auto-generated method stub
		
	}
	}