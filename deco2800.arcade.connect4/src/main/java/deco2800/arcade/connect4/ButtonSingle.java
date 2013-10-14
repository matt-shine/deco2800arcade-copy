package deco2800.arcade.connect4;

import com.badlogic.gdx.graphics.Color;

public class ButtonSingle {
    public int buttonNo;
    public String buttonText;
    public Color buttonColor;
    private long buttonYPos;
    private long buttonXPos;
    private int buttonWidth;
    private int buttonHeight;
    private int buttonID;
	    	
	public ButtonSingle(int buttonNo, int buttonID, String buttonText, Color buttonColor, long buttonYPos, long buttonXPos, int buttonWidth, int buttonHeight){
		this.buttonNo = buttonNo;
		this.buttonID = buttonID;
	    this.buttonText = buttonText;
	    this.buttonColor = buttonColor;
	    this.buttonYPos = buttonYPos;
	    this.buttonXPos = buttonXPos;
	    this.buttonWidth = buttonWidth;
	    this.buttonHeight = buttonHeight;
	}
	
	public int GetButtonID(){
		return this.buttonID;
	}
	
	public long GetXPos(){
		return this.buttonXPos;
	}
	
	public long GetYPos(){
		return this.buttonYPos;
	}
	
	public void CheckHovered(double mouseX, double mouseY) {
		if ((mouseX >= buttonXPos) && (mouseX <= (buttonXPos + buttonWidth))) {
			if ((mouseY >= buttonYPos) && (mouseY < (buttonYPos + buttonHeight))) {
				buttonColor = Color.YELLOW;
			} else {
				buttonColor = Color.RED;
			}
		} else {
			buttonColor = Color.RED;
		}
	}
	
	public boolean CheckPressed(double mouseX, double mouseY) {
		if ((mouseX >= buttonXPos) && (mouseX <= (buttonXPos + buttonWidth))) {
			if ((mouseY >= buttonYPos) && (mouseY < (buttonYPos + buttonHeight))) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
	
	public void setXPos(long newXPos) {
		buttonXPos = newXPos;
	}
	
	public void setYPos(long newYPos) {
		buttonYPos = newYPos;
	}
}
