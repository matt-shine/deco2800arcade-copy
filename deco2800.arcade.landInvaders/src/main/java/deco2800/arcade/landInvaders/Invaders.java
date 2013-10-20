package deco2800.arcade.landInvaders;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import javax.swing.*;

import deco2800.arcade.landInvaders.Screens.gameOver;
import deco2800.arcade.landInvaders.Screens.stageClear;

import java.util.ArrayList;

public class Invaders extends JFrame implements Runnable {
	JFrame appFrame;
	public static int Width = 800;
	public static int Height = 670;
	private Image background = null;
	private Graphics mains;
	private BufferedImage bg;
	private JPanel panel;
	private enemyGroup enemyG;
	private tank tank;
	private int move;
	private int direction;
	private int shotsNmb;
	private int level;
	private blockWall blockWall;
	private boolean moveDown;
	private int bglevel;
	private String imgString;
	private Image frame = null;
	private int healthBar;
	private int score;
	private int totalLevel;
	private boolean isPause;
	private int highScore;

	private ArrayList<blockWall> WallList;
	private ArrayList<tankshot> shots;
	private ArrayList<enemyShot> Eshots;

	/**
	 * Initialize objects in the game and allocate values to player, score and
	 * level
	 */
	public Invaders() {

		super("Land Invaders");
		bglevel = 1;
		WallList = new ArrayList<blockWall>();

		healthBar = 3;
		highScore = 0;
		score = 0;
		totalLevel = 1;
		isPause = true;

		setGameImg("/tank/");
		imgString = "/tank/";
		WallList.add(new blockWall(180, 480, 4, 8, imgString + "WallD.png"));
		WallList.add(new blockWall(380, 480, 4, 8, imgString + "WallD.png"));
		WallList.add(new blockWall(580, 480, 4, 8, imgString + "WallD.png"));
		shotsNmb = 100;
		level = 1;
		shots = new ArrayList<tankshot>();
		Eshots = new ArrayList<enemyShot>();

		frame = new javax.swing.ImageIcon(this.getClass().getResource(
				"/image/Frame.png")).getImage();

		addKeyListener(tank);
		move = 0;
		direction = 1;
		moveDown = false;

		bg = new BufferedImage(Width, Height, BufferedImage.TYPE_INT_RGB);

		mains = bg.getGraphics();
		mains.drawImage(background, 0, 0, Width, Height, panel);
		mains.drawImage(frame, 0, 0, Width, 1000, panel);

		setBackground(Color.black);
		setVisible(true);
		setSize(Width, Height);
		startGame();
		
		KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
	    manager.addKeyEventPostProcessor(new KeyEventPostProcessor() {
	        public boolean postProcessKeyEvent(KeyEvent e) {
	            if (KeyEvent.VK_ESCAPE == e.getKeyCode()) {
	                System.exit(0);
	            }
	            return true;
	        }
	    });
	}
	
	public void gameVictory1()
	{
		if(bglevel == 2)
		{
			Land
		}
	}

	/**
	 * @param type
	 *            selection of game sprite to be used in game
	 * 
	 *            Sets the game theme and sprites for different stages
	 */
	public void setGameImg(String type) {

		enemyG = new enemyGroup(3, 6, 50, 83, type + "TankE.png");
		tank = new tank(type + "TankP.png");
		addKeyListener(tank);
		background = new javax.swing.ImageIcon(this.getClass().getResource(
				type + "BGD.png")).getImage();
	}

	public void showStageClear() {
		isPause = false;
		stageClear s = new stageClear(this, score);
		this.setVisible(false);
	}
	
	public int getScore()
	{
		return score;
	}
	
	public int getHighScore()
	{
		return highScore;
	}

	public void nextStage() {

		isPause = true;
		this.setVisible(true);
	}

	/**
	 * Starts the game using thread class
	 */
	public void startGame() {

		Thread thread = new Thread(this);
		thread.start();
	}

	public void paint(Graphics g) {

		mains.setColor(Color.white);
		mains.setFont(new Font("Algerian", 1, 28));

		mains.clearRect(0, 0, Width, Height);
		mains.drawImage(background, 0, 0, Width, Height, panel);
		mains.drawImage(frame, 10, 20, Width, 662, panel);

		mains.drawString(score + "", 420, 54);
		mains.drawString(healthBar + "", 150, 659);
		mains.drawString(totalLevel + "", 580, 54);
		for (int i = 0; i < shots.size(); i++) {
			shots.get(i).drawshot(mains);

		}
		for (int n = 0; n < Eshots.size(); n++) {
			Eshots.get(n).drawshot(mains);

		}
		for (int e = 0; e < WallList.size(); e++) {
			WallList.get(e).drawWall(mains, this);
		}
		enemyG.drawGroup(mains, this);
		tank.drawTank(mains, this);
		g.drawImage(bg, 0, 0, this);

	}

