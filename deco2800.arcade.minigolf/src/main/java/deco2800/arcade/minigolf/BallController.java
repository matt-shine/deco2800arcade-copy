package deco2800.arcade.minigolf;

import com.badlogic.gdx.math.*;
import com.badlogic.gdx.utils.*;

public class BallController {

	private World world;
	private Ball ball;
	private Array<Block1> collisionBlocks;
	private Array<Block1> openBlocks;
	private Array<Block1> teleBlocks;
	private Boolean collisionX = false;
	private Boolean collisionY = false;
	private Boolean specialCol = false;
	private int destinationTele;
	private float vectX, vectY;
	
	public BallController(World world) {
		this.world = world;
		this.ball = world.getBall();
		this.collisionBlocks = world.getWallBlocks();
		this.collisionBlocks.addAll(world.getHoleBlock());
		this.collisionBlocks.addAll(world.getInvWallBlocks());
		this.collisionBlocks.addAll(world.getCornerBlocks());
		this.collisionBlocks.addAll(world.getCapBlocks());
		this.collisionBlocks.addAll(world.getWaterBlocks());
		this.collisionBlocks.addAll(world.getTeleBlocks());
		this.collisionBlocks.addAll(world.getDiagBlocks());
		this.teleBlocks = world.getTeleBlocks();
		this.openBlocks = world.getGroundBlocks();		
		
	}

	public void update() {
		collisionDetect();
	}

	public void collisionDetect() {
		
		Polygon ballBounds = ball.getBounds();
		for (Block1 block : collisionBlocks) {
			Block1.FacingDir blockDir = block.getDir();
			Block1.BlockType blockType = block.getType();
			Boolean boundColl = Intersector.overlapConvexPolygons(ballBounds,
					block.getBounds());
			if (boundColl) {				
					
				if (blockType == Block1.BlockType.WALL || blockType == Block1.BlockType.INVWALL) {
						
					if ((blockDir == Block1.FacingDir.EAST)
							|| (blockDir == Block1.FacingDir.WEST)) {
						if(!collisionX){
							ball.bounceX = ball.bounceX == true ? false : true;
							collisionX = true;
						}
					} else {
						if(!collisionY){
							ball.bounceY = ball.bounceY == true ? false : true;
							collisionY = true;
						}
					}					
				}				
				
					
				else if (blockType == Block1.BlockType.HOLE) {
					if (ball.inHole == false) {
						ball.inHole = true;							
					}
					
				} else if (blockType == Block1.BlockType.CORNER) {
					if(!collisionX){
						ball.bounceX = ball.bounceX == true ? false : true;
						collisionX = true;
					}
					if(!collisionY){
						ball.bounceY = ball.bounceY == true ? false : true;
						collisionY = true;
					}
					
					collisionX = true;
					collisionY = true;
					
				
				} else if (blockType == Block1.BlockType.DIAGONAL) {
					System.out.println("diag");
					if ((blockDir == Block1.FacingDir.EAST)
							|| (blockDir == Block1.FacingDir.WEST)) {
						ball.bounceDiagX = true;
					} else {
						ball.bounceDiagY = true;
					}
				
					collisionX = true;
					collisionY = true;
				
			
				}
				
				if(!specialCol){
					 if (blockType == Block1.BlockType.HILL){
						
						
						if ((blockDir == Block1.FacingDir.EAST)) {
							ball.hillX += 1.5f;						
							
						} else if ((blockDir == Block1.FacingDir.NORTH)){
							
							ball.hillY += 1.5f;
							
						} else if ((blockDir == Block1.FacingDir.WEST)){
							ball.hillX -= 1.5f;
							
						} else if ((blockDir == Block1.FacingDir.SOUTH)){
							ball.hillY -= 1.5f;							
						} 
						
						if (collisionX) collisionX = false;
						if (collisionY) collisionY = false;
						
					 }		
					 
					
					else if (blockType == Block1.BlockType.WATER){
						ball.setPosition(world.holeStartX, world.holeStartY);
						ball.inWater = true;
						
						specialCol = true;
						
						if (collisionX) collisionX = false;						
						if (collisionY) collisionY = false;
						
					}
					
					else if (blockType == Block1.BlockType.TELEPORTER){							
						if(block.teleNumber%2 == 1){
							destinationTele = (block.teleNumber + 1); 
							
							for (Block1 teleBlock : teleBlocks) {
								if (teleBlock.teleNumber == destinationTele){
									ball.setPosition(teleBlock.getPosition().x, teleBlock.getPosition().y);
								}
							}
						} 
//						for (Block1 teleBlock : teleBlocks) {
//							if (teleBlock.teleNumber == destinationTele){
//								//ball.position = new Vector2(teleBlock.getPosition().x, teleBlock.getPosition().y);
//								ball.setPosition(teleBlock.getPosition().x, teleBlock.getPosition().y);
//							}
//						}
						specialCol = true;
						
						if (collisionX) collisionX = false;
						if (collisionY) collisionY = false;
						
						
					}
				
				}

			}
		}		

		
		for (Block1 groundBlock : openBlocks) {
			if (Intersector.overlapConvexPolygons(ballBounds,
					groundBlock.getBounds())) {				
				if (collisionX) collisionX = false;
				if (collisionY) collisionY = false;
			
				if (specialCol) specialCol = false;
				
				
			}
		}
		
	}
	

}	
	