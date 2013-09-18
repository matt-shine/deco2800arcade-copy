package com.test.game.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

public class Block extends Entity {
	
	//private Texture texture;
	public enum TextureAtlasReference {
		LEVEL
	}
	
	TextureAtlasReference atlas;
	private int atlasIndex;
	
	static final float SIZE = 1f;
	
	
	
	public Block(Vector2 pos, TextureAtlasReference atlas, int atlasIndex) {
		super(pos, SIZE, SIZE);
		this.atlas = atlas;
		this.atlasIndex = atlasIndex;
	}



	public TextureAtlasReference getAtlas() {
		return atlas;
	}



	/*public void setAtlas(TextureAtlas atlas) {
		this.atlas = atlas;
	}*/



	public int getAtlasIndex() {
		return atlasIndex;
	}



	public void setAtlasIndex(int atlasIndex) {
		this.atlasIndex = atlasIndex;
	}



	
}
