package deco2800.arcade.pacman;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

import org.lwjgl.util.Point;

import com.badlogic.gdx.Gdx;

/**
 * The map of the pacman game, containing the list of tiles and which tiles 
 * are doors to the ghost pen
 */
public class GameMap {

	private boolean vsym;
	private Tile[][] grid; //game map
	private List<WallTile> ghostDoors; //list of ghost doors for ghosts to access
	private Tile pacStart; //the right starting tile for pacman (appears on two tiles)
	private Tile[] ghostStarts; //starting positions for ghosts
	private Tile blinkyStart;
	private Tile fruitRight; // the right tile that fruit appears on
	private int dotsEaten;
	private int ghostsEaten;
	private boolean energized;
	private int hOffset;
	private int vOffset;
	private final int tileSideLength; // length of side of square- should be
	// same for all tiles in a GameMap
	public final int SCREEN_HEIGHT;
	public final int SCREEN_WIDTH;
	private final int NUM_BUFFER_TILES; //the number of buffer tiles around the edge of the map on each side
	private List<Tile> afterTeleports;
	
	public GameMap(int SCREEN_WIDTH, int SCREEN_HEIGHT, int numGhosts) {
		vsym = false;
		ghostDoors = new ArrayList<WallTile>();		
		this.SCREEN_HEIGHT = SCREEN_HEIGHT;
		this.SCREEN_WIDTH = SCREEN_WIDTH;
		ghostStarts = new Tile[numGhosts];
		tileSideLength = 16;
		NUM_BUFFER_TILES = 4;
		afterTeleports = new ArrayList<Tile>();
	}
	

	/**
	 * Reads a file and converts it into a character array to generate tiles from
	 */
	public ArrayList<char[]> readMap(String fileName) {
		String contents = Gdx.files.internal(fileName).readString();
		//resultArray contains a number of char arrays equal to the number of lines
		// each array contains all the characters from that line
		ArrayList<char[]> resultArray = new ArrayList<char[]>();
		String[] lineArray = contents.split(System.getProperty("line.separator"));
		String line = lineArray[0];
		if (line.contains("VSYM")) {
			vsym = true;
		}
		for (int i = 1; i < lineArray.length; i++) {
			line = lineArray[i];			
			if (vsym) {
				line = useVSymmetry(line); 
			}
			resultArray.add(line.toCharArray());
		}
		//reverse as libgdx draws from bottom left
		Collections.reverse(resultArray);
		return resultArray;
	}
	
	/**
	 * Takes a string from a vertically symmetrical map file and uses that 
	 * property to generate the other half of the map, replacing tiles with 
	 * their reflection when appropriate. 
	 * @return: the string containing the whole of that line to make tiles from
	 */
	private String useVSymmetry(String line) {
		StringBuffer reverse = new StringBuffer(line).reverse();
		for (int i = 0; i < reverse.length(); i++) {
			char letter = reverse.charAt(i);
			String replacer = null;
			switch(letter) {
			case 'A': replacer = "C"; break;
			case 'C': replacer = "A"; break;
			case 'D': replacer = "E"; break;
			case 'E': replacer = "D"; break;
			case 'F': replacer = "H"; break;
			case 'H': replacer = "F"; break;
			case 'a': replacer = "c"; break;
			case 'c': replacer = "a"; break;
			case 'd': replacer = "e"; break;
			case 'e': replacer = "d"; break;
			case 'f': replacer = "h"; break;
			case 'h': replacer = "f"; break;
			case '1': replacer = "3"; break;
			case '3': replacer = "1"; break;
			case '4': replacer = "5"; break;
			case '5': replacer = "4"; break;
			case '6': replacer = "8"; break;
			case '8': replacer = "6"; break;
			case '9': replacer = "0"; break;
			case '0': replacer = "9"; break;
			case 'R': replacer = "S"; break;
			case 'S': replacer = "R"; break;
			case 'X': replacer = "Z"; break;
			case 'Z': replacer = "X"; break;
			case 'W': replacer = "Y"; break;
			case 'Y': replacer = "W"; break;
			case 'x': replacer = "z"; break;
			case 'z': replacer = "x"; break;
			case 'w': replacer = "y"; break;
			case 'y': replacer = "w"; break;
			case 'J': replacer = "L"; break;
			case 'L': replacer = "J"; break;
			case 'K': replacer = "M"; break;
			case 'M': replacer = "K"; break;
			// r is left tile fruit starts on
			// s is left tile pacman starts on
			case 'r': replacer = " "; break;
			case 's': replacer = " "; break;
			// !, @, #, $ are the 4 ghost starting positions
			case '!': replacer = "@"; break;
			case '#': replacer = "$"; break;
			case '@': replacer = "!"; break;
			case '$': replacer = "#"; break;
			}
			if (replacer !=  null) {
				reverse.replace(i, i+1, replacer);
			}
		}
		return line + reverse.toString();
	}
	
