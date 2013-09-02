package deco2800.arcade.forum;

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


public class Interface {

   public static void main(String[] args) {
      
      JFrame f = new JFrame("A JFrame");
      f.setSize(804, 631);
      f.setLocation(300,200);
      f.getContentPane().add(BorderLayout.CENTER, new JTextArea(10, 40));
      
      JToolBar toolBar = new JToolBar();
      f.getContentPane().add(toolBar, BorderLayout.NORTH);
      
      Label label = new Label("Arcade Forum");
      label.setFont(new Font("Dialog", Font.BOLD, 18));
      toolBar.add(label);
      
      Button button_1 = new Button("Login");
      toolBar.add(button_1);
      
      Button button = new Button("Join now");
      toolBar.add(button);
      
      JPanel panel = new JPanel();
      f.getContentPane().add(panel, BorderLayout.WEST);
      
      JButton btnNewButton = new JButton("Home");
      panel.add(btnNewButton);
      
      JButton btnNewButton_1 = new JButton("profile");
      panel.add(btnNewButton_1);
      f.setVisible(true);
      
    }
}
