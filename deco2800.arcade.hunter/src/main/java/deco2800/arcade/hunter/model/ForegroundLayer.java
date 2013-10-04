package deco2800.arcade.hunter.model;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import com.badlogic.gdx.math.Vector3;
import deco2800.arcade.hunter.Hunter.Config;
import deco2800.arcade.hunter.model.MapPane.MapType;

public class ForegroundLayer extends Map {
	private ArrayList<MapPane> panes;
	private int paneCount; //How many panes should we keep loaded at a time, should be the number that can fit on the screen plus one
    private MapType currentType = MapType.GRASS;

    //Key is one of the MapType enums, value is a list of pane objects which can be instantiated
    private HashMap<MapType, ArrayList<MapPane>> mapPanes = new HashMap<MapType, ArrayList<MapPane>>();
	
	/**
     * @param speedModifier How fast the pane should move relative to the camera. 1 is the same speed as the camera.
	 * @param paneCount map panes to keep loaded at a time
	 */
	public ForegroundLayer(float speedModifier, int paneCount) {
		super(speedModifier);
		this.paneCount = paneCount;

        loadPanes(Gdx.files.internal("maps/maplist.txt"));
		
		panes = new ArrayList<MapPane>(paneCount);
		while (panes.size() < this.paneCount) {
			panes.add(getRandomPane());
		}
	}
	
	public ArrayList<MapPane> getPanes() {
		return new ArrayList<MapPane>(panes);
	}

    /*
     * Load a set of panes from a given file into the mapPanes HashMap above
     * file should have the format:
     * [MapType]
     * filename.map
     * filename2.map
     * [MapType2]
     * filename3.map
     */
    private void loadPanes(FileHandle listFile) {
        BufferedReader br;
        String line, tagText;
        MapType type = null;

        try {
            br = new BufferedReader(new InputStreamReader(listFile.read()));

            while (null != (line = br.readLine())) {
                if (line.matches("\\[\\w+\\]")) {
                    //Line is a tag which corresponds to a set of maps for a new MapType
                    tagText = line.replace("[", "").replace("]", "");
                    try {
                        type = MapType.valueOf(tagText);

                    } catch (IllegalArgumentException e) {
                        e.printStackTrace(); //Todo, more detail here?
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
            e.printStackTrace();  //Todo, more detail here?
        } catch (IOException e) {
            e.printStackTrace();  //Todo, more detail here?
        }
    }
	
	/**
	 * Get a random map pane of the requested type
	 * @return new MapPane
	 */
	private MapPane getRandomPane() {
        ArrayList<MapPane> typePanes = mapPanes.get(this.currentType);

        return typePanes.get(Config.randomGenerator.nextInt(typePanes.size()));
	}
	
	/**
	 * Update the state of the map, should be called each time the main render loop is called
	 * @param delta delta time of the render loop
	 * @param cameraPos current camera position
	 */
	public void update(float delta, Vector3 cameraPos) {
		if (cameraPos.x - Config.PANE_SIZE_PX * paneCount > offset.x) {
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

	@Override
	public void draw(SpriteBatch batch) {
		// TODO Auto-generated method stub
		
		float yOffset = this.offset.y;
		//REPLACE TODO with getPaneOffset()
		for (int i = 0; i < panes.size(); i++) {
			if (i != 0) {
				yOffset += (panes.get(i-1).getEndOffset() - panes.get(i).getStartOffset());
			}
			
			batch.draw(panes.get(i).getRendered(), i * Config.PANE_SIZE_PX + offset.x, yOffset);
		}		
	}
	
	/*
	 * Pane y offset relative to the main map offset
	 */
	private int getPaneOffset(int paneIndex) {
		int yOffset = (int) this.offset.y;
		
		if (paneIndex == 0) {
			return yOffset;
		} else if (paneIndex > 0 && paneIndex <= paneCount) {
			for (int i = 1; i <= paneIndex; i++) {
				yOffset += (panes.get(i-1).getEndOffset() - panes.get(i).getStartOffset());
			}
			
			return yOffset;
		} else {
			System.out.println("DEBUG: Should never get to here");
			return 0;
		}
	}

    /**
     * Get the pane at a given X offset
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
	 * @param x x coordinate
	 * @param y y coordinate
	 * @return collision tile type, or -1 if the given location is out of bounds
	 */
	public int getCollisionTileAt(float x, float y) {
		int tileOffsetY;
		int pane = getPane(x);
		
		int tileX, tileY;
		
		if (pane != -1) {
			tileOffsetY = (int) (y - getPaneOffset(pane));
		} else {
			//Player is out of bounds
			return -1;
		}
		
		tileX = getColumn(x);
		tileY = tileOffsetY / Config.TILE_SIZE;
		
		if (tileX == -1 || tileY < 0 || tileY >= Config.PANE_SIZE) {
			return -2;
		}

		return panes.get(pane).getCollisionTile(tileX, tileY);
	}

    /**
     * Get the y position of the pixel above the top collision tile
     * @param x the x position of the column to check
     * @return -1 if there is no top collision tile
     */
    public int getColumnTop(float x) {
        int tile = getColumn(x);
        int pane = getPane(x);

        for (int i = Config.PANE_SIZE - 1; i >= 0; i--) {
            int collisionType = panes.get(pane).getCollisionTile(tile, i);
            if (collisionType == -1) {
                return -1;
            } else if (collisionType != 0) {
                return getPaneOffset(pane) + (i+1) * Config.TILE_SIZE;
            }
        }
        return -1;
    }
}
