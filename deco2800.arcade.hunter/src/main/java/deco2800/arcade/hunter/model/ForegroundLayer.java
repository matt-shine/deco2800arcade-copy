package deco2800.arcade.hunter.model;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

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

        return typePanes.get((int) Math.round(Math.random() * (typePanes.size() - 1                                                                                 )));
	}
	
	/**
	 * Update the state of the map, should be called each time the main render loop is called
	 * @param delta delta time of the render loop
	 * @param cameraX current camera x position
	 */
	public void update(float delta, float cameraX) {
		if (cameraX - Config.PANE_SIZE_PX * paneCount > offset.x) {
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
	 * Get the collision tile at a given world-space coordinate
	 * @param x x coordinate
	 * @param y y coordinate
	 * @return collision tile type, or -1 if the given location is out of bounds
	 */
	public int getCollisionTileAt(float x, float y) {
		int tileOffsetX = (int) (x - offset.x);
		int tileOffsetY;
		int pane = (int) Math.floor(tileOffsetX / Config.PANE_SIZE_PX);
		
		int tileX, tileY;
		
		if (pane >=0 && pane < paneCount) {
			tileOffsetY = (int) (y - getPaneOffset(pane));
		} else {
			//Player is out of bounds
			return -1;
		}
		
		tileX = (tileOffsetX - pane * Config.PANE_SIZE_PX) / Config.TILE_SIZE;
		tileY = tileOffsetY / Config.TILE_SIZE;
		
		if (tileX < 0 || tileX >= Config.PANE_SIZE ||
				tileY < 0 || tileY >= Config.PANE_SIZE) {
			return -2;
		}

		return panes.get(pane).getCollisionTile(tileX, tileY);
	}
}
