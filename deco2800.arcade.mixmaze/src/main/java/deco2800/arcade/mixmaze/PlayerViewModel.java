/*
 * PlayerViewModel
 */
package deco2800.arcade.mixmaze;

import deco2800.arcade.mixmaze.domain.BrickModel;
import deco2800.arcade.mixmaze.domain.ItemModel;
import deco2800.arcade.mixmaze.domain.MixMazeModel;
import deco2800.arcade.mixmaze.domain.PlayerModel;
import deco2800.arcade.mixmaze.domain.PlayerModel.PlayerAction;
import deco2800.arcade.mixmaze.domain.TileModel;
import deco2800.arcade.utils.KeyManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

import java.util.HashMap;

import static deco2800.arcade.mixmaze.domain.Direction.*;
import static deco2800.arcade.mixmaze.domain.ItemModel.Type.*;
import static deco2800.arcade.mixmaze.domain.PlayerModel.PlayerAction.*;

import static com.badlogic.gdx.Input.Keys.*;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;

/**
 * PlayerViewModel draws the character of the player.
 */
final class PlayerViewModel extends Actor {

	private static final String LOG = PlayerViewModel.class.getSimpleName();

	private final PlayerModel model;
	private final MixMazeModel gameModel;
	private final int tileSize;
	private final TextureRegion[] region;
	private final KeyManager km;
	private final int id;

	/**
	 * Handles movement input.
	 */
	private class PlayerInputListener extends InputListener {

		@Override
		public boolean keyDown(InputEvent event, int keycode) {
			Gdx.app.debug(LOG, "player " + id);
			/*
			Gdx.app.debug(LOG, keycode + " pressed");
			Gdx.app.debug(LOG, km.get(keycode) + " mapped");
			*/

			switch (km.get(keycode)) {
			case LEFT:
				gameModel.movePlayer(model, WEST);
				break;
			case RIGHT:
				gameModel.movePlayer(model, EAST);
				break;
			case UP:
				gameModel.movePlayer(model, NORTH);
				break;
			case DOWN:
				gameModel.movePlayer(model, SOUTH);
				break;
			case NUM_5:
				Gdx.app.debug(LOG, "changing action");
				switchAction();
				break;
			case NUM_6:
				/*
				Gdx.app.debug(LOG, "invoking action");
				Gdx.app.debug(LOG, "player: " + model.getX()
						+ "\t" + model.getY());
				*/
				invokeAction();
				break;
			default:
				return false;	// event not handled
			}
			Gdx.app.debug(LOG, "directon: " + model.getDirection());
			Gdx.app.debug(LOG, "pos: " + model.getX() + "\t"
					+ model.getY());
			event.cancel();
			return true;
		}
	}

	/**
	 * Constructor
	 */
	PlayerViewModel(PlayerModel model, MixMazeModel gameModel,
			int tileSize, int id) {
		Vector2 stagePos;
		Texture texture;
		HashMap<Integer, Integer> mapping =
				new HashMap<Integer, Integer>();

		this.model = model;
		this.gameModel = gameModel;
		this.tileSize = tileSize;
		this.id = id;

		/* Player 1 uses W, S, A, D to move. */
		if (id == 1) {
			mapping.put(W, UP);
			mapping.put(S, DOWN);
			mapping.put(A, LEFT);
			mapping.put(D, RIGHT);
			mapping.put(G, NUM_5);
			mapping.put(H, NUM_6);
		}
		km = new KeyManager(mapping);

		/* load texture */
		texture = new Texture(Gdx.files.internal(
					(id == 1) ? "devil.png" : "angel.png"));
		region = new TextureRegion[4];

		/* index should be consistent with domain.Direction */
		region[NORTH] = new TextureRegion(texture, 0, 0, 256, 256);
		region[SOUTH] = new TextureRegion(texture, 256, 0, 256, 256);
		region[WEST] = new TextureRegion(texture, 512, 0, 256, 256);
		region[EAST] = new TextureRegion(texture, 768, 0, 256, 256);

		addListener(new PlayerInputListener());
	}

	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		//Gdx.app.log(LOG, "" + getX() + "\t" + getY());

		/*
		 * NOTE: models use the y-down coordinate system.
		 */
		batch.draw(region[model.getDirection()],
				model.getX() * tileSize,
				640 - (model.getY() + 1) * tileSize,
				tileSize, tileSize);
	}

	/**
	 * Returns the amount of bricks this player has.
	 *
	 * @return the amount of bricks
	 */
	public int getBrickAmount() {
		BrickModel brick = model.getBrick();

		if (brick == null) {
			return 0;
		} else {
			return brick.getAmount();
		}
	}

	private boolean hasItem(ItemModel.Type type) {
		ItemModel item = null;

		switch (type) {
		case PICK:
			item = model.getPick();
			break;
		case TNT:
			item = model.getTNT();
			break;
		}

		return (item == null) ? false : true;
	}

	/**
	 * Returns if this player has a pick.
	 *
	 * @param pid the player id, can be either 1 or 2
	 * @return true if the player has pick, otherwise false
	 */
	public boolean hasPick() {
		return hasItem(PICK);
	}

	/**
	 * Returns if this player has a TNT.
	 *
	 * @param pid the player id, can be either 1 or 2
	 * @return true if the player has TNT, otherwise false
	 */
	public boolean hasTNT() {
		return hasItem(TNT);
	}

	/**
	 * Returns the number of boxes this player has.
	 *
	 * @return the number of boxes
	 */
	public int getScore() {
		return gameModel.getPlayerScore(model);
	}

	/**
	 * Returns the name of the active action of this player.
	 *
	 * @return a String representing the active action
	 */
	public String getActionName() {
		PlayerAction act = model.getPlayerAction();

		switch (act) {
		case USE_BRICK:
			return "using brick";
		case USE_PICK:
			return "using pick";
		case USE_TNT:
			return "using TNT";
		default:
			return "unknown";
		}
	}

	/*
	 * Switch to the next action, in this order
	 * USE_BRICK -> USE_PICK -> USE_TNT -> USE_BRICK
	 * Skip an action if this player does not have the associated item.
	 */
	private void switchAction() {
		PlayerAction act = model.getPlayerAction();

		switch (act) {
		case USE_BRICK:
			if (hasPick()) {
				model.setPlayerAction(USE_PICK);
			} else if (hasTNT()) {
				model.setPlayerAction(USE_TNT);
			}
			break;
		case USE_PICK:
			if (hasTNT()) {
				model.setPlayerAction(USE_TNT);
			} else {
				model.setPlayerAction(USE_BRICK);
			}
			break;
		case USE_TNT:
			model.setPlayerAction(USE_BRICK);
			break;
		}
	}

	/*
	 * Invokes the active action of this player.
	 */
	private void invokeAction() {
		TileModel tile = gameModel.getBoardTile(
				model.getX(), model.getY());
		PlayerAction act = model.getPlayerAction();

		switch (act) {
		case USE_BRICK:
			tile.buildWall(model, model.getDirection());
			break;
		case USE_PICK:
			tile.destroyWall(model, model.getDirection());
			break;
		case USE_TNT:
			for (int dir = 0; dir < 4; dir++)
				tile.destroyWall(model, dir);
			break;
		}
	}

}
