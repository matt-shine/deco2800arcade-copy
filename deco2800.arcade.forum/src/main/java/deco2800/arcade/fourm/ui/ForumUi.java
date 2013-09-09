package deco2800.arcade.fourm.ui;

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

public class ForumUi {
	private static JTextField textField;

	   public static void main(String[] args) {
	      
	      JFrame f = new JFrame("Arcade Forum");
	      f.setSize(1024, 768);
	      f.setLocation(300,200);
	      f.getContentPane().setLayout(null);
	      
	      //Header
	      JLabel lblArcadeForum = new JLabel("ARCADE FORUM");
	      lblArcadeForum.setForeground(Color.WHITE);
	      lblArcadeForum.setFont(new Font("Tahoma", Font.BOLD, 16));
	      lblArcadeForum.setBounds(12, 13, 141, 28);
	      f.getContentPane().add(lblArcadeForum); 
	      JButton btnLogIn = new JButton("LOG IN");
	      btnLogIn.setBounds(897, 16, 97, 25);
	      f.getContentPane().add(btnLogIn);	      

	      //Menu bar 
	      JLabel lblHome = new JLabel("HOME");
	      lblHome.setFont(new Font("Cambria", Font.BOLD, 15));
	      lblHome.setBounds(26, 76, 56, 16);
	      f.getContentPane().add(lblHome);
	      
	      JLabel lblFaq = new JLabel("FAQ");
	      lblFaq.setFont(new Font("Cambria", Font.BOLD, 15));
	      lblFaq.setBounds(147, 76, 74, 16);
	      f.getContentPane().add(lblFaq);
	      
	      JButton btnSearch = new JButton("Search");
	      btnSearch.setBounds(897, 73, 97, 25);
	      f.getContentPane().add(btnSearch);
	      
	      textField = new JTextField();
	      textField.setBounds(769, 74, 116, 22);
	      f.getContentPane().add(textField);
	      textField.setColumns(10);
	      //Body
	      JTextArea textArea = new JTextArea();
	      textArea.setBackground(new Color(192, 192, 192));
	      textArea.setBounds(776, 152, 218, 220);
	      f.getContentPane().add(textArea);
	      
	      JLabel lblAnn = new JLabel("Announcement");
	      lblAnn.setFont(new Font("Tahoma", Font.BOLD, 15));
	      lblAnn.setBounds(776, 129, 141, 16);
	      f.getContentPane().add(lblAnn);
	      
	      JTextArea textArea_1 = new JTextArea();
	      textArea_1.setBackground(new Color(30, 144, 255));
	      textArea_1.setBounds(546, 152, 218, 496);
	      f.getContentPane().add(textArea_1);
	      
	      JLabel lblTag = new JLabel("Tag");
	      lblTag.setFont(new Font("Tahoma", Font.BOLD, 15));
	      lblTag.setBounds(546, 129, 97, 16);
	      f.getContentPane().add(lblTag);
	      
	      JLabel lblNewLabel = new JLabel("General Discussion");
	      lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 13));
	      lblNewLabel.setBounds(12, 155, 141, 16);
	      f.getContentPane().add(lblNewLabel);
	      
	      JLabel lblTutorial = new JLabel("Tutorial ");
	      lblTutorial.setFont(new Font("Tahoma", Font.BOLD, 13));
	      lblTutorial.setBounds(12, 280, 141, 16);
	      f.getContentPane().add(lblTutorial);
	      
	      JLabel lblReportBug = new JLabel("Report Bug");
	      lblReportBug.setFont(new Font("Tahoma", Font.BOLD, 13));
	      lblReportBug.setBounds(12, 432, 141, 16);
	      f.getContentPane().add(lblReportBug);
	      
	      JTextPane txtpnForDiscussionOf = new JTextPane();
	      txtpnForDiscussionOf.setEnabled(false);
	      txtpnForDiscussionOf.setEditable(false);
	      txtpnForDiscussionOf.setFont(new Font("Tahoma", Font.ITALIC, 13));
	      txtpnForDiscussionOf.setText("For discussion of non technical stuff");
	      txtpnForDiscussionOf.setBounds(12, 184, 520, 73);
	      f.getContentPane().add(txtpnForDiscussionOf);
	      
	      JTextPane txtpnTipsAndTrick = new JTextPane();
	      txtpnTipsAndTrick.setEnabled(false);
	      txtpnTipsAndTrick.setText("Tips and trick of doing work");
	      txtpnTipsAndTrick.setFont(new Font("Tahoma", Font.ITALIC, 13));
	      txtpnTipsAndTrick.setBounds(12, 309, 520, 73);
	      f.getContentPane().add(txtpnTipsAndTrick);
	      
	      JTextPane txtpnFacingBugIssue = new JTextPane();
	      txtpnFacingBugIssue.setEditable(false);
	      txtpnFacingBugIssue.setEnabled(false);
	      txtpnFacingBugIssue.setText("Facing bug issue? Report here");
	      txtpnFacingBugIssue.setFont(new Font("Tahoma", Font.ITALIC, 13));
	      txtpnFacingBugIssue.setBounds(14, 461, 520, 73);
	      f.getContentPane().add(txtpnFacingBugIssue);

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
	}