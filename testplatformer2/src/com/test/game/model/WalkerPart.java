package com.test.game.model;

import com.badlogic.gdx.math.Vector2;

public class WalkerPart extends MovableEntity {
	private int id;
	public WalkerPart(int id, float rotation, Vector2 position) {
		super(0, rotation, position, 1, 1);
		this.id = id;
	}
	
	public int getId () {
		return id;
	}
}
