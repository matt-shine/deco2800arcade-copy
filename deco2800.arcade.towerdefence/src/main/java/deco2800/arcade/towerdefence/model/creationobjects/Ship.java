package deco2800.arcade.towerdefence.model.creationobjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

import deco2800.arcade.towerdefence.model.Grid;
import deco2800.arcade.towerdefence.model.GridObject;
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

	// The grid used by this ship
	private Grid grid;
	// The GameScreen that is using this ship
	private GameScreen game;

	/**
	 * THe constructor for the ship. Sets the game as well as creating the grid.
	 * 
	 * @param game
	 *            The GameScreen which this ship belongs to.
	 * @Param targetPosition A vector representing the target (portal)
	 */
	public Ship(GameScreen game, Vector2 targetPosition) {
		this.game = game;
		this.grid = new Grid(Gdx.graphics.getHeight(), Gdx.graphics.getWidth(),
				"shipGrid", 25, this, targetPosition);
	}

	public void updateGameState(Object gameState, int change) {
	}

	/**
	 * Current resources of the ship. May need to be split into types later.
	 * 
	 * @return
	 */
	public int resources() {
		return 0;
	}

	/**
	 * Current score for the player. Perhaps a formula of units killed, wave and
	 * ship health?
	 * 
	 * @return
	 */
	public int score() {
		return 0;
	}

	/**
	 * Current wave the player is on.
	 * 
	 * @return
	 */
	public int wave() {
		return 0;
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
	 * Jump forward to wave n, useful for save/load.
	 * 
	 * @param n
	 */
	public void setWave(int n) {
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
