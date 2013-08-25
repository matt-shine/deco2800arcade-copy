package deco2800.arcade.mixmaze;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Group;

import deco2800.arcade.mixmaze.domain.TileModel;

public class TileViewModel extends Group {
	private static final String LOG = TileViewModel.class.getSimpleName();
	
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
		Gdx.app.debug(LOG, String.format("Initializing (%d, %d)", m.getX(), m.getY()));
		model = m;
		setX(128f * model.getX());
		setY(128f * model.getY());
		
		// Initialize wall view models
		walls = new WallViewModel[4];
		for(int i = 0; i < 4; ++i) {
			walls[i] = new WallViewModel(model.getWall(i));
			this.addActor(walls[i]);
		}
	}
}
