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
public class MakeThreadView {
	public JTextField TitleTBox;
	public JTextField TagsTBox;
	public JTextPane textPane;
	public JButton submitBtn;
	public JButton cancelBtn;
	public JComboBox categoryCBox;
	public JFrame f;
	public JLabel lblCatgory;

	   public MakeThreadView(JFrame frame) {
		   this.f = frame;
	      //Initialize new JFrame for forum interface
		   this.f.getContentPane().setLayout(null);
	      
		   this.textPane = new JTextPane();
		   this.textPane.setBounds(42, 97, 848, 190);
		   this.f.getContentPane().add(this.textPane);
	      
		   this.submitBtn = new JButton("Submit Thread");
		   this.submitBtn.setForeground(Color.BLACK);
		   this.submitBtn.setBounds(316, 378, 127, 24);
		   this.f.getContentPane().add(this.submitBtn);
	      
		   this.cancelBtn = new JButton("Cancel");
		   this.cancelBtn.setBounds(516, 378, 79, 24);
		   this.f.getContentPane().add(this.cancelBtn);
		   addHomeLabelListener(this.cancelBtn);
		   
	      
		   
		   this.TitleTBox = new JTextField();
		   this.TitleTBox.setBounds(42, 32, 262, 22);
		   this.f.getContentPane().add(this.TitleTBox);
		   this.TitleTBox.setColumns(10);
	      
		   JLabel lblTitle = new JLabel("Title:");
		   lblTitle.setBounds(40, 13, 56, 16);
		   this.f.getContentPane().add(lblTitle);
	      
		   JLabel lblMessage = new JLabel("Message:");
		   lblMessage.setBounds(42, 79, 56, 16);
		   this.f.getContentPane().add(lblMessage);
	      
		   this.TagsTBox = new JTextField();
		   this.TagsTBox.setBounds(42, 321, 167, 22);
		   this.f.getContentPane().add(this.TagsTBox);
		   this.TagsTBox.setColumns(10);
	      
		   JLabel lblTags = new JLabel("Tags:");
		   lblTags.setBounds(42, 302, 56, 16);
		   this.f.getContentPane().add(lblTags);
		   
		   
		   this.categoryCBox = new JComboBox();
		   this.categoryCBox.setModel(new DefaultComboBoxModel(new String[] {"General_Discussion", "Tutorial", "Report_Bug", "Others"}));
		   this.categoryCBox.setBounds(748, 321, 142, 22);
		   this.f.getContentPane().add(this.categoryCBox);
		      
		   this.lblCatgory = new JLabel("Category:");
		   this.lblCatgory.setBounds(751, 300, 56, 16);
		   f.getContentPane().add(this.lblCatgory);
		   
		   
		   
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

