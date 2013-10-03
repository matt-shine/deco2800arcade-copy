package deco2800.arcade.mixmaze.domain.view;

import deco2800.arcade.mixmaze.PlayerNetworkView;

public interface IPlayerModel {

	enum Action {
		USE_BRICK,
		USE_PICK,
		USE_TNT;

		public Action getNextAction(boolean hasPick, boolean hasTnt) {
			Action next = values()[(ordinal() + 1) % values().length];
			if ((next == USE_PICK && !hasPick)
					|| (next == USE_TNT && !hasTnt))
				return next.getNextAction(hasPick, hasTnt);
			else
				return next;
		}
	}

	/**
	 * Adds a viewer to this player model.
	 *
	 * @param v	the viewer
	 */
	void addViewer(PlayerNetworkView v);

	int getId();

	/**
	 * Returns the x-coordinates for this player. The x-coordinate's orgin is at
	 * the top left.
	 *
	 * @return the x-coordinate of this player
	 */
	int getX();

	/**
	 * Returns the next x-coordinate.
	 *
	 * @return the next x-coordinate in relative to the direction facing.
	 */
	int getNextX();

	/**
	 * Sets the x-coordinates for this player. The x-coordinate's orgin is at
	 * the top left.
	 *
	 * @param x the x-coordinate of this player
	 */
	void setX(int x);

	/**
	 * Returns the y-coordinates for this player. The y-coordinate's orgin is at
	 * the top left.
	 *
	 * @return the y-coordinate of this player
	 */
	int getY();

	int getNextY();

	/**
	 * Sets the y-coordinates for this player. The y-coordinate's orgin is at
	 * the top left.
	 *
	 * @param x the y-coordinate of this player
	 */
	void setY(int y);

	/**
	 * Moves the player. This player is moved one <code>tile</> forward in the direction facing.
	 *
	 */
	void move();

	/**
	 * Returns the <code>direction</> of this player.
	 *
	 * @return the facing <code>direction</> of this player
	 */
	int getDirection();

	/**
	 * Change this player's direction by the specified <code>direction</>
	 * @param direction an integer representation of the direction
	 */
	void setDirection(int direction);

	/**
	 * Switches to the next action, in this order
	 * USE_BRICK - USE_PICK - USE_TNT - USE_BRICK.
	 * An action is skipped if this player does not have
	 * the associated item.
	 */
	void switchAction();

	/**Checks of this player can perform a action.
	 *
	 * @return true if this player can use any action, false otherwise
	 */
	boolean canUseAction();

	/**
	 * Uses the active action of this player.
	 *
	 * @param tile the tile where this player is
	 */
	boolean useAction(ITileModel tile);

	IBrickModel getBrick();

	IPickModel getPick();

	ITNTModel getTNT();

}
