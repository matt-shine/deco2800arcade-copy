package zombieInvaders;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;

//Need to do up class Sprite
public class tank extends Sprites implements Stats{
    public final int START_Y = 300;
    public final int START_X = 300;
    private final String player = "AngryRedBird.png";
    private int iconWidth;
    
    public tank(){


    	ImageIcon playerImage = new ImageIcon(this.getClass().getResource(player));
    	iconWidth = playerImage.getImage().getWidth(null);
    	
    	setImage(playerImage.getImage());
    	setX(START_X);
    	setY(START_Y);
    	
    }
    public void move() {
    	xCoords += directCoords;
        if (xCoords <= 2)
        {
        	xCoords = 2;
        }
        
        if (xCoords >= BOARD_WIDTH - 2*iconWidth)
        {
        	xCoords = BOARD_WIDTH - 2*iconWidth;
        }
    }

    public void pressKey(KeyEvent e) {
        int keyCode = e.getKeyCode();

        if (keyCode == KeyEvent.VK_LEFT)
        {
        	directCoords = -2;
        }

        if (keyCode == KeyEvent.VK_RIGHT)
        {
        	directCoords = 2;
        }

    }

    public void releaseKey(KeyEvent e) {
        int keyCode = e.getKeyCode();

        if (keyCode == KeyEvent.VK_LEFT)
        {
        	directCoords = 0;
        }

        if (keyCode == KeyEvent.VK_RIGHT)
        {
        	directCoords = 0;
        }
    }
}
