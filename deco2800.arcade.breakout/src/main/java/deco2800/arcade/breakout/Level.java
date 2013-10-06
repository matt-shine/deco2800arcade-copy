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
//		int index = 0;
//		bricks = new Brick[20];
//		context.setOuter(4);
//		context.setInner(5);
//		for (int i = 0; i < 4; i++) {
//			for (int j = 0; j < 5; j++) {
//				bricks[index] = new Brick(j * 125 + index * 10 + 120,
//						SCREENHEIGHT - (4 * index) - (i * 40) - 110);
//				index++;
//		
//			}
//	
//		}
		bricks = new Brick[44];
		context.setOuter(44);
		context.setInner(1);
		//0
		bricks[0] = new Brick(580,640);
		bricks[1] = new Brick(460,640);
		bricks[2] = new Brick(340,640);
		bricks[3] = new Brick(220,640);
		bricks[4] = new Brick(700,640);
		bricks[5] = new Brick(820,640);
		bricks[6] = new Brick(940,640);
		bricks[7] = new Brick(220,400);
		bricks[8] = new Brick(580,400);
		bricks[9] = new Brick(940,400);
		
		//1
		bricks[10] = new Brick(220,600);
		bricks[11] = new Brick(580,600);
		bricks[12] = new Brick(940,600);
		bricks[13] = new Brick (220,360);
		bricks[14] = new Brick (580,360);
		bricks[15] = new Brick (940,360);
		//2
		bricks[16] = new Brick(220,560);
		bricks[17] = new Brick(580,560);
		bricks[18] = new Brick(940,560);
		bricks[19] = new Brick(220,320);
		bricks[20] = new Brick(580,320);
		bricks[21] = new Brick(940,320);
		//3
		bricks[22] =  new Brick(220,520);
		bricks[23] =  new Brick(580,520);
		bricks[24] =  new Brick(940,520);
		bricks[25] = new Brick(220,280);
		bricks[26] = new Brick(580,280);
		bricks[27] = new Brick(940,280);
		//4
		bricks[28] = new Brick(220,480);
		bricks[29] = new Brick(580,480);
		bricks[30] = new Brick(940,480);
		bricks[31] = new Brick(220,240);
		bricks[32] = new Brick(580,240);
		bricks[33] = new Brick(940,240);
		//5
		bricks[34] = new Brick(220,440);
		bricks[35] = new Brick(580,440);
		bricks[36] = new Brick(940,440);
		bricks[37] = new Brick(220,200);
		bricks[38] = new Brick(580,200);
		bricks[39] = new Brick(940,200);
		bricks[40] = new Brick(460,200);
		bricks[41] = new Brick(340,200);
		bricks[42] = new Brick(700,200);
		bricks[43] = new Brick(820,200);

		return bricks;
	}
	
	public Brick[] levelThree(Brick[] bricks, GameScreen context) {
		//int index = 0;
		bricks = new Brick[52];
		context.setOuter(52);
		context.setInner(1);
		bricks[0] = new Brick(640,640,80,40);
		bricks[1] = new Brick(600,600,80,40);
		bricks[2] = new Brick(560,560,80,40);
		bricks[3] = new Brick(520,520,80,40);
		bricks[4] = new Brick(480,480,80,40);
		bricks[5] = new Brick(320,480,80,40);
		bricks[6] = new Brick(280,520,80,40);
		bricks[7] = new Brick(360,520,80,40);
		bricks[8] = new Brick(320,560,80,40);
		
		bricks[9] = new Brick(440,440,80,40);
		bricks[10] = new Brick(480,400,80,40);
		bricks[11] = new Brick(520,360,80,40);
		bricks[12] = new Brick(560,320,80,40);
		bricks[13] = new Brick(600,280,80,40);
		bricks[14] = new Brick(320,400,80,40);
		bricks[15] = new Brick(280,360,80,40);
		bricks[16] = new Brick(360,360,80,40);
		bricks[17] = new Brick(320,320,80,40);
		
		bricks[18] = new Brick(640,240,80,40);
		bricks[19] = new Brick(680,280,80,40);
		bricks[20] = new Brick(720,320,80,40);
		bricks[21] = new Brick(760,360,80,40);
		bricks[22] = new Brick(800,400,80,40);
		bricks[23] = new Brick(960,320,80,40);
		bricks[24] = new Brick(920,360,80,40);
		bricks[25] = new Brick(1000,360,80,40);
		bricks[26] = new Brick(960,400,80,40);
		
		bricks[27] = new Brick(840,440,80,40);
		bricks[28] = new Brick(800,480,80,40);
		bricks[29] = new Brick(760,520,80,40);
		bricks[30] = new Brick(720,560,80,40);
		bricks[31] = new Brick(680,600,80,40);
		bricks[32] = new Brick(960,480,80,40);
		bricks[33] = new Brick(920,520,80,40);
		bricks[34] = new Brick(1000,520,80,40);
		bricks[35] = new Brick(960,560,80,40);
		
		bricks[36] = new Brick(640,560,80,40);
		bricks[37] = new Brick(600,520,80,40);
		bricks[38] = new Brick(680,520,80,40);
		bricks[39] = new Brick(560,480,80,40);
		bricks[40] = new Brick(720,480,80,40);
		bricks[41] = new Brick(520,440,80,40);
		bricks[42] = new Brick(760,440,80,40);
		bricks[43] = new Brick(560,400,80,40);
		bricks[44] = new Brick(720,400,80,40);
		bricks[45] = new Brick(600,360,80,40);
		bricks[46] = new Brick(680,360,80,40);
		bricks[47] = new Brick(640,320,80,40);
		
		bricks[48] = new Brick(640,480,80,40);
		bricks[49] = new Brick(600,440,80,40);
		bricks[50] = new Brick(680,440,80,40);
		bricks[51] = new Brick(640,400,80,40);
		
//		while (index < 20) {
//			int shift = 0;
//			double yPos = Math.sin(18 * index);
//			if (yPos < 0) {
//				shift = -40;
//			} else if (yPos > 0) {
//				shift = 40;
//			}
//			bricks[index] = new Brick((float)(640 + 225*Math.cos(18*index)),
//					(float)(360 + shift + 225*yPos));
//			index++;
//		}
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
	public Brick[] levelFive(Brick[] bricks, GameScreen context) {
		bricks = new Brick[34];
		context.setOuter(17);
		context.setInner(2);
		bricks[0] = new Brick(640,400);
		bricks[1] = new Brick(640,440);
		bricks[2] = new Brick(520,400);
		bricks[3] = new Brick(520,440);
		bricks[4] = new Brick(760,400);
		bricks[5] = new Brick(760,440);
		bricks[6] = new Brick(760,480);
		bricks[7] = new Brick(400,400);
		bricks[8] = new Brick(400,440);
		bricks[9] = new Brick(400,480);
		bricks[10] = new Brick(880,400);
		bricks[11] = new Brick(880,440);
		bricks[12] = new Brick(880,480);
		bricks[13] = new Brick(880,520);
		bricks[14] = new Brick(880,560);
		bricks[15] = new Brick(280,400);
		bricks[16] = new Brick(280,440);
		bricks[17] = new Brick(280,480);
		bricks[18] = new Brick(280,520);
		bricks[19] = new Brick(280,560);
		bricks[20] = new Brick(1000,400);
		bricks[21] = new Brick(1000,440);
		bricks[22] = new Brick(1000,480);
		bricks[23] = new Brick(1000,520);
		bricks[24] = new Brick(1000,560);
		bricks[25] = new Brick(1000,600);
		bricks[26] = new Brick(1000,640);
		bricks[27] = new Brick(160,400);
		bricks[28] = new Brick(160,440);
		bricks[29] = new Brick(160,480);
		bricks[30] = new Brick(160,520);
		bricks[31] = new Brick(160,560);
		bricks[32] = new Brick(160,600);
		bricks[33] = new Brick(160,640);
		return bricks;
	}
	
	public Brick[] levelSix(Brick[] bricks, GameScreen context) {
		bricks = new Brick[64];
		context.setOuter(64);
		context.setInner(1);
		bricks[0] = new Brick(560,400,40,80);
		bricks[1] = new Brick(600,400,40,80);
		bricks[2] = new Brick(640,400,40,80);
		bricks[3] = new Brick(680,400,40,80);
		
		bricks[4] = new Brick(480,360,40,80);
		bricks[5] = new Brick(480,440,40,80);
		bricks[6] = new Brick(520,360,40,80);
		bricks[7] = new Brick(520,440,40,80);
		bricks[8] = new Brick(720,360,40,80);
		bricks[9] = new Brick(720,440,40,80);
		bricks[10] = new Brick(760,360,40,80);
		bricks[11] = new Brick(760,440,40,80);
		
		
		bricks[12] = new Brick(400,320,40,80);
		bricks[13] = new Brick(400,400,40,80);
		bricks[14] = new Brick(400,480,40,80);
		bricks[15] = new Brick(440,320,40,80);
		bricks[16] = new Brick(440,400,40,80);
		bricks[17] = new Brick(440,480,40,80);
		bricks[18] = new Brick(800,320,40,80);
		bricks[19] = new Brick(800,400,40,80);
		bricks[20] = new Brick(800,480,40,80);
		bricks[21] = new Brick(840,320,40,80);
		bricks[22] = new Brick(840,400,40,80);
		bricks[23] = new Brick(840,480,40,80);
		
		bricks[24] = new Brick(320,280,40,80);
		bricks[25] = new Brick(320,360,40,80);
		bricks[26] = new Brick(320,440,40,80);
		bricks[27] = new Brick(320,520,40,80);
		bricks[28] = new Brick(360,280,40,80);
		bricks[29] = new Brick(360,360,40,80);
		bricks[30] = new Brick(360,440,40,80);
		bricks[31] = new Brick(360,520,40,80);
		bricks[32] = new Brick(880,280,40,80);
		bricks[33] = new Brick(880,360,40,80);
		bricks[34] = new Brick(880,440,40,80);
		bricks[35] = new Brick(880,520,40,80);
		bricks[36] = new Brick(920,280,40,80);
		bricks[37] = new Brick(920,360,40,80);
		bricks[38] = new Brick(920,440,40,80);
		bricks[39] = new Brick(920,520,40,80);
		
		bricks[40] = new Brick(240,200,40,80);
		bricks[41] = new Brick(240,280,40,80);
		bricks[42] = new Brick(240,360,40,80);
		bricks[43] = new Brick(240,440,40,80);
		bricks[44] = new Brick(240,520,40,80);
		bricks[45] = new Brick(240,600,40,80);
		bricks[46] = new Brick(280,200,40,80);
		bricks[47] = new Brick(280,280,40,80);
		bricks[48] = new Brick(280,360,40,80);
		bricks[49] = new Brick(280,440,40,80);
		bricks[50] = new Brick(280,520,40,80);
		bricks[51] = new Brick(280,600,40,80);
		bricks[52] = new Brick(960,200,40,80);
		bricks[53] = new Brick(960,280,40,80);
		bricks[54] = new Brick(960,360,40,80);
		bricks[55] = new Brick(960,440,40,80);
		bricks[56] = new Brick(960,520,40,80);
		bricks[57] = new Brick(960,600,40,80);
		bricks[58] = new Brick(1000,200,40,80);
		bricks[59] = new Brick(1000,280,40,80);
		bricks[60] = new Brick(1000,360,40,80);
		bricks[61] = new Brick(1000,440,40,80);
		bricks[62] = new Brick(1000,520,40,80);
		bricks[63] = new Brick(1000,600,40,80);
		return bricks;
	}
	public Brick[] levelSeven(Brick[] bricks, GameScreen context) {
		bricks = new Brick[65];
		context.setOuter(5);
		context.setInner(13);
		bricks[0] = new Brick(400,640,80,40);
		bricks[1] = new Brick(480,640,80,40);
		bricks[2] = new Brick(560,640,80,40);
		bricks[3] = new Brick(640,640,80,40);
		bricks[4] = new Brick(720,640,80,40);
		bricks[5] = new Brick(800,640,80,40);
		bricks[6] = new Brick(880,640,80,40);
		
		bricks[7] = new Brick(400,160,80,40);
		bricks[8] = new Brick(480,160,80,40);
		bricks[9] = new Brick(560,160,80,40);
		bricks[10] = new Brick(640,160,80,40);
		bricks[11] = new Brick(720,160,80,40);
		bricks[12] = new Brick(800,160,80,40);
		bricks[13] = new Brick(880,160,80,40);
		
		bricks[14] = new Brick(440,600,80,40);
		bricks[15] = new Brick(520,600,80,40);
		bricks[16] = new Brick(600,600,80,40);
		bricks[17] = new Brick(680,600,80,40);
		bricks[18] = new Brick(760,600,80,40);
		bricks[19] = new Brick(840,600,80,40);
		
		bricks[20] = new Brick(440,200,80,40);
		bricks[21] = new Brick(520,200,80,40);
		bricks[22] = new Brick(600,200,80,40);
		bricks[23] = new Brick(680,200,80,40);
		bricks[24] = new Brick(760,200,80,40);
		bricks[25] = new Brick(840,200,80,40);
		
		bricks[26] = new Brick(480,560,80,40);
		bricks[27] = new Brick(560,560,80,40);
		bricks[28] = new Brick(640,560,80,40);
		bricks[29] = new Brick(720,560,80,40);
		bricks[30] = new Brick(800,560,80,40);
		
		bricks[31] = new Brick(480,240,80,40);
		bricks[32] = new Brick(560,240,80,40);
		bricks[33] = new Brick(640,240,80,40);
		bricks[34] = new Brick(720,240,80,40);
		bricks[35] = new Brick(800,240,80,40);
		
		bricks[36] = new Brick(520,520,80,40);
		bricks[37] = new Brick(600,520,80,40);
		bricks[38] = new Brick(680,520,80,40);
		bricks[39] = new Brick(760,520,80,40);
		
		bricks[40] = new Brick(520,280,80,40);
		bricks[41] = new Brick(600,280,80,40);
		bricks[42] = new Brick(680,280,80,40);
		bricks[43] = new Brick(760,280,80,40);
		
		bricks[44] = new Brick(560,480,80,40);
		bricks[45] = new Brick(640,480,80,40);
		bricks[46] = new Brick(720,480,80,40);
		
		bricks[47] = new Brick(560,320,80,40);
		bricks[48] = new Brick(640,320,80,40);
		bricks[49] = new Brick(720,320,80,40);
		
		bricks[50] = new Brick(600,440,80,40);
		bricks[51] = new Brick(680,440,80,40);
		
		bricks[52] = new Brick(600,360,80,40);
		bricks[53] = new Brick(680,360,80,40);
		
		bricks[54] = new Brick(640,400,80,40);
		
		bricks[55] = new Brick(200,440,80,40);
		bricks[56] = new Brick(280,440,80,40);
		bricks[57] = new Brick(240,400,80,40);
		bricks[58] = new Brick(200,360,80,40);
		bricks[59] = new Brick(280,360,80,40);
		
		bricks[60] = new Brick(1120,400,80,40);
		bricks[61] = new Brick(1080,440,80,40);
		bricks[62] = new Brick(1080,360,80,40);
		bricks[63] = new Brick(1160,440,80,40);
		bricks[64] = new Brick(1160,360,80,40);
		
		
		return bricks;
	}
	public Brick[] levelEight(Brick[] bricks, GameScreen context) {
		bricks = new Brick[89];
		context.setOuter(89);
		context.setInner(1);
		//Top leaf
		bricks[0] = new Brick(640,600,40,40);
		bricks[1] = new Brick(540,640,100,40);
		bricks[2] = new Brick(680,640,100,40);
		bricks[3] = new Brick(780,560,40,80);
		bricks[4] = new Brick(760,520,40,40);
		bricks[5] = new Brick(740,480,40,40);
		bricks[6] = new Brick(660,440,80,40);
		bricks[7] = new Brick(580,440,80,40);
		bricks[8] = new Brick(540,480,40,40);
		bricks[9] = new Brick(520,520,40,40);
		bricks[10] = new Brick(500,560,40,80);
		
		//Middle bricks
		bricks[11] = new Brick(640,400,40,40);
		bricks[12] = new Brick(580,380,60,40);
		bricks[13] = new Brick(640,360,40,40);
		bricks[14] = new Brick(680,380,60,40);
		//Right Leaf
		bricks[15] = new Brick(740,400,40,80);
		bricks[16] = new Brick(740,320,40,80);
		bricks[17] = new Brick(780,280,40,40);
		bricks[18] = new Brick(820,260,40,40);
		bricks[19] = new Brick(860,240,80,40);
		bricks[20] = new Brick(940,280,40,100);
		bricks[21] = new Brick(900,380,40,40);
		bricks[22] = new Brick(940,420,40,100);
		bricks[23] = new Brick(860,520,80,40);
		bricks[24] = new Brick(820,500,40,40);
		bricks[25] = new Brick(780,480,40,40);
		// Bottom Leaf
		bricks[26] = new Brick(580,320,80,40);
		bricks[27] = new Brick(660,320,80,40);
		bricks[28] = new Brick(540,280,40,40);
		bricks[29] = new Brick(520,240,40,40);
		bricks[30] = new Brick(500,160,40,80);
		bricks[31] = new Brick(540,120,100,40);
		bricks[32] = new Brick(640,160,40,40);
		bricks[33] = new Brick(680,120,100,40);
		bricks[34] = new Brick(780,160,40,80);
		bricks[35] = new Brick(760,240,40,40);
		bricks[36] = new Brick(740,280,40,40);
		//left leaf
		bricks[37] = new Brick(540,400,40,80);
		bricks[38] = new Brick(540,320,40,80);
		bricks[39] = new Brick(500,280,40,40);
		bricks[40] = new Brick(460,260,40,40);
		bricks[41] = new Brick(380,240,80,40);
		bricks[42] = new Brick(340,280,40,100);
		bricks[43] = new Brick(380,380,40,40);
		bricks[44] = new Brick(340,420,40,100);
		bricks[45] = new Brick(380,520,80,40);
		bricks[46] = new Brick(460,500,40,40);
		bricks[47] = new Brick(500,480,40,40);
		
		//Inside top leaf
		bricks[48] = new Brick(660,480,80,40);
		bricks[49] = new Brick(580,480,80,40);
		bricks[50] = new Brick(560,520,100,40);
		bricks[51] = new Brick(660,520,100,40);
		bricks[52] = new Brick(540,560,80,40);
		bricks[53] = new Brick(620,560,80,40);
		bricks[54] = new Brick(700,560,80,40);
		bricks[55] = new Brick(540,600,100,40);
		bricks[56] = new Brick(680,600,100,40);
		//Inside right leaf
		bricks[57] = new Brick(780,320,40,80);
		bricks[58] = new Brick(780,400,40,80);
		bricks[59] = new Brick(820,300,40,100);
		bricks[60] = new Brick(820,400,40,100);
		bricks[61] = new Brick(860,280,40,80);
		bricks[62] = new Brick(860,360,40,80);
		bricks[63] = new Brick(860,440,40,80);
		bricks[64] = new Brick(900,420,40,100);
		bricks[65] = new Brick(900,280,40,100);
		//Inside bottom leaf
		bricks[66] = new Brick(660,280,80,40);
		bricks[67] = new Brick(580,280,80,40);
		bricks[68] = new Brick(560,240,100,40);
		bricks[69] = new Brick(660,240,100,40);
		bricks[70] = new Brick(540,200,80,40);
		bricks[71] = new Brick(620,200,80,40);
		bricks[72] = new Brick(700,200,80,40);
		bricks[73] = new Brick(540,160,100,40);
		bricks[74] = new Brick(680,160,100,40);
		//Inside left leaf
		bricks[75] = new Brick(500,320,40,80);
		bricks[76] = new Brick(500,400,40,80);
		bricks[77] = new Brick(460,300,40,100);
		bricks[78] = new Brick(460,400,40,100);
		bricks[79] = new Brick(420,280,40,80);
		bricks[80] = new Brick(420,360,40,80);
		bricks[81] = new Brick(420,440,40,80);
		bricks[82] = new Brick(380,420,40,100);
		bricks[83] = new Brick(380,280,40,100);
		//Stem
		bricks[84] = new Brick(460,220,40,40);
		bricks[85] = new Brick(420,200,40,40);
		bricks[86] = new Brick(340,180,80,40);
		bricks[87] = new Brick(260,160,80,40);
		bricks[88] = new Brick(180,140,80,40);
		return bricks;
	}
	public Brick[] levelNine(Brick[] bricks, GameScreen context) {
		bricks = new Brick[61];
		context.setOuter(61);
		context.setInner(1);
		//Head
		bricks[0] = new Brick(640,620,10,20);
		bricks[1] = new Brick(635,640,10,20);
		bricks[2] = new Brick(630,660,10,20);
		bricks[3] = new Brick(710,620,10,20);
		bricks[4] = new Brick(715,640,10,20);
		bricks[5] = new Brick(720,660,10,20);
		
		bricks[6] = new Brick(680,580,40,40);
		bricks[7] = new Brick(640,580,40,40);
		bricks[8] = new Brick(620,540,40,40);
		bricks[9] = new Brick(660,540,40,40);
		bricks[10] = new Brick(700,540,40,40);
		//Legs
		bricks[11] = new Brick(440,440,80,20);
		bricks[12] = new Brick(360,440,80,20);
		bricks[13] = new Brick(340,440,20,80);
				
		bricks[14] = new Brick(360,360,80,20);
		bricks[15] = new Brick(280,360,80,20);
				
		bricks[16] = new Brick(440,260,80,20);
		bricks[17] = new Brick(360,260,80,20);
		bricks[18] = new Brick(340,200,20,80);
			
		bricks[19] = new Brick(840,260,80,20);
		bricks[20] = new Brick(920,260,80,20);
		bricks[21] = new Brick(1000,200,20,80);
			
		bricks[22] = new Brick(920,360,80,20);
		bricks[23] = new Brick(1000,360,80,20);
				
		bricks[24] = new Brick(840,440,80,20);
		bricks[25] = new Brick(920,440,80,20);
		bricks[26] = new Brick(1000,440,20,80);
		
		//spots
		bricks[27] = new Brick(640,460,80,40);
		bricks[28] = new Brick(560,380,80,40);
		bricks[29] = new Brick(720,380,80,40);
		bricks[30] = new Brick(560,300,80,40);
		bricks[31] = new Brick(720,300,80,40);
		bricks[32] = new Brick(640,220,80,40);

		//Body 
		bricks[33] = new Brick(600,500,80,40);
		bricks[34] = new Brick(680,500,80,40);
		bricks[35] = new Brick(560,460,80,40);
		bricks[36] = new Brick(720,460,80,40);
		bricks[37] = new Brick(520,420,80,40);
		bricks[38] = new Brick(600,420,80,40);
		bricks[39] = new Brick(680,420,80,40);
		bricks[40] = new Brick(760,420,80,40);
		bricks[41] = new Brick(480,380,80,40);
		bricks[42] = new Brick(640,380,80,40);
		bricks[43] = new Brick(800,380,80,40);
		bricks[44] = new Brick(440,340,80,40);
		bricks[45] = new Brick(520,340,80,40);
		bricks[46] = new Brick(600,340,80,40);
		bricks[47] = new Brick(680,340,80,40);
		bricks[48] = new Brick(760,340,80,40);
		bricks[49] = new Brick(840,340,80,40);
		bricks[50] = new Brick(480,300,80,40);
		bricks[51] = new Brick(640,300,80,40);
		bricks[52] = new Brick(800,300,80,40);
		bricks[53] = new Brick(520,260,80,40);
		bricks[54] = new Brick(600,260,80,40);
		bricks[55] = new Brick(680,260,80,40);
		bricks[56] = new Brick(760,260,80,40);
		bricks[57] = new Brick(560,220,80,40);
		bricks[58] = new Brick(720,220,80,40);
		bricks[59] = new Brick(600,180,80,40);
		bricks[60] = new Brick(680,180,80,40);	
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
