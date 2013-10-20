package deco2800.arcade.cyra.game;

import com.badlogic.gdx.Gdx;

import deco2800.arcade.cyra.world.ParallaxCamera;
import deco2800.arcade.cyra.world.World;
import deco2800.arcade.cyra.world.WorldRenderer;

/**
 * The game screen, this controls what the game is running and rendering. 
 * 
 * @author Game Over
 *
 */
public class GameScreen extends AbstractScreen {

	private World world;
	private WorldRenderer render;
	private int level = 2;

	/**
	 * Constructing game screen, creates world and sets camera.
	 * 
	 * @param game instance of Cyra
	 * @param difficulty difficulty setting of the game
	 */
	public GameScreen(Cyra game, float difficulty) {
		super(game);
		float width = Gdx.graphics.getWidth()/45;
		float height = Gdx.graphics.getHeight()/45;
		ParallaxCamera cam = new ParallaxCamera(width, height);
		world = new World(game, level, difficulty, cam);
		render = new WorldRenderer(world, cam);

	}
	
	/**
	 * The main game loop.
	 */
	public void render(float delta) {
		super.render(delta);
		world.update();
		render.render();
		/*if (firstUpdate) {
			render.resetCamera();
			firstUpdate = false;
		}*/
	}
	
	public void hide() {
		super.hide();
		dispose();
	}
	
	public void dispose() {
		super.dispose();
		world.dispose();
	}

}
