package deco2800.arcade.pacman;

import deco2800.arcade.pacman.Ghost.GhostName;

/**
 * Model of the Pacman game
 * @author Nicholas
 *
 */
public class PacModel {
 
	private final int SCREEN_HEIGHT;
	private final int SCREEN_WIDTH;
	
	private PacChar player;
	private Ghost blinky;
	private Ghost pinky;
	private String mapName; // name of level map
	private GameMap gameMap;
	
	public PacModel(int SCREENWIDTH, int SCREENHEIGHT, int NUM_GHOSTS) {
		this.SCREEN_HEIGHT = SCREENHEIGHT;
		this.SCREEN_WIDTH = SCREENWIDTH;
		// level map file
		mapName = "levelMap.txt";
		//initialise gamemap
		gameMap = new GameMap(SCREEN_WIDTH, SCREEN_HEIGHT, NUM_GHOSTS);			
		gameMap.createTiles(gameMap.readMap(mapName));
		//initialise pacman
		player = new PacChar(gameMap);
		blinky = new Ghost(gameMap, GhostName.BLINKY, player);
		pinky = new Ghost(gameMap, GhostName.PINKY, player);
	}

	public void prepareDraw() {
		player.prepareDraw();
		blinky.prepareDraw();
		pinky.prepareDraw();
	}
		
	//Getters added for testing
	public String getMapName() {
		return mapName;
	}

	public GameMap getGameMap() {
		return gameMap;
	}

	public PacChar getPlayer() {
		return player;
	}

	public Ghost getBlinky() {
		return blinky;
	}

	public Ghost getPinky() {
		return pinky;
	}
	
	public int getSCREENHEIGHT() {
		return SCREEN_HEIGHT;
	}

	public int getSCREENWIDTH() {
		return SCREEN_WIDTH;
	}
	
}
