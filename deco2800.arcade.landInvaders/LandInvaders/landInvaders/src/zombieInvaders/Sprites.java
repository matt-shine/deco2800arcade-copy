package zombieInvaders;

import java.awt.Image;

public class Sprites {

    private boolean visible;
    private Image image;
    protected int xCoords;
    protected int yCoords;
    protected boolean dying;
    protected int directCoords;

    public Sprites() {
        visible = true;
    }

    public void die() {
        visible = false;
    }

    public boolean isVisible() {
        return visible;
    }

    protected void setVisible(boolean visible) {
        this.visible = visible;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public Image getImage() {
        return image;
    }

    public void setX(int xCoords) {
        this.xCoords = xCoords;
    }

    public void setY(int yCoords) {
        this.yCoords = yCoords;
    }
    public int getY() {
        return yCoords;
    }

    public int getX() {
        return xCoords;
    }

    public void setDying(boolean dying) {
        this.dying = dying;
    }

    public boolean isDying() {
        return this.dying;
    }
}
