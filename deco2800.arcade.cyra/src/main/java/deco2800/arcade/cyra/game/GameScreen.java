package deco2800.arcade.cyra.game;

import com.badlogic.gdx.Gdx;

import deco2800.arcade.cyra.world.ParallaxCamera;
import deco2800.arcade.cyra.world.World;
import deco2800.arcade.cyra.world.WorldRenderer;

public class GameScreen extends AbstractScreen {

	World world;
	WorldRenderer render;
	//LevelLayout levelLayout;
	int level = 2;
	//boolean firstUpdate = true;
	
	public GameScreen(Cyra game) {
		super(game);
		float width = Gdx.graphics.getWidth()/45;
		float height = Gdx.graphics.getHeight()/45;
		ParallaxCamera cam = new ParallaxCamera(width, height);
		world = new World(game, level, cam);
		render = new WorldRenderer(world, cam);

	}
	
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
