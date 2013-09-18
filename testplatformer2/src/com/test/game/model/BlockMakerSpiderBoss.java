package com.test.game.model;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class BlockMakerSpiderBoss extends BlockMaker {
	
	public static final float SPEED = 3f;
	
	private Array<Block> blocks;
	private float count;
	private float rank;
	private boolean camHasReachedStartPosition;
	
	private int loopPos;
	
	//private TextureAtlas groundTextures;
		
	private Array<MovableEntity> moveWithEntities;
	
	public BlockMakerSpiderBoss(float rank) {
		this(rank, new Array<MovableEntity>());
	}
	
	public BlockMakerSpiderBoss(float rank, Array<MovableEntity> moveWithEntities) {
		this.rank = rank;
		loopPos = 0;
		//groundTextures = new TextureAtlas("data/level packfile");
		isActive = false;
		this.moveWithEntities = moveWithEntities;
		blocks = new Array<Block>();
		initBlocks();
		camHasReachedStartPosition = false;
	}
	
	public Array<Block> getBlocks() {
		return blocks;
	}

	public void initBlocks() {
		float initX = 329; //find a better way to get this variable
		for (float i = 0; i < 70; i += 1f) {
			blocks.add(new Block(new Vector2(initX + i, 0f), Block.TextureAtlasReference.LEVEL, 0));
			blocks.add(new Block(new Vector2(initX + i, 1f), Block.TextureAtlasReference.LEVEL, 0));
			blocks.add(new Block(new Vector2(initX + i, 2f), Block.TextureAtlasReference.LEVEL, 0));
			blocks.add(new Block(new Vector2(initX + i, 3f), Block.TextureAtlasReference.LEVEL, 2));
		}
			
	}
	
	public void update(float delta, OrthographicCamera cam) {
		count += delta;
		
		//Add new blocks
		if (count > 1 / SPEED) {
			System.out.println("Making new block");
			count = 0;
			loopPos++;
			
			if (loopPos > 0 && loopPos < 15) {
				blocks.add(new Block(new Vector2(cam.position.x+cam.viewportWidth/2, 0f), Block.TextureAtlasReference.LEVEL, 0));
				blocks.add(new Block(new Vector2(cam.position.x+cam.viewportWidth/2, 1f), Block.TextureAtlasReference.LEVEL, 0));
				blocks.add(new Block(new Vector2(cam.position.x+cam.viewportWidth/2, 2f), Block.TextureAtlasReference.LEVEL, 0));
				blocks.add(new Block(new Vector2(cam.position.x+cam.viewportWidth/2, 3f), Block.TextureAtlasReference.LEVEL, 2));
			}
			if (loopPos > 2 && loopPos < 6) {
				blocks.add(new Block(new Vector2(cam.position.x+cam.viewportWidth/2, 7f), Block.TextureAtlasReference.LEVEL, 2));
				
			}
			if (loopPos >= 15 && rank < 50) {
				blocks.add(new Block(new Vector2(cam.position.x+cam.viewportWidth/2, 0f), Block.TextureAtlasReference.LEVEL, 0));
				blocks.add(new Block(new Vector2(cam.position.x+cam.viewportWidth/2, 1f), Block.TextureAtlasReference.LEVEL, 0));
				blocks.add(new Block(new Vector2(cam.position.x+cam.viewportWidth/2, 2f), Block.TextureAtlasReference.LEVEL, 0));
				blocks.add(new Block(new Vector2(cam.position.x+cam.viewportWidth/2, 3f), Block.TextureAtlasReference.LEVEL, 2));
			}
			if (loopPos == 20) {
				loopPos = 0;
			}
			
		}
		
		//Move objects in the moveWithEntities
		for (MovableEntity mve: moveWithEntities) {
			mve.getPosition().x -= delta * SPEED;
		}
		
		//Move and remove existing blocks
		for (int i=0; i<blocks.size; i++) {
			Block block = blocks.get(i);
			if (camHasReachedStartPosition) {
				block.getPosition().x -= delta * SPEED;
			} else {
				block.getPosition().x -= delta * SPEED / 2;
			}
			if (block.getPosition().x + block.getWidth() < cam.position.x - cam.viewportWidth/2) {
				blocks.removeIndex(i);
				i--;
			}
		}
		
		
	}
	
	public void camHasReachedStartPosition() {
		camHasReachedStartPosition = true;
	}

	
}
