package zombieInvaders;

import javax.swing.ImageIcon;

public class shot extends Sprite {

	//TODO shot image, file location
	private String shot = "";
	private final int H_SPACE = 6;
    private final int V_SPACE = 1;

    public Shot() {
    }

    public Shot(int x, int y) {

        ImageIcon ii = new ImageIcon(this.getClass().getResource(shot));
        setImage(ii.getImage());
        setX(x + H_SPACE);
        setY(y - V_SPACE);
    }
}
