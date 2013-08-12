package com.test.game.world;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.test.game.model.Enemy;
import com.test.game.model.EnemySpawner;
import com.test.game.model.Follower;

public final class Level1Objects {

	

	public static Array<Object> loadObjects(TiledMap map) {
		Array<Object> out = new Array<Object>();
		for (MapObject object : map.getLayers().get("spawners").getObjects()) {
			if(object instanceof RectangleMapObject) {
				Rectangle rect = ((RectangleMapObject)object).getRectangle();
				Class<Follower> c = Follower.class;
				EnemySpawner e = new EnemySpawner(c, new Vector2(rect.x/32f, rect.y/32f), 4, 100, 1); //the 32f comes from the LevelLayout
				out.add(e);
			}
		}
		return out;
	}
}
