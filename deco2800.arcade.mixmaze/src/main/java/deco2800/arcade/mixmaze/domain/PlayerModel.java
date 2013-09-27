package deco2800.arcade.mixmaze.domain;

import deco2800.arcade.mixmaze.domain.view.IBrickModel;
import deco2800.arcade.mixmaze.domain.view.IItemModel;
import deco2800.arcade.mixmaze.domain.view.IItemModel.ItemType;
import deco2800.arcade.mixmaze.domain.view.IPlayerModel;
import deco2800.arcade.mixmaze.domain.view.IPickModel;
import deco2800.arcade.mixmaze.domain.view.ITNTModel;
import deco2800.arcade.mixmaze.domain.view.ITileModel;

import static deco2800.arcade.mixmaze.domain.Direction.*;

/**
 * Player model represents a player.
 */
public class PlayerModel implements IPlayerModel {

	// Player data
	private int playerID;
	private int playerX;
	private int playerY;
	private int playerDirection;
	private PlayerAction playerAction;
	private long lastMoved;
	private long lastAction;

	// Item data
	private BrickModel brick;
	private PickModel pick;
	private TNTModel tnt;

	/**
	 * Returns the playerID of this player
	 * @return the <code>playerID</> of this player
	 */
	public int getPlayerID() {
		return playerID;
	}

	/**
	 * Returns the x-coordinates for this player. The x-coordinate's orgin is at
	 * the top left.
	 *
	 * @return the x-coordinate of this player
	 */
	public int getX() {
		return playerX;
	}

	/**
	 * Returns the next x-coordinate. Note the returned coordinate
	 * might be out of board range.
	 *
	 * @return the next x-coordinate in relative to the direction facing.
	 */
	public int getNextX() {
		if(!isXDirection(playerDirection)) {
			return playerX;
		}
		return isPositiveDirection(playerDirection) ? (playerX + 1) : (playerX - 1);
	}

	/**
	 * Sets the x-coordinates for this player. The x-coordinate's orgin is at
	 * the top left.
	 *
	 * @param x the x-coordinate of this player
	 */
	public void setX(int x) {
		playerX = x;
	}

	/**
	 * Returns the y-coordinates for this player. The y-coordinate's orgin is at
	 * the top left.
	 *
	 * @return the y-coordinate of this player
	 */
	public int getY() {
		return playerY;
	}

	public int getNextY() {
		if(!isYDirection(playerDirection)) {
			return playerY;
		}
		return isPositiveDirection(playerDirection) ? (playerY + 1) : (playerY - 1);
	}

	/**
	 * Sets the y-coordinates for this player. The y-coordinate's orgin is at
	 * the top left.
	 *
	 * @param x the y-coordinate of this player
	 */
	public void setY(int y) {
		playerY = y;
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
	 * Moves the player. This player is moved one <code>tile</> forward in the direction facing.
	 *
	 */
	public void move() {
		if(canMove()) {
			playerX = getNextX();
			playerY = getNextY();
			lastMoved = System.currentTimeMillis();
		}
	}

	/**
	 * Returns the <code>direction</> of this player.
	 *
	 * @return the facing <code>direction</> of this player
	 */
	public int getDirection() {
		return playerDirection;
	}

	/**
	 * Change this player's direction by the specified <code>direction</>
	 * @param direction an integer representation of the direction
	 */
	public void setDirection(int direction) {
		if(!isDirection(direction)) {
			throw NOT_A_DIRECTION;
		}
		playerDirection = direction;
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

	public boolean pickupItem(IItemModel iitem) {
		ItemModel item = (ItemModel) iitem;

		if(item.getType() == ItemType.BRICK) {
			BrickModel brick = (BrickModel)item;
			brick.mergeBricks(brick);
			return brick.getAmount() == 0;
		} else if(item.getType() == ItemType.PICK && pick == null) {
			pick = (PickModel)item;
			return true;
		} else if(item.getType() == ItemType.TNT && tnt == null) {
			tnt = (TNTModel)item;
			return true;
		}
		return false;
	}

	/**
	 * Returns the active action of this player.
	 *
	 * @return one of USE_BRICK, USE_PICK, and USE_TNT
	 */
	public PlayerAction getPlayerAction() {
		return playerAction;
	}

	/**
	 * Switches to the next action, in this order
	 * USE_BRICK - USE_PICK - USE_TNT - USE_BRICK.
	 * An action is skipped if this player does not have
	 * the associated item.
	 */
	public void switchAction() {
		playerAction = playerAction.getNextAction();
		if((playerAction == PlayerAction.USE_PICK && pick == null) || (playerAction == PlayerAction.USE_TNT && tnt == null)) {
			switchAction();
		}
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
			if(playerAction == PlayerAction.USE_BRICK && brick.getAmount() > 0) {
				WallModel wall = (WallModel) tile.getWall(playerDirection);
				if(!wall.isBuilt()) {
					brick.removeOne();
					wall.build(this);
					used = true;
				}
			} else if(playerAction == PlayerAction.USE_PICK && pick != null) {
				WallModel wall = (WallModel) tile.getWall(playerDirection);
				if(wall.isBuilt()) {
					pick = null;
					wall.destroy(this);
					used = true;
				}
			} else if(playerAction == PlayerAction.USE_TNT && tnt != null) {
				tnt = null;
				for(int direction = 0; direction < 4; ++direction) {
					WallModel wall = (WallModel) tile.getWall(direction);
					if(wall.isBuilt()) {
						wall.destroy(this);
					}
				}
				used = true;
			}
			lastAction = used ? System.currentTimeMillis() : lastAction;
		}
		return false;
	}

	@Override
	public String toString() {
		return "<Player " + playerID + ">";
	}

	/**
	 * Constructs a new Player with the specified <code>id</code>
	 *
	 * @param id the playerID of this player
	 */
	public PlayerModel(int id) {
		playerID = id;
		playerAction = PlayerAction.USE_BRICK;
		brick = new BrickModel(4);
	}
}
