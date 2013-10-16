package deco2800.arcade.connect4;

import com.badlogic.gdx.graphics.Color;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.*;

public class Buttons {
	private SpriteBatch batch;
	private BitmapFont font;
	private long xPos;
	private long yPos;
	private final int WIDTH = 100;
	private final int spaceAmt = 50;
	private boolean isDisplayed;
	private ArrayList<ButtonSingle> buttons =  new ArrayList<ButtonSingle>();
	
	public Buttons() {
		xPos = 0;
		yPos = 0;
		isDisplayed = false;
		font = new BitmapFont();
		font.setScale(2);
		batch = new SpriteBatch();
		
		ButtonSingle a = new ButtonSingle(1, "Quit", Color.RED, yPos);
		buttons.add(a);
		System.out.println(a.buttonText);
		ButtonSingle b = new ButtonSingle(2, "Replay", Color.RED, yPos - spaceAmt);
		buttons.add(b);
	}
	
	public void display(){
		isDisplayed = true;
	}
	
	public void hide(){
		isDisplayed = false;
	}
	
	public void setX(long xSet){
		xPos = xSet - WIDTH;
	}
	
	public void setY(long ySet){
		yPos = ySet;
	}

    public void render()
    {
    	if (isDisplayed) {
    		batch.begin();
    		for (ButtonSingle x: buttons){
    			font.setColor(x.buttonColor);   			
    			font.draw(batch, x.buttonText, xPos, yPos - (spaceAmt * (x.buttonNo-1)) );
    		}
    		batch.end();
    		//font.setColor(Color.RED);
    		//font.draw(batch, "Quit", xPos, yPos); //button 1
    		//System.out.println(xPos);
    		//System.out.println(yPos);
    		//System.out.println("Font button 1 ^");
    		//font.draw(batch, "Replay", xPos, yPos - spaceAmt); //button 2
    		//System.out.println(xPos);
    		//System.out.println(yPos - spaceAmt);
    		//batch.end();
    	}
    }
    
    public void checkHovered(long mouseX1, long mouseY1){
    	double mouseX = mouseX1 * 1.6;
    	double mouseY = mouseY1 * 1.6;
    	if ( (mouseX >= xPos) && ( mouseX <= ( xPos + WIDTH)) ) {
    		for (ButtonSingle x: buttons){
    			if ( (mouseY >= yPos - (spaceAmt*(x.buttonNo-1))) && (mouseY < (yPos - (spaceAmt*(x.buttonNo-1)) + spaceAmt )) ) {
    				buttons.get(x.buttonNo-1).buttonColor = Color.YELLOW;
    			} else {
    				buttons.get(x.buttonNo-1).buttonColor = Color.RED;
    			}
    		}
    		
    		
    		/*if ( (mouseY >= yPos) && (mouseY < yPos + spaceAmt) ){
    			buttons.get(0).buttonColor = Color.YELLOW;
    		} else {
    			buttons.get(0).buttonColor = Color.RED;
    		}
    		
    		if ( (mouseY >= yPos - spaceAmt) && (mouseY < yPos) ){
    			buttons.get(1).buttonColor = Color.YELLOW;
    		} else {
    			buttons.get(1).buttonColor = Color.RED;
    		}*/
    	}
    }
    
    public int checkButtonsPressed(long mouseX1, long mouseY1){
    	double mouseX = mouseX1 * 1.6;
    	double mouseY = mouseY1 * 1.6;
    	if ( (mouseX >= xPos) && ( mouseX <= ( xPos + WIDTH)) ) {
    		if ( (mouseY >= yPos) && (mouseY < yPos + spaceAmt) ){
    			return 1;
    		} else if ( (mouseY >= yPos - spaceAmt) && (mouseY < yPos) ){
    			return 2;
    		}
    	}
    	return 0;
    }	
}
