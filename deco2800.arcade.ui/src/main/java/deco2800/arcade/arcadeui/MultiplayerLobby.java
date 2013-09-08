package deco2800.arcade.arcadeui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

import deco2800.arcade.client.ArcadeInputMux;

import deco2800.arcade.client.ArcadeSystem;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;

import javax.swing.*;
import javax.swing.border.EtchedBorder;

public class MultiplayerLobby implements Screen {

	private OrthographicCamera camera;
	private ShapeRenderer shapeRenderer;
	private SpriteBatch batch;
	private BitmapFont font;
	
	private Stage stage;

	public void show() {
		font = new BitmapFont(true);
		batch = new SpriteBatch();
		camera = new OrthographicCamera();
		camera.setToOrtho(true, 1280, 720);
		shapeRenderer = new ShapeRenderer();
Main_Gui();		
	}
	@Override
	public void render(float arg0) {
		
		camera.update();
	    shapeRenderer.setProjectionMatrix(camera.combined);
	    batch.setProjectionMatrix(camera.combined);
	    
		Gdx.gl.glClearColor(0.9f, 0, 0, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		//draw a placeholder shape
		/*
	    shapeRenderer.begin(ShapeType.FilledRectangle);
	    
		
		
	    
		shapeRenderer.filledRect(1280,
	       720,
	        1280 - 720,
	        720 - 1280);
	    
	    shapeRenderer.end();
		
	    
		
	    batch.begin();
		*/
	    //font.setColor(Color.Black);
		
		
		

		

	    int h = 110;
	    font.draw(batch, "Multiplayer Lobby", 110, h);
	    batch.end();
	}
	
	
	
	@Override
	public void dispose() {
		// TODO Auto-generated method stub
	
	}

	@Override
	public void hide() {
	// TODO Auto-generated method stub
	
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resize(int arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}
	
	
	
	public void Main_Gui () {
	
	
	/**
	 * @Author Kieran Burke (Team !Shop)
	 */
	
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

//area.setForeground(Color.red);
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
