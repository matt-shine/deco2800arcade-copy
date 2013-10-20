package deco2800.arcade.hunter.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import deco2800.arcade.hunter.Hunter;
import deco2800.arcade.hunter.Hunter.Config;
import deco2800.arcade.hunter.model.MapPane.MapType;
import deco2800.arcade.hunter.screens.GameScreen;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

public class ForegroundLayer extends Map {
    private final ArrayList<MapPane> panes; //Set of the currently loaded panes
    private final int paneCount; //How many panes should we keep loaded at a time, should be the number that can fit on the screen plus one
    private MapType currentType = MapType.GRASS;

    //Key is one of the MapType enums, value is a list of pane objects which can be instantiated
    private final HashMap<MapType, ArrayList<MapPane>> mapPanes = new HashMap<MapType, ArrayList<MapPane>>();

    private final GameScreen gameScreen;

    /**
     * @param speedModifier How fast the pane should move relative to the camera. 1 is the same speed as the camera.
     * @param paneCount map panes to keep loaded at a time
     * @param gameScreen the GameScreen that controls this ForegroundLayer instance
     */
    public ForegroundLayer(float speedModifier, int paneCount, GameScreen gameScreen) {
        super(speedModifier);
        this.paneCount = paneCount;
        this.gameScreen = gameScreen;

        loadPanes(Gdx.files.internal("maps/maplist.txt"));

        //Initialise the panes list with a full set of panes
        panes = new ArrayList<MapPane>(paneCount);
        while (panes.size() < this.paneCount) {
            panes.add(getRandomPane());
        }
    }

