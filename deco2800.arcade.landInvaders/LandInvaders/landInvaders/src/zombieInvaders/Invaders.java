package zombieInvaders;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Invaders extends JFrame implements Stats {

	public static int Width = 800;
	public static int Height = 500;
	Image background = null;
	Graphics mains;
	BufferedImage bg;
	JPanel panel;

	public Invaders() {
		

		//super("Land Invaders");
		add(new Gamescape());
		setTitle("Space Invaders");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		background = new javax.swing.ImageIcon("bgimage.jpg").getImage();
		bg = new BufferedImage(Width, Height, BufferedImage.TYPE_INT_RGB);
		mains = bg.createGraphics();
		setBackground(Color.black);
		setVisible(true);
		setSize(BOARD_WIDTH, BOARD_HEIGHT);
		setLocationRelativeTo(null);
		setResizable(false);
		startGame();

	}

	public void paint(Graphics g) {

		g.drawImage(background, 0, 0, this);
	}

	public static void main(String[] args) {
		Invaders invader = new Invaders();
	}

	public void startGame() {
		Thread thread = new Thread();
		thread.start();
	}

}
