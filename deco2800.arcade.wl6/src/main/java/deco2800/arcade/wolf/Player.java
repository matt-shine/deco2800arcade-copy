package deco2800.arcade.wolf;

import java.util.HashSet;

import com.badlogic.gdx.math.Vector2;

import deco2800.arcade.wolf.WL6Meta.KEY_TYPE;

public class Player extends Mob {

    public static final float SPEED = 0.05f;


    private int STARTING_HEALTH = 100;
    private int points = 0;
    private int currentGun = 1;
    private HashSet<Integer> guns = new HashSet<Integer>();
    private int ammo = 16;
    private HashSet<KEY_TYPE> keys = new HashSet<KEY_TYPE>();

    private float gunTimer = 0;


    public Player(int uid) {
        super(uid);
        setHealth(STARTING_HEALTH);
        guns.add(0);
        guns.add(1);
    }


    public void draw(Renderer renderer) {
        if (renderer.isDebugMode()) {
            renderer.drawBasicSprite(getTextureName(), getPos().x, getPos().y, -getAngle());
        }

        //no super call
    }



    @Override
    public void tick(GameModel model) {

        this.gunTimer -= model.delta();

        //detect the end of a level
        Vector2 blockPos = getBlockPos();
        int neighbours = 0;
        if (model.getMap().getTerrainAt((int) blockPos.x + 1, (int) blockPos.y) == WL6Meta.ELEVATOR) {
            neighbours++;
        }

        if (model.getMap().getTerrainAt((int) blockPos.x - 1, (int) blockPos.y) == WL6Meta.ELEVATOR) {
            neighbours++;
        }

        if (model.getMap().getTerrainAt((int) blockPos.x, (int) blockPos.y + 1) == WL6Meta.ELEVATOR) {
            neighbours++;
        }

        if (model.getMap().getTerrainAt((int) blockPos.x, (int) blockPos.y - 1) == WL6Meta.ELEVATOR) {
            neighbours++;
        }

        if (neighbours >= 3) {
            //if we're standing on a secret elevator, go to secret floor, also if we're if this
            //magic position (10, 51) because the secret elevator isn't in the map file on the first level
            //for god knows why. It looks like the real wolfenstein game hard codes this too.
            if (model.getMap().getTerrainAt((int) blockPos.x, (int) blockPos.y) == WL6Meta.SECRET_ELEVATOR ||
                    (model.getChapter().equals("1") && ((int) blockPos.x) == 10 && ((int) blockPos.y) == 51)) {
                model.secretLevel();
            } else {
                model.nextLevel();
            }

        }

        if (this.getHealth() <= 0) {
            model.reset();
        }

        super.tick(model);
    }


    public void shoot(GameModel g, boolean justDown) {
        if (gunTimer <= 0 && ammo > 0 && (currentGun != 1 || justDown)) {
            int damage = calcDamage(2);
            Projectile bullet = new Projectile(0, damage, false, "worm");
            g.addDoodad(bullet);
            bullet.setPos(this.getPos());
            bullet.setVel((new Vector2(0, -0.2f)).rotate(-this.getAngle()));
            this.ammo = Math.max(ammo - 1, 0);

            if (this.currentGun == 1) {
                gunTimer = 0;
            } else if (this.currentGun == 2) {
                gunTimer = 0.25f;
            } else if (this.currentGun == 3) {
                gunTimer = 0.12f;
            }

        }
    }



    public void addHealth(int health, boolean overheal) {
        setHealth(Math.min(this.getHealth() + health, overheal ? 150 : 100));
    }


    public int getPoints() {
        return points;
    }


    public void setPoints(int points) {
        this.points = points;
    }


    public void addPoints(int points) {
        this.points += points;
    }


    public int getCurrentGun() {
        return currentGun;
    }


    public void setCurrentGun(int currentGun) {
        if (this.guns.contains(currentGun)) {
            this.currentGun = currentGun;
        }
    }


    public int getAmmo() {
        return ammo;
    }


    public void setAmmo(int ammo) {
        if (this.ammo == 0 && ammo > 0) {
            if (guns.contains(3)) {
                this.currentGun = 3;
            } else if (guns.contains(2)) {
                this.currentGun = 2;
            } else {
                this.currentGun = 1;
            }
        }
        this.ammo = Math.min(ammo, 99);
        if (ammo <= 0) {
            this.ammo = 0;
            this.setCurrentGun(0);
        }
    }

    public void addAmmo(int ammo) {
        setAmmo(this.ammo + ammo);
    }


    public void addGun(int gun) {

        if (!guns.contains(gun)) {
            this.currentGun = gun;
        }

        this.guns.add(gun);

    }

    public HashSet<Integer> getGuns() {
        return new HashSet<Integer>(guns);
    }

    public void setGuns(HashSet<Integer> newGuns) {
        guns = new HashSet<Integer>(newGuns);
    }

    public void addKey(KEY_TYPE k) {
        keys.add(k);
    }

    public boolean hasKey(KEY_TYPE k) {
        return keys.contains(k);
    }

    @Override
    public void takeDamage(GameModel model, int damage) {
        if (model.getDifficulty() == 1) {
            setHealth(getHealth() - (damage / 4));
        }
        else {
            setHealth(getHealth() - damage);
        }
    }

    /**
     * Damage Calculation
     *
     * @param dist Distance between enemy and player (in number of squares)
     * @return Damage to be dealt.
     */
    public int calcDamage(int dist) {
        boolean hit = false;
        if (dist > 4) {
            if (randInt(0, 255, getRand()) / 12 < dist) {
                hit = true;
            }
        }
        else {
            hit = true;
        }

        int damage = randInt(0, 255, getRand());

        if (hit) {
            if (dist < 2) {
                damage = damage / 4;
            }
            else {
                damage = damage / 6;
            }
        }
        else {
            damage = 0;
        }

        return damage;
    }



}