    /**
     * Load a set of panes from a given file into the mapPanes HashMap above
     * file should have the format (file type doesn't matter):
     * [MapType]
     * filename.map
     * filename2.map
     * [MapType2]
     * filename3.map
     *
     * @param listFile file containing a list of maps to load.
     */
    private void loadPanes(FileHandle listFile) {
        BufferedReader br;
        String line, tagText;
        MapType type = null;

        try {
            br = new BufferedReader(new InputStreamReader(listFile.read()));

            //Iterate through the file one line at a time
            while (null != (line = br.readLine())) {
                if (line.matches("\\[\\w+\\]")) {
                    //Line is a tag which corresponds to a set of maps for a new MapType
                    tagText = line.replace("[", "").replace("]", "");
                    try {
                        type = MapType.valueOf(tagText);

                    } catch (IllegalArgumentException e) {
                        e.printStackTrace();
                    }
                } else if (type != null) {
                    //Here's a map file for the current type! Add it to the list.
                    ArrayList<MapPane> typePanes = mapPanes.get(type);
                    if (typePanes == null) {
                        typePanes = new ArrayList<MapPane>();
                    }
                    typePanes.add(new MapPane("maps/" + line, type));
                    mapPanes.put(type, typePanes);
                }
            }

            br.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Get a random map pane of the current pane type
     *
     * @return new MapPane
     */
    private MapPane getRandomPane() {
        ArrayList<MapPane> typePanes = mapPanes.get(this.currentType);

        return typePanes.get(Hunter.State.randomGenerator.nextInt(typePanes.size()));
    }

    /**
     * Update the state of the map, should be called each time the main render loop is called
     *
     * @param delta delta time of the render loop
     * @param cameraPos current camera position
     */
    public void update(float delta, Vector3 cameraPos) {
        if (cameraPos.x - Config.PANE_SIZE_PX * 2 > offset.x) {
            offset.x += Config.PANE_SIZE_PX;

            if (offset.x > (currentType.ordinal() + 1) * Config.PANES_PER_TYPE * Config.PANE_SIZE_PX) {
                if (currentType.ordinal() < MapType.values().length - 1) {
                    currentType = MapType.values()[(currentType.ordinal() + 1)];
                }
            }

            offset.y += (panes.get(0).getEndOffset() - panes.get(1).getStartOffset());

            panes.remove(0);
            panes.add(getRandomPane());
        }
    }

    /**
     * Draw each pane one by one to the given sprite batch
     * @param batch SpriteBatch where you want to draw the current list of map panes
     */
    @Override
    public void draw(SpriteBatch batch) {
        float xOffset, yOffset;
        for (int i = 0; i < panes.size(); i++) {
            xOffset = i * Config.PANE_SIZE_PX + offset.x;
            yOffset = getPaneOffset(i);
            batch.draw(panes.get(i).getBgRendered(), xOffset, yOffset);
            batch.draw(panes.get(i).getFgRendered(), xOffset, yOffset);
        }
    }

    /**
     * Pane y offset relative to the main map offset
     * @param paneIndex in the currently loaded set of panes
     */
    public int getPaneOffset(int paneIndex) {
        int yOffset = (int) this.offset.y;

        if (paneIndex == 0) {
            return yOffset;
        } else if (paneIndex > 0 && paneIndex <= paneCount) {
            for (int i = 1; i <= paneIndex; i++) {
                yOffset += (panes.get(i - 1).getEndOffset() - panes.get(i).getStartOffset());
            }

            return yOffset;
        } else {
            System.out.println("DEBUG: Should never get to here");
            return 0;
        }
    }

    /**
     * Get the pane at a given X offset
     *
     * @param x x offset to check
     * @return pane index for the given x offset, -1 if the coordinate does not correspond to a valid pane
     */
    public int getPane(float x) {
        int pane = (int) Math.floor((x - offset.x) / Config.PANE_SIZE_PX);
        if (pane >= 0 && pane < paneCount) {
            return pane;
        }
        return -1;
    }

    /**
     * Find the column that corresponds to a given x coordinate
     *
     * @param x in pixel coordinates
     * @return corresponding column number
     */
    public int getColumn(float x) {
        int tile = (int) ((x - offset.x) - getPane(x) * Config.PANE_SIZE_PX) / Config.TILE_SIZE;
        if (tile >= 0 && tile < Config.PANE_SIZE) {
            return tile;
        }
        return -1;
    }

    /**
     * Get the collision tile at a given world-space coordinate
     *
     * @param x x coordinate
     * @param y y coordinate
     * @return collision tile type, -1 if the given location is out of bounds
     */
    public int getCollisionTileAt(float x, float y) {
        int tileOffsetY;
        int pane = getPane(x);

        int tileX, tileY;

        if (pane != -1) {
            tileOffsetY = (int) (y - getPaneOffset(pane));
        } else {
            return -1;
        }

        tileX = getColumn(x);
        tileY = tileOffsetY / Config.TILE_SIZE;

        if (tileX == -1 || tileY < 0 || tileY >= Config.PANE_SIZE) {
            return -1;
        }

        return panes.get(pane).getCollisionTile(tileX, tileY);
    }

    /**
     * Get the y position of the pixel above the top collision tile
     *
     * @param x the x position of the column to check
     * @return -1 if there is no top collision tile for the given x coordinate
     */
    public int getColumnTop(float x) {
        int tile = getColumn(x);
        int pane = getPane(x);

        if (pane == -1) {
            return -1;
        }

        for (int i = Config.PANE_SIZE - 1; i >= 0; i--) {
            int collisionType = panes.get(pane).getCollisionTile(tile, i);
            if (collisionType == -1) {
                return -1;
            } else {
                int tileX = (int) (x % Config.TILE_SIZE);
                switch (collisionType) {
                    case 0:
                        //Empty tile, do nothing
                        break;
                    case 1:
                        //Solid tile
                        return Config.TILE_SIZE * (i + 1) + getPaneOffset(pane);
                    case 2:
                        // /_| 45 degree upward slope
                        return Config.TILE_SIZE * i + getPaneOffset(pane) + tileX;
                    case 3:
                        // |_\ 45 degree downward slope
                        return Config.TILE_SIZE * (i + 1) + getPaneOffset(pane) - tileX;
                    case 4:
                        // Slope from 0 to (Config.TILE_SIZE / 2)
                        return Config.TILE_SIZE * i + getPaneOffset(pane) + (tileX / 2);
                    case 5:
                        // Slope from (Config.TILE_SIZE / 2) to Config.TILE_SIZE
                        return Config.TILE_SIZE * i + getPaneOffset(pane) + (tileX / 2 + Config.TILE_SIZE / 2);
                    case 6:
                        // Slope from (Config.TILE_SIZE / 2) to 0
                        return Config.TILE_SIZE * (i + 1) + getPaneOffset(pane) - (tileX / 2);
                    case 7:
                        // Slope from Config.TILE_SIZE to (Config.TILE_SIZE / 2)
                        return Config.TILE_SIZE * (i + 1) + getPaneOffset(pane) - (tileX / 2 + (Config.TILE_SIZE / 2));
                    default:
                        return -1;
                }
            }
        }
        return -1;
    }

    public void dispose() {
        for (MapPane p : panes) {
            p.dispose();
        }
    }
}