package deco2800.arcade.landInvaders;


import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;

public class Invaders extends JFrame implements Runnable {
	JFrame appFrame;
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
	private int shotsNmb;
	private int level;
	private blockWall blockWall;
	private boolean moveDown;

	private ArrayList<blockWall> WallList;
	private ArrayList<tankshot> shots;
	private ArrayList<enemyShot> Eshots;

	public Invaders() {

		super("Land Invaders");
		WallList = new ArrayList<blockWall>();
		WallList.add(new blockWall(180, 350, 4, 8));
		WallList.add(new blockWall(380, 350, 4, 8));
		WallList.add(new blockWall(580, 350, 4, 8));
		shotsNmb = 6;
		level = 3;
		shots = new ArrayList<tankshot>();
		Eshots = new ArrayList<enemyShot>();
		enemyG = new enemyGroup(3, 6);
		tank = new tank();
		addKeyListener(tank);
		move = 0;
		direction = 1;
		moveDown = false;
		
		background = new javax.swing.ImageIcon("deco2800.arcade.landInvaders/src/main/resources/image/city.jpg").getImage();

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
		for (int i = 0; i < shots.size(); i++) {
			shots.get(i).drawshot(mains);

		}
		for (int n = 0; n < Eshots.size(); n++) {
			Eshots.get(n).drawshot(mains);

		}
		for (int e = 0; e < WallList.size(); e++) {
			WallList.get(e).drawWall(mains);
		}
		enemyG.drawGroup(mains);
		tank.drawTank(mains, this);
		g.drawImage(bg, 0, 0, this);

	}

	public void update(Graphics g) {
		paint(g);
	}

	public void shotUpdate() {
		for (int i = 0; i < shots.size(); i++) {
			shots.get(i).Update();
			if (shots.get(i).positionY() < 0) {
				shots.remove(i);
			}

		}
		for (int i = 0; i < Eshots.size(); i++) {
			Eshots.get(i).Update();
			if (Eshots.get(i).positionY() < 0) {
				Eshots.remove(i);
			}

		}
	}

	public void hitEnemy() {

		for (int i = 0; i < shots.size(); i++) {
			if (enemyG.checkHit(shots.get(i)) == true) {
				shots.remove(i);
			}

		}
		levelCheck();

	}

	public void hitwall() {
		for (int n = 0; n < WallList.size(); n++) {
			blockWall = WallList.get(n);
			for (int i = 0; i < shots.size(); i++) {
				if (blockWall.checkHit(shots.get(i)) == true) {
					shots.remove(i);
				}
			}

			for (int i = 0; i < Eshots.size(); i++) {
				if (blockWall.checkEnemyHit(Eshots.get(i)) == true) {
					Eshots.remove(i);
				}
			}
		}
	}

	public void enemyMove(int count, boolean moveD) {
 
		if (count % 10 == 0) {
			if (move == 130) {
				direction = -1;
				if (moveD == true)
					moveDown = true;
			}
			if (move == -30) {
				direction = 1;
				if (moveD == true)
					moveDown = true;
			}
			move += 10 * direction;
			enemyG.moveUpdate(10 * direction, moveDown);
			moveDown = false;
		}
	}

	public void levelSelect(int count) {

		switch (level) {

		case 1:
			enemyMove(count, false); 
			break;
		case 2:
			enemyMove(count, false);
			levelTwo(count); 
			break;
		case 3:
			enemyMove(count, true);
			levelTwo(count);
			break;

		}
	}
	
	public void levelCheck(){
		if(enemyG.isEmpty() ==true){
			if(level != 3){
				level++;
			}else{
				level =1;
			}
			restart();
		
		}
	}
	
	public void restart(){
		shots = new ArrayList<tankshot>();
		Eshots = new ArrayList<enemyShot>();
		enemyG = new enemyGroup(3, 6);
		tank = new tank();
		addKeyListener(tank);
		move = 0;
		direction = 1;
		moveDown = false;
	}

	public void levelTwo(int count) {

		Eshots.addAll(enemyG.enemyShot(count));

	}

	public static void main(String[] args) throws Exception {
		Invaders invader = new Invaders();
	}

	@Override
	public void run() {
		int count = 0;
		while (true) {
			tank.tankMove();
			try {
				Thread.sleep(50);
			} catch (InterruptedException ie) {
			}

			if (tank.shotCheck() == true) {
				if (shots.size() < shotsNmb)
					shots.add(new tankshot(tank.PositionX(), tank.PositionY()));
				tank.finishShot();
			}

			shotUpdate();
			levelSelect(count);
			hitEnemy();
			hitwall();
			repaint();
			count++;
		}

	}

}
