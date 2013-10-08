package deco2800.cyra.model;

import java.util.HashMap;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import deco2800.cyra.world.Level2Scenes;

public class BlockMakerSpiderBoss extends BlockMaker {
	
	public static final float SPEED = 3f;
	
	private Array<Block> blocks;
	private Array<Block> oldBlocks;
	private HashMap<Block, Integer> oldBlocksType;
	private Block latestBlock;
	private float count;
	//private float rank;
	private boolean camHasReachedStartPosition;
	private boolean firstUpdate;
	
	private int loopPos;
	
	private enum State {
		RIGHT, DOWN, STATICTRANSITION, STATIC
	}
	
	private State state;
	//private TextureAtlas groundTextures;
		
	private Array<MovableEntity> moveWithEntities;
	
	public BlockMakerSpiderBoss() {
		this(new Array<MovableEntity>());
	}
	
	public BlockMakerSpiderBoss(Array<MovableEntity> moveWithEntities) {
		//this.rank = rank;
		loopPos = 0;
		//groundTextures = new TextureAtlas("data/level packfile");
		isActive = false;
		this.moveWithEntities = moveWithEntities;
		blocks = new Array<Block>();
		initBlocks();
		camHasReachedStartPosition = false;
		oldBlocks = new Array<Block>();
		oldBlocksType = new HashMap<Block, Integer>();
		state = State.RIGHT;
		latestBlock = new Block(new Vector2(-40,400), Block.TextureAtlasReference.LEVEL, 0);
		firstUpdate = false;
	}
	
	public Array<Block> getBlocks() {
		Array<Block> output = new Array<Block>();
		output.addAll(blocks);
		output.addAll(oldBlocks);
		return blocks;
	}

	public void initBlocks() {
		float initX = Level2Scenes.SPIDER_BOSS_START + 13f; //find a better way to get this variable
		for (float i = 0; i < 70; i += 1f) {
			blocks.add(new Block(new Vector2(initX + i, 0f), Block.TextureAtlasReference.LEVEL, 0));
			blocks.add(new Block(new Vector2(initX + i, 1f), Block.TextureAtlasReference.LEVEL, 0));
			blocks.add(new Block(new Vector2(initX + i, 2f), Block.TextureAtlasReference.LEVEL, 0));
			blocks.add(new Block(new Vector2(initX + i, 3f), Block.TextureAtlasReference.LEVEL, 2));
		}
			
	}
	
