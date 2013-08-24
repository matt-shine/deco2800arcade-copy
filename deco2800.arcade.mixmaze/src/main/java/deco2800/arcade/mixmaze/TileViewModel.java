package deco2800.arcade.mixmaze;

import com.badlogic.gdx.scenes.scene2d.Actor;

import deco2800.arcade.mixmaze.domain.TileModel;

public class TileViewModel extends Actor {
	private TileModel model;
	private WallViewModel[] walls;
	
	public void dispose() {
		for(int i = 0; i < 4; ++i) {
			walls[i].dispose();
		}
	}
	
	public TileViewModel(TileModel m) {
		if(m == null) {
			throw new IllegalArgumentException("m cannot be null.");
		}
		model = m;
		
		// Initialize wall view models
		walls = new WallViewModel[4];
		for(int i = 0; i < 4; ++i) {
			walls[i] = new WallViewModel(model.getWall(i));
		}
		
		setX(128f * model.getX());
		setY(128f * model.getY());
	}
}
