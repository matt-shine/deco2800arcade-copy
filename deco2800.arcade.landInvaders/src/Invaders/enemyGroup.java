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
				temp[i] = new enemy(100+i*100,100+n*40,10,10);

			}
			lists[n] = temp;
		}

	}
	
	public void drawGroup(Graphics g,int move){
		
		for (int n = 0; n < rowNum; n++) {
			for (int i = 0; i < rowEnemyNum; i++) {
				lists[n][i].drawEnemy(g,move);

			}
		}
		
		
	}

}
