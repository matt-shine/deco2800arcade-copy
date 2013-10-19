package deco2800.arcade.landInvaders;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JFrame;


public class enemy {
	private int position_x;
	private int position_y;
	private int width;
	private int height;
	private String img;

	
/**
 * @param Px define x-coordinate of single enemy sprite
 * @param Py define y-coordinate of single enemy sprite
 * @param SizeW define width of single enemy sprite
 * @param SizeH define height of single enemy sprite
 * @param img set the name of the image of the enemy sprite
 */
public enemy(int Px,int Py,int SizeW, int SizeH, String img){
	position_x = Px;
	position_y = Py;
	width = SizeW;
	height=SizeH;
	this.img = img;
}

/**
 * @param g the graphic of the game stage
 * @param p the main frame of the game
 */
public void drawEnemy(Graphics g,JFrame p){
	//g.setColor(Color.red);
	//g.fillRect(position_x, position_y, width, height);
	Image im = new javax.swing.ImageIcon(this.getClass().getResource(img)).getImage();
	g.drawImage(im,position_x, position_y,width,height,p);
	
	
}

/**
 * @param move distance enemy will travel
 * @param moveDown direction of enemy movement
 */
public void moveUpdate(int move,boolean moveDown){
	
	if(moveDown == true)position_y += 10;
	else position_x += move;
}

/**
 * @return x-coordinate of enemy sprite
 */
public int positionX(){
	
	return position_x;
}

/**
 * @return y-coordinate of enemy sprite
 */
public int positionY(){
	
	return position_y;
}

/**
 * @return width of enemy sprite 
 */
public int width(){
	return width;
}

/**
 * @return height of enemy sprite
 */
public int height(){
	return height;
}






}