	/**
	 * Generate tiles as specified by the character ArrayList
	 */
	public void createTiles(ArrayList<char[]> map) {
		//initialise size of map grid
		int mapSize = map.size();
		int lineSize = map.get(0).length;
		int ySize = mapSize + NUM_BUFFER_TILES*2; // number of lines + buffer tiles
		int xSize = lineSize + NUM_BUFFER_TILES*2; //length of each line + buffer tiles
		hOffset = (SCREEN_WIDTH - xSize * tileSideLength) /2;
		vOffset = (SCREEN_HEIGHT - ySize * tileSideLength) /2;
		grid = new Tile[xSize][ySize];
		//set up teleport target making- currently supports any even number 
		//of teleporters, each targeting the next one which appears in the list
		Tile target = null;
		Tile square;
		List<Point> afterTeleportsSetup = new ArrayList<Point>();
		//create tiles
		for (int lineNum = 0; lineNum < map.size(); lineNum++) {
			char[] line = map.get(lineNum);
			for (int i = 0; i < line.length; i++) {
				char type = line[i];
				if (type == 'p' || type == 'P') {
					square = new DotTile(this, type);
				} else if (type == 'T') {
					square = new TeleportTile(this);
					//set up teleport targeting between two most recently
					//made teleporttiles
					if (target != null) {
						((TeleportTile) square).setTarget(target);
						((TeleportTile) target).setTarget(square);
						target = null;
					} else {
						target = square;
					}
				} else if (type == ' ' || type == 'r' || type == 's' 
					|| type == '!' || type == '@' || type == '#' || type == '$'){
					square = new Tile(this);
					switch(type) {
					case 'r': fruitRight = square; break;
					case 's': pacStart = square; break;
					case '!': ghostStarts[0] = square; break;
					case '@': ghostStarts[1] = square; break;
					case '#': ghostStarts[2] = square; break;
					case '$': ghostStarts[3] = square; break;
 					}
 				} else {
					square = new WallTile(this, type);
					// if it's a ghost door, add it to the list
					if (type == 'Q') {
						ghostDoors.add((WallTile) square);
					}
 				}
				grid[i+NUM_BUFFER_TILES][lineNum+NUM_BUFFER_TILES] = square;
				if (square.getClass() == TeleportTile.class) {
					if (afterTeleportsSetup.size() % 2 == 0) {						
						afterTeleportsSetup.add(new Point(i+NUM_BUFFER_TILES - 1,lineNum+NUM_BUFFER_TILES));
					} else {
						afterTeleportsSetup.add(new Point(i+NUM_BUFFER_TILES + 1,lineNum+NUM_BUFFER_TILES));
					}
				}
			}
		}
		//sets up buffer tiles
		for (int x = 0; x < xSize; x++) {
			for (int y = 0; y < ySize; y++) {
				if (grid[x][y] == null) {
					grid[x][y] = new Tile(this);
				}
			}
		}
		for (Point p: afterTeleportsSetup) {
			afterTeleports.add(grid[p.getX()][p.getY()]);
		}
	}
	
	/**
	 * Returns the tile the middle pixel of the mover is currently in
	 * Returns null if the mover is not in a tile (shouldn't happen)
	 */
	public Tile findMoverTile(Mover mover) {
		int x = mover.getMidX();
		int y = mover.getMidY();
		for (int i = NUM_BUFFER_TILES; i < grid.length-NUM_BUFFER_TILES; i++) {
			int tileX = getTileCoords(grid[i][0]).getX();
			if (x > tileX && x <= tileX + tileSideLength) {
				for (int j = NUM_BUFFER_TILES; j < grid[i].length-NUM_BUFFER_TILES; j++) {
					int tileY = getTileCoords(grid[i][j]).getY(); 
					if (y > tileY && y <= tileY + tileSideLength) {
						return grid[i][j];
					}
				}
			}			
		}
		return null;
	}
	
	/**
	 *  Returns the coordinates of the bottom left corner of the tile
	 *  @param the position of the tile in the grid
	 */
	public Point getTileCoords(Tile tile) {
		Point xy = getTilePos(tile);
		return new Point(hOffset + xy.getX()*tileSideLength, vOffset + xy.getY()*tileSideLength);
	} 
	
	/**
	 * Returns the position of the tile in the grid
	 * Returns null if the tile is not in the grid (shouldn't happen)
	 */
	public Point getTilePos(Tile tile) {
		for (int x = 0; x < grid.length; x++) {
			for (int y = 0; y < grid[x].length; y++) {
				if (tile == grid[x][y]) {
					return new Point(x, y);
				}
			}
		}
		return null;
	}

	public Tile[][] getGrid() {
		return grid;
	}


	public int getHOffset() {
		return hOffset;
	}


	public int getVOffset() {
		return vOffset;
	}

	public int getTileSideLength() {
		return tileSideLength;
	}


	public Tile[] getGhostStarts() {
		return ghostStarts;
	}


	public int getDotsEaten() {
		return dotsEaten;
	}


	public void setDotsEaten(int dotsEaten) {
		this.dotsEaten = dotsEaten;
	}


	public boolean isEnergized() {
		return energized;
	}


	public void setEnergized(boolean energized) {
		this.energized = energized;
	}


	public int getGhostsEaten() {
		return ghostsEaten;
	}


	public void setGhostsEaten(int ghostsEaten) {
		this.ghostsEaten = ghostsEaten;
	}
	
	public List<WallTile> getGhostDoors() {
		return ghostDoors;
	}

	public Tile getPacStart() {
		return pacStart;
	}

	public Tile getFruitRight() {
		return fruitRight;
	}

	public List<Tile> getAfterTeleports() {
		return afterTeleports;
	}
}
