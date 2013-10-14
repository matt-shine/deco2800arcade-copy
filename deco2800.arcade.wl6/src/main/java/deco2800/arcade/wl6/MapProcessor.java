package deco2800.arcade.wl6;

import com.badlogic.gdx.math.Vector2;

import deco2800.arcade.wl6.WL6Meta.KEY_TYPE;
import deco2800.arcade.wl6.enemy.Dog;
import deco2800.arcade.wl6.enemy.EnemyType;
import deco2800.arcade.wl6.enemy.Guard;

public class MapProcessor {

    /**
     * Takes the game and generates all the items on the map. Most items are
     * handled automatically as described in WL6Meta, but for the few special cases,
     * This is where they are handled.
     * @param model
     */
    public static void processEverything(GameModel model) {

        Level map = model.getMap();

        for (int i = 0; i < WL6.MAP_DIM; i++) {
            for (int j = 0; j < WL6.MAP_DIM; j++) {

                //now generate all the items

                int id = map.getDoodadAt(i, j);
                DoodadInfo dInfo = WL6Meta.doodad(id);

                //spawn points
                if (id >= WL6Meta.SPAWN_POINT && id < WL6Meta.SPAWN_POINT + 4) {

                    model.setSpawnPoint(i + 0.5f, j + 0.5f, WL6Meta.dirToAngle(dInfo.facingDir));

                } else if (id == WL6Meta.SECRET_DOOR) {

                    SecretDoor door = new SecretDoor(doodadID());
                    door.setTextureName(dInfo.texture);
                    door.setPos(new Vector2(i + 0.5f, j + 0.5f));
                    model.addDoodad(door);

                } else {

                    //everything else
                    spawnDoodadFromInfo(model, dInfo, id, i, j);

                }
            }
        }

        //spawn all the blocks that are actually doodads
        for (int i = 0; i < WL6.MAP_DIM; i++) {
            for (int j = 0; j < WL6.MAP_DIM; j++) {

                int id = map.getTerrainAt(i, j);
                BlockInfo dInfo = WL6Meta.block(id);

                if (WL6Meta.hasDoorAt(i, j, map)) {
                    spawnDoor(model, dInfo, id, i, j);
                }
            }
        }
    }

    /**
     * Unique ids for doodads. TODO make this better
     *
     * @return
     */
    public static int doodadID() {
        return (int) Math.floor(Math.random() * Integer.MAX_VALUE);
    }

    public static void spawnDoor(GameModel model, BlockInfo bInfo, int id, int x, int y) {
        //TODO: respect door types
        Door door = new Door(doodadID(), id % 2 != 0, KEY_TYPE.NONE);
        door.setTextureName(bInfo.texture);
        door.setPos(new Vector2(x + 0.5f, y + 0.5f));
        model.addDoodad(door);
    }


    /**
     * Takes a DoodadInfo and turns it into a real doodad.
     * @param model The game
     * @param d The DoodadInfo object
     * @param id The tile id of the doodad
     * @param x The x position of the doodad
     * @param y The y position of the doodad
     */
    public static void spawnDoodadFromInfo(GameModel model, DoodadInfo d, int id, int x, int y) {
        Doodad dd = null;

        if (d.special) {
            System.err.println("Tried to automatically generate a special case doodad: " + id + " (" + x + ", " + y + ")");
            return;
        }

        if (d.difficulty > model.getDifficulty()) {
        	return;
        }
        
        if (d.texture == null)
        {
            // This doodad is invisible so it must be a waypoint or something
            if (d.facingDir != null) {
                model.addWaypoint(WL6Meta.dirToAngle(d.facingDir), x, y);
            }
        }
        else if (d.enemytype != EnemyType.NOT_AN_ENEMY)
        {
            switch (d.enemytype) {
                case NOT_AN_ENEMY:
                    break;
                case GUARD:
                    dd = new Guard(doodadID());
                    break;
                case OFFICER:
                    break;
                case SS:
                    break;
                case DOG:
                    dd = new Dog(doodadID());
                    break;
                case MUTANT:
                    break;
                case FAKE_HITLER:
                    break;
                case GHOSTS_1:
                    break;
                case GHOSTS_2:
                    break;
                case GHOSTS_3:
                    break;
                case GHOSTS_4:
                    break;
                case HANS:
                    break;
                case SCHABBS:
                    break;
                case HITLER:
                    break;
                case GIFTMACHER:
                    break;
                case GRETEL:
                    break;
                case FETTGESICHT:
                    break;
            }
        }
        else if (d.health != 0 || d.points != 0 || d.ammo != 0 || d.gun != 0)
        {
            dd = new Pickup(doodadID(), d.health, d.points, d.ammo, d.gun);
            dd.setTextureName(d.texture);
        }
        else if (d.solid)
        {
            //TODO make these solid
            dd = new Doodad(doodadID());
            dd.setTextureName(d.texture);
        }
        else
        {
            //spawn a static nonsolid doodad
            dd = new Doodad(doodadID());
            dd.setTextureName(d.texture);
        }

        if (dd != null)
        {
            dd.setPos(new Vector2(x + 0.5f, y + 0.5f));
            model.addDoodad(dd);
        }

    }
}
