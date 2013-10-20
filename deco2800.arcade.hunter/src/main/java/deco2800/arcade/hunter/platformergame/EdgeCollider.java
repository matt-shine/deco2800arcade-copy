package deco2800.arcade.hunter.platformergame;

public class EdgeCollider {
    public boolean top = false;
    public boolean bottom = false;
    public boolean left = false;
    public boolean right = false;

    public void clear() {
        top = false;
        bottom = false;
        left = false;
        right = false;
    }
}
