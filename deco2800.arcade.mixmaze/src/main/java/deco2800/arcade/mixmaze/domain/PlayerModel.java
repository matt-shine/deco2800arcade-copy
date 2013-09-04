package deco2800.arcade.mixmaze.domain;

import static deco2800.arcade.mixmaze.domain.Direction.*;
import static deco2800.arcade.mixmaze.domain.BrickModel.MAX_BRICKS;
import static deco2800.arcade.mixmaze.domain.PlayerModel.PlayerAction.*;

/**
 * Player model represents a player.
 */
public class PlayerModel {
	public enum PlayerAction {
		USE_BRICK,
		USE_PICK,
		USE_TNT
	}

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
	 * Returns the next x-coordinate.
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
	public boolean canMove() {
		return (System.currentTimeMillis() - lastMoved) >= (0.3 * 1000);
	}

	/**
	 * Moves the player. This player is moved one <code>tile</> forward in the direction facing.
	 *
	 */
	public void move() {
		playerX = getNextX();
		playerY = getNextY();
		lastMoved = System.currentTimeMillis();
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

	/**
	 * Returns the active action of this player.
	 *
	 * @return one of USE_BRICK, USE_PICK, and USE_TNT
	 */
	public PlayerAction getPlayerAction() {
		return playerAction;
	}

	/**
	 * Sets the active action of this player.
	 *
	 * @param action Possible values are USE_BRICK, USE_PICK, and USE_TNT.
	 */
	public void setPlayerAction(PlayerAction action) {
		playerAction = action;
	}

	/**Checks of this player can perform a action.
	 *
	 * @return true if this player can use any action, false otherwise
	 */
	public boolean canUseAction() {
		return (System.currentTimeMillis() - lastAction) >= (3 * 1000);
	}

	/**
	 * Picks up an item on a tile of the gameboard.
	 * <p>
	 * This player will pick up as many bricks as possible until reaching
	 * <code>MAX_BRICKS</code>.  In the case of pick or TNT, the item will
	 * be only picked up if this player does not possess one.
	 *
	 * @param item the item to be picked up by this player
	 */
	public void pickUpItem(ItemModel item) {
		if (item instanceof BrickModel) {
			BrickModel tileBrick = (BrickModel)item;

			if (brick.getAmount() + tileBrick.getAmount()
					<= MAX_BRICKS) {
				/* pick up all bricks */
				brick.addAmount(tileBrick.getAmount());
				tileBrick.pickUpItem();
			} else {
				/* pick up to the maximum number */
				tileBrick.removeAmount(MAX_BRICKS
						- brick.getAmount());
				brick.setAmount(MAX_BRICKS);
			}
		} else if (item instanceof PickModel) {
			PickModel tilePick = (PickModel)item;
			if (pick == null) {
				pick = tilePick;
				pick.pickUpItem();
			}
		} else if (item instanceof TNTModel) {
			TNTModel tileTNT = (TNTModel)item;
			if (tnt == null) {
				tnt = tileTNT;
				tnt.pickUpItem();
			}
		}
	}

	// all these 3 method are not used anywher in the workspad? dumindu
	public BrickModel getBrick() {
		return brick;
	}

	public PickModel getPick() {
		return pick;
	}

	public TNTModel getTNT() {
		return tnt;
	}

	/**
	 * Switches to the next action, in this order
	 * USE_BRICK - USE_PICK - USE_TNT - USE_BRICK.
	 * An action is skipped if this player does not have
	 * the associated item.
	 */
	public void switchAction() {
		/* brick is never null */
		switch (playerAction) {
		case USE_BRICK:
			if (pick != null) {
				setPlayerAction(USE_PICK);
			} else if (tnt != null) {
				setPlayerAction(USE_TNT);
			}
			break;
		case USE_PICK:
			if (tnt != null) {
				setPlayerAction(USE_TNT);
			} else {
				setPlayerAction(USE_BRICK);
			}
			break;
		case USE_TNT:
			setPlayerAction(USE_BRICK);
			break;
		}
	}

	/**
	 * Uses the active action of this player.
	 *
	 * @param tile the tile where this player is
	 */
	public void useAction(TileModel tile) {
		switch (playerAction) {
		case USE_BRICK:
			tile.buildWall(this, playerDirection);
			break;
		case USE_PICK:
			if (tile.destroyWall(this, playerDirection))
				pick = null;
			break;
		case USE_TNT:
			tnt = null;	// exploded
			for (int dir = 0; dir < 4; dir++)
				tile.destroyWall(this, dir);
			break;
		}
	}

	/**
	 * Constructs a new Player with the specified <code>id</code>
	 *
	 * @param id the playerID of this player
	 */
	public PlayerModel(int id) {
		playerID = id;
		playerAction = PlayerAction.USE_BRICK;
		// why do we create brickmodel here? dumindu
		brick = new BrickModel(4);
	}

}
