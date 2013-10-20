package deco2800.arcade.mixmaze;

import deco2800.arcade.mixmaze.domain.IMixMazeModel;
import deco2800.arcade.mixmaze.domain.PlayerModel;
import deco2800.arcade.mixmaze.domain.PlayerModelObserver;
import deco2800.arcade.utils.KeyManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.utils.Timer;
import java.util.HashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static deco2800.arcade.mixmaze.domain.Direction.*;
import static com.badlogic.gdx.Input.Keys.*;

/**
 * PlayerViewModel draws the character of the player.
 */
public final class PlayerViewModel extends Actor implements PlayerModelObserver {

	final Logger logger = LoggerFactory.getLogger(PlayerViewModel.class);

	private final IMixMazeModel gameModel;
	private final int tileSize;
	private final TextureRegion bodyRegion;
	private final TextureRegion headRegion;
	private final TextureRegion buildRegion;
	private final TextureRegion destroyRegion;
	private final KeyManager km;
	private final int id;
	private final GameScreen.ScoreBar scorebar;
	private final GameScreen.SidePanel sidePanel;

	private int x;
	private int y;
	private int rotation;
	private boolean building;
	private boolean destroying;

	/**
	 * Constructor
	 * 
	 * @param playerControls
	 */
	PlayerViewModel(IMixMazeModel gameModel, int tileSize, int id,
			int[] playerControls, GameScreen.ScoreBar scorebar,
			GameScreen.SidePanel sidePanel,
			TextureRegion headRegion) {
		HashMap<Integer, Integer> mapping = new HashMap<Integer, Integer>();

		this.gameModel = gameModel;
		this.tileSize = tileSize;
		this.id = id;
		this.scorebar = scorebar;
		this.sidePanel = sidePanel;

		if (playerControls[0] != NUM_5){
			mapping.put(playerControls[0], NUM_5);}
		if (playerControls[1] != NUM_6){
			mapping.put(playerControls[1], NUM_6);}
		if (playerControls[2] != UP){
			mapping.put(playerControls[2], UP);}
		if (playerControls[3] != LEFT){
			mapping.put(playerControls[3], LEFT);}
		if (playerControls[4] != DOWN){
			mapping.put(playerControls[4], DOWN);}
		if (playerControls[5] != RIGHT){
			mapping.put(playerControls[5], RIGHT);}

		km = new KeyManager(mapping);

		/* load texture */
		bodyRegion = new TextureRegion(
				new Texture(Gdx.files.internal("body.png")));
		buildRegion = new TextureRegion(
				new Texture(Gdx.files.internal("build.png")));
		destroyRegion = new TextureRegion(
				new Texture(Gdx.files.internal("destroy.png")));
		/*
		texture = new Texture(Gdx.files.internal((id == 1) ? "miner.png"
				: "cowboy.png"));
		*/
		this.headRegion = headRegion;
		building = false;
		destroying = false;

		if (id == 1){
			this.setColor(1f, 0f, 0f, 1f);
		}
		else{
			this.setColor(0f, 0f, 1f, 1f);
		}
		addListener(new PlayerInputListener());
	}

	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		Color old = batch.getColor();

