package deco2800.arcade.fourm.ui;


import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.Color;


public class ForumLogin {
	private static JTextField textField;
	private static JTextField textField_1;
	

   public static void main(String[] args) {
      
      JFrame f = new JFrame("A JFrame");
      f.setSize(423, 295);
      f.setLocation(300,200);
      f.getContentPane().setLayout(null);
      
      JLabel lblUsername = new JLabel("USERNAME");
      lblUsername.setFont(new Font("Tahoma", Font.BOLD, 13));
      lblUsername.setBounds(158, 35, 93, 16);
      f.getContentPane().add(lblUsername);
      
      JLabel lblPassword = new JLabel("PASSWORD");
      lblPassword.setFont(new Font("Tahoma", Font.BOLD, 13));
      lblPassword.setBounds(156, 97, 75, 16);
      f.getContentPane().add(lblPassword);
      
      textField = new JTextField();
      textField.setBounds(116, 62, 160, 22);
      f.getContentPane().add(textField);
      textField.setColumns(10);
      
      textField_1 = new JTextField();
      textField_1.setColumns(10);
      textField_1.setBounds(116, 126, 163, 22);
      f.getContentPane().add(textField_1);
      
      JButton btnNewButton = new JButton("Log Me In");
      btnNewButton.setBounds(99, 183, 97, 25);
      f.getContentPane().add(btnNewButton);
      
      JButton btnCancel = new JButton("Cancel");
      btnCancel.setBounds(208, 183, 97, 25);
      f.getContentPane().add(btnCancel);
      
      JLabel lblForgotPassword = new JLabel("Forgot password?");
      lblForgotPassword.setForeground(Color.GRAY);
      lblForgotPassword.setBounds(146, 150, 115, 16);
      f.getContentPane().add(lblForgotPassword);
      f.setVisible(true);
      
    }
}