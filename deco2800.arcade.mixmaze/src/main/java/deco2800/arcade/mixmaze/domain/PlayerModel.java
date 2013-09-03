package deco2800.arcade.mixmaze.domain;

import static deco2800.arcade.mixmaze.domain.Direction.*;

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

	//redundant method,havent been used anywhere in the workspace?dumi
	public void useAction(TileModel tile) {
		if(playerAction == PlayerAction.USE_BRICK) {
			if(brick != null) {
				tile.getWall(playerDirection).build(this);
				brick.removeAmount(1);
				if(brick.getAmount() == 0) {
					brick = null;
				}
			}
		} else if(playerAction == PlayerAction.USE_BRICK) {

		} else {

		}
		lastAction = System.currentTimeMillis();
	}
	
	/**
	 * Pickup an item from a tile in the gameboard. If the item is a a collection of bricks,
	 *  it will pick up as many brick as this player can until it reach the <code>MAX_BRICKS</code>
	 *  In case of pick or tnt, player will pickup only if the the player doesn't already own the item.
	 * 
	 * @param item an <code>ItemModel</code> object which to be picked up by this player
	 */
	public void pickUpItem(ItemModel item) {
		if(item instanceof BrickModel) {
			BrickModel tileBrick = (BrickModel)item;
			if(brick == null) {
				brick = tileBrick;
				brick.pickUpItem();
			} else {
				int maxConsume = BrickModel.MAX_BRICKS - brick.getAmount();
				int remainer = tileBrick.getAmount() - maxConsume;
				if(remainer < 0) {
					brick.addAmount(maxConsume + remainer);
					tileBrick.pickUpItem();
				} else if(remainer == 0) {
					brick.addAmount(maxConsume);
					tileBrick.pickUpItem();
				} else {
					brick.addAmount(maxConsume);
					tileBrick.setAmount(remainer);
				}
			}
		} else if(item instanceof PickModel) {
			PickModel tilePick = (PickModel)item;
			if(pick == null) {
				pick = tilePick;
				pick.pickUpItem();
			}
		} else {
			TNTModel tileTNT = (TNTModel)item;
			if(tnt == null) {
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
	 * Constructs a new Player with the specified <code>id</code>
	 * 
	 * @param id the playerID of this player
	 */
	public PlayerModel(int id) {
		playerID = id;
		playerAction = PlayerAction.USE_BRICK;
		// why do we create brickmodel here? dumindu 
		brick = new BrickModel(null, 4);
	}
}
