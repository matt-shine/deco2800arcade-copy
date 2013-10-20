package deco2800.arcade.forum.ui;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import java.awt.TextField;
import javax.swing.JButton;
import javax.swing.JToolBar;

import java.awt.Container;
import java.awt.Label;
import java.awt.Button;
import java.awt.Font;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.Color;
import javax.swing.JTextPane;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import deco2800.arcade.forum.ClientConnection;
import deco2800.arcade.model.forum.ParentThread;
import deco2800.arcade.protocol.forum.GetParentThreadsRequest;
import deco2800.arcade.protocol.forum.GetParentThreadsResponse;
import deco2800.arcade.protocol.forum.ParentThreadProtocol;
import deco2800.arcade.protocol.forum.TagsRequest;
import deco2800.arcade.protocol.forum.TagsResponse;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
/**
 * Main class for Forum Interface
 *
 * @author TeamForum
 */
public class ForumUi {
	public JTextField textField;
	public JLabel lblFaq;
	public JLabel lblHome;
	public JButton btnSearch;
	public JTextArea textArea;
	public JLabel lblAnn;
	public JTextArea textArea_1;
	public JLabel lblTag;
	public JLabel lblGeneralDiscussion;
	public JLabel lblTutorial;
	public JLabel lblReportBug;
	public JTextPane txtpnForDiscussionOf;
	public JTextPane txtpnTipsAndTrick;
	public JTextPane txtpnFacingBugIssue;
	public JPanel panel;
	public JPanel panel_1;
	private JFrame f;
	
	/* Numbers of tags to be retrieved */
	public static final int NUM_TAGS = 20;
	/* Numbers of threads to be retrieved */
	public static final int NUM_THREADS = 5;
	
