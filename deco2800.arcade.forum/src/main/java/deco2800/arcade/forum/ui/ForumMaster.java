package deco2800.arcade.forum.ui;

import java.awt.*;
import javax.swing.*;

/**
 * Main class for forum interface
 *
 * @author TeamForum
 */
public class ForumMaster {

	   public ForumMaster() {
	      //Initialize new JFrame for forum interface
	      JFrame f = new JFrame("Arcade Forum");
	      f.setSize(1024, 768);
	      f.setLocation(300,200);
	      f.getContentPane().setLayout(null);
	      
	      new ForumUi(f);
	    }
}