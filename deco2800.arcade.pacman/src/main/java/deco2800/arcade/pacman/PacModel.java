package deco2800.arcade.pacman;

import deco2800.arcade.pacman.Ghost.GhostName;

/**
 * Model of the Pacman game
 * @author Nicholas
 *
 */
public class PacModel {

	private enum GameState {
		READY,
		RUNNING,
		GAMEOVER
	}
 
	public final int SCREENHEIGHT;
	public final int SCREENWIDTH;
	
	private GameState gameState;
	private PacChar player;
	private Ghost blinky;
	private Ghost pinky;
	private String mapName; // name of level map
	private GameMap gameMap;
	
	public PacModel(int SCREENHEIGHT, int SCREENWIDTH) {
		this.SCREENHEIGHT = SCREENHEIGHT;
		this.SCREENWIDTH = SCREENWIDTH;
		// level map file
		mapName = "levelMap.txt";
		//initialise gamemap
		//TODO move offset to PacView
		gameMap = new GameMap(SCREENHEIGHT, SCREENWIDTH);			
		gameMap.createTiles(gameMap.readMap(mapName));
		//initialise pacman
		player = new PacChar(gameMap);
		blinky = new Ghost(gameMap, GhostName.BLINKY, player);
		pinky = new Ghost(gameMap, GhostName.PINKY, player);
		//Initialise game state
		gameState = GameState.READY;	
	}

	public void prepareDraw() {
		player.prepareDraw();
		blinky.prepareDraw();
		pinky.prepareDraw();
	}
	
	
	public GameState getGameState() {
		return gameState;
	}

	public void setGameState(GameState gameState) {
		this.gameState = gameState;
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
		return SCREENHEIGHT;
	}

	public int getSCREENWIDTH() {
		return SCREENWIDTH;
	}
	
}
