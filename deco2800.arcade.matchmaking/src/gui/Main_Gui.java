package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
public class Main_Gui {
	
	
	/**
	 * @Author Kieran Burke (Team !Shop)
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
System.out.println("Deco2800 !Team Shop Gui Started");

// Generate main GUI Frame
final JFrame f = new JFrame("DECO2800 - !Team Shop Matchmaking v1.0");
f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

//Use the code below to add an image to the Gui Window

//Note: Still need to add absolute URL path to avoid image display error on a different pc
//ImageIcon image = new ImageIcon("C:\\Users\\Kieran\\Desktop\\gui_background.jpg");
//JLabel imageLabel = new JLabel(image);
//f.add(imageLabel, BorderLayout.EAST);


//Software Version Status Bar 
//JLabel sb = new JLabel("Version 1.0");
//sb.setBorder(BorderFactory.createEtchedBorder(
    //EtchedBorder.RAISED));

//f.add(sb, BorderLayout.SOUTH);


//f.setExtendedState(Frame.MAXIMIZED_BOTH);


//Create main container
Container cp = f.getContentPane();
Container gp = f.getContentPane();
JPanel panel = new JPanel();


// Main Area To Display Connection List
final JTextPane area = new JTextPane();
area.setEditable(false);


area.setBorder(BorderFactory.createEtchedBorder(
	     EtchedBorder.RAISED));
area.setSize(new Dimension(500,500));

area.setForeground(Color.red);
area.setText("Click refresh to search for available games...");

gp.setVisible(true);
gp.add(area, BorderLayout.NORTH);

//Main Scroll Bar

JScrollPane pane = new JScrollPane(area);
pane.setPreferredSize(new Dimension(780,500));
pane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
panel.add(pane);
gp.add(panel,BorderLayout.NORTH);


//Create Buttons Refresh and Exit
JButton button;
JButton button2;

button = new JButton("Refresh");
button.setPreferredSize(new Dimension (100,20));
f.add(button, BorderLayout.WEST);

button2 = new JButton("Exit");
button2.setPreferredSize(new Dimension (100,20));
cp.add(button2, BorderLayout.EAST);


//Generate Top Menu Bar and Sub Menus
JMenuBar topmenu = new JMenuBar();
f.setJMenuBar(topmenu);

//Create Main Button
JMenu main = new JMenu("Multiplayer");
topmenu.add(main);

//Create About Button
JMenu about = new JMenu("About");
topmenu.add(about);

JMenuItem aboutbutton = new JMenuItem("About");
about.add(aboutbutton);

// About Button Event Listener
aboutbutton.addActionListener(new ActionListener() {
	 
    public void actionPerformed(ActionEvent e)
    {
        
    	JOptionPane.showMessageDialog(null, "Team !Shop Copyright 2013 All Rights Reserved.", "About", 
    		    JOptionPane.PLAIN_MESSAGE);
    		
        System.out.println("You clicked the About button");
    }
}); 


//Refresh Button Event Listener
button.addActionListener(new ActionListener() {
	 
    public void actionPerformed(ActionEvent e)
    {
    	
        System.out.println("You clicked the Refresh button");
        
        
     // Display Connection List Function
        
        //change the array below to take host user ip address or user id.
        
        int[] anArray = { 
        	    1, 2, 3,
        	    4, 5, 6, 
        	    7, 8, 9, 10
        	};
        
        area.setText(" ");

        if (anArray.length > 0){
        	//for(int i : anArray){
        		
        		for(int i= anArray[0]; i < anArray.length +1; i++) {
        			final JButton joiner = new JButton("Join" + i);
        			
        			 
        			
        area.insertComponent(joiner);
        joiner.setName(""+ i);
        System.out.println(joiner.getName());
        System.out.println("Element:" + i);


        joiner.addActionListener(new ActionListener() {
        	 
            public void actionPerformed(ActionEvent e)
            {
            	
                System.out.println("You clicked the Joiner button: " + joiner.getName());
                //Add game connection code here: Example: Join multiplayer game where host user id or ip address equals button name. 
                //Note you can retrieve the button name using joiner.getname()
            }
        }); 

        		}

        }
        else{
        	area.setText("No Multiplayer Games Found...");

        }
        
        
    }
}); 

//Exit Button Event Listener
button2.addActionListener(new ActionListener() {
	 
    public void actionPerformed(ActionEvent e)
    {
    	
    	if (JOptionPane.showConfirmDialog(null, "Are you sure you want to exit?", "Exit", 
    		    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE)
    		    == JOptionPane.YES_OPTION)
    		{
    		
    		System.exit(0);
    		}
    		else
    		{
    		 //Do nothing
    		}
        System.out.println("You clicked the Exit button");
    }
   
}); 


//Create Button
JMenu help = new JMenu("Help");
topmenu.add(help);

JMenuItem helpbutton = new JMenuItem("Help");
help.add(helpbutton);

// Help Button Event Listener
helpbutton.addActionListener(new ActionListener() {
	 
    public void actionPerformed(ActionEvent e)
    {
        
    	JOptionPane.showMessageDialog(null, "<Help Message>", "Help", 
    		    JOptionPane.PLAIN_MESSAGE);
        System.out.println("You clicked the Help button");
    }
}); 

//Create Menu Refresh Button
JMenuItem filemode = new JMenuItem("Refresh");
main.add(filemode);

//File Button Event Listener
filemode.addActionListener(new ActionListener() {
	 
    public void actionPerformed(ActionEvent e )
    {
    	
        System.out.println("You clicked the Refresh button");
        
    }
});      

// Create Button
JMenuItem exitmode = new JMenuItem("Exit");
main.add(exitmode);

// Exit Button in menu bar Event Listener
exitmode.addActionListener(new ActionListener() {
	 
    public void actionPerformed(ActionEvent e)
    {
        
    	if (JOptionPane.showConfirmDialog(null, "Are you sure you want to exit?", "Exit", 
    		    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE)
    		    == JOptionPane.YES_OPTION)
    		{
    		
    		System.exit(0);
    		}
    		else
    		{
    		 //Do nothing
    		}
        System.out.println("You clicked the Exit button");
    }
}); 


// Set GUI frame size and make it visible, do not allow window resize 
f.setSize(800, 600);
f.setResizable(false);
f.setVisible(true);

	}
	
}
