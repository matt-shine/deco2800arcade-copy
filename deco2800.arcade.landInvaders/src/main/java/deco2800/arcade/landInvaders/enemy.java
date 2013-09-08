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

	
public enemy(int Px,int Py,int SizeW, int SizeH, String img){
	position_x = Px;
	position_y = Py;
	width = SizeW;
	height=SizeH;
	this.img = img;
}

public void drawEnemy(Graphics g,JFrame p){
	//g.setColor(Color.red);
	//g.fillRect(position_x, position_y, width, height);
	Image im = new javax.swing.ImageIcon(this.getClass().getResource(img)).getImage();
	g.drawImage(im,position_x, position_y,width,height,p);
	
	
}

public void moveUpdate(int move,boolean moveDown){
	
	
	
	if(moveDown == true)position_y += 10;
	else position_x += move;
}

public int positionX(){
	
	return position_x;
}

public int positionY(){
	
	return position_y;
}

public int width(){
	return width;
}

public int height(){
	return height;
}






}