	public void update(Graphics g) {
		paint(g);
	}

	/**
	 * Constantly check if shots made by both player and enemy have hit an
	 * object, if it does not hit anything then the shots will be removed once
	 * it's out of the frame
	 */
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

	/**
	 * Check if shots made by player have hit an enemy based on location of shot
	 * and enemy object. If the shot hit an enemy, remove both enemy and shot
	 * objects.
	 */
	public void hitEnemy() {

		for (int i = 0; i < shots.size(); i++) {
			if (enemyG.checkHit(shots.get(i)) == true) {
				shots.remove(i);
				score++;
			}

		}
		levelCheck();

	}

	/**
	 * Check if any shots made by enemy and player hit a wall. If the condition
	 * is met, remove a part of the wall
	 * 
	 * 
	 */
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

	/**
	 * @param count
	 *            determine the amount of time before enemy object change
	 *            position
	 * @param moveD
	 *            determines the boolean condition if the enemy group is allowed
	 *            to move down or not
	 */
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

	/**
	 * @param count
	 *            determine the level of the game
	 * 
	 *            Select the action level of the game. Game has 3 levels.
	 */
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

	/**
	 * Check if any enemies are left. If no enemies are left and level is not 3,
	 * increase the action level of the game.
	 */
	public void levelCheck() {

		if (enemyG.isEmpty() == true) {
			if (level != 3) {
				level++;
			} else {
				level = 1;
			}
			restart();

		}
	}

	/**
	 * Restart the games and reset sprites for the new game level
	 */
	public void restart() {
		boolean createWall = false;
		if (bglevel != 3 && level == 1) {
			bglevel++;
			createWall = true;
			showStageClear();

		}
		if (bglevel == 3 && level == 1) {
			WallList = new ArrayList<blockWall>();
		}
		shots = new ArrayList<tankshot>();
		Eshots = new ArrayList<enemyShot>();
		if (bglevel == 1) {
			imgString = "/tank/";
		}
		if (bglevel == 2) {
			imgString = "/plane/";

		}
		if (bglevel == 3) {
			imgString = "/ship/";
			bglevel = 1;

		}

		if (createWall == true) {
			WallList = new ArrayList<blockWall>();
			WallList.add(new blockWall(180, 480, 4, 8, imgString + "WallD.png"));
			WallList.add(new blockWall(380, 480, 4, 8, imgString + "WallD.png"));
			WallList.add(new blockWall(580, 480, 4, 8, imgString + "WallD.png"));
			createWall = false;
		}
		setGameImg(imgString);
		move = 0;
		direction = 1;
		moveDown = false;
	}

	/**
	 * @param count
	 *            controls the rate of shots fired by enemy sprites
	 */
	public void levelTwo(int count) {

		Eshots.addAll(enemyG.enemyShot(count));

	}

	public void hitPlayer() {
		for (int i = 0; i < Eshots.size(); i++) {
			if ((tank.PositionX() + tank.width() > Eshots.get(i).positionX())
					&& (Eshots.get(i).positionX() > (tank.PositionX() - Eshots
							.get(i).width()))
					&& (Eshots.get(i).positionY() > (tank.PositionY() - Eshots
							.get(i).height()))
					&& (Eshots.get(i).positionY() < tank.PositionY()
							+ tank.height())) {
				Eshots.remove(i);
				healthBar--;
				if(healthBar == 0){
					highScore = score;
					gameOver a = new gameOver(score);
					this.dispose();
				}

			}
		}
	}

	@Override
	public void run() {
		int count = 0;
		while (true) {
			if (isPause) {
				tank.tankMove();
				try {
					Thread.sleep(50);
				} catch (InterruptedException ie) {
				}
				hitPlayer();
				if (tank.shotCheck() == true) {
					if (shots.size() < shotsNmb)
						shots.add(new tankshot(tank.PositionX(), tank
								.PositionY()));
					tank.finishShot();
				}

				shotUpdate();
				levelSelect(count);
				hitEnemy();
				hitwall();
				repaint();
				count++;
			}else{
				try {  
                    Thread.sleep(2000);  
                } catch (InterruptedException e) {  
                    e.printStackTrace();  
                }  
			}
		}

	}

}
