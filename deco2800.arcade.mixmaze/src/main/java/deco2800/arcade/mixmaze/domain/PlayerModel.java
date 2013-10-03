package deco2800.arcade.mixmaze.domain;

import deco2800.arcade.mixmaze.PlayerNetworkView;
import deco2800.arcade.mixmaze.domain.view.IBrickModel;
import deco2800.arcade.mixmaze.domain.view.IPlayerModel;
import deco2800.arcade.mixmaze.domain.view.IPickModel;
import deco2800.arcade.mixmaze.domain.view.ITNTModel;
import deco2800.arcade.mixmaze.domain.view.ITileModel;
import java.util.ArrayList;

import static deco2800.arcade.mixmaze.domain.Direction.*;
import static deco2800.arcade.mixmaze.domain.view.IItemModel.ItemType.*;

/**
 * Player model represents a player.
 */
public class PlayerModel implements IPlayerModel {

	// Player data
	private int id;
	private int x;
	private int y;
	private int direction;
	private Action action;
	private long lastMoved;
	private long lastAction;
	private int score;
	private ArrayList<PlayerNetworkView> viewers;

	// Item data
	private BrickModel brick;
	private PickModel pick;
	private TNTModel tnt;

	/**
	 * Constructs a new Player with the specified <code>id</code>
	 *
	 * @param id the id of this player
	 */
	public PlayerModel(int id) {
		this.id = id;
		action = Action.USE_BRICK;
		brick = new BrickModel(4);
		score = 0;
		viewers = new ArrayList<PlayerNetworkView>();
	}

	@Override
	public void addViewer(PlayerNetworkView v) {
		viewers.add(v);
		v.updateScore(score);
		v.updateDirection(direction);
		v.updatePosition(x, y);
		v.updateAction(action);
		v.updateBrick(brick.getAmount());
		v.updatePick(pick != null);
		v.updateTnt(tnt != null);
	}

	/**
	 * Increments the score of this player by 1.
	 */
	void incrementScore() {
		changeScore(1);
	}

	/**
	 * Decrements the score of this player by 1.
	 */
	void decrementScore() {
		changeScore(-1);
	}

	private void changeScore(int delta) {
		score += delta;
		for (PlayerNetworkView v : viewers)
			v.updateScore(score);
	}

	/**
	 * Returns the id of this player
	 * @return the <code>id</> of this player
	 */
	public int getId() {
		return id;
	}

	/**
	 * Returns the x-coordinates for this player. The x-coordinate's orgin is at
	 * the top left.
	 *
	 * @return the x-coordinate of this player
	 */
	public int getX() {
		return x;
	}

	/**
	 * Returns the next x-coordinate. Note the returned coordinate
	 * might be out of board range.
	 *
	 * @return the next x-coordinate in relative to the direction facing.
	 */
	public int getNextX() {
		if (!isXDirection(direction)) {
			return x;
		}
		return isPositiveDirection(direction) ? (x + 1) : (x - 1);
	}

	/**
	 * Sets the x-coordinates for this player. The x-coordinate's orgin is at
	 * the top left.
	 *
	 * @param x the x-coordinate of this player
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * Returns the y-coordinates for this player. The y-coordinate's orgin is at
	 * the top left.
	 *
	 * @return the y-coordinate of this player
	 */
	public int getY() {
		return y;
	}

	public int getNextY() {
		if (!isYDirection(direction)) {
			return y;
		}
		return isPositiveDirection(direction) ? (y + 1) : (y - 1);
	}

	/**
	 * Sets the y-coordinates for this player. The y-coordinate's orgin is at
	 * the top left.
	 *
	 * @param y the y-coordinate of this player
	 */
	public void setY(int y) {
		this.y = y;
	}

	/** Checks if this player can move. The player can only make a move after 0.3
	 * seconds after the previous move
	 *
	 * @return true if the player can move, false otherwise
	 */
	private boolean canMove() {
		return (System.currentTimeMillis() - lastMoved) >= (0.3 * 1000);
	}

	/**
	 * Moves the player by one <code>tile</code> in the facing direction.
	 */
	public void move() {
		if (canMove()) {
			x = getNextX();
			y = getNextY();
			for (PlayerNetworkView v : viewers)
				v.updatePosition(x, y);
			lastMoved = System.currentTimeMillis();
		}
	}

