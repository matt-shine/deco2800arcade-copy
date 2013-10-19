package deco2800.arcade.breakout.screens;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import org.junit.*;
import org.mockito.Mockito;

import com.badlogic.gdx.math.Vector2;

import deco2800.arcade.breakout.Ball;
import deco2800.arcade.breakout.Paddle;

public class PaddleTest {

		GameScreen context = mock(GameScreen.class);
		Paddle mockPaddle = mock(Paddle.class);
		Vector2 mockV2 = mock(Vector2.class);
		Ball mockBall = mock(Ball.class);

		
		@Test
		public void decreasePaddle() throws Exception {
 			mockPaddle.setWidth(mockPaddle.getStandardWidth());
 			mockPaddle.decreaseSize();
 			assertEquals(mockPaddle.getStandardWidth()/2, mockPaddle.getPaddleShapeWidth(),0.001);
		}
	
				@Test
				public void increasePaddle() throws Exception {
					mockPaddle.setWidth(mockPaddle.getStandardWidth());
					mockPaddle.increaseSize();
					assertEquals(mockPaddle.getStandardWidth()*2, mockPaddle.getPaddleShapeWidth(),0.001);
				}
				
				@Test
				public void setPaddleWidth() throws Exception {
					float width = (float) 77.7;
					mockPaddle.setWidth(width);
					assertEquals(mockPaddle.getStandardWidth()/2, mockPaddle.getPaddleShapeWidth(),0.001);
				}
				
			/*	@Test
				public void moveTest() throws Exception {
					mockPaddle.setWidth(mockPaddle.getStandardWidth());
					mockV2 = new Vector2(Breakout.SCREENWIDTH / 2, 10);
					mockPaddle.setPosition(mockV2);
					mockPaddle.update(mockBall);
					float expectedX = Breakout.SCREENWIDTH - mockPaddle.getWidth();
					assertEquals(expectedX,  mockPaddle.getPaddleX(),0.001);
				}*/

}
