package deco2800.arcade.mixmaze;

import com.badlogic.gdx.scenes.scene2d.Group;

import deco2800.arcade.mixmaze.domain.TileModel;

public class TileViewModel extends Group {
	private TileModel model;
	
	public TileViewModel(TileModel m) {
		model = m;
	}
}
