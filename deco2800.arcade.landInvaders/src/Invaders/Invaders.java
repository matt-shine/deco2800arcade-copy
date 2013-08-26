package Invaders;
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.*;
import java.awt.event.*;

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

	public Invaders() {

		super("Land Invaders");
		enemyG = new enemyGroup(3, 6);
		tank = new tank();
		addKeyListener(tank);
		move =0;
		direction =1;
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
		enemyG.drawGroup(mains,move);
		tank.drawTank(mains,this);
		g.drawImage(bg, 0, 0, this);

	}

	public void update(Graphics g) {
		paint(g);
	}

	public static void main(String[] args) {
		Invaders invader = new Invaders();
	}

	@Override
	public void run() {
		int count = 0;
		while (true) {
			try {
				Thread.sleep(50);
			} catch (InterruptedException ie) {
			}
			if(count%10 ==0){
				if(move==130)direction =-1;
				if(move==-30)direction=1;
					move+= 10*direction;
			}
			repaint();
			count++;
		}
		

	}

}
