package deco2800.arcade.wolf;

import com.badlogic.gdx.math.Vector2;

import deco2800.arcade.wolf.WL6Meta.KEY_TYPE;
import deco2800.arcade.wolf.enemy.*;

public class MapProcessor {

    /**
     * Takes the game and generates all the items on the map. Most items are
     * handled automatically as described in WL6Meta, but for the few special cases,
     * This is where they are handled.
     * @param model
     */
    public static void processEverything(GameModel model) {

        Level map = model.getMap();
        
        
        
        //spawn all the blocks that are actually doodads
        //and initialise the collision map
        for (int i = 0; i < WL6.MAP_DIM; i++) {
            for (int j = 0; j < WL6.MAP_DIM; j++) {

                int id = map.getTerrainAt(i, j);
                BlockInfo dInfo = WL6Meta.block(id);

                if (WL6Meta.hasDoorAt(i, j, map)) {
                    spawnDoor(model, dInfo, id, i, j);
                }
                
                model.getCollisionGrid().setSolidAt(i, j, dInfo.solid ? 1 : 0);
            }
        }
        
        
        
        //spawn all the doodads
        for (int i = 0; i < WL6.MAP_DIM; i++) {
            for (int j = 0; j < WL6.MAP_DIM; j++) {

                //now generate all the items

                int id = map.getDoodadAt(i, j);
                DoodadInfo dInfo = WL6Meta.doodad(id);
                
                
                //spawn points
                if (id >= WL6Meta.SPAWN_POINT && id < WL6Meta.SPAWN_POINT + 4) {

                    model.setSpawnPoint(i + 0.5f, j + 0.5f, WL6Meta.dirToAngle(dInfo.direction));

                } else if (id == WL6Meta.SECRET_DOOR) {

                    SecretDoor door = new SecretDoor(doodadID());
                    door.setTextureName(dInfo.texture);
                    door.setPos(new Vector2(i + 0.5f, j + 0.5f));
                    model.addDoodad(door);
                    
                } else if (id == WL6Meta.GOLDKEY) {
                	
                    Pickup key = new Pickup(doodadID(), KEY_TYPE.GOLD);
                    key.setPos(new Vector2(i + 0.5f, j + 0.5f));
                    model.addDoodad(key);
                    
                } else if (id == WL6Meta.SILVERKEY) {
                	
                    Pickup key = new Pickup(doodadID(), KEY_TYPE.SILVER);
                    key.setPos(new Vector2(i + 0.5f, j + 0.5f));
                    model.addDoodad(key);

                } else if (id == WL6Meta.ENDGAME) {
                	
                    LevelEnd end = new LevelEnd(doodadID());
                    end.setPos(new Vector2(i + 0.5f, j + 0.5f));
                    model.addDoodad(end);

                } else {

                    //everything else
                    spawnDoodadFromInfo(model, dInfo, id, i, j);
                    
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
        KEY_TYPE k = null;
        if (id == WL6Meta.DOOR_GOLDKEY || id == WL6Meta.DOOR_GOLDKEY + 1) {
        	k = KEY_TYPE.GOLD;
        }
        if (id == WL6Meta.DOOR_SILVERKEY || id == WL6Meta.DOOR_SILVERKEY + 1) {
        	k = KEY_TYPE.SILVER;
        }
        
        Door door = new Door(doodadID(), id % 2 != 0, k);
        door.setTextureName(bInfo.texture);
        if (id == WL6Meta.DOOR_ELEVATOR) {
        	door.setTextureName("elevator_door");
        } else if (id == WL6Meta.DOOR_GOLDKEY) {
        	door.setTextureName("gold_door");
        } else if (id == WL6Meta.SILVERKEY) {
        	door.setTextureName("solver_door");
        } else {
        	door.setTextureName("door");
        }
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
        
        if (d.texture == null) {
            // This doodad is invisible so it must be a waypoint or something
        	
        	
            if (d.direction != null) {
                model.addWaypoint(d.direction, x, y);
            }
            
            
        } else if (d.enemytype != EnemyType.NOT_AN_ENEMY) {
            switch (d.enemytype) {
                case NOT_AN_ENEMY:
                    break;
                case GUARD:
                    dd = new Guard(doodadID(), d);
                    break;
                case OFFICER:
                	dd = new Officer(doodadID(), d);
                	break;
                case SS:
                    dd = new SS(doodadID(), d);
                    break;
                case DOG:
                    dd = new Dog(doodadID(), d);
                    break;
                case MUTANT:
                	dd = new Mutant(doodadID(), d);
                	break;
                case FAKE_HITLER:
                	dd = new FakeHitler(doodadID(), d);
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
                	dd = new Hans(doodadID(), d);
                	break;
                case SCHABBS:
                	dd = new Schabbs(doodadID(), d);
                	break;
                case HITLER:
                	dd = new Hitler(doodadID(), d);
                	break;
                case GIFTMACHER:
                	dd = new Giftmacher(doodadID(), d);
                	break;
                case GRETEL:
                	dd = new Gretel(doodadID(), d);
                	break;
                case FETTGESICHT:
                	dd = new Fettgesicht(doodadID(), d);
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
            dd = new Doodad(doodadID());
            dd.setTextureName(d.texture);
            model.getCollisionGrid().setSolidAt(x, y, 1);
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
