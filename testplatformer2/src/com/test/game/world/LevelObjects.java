package com.test.game.world;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.utils.Array;

public abstract class LevelObjects {
	public abstract Array<Object> loadObjects(TiledMap map);
}