		batch.setColor(this.getColor());
		batch.draw(bodyRegion, x * tileSize, 640 - (y + 1) * tileSize,
				tileSize, tileSize);
		if (building) {
			batch.draw(buildRegion, x * tileSize, 
					640 - (y + 1) * tileSize,
					tileSize / 2, tileSize / 2,
					tileSize, tileSize,
					1, 1, rotation);
		}
		if (destroying) {
			batch.draw(destroyRegion, x * tileSize, 
					640 - (y + 1) * tileSize,
					tileSize / 2, tileSize / 2,
					tileSize, tileSize,
					1, 1, rotation);
		}
		batch.setColor(old);
		batch.draw(headRegion, x * tileSize, 640 - (y + 1) * tileSize,
				tileSize / 2, tileSize / 2, tileSize, tileSize, 1, 1, rotation);
	}

	@Override
	public void updateScore(int score) {
		logger.debug("score: {}", score);
		scorebar.update(score);
	}

	@Override
	public void updateBrick(int amount) {
		logger.debug("amount: {}", amount);
		sidePanel.updateBrick(amount);
	}

	@Override
	public void updatePick(boolean hasPick) {
		logger.debug("hasPick: {}", hasPick);
		sidePanel.updatePick(hasPick);
	}

	@Override
	public void updateTnt(boolean hasTnt) {
		logger.debug("hasTnt: {}", hasTnt);
		sidePanel.updateTnt(hasTnt);
	}

	@Override
	public void updateAction(PlayerModel.Action action) {
		logger.debug("action: {}", action);
		sidePanel.updateAction(action);
	}

	@Override
	public void updateDirection(int direction) {
		logger.debug("direction: {}", direction);
		switch (direction) {
		case WEST:
			rotation = -90;
			break;
		case NORTH:
			rotation = 180;
			break;
		case EAST:
			rotation = 90;
			break;
		case SOUTH:
			rotation = 0;
			break;
		default:
			break;
		}
	}

	@Override
	public void updatePosition(int x, int y) {
		logger.debug("(x, y): ({}, {})", x, y);
		this.x = x;
		this.y = y;
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
	/*
	 * public int getBrickAmount() { IBrickModel brick = model.getBrick();
	 * 
	 * if (brick == null) { return 0; } else { return brick.getAmount(); } }
	 */

	/*
	 * private boolean hasItem(IItemModel.ItemType type) { IItemModel item =
	 * null;
	 * 
	 * switch (type) { case PICK: item = model.getPick(); break; case TNT: item
	 * = model.getTNT(); break; }
	 * 
	 * return (item == null) ? false : true; }
	 */

	/**
	 * Returns if this player has a pick.
	 * 
	 * @param pid
	 *            the player id, can be either 1 or 2
	 * @return true if the player has pick, otherwise false
	 */
	/*
	 * public boolean hasPick() { return hasItem(PICK); }
	 */

	/**
	 * Returns if this player has a TNT.
	 * 
	 * @param pid
	 *            the player id, can be either 1 or 2
	 * @return true if the player has TNT, otherwise false
	 */
	/*
	 * public boolean hasTNT() { return hasItem(TNT); }
	 */

	/**
	 * Returns the active action of this player.
	 * 
	 * @return one of <code>USE_BRICK</code>, <code>USE_PICK</code>, or
	 *         <code>USE_TNT</code>
	 */
	/*
	 * public PlayerAction getAction() { return model.getPlayerAction(); }
	 */

	/**
	 * Returns the name of the active action of this player.
	 * 
	 * @return a String representing the active action
	 */
	/*
	 * public String getActionName() { PlayerAction act =
	 * model.getPlayerAction();
	 * 
	 * switch (act) { case USE_BRICK: return "using brick"; case USE_PICK:
	 * return "using pick"; case USE_TNT: return "using TNT"; default: return
	 * "unknown"; } }
	 */

	/*
	 * Handles movement input.
	 */
	private class PlayerInputListener extends InputListener {

		@Override
		public boolean keyDown(InputEvent event, int keycode) {
			switch (km.get(keycode)) {
			case LEFT:
				gameModel.movePlayer(id, WEST);
				break;
			case RIGHT:
				gameModel.movePlayer(id, EAST);
				break;
			case UP:
				gameModel.movePlayer(id, NORTH);
				break;
			case DOWN:
				gameModel.movePlayer(id, SOUTH);
				break;
			case NUM_5:
				gameModel.switchPlayerAction(id);
				break;
			case NUM_6:
				PlayerModel.Action act = 
						gameModel.usePlayerAction(id);
				if (act == PlayerModel.Action.USE_BRICK) {
					building = true;
					Timer.schedule(new Timer.Task(){
						public void run() {
							building = false;
						}
					}, 1);
				} else if (act == PlayerModel.Action.USE_PICK) {
					destroying = true;
					Timer.schedule(new Timer.Task(){
						public void run() {
							destroying = false;
						}
					}, 1);
				}
				break;
			default:
				return false; // event not handled
			}

			event.cancel();
			return true;
		}
	}

}
