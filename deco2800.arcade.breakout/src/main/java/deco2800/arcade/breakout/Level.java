package deco2800.arcade.breakout;

import java.io.IOException;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import deco2800.arcade.breakout.screens.GameScreen;
/**
 * Sets the individual levels
 * @author Carlie Smits
 *
 */
public class Level {
	
	public Level() {
		
	}
	/**
	 * Read each line of the input file and store it in a string to use in
	 * initialising the brick array
	 * @param inputFileName - the name of the file being accessed
	 * @return - the bricks from the input 
	 * @throws IOException - when the file is in an incorrect format
	 */
	public Brick [] readFile(String inputFileName) throws IOException {
		String fileContents = Gdx.files.classpath(inputFileName).readString();
		return initialiseBrickArray (fileContents);
	}
	/**
	 * Obtains the bricks coordinates and sizing based on the file being
	 * read
	 * @param fileContents - the whole contents of the level file
	 * @return - the bricks from the input
	 * @throws IOException - called when the file is in an incorrect format
	 */
	public Brick [] initialiseBrickArray (String fileContents) 
			throws IOException {
		String[] contentsArr = fileContents.split("\n");
		int numBricks = 0;
		try {
			numBricks = Integer.valueOf(contentsArr[0].trim());
		} catch (Exception e) {
			return null;
		}
		Brick[] bricks = new Brick[numBricks];
		
		for (int i = 1; i < contentsArr.length; i++) {
			String s = contentsArr[i].trim();
			String [] brickDetails = s.split(",");
			if (brickDetails.length == 2) {
				bricks[i-1] = new Brick(Float.parseFloat(brickDetails[0]), 
						Float.parseFloat(brickDetails[1]));
			} else if (brickDetails.length == 4) {
				bricks[i-1] = new Brick(Float.parseFloat(brickDetails[0]), 
						Float.parseFloat(brickDetails[1]), 
						Float.parseFloat(brickDetails[2]), 
						Float.parseFloat(brickDetails[3]));
			} else {
				throw new IOException("File not in correct format. Brick " +
						"should be x,y or x,y,width,height");
			}
		}
		return bricks;
	}
	
	/**
	 * A render method that handles each level and its specific styling
	 * @param bricks - an array of rectangular forms with a given
	 * size and coordinate
	 * @param context - the current game screen
	 * @param b - contains the rendering details
	 */
	public void render(Brick[] bricks, GameScreen context, SpriteBatch b) {
		int index = 0;
		while (index < bricks.length) {
			if (bricks[index].getState()) {
				bricks[index].render(context.getLevel(), b, index);
			}
			index++;
		}
	}

}
