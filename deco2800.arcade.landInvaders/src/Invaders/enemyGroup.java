package Invaders;

import java.awt.Graphics;

public class enemyGroup {

	private int rowNum;
	private int rowEnemyNum;
	private enemy[] temp;
	private enemy[][] lists;

	public enemyGroup(int rowNum, int rowEnemyNum) {

		this.rowNum = rowNum;
		this.rowEnemyNum = rowEnemyNum;
		lists = new enemy[rowNum][rowEnemyNum];

		createGroup();

	}

	public void createGroup() {

		for (int n = 0; n < rowNum; n++) {
			temp = new enemy[rowEnemyNum];
			for (int i = 0; i < rowEnemyNum; i++) {
				temp[i] = new enemy(100 + i * 100, 100 + n * 40, 30, 30);

			}
			lists[n] = temp;
		}

	}

	public void drawGroup(Graphics g) {

		for (int n = 0; n < rowNum; n++) {
			for (int i = 0; i < rowEnemyNum; i++) {
				if (lists[n][i] != null) {
					lists[n][i].drawEnemy(g);
				}

			}
		}

	}
	
	public void moveUpdate(int move){
		for (int n = 0; n < rowNum; n++) {
			for (int i = 0; i < rowEnemyNum; i++) {
				if (lists[n][i] != null) {
					lists[n][i].moveUpdate(move);
				}

			}
		}
		
		
	}

	public boolean checkHit(tankshot shot) {

		for (int n = 0; n < rowNum; n++) {
			for (int i = 0; i < rowEnemyNum; i++) {
				if (lists[n][i] != null) {
					
					if ((((shot.positionX() > lists[n][i].positionX() && shot.positionX() < lists[n][i].positionX()+ lists[n][i].width())
							|| ((shot.positionX()+shot.width() > lists[n][i].positionX() && shot.positionX() + shot.width() < lists[n][i].positionX() + lists[n][i].width())))
							&& shot.positionY() < lists[n][i].positionY()
									+ lists[n][i].height())) {

						lists[n][i] = null;
						return true;

					}
					
					
					
				}
			}
		}
		return false;
	}

}
