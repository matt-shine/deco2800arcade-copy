package deco2800.arcade.wl6;

public class Pickup extends Doodad {

    private int health = 0;
    private int points = 0;
    private int gun = 0;
    private int ammo = 0;
    private boolean overheal = false;//TODO overhealing

    public Pickup(int uid, int health, int points, int ammo, int gun) {
        super(uid);
        this.health = health;
        this.points = points;
        this.gun = gun;
        this.ammo = ammo;
    }

    @Override
    public void tick(GameModel game) {

        if (this.getPos().dst(game.getPlayer().getPos()) < 0.8) {
            game.destroyDoodad(this);
            
            game.getPlayer().addAmmo(this.ammo);
            game.getPlayer().addPoints(this.points);
            game.getPlayer().addGun(this.gun);
            game.getPlayer().addHealth(this.health, overheal);
            
        }

    }



}
