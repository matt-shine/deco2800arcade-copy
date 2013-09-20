package deco2800.arcade.breakout;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Level {
	
	private final int SCREENHEIGHT = 720;
	private final int SCREENWIDTH = 1280;
	
	public Level() {
		
	}
	
	public Brick[] levelOne(Brick[] bricks, GameScreen context) {
		// create 48 Bricks in a rectangle formation
		int index = 0;
		bricks = new Brick[48];
		context.setOuter(6);
		context.setInner(8);
		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 8; j++) {
				bricks[index] = new Brick(j * 125 + 120, SCREENHEIGHT - i
						* 45 - 110);
				index++;
			}
		}
		return bricks;
	}
	
	public Brick[] levelTwo(Brick[] bricks, GameScreen context) {
		int index = 0;
		bricks = new Brick[20];
		context.setOuter(4);
		context.setInner(5);
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 5; j++) {
				bricks[index] = new Brick(j * 125 + index * 10 + 120,
						SCREENHEIGHT - (4 * index) - (i * 40) - 110);
				index++;
		
			}
	
		}
		return bricks;
	}
	
	public Brick[] levelThree(Brick[] bricks, GameScreen context) {
		int index = 0;
		bricks = new Brick[20];
		context.setOuter(4);
		context.setInner(5);
		while (index < 20) {
			int shift = 0;
			double yPos = Math.sin(18 * index);
			if (yPos < 0) {
				shift = -40;
			} else if (yPos > 0) {
				shift = 40;
			}
			bricks[index] = new Brick((float)(640 + 225*Math.cos(18*index)),
					(float)(360 + shift + 225*yPos));
			index++;
		}
		return bricks;
	}
	
	public Brick[] levelFour(Brick[] bricks, GameScreen context) {
		bricks = new Brick[34];
		context.setOuter(17);
		context.setInner(2);
		bricks[0] = new Brick(640,640);
		bricks[1] = new Brick(640,600);
		bricks[2] = new Brick(520,640);
		bricks[3] = new Brick(520,600);
		bricks[4] = new Brick(400,640);
		bricks[5] = new Brick(400,600);
		bricks[6] = new Brick(400,560);
		bricks[7] = new Brick(760,640);
		bricks[8] = new Brick(760,600);
		bricks[9] = new Brick(760,560);
		bricks[10] = new Brick(280,640);
		bricks[11] = new Brick(280,600);
		bricks[12] = new Brick(280,560);
		bricks[13] = new Brick(280,520);
		bricks[14] = new Brick(280,480);
		bricks[15] = new Brick(880,640);
		bricks[16] = new Brick(880,600);
		bricks[17] = new Brick(880,560);
		bricks[18] = new Brick(880,520);
		bricks[19] = new Brick(880,480);
		bricks[20] = new Brick(160,640);
		bricks[21] = new Brick(160,600);
		bricks[22] = new Brick(160,560);
		bricks[23] = new Brick(160,520);
		bricks[24] = new Brick(160,480);
		bricks[25] = new Brick(160,440);
		bricks[26] = new Brick(160,400);
		bricks[27] = new Brick(1000,640);
		bricks[28] = new Brick(1000,600);
		bricks[29] = new Brick(1000,560);
		bricks[30] = new Brick(1000,520);
		bricks[31] = new Brick(1000,480);
		bricks[32] = new Brick(1000, 440);
		bricks[33] = new Brick(1000,400);
		return bricks;
	}
	
	public void render(Brick[] bricks, int outer, int inner, GameScreen context, 
			ShapeRenderer shapeRenderer, SpriteBatch b) {
		int index = 0;
		for (int i=0; i < outer; i++) {
			for (int j = 0; j < inner; j++) {
				if (bricks[index].getState()) {
					bricks[index].render(shapeRenderer, i, context.getLevel(), b, index);
				}
				index++;
			}
		}
	}

}
