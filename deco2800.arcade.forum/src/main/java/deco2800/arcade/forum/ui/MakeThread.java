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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
/**
 * Main class for forum interface
 *
 * @author TeamForum
 */
public class MakeThread {
	private static JTextField TitleTBox;
	private static JTextField TagsTBox;

	   public static void main(String[] args) {
	      //Initialize new JFrame for forum interface
	      JFrame f = new JFrame("Arcade Forum");
	      f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	      f.setResizable(false);
	      f.getContentPane().setBackground(new Color(211, 211, 211));
	      f.setSize(694, 465);
	      f.setLocation(300,200);
	      f.getContentPane().setLayout(null);
	      
	      JTextPane textPane = new JTextPane();
	      textPane.setBounds(42, 97, 575, 190);
	      f.getContentPane().add(textPane);
	      
	      Button SubmitBtn = new Button("Submit New Thread");
	      SubmitBtn.setForeground(Color.BLACK);
	      SubmitBtn.setBounds(111, 378, 127, 24);
	      f.getContentPane().add(SubmitBtn);
	      
	      Button CancelBtn = new Button("Cancel");
	      CancelBtn.addActionListener(new ActionListener() {
	      	public void actionPerformed(ActionEvent arg0) {
	      		
	      		System.exit(0);
	      	}
	      });
	      CancelBtn.setBounds(464, 378, 79, 24);
	      f.getContentPane().add(CancelBtn);
	      
	      TitleTBox = new JTextField();
	      TitleTBox.setBounds(42, 32, 262, 22);
	      f.getContentPane().add(TitleTBox);
	      TitleTBox.setColumns(10);
	      
	      JLabel lblTitle = new JLabel("Title:");
	      lblTitle.setBounds(40, 13, 56, 16);
	      f.getContentPane().add(lblTitle);
	      
	      JLabel lblMessage = new JLabel("Message:");
	      lblMessage.setBounds(42, 79, 56, 16);
	      f.getContentPane().add(lblMessage);
	      
	      TagsTBox = new JTextField();
	      TagsTBox.setBounds(42, 321, 167, 22);
	      f.getContentPane().add(TagsTBox);
	      TagsTBox.setColumns(10);
	      
	      JLabel lblTags = new JLabel("Tags:");
	      lblTags.setBounds(42, 302, 56, 16);
	      f.getContentPane().add(lblTags);
	      f.setVisible(true);
	      
	    }
	   
	   
	public void addListener(ActionListener dummy, JButton button) {
		button.addActionListener(dummy);
		}
	}

