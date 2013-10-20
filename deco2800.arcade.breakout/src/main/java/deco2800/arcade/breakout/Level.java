package deco2800.arcade.breakout;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import deco2800.arcade.breakout.screens.GameScreen;
/**
 * Sets the individual levels
 * @author Carlie Smits
 *
 */
public class Level {
	
	//private static final int SCREENHEIGHT = 720;
	//private static final int SCREENWIDTH = 1280;
	
	public Level() {
		
	}
	/**
	 * Obtains the bricks coordinates and sizing based on the file being
	 * read
	 * @param inputFileName - the name of the file being accessed
	 * @param bricks - an array of rectangular forms with a given
	 * size and coordinate
	 * @param context - the current game screen
	 * @return - the bricks from the input
	 * @throws IOException - called when the file is in an incorrect format
	 */
	public Brick [] readFile(String inputFileName, Brick[] bricks, 
			GameScreen context) throws IOException {
		BufferedReader input = new BufferedReader(Gdx.files.classpath(inputFileName).reader());
		int numBricks = 0;
		try {
			numBricks = Integer.valueOf(input.readLine().trim());
		} catch (Exception e) {
			input.close();
		}
		bricks = new Brick[numBricks];
		
		for (int i = 0; i < numBricks; i++) {
			String s = input.readLine();
			String [] brickDetails = s.split(",");
			if (brickDetails.length == 2) {
				bricks[i] = new Brick(Float.parseFloat(brickDetails[0]), 
						Float.parseFloat(brickDetails[1]));
			} else if (brickDetails.length == 4) {
				bricks[i] = new Brick(Float.parseFloat(brickDetails[0]), 
						Float.parseFloat(brickDetails[1]), Float.parseFloat(brickDetails[2]), 
						Float.parseFloat(brickDetails[3]));
			} else {
				input.close();
				throw new IOException("File not in correct format. Brick should be x,y or x,y,width,height");
			}
		}
		input.close();
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
