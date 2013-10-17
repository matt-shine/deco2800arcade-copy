package deco2800.arcade.minigolf;

import com.badlogic.gdx.math.*;
import com.badlogic.gdx.utils.Array; 

import deco2800.arcade.minigolf.Block1.BlockType;
import deco2800.arcade.minigolf.Block1.FacingDir;
import java.net.URL;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;



/* Holds the object positions to be rendered on screen */

public class World{

	// arrays which hold all blocks of a certain type
	Array<Block1> wallArray = new Array<Block1>();
	Array<Block1> invWallArray = new Array<Block1>();
	Array<Block1> groundArray = new Array<Block1>(); 
	Array<Block1> cornerArray = new Array<Block1>();
	Array<Block1> hillArray = new Array<Block1>();
	Array<Block1> holeArray = new Array<Block1>();
	Array<Block1> waterArray = new Array<Block1>();
	Array<Block1> teleArray = new Array<Block1>();
	Array<Block1> diagArray = new Array<Block1>();
	
	int holeStartX;
	int holeStartY;
	Ball ball;  
	
	/* arrays which hold different block types */
	public Array<Block1> getInvWallBlocks() {
		return invWallArray;
	}
	
	public Array<Block1> getHillBlocks() {
		return hillArray;
	}
	
	public Array<Block1> getWallBlocks() {
		return wallArray;
	}
	public Array<Block1> getGroundBlocks() {
		return groundArray; 
	}
	public Array<Block1> getCornerBlocks() {
		return cornerArray;
	}
	public Array<Block1> getHoleBlock() {
		return holeArray;
	}
	public Array<Block1> getDiagBlocks(){
		return diagArray;
	}
	public Array<Block1> getWaterBlocks() {
		return waterArray;
	}
	public Array<Block1> getTeleBlocks() {
		return teleArray;
	}
	
	public Ball getBall() { 
		return ball; 
	}
	
	/* construct level/hole based upon @param */
	public World(int state) throws Exception {
	URL path = this.getClass().getResource("/");
	
	String resource = path.toString().replace(".arcade/build/classes/main/", 
				".arcade.minigolf/src/main/").replace("file:", "") + 
				"resources/Levels/level" + state + ".txt" ;
		System.out.println(resource);
		if(state == 1) {
			System.out.println("level 1");
			createHole(resource);  
		}
		if(state == 2){
			System.out.println("level 2");
			createHole(resource);
		}
		if(state == 3){
			System.out.println("level 3");
			createHole(resource);
		}
		if(state == 4){
			System.out.println("level 4");
			createHole(resource);
		}
		if(state == 5){
			System.out.println("level 5");
			createHole(resource);
		}
		if(state == 6){
			System.out.println("level 6");
			createHole(resource);
		}
		if(state == 7){
			System.out.println("level 7");
			createHole(resource);
		}
	}
	/* clear all arrays before they are used */
	private void clearArrays(){
		cornerArray = new Array<Block1>();
		wallArray = new Array<Block1>();
		groundArray = new Array<Block1>(); 
		invWallArray = new Array<Block1>();
		teleArray = new Array<Block1>();
		diagArray = new Array<Block1>();
		hillArray = new Array<Block1>();
		holeArray = new Array<Block1>();
		waterArray = new Array<Block1>();
	}
	
	private void createHole(String text) throws Exception {
		
		clearArrays();//make sure all arrays are empty
		File file = new File(text);
		int widthNum;
		int heightNum;
		char currentChar = 0;		
		try{
			//store the first 2 integer values found
			Scanner scan = new Scanner(file);
			int[] listVar = new int[3]; 
			int a = 0; 
			while(a < 2){
				listVar[a++] = scan.nextInt();
			}
			widthNum = listVar[0]; //width
			heightNum = listVar[1];//height
			
			//check width and height values are positive and greater than 0
			//if from size 1 - 10 have start pos...., if size 11 - 20 have start pos...
			
			FileReader input = new FileReader(file); 
			a = 0;
			while(currentChar != (char)-1){ //while not end of file
				//ignore first line since we have dimensions
				while (a < 5){ 
					currentChar = (char)input.read();
					a++;
				}
				//loop through file incrementing by the block size
				for(int i = 600; i>=(90); i-=Block1.SIZE){ //height or y
					for(int j = 120; j <= ((widthNum * Block1.SIZE) + 135); j+=Block1.SIZE){ //width or x
						currentChar = (char)input.read();//get current character
						//check if wall
						if(currentChar == 'N')//north wall
							wallArray.add(new Block1(new Vector2(j,i), BlockType.WALL, FacingDir.NORTH,0));
						 else if(currentChar == 'S')//south wall
							wallArray.add(new Block1(new Vector2(j,i), BlockType.WALL, FacingDir.SOUTH,0));
						 else if(currentChar == 'E')//east wall
							wallArray.add(new Block1(new Vector2(j,i), BlockType.WALL, FacingDir.EAST,0));
						 else if(currentChar == 'W')//west wall
							wallArray.add(new Block1(new Vector2(j,i), BlockType.WALL, FacingDir.WEST,0));
						//check if corner
						 else if(currentChar == 'n')//north corner
							cornerArray.add(new Block1(new Vector2(j,i), BlockType.CORNER, FacingDir.NORTH,0));
						 else if(currentChar == 's')//south corner
							cornerArray.add(new Block1(new Vector2(j,i), BlockType.CORNER, FacingDir.SOUTH,0));
						 else if(currentChar == 'e')//east corner
							cornerArray.add(new Block1(new Vector2(j,i), BlockType.CORNER, FacingDir.EAST,0));
						 else if(currentChar == 'w')//west corner
							cornerArray.add(new Block1(new Vector2(j,i), BlockType.CORNER, FacingDir.WEST,0));
						//check if diag corner 
						 else if(currentChar == 'L')
							 diagArray.add(new Block1(new Vector2(j,i), BlockType.DIAGONAL, FacingDir.SOUTH,0));
						 else if(currentChar == 'J')
							 diagArray.add(new Block1(new Vector2(j,i), BlockType.DIAGONAL, FacingDir.EAST,0));
						 else if(currentChar == 'r')
							 diagArray.add(new Block1(new Vector2(j,i), BlockType.DIAGONAL, FacingDir.WEST,0));
						 else if(currentChar == 'T')
							 diagArray.add(new Block1(new Vector2(j,i), BlockType.DIAGONAL, FacingDir.NORTH,0));
						//check if water
						 else if(currentChar == 'V')
							 waterArray.add(new Block1(new Vector2(j,i), BlockType.WATER, FacingDir.NORTH,0));
						 else if(currentChar == '/')//ground
							groundArray.add(new Block1(new Vector2(j,i), BlockType.OPEN, FacingDir.NORTH,0));
						 else if(currentChar == 'O')//hole
							 holeArray.add(new Block1(new Vector2(j,i), BlockType.HOLE, FacingDir.NORTH,0));
						 else if(currentChar == 'B'){//ball, set ball and add ground
							 ball = new Ball(new Vector2(j,i));
							 holeStartX = j;
							 holeStartY = i;
							 groundArray.add(new Block1(new Vector2(j,i), BlockType.OPEN, FacingDir.NORTH,0));
						 }						
					}
				}								
			}	
			//to-do - check if width and height match given values. check for illegal chars
			input.close();
		}catch (FileNotFoundException e) {
		    e.printStackTrace();
		} catch (IOException e) {
		    e.printStackTrace();
		} catch (NumberFormatException e) {
	           System.out.println("This is not a number");
	           System.out.println(e.getMessage());
	    } catch (Exception e){
	    	e.printStackTrace();
	    }
	}
}