	public ForumUi(JFrame window) {
		//Initialize new JFrame for forum interface
		this.f = window;
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
		this.lblHome = new JLabel("HOME");
		this.lblHome.setFont(new Font("Cambria", Font.BOLD, 15));
		this.lblHome.setBounds(26, 76, 56, 16);
		f.getContentPane().add(this.lblHome);
	

	
		
	
		
	  
		/*
		 
				//FAQ
		this.lblFaq = new JLabel("FAQ");
		this.lblFaq.setFont(new Font("Cambria", Font.BOLD, 15));
		lblFaq.setBounds(147, 76, 74, 16);
		f.getContentPane().add(this.lblFaq);
		
		//Search Button
		this.btnSearch = new JButton("Search");
		this.btnSearch.setBounds(897, 73, 97, 25);
		f.getContentPane().add(this.btnSearch);
	  
		this.textField = new JTextField();
		this.textField.setBounds(769, 74, 116, 22);
		f.getContentPane().add(this.textField);
		this.textField.setColumns(10);
		
		//Body
		this.textArea = new JTextArea();
		this.textArea.setBackground(new Color(192, 192, 192));
		this.textArea.setBounds(776, 152, 218, 220);
		f.getContentPane().add(this.textArea);
	  
	  
		this.textArea_1 = new JTextArea();
		this.textArea_1.setBackground(new Color(30, 144, 255));
		this.textArea_1.setBounds(546, 152, 218, 496);
		f.getContentPane().add(this.textArea_1);
		
		this.lblAnn = new JLabel("Announcement");
		this.lblAnn.setFont(new Font("Tahoma", Font.BOLD, 15));
		this.lblAnn.setBounds(776, 129, 141, 16);
		f.getContentPane().add(this.lblAnn);
		
		this.lblTag = new JLabel("Tag");
		this.lblTag.setFont(new Font("Tahoma", Font.BOLD, 15));
		this.lblTag.setBounds(546, 129, 97, 16);
		f.getContentPane().add(this.lblTag);
		
	  	*/
		
		this.lblGeneralDiscussion = new JLabel("General Discussion");
		this.lblGeneralDiscussion.setFont(new Font("Tahoma", Font.BOLD, 13));
		this.lblGeneralDiscussion.setBounds(12, 130, 946, 73);
		addGDLabelListener(this.lblGeneralDiscussion);
		f.getContentPane().add(this.lblGeneralDiscussion);
	 
		//Tutorial Label
		this.lblTutorial = new JLabel("Tutorial");
		this.lblTutorial.setFont(new Font("Tahoma", Font.BOLD, 13));
		this.lblTutorial.setBounds(12, 280, 141, 16);
		addTUTLabelListener(this.lblTutorial);
		f.getContentPane().add(this.lblTutorial);
	
		//Bug Reporting
		this.lblReportBug = new JLabel("Report Bug");
		this.lblReportBug.setFont(new Font("Tahoma", Font.BOLD, 13));
		this.lblReportBug.setBounds(12, 432, 141, 16);
		addRBLabelListener(this.lblReportBug);
		f.getContentPane().add(this.lblReportBug);
	  
		this.txtpnForDiscussionOf = new JTextPane();
		this.txtpnForDiscussionOf.setEnabled(false);
		this.txtpnForDiscussionOf.setEditable(false);
		this.txtpnForDiscussionOf.setFont(new Font("Tahoma", Font.ITALIC, 13));
		this.txtpnForDiscussionOf.setText("For discussion of non technical stuff");
		this.txtpnForDiscussionOf.setBounds(12, 184, 946, 73);
		f.getContentPane().add(this.txtpnForDiscussionOf);
	  
		this.txtpnTipsAndTrick = new JTextPane();
		this.txtpnTipsAndTrick.setEnabled(false);
		this.txtpnTipsAndTrick.setText("Tips and trick of doing work");
		this.txtpnTipsAndTrick.setFont(new Font("Tahoma", Font.ITALIC, 13));
		this.txtpnTipsAndTrick.setBounds(12, 309, 946, 73);
		f.getContentPane().add(this.txtpnTipsAndTrick);
	  
		this.txtpnFacingBugIssue = new JTextPane();
		this.txtpnFacingBugIssue.setEditable(false);
		this.txtpnFacingBugIssue.setEnabled(false);
		this.txtpnFacingBugIssue.setText("Facing bug issue? Report here");
		this.txtpnFacingBugIssue.setFont(new Font("Tahoma", Font.ITALIC, 13));
		this.txtpnFacingBugIssue.setBounds(14, 461, 946, 73);
		f.getContentPane().add(this.txtpnFacingBugIssue);

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
   
	public void open_general_discussion() {
	   this.f.setContentPane(new JPanel(new BorderLayout()));
	   new GeneralDiscussion(this.f);
	}
   
	private void addGDLabelListener(JLabel label) {
	   label.addMouseListener(new MouseAdapter() {
		   public void mouseClicked(MouseEvent e) {
			   open_general_discussion();
		   }
	   });
	}
   
	public void open_tutorial() {
		this.f.setContentPane(new JPanel(new BorderLayout()));
		new Tutorial(this.f);
	}
   
	private void addTUTLabelListener(JLabel label) {
		label.addMouseListener(new MouseAdapter()
	    {
			public void mouseClicked(MouseEvent e) {
			   open_tutorial();
		   }
	    });
    }
   
	public void open_Report_Bug() {
	    this.f.setContentPane(new JPanel(new BorderLayout()));
	    new ReportBug(this.f);
    }
   
    private void addRBLabelListener(JLabel label) {
	    label.addMouseListener(new MouseAdapter()
	    {
	    	public void mouseClicked(MouseEvent e) {
			   open_Report_Bug();
		   }
    	});
    }
    
    /**
     * Send requests to retrieve some tags from server. 
     * Before calling this, the corresponding listener must be added to the client. 
     * Set NUM_TAGS for numbers of tags to be retrieved.
     * You may move this to ForumUIControl (but still accessible from another class).
     * 
     * @param con	ClientConnection instance.
     */
    public void sendTagsRequest(ClientConnection con) {
    	if (con.getClient().isConnected() == false) {
    		System.err.println("Not connected");
    		return;
    	}
    	
    	TagsRequest request = new TagsRequest();
    	request.limit = NUM_TAGS;
    	con.getClient().sendTCP(request);
    	return;
    }
    
    public void sendThreadsRequest(ClientConnection con, String category) {
    	if (con.getClient().isConnected() == false) {
    		System.err.println("Not connected");
    		return;
    	}
    	
    	GetParentThreadsRequest request = new GetParentThreadsRequest();
    	request.start = 0;
    	request.end = 0;
    	request.limit = NUM_THREADS;
    	request.category = category;
    	con.getClient().sendTCP(request);
    	return;
    }
    
    /**
     * Controller for KryoNet connection listener. Remove this listener before closing the ForumUi
     * if not the client is closed.
     * <p>
     * Supported protocols;
     * <ul>
     * 	<li>TagsResponse</li>
     * </ul>
     * 
     * @author Junya, Team Forum
     */
    private class NetworkController extends Listener {
    	@Override
    	public void disconnected(Connection connection) {
    		super.disconnected(connection);
    		System.out.println("disconnected"); 
    	}
    	
    	@Override
    	public void received(Connection connection, Object object) {
    		super.received(connection, object);
    		if (object instanceof TagsResponse) {
    			TagsResponse response = (TagsResponse) object;
    			if (response.error == "") {
    				System.err.println(response.error);
    			} else {
    				// Change followings to do something if tags are received.
    				StringBuilder sd = new StringBuilder("Tags: ");
    				for (String tag : response.result) {
    					sd.append(tag);
    					sd.append("#");
    				}
    				System.out.println(new String(sd));
    			}
    			return;
    		} else if (object instanceof GetParentThreadsResponse) {
    			GetParentThreadsResponse response = (GetParentThreadsResponse) object;
    			if (response.error == "") {
    				System.err.println(response.error);
    			} else {
    				ParentThread[] threads = ParentThreadProtocol.getParentThreads(response.result);
    				if (threads == null) {
    					System.out.println("No result");
    				} else {
    					System.out.println("Parent threads in " + threads[0].getCategory() + ":");
    					for (ParentThread thread : threads) {
    						System.out.println(thread.toString());
    					}
    				}
    			}
    			return;
    		}
    	}
    }
}
	   
	   
