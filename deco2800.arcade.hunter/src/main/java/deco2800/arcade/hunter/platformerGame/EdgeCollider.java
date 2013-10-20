package deco2800.arcade.hunter.platformerGame;

public class EdgeCollider {
    private boolean top = false;
    private boolean bottom = false;
    private boolean left = false;
    private boolean right = false;

    public boolean isBottom() {
        return bottom;
    }

    public void setBottom(boolean bottom) {
        this.bottom = bottom;
    }

    public boolean isLeft() {
        return left;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public boolean isRight() {
        return right;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public boolean isTop() {
        return top;
    }

    public void setTop(boolean top) {
        this.top = top;
    }

    public void clear() {
        top = false;
        bottom = false;
        left = false;
        right = false;
    }
}
