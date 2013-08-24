package zombieInvaders;

import javax.swing.ImageIcon;

public class Shot extends Sprites {

	//TODO shot image, file location
	private String shot = "";
	private final int HSpace = 6;
    private final int VSpace = 1;

    public Shot(int x, int y) {

        ImageIcon ii = new ImageIcon(this.getClass().getResource(shot));
        setImage(ii.getImage());
        setX(x + HSpace);
        setY(y - VSpace);
    }
}