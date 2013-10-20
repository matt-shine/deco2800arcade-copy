package deco2800.arcade.towerdefence.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

import deco2800.arcade.towerdefence.view.GameScreen;

/**
 * The interface for the creation of a player ship. For single player games one
 * ship per player only. For cooperative play one ship only and n(starting with
 * 2) players. For head-to-head m ships, m players(starting with 2).
 * 
 * @author hadronn
 * 
 */
public class Ship {
	/**
	 * To be invoked while instantiating or destroying an object to keep track
	 * of any required state such as numbers on the grid.
	 * 
	 * TODO decide what game state will be kept, calculated and if calculated
	 * when. This may need to be split into further methods Here we need to
	 * consider: What actions are pushed to replay, how exact do replays need to
	 * be? What stats do we need to keep track of for game mechanic reasons?
	 * What stats do we need to keep track of for achievements? What stats do we
	 * need to keep track of for accolades? What stats do we need to keep track
	 * of for save/load?
	 * 
	 * We just reference the ship (game world) whenever we want to update what's
	 * happened within it.
	 * 
	 * We should create fields in the Ship class for each state we'd like to
	 * keep track of and increment/decrement as the game plays.
	 */

	// Fields
	// The grid used by this ship
	private Grid grid;
	// The GameScreen that is using this ship
	private GameScreen gameScreen;
	// The resources of the ship
	private int resources;
	// The Ship (player) score
	// Perhaps a formula of units killed, wave and ship health?
	private int score;
	// The current wave of enemies
	private int wave;

	// Constructor
	/**
	 * THe constructor for the ship. Sets the game as well as creating the grid.
	 * 
	 * @param game
	 *            The GameScreen which this ship belongs to.
	 * @Param targetPosition A vector representing the target (portal)
	 */
	public Ship(GameScreen gameScreen, Vector2 targetPosition) {
		this.gameScreen = gameScreen;
		this.grid = new Grid(Gdx.graphics.getHeight(), Gdx.graphics.getWidth(),
				"shipGrid", 25, this, targetPosition);
	}

	// Getters
	/**
	 * Grid of the ship.
	 * 
	 * @return grid
	 */
	public Grid grid() {
		return grid;
	}
	
	/**
	 * GameScreen used by the ship.
	 * 
	 * @return gameScreen
	 */
	public GameScreen gameScreen() {
		return gameScreen;
	}
	
	/**
	 * Current resources of the ship. May need to be split into types later.
	 * 
	 * @return resources
	 */
	public int resources() {
		return resources;
	}

	/**
	 * Current score for the player.
	 * 
	 * @return score
	 */
	public int score() {
		return score;
	}

	/**
	 * Current wave the player is on.
	 * 
	 * @return wave
	 */
	public int wave() {
		return wave;
	}

	// Setters
	/**
	 * Set the Grid of the ship.
	 * 
	 * @param grid
	 */
	public void grid(Grid grid) {
		this.grid = grid;
	}
	
	/**
	 * Set the GameScreen used by the ship.
	 * 
	 * @param gameScreen
	 */
	public void gameScreen(GameScreen gameScreen) {
		this.gameScreen = gameScreen;
	}
	
	/**
	 * Set the resources of the ship.
	 * 
	 * @param number
	 */
	public void resources(int number) {
		this.resources = number;
	}

	/**
	 * Set the score of the ship (player)
	 * 
	 * @param number
	 */
	public void score(int number) {
		this.score = number;
	}

	/**
	 * Set wave number, useful for save/load.
	 * 
	 * @param number
	 */
	public void wave(int number) {
		this.wave = number;
	}

	// Methods
	/**
	 * Method for updating the gameState
	 * 
	 * @param gameState
	 * @param change
	 */
	public void updateGameState(Object gameState, int change) {
		// TODO implement
	}

	/**
	 * Give the player more score.
	 */
	public void addScore() {
	}

	/**
	 * Give the ship more resources
	 */
	public void addResources() {
	}

	/**
	 * Increment the wave, keep to units of 1 don't be cute.
	 */
	public void nextWave() {

	}

	/**
	 * Try and place an object in the grid.
	 * 
	 * @param object
	 *            The GridObject to be placed. The placement position is
	 *            determined by the object's position vector.
	 * @return A boolean representing whether the placement was successful.
	 */
	public boolean placeObject(GridObject object) {
		return grid.buildObject(object);
	}
}
