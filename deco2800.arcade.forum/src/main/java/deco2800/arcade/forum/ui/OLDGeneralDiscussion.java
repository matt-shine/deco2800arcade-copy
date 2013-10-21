package deco2800.arcade.forum.ui;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import java.awt.TextField;
import javax.swing.JButton;
import javax.swing.JToolBar;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Button;
import java.awt.Font;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.JViewport;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.JTextPane;

import deco2800.arcade.forum.ForumException;
import deco2800.arcade.model.forum.*;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Main class for General Discussion page
 *
 * @author TeamForum
 */
public class OLDGeneralDiscussion {
	private JTextField textField;
	private JPanel panel_1;
	private JPanel panel;
	private JPanel ThreadPanel;
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
	private JScrollPane scrollPane;
	private JFrame f;
	private int threadCount;
	public JButton btnPrevButton;
	public JButton btnNextButton;
	private JPanel threadDisplay;
	private ThreadListController controller;
	public GridLayout threadGrid;
	private JButton replyThread;
	private ParentThread currThread;
	

	public OLDGeneralDiscussion(JFrame window) {
		this.f = window;
		f.getContentPane().setLayout(null);
		threadCount = 0;
	      
	    JButton btnLogIn = new JButton("LOG IN");
	   	      
	    btnLogIn.setBounds(897, 16, 97, 25);
	    f.getContentPane().add(btnLogIn);
	      

	      
	    this.btnNewButton = new JButton("New Thread");
	    this.btnNewButton.setBounds(7, 194, 116, 25);
	    f.getContentPane().add(this.btnNewButton);
	    addMakeThreadListener(this.btnNewButton);
	    
	    this.btnPrevButton = new JButton("Prev 9");
	    this.btnPrevButton.setBounds(133, 194, 116, 25);
	    f.getContentPane().add(this.btnPrevButton);
	    
	    this.btnNextButton = new JButton("Next 9");
	    this.btnNextButton.setBounds(249, 194, 116, 25);
	    f.getContentPane().add(this.btnNextButton);
	      
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
	       
	      
	    this.lblNewLabel = new JLabel("General Discussion");
	    this.lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
	    this.lblNewLabel.setBounds(12, 155, 163, 26);
	    f.getContentPane().add(this.lblNewLabel);
	      

	    
	    /*
	     * 
	    
	    this.textField = new JTextField();
	    this.textField.setBounds(769, 74, 116, 22);
	    f.getContentPane().add(this.textField);
	    this.textField.setColumns(10);
	    
	    this.btnSearch = new JButton("Search");
	    this.btnSearch.setBounds(897, 73, 97, 25);
	    f.getContentPane().add(this.btnSearch);
	    
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
	     
	    this.textArea = new JTextArea();
	    this.textArea.setBackground(new Color(192, 192, 192));
	    this.textArea.setBounds(776, 152, 218, 220);
	    f.getContentPane().add(this.textArea);
	      
	    this.textArea_1 = new JTextArea();
	    this.textArea_1.setBackground(new Color(30, 144, 255));
	    this.textArea_1.setBounds(546, 152, 218, 496);
	    f.getContentPane().add(this.textArea_1);
	     */
	    
	    this.panel = new JPanel();
	    this.panel.setBackground(Color.ORANGE);
	    this.panel.setBounds(0, 59, 1006, 57);
	    f.getContentPane().add(this.panel);
	      
	    this.panel_1 = new JPanel();
	    this.panel_1.setBackground(Color.DARK_GRAY);
	    this.panel_1.setBounds(0, 0, 1006, 57);
	    f.getContentPane().add(this.panel_1);
	    f.setVisible(true);
	    
	    this.ThreadPanel = new JPanel();
	    this.ThreadPanel.setBorder(new LineBorder(new Color(0, 0, 0)));
	    this.ThreadPanel.setBounds(17, 232, 520, 464);
	    f.getContentPane().add(ThreadPanel);
	    f.setVisible(true);
	    
	    this.threadDisplay = new JPanel();
	    this.threadDisplay.setLayout(this.threadGrid = new GridLayout(1, 1));
	    
	    
	    this.scrollPane = new JScrollPane(this.threadDisplay,
	            JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
	            JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
	    this.scrollPane.setBounds(542, 155, 437, 541);
	    this.f.getContentPane().add(this.scrollPane);
	    
	    this.replyThread = new JButton("Reply");
	    this.replyThread.setBounds(863, 124, 116, 25);
	    addReplyThreadListener(this.replyThread);
	    this.replyThread.setVisible(false);
	    this.f.getContentPane().add(this.replyThread);
	    
	    
	    f.setVisible(true); 
	    
	    //UNCOMMENT THIS TO RE-USE
	    /*
	    try {
			this.controller = new ThreadListController(this, new ThreadListModel(1));
		} catch (ForumException e) {
			System.out.println("Threads failed to load");
			JOptionPane.showMessageDialog(null, "Failed to load threads", 
					"Error", JOptionPane.ERROR_MESSAGE);
		}
		*/
	}
	
	public void clear_display() {
		this.threadDisplay.removeAll();
	}
	
	public void update_display() {
		this.threadDisplay.updateUI();
	}
	
	public void display_parent(ParentThread p) {
		JPanel window = new JPanel();
		window.setLayout(new GridLayout(4, 1));
		JTextArea message = new JTextArea();
		JTextArea title = new JTextArea();
		JTextArea author = new JTextArea();
		JTextArea date = new JTextArea();
		window.setBackground(Color.DARK_GRAY);
		window.setBorder(new LineBorder(new Color(0, 0, 0)));
		message.setColumns(35);
		title.setColumns(30);
		author.setText("By: " + p.getCreatedBy().getName());
		date.setText("Written on: " + p.getTimestamp().toString());
		message.setText(p.getMessage());
		title.setText(p.getTopic());
		title.setFont(new Font("Cambria", Font.BOLD, 15));
		window.add(title);
		window.add(message);
		window.add(author);
		window.add(date);
		this.threadDisplay.add(window);
		
	}
	
	public void display_child(ChildThread c) {
		JPanel window = new JPanel();
		window.setLayout(new GridLayout(3, 1));
		JTextArea message = new JTextArea();
		JTextArea author = new JTextArea();
		JTextArea date = new JTextArea();
		window.setBackground(Color.DARK_GRAY);
		window.setBorder(new LineBorder(new Color(0, 0, 0)));
		message.setColumns(35);
		author.setText("By: " + c.getCreatedBy().getName());
		date.setText("Written on: " + c.getTimestamp().toString());
		message.setText(c.getMessage());
		window.add(message);
		window.add(author);
		window.add(date);
		this.threadDisplay.add(window);
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
	   
	public void open_MakeThread() throws ForumException {
		this.f.setContentPane(new JPanel(new BorderLayout()));
	   	new MakeThreadController(new MakeThreadView(this.f));
	}
	
	public void open_ReplyThread() throws ForumException {
		this.f.setContentPane(new JPanel(new BorderLayout()));
	   	new ReplyThreadController(new ReplyThreadView(this.f, currThread.getTopic()), currThread.getId());
	}
	   	
	private void addMakeThreadListener(JButton button) {
		button.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				try {
					open_MakeThread();
				} catch (ForumException e1) {
					System.out.println("Didnt work");
				}
			}
		});
	}
	
	private void addReplyThreadListener(JButton button) {
		button.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				try {
					open_ReplyThread();
				} catch (ForumException e1) {
					System.out.println("Didnt work");
				}
			}
		});
	}
	//17, 232, 520, 464
	public void addInnerThreadPanel(final ParentThread thread) {
		this.threadCount += 1;
		ParentThread t = thread;
		int threadNum = this.threadCount;
		JPanel threadDisplay = new JPanel();
	    threadDisplay.setBackground(Color.WHITE);
	    threadDisplay.setPreferredSize(new Dimension(515, 46));
	    threadDisplay.setMaximumSize(threadDisplay.getPreferredSize());
	    threadDisplay.setMinimumSize(threadDisplay.getPreferredSize());
	    threadDisplay.setLocation(17, (233 + (46 * threadNum - 1)));
	    JLabel title = new JLabel(thread.getTopic());
	    title.setFont(new Font("Tahoma", Font.BOLD, 15));
	    title.setBounds(19, (235 + (46 * threadNum - 1)), 510, 46);
	    threadDisplay.add(title);
	    threadDisplay.addMouseListener(new MouseAdapter() {
	    	public void mousePressed(MouseEvent e) {
	    		clear_display();
	    		display_parent(thread);
	    		currThread = thread;
	    		update_display();
	    		replyThread.setVisible(true);
	    		controller.request_childs(thread.getId());
	    	}
	    });
	    this.ThreadPanel.add(threadDisplay);	
	}
	
	public void clearThreadPanel() {
		this.ThreadPanel.removeAll();
	}
	
	public void updateThreadPanel() {
		this.ThreadPanel.updateUI();
	}
}