package deco2800.arcade.landInvaders;

import java.awt.Graphics;

import javax.swing.JFrame;

public class blockWall {
	private int rowNum;
	private int rowEnemyNum;
	private enemy[] temp;
	private enemy[][] lists;
	private int px;
	private int py;
	private boolean wallCondition = false;
	
	/**
	 * @param px define the x-coordinates of the wall
	 * @param py define the y-coordinates of the wall
	 * @param height define density(height) of the wall
	 * @param width define the width of the wall
	 * @param img set the name of the image of the wall
	 */
	public blockWall(int px, int py, int height, int width, String img) {
		this.rowNum = height;
		this.rowEnemyNum = width;
		lists = new enemy[height][width];
		this.px=px;
		this.py=py;
		createWall(img);
		wallCondition = true;
	}
	
	/**
	 * @return True if wall is successfully constructed, false otherwise
	 */
	public boolean checkWallCondition()
	{
		return wallCondition;
	}
	
	/**
	 * @return list of wall sprites
	 */
	public enemy[][] checkList()
	{
		return lists;
	}

	/**
	 * @param img set the name of the image of the wall
	 */
	public void createWall(String img) {

		for (int n = 0; n < rowNum; n++) {
			temp = new enemy[rowEnemyNum];
			for (int i = 0; i < rowEnemyNum; i++) {
				temp[i] = new enemy(px+i*10, py + n * 10, 10, 10,img);

			}
			lists[n] = temp;
		}

	}

	/**
	 * @param g the graphics of the game stage
	 * @param p the main frame of the game
	 */
	public void drawWall(Graphics g,JFrame p) {

		for (int n = 0; n < rowNum; n++) {
			for (int i = 0; i < rowEnemyNum; i++) {
				if (lists[n][i] != null) {
					lists[n][i].drawEnemy(g,p);
				}
			}
		}
	}



	/**
	 * @param shot player(tank) shot
	 * @return true if player shot hits the wall, false otherwise
	 *
	 */
	public boolean checkHit(tankshot shot) {

		for (int n = 0; n < rowNum; n++) {
			for (int i = 0; i < rowEnemyNum; i++) {
				if (lists[n][i] != null) {

					if ((((shot.positionX() > lists[n][i].positionX() && shot
							.positionX() < lists[n][i].positionX()
							+ lists[n][i].width()) || ((shot.positionX()
							+ shot.width() > lists[n][i].positionX() && shot
							.positionX() + shot.width() < lists[n][i]
							.positionX() + lists[n][i].width()))) && (shot
							.positionY() < lists[n][i].positionY()
							+ lists[n][i].height() && shot.positionY() > lists[n][i]
							.positionY()))) {

						lists[n][i] = null;
						return true;

					}

				}
			}
		}
		return false;
	}
	
	/**
	 * @param shot enemy sprite shot
	 * @return true if enemy shot hits the wall, false otherwise
	 */
	public boolean checkEnemyHit(enemyShot shot) {

		for (int n = 0; n < rowNum; n++) {
			for (int i = 0; i < rowEnemyNum; i++) {
				if (lists[n][i] != null) {
					
					if ((((shot.positionX() > lists[n][i].positionX() && shot.positionX() < lists[n][i].positionX()+ lists[n][i].width())
							|| ((shot.positionX()+shot.width() > lists[n][i].positionX() && shot.positionX() + shot.width() < lists[n][i].positionX() + lists[n][i].width())))
							&&( (shot.positionY()+shot.height()) <= lists[n][i].positionY()
									+ lists[n][i].height()&&  (shot.positionY() +shot.width()) > lists[n][i].positionY()))) {

						lists[n][i] = null;
						return true;

					}
					
					
					
				}
			}
		}
		return false;
	}
}