	/**
	 * Returns the <code>direction</> of this player.
	 *
	 * @return the facing <code>direction</> of this player
	 */
	public int getDirection() {
		return direction;
	}

	/**
	 * Change this player's direction by the specified <code>direction</>
	 *
	 * @param direction an integer representation of the direction
	 */
	public void setDirection(int direction) {
		/*
		if (!isDirection(direction)) {
			throw NOT_A_DIRECTION;
		}
		*/
		this.direction = direction;
		for (PlayerNetworkView v : viewers)
			v.updateDirection(direction);
	}

	public IBrickModel getBrick() {
		return brick;
	}

	public IPickModel getPick() {
		return pick;
	}

	public ITNTModel getTNT() {
		return tnt;
	}

	/**
	 * Tries to pick up the specified <code>item</code>.
	 * <code>item</code> must not be null.
	 *
	 * @param item	the item
	 * @return <code>true</code> if the item is fully picked up,
	 * e.g. pile of bricks, <code>false</code> otherwise.
	 */
	boolean pickupItem(ItemModel item) {
		boolean res = false;

		if (item.getType() == BRICK) {
			BrickModel brick = (BrickModel) item;

			this.brick.mergeBricks(brick);
			updateBrick(this.brick.getAmount());
			res = (brick.getAmount() == 0);
		} else if(item.getType() == PICK && this.pick == null) {
			pick = (PickModel) item;
			updatePick(true);
			res = true;
		} else if(item.getType() == TNT && this.tnt == null) {
			tnt = (TNTModel) item;
			updateTnt(true);
			res = true;
		}
		return res;
	}

	/**
	 * Returns the active action of this player.
	 *
	 * @return one of USE_BRICK, USE_PICK, and USE_TNT
	 */
	public Action getAction() {
		return action;
	}

	/**
	 * Switches to the next action, in this order
	 * USE_BRICK - USE_PICK - USE_TNT - USE_BRICK.
	 * An action is skipped if this player does not have
	 * the associated item.
	 */
	public void switchAction() {
		IPlayerModel.Action old = action;

		action = action.getNextAction(pick != null,
				tnt != null);
		if (action != old)
			updateAction(action);
	}

	/**Checks of this player can perform a action.
	 *
	 * @return true if this player can use any action, false otherwise
	 */
	public boolean canUseAction() {
		return (System.currentTimeMillis() - lastAction) >= (0.5 * 1000);
	}

	/**
	 * Uses the active action of this player.
	 *
	 * @param tile the tile where this player is
	 */
	public boolean useAction(ITileModel itile) {
		TileModel tile = (TileModel) itile;

		if(canUseAction()) {
			boolean used = false;
			if(action == Action.USE_BRICK && brick.getAmount() > 0) {
				WallModel wall = (WallModel) tile.getWall(direction);
				if(!wall.isBuilt()) {
					brick.removeOne();
					wall.build(this);
					used = true;
					updateBrick(brick.getAmount());
				}
			} else if(action == Action.USE_PICK && pick != null) {
				WallModel wall = (WallModel) tile.getWall(direction);
				if(wall.isBuilt()) {
					pick = null;
					wall.destroy(this);
					updatePick(false);
					used = true;
				}
			} else if(action == Action.USE_TNT && tnt != null) {
				tnt = null;
				for(int dir = 0; dir < 4; ++dir) {
					WallModel wall = (WallModel) tile.getWall(dir);
					if(wall.isBuilt()) {
						wall.destroy(this);
					}
				}
				updateTnt(false);
				used = true;
			}
			lastAction = used ? System.currentTimeMillis() : lastAction;
			return used;
		}
		return false;
	}

	private void updateBrick(int amount) {
		for (PlayerNetworkView v : viewers)
			v.updateBrick(amount);
	}

	private void updatePick(boolean hasPick) {
		for (PlayerNetworkView v : viewers)
			v.updatePick(hasPick);
	}

	private void updateTnt(boolean hasTnt) {
		for (PlayerNetworkView v : viewers)
			v.updateTnt(hasTnt);
	}

	private void updateAction(IPlayerModel.Action action) {
		for (PlayerNetworkView v : viewers)
			v.updateAction(action);
	}

	@Override
	public String toString() {
		return "<Player " + id + ">";
	}

}
