/*
 * PlayerViewModel
 */
package deco2800.arcade.mixmaze;

import deco2800.arcade.mixmaze.domain.MixMazeModel;
import deco2800.arcade.mixmaze.domain.PlayerModel;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

import static deco2800.arcade.mixmaze.domain.Direction.*;

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

	/**
	 * Handles movement input.
	 */
	private class PlayerInputListener extends InputListener {
		@Override
		public boolean keyDown(InputEvent event, int keycode) {
			Actor actor = event.getListenerActor();

			Gdx.app.debug(LOG, keycode + " pressed");
			switch (keycode) {
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
			default:
				return false;	// event not handled
			}
			Gdx.app.debug(LOG, "directon: " + model.getDirection());
			Gdx.app.debug(LOG, "pos: " + model.getX() + "\t"
					+ model.getY());
			return true;
		}
	}

	/**
	 * Constructor
	 */
	PlayerViewModel(PlayerModel model, MixMazeModel gameModel,
			int tileSize) {
		Vector2 stagePos;
		Texture texture;

		this.model = model;
		this.gameModel = gameModel;
		this.tileSize = tileSize;

		/* load texture */
		texture = new Texture(Gdx.files.internal("devil.png"));
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
}
