package deco2800.arcade.mixmaze.domain;

import java.util.ArrayList;

import deco2800.arcade.mixmaze.Achievements;
import deco2800.arcade.mixmaze.Sounds;
import static deco2800.arcade.mixmaze.domain.Direction.*;
import static deco2800.arcade.mixmaze.domain.ItemModel.Type.*;

/**
 * Player model represents a player.
 */
public class PlayerModel {

	/**
	 * Player action
	 */
	public enum Action {
		USE_BRICK, USE_PICK, USE_TNT;

		/**
		 * Returns the next action, given the status of pick and TNT.
		 * 
		 * @param hasPick
		 *            whether this player has pick or not
		 * @param hasTnt
		 *            whether this player has TNT or not
		 * @return the next action
		 */
		public Action getNextAction(boolean hasPick, boolean hasTnt) {
			Action next = values()[(ordinal() + 1) % values().length];
			if ((next == USE_PICK && !hasPick) || (next == USE_TNT && !hasTnt)) {
				return next.getNextAction(hasPick, hasTnt);
			} else {
				return next;
			}
		}
	}

	/** Player id */
	private int id;

	/** Column number */
	private int x;

	/** Row number */
	private int y;

	/** Facing direction */
	private int direction;

	/** Current action */
	private Action action;

	/** Number of boxes */
	private int score;

	/** The brick on this player */
	private BrickModel brick;

	/** The pick on this player */
	private PickModel pick;

	/** The TNT on this player */
	private TNTModel tnt;

	/** Observers to this player */
	private ArrayList<PlayerModelObserver> observers;

	private long lastMoved;
	private long lastAction;

	/**
	 * Constructor
	 * 
	 * @param id
	 *            the player id
	 */
	PlayerModel(int id) {
		this.id = id;
		action = Action.USE_BRICK;
		brick = new BrickModel(4);
		score = 0;
		observers = new ArrayList<PlayerModelObserver>();
	}

