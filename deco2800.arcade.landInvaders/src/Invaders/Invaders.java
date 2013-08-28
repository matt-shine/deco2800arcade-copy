package Invaders;
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;

public class Invaders extends JFrame implements Runnable {

	public static int Width = 800;
	public static int Height = 500;
	private Image background = null;
	private Graphics mains;
	private BufferedImage bg;
	private JPanel panel;
	private enemyGroup enemyG;
	private tank tank;
	private int move;
	private int direction;
	private Robot r;
	
	private ArrayList<tankshot> shots;

	public Invaders() throws Exception{

		super("Land Invaders");
		shots = new ArrayList<tankshot>();
		enemyG = new enemyGroup(3, 6);
		tank = new tank();
		addKeyListener(tank);
		move =0;
		direction =1;
		r=new Robot();
		background = new javax.swing.ImageIcon("bgimage.jpg").getImage();

		bg = new BufferedImage(Width, Height, BufferedImage.TYPE_INT_RGB);

		mains = bg.getGraphics();
		mains.drawImage(background, 0, 0, Width, Height, panel);

		setBackground(Color.black);
		setVisible(true);
		setSize(Width, Height);
		startGame();

	}

	public void startGame() {

		Thread thread = new Thread(this);
		thread.start();
	}

	public void paint(Graphics g) {

		mains.clearRect(0, 0, Width, Height);
		
		mains.drawImage(background, 0, 0, Width, Height, panel);
		for(int i = 0; i<shots.size();i++){
			shots.get(i).drawshot(mains);
			
		}
		enemyG.drawGroup(mains,move);
		tank.drawTank(mains,this);
		g.drawImage(bg, 0, 0, this);

	}

	public void update(Graphics g) {
		paint(g);
	}
	
	public void enemyMove(int count){
		if(count%10 ==0){
			if(move==130)direction =-1;
			if(move==-30)direction=1;
				move+= 10*direction;
		}
	}
	

	public static void main(String[] args) throws Exception{
		Invaders invader = new Invaders();
	}

	@Override
	public void run() {
		int count = 0;
		while (true) {
			if(tank.moveLeft()==true)r.keyPress(KeyEvent.VK_LEFT);
			if(tank.moveRight()==true)r.keyPress(KeyEvent.VK_RIGHT);
			try {
				Thread.sleep(50);
			} catch (InterruptedException ie) {
			}
			
			if(tank.shotCheck() == true){
				shots.add(new tankshot(tank.PositionX(),tank.PositionY()));
				tank.finishShot();
			}
			
			enemyMove(count);
			repaint();
			count++;
		}
		

	}

}
