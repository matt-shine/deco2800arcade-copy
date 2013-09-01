package deco2800.arcade.userui.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import deco2800.arcade.userui.model.Model;

public class View extends JFrame implements ActionListener {
	
	/*
	 * Declare elements here
	 */
	
	private Model model;
	private ImagePanel menupanel;
	private JPanel sidepanel;
	private JPanel contentpanel;
	
	//User a GridBagLayout to these sections
	private GridBagLayout menulayout = new GridBagLayout();
	private GridBagLayout sidelayout = new GridBagLayout();
	private GridBagLayout mainlayout = new GridBagLayout();
	
	public View(Model model) throws HeadlessException {
		super("User Profile");
		
		this.model = model;
		
		/*
		 * Add elements here to window
		 */
		
	    menupanel = new ImagePanel(new ImageIcon("assets/images/Menu_Bar.png").getImage());
	    menupanel.setLayout(menulayout);
	    sidepanel = new JPanel(sidelayout);
	    contentpanel = new JPanel(mainlayout);

		/*
		 * Specify the layouts used here and add to Panels
		 */
	    		
		add(menupanel, BorderLayout.NORTH);
		add(sidepanel);
		add(contentpanel);

		this.pack();
		
		sidepanel.setBackground(Color.darkGray);
		sidepanel.setSize(400,800);
        
		contentpanel.setBackground(Color.gray);
		contentpanel.setSize(400, 800);
	
		// Set the  view window constraints
		setSize(1280,800);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		
		/* Example code to call a button event
		 * JButton source = (JButton)e.getSource();
		
		if(source == editbutton) {
			System.out.println("Edit profile");
		} else {
			System.out.println("Some other button");		
		}*/
		
	}
	
	
}

/*
 * Class for adding background panel to JPanels
 */

class ImagePanel extends JPanel {

	  private Image img;

	  public ImagePanel(String img) {
	    this(new ImageIcon(img).getImage());
	  }

	  public ImagePanel(Image img) {
	    this.img = img;
	    Dimension size = new Dimension(img.getWidth(null), img.getHeight(null));
	    setPreferredSize(size);
	    setMinimumSize(size);
	    setMaximumSize(size);
	    setSize(size);
	    setLayout(null);
	  }

	  public void paintComponent(Graphics g) {
	    g.drawImage(img, 0, 0, null);
	  }

	}
