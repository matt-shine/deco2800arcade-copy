package deco2800.arcade.landInvaders;

import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JFrame;

public class enemyGroup {

	private int rowNum;
	private int rowEnemyNum;
	private enemy[] temp;
	private enemy[][] lists;
	private int shotRow;

	/**
	 * @param rowNum number of enemy rows
	 * @param rowEnemyNum create number of enemies per row
	 * @param EsizeW width of a single enemy sprite
	 * @param EsizeH height of a single enemy sprite
	 * @param img
	 */
	public enemyGroup(int rowNum, int rowEnemyNum, int EsizeW, int EsizeH, String img) {

		this.rowNum = rowNum;
		this.rowEnemyNum = rowEnemyNum;
		shotRow= 1;
		lists = new enemy[rowNum][rowEnemyNum];

		createGroup(img, EsizeW, EsizeH);

	}

	/**
	 * @param img
	 * @param EsizeW width of a single enemy sprite
	 * @param EsizeH height of a single enemy sprite
	 */
	public void createGroup(String img ,int EsizeW, int EsizeH) {

		for (int n = 0; n < rowNum; n++) {
			temp = new enemy[rowEnemyNum];
			for (int i = 0; i < rowEnemyNum; i++) {
				temp[i] = new enemy(100 + i * 100, 100 + n * 70, EsizeW, EsizeH, img);

			}
			lists[n] = temp;
		}

	}

	/**
	 * @param g
	 * @param p
	 */
	public void drawGroup(Graphics g,JFrame p) {

		for (int n = 0; n < rowNum; n++) {
			for (int i = 0; i < rowEnemyNum; i++) {
				if (lists[n][i] != null) {
					lists[n][i].drawEnemy(g,p);
				}

			}
		}

	}
	
	/**
	 * @param move
	 * @param moveDown
	 */
	public void moveUpdate(int move, boolean moveDown){
		for (int n = 0; n < rowNum; n++) {
			for (int i = 0; i < rowEnemyNum; i++) {
				if (lists[n][i] != null) {
					lists[n][i].moveUpdate(move,moveDown);
				}

			}
		}
		
		
	}

	/**
	 * @param shot determine if shot of player(tank) has hit a single enemy sprite
	 * @return true if player shot has hit enemy, false otherwise
	 */
	public boolean checkHit(tankshot shot) {

		for (int n = 0; n < rowNum; n++) {
			for (int i = 0; i < rowEnemyNum; i++) {
				if (lists[n][i] != null) {
					
					if ((((shot.positionX() > lists[n][i].positionX() && shot.positionX() < lists[n][i].positionX()+ lists[n][i].width())
							|| ((shot.positionX()+shot.width() > lists[n][i].positionX() && shot.positionX() + shot.width() < lists[n][i].positionX() + lists[n][i].width())))
							&&( shot.positionY() < lists[n][i].positionY()
									+ lists[n][i].height()&&  shot.positionY()> lists[n][i].positionY()))) {

						lists[n][i] = null;
						return true;
					}
				}
			}
		}
		return false;
	}
	
	/**
	 * @param count controls the rate of shots fired by enemy sprites
	 * @return arraylist of enemy shots
	 */
	public ArrayList<enemyShot> enemyShot(int count){
		ArrayList<enemyShot> shots =new ArrayList<enemyShot>();
		if(count%50==0){
			
			for (int i = 0; i < rowEnemyNum; i++) {
				if (lists[shotRow-1][i] != null) {
					shots.add(new enemyShot(lists[shotRow-1][i].positionX(),lists[shotRow-1][i].positionY()));
				}		
			}
			if(shotRow != 3){
				shotRow ++;
			}else{
				shotRow =1;
			}
		}
		return shots;
	}
	
	/**
	 * @return true if enemy group is empty, false otherwise
	 */
	public boolean isEmpty(){
		
		for (int n = 0; n < rowNum; n++) {
			for (int i = 0; i < rowEnemyNum; i++) {
				if (lists[n][i] != null) {
					return false;
					
				}

			}
		}
		return true;
	}

}
