package deco2800.arcade.minigolf;

import com.badlogic.gdx.math.*;
import com.badlogic.gdx.utils.*;
/**
 * Class which handles all of the collision detection/interactivity between
 * the ball and the blocks. This class checks for overlapping polygons, which
 * are the block's bounds variable (Block1.bounds), and acts accordingly 
 * depending which block the ball has collided with.
 * 
 * The class has variables in place to prevent continuous detection of the 
 * same block repeatedly. (EXAMPLE: Without these variables, the ball would
 * "shake" between walls. Or teleporters (previously) would stay in an infinite
 * loop of teleporting. 
 *
 */
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
	
	
	public BallController(World world) {
		this.world = world;
		this.ball = world.getBall();
		
		// Adds all blocks that have interactivity with the ball into
		// the array collisionBlocks.
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
		
		// Check if the ball is colliding with any of the block in the
		// collisionBlocks array.
		Polygon ballBounds = ball.getBounds();
		for (Block1 block : collisionBlocks) {
			Block1.FacingDir blockDir = block.getDir();
			Block1.BlockType blockType = block.getType();
			Boolean boundColl = Intersector.overlapConvexPolygons(ballBounds,
					block.getBounds());
			if (boundColl) {
				
				// If Ball collides with a WALL/INVWALL type block, apply the relevant
				// changes. Collision is not allowed two times in a row if the specified
				// axis variable (collisionX/Y) has been set to true.
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
				
				
				// Run code if ball collides with the HOLE block.	
				else if (blockType == Block1.BlockType.HOLE) {
					if (ball.inHole == false) {
						ball.inHole = true;							
					}
				
				// Run code if ball collides with the CORNER block. Reverses
				// both axis of the ball.
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
					
				// Run code if ball collides with the DIAGONAL block. Sets the
				// bounceDiag(AXIS) variable to be true.
				} else if (blockType == Block1.BlockType.DIAGONAL) {
					if ((blockDir == Block1.FacingDir.EAST)
							|| (blockDir == Block1.FacingDir.WEST)) {
						ball.bounceDiagX = true;
					} else {
						ball.bounceDiagY = true;
					}
				
					collisionX = true;
					collisionY = true;
				
			
				}
				
				// These lines of code only run when the specialCol variable
				// is set to false. This is to prevent multiple collision
				// detections of the same block.
				if(!specialCol){					
					 
					// Run code if ball collides with a WATER block. Resets collision
					// variables.
					 if (blockType == Block1.BlockType.WATER){
						
						//ball.position = new Vector2(world.holeStartX, world.holeStartY);
						ball.setPosition(world.holeStartX, world.holeStartY);
						ball.inWater = true;
						
						specialCol = true;
						
						if (collisionX) collisionX = false;						
						if (collisionY) collisionY = false;						
					}
					
					 // Runs this code if the ball collides with a TELEPORTER
					 // block. One-way teleporter moves ball from odd (k) tele
					 // to its even (k+1) counterpart. Also resets all collision
					 // variables but specialCol
					else if (blockType == Block1.BlockType.TELEPORTER){						
						
						if(block.teleNumber%2 != 0){
							destinationTele = (block.teleNumber + 1);							
						}
						
						// Searches for the destination teleporter in the list 
						// of all the teleporter blocks.
						for (Block1 teleBlock : teleBlocks) {
							if (teleBlock.teleNumber == destinationTele && block.teleNumber%2 == 1){
								
								ball.setPosition(teleBlock.getPosition().x, teleBlock.getPosition().y);
							}
						}
						specialCol = true;
						
						if (collisionX) collisionX = false;
						if (collisionY) collisionY = false;
						
						
					}
				
				}

			}
		}
		

		// If the ball collides with an OPEN type block. Reset all
		// collision variables. There is a downside to this. If the 
		// ball does not hit an open block and has to do other collision
		// with another block, collision may not work as intended. 
		// (EXAMPLE: Ball goes through walls.)
		for (Block1 groundBlock : openBlocks) {
			if (Intersector.overlapConvexPolygons(ballBounds,
					groundBlock.getBounds())) {				
				if (collisionX){ 
					collisionX = false;
				}
				if (collisionY){
					collisionY = false;
				}			
				if (specialCol){
					specialCol = false;
				}
				
				
			}
		}
		
	}
	

}

	