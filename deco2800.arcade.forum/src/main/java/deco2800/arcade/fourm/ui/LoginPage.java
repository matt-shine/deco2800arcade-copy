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

public class LoginPage {
	private static JTextField textField;
	private static JTextField textField_1;

   public static void main(String[] args) {
      
      JFrame f = new JFrame("Login");
      f.setSize(387, 226);
      f.setLocation(300,200);
      f.getContentPane().setLayout(null);
      
      JLabel lblUserName = new JLabel("User Name:");
      lblUserName.setFont(new Font("Tahoma", Font.PLAIN, 15));
      lblUserName.setBounds(67, 46, 96, 16);
      f.getContentPane().add(lblUserName);
      
      JLabel lblPassword = new JLabel("Password:");
      lblPassword.setFont(new Font("Tahoma", Font.PLAIN, 15));
      lblPassword.setBounds(77, 75, 96, 16);
      f.getContentPane().add(lblPassword);
      
      textField = new JTextField();
      textField.setBounds(175, 44, 116, 22);
      f.getContentPane().add(textField);
      textField.setColumns(10);
      
      textField_1 = new JTextField();
      textField_1.setBounds(175, 73, 116, 22);
      f.getContentPane().add(textField_1);
      textField_1.setColumns(10);
      
      JButton btnLoginIn = new JButton("Login in");
      btnLoginIn.setBounds(67, 117, 97, 25);
      f.getContentPane().add(btnLoginIn);
      
      JButton btnCancel = new JButton("Cancel");
      btnCancel.setBounds(203, 117, 97, 25);
      f.getContentPane().add(btnCancel);
      
      JLabel label = new JLabel("");
      label.setBounds(193, 97, 56, 16);
      f.getContentPane().add(label);
      f.setVisible(true);
      
    }
}