	public void update(float delta, OrthographicCamera cam, float rank) {
		count += delta;
		if (state == State.RIGHT) {
			//Add new blocks
			//if (count > 1 / SPEED) {
			
			if (latestBlock.getPosition().x <= cam.position.x+cam.viewportWidth/2) {
				//System.out.println("Making new block");
				//count = 0;
				loopPos++;
				float spawnX = latestBlock.getPosition().x + Block.SIZE;
				if (loopPos > 0 && loopPos < 13) {
					blocks.add(new Block(new Vector2(spawnX, 0f), Block.TextureAtlasReference.LEVEL, 0));
					blocks.add(new Block(new Vector2(spawnX, 1f), Block.TextureAtlasReference.LEVEL, 0));
					blocks.add(new Block(new Vector2(spawnX, 2f), Block.TextureAtlasReference.LEVEL, 0));
					blocks.add(new Block(new Vector2(spawnX, 3f), Block.TextureAtlasReference.LEVEL, 2));
				}
				if (loopPos > 2 && loopPos < 6) {
					blocks.add(new Block(new Vector2(spawnX, 7f), Block.TextureAtlasReference.LEVEL, 2));
					
				}
				if (loopPos >= 13 && rank < 0.75f) {
					blocks.add(new Block(new Vector2(spawnX, 0f), Block.TextureAtlasReference.LEVEL, 0));
					blocks.add(new Block(new Vector2(spawnX, 1f), Block.TextureAtlasReference.LEVEL, 0));
					blocks.add(new Block(new Vector2(spawnX, 2f), Block.TextureAtlasReference.LEVEL, 0));
					blocks.add(new Block(new Vector2(spawnX, 3f), Block.TextureAtlasReference.LEVEL, 2));
				}
				if (loopPos == 17) {
					loopPos = 0;
				}
				latestBlock = new Block(new Vector2(spawnX, cam.position.y+cam.viewportHeight/2), Block.TextureAtlasReference.LEVEL, 2);
				blocks.add(latestBlock);
				
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
			
			
			
		} else if (state == State.DOWN) {
			//add new blocks
		
			if (latestBlock.getPosition().y >= -2f) {
				//System.out.println("Making new block. x=" +(cam.position.x-cam.viewportWidth/2-Block.SIZE) + " camX="+ cam.position.x + " camviewwith=" + cam.viewportWidth);
				count = 0;
				float spawnY = latestBlock.getPosition().y	- Block.SIZE;	
				blocks.add(new Block(new Vector2(cam.position.x-cam.viewportWidth/2, spawnY), Block.TextureAtlasReference.LEVEL, 0));
				latestBlock = new Block(new Vector2(cam.position.x+cam.viewportWidth/2 - Block.SIZE, spawnY), Block.TextureAtlasReference.LEVEL, 0);
				blocks.add(latestBlock);
			}
			
			
			
			//Move objects in the moveWithEntities
			for (MovableEntity mve: moveWithEntities) {
				mve.getPosition().y += delta * Player.MAX_FALL_VELOCITY;
			}
			
			//Move and remove existing blocks
			for (int i=0; i<blocks.size; i++) {
				Block block = blocks.get(i);
				block.getPosition().y += delta * Player.MAX_FALL_VELOCITY;
				if (block.getPosition().y  > cam.position.y + cam.viewportHeight/2) {
					//System.out.println("Removing Block at " + block.getPosition());
					blocks.removeIndex(i);
					i--;
				}
				
			}
		} else if (state == State.STATICTRANSITION) {
			if (firstUpdate) {
				for (float i = cam.position.x - cam.viewportWidth/2; i < cam.position.x + cam.viewportWidth/2; i+=1f) {
					blocks.add(new Block(new Vector2(i, -1f), Block.TextureAtlasReference.LEVEL, 0));
					blocks.add(new Block(new Vector2(i, -2f), Block.TextureAtlasReference.LEVEL, 0));
					blocks.add(new Block(new Vector2(i, -3f), Block.TextureAtlasReference.LEVEL, 0));
					latestBlock = new Block(new Vector2(i, -4f), Block.TextureAtlasReference.LEVEL, 0);
					blocks.add(latestBlock);
				}
				firstUpdate = false;
			}
			for (Block b: blocks) {
				b.getPosition().y += delta * Player.MAX_FALL_VELOCITY;
			}
			if (latestBlock.getPosition().y >= 0f) {
				state = State.STATIC;
			}
		}
		
		//Move old blocks
		for (Block b: oldBlocks) {
			
			int type = oldBlocksType.get(b);
			float destroySpeed = 14f;
			switch(type) {
			case 0:
				//System.out.println("Found oldBlock at " + b.getPosition());
				b.getPosition().x -= delta *destroySpeed;
				b.getPosition().y += delta* destroySpeed;
				break;
			case 1:
				b.getPosition().x -= delta *destroySpeed * 0.5f;
				b.getPosition().y += delta* destroySpeed * 2f;
				break;
			case 2:
				b.getPosition().x += delta *destroySpeed * 0.5f;
				b.getPosition().y += delta* destroySpeed * 2f;
				break;
			case 3:
				b.getPosition().x += delta *destroySpeed;
				b.getPosition().y += delta* destroySpeed;
				break;
				
			}
			if (b.getPosition().y > cam.position.y + cam.viewportHeight) {
				oldBlocks.removeValue(b, true);
			}
			
		}
		
	}
	
	public void camHasReachedStartPosition() {
		camHasReachedStartPosition = true;
	}
	
	public void startDownward() {
		System.out.println("Start downward");
		oldBlocks.addAll(blocks);
		int type = 0;
		for (Block b: oldBlocks) {
			b.setSolid(false);
			oldBlocksType.put(b, type);
			switch (type) {
			case 0:
				b.setDrawRotation(-45f);
				break;
			case 1:
				b.setDrawRotation(-20f);
				break;
			case 2:
				b.setDrawRotation(20f);
				break;
			case 3:
				b.setDrawRotation(45f);
				break;
			}
			if (++type == 4) {
				type = 0;
			}
		}
		//blocks.clear();
		state = State.DOWN;
		loopPos = 0;
	}
	
	public void startStatic() {
		firstUpdate = true;
		state = State.STATICTRANSITION;
	}
	
	public void staticExplosion(float xPosition) {
		for (float i = xPosition; i < xPosition+7f; i+= 1f) {
			for (float j = 0f; j <= 3f; j += 1f) {
				Block block = new Block(new Vector2(i, j), Block.TextureAtlasReference.LEVEL, 0);
				oldBlocks.add(block);
				blocks.add(block);
			}
		}
		int type = 0;
		for (Block b: oldBlocks) {
			b.setSolid(false);
			oldBlocksType.put(b, type);
			switch (type) {
			case 0:
				b.setDrawRotation(-45f);
				break;
			case 1:
				b.setDrawRotation(-20f);
				break;
			case 2:
				b.setDrawRotation(20f);
				break;
			case 3:
				b.setDrawRotation(45f);
				break;
			}
			if (++type == 4) {
				type = 0;
			}
		}
	}
	
	public void setMoveWithEntites(Array<MovableEntity> moveEntities) {
		this.moveWithEntities = moveEntities;
	}

	
}
