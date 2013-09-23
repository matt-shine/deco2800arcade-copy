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
	public Brick[] levelTen(Brick[] bricks, GameScreen context) {
		bricks = new Brick[189];
		context.setOuter(189);
		context.setInner(1);
		//Layer one of trophy
		bricks[0] = new Brick(600,640,80,40);
		bricks[1] = new Brick(520,640,80,40);
		bricks[2] = new Brick(440,640,80,40);
		bricks[3] = new Brick(680,640,80,40);
		bricks[4] = new Brick(760,640,80,40);
		//Layer two of trophy
		bricks[5] = new Brick(480,600,80,40);
		bricks[6] = new Brick(560,600,80,40);
		bricks[7] = new Brick(640,600,80,40);
		bricks[8] = new Brick(720,600,80,40);
		//Layer three of trophy
		bricks[9] = new Brick(520,560,80,40);
		bricks[10] = new Brick(600,560,80,40);
		bricks[11] = new Brick(680,560,80,40);
		//Layer four of trophy
		bricks[12] = new Brick(560,520,80,40);
		bricks[13] = new Brick(640,520,80,40);
		//Layer five and six of trophy
		bricks[14] = new Brick(600,480,80,40);
		bricks[15] = new Brick(600,440,80,40);
		//Layer seven
		bricks[16] = new Brick(560,400,80,40);
		bricks[17] = new Brick(640,400,80,40);
		//Left Handle
		bricks[18] = new Brick(400,620,80,20);
		bricks[19] = new Brick(380,540,20,100);
		bricks[20] = new Brick(400,540,80,20);
		bricks[21] = new Brick(480,540,80,20);
		//Right Handle
		bricks[22] = new Brick(800,620,80,20);
		bricks[23] = new Brick(880,540,20,100);
		bricks[24] = new Brick(800,540,80,20);
		bricks[25] = new Brick(720,540,80,20);
		//Letter C
		bricks[26] = new Brick(40,240,20,20);
		bricks[27] = new Brick(50,220,20,20);
		bricks[28] = new Brick(60,200,20,20);
		bricks[29] = new Brick(80,200,20,20);
		bricks[30] = new Brick(50,260,20,20);
		bricks[31] = new Brick(60,280,20,20);
		bricks[32] = new Brick(80,280,20,20);
		//Letter A
		bricks[33] = new Brick(440,200,20,20);
		bricks[34] = new Brick(440,220,20,20);
		bricks[35] = new Brick(440,240,20,20);
		bricks[36] = new Brick(450,260,20,20);
		bricks[37] = new Brick(460,280,20,20);
		bricks[38] = new Brick(480,280,20,20);
		bricks[39] = new Brick(490,260,20,20);
		bricks[40] = new Brick(500,240,20,20);
		bricks[41] = new Brick(500,220,20,20);
		bricks[42] = new Brick(500,200,20,20);
		bricks[43] = new Brick(460,220,20,20);
		bricks[44] = new Brick(480,220,20,20);
		//Letter T
		bricks[45] = new Brick(850,280,20,20);
		bricks[46] = new Brick(870,280,20,20);
		bricks[47] = new Brick(890,280,20,20);
		bricks[48] = new Brick(870,260,20,20);
		bricks[49] = new Brick(870,240,20,20);
		bricks[50] = new Brick(870,220,20,20);
		bricks[51] = new Brick(870,200,20,20);
		//Letter O
		bricks[52] = new Brick(110,220,20,20);
		bricks[53] = new Brick(120,200,20,20);
		bricks[54] = new Brick(140,200,20,20);
		bricks[55] = new Brick(150,220,20,20);
		bricks[56] = new Brick(110,240,20,20);
		bricks[57] = new Brick(110,260,20,20);
		bricks[58] = new Brick(120,280,20,20);
		bricks[59] = new Brick(150,240,20,20);
		bricks[60] = new Brick(150,260,20,20);
		bricks[61] = new Brick(140,280,20,20);
		//Letter T
		bricks[62] = new Brick(530,280,20,20);
		bricks[63] = new Brick(550,280,20,20);
		bricks[64] = new Brick(570,280,20,20);
		bricks[65] = new Brick(550,260,20,20);
		bricks[66] = new Brick(550,240,20,20);
		bricks[67] = new Brick(550,220,20,20);
		bricks[68] = new Brick(550,200,20,20);
		//Letter I
		bricks[69] = new Brick(920,280,20,20);
		bricks[70] = new Brick(920,260,20,20);
		bricks[71] = new Brick(920,240,20,20);
		bricks[72] = new Brick(920,220,20,20);
		bricks[73] = new Brick(920,200,20,20);
		//Letter N
		bricks[74] = new Brick(180,200,20,20);
		bricks[75] = new Brick(180,220,20,20);
		bricks[76] = new Brick(180,240,20,20);
		bricks[77] = new Brick(180,260,20,20);
		bricks[78] = new Brick(180,280,20,20);
		bricks[79] = new Brick(200,260,20,20);
		bricks[80] = new Brick(210,240,20,20);
		bricks[81] = new Brick(230,220,20,20);
		bricks[82] = new Brick(250,200,20,20);
		bricks[83] = new Brick(250,220,20,20);
		bricks[84] = new Brick(250,240,20,20);
		bricks[85] = new Brick(250,260,20,20);
		bricks[86] = new Brick(250,280,20,20);
		//Letter U
		bricks[87] = new Brick(600,280,20,20);
		bricks[88] = new Brick(600,260,20,20);
		bricks[89] = new Brick(600,240,20,20);
		bricks[90] = new Brick(600,220,20,20);
		bricks[91] = new Brick(620,200,20,20);
		bricks[92] = new Brick(640,200,20,20);
		bricks[93] = new Brick(660,220,20,20);
		bricks[94] = new Brick(660,240,20,20);
		bricks[95] = new Brick(660,260,20,20);
		bricks[96] = new Brick(660,280,20,20);
		//Letter O
		bricks[97] = new Brick(950,220,20,20);
		bricks[98] = new Brick(960,200,20,20);
		bricks[99] = new Brick(980,200,20,20);
		bricks[100] = new Brick(990,220,20,20);
		bricks[101] = new Brick(950,240,20,20);
		bricks[102] = new Brick(950,260,20,20);
		bricks[103] = new Brick(980,280,20,20);
		bricks[104] = new Brick(990,240,20,20);
		bricks[105] = new Brick(990,260,20,20);
		bricks[106] = new Brick(960,280,20,20);
		//Letter G
		bricks[107] = new Brick(280,220,20,20);
		bricks[108] = new Brick(280,240,20,20);
		bricks[109] = new Brick(280,260,20,20);
		bricks[110] = new Brick(290,280,20,20);
		bricks[111] = new Brick(310,280,20,20);
		bricks[112] = new Brick(290,200,20,20);
		bricks[113] = new Brick(310,200,20,20);
		bricks[114] = new Brick(330,220,20,20);
		bricks[115] = new Brick(330,240,20,20);
		bricks[116] = new Brick(310,240,20,20);
		//Letter L
		bricks[117] = new Brick(690,200,20,20);
		bricks[118] = new Brick(690,220,20,20);
		bricks[119] = new Brick(690,240,20,20);
		bricks[120] = new Brick(690,260,20,20);
		bricks[121] = new Brick(690,280,20,20);
		bricks[122] = new Brick(710,200,20,20);
		bricks[123] = new Brick(730,200,20,20);
		//Letter N
		bricks[124] = new Brick(1020,200,20,20);
		bricks[125] = new Brick(1020,220,20,20);
		bricks[126] = new Brick(1020,240,20,20);
		bricks[127] = new Brick(1020,260,20,20);
		bricks[128] = new Brick(1020,280,20,20);
		bricks[129] = new Brick(1040,260,20,20);
		bricks[130] = new Brick(1050,240,20,20);
		bricks[131] = new Brick(1070,220,20,20);
		bricks[132] = new Brick(1090,200,20,20);
		bricks[133] = new Brick(1090,220,20,20);
		bricks[134] = new Brick(1090,240,20,20);
		bricks[135] = new Brick(1090,260,20,20);
		bricks[136] = new Brick(1090,280,20,20);
		//Letter R
		bricks[137] = new Brick(360,200,20,20);
		bricks[138] = new Brick(360,220,20,20);
		bricks[139] = new Brick(360,240,20,20);
		bricks[140] = new Brick(360,260,20,20);
		bricks[141] = new Brick(360,280,20,20);
		bricks[142] = new Brick(380,280,20,20);
		bricks[143] = new Brick(400,260,20,20);
		bricks[144] = new Brick(380,240,20,20);
		bricks[145] = new Brick(400,220,20,20);
		bricks[146] = new Brick(410,200,20,20);
		//Letter A
		bricks[147] = new Brick(760,200,20,20);
		bricks[148] = new Brick(760,220,20,20);
		bricks[149] = new Brick(760,240,20,20);
		bricks[150] = new Brick(770,260,20,20);
		bricks[151] = new Brick(780,280,20,20);
		bricks[152] = new Brick(800,280,20,20);
		bricks[153] = new Brick(810,260,20,20);
		bricks[154] = new Brick(820,240,20,20);
		bricks[155] = new Brick(820,220,20,20);
		bricks[156] = new Brick(820,200,20,20);
		bricks[157] = new Brick(780,220,20,20);
		bricks[158] = new Brick(800,220,20,20);
		//Letter S
		bricks[159] = new Brick(1160,280,20,20);
		bricks[160] = new Brick(1140,280,20,20);
		bricks[161] = new Brick(1120,260,20,20);
		bricks[162] = new Brick(1120,240,20,20);
		bricks[163] = new Brick(1140,240,20,20);
		bricks[164] = new Brick(1160,220,20,20);
		bricks[165] = new Brick(1140,200,20,20);
		bricks[166] = new Brick(1120,200,20,20);
		//Left mini trophy 
		bricks[167] = new Brick(270,440,40,40);
		bricks[168] = new Brick(280,480,20,20);
		bricks[169] = new Brick(270,500,40,40);
		bricks[170] = new Brick(250,540,40,40);
		bricks[171] = new Brick(290,540,40,40);
		//Left mini trophy handle
		bricks[172] = new Brick(230,570,20,10);
		bricks[173] = new Brick(220,540,10,40);
		bricks[174] = new Brick(230,540,20,10);
		
		bricks[175] = new Brick(330,570,20,10);
		bricks[176] = new Brick(350,540,10,40);
		bricks[177] = new Brick(330,540,20,10);
		//Right mini trophy
		bricks[178] = new Brick(970,440,40,40);
		bricks[179] = new Brick(980,480,20,20);
		bricks[180] = new Brick(970,500,40,40);
		bricks[181] = new Brick(950,540,40,40);
		bricks[182] = new Brick(990,540,40,40);
		//Right mini trophy handle
		bricks[183] = new Brick(930,570,20,10);
		bricks[184] = new Brick(920,540,10,40);
		bricks[185] = new Brick(930,540,20,10);
		
		bricks[186] = new Brick(1030,570,20,10);
		bricks[187] = new Brick(1050,540,10,40);
		bricks[188] = new Brick(1030,540,20,10);
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
