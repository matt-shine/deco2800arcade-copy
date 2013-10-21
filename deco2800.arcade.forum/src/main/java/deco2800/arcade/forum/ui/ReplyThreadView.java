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
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
/**
 * Main class for forum interface
 *
 * @author TeamForum
 */
public class ReplyThreadView {
	public JTextField TagsTBox;
	public JTextPane textPane;
	public JButton submitBtn;
	public JButton cancelBtn;
	public JComboBox categoryCBox;
	public JFrame f;
	public JLabel lblCatgory;

	   public ReplyThreadView(JFrame frame, String title) {
		   this.f = frame;
	      //Initialize new JFrame for forum interface
		   this.f.getContentPane().setLayout(null);
	      
		   this.textPane = new JTextPane();
		   this.textPane.setBounds(65, 97, 848, 190);
		   this.f.getContentPane().add(this.textPane);
	      
		   this.submitBtn = new JButton("Submit Thread");
		   this.submitBtn.setForeground(Color.BLACK);
		   this.submitBtn.setBounds(316, 378, 127, 24);
		   this.f.getContentPane().add(this.submitBtn);
	      
		   this.cancelBtn = new JButton("Cancel");
		   this.cancelBtn.setBounds(516, 378, 79, 24);
		   this.f.getContentPane().add(this.cancelBtn);
		   addHomeLabelListener(this.cancelBtn);
		   
	      
		   JLabel lblTitle = new JLabel("Title: RE: " + title);
		   lblTitle.setBounds(43, 13, 150, 16);
		   this.f.getContentPane().add(lblTitle);
	      
		   JLabel lblMessage = new JLabel("Message:");
		   lblMessage.setBounds(46, 79, 56, 16);
		   this.f.getContentPane().add(lblMessage);
		   
		   JPanel panelTitle = new JPanel();
		   panelTitle.setBackground(Color.ORANGE);
		   panelTitle.setBounds(42, 0, 888, 41);
		   f.getContentPane().add(panelTitle);
		   
		   JPanel panel_Background = new JPanel();
		   panel_Background.setBackground(new Color(210, 105, 30));
		   panel_Background.setBounds(0, 0, 1016, 721);
		   f.getContentPane().add(panel_Background);
		      
		   this.f.setVisible(true);
		   
		   
	   }
	   
	   public void addListener(ActionListener dummy, JButton button) {
		   button.addActionListener(dummy);
	   }
	   
	   public void closeWindow() {
		   this.f.setContentPane(new JPanel(new BorderLayout()));
		   new ForumUi(this.f);
	   }
	   
		public void open_home() {
			this.f.setContentPane(new JPanel(new BorderLayout()));
			new ForumUi(this.f);
		}

	   
		private void addHomeLabelListener(JButton cancelBtn2) {
			cancelBtn2.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
					open_home();
				}
			});
		}
}

