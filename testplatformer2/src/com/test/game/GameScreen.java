package com.test.game;

import com.test.game.world.World;
import com.test.game.world.WorldRenderer;

public class GameScreen extends AbstractScreen {

	World world;
	WorldRenderer render;
	//LevelLayout levelLayout;
	int level = 1;
	
	public GameScreen(TestGame2 game) {
		super(game);
		world = new World(game, level);
		render = new WorldRenderer(world);
	}
	
	public void render(float delta) {
		super.render(delta);
		world.update();
		render.render();
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
