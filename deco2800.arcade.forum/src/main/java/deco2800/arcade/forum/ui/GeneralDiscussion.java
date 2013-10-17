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
 * Main class for General Discussion page
 *
 * @author TeamForum
 */
public class GeneralDiscussion {
	private JTextField textField;
	private JPanel panel_1;
	private JPanel panel;
	private JTextArea textArea_1;
	private JTextArea textArea;
	private JLabel lblNewLabel;
	private JLabel lblTag;
	private JLabel lblAnn;
	private JLabel lblFaq;
	private JLabel lblHome;
	private JLabel lblArcadeForum;
	private JButton btnNewButton;
	private JButton btnSearch;
	private JFrame f;
	

	public GeneralDiscussion(JFrame window) {
		this.f = window;
		f.getContentPane().setLayout(null);
	      
	    JButton btnLogIn = new JButton("LOG IN");
	   	      
	    btnLogIn.setBounds(897, 16, 97, 25);
	    f.getContentPane().add(btnLogIn);
	      
	    this.btnSearch = new JButton("Search");
	    this.btnSearch.setBounds(897, 73, 97, 25);
	    f.getContentPane().add(this.btnSearch);
	      
	    this.btnNewButton = new JButton("New Thread");
	    this.btnNewButton.setBounds(7, 194, 116, 25);
	    f.getContentPane().add(this.btnNewButton);
	    addMakeThreadListener(this.btnNewButton);
	      
	    this.lblArcadeForum = new JLabel("ARCADE FORUM");
	    this.lblArcadeForum.setForeground(Color.WHITE);
	    this.lblArcadeForum.setFont(new Font("Tahoma", Font.BOLD, 16));
	    this.lblArcadeForum.setBounds(12, 13, 141, 28);
	    f.getContentPane().add(this.lblArcadeForum);
	    
	    this.lblHome = new JLabel("HOME");
	    this.lblHome.setFont(new Font("Cambria", Font.BOLD, 15));
	    this.lblHome.setBounds(26, 76, 56, 16);
	    f.getContentPane().add(this.lblHome);
	    addHomeLabelListener(this.lblHome);
	      
	    this.lblFaq = new JLabel("FAQ");
	    this.lblFaq.setFont(new Font("Cambria", Font.BOLD, 15));
	    this.lblFaq.setBounds(147, 76, 74, 16);
	    f.getContentPane().add(this.lblFaq);
	     
	    this.lblAnn = new JLabel("Announcement");
	    this.lblAnn.setFont(new Font("Tahoma", Font.BOLD, 15));
	    this.lblAnn.setBounds(776, 129, 141, 16);
	    f.getContentPane().add(this.lblAnn);
	      
	    this.lblTag = new JLabel("Tag");
	    this.lblTag.setFont(new Font("Tahoma", Font.BOLD, 15));
	    this.lblTag.setBounds(546, 129, 97, 16);
	    f.getContentPane().add(this.lblTag);
	      
	    this.lblNewLabel = new JLabel("General Discussion");
	    this.lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
	    this.lblNewLabel.setBounds(12, 155, 163, 26);
	    f.getContentPane().add(this.lblNewLabel);
	      
	    this.textField = new JTextField();
	    this.textField.setBounds(769, 74, 116, 22);
	    f.getContentPane().add(this.textField);
	    this.textField.setColumns(10);
	      
	    this.textArea = new JTextArea();
	    this.textArea.setBackground(new Color(192, 192, 192));
	    this.textArea.setBounds(776, 152, 218, 220);
	    f.getContentPane().add(this.textArea);
	      
	    this.textArea_1 = new JTextArea();
	    this.textArea_1.setBackground(new Color(30, 144, 255));
	    this.textArea_1.setBounds(546, 152, 218, 496);
	    f.getContentPane().add(this.textArea_1);
	      
	    this.panel = new JPanel();
	    this.panel.setBackground(Color.ORANGE);
	    this.panel.setBounds(0, 59, 1006, 57);
	    f.getContentPane().add(this.panel);
	      
	    this.panel_1 = new JPanel();
	    this.panel_1.setBackground(Color.DARK_GRAY);
	    this.panel_1.setBounds(0, 0, 1006, 57);
	    f.getContentPane().add(this.panel_1);
	    f.setVisible(true);      
	}

	public void open_home() {
		this.f.setContentPane(new JPanel(new BorderLayout()));
		new ForumUi(this.f);
	}

	   
	private void addHomeLabelListener(JLabel label) {
		label.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				open_home();
			}
		});
	}
	   
	public void open_MakeThread() {
		this.f.setContentPane(new JPanel(new BorderLayout()));
	   	new MakeThread();
	}
	   	
	private void addMakeThreadListener(JButton button) {
		button.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				open_MakeThread();
			}
		});
	}   
}