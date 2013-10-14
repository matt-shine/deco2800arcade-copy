package deco2800.arcade.wl6.enemy;

public class SS extends Enemy {

    private int STARTING_HEALTH = 50;

    public SS(int uid) {
        super(uid);

        setHealth(STARTING_HEALTH);
        pathSpeed = 512;
        chaseSpeed = 1536;
        pain = true;
        points = 200;

        damage = 0;
        dType = HITSCAN;
        this.setTextureName("lemongrab");
    }



}
