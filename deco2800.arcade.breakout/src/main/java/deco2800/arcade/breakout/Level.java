package deco2800.arcade.breakout;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Level {
	
	private final int SCREENHEIGHT = 720;
	private final int SCREENWIDTH = 1280;
	
	public Level() {
		
	}
	public Brick [] readFile(String inputFileName, Brick[] bricks, 
			GameScreen context) throws IOException {
		BufferedReader input = new BufferedReader(Gdx.files.classpath(inputFileName).reader());
		// Read the number of brick's from the file (first line).
		int numBricks = 0;
		try {
			numBricks = Integer.valueOf(input.readLine().trim());
		} catch (Exception e) {
			input.close();
			// file not correct format
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
			// create new bricks from the input.
		}
		input.close();
		return bricks;
	}

	public void render(Brick[] bricks, int outer, int inner, GameScreen context, 
			ShapeRenderer shapeRenderer, SpriteBatch b) {
		int index = 0;
		while (index < bricks.length) {
			if (bricks[index].getState()) {
				bricks[index].render(context.getLevel(), b, index);
			}
			index++;
		}
//		}
	}

}
