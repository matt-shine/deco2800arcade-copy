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
import static deco2800.arcade.mixmaze.domain.ItemModel.ItemType.*;
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
	 * Constructor
	 * @param playerControls 
	 */
	PlayerViewModel(PlayerModel model, MixMazeModel gameModel,
			int tileSize, int id, int[] playerControls) {
		Vector2 stagePos;
		Texture texture;
		HashMap<Integer, Integer> mapping =
				new HashMap<Integer, Integer>();

		this.model = model;
		this.gameModel = gameModel;
		this.tileSize = tileSize;
		this.id = id;

	if(playerControls[0] != UP) mapping.put(playerControls[0], UP);
	if(playerControls[1] != DOWN) mapping.put(playerControls[1], DOWN);
	if(playerControls[2] != LEFT) mapping.put(playerControls[2], LEFT);
	if(playerControls[3] != RIGHT) mapping.put(playerControls[3], RIGHT);
	if(playerControls[4] != NUM_5) mapping.put(playerControls[4], NUM_5);
	if(playerControls[5] != NUM_6) mapping.put(playerControls[5], NUM_6);
		
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
		if (id == 1) {
			batch.setColor(1, 0, 0, 1);
		} else if (id == 2) {
			batch.setColor(0, 0, 1, 1);
		}

		batch.draw(region[model.getDirection()],
				model.getX() * tileSize,
				640 - (model.getY() + 1) * tileSize,
				tileSize, tileSize);
	}

	/**
	 * Returns the ID of this player.
	 *
	 * @return this player's ID
	 */
	public int getId() {
		return id;
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

	private boolean hasItem(ItemModel.ItemType type) {
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
	 * Returns the active action of this player.
	 *
	 * @return one of <code>USE_BRICK</code>, <code>USE_PICK</code>, or
	 * 	   <code>USE_TNT</code>
	 */
	public PlayerAction getAction() {
		return model.getPlayerAction();
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
	 * Handles movement input.
	 */
	private class PlayerInputListener extends InputListener {

		@Override
		public boolean keyDown(InputEvent event, int keycode) {
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
				model.switchAction();
				break;
			case NUM_6:
				TileModel tile = gameModel.getBoardTile(
						model.getX(), model.getY());
				model.useAction(tile);
				break;
			default:
				return false;	// event not handled
			}

			event.cancel();
			return true;
		}
	}

}
