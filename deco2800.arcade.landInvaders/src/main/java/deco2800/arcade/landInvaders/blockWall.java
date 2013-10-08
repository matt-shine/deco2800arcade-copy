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
	
	public blockWall(int px, int py, int rowNum, int rowEnemyNum, String img) {
		this.rowNum = rowNum;
		this.rowEnemyNum = rowEnemyNum;
		lists = new enemy[rowNum][rowEnemyNum];
		this.px=px;
		this.py=py;
		createWall(img);
		

	}

	public void createWall(String img) {

		for (int n = 0; n < rowNum; n++) {
			temp = new enemy[rowEnemyNum];
			for (int i = 0; i < rowEnemyNum; i++) {
				temp[i] = new enemy(px+i*10, py + n * 10, 10, 10,img);

			}
			lists[n] = temp;
		}

	}

	public void drawWall(Graphics g,JFrame p) {

		for (int n = 0; n < rowNum; n++) {
			for (int i = 0; i < rowEnemyNum; i++) {
				if (lists[n][i] != null) {
					lists[n][i].drawEnemy(g,p);
				}

			}
		}

	}



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
