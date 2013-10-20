package deco2800.arcade.minigolf;

import java.net.URL;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.utils.Array; 

import deco2800.arcade.minigolf.Block1.BlockType;
import deco2800.arcade.minigolf.Block1.FacingDir;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.Scanner;


/* Gets a level and reads characters from its text file. 
 * Places blocks into different arrays based on the character in the text file. 
 * The arrays hold the object type and positions to be rendered on screen. 
 */

public class World {

	// arrays which hold all blocks of a certain type to be drawn
	Array<Block1> wallArray = new Array<Block1>();
	Array<Block1> invWallArray = new Array<Block1>();
	Array<Block1> capBlockArray = new Array<Block1>();
	Array<Block1> groundArray = new Array<Block1>(); 
	Array<Block1> cornerArray = new Array<Block1>();
	Array<Block1> holeArray = new Array<Block1>();
	Array<Block1> waterArray = new Array<Block1>();
	Array<Block1> teleArray = new Array<Block1>();
	Array<Block1> diagArray = new Array<Block1>();
	
	// store the x and y start position of the ball
	public int holeStartX;
	public int holeStartY;
	
	Ball ball;  
	
	/* Get the arrays which hold different block types */
	public Array<Block1> getInvWallBlocks() {
		return invWallArray;
	}
	public Array<Block1> getCapBlocks(){
		return capBlockArray;
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
	
	/* get the ball instance that is placed within each level */
	public Ball getBall() { 
		return ball; 
	}
	
	/* construct level/hole based upon @param */
	public World(int state) throws Exception {
	URL path = this.getClass().getResource("/");
	
	String resource = path.toString().replace(".arcade/build/classes/main/", 
				".arcade.minigolf/src/main/").replace("file:", "") + 
				"resources/Levels/level" + state + ".txt" ;
		
	createHole(resource);
	
		
	}
	/* clears all arrays before they are used */
	private void clearArrays(){
		cornerArray = new Array<Block1>();
		wallArray = new Array<Block1>();
		groundArray = new Array<Block1>(); 
		invWallArray = new Array<Block1>();
		teleArray = new Array<Block1>();
		diagArray = new Array<Block1>();
		capBlockArray = new Array<Block1>();
		holeArray = new Array<Block1>();
		waterArray = new Array<Block1>();
	}
	
	/* Create the hole by reading text file specified in @param */
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
			//check that the width and height are correct values
			scan.close();
			if(widthNum <= 0 || heightNum <= 0){
				throw new InvalidNumberException("width and height must be greater than 0");  
			}
			int startWidth = CreateXStartPos(widthNum);
			int startHeight = CreateYStartPos(heightNum);
			
			FileReader input = new FileReader(file); 
			a = 0;
			
			while(currentChar != (char)-1){ //while not end of file
				//ignore first line since we have dimensions
				while (a < 7){ 
					currentChar = (char)input.read();
					a++;
					//check that the first line only holds 2 values
					if(a == 7 && currentChar != '\n'){
						throw new InvalidNumberException
						("only 2 values (width height) can be on the first line eg (10 20\n)");
					}
				}
				
				//loop through file incrementing by the block size, find currentChar and replace with block
				for(int i = startHeight; i>=(90); i-=Block1.SIZE){ //height or y
					for(int j = startWidth; j <= ((widthNum * Block1.SIZE) + (15+startWidth)); j+=Block1.SIZE){ //width or x
						currentChar = (char)input.read();//get current character
						//check if current character is:
						checkIfWall(currentChar, j, i);						
						checkIfCorner(currentChar, j, i);						
						checkIfDiag(currentChar, j, i);
						checkIfCapBlock(currentChar, j, i);						
						checkIfTele(currentChar, j, i);						
						checkForRest(currentChar, j, i);
						
						//if Ball character, set startPos and add ground below it
						if(currentChar == 'B'){
							 ball = new Ball(new Vector2(j,i));
							 holeStartX = j;
							 holeStartY = i;
							 groundArray.add(new Block1(new Vector2(j,i), BlockType.OPEN, FacingDir.NORTH,0));
						 }						
					}
				}
				
				input.close();
			}	
			//input.close();
			//scan.close();
			
		//catch any exceptions found while reading the file
		}catch (FileNotFoundException e) {
			System.err.println("File unable to be located");
		    e.printStackTrace();
		} catch (IOException e) {
		    e.printStackTrace();
		} catch (NumberFormatException e) {
	           System.err.println("This is not a number");
	           System.err.println(e.getMessage());
	    } catch (Exception e){
	    	System.err.println("An exception has occured while opening the map file");
	    	e.printStackTrace();
	    }
	}
	
	/* takes the width of the map, returns startX based upon it*/
	private int CreateXStartPos(int width){
		int xPos = 0;
		if(width >= 1 && width <= 10 )
			xPos = 500; 
		if(width >= 11 && width <= 20)
			xPos = 450;
		if(width >= 21 && width <= 30)
			xPos = 400;
		if(width >= 31 && width <= 40)
			xPos = 350;
		if(width >= 41 && width <= 50)
			xPos = 300;
		if(width >= 51 && width <= 100)
			xPos = 250;
		
		return xPos;
	}
	/* takes the height of the map, returns startY based upon it*/
	private int CreateYStartPos(int height){
		int yPos = 0;
		if(height >= 1 && height <= 10 )
			yPos = 450; 
		if(height >= 11 && height <= 20)
			yPos = 500;
		if(height >= 21 && height <= 30)
			yPos = 550;
		if(height >= 31 && height <= 40)
			yPos = 600;
		if(height >= 41 && height <= 60)
			yPos = 650;
		
		return yPos;
	}
	
	private void checkIfWall(char currentChar, int j, int i){
		if(currentChar == 'N')//north wall
			wallArray.add(new Block1(new Vector2(j,i), BlockType.WALL, FacingDir.NORTH,0));
		 else if(currentChar == 'S')//south wall
			wallArray.add(new Block1(new Vector2(j,i), BlockType.WALL, FacingDir.SOUTH,0));
		 else if(currentChar == 'E')//east wall
			wallArray.add(new Block1(new Vector2(j,i), BlockType.WALL, FacingDir.EAST,0));
		 else if(currentChar == 'W')//west wall
			wallArray.add(new Block1(new Vector2(j,i), BlockType.WALL, FacingDir.WEST,0));
	}
	
	private void checkIfCorner(char currentChar, int j, int i){
		if(currentChar == 'n')//north corner
			cornerArray.add(new Block1(new Vector2(j,i), BlockType.CORNER, FacingDir.NORTH,0));
		 else if(currentChar == 's')//south corner
			cornerArray.add(new Block1(new Vector2(j,i), BlockType.CORNER, FacingDir.SOUTH,0));
		 else if(currentChar == 'e')//east corner
			cornerArray.add(new Block1(new Vector2(j,i), BlockType.CORNER, FacingDir.EAST,0));
		 else if(currentChar == 'w')//west corner
			cornerArray.add(new Block1(new Vector2(j,i), BlockType.CORNER, FacingDir.WEST,0));
	}
	
	private void checkIfDiag(char currentChar, int j, int i){
		 if(currentChar == 'L')
			 diagArray.add(new Block1(new Vector2(j,i), BlockType.DIAGONAL, FacingDir.SOUTH,0));
		 else if(currentChar == 'J')
			 diagArray.add(new Block1(new Vector2(j,i), BlockType.DIAGONAL, FacingDir.EAST,0));
		 else if(currentChar == 'r')
			 diagArray.add(new Block1(new Vector2(j,i), BlockType.DIAGONAL, FacingDir.WEST,0));
		 else if(currentChar == 'T')
			 diagArray.add(new Block1(new Vector2(j,i), BlockType.DIAGONAL, FacingDir.NORTH,0));
	}
	
	private void checkIfCapBlock(char currentChar, int j, int i){
		 if(currentChar == '-')
			 invWallArray.add(new Block1(new Vector2(j,i), BlockType.INVWALL, FacingDir.NORTH,0));
		 else if(currentChar == '|')
			 invWallArray.add(new Block1(new Vector2(j,i), BlockType.INVWALL, FacingDir.EAST,0));
		 else if(currentChar == 'l')
			 invWallArray.add(new Block1(new Vector2(j,i), BlockType.INVWALL, FacingDir.WEST,0));
		 else if(currentChar == '_')
			 invWallArray.add(new Block1(new Vector2(j,i), BlockType.INVWALL, FacingDir.SOUTH,0));
		 else if(currentChar == ')')
			 capBlockArray.add(new Block1(new Vector2(j,i), BlockType.INVWALL, FacingDir.WEST,0));
		 else if(currentChar == '(')
			 capBlockArray.add(new Block1(new Vector2(j,i), BlockType.INVWALL, FacingDir.EAST,0));
		 else if(currentChar == '~')
			 capBlockArray.add(new Block1(new Vector2(j,i), BlockType.INVWALL, FacingDir.NORTH,0));
		 else if(currentChar == '^')
			 capBlockArray.add(new Block1(new Vector2(j,i), BlockType.INVWALL, FacingDir.SOUTH,0));
	}
	
	private void checkIfTele(char currentChar, int j, int i){
		 if(currentChar == '1')
			 teleArray.add(new Block1(new Vector2(j,i), BlockType.TELEPORTER, FacingDir.NORTH,1));
		 else if(currentChar == '2')
			 teleArray.add(new Block1(new Vector2(j,i), BlockType.TELEPORTER, FacingDir.NORTH,2));
		 else if(currentChar == '3')
			 teleArray.add(new Block1(new Vector2(j,i), BlockType.TELEPORTER, FacingDir.NORTH,3));
		 else if(currentChar == '4')
			 teleArray.add(new Block1(new Vector2(j,i), BlockType.TELEPORTER, FacingDir.NORTH,4));
		 else if(currentChar == '5'){
			 teleArray.add(new Block1(new Vector2(j,i), BlockType.TELEPORTER, FacingDir.NORTH,5));
		 }
		 else if(currentChar == '6'){
			 teleArray.add(new Block1(new Vector2(j,i), BlockType.TELEPORTER, FacingDir.NORTH,6));
		 }
	}
	
	private void checkForRest(char currentChar, int j, int i){
		if(currentChar == 'V')
			waterArray.add(new Block1(new Vector2(j,i), BlockType.WATER, FacingDir.NORTH,0));
		else if(currentChar == '/')//ground
			groundArray.add(new Block1(new Vector2(j,i), BlockType.OPEN, FacingDir.NORTH,0));
		else if(currentChar == 'O')//hole
			holeArray.add(new Block1(new Vector2(j,i), BlockType.HOLE, FacingDir.NORTH,0));
	}
	
	/* An exception indicating that an Invalid number has been given in the map */
	@SuppressWarnings("serial")
	public class InvalidNumberException extends RuntimeException {

		   public InvalidNumberException(){
		        super();
		    }
			
		    public InvalidNumberException(String s){
		        super(s);
		    }

	}
}