	/**
	 * Adds an observer to this player.
	 * 
	 * @param observer
	 *            the observer
	 */
	public void addViewer(PlayerModelObserver observer) {
		observers.add(observer);
		observer.updateScore(score);
		observer.updateDirection(direction);
		observer.updatePosition(x, y);
		observer.updateAction(action);
		observer.updateBrick(brick.getAmount());
		observer.updatePick(pick != null);
		observer.updateTnt(tnt != null);
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

	/*
	 * Changes the score of this player by delta.
	 */
	private void changeScore(int delta) {
		score += delta;
		for (PlayerModelObserver v : observers) {
			v.updateScore(score);
		}
	}

	/**
	 * Returns the id of this player.
	 * 
	 * @return the <code>id</code> of this player
	 */
	public int getId() {
		return id;
	}

	/**
	 * Returns the x-coordinate of this player. Origin is at top left.
	 * 
	 * @return the x-coordinate of this player
	 */
	int getX() {
		return x;
	}

	/**
	 * Returns the next x-coordinate. Note the returned coordinate might be out
	 * of board range.
	 * 
	 * @return the next x-coordinate in relative to the direction facing.
	 */
	int getNextX() {
		if (isXDirection(direction)) {
			return isPositiveDirection(direction) ? (x + 1) : (x - 1);
		} else {
			return x;
		}
	}

	/**
	 * Sets the x-coordinate of this player. Origin is at top left.
	 * 
	 * @param x
	 *            the x-coordinate of this player
	 */
	void setX(int x) {
		this.x = x;
	}

	/**
	 * Returns the y-coordinate of this player. Origin is at top left.
	 * 
	 * @return the y-coordinate of this player
	 */
	int getY() {
		return y;
	}

	/**
	 * Returns the next y-coordinate. Note the returned coordinate might be out
	 * of board range.
	 * 
	 * @return the next y-coordinate in relative to the direction facing.
	 */
	int getNextY() {
		if (isYDirection(direction)) {
			return isPositiveDirection(direction) ? (y + 1) : (y - 1);
		} else {
			return y;
		}
	}

	/**
	 * Sets the y-coordinate of this player. Origin is at top left.
	 * 
	 * @param y
	 *            the y-coordinate of this player
	 */
	void setY(int y) {
		this.y = y;
	}

	public boolean isAtLocation(int x, int y) {
		return this.x == x && this.y == y;
	}

	/**
	 * Checks if this player can move. The player can only make a move after 300
	 * milliseconds since the previous move.
	 * 
	 * @return true if the player can move, false otherwise
	 */
	private boolean canMove() {
		return ((System.currentTimeMillis() - lastMoved) >= 300);
	}

	/**
	 * Moves the player by one tile in the facing direction.
	 */
	void move() {
		if (canMove()) {
			x = getNextX();
			y = getNextY();
			for (PlayerModelObserver v : observers) {
				v.updatePosition(x, y);
			}
			lastMoved = System.currentTimeMillis();
		}
	}

	/**
	 * Returns the <code>direction</> of this player.
	 * 
	 * @return the facing <code>direction</> of this player
	 */
	int getDirection() {
		return direction;
	}

	/**
	 * Changes this player's direction to the specified <code>direction</code>.
	 * 
	 * @param direction
	 *            the requested direction
	 */
	void setDirection(int direction) {
		if (!isDirection(direction)) {
			throw NOT_A_DIRECTION;
		}
		this.direction = direction;
		for (PlayerModelObserver v : observers) {
			v.updateDirection(direction);
		}
	}

	BrickModel getBrick() {
		return brick;
	}

	PickModel getPick() {
		return pick;
	}

	TNTModel getTNT() {
		return tnt;
	}

	/**
	 * Tries to pick up the specified <code>item</code>. <code>item</code> must
	 * not be null.
	 * 
	 * @param item
	 *            the item
	 * @return <code>true</code> if the item is fully picked up, e.g. pile of
	 *         bricks, <code>false</code> otherwise.
	 */
	boolean pickupItem(ItemModel item) {
		boolean res = false;

		if (item.getType() == BRICK) {
			BrickModel brick = (BrickModel) item;

			this.brick.mergeBricks(brick);
			updateBrick(this.brick.getAmount());
			res = (brick.getAmount() == 0);
		} else if (item.getType() == PICK && this.pick == null) {
			pick = (PickModel) item;
			updatePick(true);
			res = true;
			if(id == 1) Achievements.incrementAchievement(Achievements.AchievementType.BreakingGood);
		} else if (item.getType() == TNT && this.tnt == null) {
			tnt = (TNTModel) item;
			updateTnt(true);
			res = true;
			if(id == 1) Achievements.incrementAchievement(Achievements.AchievementType.TNT);
		}
		return res;
	}

	/**
	 * Returns the active action of this player.
	 * 
	 * @return one of USE_BRICK, USE_PICK, and USE_TNT
	 */
	Action getAction() {
		return action;
	}

	/**
	 * Switches to the next action, in this order USE_BRICK - USE_PICK - USE_TNT
	 * - USE_BRICK. An action is skipped if this player does not have the
	 * associated item.
	 */
	void switchAction() {
		PlayerModel.Action old = action;

		action = action.getNextAction(pick != null, tnt != null);
		if (action != old) {
			updateAction(action);
		}
	}

	/**
	 * Checks if this player can perform a action.
	 * 
	 * @return true if this player can use any action, false otherwise
	 */
	boolean canUseAction() {
		return (System.currentTimeMillis() - lastAction) >= (500);
	}

	/**
	 * Uses the active action of this player.
	 * 
	 * @param tile
	 *            the tile where this player is
	 * @return <code>true</code> if this player used the action,
	 *         <code>false</code> otherwise.
	 */
	boolean useAction(TileModel tile) {
		/* XXX: too many indentation levels */
		if (canUseAction()) {
			boolean used = false;
			if (action == Action.USE_BRICK && brick.getAmount() > 0) {
				WallModel wall = (WallModel) tile.getWall(direction);
				if (!wall.isBuilt() && !wall.isInBox()) {
					brick.removeOne();
					wall.build(this);
					used = true;
					updateBrick(brick.getAmount());
					Sounds.playBuild();
				}
			} else if (action == Action.USE_PICK && pick != null) {
				WallModel wall = (WallModel) tile.getWall(direction);
				if (wall.isBuilt()) {
					pick = null;
					wall.destroy(this);
					updatePick(false);
					used = true;
					Sounds.playDestroy();
					if(id == 1) Achievements.incrementAchievement(Achievements.AchievementType.UsePick);
					switchAction();
				}
			} else if (action == Action.USE_TNT && tnt != null) {
				tnt = null;
				for (int dir = 0; dir < 4; ++dir) {
					WallModel wall = (WallModel) tile.getWall(dir);
					if (wall.isBuilt()) {
						wall.destroy(this);
					}
				}
				updateTnt(false);
				used = true;
				Sounds.playTNT();
				if(id == 1) Achievements.incrementAchievement(Achievements.AchievementType.UseTNT);
				switchAction();
			}
			lastAction = used ? System.currentTimeMillis() : lastAction;
			return used;
		}
		return false;
	}

	/**
	 * Updates all observers on the brick status.
	 * 
	 * @param amount
	 *            the brick amount
	 */
	private void updateBrick(int amount) {
		for (PlayerModelObserver v : observers) {
			v.updateBrick(amount);
		}
	}

	/**
	 * Updates all observers on the pick status.
	 * 
	 * @param hasPick
	 *            if this player has a pick
	 */
	private void updatePick(boolean hasPick) {
		for (PlayerModelObserver v : observers) {
			v.updatePick(hasPick);
		}
	}

	/**
	 * Updates all observers on the TNT status.
	 * 
	 * @param hasTnt
	 *            if this player has a TNT
	 */
	private void updateTnt(boolean hasTnt) {
		for (PlayerModelObserver v : observers) {
			v.updateTnt(hasTnt);
		}
	}

	/**
	 * Updates all observers on the action status.
	 * 
	 * @param action
	 *            the current action of this player
	 */
	private void updateAction(Action action) {
		for (PlayerModelObserver v : observers) {
			v.updateAction(action);
		}
	}

	@Override
	public String toString() {
		return "<Player: " + id + ">";
	}

}
