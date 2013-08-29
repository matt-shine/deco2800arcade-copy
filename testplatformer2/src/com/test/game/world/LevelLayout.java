package com.test.game.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

public class LevelLayout {
	private OrthogonalTiledMapRenderer renderer;
	private TiledMap map;
	//private TileAtlas mapAtlas;
	private TiledMapTileLayer collisionLayer;
	
	public LevelLayout (int level) {
		//edit these strings to use the level parameter
		map = new TmxMapLoader().load("data/level"+level+".tmx");
		/*mapAtlas = new TileAtlas(map, Gdx.files.internal("data/level"+level));
		renderer = new TiledMapRenderer(map, mapAtlas, 32, 32, 1, 1);
		collisionLayer = (TiledLayer)map.layers.get(1);*/
		renderer = new OrthogonalTiledMapRenderer(map, 1/32f); //Will also need to change stuff in the level objects classes
		collisionLayer = (TiledMapTileLayer)map.getLayers().get("Collision");
	}
	/*
	public TileMapRenderer getRenderer() {
		return renderer;
	}
	
	public TiledLayer getCollisionLayer() {
		return collisionLayer;
	}*/
	public OrthogonalTiledMapRenderer getRenderer() {
		return renderer;
	}
	
	public TiledMapTileLayer getCollisionLayer() {
		return collisionLayer;
	}
	
	public TiledMap getMap() {
		return map;
	}
	
}
