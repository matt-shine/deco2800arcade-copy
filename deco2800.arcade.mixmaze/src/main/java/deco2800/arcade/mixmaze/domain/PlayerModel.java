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

	public int getPlayerID() {
		return playerID;
	}

	public int getX() {
		return playerX;
	}

	public int getNextX() {
		if(!isXDirection(playerDirection)) {
			return playerX;
		}
		return isPositiveDirection(playerDirection) ? (playerX + 1) : (playerX - 1);
	}

	public void setX(int x) {
		playerX = x;
	}

	public int getY() {
		return playerY;
	}

	public int getNextY() {
		if(!isYDirection(playerDirection)) {
			return playerY;
		}
		return isPositiveDirection(playerDirection) ? (playerY + 1) : (playerY - 1);
	}

	public void setY(int y) {
		playerY = y;
	}

	public boolean canMove() {
		return (System.currentTimeMillis() - lastMoved) >= (0.3 * 1000);
	}

	public void move() {
		playerX = getNextX();
		playerY = getNextY();
		lastMoved = System.currentTimeMillis();
	}

	public int getDirection() {
		return playerDirection;
	}

	public void setDirection(int direction) {
		if(!isDirection(direction)) {
			throw NOT_A_DIRECTION;
		}
		playerDirection = direction;
	}

	/**
	 * Returns the acitve action of this player.
	 *
	 * @return one of USE_BRICK, USE_PICK, and USE_TNT
	 */
	public PlayerAction getPlayerAction() {
		return playerAction;
	}

	/**
	 * Sets the acitve action of this player.
	 *
	 * @param action Possible values are USE_BRICK, USE_PICK, and USE_TNT.
	 */
	public void setPlayerAction(PlayerAction action) {
		playerAction = action;
	}

	public boolean canUseAction() {
		return (System.currentTimeMillis() - lastAction) >= (3 * 1000);
	}

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

	public BrickModel getBrick() {
		return brick;
	}

	public PickModel getPick() {
		return pick;
	}

	public TNTModel getTNT() {
		return tnt;
	}

	public PlayerModel(int id) {
		playerID = id;
		playerAction = PlayerAction.USE_BRICK;
		brick = new BrickModel(null, 4);
	}
}
