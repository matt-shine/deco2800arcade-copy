package deco2800.arcade.minigolf;

import com.badlogic.gdx.math.*;
import com.badlogic.gdx.utils.*;
import java.util.*;

@SuppressWarnings("unused")
public class Block1 {
	
	public enum BlockType {
		CORNER, INVCORNER, INVWALL, WALL, HOLE, OPEN,
		HILL, TELEPORTER, DIAGONAL, WATER,
		CLOSED
	}
	
	public enum FacingDir {
		WEST, NORTH, SOUTH, EAST
	}
	
	public int teleNumber = 0;
	
	static final float SIZE = 15f;	
	
	Vector2 position = new Vector2();
	Polygon bounds;
	
	BlockType type = BlockType.OPEN;
	FacingDir dir = FacingDir.WEST;
	float[] coords;
	
	
	public Block1(Vector2 pos, BlockType blockType, FacingDir dir, int teleNum){
		switch(blockType){
		case HILL:
		case CLOSED:
		case WATER:
			coords = new float[] {0, 0, SIZE, SIZE, SIZE, 0, 0, SIZE};
			this.bounds = new Polygon(coords);
			this.bounds.setPosition(pos.x, pos.y);
			this.position = pos;
			this.dir = dir; 
			this.type = blockType;
			break;
				
		case OPEN:
			coords = new float[] {SIZE/5, SIZE/5, SIZE/5, 4*(SIZE/5), 4*(SIZE/5),
					SIZE/5, 4*(SIZE/5),4*(SIZE/5)};
			this.bounds = new Polygon(coords);
			this.bounds.setPosition(pos.x, pos.y);
			this.position = pos;
			this.dir = dir; 
			this.type = blockType;
			break;
		
		case TELEPORTER:
			this.teleNumber = teleNum;
		case HOLE:
			coords = new float[] { 0, 0, SIZE / 4, 0, 0, SIZE / 4, SIZE / 4,
					SIZE / 4 };
			this.bounds = new Polygon(coords);
			this.bounds.setPosition(pos.x, pos.y);
			//this.bounds.setScale(1.5f, 1.5f);
			this.position = pos;
			this.dir = dir;
			this.type = blockType;
			break;
				
		case CORNER:
		case INVCORNER:
			coords = new float[] {0, 0, SIZE, SIZE, SIZE, 0, 0, SIZE};
			this.bounds = new Polygon(coords);
			this.bounds.setPosition(pos.x, pos.y);
			this.position = pos;
			this.dir = dir;
			this.type = blockType;
			break;	
				
		case WALL:
			switch(dir){
			case SOUTH:
				coords = new float[] {0,0, SIZE, 0, SIZE, 
						3*(SIZE/4), 0, 3*(SIZE/4)};					
				break;
			case NORTH:
				coords = new float[] {0, SIZE, 0, SIZE/4, SIZE,
						SIZE/4, SIZE, SIZE};					
				break;
			case WEST:
				coords = new float[] {0, 0, 0, SIZE, 3*(SIZE/4), 
						SIZE, 3*(SIZE/4), 0};
				break;					
			case EAST:
				coords = new float[] {SIZE, 0, SIZE/4, 0, SIZE/4,
						SIZE, SIZE, SIZE};
				break;
			default:
				break;
			}
			this.bounds = new Polygon(coords);
			this.bounds.setPosition(pos.x, pos.y);
			this.position = pos;
			this.dir = dir; 
			this.type = blockType;
			break;	
		case INVWALL:
			switch(dir){
			case SOUTH:
				coords = new float[] {SIZE/4,0, SIZE/4, SIZE/6, SIZE, 
						SIZE/6, SIZE, 0};					
				break;
			case NORTH:
				coords = new float[] {SIZE/4, SIZE, SIZE/4, 5*(SIZE/6),  SIZE,
						5*(SIZE/6), SIZE, SIZE};					
				break;
			case WEST:
				coords = new float[] {0, 0, SIZE/6, 0, 0, 
						3*(SIZE/4), SIZE/6, 3*(SIZE/4)};
				break;					
			case EAST:
				coords = new float[] {SIZE, 0, 5*(SIZE/6), 0, 5*(SIZE/6), 3*(SIZE/4),
						SIZE, 3*(SIZE/4)};
				break;
			default:
				break;
			}
			this.bounds = new Polygon(coords);
			this.bounds.setPosition(pos.x, pos.y);
			this.position = pos;
			this.dir = dir; 
			this.type = blockType;
			break;
		case DIAGONAL:
			switch(dir){
			case SOUTH:
				coords = new float[] {SIZE, SIZE, (SIZE/4), SIZE, SIZE, 
						SIZE/4};					
				break;
			case NORTH:
				coords = new float[] {0, 3*(SIZE/4), 3*(SIZE/4), 0, 0,
						0};					
				break;
			case EAST:
				coords = new float[] {0, SIZE/4, 0, SIZE, 3*(SIZE/4), 
						SIZE};
				break;					
			case WEST:
				coords = new float[] {SIZE/4, 0, SIZE, 0, SIZE, 3*(SIZE/4)};
				break;
			default:
				break;
			}
			this.bounds = new Polygon(coords);
			this.bounds.setPosition(pos.x, pos.y);
			this.position = pos;
			this.dir = dir; 
			this.type = blockType;
			break;
		default:
			break;
		}
		this.position = pos;
		this.bounds.setPosition(pos.x, pos.y);
		
	} 
	
	public Vector2 getPosition() {
		return this.position;
	}

	public Polygon getBounds() {
		return this.bounds;
	}
	
	public BlockType getType() {
		return this.type;
	}

	public FacingDir getDir() {
		return this.dir;
	}
	
}
